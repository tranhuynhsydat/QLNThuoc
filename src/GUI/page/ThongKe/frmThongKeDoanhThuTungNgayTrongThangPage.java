/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package GUI.page.ThongKe;

import Entity.ThongKe;
import GUI.barchart.ModelChart;
import java.time.LocalDate;
import java.util.List;
import GUI.barchart.chart;
import Utils.Formatter;
import Utils.TableSorter;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import DAO.ThongKeDAO;
import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.border.LineBorder;

/**
 *
 * @author roxan
 */
public class frmThongKeDoanhThuTungNgayTrongThangPage extends javax.swing.JPanel {

    private final int currentMonth = LocalDate.now().getMonthValue();
    private final int currentYear = LocalDate.now().getYear();
    private List<ThongKe> listTK; // Danh sách thống kê theo ngày
    private DefaultTableModel modal;
    private ThongKeDAO thongKeDAO; // Đối tượng ThongKeDAO để truy vấn dữ liệu

    public frmThongKeDoanhThuTungNgayTrongThangPage() {
        initComponents();
        thongKeDAO = new ThongKeDAO(); // Khởi tạo ThongKeDAO
        chartLayout();
        tableLayout();
        loadDataset();
        tinhTong();
    }

    private void tinhTong() {
        int colDoanhThu = 1;
        int colChiPhi = 2;   
        long tongDoanhThu = 0;
        long tongChiPhi = 0;

        for (int i = 0; i < table.getRowCount(); i++) {
            // Tính doanh thu
            Object valDT = table.getValueAt(i, colDoanhThu);
            if (valDT != null) {
                String sDT = valDT.toString().trim().replaceAll("[^0-9]", "");
                if (!sDT.isEmpty()) {
                    try {
                        tongDoanhThu += Long.parseLong(sDT);
                    } catch (NumberFormatException e) {
                    }
                }
            }

            // Tính chi phí
            Object valCP = table.getValueAt(i, colChiPhi);
            if (valCP != null) {
                String sCP = valCP.toString().trim().replaceAll("[^0-9]", "");
                if (!sCP.isEmpty()) {
                    try {
                        tongChiPhi += Long.parseLong(sCP);
                    } catch (NumberFormatException e) {
                    }
                }
            }
        }

        // Lợi nhuận
        long loiNhuan = tongDoanhThu - tongChiPhi;

        // Định dạng số với dấu phân cách và đính kèm "đ"
        java.text.NumberFormat nf = java.text.NumberFormat.getInstance(new java.util.Locale("vi", "VN"));

        jLabel4.setText(nf.format(tongDoanhThu) + "đ");
        jLabel5.setText(nf.format(tongChiPhi) + "đ");
        jLabel7.setText(nf.format(loiNhuan) + "đ");
    }

    // Thiết lập layout cho biểu đồ
    private void chartLayout() {
        txtMonth.setMonth(currentMonth - 1);
        txtYear.setValue(currentYear);

        chart.addLegend("Doanh thu", new Color(135, 189, 245));
        chart.addLegend("Chi phí", new Color(245, 189, 135));
        chart.addLegend("Lợi nhuận", new Color(139, 225, 196));

        chart.start();
    }

    // Tải dữ liệu vào biểu đồ
    private void loadChart() {
        double sum_doanhthu = 0;
        double sum_chiphi = 0;
        double sum_loinhuan = 0;

        for (int day = 0; day < listTK.size(); day++) {
            sum_doanhthu += listTK.get(day).getDoanhThu();
            sum_chiphi += listTK.get(day).getChiPhi();
            sum_loinhuan += listTK.get(day).getLoiNhuan();
            if ((day + 1) % 3 == 0 || day == listTK.size() - 1) {
                int startDay = day - 2;
                if (startDay < 0) {
                    startDay = 0;
                }
                int endDay = day;
                chart.addData(new ModelChart("Ngày " + (startDay + 1) + " - " + (endDay + 1), new double[]{sum_doanhthu, sum_chiphi, sum_loinhuan}));
                sum_doanhthu = 0;
                sum_chiphi = 0;
                sum_loinhuan = 0;
            }
        }
    }

    // Thiết lập layout cho bảng
    private void tableLayout() {
        String[] header = new String[]{"Thời gian", "Doanh thu", "Chi phí", "Lợi nhuận"};
        modal = new DefaultTableModel();
        modal.setColumnIdentifiers(header);
        table.setModel(modal);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        table.setDefaultRenderer(Object.class, centerRenderer);
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(0).setPreferredWidth(30);

        sortTable();
    }

    // Sắp xếp bảng
    private void sortTable() {
        table.setAutoCreateRowSorter(true);
        TableSorter.configureTableColumnSorter(table, 0, TableSorter.STRING_COMPARATOR);
    }

    // Tải dữ liệu vào bảng
    private void loadTable() {
        modal.setRowCount(0);
        for (ThongKe e : listTK) {
            modal.addRow(new Object[]{
                Formatter.FormatDate(e.getThoiGian()), Formatter.FormatVND(e.getDoanhThu()), Formatter.FormatVND(e.getChiPhi()), Formatter.FormatVND(e.getLoiNhuan())
            });
        }
    }

    // Tải dữ liệu (biểu đồ và bảng)
    private void loadDataset() {
        chart.clear();

        // Lấy dữ liệu từ ThongKeDAO thay vì ThongKeController
        listTK = thongKeDAO.select3DaysByMonthYear(currentMonth, currentYear);

        loadChart();
        loadTable();
        chart.start();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        lblChart = new javax.swing.JLabel();
        txtMonth = new com.toedter.calendar.JMonthChooser();
        lblChart1 = new javax.swing.JLabel();
        txtYear = new com.toedter.components.JSpinField();
        btnStatistic = new javax.swing.JButton();
        chart = new GUI.barchart.chart();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();

        setMinimumSize(new java.awt.Dimension(829, 624));
        setPreferredSize(new java.awt.Dimension(829, 624));
        setLayout(new java.awt.BorderLayout());

        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.PAGE_AXIS));

        jPanel5.setBackground(new java.awt.Color(247, 247, 247));
        jPanel5.setPreferredSize(new java.awt.Dimension(1188, 30));
        jPanel5.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 8, 0));

        lblChart.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        lblChart.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblChart.setText("Tháng");
        lblChart.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblChart.setPreferredSize(new java.awt.Dimension(60, 30));
        jPanel5.add(lblChart);

        txtMonth.setPreferredSize(new java.awt.Dimension(130, 26));
        jPanel5.add(txtMonth);

        lblChart1.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        lblChart1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblChart1.setText("Năm");
        lblChart1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblChart1.setPreferredSize(new java.awt.Dimension(40, 30));
        jPanel5.add(lblChart1);

        txtYear.setPreferredSize(new java.awt.Dimension(80, 26));
        jPanel5.add(txtYear);

        btnStatistic.setBackground(new java.awt.Color(51, 153, 255));
        btnStatistic.setForeground(new java.awt.Color(204, 255, 255));
        btnStatistic.setText("Thống kê");
        btnStatistic.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStatisticActionPerformed(evt);
            }
        });
        jPanel5.add(btnStatistic);

        jPanel1.add(jPanel5);
        jPanel1.add(chart);

        jScrollPane1.setPreferredSize(new java.awt.Dimension(456, 300));

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Thời gian", "Doanh Thu", "Chi phí", "Lợi nhuận"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table.setFocusable(false);
        table.setRowHeight(40);
        table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        table.setShowHorizontalLines(true);
        jScrollPane1.setViewportView(table);

        jPanel1.add(jScrollPane1);

        jPanel2.setPreferredSize(new java.awt.Dimension(100, 30));
        jPanel2.setLayout(new javax.swing.BoxLayout(jPanel2, javax.swing.BoxLayout.LINE_AXIS));

        jPanel3.setMaximumSize(new java.awt.Dimension(230, 30));
        jPanel3.setMinimumSize(new java.awt.Dimension(230, 30));
        jPanel3.setPreferredSize(new java.awt.Dimension(230, 30));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 3, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 0, 51));
        jLabel3.setText("TỔNG:");
        jLabel3.setMaximumSize(new java.awt.Dimension(60, 25));
        jLabel3.setMinimumSize(new java.awt.Dimension(60, 25));
        jLabel3.setPreferredSize(new java.awt.Dimension(60, 25));
        jLabel3.setRequestFocusEnabled(false);
        jPanel3.add(jLabel3);

        jPanel2.add(jPanel3);

        jPanel4.setLayout(new javax.swing.BoxLayout(jPanel4, javax.swing.BoxLayout.X_AXIS));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("Doanh thu:");
        jPanel6.add(jLabel2);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(102, 102, 102));
        jPanel6.add(jLabel4);

        jPanel4.add(jPanel6);

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setText("Chi phí:");
        jPanel7.add(jLabel6);

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(102, 102, 102));
        jLabel5.setText("q");
        jLabel5.setMaximumSize(new java.awt.Dimension(72, 20));
        jLabel5.setMinimumSize(new java.awt.Dimension(72, 20));
        jLabel5.setPreferredSize(new java.awt.Dimension(72, 20));
        jPanel7.add(jLabel5);

        jPanel4.add(jPanel7);

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setText("Lợi nhuận:");
        jPanel8.add(jLabel8);

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(102, 102, 102));
        jLabel7.setText("s");
        jPanel8.add(jLabel7);

        jPanel4.add(jPanel8);

        jPanel2.add(jPanel4);

        jPanel1.add(jPanel2);

        add(jPanel1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents
    private boolean isValidFilterFields() {
        return true;
    }
    private void btnStatisticActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStatisticActionPerformed
        if (isValidFilterFields()) {
            int mounth = txtMonth.getMonth() + 1;
            int year = txtYear.getValue();

            listTK = thongKeDAO.select3DaysByMonthYear(mounth, year);
            loadDataset();
        }
    }//GEN-LAST:event_btnStatisticActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnStatistic;
    private GUI.barchart.chart chart;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblChart;
    private javax.swing.JLabel lblChart1;
    private javax.swing.JTable table;
    private com.toedter.calendar.JMonthChooser txtMonth;
    private com.toedter.components.JSpinField txtYear;
    // End of variables declaration//GEN-END:variables
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package GUI.page.ThongKe;

import DAO.ThongKeDAO;
import Entity.ThongKeTheoNam;
import GUI.barchart.ModelChart;
import Utils.Formatter;
import Utils.MessageDialog;
import Utils.TableSorter;
import Utils.Validation;
import java.awt.Color;
import java.time.LocalDate;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author roxan
 */
public class frmThongKeDoanhThuTheoNamPage extends javax.swing.JPanel {

    /**
     * Creates new form frmThongKeDoanhThuTheoNamPage
     */
    private final int currentYear = LocalDate.now().getYear();
    private List<ThongKeTheoNam> listTK;
    private DefaultTableModel modal;
    private ThongKeDAO thongKeDAO;

    public frmThongKeDoanhThuTheoNamPage() {
        initComponents();
        thongKeDAO = new ThongKeDAO();  // Khởi tạo ThongKeDAO để truy vấn trực tiếp
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
                String sDT = valDT.toString().trim().replaceAll("[^0-9\\-]", "");
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
                String sCP = valCP.toString().trim().replaceAll("[^0-9\\-]", "");
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

    // Thiết lập biểu đồ
    private void chartLayout() {
        chart.addLegend("Doanh thu", new Color(135, 189, 245));
        chart.addLegend("Chi phí", new Color(245, 189, 135));
        chart.addLegend("Lợi nhuận", new Color(139, 225, 196));
        chart.start();
    }

    // Tải dữ liệu vào biểu đồ
    private void loadChart() {
        for (ThongKeTheoNam e : listTK) {
            chart.addData(new ModelChart("Năm " + e.getNam(), new double[]{e.getDoanhThu(), e.getChiPhi(), e.getLoiNhuan()}));
        }
    }

    // Thiết lập bảng
    private void tableLayout() {
        String[] header = new String[]{"Năm", "Doanh thu", "Chi phí", "Lợi nhuận"};
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
        for (ThongKeTheoNam e : listTK) {
            modal.addRow(new Object[]{
                e.getNam() + "", Formatter.FormatVND(e.getDoanhThu()), Formatter.FormatVND(e.getChiPhi()), Formatter.FormatVND(e.getLoiNhuan())
            });
        }
    }

    // Tải dữ liệu thống kê (biểu đồ và bảng)
    private void loadDataset() {
        chart.clear();
        int startYear = Integer.parseInt(txtStartYear.getText()); // Năm bắt đầu
        int endYear = Integer.parseInt(txtEndYear.getText()); // Năm kết thúc
        listTK = thongKeDAO.selectFromYearToYear(startYear, endYear);  // Truy vấn trực tiếp trong DAO
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

        jPanel5 = new javax.swing.JPanel();
        lblChart = new javax.swing.JLabel();
        txtStartYear = new javax.swing.JTextField();
        lblChart1 = new javax.swing.JLabel();
        txtEndYear = new javax.swing.JTextField();
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

        setMinimumSize(new java.awt.Dimension(1130, 800));
        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.Y_AXIS));

        jPanel5.setBackground(new java.awt.Color(247, 247, 247));
        jPanel5.setPreferredSize(new java.awt.Dimension(1188, 30));
        jPanel5.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 8, 0));

        lblChart.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        lblChart.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblChart.setText("Từ năm");
        lblChart.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblChart.setPreferredSize(new java.awt.Dimension(60, 30));
        jPanel5.add(lblChart);

        txtStartYear.setText(String.valueOf(currentYear-10));
        jPanel5.add(txtStartYear);

        lblChart1.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        lblChart1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblChart1.setText("Đến năm");
        lblChart1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblChart1.setPreferredSize(new java.awt.Dimension(60, 30));
        jPanel5.add(lblChart1);

        txtEndYear.setText(String.valueOf(currentYear));
        txtEndYear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEndYearActionPerformed(evt);
            }
        });
        jPanel5.add(txtEndYear);

        btnStatistic.setBackground(new java.awt.Color(51, 153, 255));
        btnStatistic.setForeground(new java.awt.Color(204, 255, 255));
        btnStatistic.setText("Thống kê");
        btnStatistic.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStatisticActionPerformed(evt);
            }
        });
        jPanel5.add(btnStatistic);

        add(jPanel5);
        add(chart);

        jScrollPane1.setPreferredSize(new java.awt.Dimension(456, 300));

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã", "Họ tên", "Số điện thoại", "Giới tính", "Năm sinh", "Ngày vào làm"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
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

        add(jScrollPane1);

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
        jPanel8.add(jLabel7);

        jPanel4.add(jPanel8);

        jPanel2.add(jPanel4);

        add(jPanel2);
    }// </editor-fold>//GEN-END:initComponents
private boolean isValidFilterFields() {
        if (Validation.isEmpty(txtStartYear.getText().trim())) {
            MessageDialog.warring(this, "Không được để trống!");
            Validation.resetTextfield(txtStartYear);
            return false;
        }
        if (Validation.isEmpty(txtEndYear.getText().trim())) {
            MessageDialog.warring(this, "Không được để trống!");
            Validation.resetTextfield(txtEndYear);
            return false;
        }

        int fromYear = Integer.parseInt(txtStartYear.getText());
        int toYear = Integer.parseInt(txtEndYear.getText());

        try {
            if (fromYear <= 1900 || fromYear > currentYear
                    && toYear <= 1900 || toYear > currentYear) {
                MessageDialog.warring(this, "Số năm phải từ 1900 đến " + currentYear);
                return false;
            }
            if (toYear < fromYear) {
                MessageDialog.warring(this, "Số năm kết thúc phải >= năm bắt đầu!");
                Validation.selectAllTextfield(txtEndYear);
                return false;
            }
            if (toYear - fromYear >= 10) {
                MessageDialog.warring(this, "Hai năm không cách nhau quá 10 năm");
                Validation.selectAllTextfield(txtStartYear);
                return false;
            }
        } catch (NumberFormatException e) {
            MessageDialog.warring(this, "Số không hợp lệ!");
            Validation.resetTextfield(txtStartYear);
            return false;
        }

        return true;
    }
    private void btnStatisticActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStatisticActionPerformed
        if (isValidFilterFields()) {
            int startYear = Integer.parseInt(txtStartYear.getText());
            int endYear = Integer.parseInt(txtEndYear.getText());
            listTK = thongKeDAO.selectFromYearToYear(startYear, endYear); // Truy vấn trực tiếp trong DAO
            loadDataset();
        }
    }//GEN-LAST:event_btnStatisticActionPerformed

    private void txtEndYearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEndYearActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEndYearActionPerformed


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
    private javax.swing.JTextField txtEndYear;
    private javax.swing.JTextField txtStartYear;
    // End of variables declaration//GEN-END:variables
}

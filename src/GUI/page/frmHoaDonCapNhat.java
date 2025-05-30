// frmHoaDonCapNhat.java
package GUI.page;

import GUI.page.frmHoaDonThem;
import DAO.HoaDonDAO;
import DAO.KhachHangDAO;
import DAO.NhanVienDAO;
import DAO.ThuocDAO;
import Entity.HoaDon;
import Entity.KhachHang;
import Entity.NhanVien;
import Entity.Thuoc;
import GUI.Main;
// Bỏ comment dòng import dưới đây nếu class này tồn tại trong dự án của bạn
// import GUI.frmHoaDon;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author roxan
 */
public class frmHoaDonCapNhat extends javax.swing.JPanel {

    private int startIndex = 0;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    private final DecimalFormat currencyFormat = new DecimalFormat("#,### VND");

    /**
     * Creates new form NewJPanel
     */
    public frmHoaDonCapNhat() {
        initComponents();
        configureTable();
        loadDataToTable();
        // Thêm sự kiện cuộn bảng
        jScrollPane1.getVerticalScrollBar().addAdjustmentListener(e -> {
            JScrollBar vertical = jScrollPane1.getVerticalScrollBar();
            int max = vertical.getMaximum();
            int current = vertical.getValue();
            int visible = vertical.getVisibleAmount();

            // Kiểm tra nếu người dùng đã cuộn đến cuối bảng
            if (current + visible >= max) {
                startIndex += 10; // Tăng chỉ mục bắt đầu để tải dữ liệu tiếp theo
                loadDataToTable(); // Tải thêm dữ liệu
            }
        });

    }

    private void configureTable() {
        // Ngăn không cho phép người dùng chỉnh sửa bảng
        jTable1.setDefaultEditor(Object.class, null); // Điều này vô hiệu hóa khả năng chỉnh sửa của bất kỳ ô nào trong
                                                      // bảng.

        // Căn giữa cho tất cả các cell trong bảng
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        // Căn giữa cho từng cột
        for (int i = 0; i < jTable1.getColumnCount(); i++) {
            jTable1.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Ngăn không cho phép chọn nhiều dòng
        jTable1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
    }

    private void loadDataToTable() {
        // Tạo một worker để tải dữ liệu trong nền
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                // Lấy danh sách thuốc từ cơ sở dữ liệu
                List<HoaDon> HoaDonList = HoaDonDAO.getHoaDonBatch(startIndex, 10);  // startIndex là chỉ mục bắt đầu
                if (HoaDonList != null && !HoaDonList.isEmpty()) {
                    SwingUtilities.invokeLater(() -> {
                        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
                        if (startIndex == 0) {
                            model.setRowCount(0);  // Xóa tất cả dữ liệu hiện tại trong bảng khi tải lại từ đầu
                        }

                        // Thêm dữ liệu mới vào bảng
                        for (HoaDon hoaDon : HoaDonList) {
                            Object[] rowData = {
                                hoaDon.getId(),
                                hoaDon.getKhachHang().getHoTen(),
                                hoaDon.getKhachHang().getSdt(),
                                hoaDon.getNhanVien().getHoTen(),
                                hoaDon.getNgayLap(),
                                hoaDon.getTongTien(),
                               
                            };
                            model.addRow(rowData);  // Thêm dòng vào bảng
                        }

                        // Sắp xếp lại bảng sau khi dữ liệu được thêm vào
                        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>((DefaultTableModel) jTable1.getModel());
                        jTable1.setRowSorter(sorter);
                        sorter.setSortKeys(Arrays.asList(new RowSorter.SortKey(0, SortOrder.ASCENDING)));  // Sắp xếp theo cột đầu tiên (Mã thuốc)
                    });
                }
                return null;
            }
        };
        worker.execute();  // Chạy worker
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tablePanel = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel34 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        btnPanel = new javax.swing.JPanel();
        btnThem = new javax.swing.JButton();

        setMinimumSize(new java.awt.Dimension(829, 624));
        setPreferredSize(new java.awt.Dimension(829, 624));
        setLayout(new java.awt.BorderLayout());

        tablePanel.setMinimumSize(new java.awt.Dimension(829, 452));
        tablePanel.setLayout(new java.awt.BorderLayout());

        jPanel1.setBackground(new java.awt.Color(0, 120, 92));
        jPanel1.setMinimumSize(new java.awt.Dimension(829, 50));
        jPanel1.setName(""); // NOI18N
        jPanel1.setPreferredSize(new java.awt.Dimension(829, 50));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jLabel2.setBackground(new java.awt.Color(69, 142, 168));
        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Thông tin hóa đơn");
        jLabel2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel1.add(jLabel2, java.awt.BorderLayout.CENTER);

        tablePanel.add(jPanel1, java.awt.BorderLayout.PAGE_START);

        jPanel34.setLayout(new java.awt.BorderLayout());

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã hóa đơn", "Tên khách hàng", "SĐT khách", "Tên nhân viên", "Ngày mua", "Tổng hóa đơn"
            }
        ));
        jTable1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTable1.setShowHorizontalLines(true);
        jScrollPane1.setViewportView(jTable1);

        jPanel34.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        tablePanel.add(jPanel34, java.awt.BorderLayout.CENTER);

        add(tablePanel, java.awt.BorderLayout.CENTER);

        btnPanel.setBackground(new java.awt.Color(222, 222, 222));
        btnPanel.setPreferredSize(new java.awt.Dimension(655, 51));
        java.awt.FlowLayout flowLayout1 = new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 100, 8);
        flowLayout1.setAlignOnBaseline(true);
        btnPanel.setLayout(flowLayout1);

        btnThem.setBackground(new java.awt.Color(0, 120, 92));
        btnThem.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnThem.setForeground(new java.awt.Color(255, 255, 255));
        btnThem.setText("Thêm");
        btnThem.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnThem.setMaximumSize(new java.awt.Dimension(85, 35));
        btnThem.setMinimumSize(new java.awt.Dimension(85, 35));
        btnThem.setPreferredSize(new java.awt.Dimension(105, 35));
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });
        btnPanel.add(btnThem);

        add(btnPanel, java.awt.BorderLayout.PAGE_END);
    }// </editor-fold>//GEN-END:initComponents


    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {
        try {
        // Tạo đối tượng frmHoaDonThem
        frmHoaDonThem formThem = new frmHoaDonThem();

        // Lấy đối tượng Main (parent frame)
        Main parentFrame = (Main) SwingUtilities.getWindowAncestor(this);
        
        // Gọi phương thức replaceMainPanel để thay thế nội dung trong mainPanel
        parentFrame.replaceMainPanel(formThem);
        
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this,
                "Không thể mở form thêm hóa đơn: " + ex.getMessage(),
                "Lỗi", JOptionPane.ERROR_MESSAGE);
    }
    }// GEN-LAST:event_btnThemActionPerformed

    private void btnXemActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnXemActionPerformed
        // Kiểm tra nếu có dòng được chọn trong JTable
        int selectedRow = jTable1.getSelectedRow();
        if (selectedRow != -1) {
            String maHD = jTable1.getValueAt(selectedRow, 1).toString(); // Lấy mã hóa đơn từ cột thứ hai

            // Lấy thông tin hóa đơn
            HoaDon hoaDon = HoaDonDAO.getHoaDonByMaHD(maHD);

            if (hoaDon != null) {
                // Hiển thị thông tin chi tiết hóa đơn
                StringBuilder chiTietHD = new StringBuilder();
                chiTietHD.append("Mã hóa đơn: ").append(hoaDon.getId()).append("\n");
                chiTietHD.append("Ngày lập: ").append(dateFormat.format(hoaDon.getNgayLap())).append("\n");
                chiTietHD.append("Khách hàng: ").append(hoaDon.getKhachHang().getHoTen()).append("\n"); // Sử dụng
                                                                                                        // getHoTen()
                chiTietHD.append("SĐT: ").append(hoaDon.getKhachHang().getSdt()).append("\n"); // Sử dụng getSdt()
                chiTietHD.append("Nhân viên: ").append(hoaDon.getNhanVien().getHoTen()).append("\n\n");

                chiTietHD.append("CHI TIẾT HÓA ĐƠN\n");
                chiTietHD.append("-----------------------------------------\n");

                hoaDon.getChiTietHoaDon().forEach(ct -> {
                    chiTietHD.append(ct.getIdThuoc()).append(" - ").append(ct.getThuoc())
                            .append(" (").append(ct.getSoLuong()).append(")")
                            .append(" x ").append(currencyFormat.format(ct.getDonGia()))
                            .append(" = ").append(currencyFormat.format(ct.getThanhTien()))
                            .append("\n");
                });

                chiTietHD.append("-----------------------------------------\n");
                chiTietHD.append("TỔNG CỘNG: ").append(currencyFormat.format(hoaDon.getTongTien()));

                JOptionPane.showMessageDialog(this, chiTietHD.toString(), "Chi tiết hóa đơn",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Không tìm thấy thông tin hóa đơn!", "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn để xem chi tiết!", "Thông báo",
                    JOptionPane.WARNING_MESSAGE);
        }
    }// GEN-LAST:event_btnXemActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel btnPanel;
    private javax.swing.JButton btnThem;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel34;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JPanel tablePanel;
    // End of variables declaration//GEN-END:variables
}
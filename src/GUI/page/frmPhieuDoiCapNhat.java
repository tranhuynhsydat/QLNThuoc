// frmHoaDonCapNhat.java
package GUI.page;

import DAO.PhieuDoiDAO;
import Entity.HoaDon;
import Entity.PhieuDoi;
import Entity.KhachHang;
import Entity.NhanVien;
import GUI.Main;
// Bỏ comment dòng import dưới đây nếu class này tồn tại trong dự án của bạn
// import GUI.frmHoaDon;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author roxan
 */
public class frmPhieuDoiCapNhat extends javax.swing.JPanel {

    private int startIndex = 0;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    private final DecimalFormat currencyFormat = new DecimalFormat("#,### VND");

    /**
     * Creates new form NewJPanel
     */
    public frmPhieuDoiCapNhat() {
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
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                List<PhieuDoi> danhSachHoaDonDoi = PhieuDoiDAO.getAllHoaDonDoi(); // Lấy tất cả phiếu đổi
                System.out.println("Dữ liệu nhận được từ DB: "
                        + (danhSachHoaDonDoi != null ? danhSachHoaDonDoi.size() : 0) + " dòng");

                if (danhSachHoaDonDoi == null || danhSachHoaDonDoi.isEmpty()) {
                    System.out.println("Không có dữ liệu để tải.");
                    return null;
                }

                SwingUtilities.invokeLater(() -> {
                    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
                    model.setRowCount(0); // Xóa dữ liệu cũ trong bảng

                    int stt = 1;
                    for (PhieuDoi pd : danhSachHoaDonDoi) {
                        String maPD = pd.getId();
                        String maHD = pd.getMaHD() != null ? pd.getMaHD() : "N/A";
                        KhachHang kh = pd.getKhachHang();
                        String tenKH = (kh != null) ? kh.getHoTen() : "N/A";
                        String sdtKH = (kh != null) ? kh.getSdt() : "N/A";
                        NhanVien nv = pd.getNhanVien();
                        String tenNV = (nv != null) ? nv.getHoTen() : "N/A";
                        String ngayLapStr = (pd.getNgayLap() != null) ? dateFormat.format(pd.getNgayLap()) : "N/A";
                        String lyDo = pd.getLyDo() != null ? pd.getLyDo() : "N/A";
                        double tongTienHoaDon = pd.getTongTienHoaDon();
                        double tongTienPhieuDoi = pd.getTongTien();
                        double chenhLech = tongTienPhieuDoi - tongTienHoaDon;

                        Object[] rowData = {
                                stt++,
                                maPD,
                                maHD,
                                tenKH,
                                sdtKH,
                                tenNV,
                                ngayLapStr,
                                lyDo,
                                String.format("%,.0f VND", tongTienHoaDon),
                                String.format("%,.0f VND", tongTienPhieuDoi),
                                (chenhLech > 0) ? "Khách trả thêm: " + String.format("%,.0f VND", chenhLech)
                                        : (chenhLech < 0)
                                                ? "Hoàn lại khách: " + String.format("%,.0f VND", Math.abs(chenhLech))
                                                : "Không chênh lệch"
                        };
                        model.addRow(rowData);
                    }

                    model.fireTableDataChanged();
                    jTable1.revalidate();
                    jTable1.repaint();
                });

                return null;
            }
        };
        worker.execute();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
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
        jLabel2.setText("Thông tin phiếu đổi");
        jLabel2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel1.add(jLabel2, java.awt.BorderLayout.CENTER);

        tablePanel.add(jPanel1, java.awt.BorderLayout.PAGE_START);

        jPanel34.setLayout(new java.awt.BorderLayout());

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {
                        { null, null, null, null, null, null, null, null, null, null, null },
                        { null, null, null, null, null, null, null, null, null, null, null },
                        { null, null, null, null, null, null, null, null, null, null, null },
                        { null, null, null, null, null, null, null, null, null, null, null }
                },
                new String[] {
                        "STT", "Mã phiếu đổi", "Mã hóa đơn", "Tên khách hàng", "SĐT", "Tên nhân viên", "Ngày mua",
                        "Lý do", "Tổng hóa đơn cũ", "Tổng phiếu đổi", "Chênh lệch"
                }));
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
            frmPhieuDoiThem formThem = new frmPhieuDoiThem();

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
        // // Kiểm tra nếu có dòng được chọn trong JTable
        // int selectedRow = jTable1.getSelectedRow();
        // if (selectedRow != -1) {
        // String maPD = jTable1.getValueAt(selectedRow, 1).toString(); // Lấy mã hóa
        // đơn từ cột thứ hai
        //
        // // Lấy thông tin hóa đơn
        // PhieuDoi hoaDonDoi = PhieuDoiDAO.getHoaDonByMaPD(maPD);
        //
        // if (hoaDonDoi != null) {
        // // Hiển thị thông tin chi tiết hóa đơn
        // StringBuilder chiTietPD = new StringBuilder();
        // chiTietPD.append("Mã hóa đơn đổi: ").append(hoaDonDoi.getId()).append("\n");
        // chiTietPD.append("Ngày lập:
        // ").append(dateFormat.format(hoaDonDoi.getNgayLap())).append("\n");
        // chiTietPD.append("Khách hàng:
        // ").append(hoaDonDoi.getKhachHang().getHoTen()).append("\n"); // Sử dụng
        // // getHoTen()
        // chiTietPD.append("SĐT:
        // ").append(hoaDonDoi.getKhachHang().getSdt()).append("\n"); // Sử dụng
        // getSdt()
        // chiTietPD.append("Nhân viên:
        // ").append(hoaDonDoi.getNhanVien().getHoTen()).append("\n\n");
        //
        // chiTietPD.append("CHI TIẾT HÓA ĐƠN ĐỔI\n");
        // chiTietPD.append("-----------------------------------------\n");
        //
        // hoaDonDoi.getChiTietHoaDonDoi().forEach(ct -> {
        // chiTietPD.append(ct.getIdThuoc()).append(" - ").append(ct.getThuoc())
        // .append(" (").append(ct.getSoLuong()).append(")")
        // .append(" x ").append(currencyFormat.format(ct.getDonGia()))
        // .append(" = ").append(currencyFormat.format(ct.getThanhTien()))
        // .append("\n");
        // });
        //
        // chiTietPD.append("-----------------------------------------\n");
        // chiTietPD.append("TỔNG CỘNG:
        // ").append(currencyFormat.format(hoaDonDoi.getTongTien()));
        //
        // JOptionPane.showMessageDialog(this, chiTietPD.toString(), "Chi tiết hóa đơn",
        // JOptionPane.INFORMATION_MESSAGE);
        // } else {
        // JOptionPane.showMessageDialog(this, "Không tìm thấy thông tin hóa đơn!",
        // "Lỗi",
        // JOptionPane.ERROR_MESSAGE);
        // }
        // } else {
        // JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn để xem chi tiết!",
        // "Thông báo",
        // JOptionPane.WARNING_MESSAGE);
        // }
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
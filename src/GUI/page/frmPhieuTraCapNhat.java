package GUI.page;

import DAO.HoaDonDAO;
import DAO.PhieuTraDAO;
import Entity.HoaDon;
import Entity.KhachHang;
import Entity.NhanVien;
import Entity.PhieuTra;
import GUI.Main;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class frmPhieuTraCapNhat extends javax.swing.JPanel {

    private int startIndex = 0;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    private final DecimalFormat moneyFormat = new DecimalFormat("#,##0 VND");

    public frmPhieuTraCapNhat() {
        initComponents();
        configureTable();
        loadDataToTable();

        jScrollPane1.getVerticalScrollBar().addAdjustmentListener(e -> {
            JScrollBar vertical = jScrollPane1.getVerticalScrollBar();
            if (!e.getValueIsAdjusting() && vertical.getValue() + vertical.getVisibleAmount() >= vertical.getMaximum()) {
                startIndex += 10;
                loadDataToTable();
            }
        });
    }

    private void configureTable() {
        jTable1.setDefaultEditor(Object.class, null);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < jTable1.getColumnCount(); i++) {
            jTable1.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        jTable1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jTable1.setAutoCreateRowSorter(true);
    }

    private void loadDataToTable() {
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                List<PhieuTra> danhSachHoaDonDoi = PhieuTraDAO.getAllPhieuTra(); // Lấy tất cả phiếu đổi
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
                    for (PhieuTra pd : danhSachHoaDonDoi) {
                        String maPD = pd.getMaPT();
                        String maHD = pd.getMaHD() != null ? pd.getMaHD() : "N/A";
                        KhachHang kh = pd.getKhachHang();
                        String tenKH = (kh != null) ? kh.getHoTen() : "N/A";
                        String sdtKH = (kh != null) ? kh.getSdt() : "N/A";
                        NhanVien nv = pd.getNhanVien();
                        String tenNV = (nv != null) ? nv.getHoTen() : "N/A";
                        String ngayLapStr = (pd.getNgayLap() != null) ? dateFormat.format(pd.getNgayLap()) : "N/A";
                        String lyDo = pd.getLyDo() != null ? pd.getLyDo() : "N/A";
                        double tongTienHoaDon = pd.getTongTienHoaDon();
                        double tongTienPhieuDoi = 0;
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

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            frmPhieuTraThem formThem = new frmPhieuTraThem();
            Main parentFrame = (Main) SwingUtilities.getWindowAncestor(this);
            parentFrame.replaceMainPanel(formThem);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Không thể mở form thêm phiếu trả: " + ex.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {
        tablePanel = new JPanel();
        jPanel1 = new JPanel();
        jLabel2 = new JLabel();
        jScrollPane1 = new JScrollPane();
        jTable1 = new JTable();
        btnPanel = new JPanel();
        btnThem = new JButton();

        setMinimumSize(new java.awt.Dimension(829, 624));
        setPreferredSize(new java.awt.Dimension(829, 624));
        setLayout(new java.awt.BorderLayout());

        tablePanel.setMinimumSize(new java.awt.Dimension(829, 452));
        tablePanel.setLayout(new java.awt.BorderLayout());

        jPanel1.setBackground(new java.awt.Color(0, 120, 92));
        jPanel1.setMinimumSize(new java.awt.Dimension(829, 50));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 24));
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Thông tin phiếu trả");
        jPanel1.add(jLabel2, java.awt.BorderLayout.CENTER);
        tablePanel.add(jPanel1, java.awt.BorderLayout.PAGE_START);

        jTable1.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{"STT", "Mã phiếu trả", "Mã hóa đơn", "Tên KH", "SĐT", "Tên NV", "Ngày trả", "Lý do", "Tổng hóa đơn"}
        ));
        jTable1.setShowHorizontalLines(true);
        jScrollPane1.setViewportView(jTable1);
        tablePanel.add(jScrollPane1, java.awt.BorderLayout.CENTER);
        add(tablePanel, java.awt.BorderLayout.CENTER);

        btnPanel.setBackground(new java.awt.Color(222, 222, 222));
        btnPanel.setPreferredSize(new java.awt.Dimension(655, 51));
        java.awt.FlowLayout flowLayout1 = new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 100, 8);
        flowLayout1.setAlignOnBaseline(true);
        btnPanel.setLayout(flowLayout1);

        btnThem.setBackground(new java.awt.Color(0, 120, 92));
        btnThem.setFont(new java.awt.Font("Segoe UI", 1, 14));
        btnThem.setForeground(new java.awt.Color(255, 255, 255));
        btnThem.setText("Thêm");
        btnThem.setPreferredSize(new java.awt.Dimension(105, 35));
        btnThem.addActionListener(this::btnThemActionPerformed);
        btnPanel.add(btnThem);

        add(btnPanel, java.awt.BorderLayout.PAGE_END);
    }
    // </editor-fold>

    private JPanel btnPanel;
    private JButton btnThem;
    private JLabel jLabel2;
    private JPanel jPanel1;
    private JScrollPane jScrollPane1;
    private JTable jTable1;
    private JPanel tablePanel;
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI;

import Entity.TaiKhoan;
import GUI.page.frmNhaCungCapCapNhat;
import GUI.page.frmSearchNhaCungCap;
import GUI.page.frmNhanVienCapNhat;
import GUI.page.frmKhachHangCapNhat;
import GUI.page.frmSearchNhanVien;
import GUI.page.frmThuocCapNhat;
import Swing.RoundedMenuItem;
import Swing.RoundedPopupMenu;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JSeparator;

/**
 *
 * @author roxan
 */
public class Main extends javax.swing.JFrame {

    public TaiKhoan tk;
    private ResourceBundle messages;
    private RoundedPopupMenu popupMenuThuoc;
    private RoundedPopupMenu popupMenuNCC;
    private RoundedPopupMenu popupMenuKhachHang;
    private RoundedPopupMenu popupMenuNhanVien;
    private RoundedPopupMenu popupMenuTaiKhoan;

    public Main() {
        initComponents();
        setLocationRelativeTo(null);
        loadLanguage("vi");
        addActionListeners(Arrays.asList(btnThongKe, btnHoaDon, btnKhachHang, btnNhaCungCap, btnNhanVien, btnPhieuNhap, btnTaiKhoan, btnThuoc, btnDangXuat, btnPhieuDoiTra));
        jScrollPane1.getVerticalScrollBar().setPreferredSize(new java.awt.Dimension(3, 0));
        setExtendedState(JFrame.MAXIMIZED_BOTH);
// Menu thuốc
        popupMenuThuoc = new RoundedPopupMenu();
        RoundedMenuItem itemThuoc1 = new RoundedMenuItem("Cập nhật");
        popupMenuThuoc.add(itemThuoc1);
        popupMenuThuoc.add(new JSeparator());

        RoundedMenuItem itemThuoc2 = new RoundedMenuItem("Tìm kiếm");
        popupMenuThuoc.add(itemThuoc2);
        popupMenuThuoc.add(new JSeparator());

        btnThuoc.addActionListener(e -> popupMenuThuoc.show(btnThuoc, btnThuoc.getWidth(), 2));
        setFontForMenuItems(new RoundedMenuItem[]{itemThuoc1, itemThuoc2});

// Menu nhà cung cấp
        popupMenuNCC = new RoundedPopupMenu();
        RoundedMenuItem itemNCC1 = new RoundedMenuItem("Cập nhật");
        popupMenuNCC.add(itemNCC1);
        popupMenuNCC.add(new JSeparator());

        RoundedMenuItem itemNCC2 = new RoundedMenuItem("Tìm kiếm");
        popupMenuNCC.add(itemNCC2);
        popupMenuNCC.add(new JSeparator());

        btnNhaCungCap.addActionListener(e -> popupMenuNCC.show(btnNhaCungCap, btnNhaCungCap.getWidth(), 2));
        setFontForMenuItems(new RoundedMenuItem[]{itemNCC1, itemNCC2});

// Menu khách hàng
        popupMenuKhachHang = new RoundedPopupMenu();
        RoundedMenuItem itemKH1 = new RoundedMenuItem("Cập nhật");
        popupMenuKhachHang.add(itemKH1);
        popupMenuKhachHang.add(new JSeparator());

        RoundedMenuItem itemKH2 = new RoundedMenuItem("Tìm kiếm");
        popupMenuKhachHang.add(itemKH2);
        popupMenuKhachHang.add(new JSeparator());

        btnKhachHang.addActionListener(e -> popupMenuKhachHang.show(btnKhachHang, btnKhachHang.getWidth(), 2));
        setFontForMenuItems(new RoundedMenuItem[]{itemKH1, itemKH2});

// Menu nhân viên
        popupMenuNhanVien = new RoundedPopupMenu();
        RoundedMenuItem itemNV1 = new RoundedMenuItem("Cập nhật");
        popupMenuNhanVien.add(itemNV1);
        popupMenuNhanVien.add(new JSeparator());

        RoundedMenuItem itemNV2 = new RoundedMenuItem("Tìm kiếm");
        popupMenuNhanVien.add(itemNV2);
        popupMenuNhanVien.add(new JSeparator());

        btnNhanVien.addActionListener(e -> popupMenuNhanVien.show(btnNhanVien, btnNhanVien.getWidth(), 2));
        setFontForMenuItems(new RoundedMenuItem[]{itemNV1, itemNV2});

// Menu tài khoản
        popupMenuTaiKhoan = new RoundedPopupMenu();
        RoundedMenuItem itemTK1 = new RoundedMenuItem("Cập nhật");
        popupMenuTaiKhoan.add(itemTK1);
        popupMenuTaiKhoan.add(new JSeparator());

        RoundedMenuItem itemTK2 = new RoundedMenuItem("Tìm kiếm");
        popupMenuTaiKhoan.add(itemTK2);
        popupMenuTaiKhoan.add(new JSeparator());

        btnTaiKhoan.addActionListener(e -> popupMenuTaiKhoan.show(btnTaiKhoan, btnTaiKhoan.getWidth(), 2));
        setFontForMenuItems(new RoundedMenuItem[]{itemTK1, itemTK2});

        itemNV1.addActionListener(e -> {
            frmNhanVienCapNhat nv = new frmNhanVienCapNhat();
            // Xóa tất cả các phần cũ 
            mainPanel.removeAll();
            // Đặt layout cho mainPanel
            mainPanel.setLayout(new java.awt.BorderLayout());

            // Thêm NhaCungCapCapNhat vào mainPanel
            mainPanel.add(nv, java.awt.BorderLayout.CENTER);

            // Cập nhật lại giao diện
            mainPanel.revalidate();
            mainPanel.repaint();
        });
        
        itemNV2.addActionListener(e -> {
            frmSearchNhanVien nv = new frmSearchNhanVien();
            // Xóa tất cả các phần cũ 
            mainPanel.removeAll();
            // Đặt layout cho mainPanel
            mainPanel.setLayout(new java.awt.BorderLayout());

            // Thêm NhaCungCapCapNhat vào mainPanel
            mainPanel.add(nv, java.awt.BorderLayout.CENTER);

            // Cập nhật lại giao diện
            mainPanel.revalidate();
            mainPanel.repaint();
        });
//sự kiện tìm kiếm nhân viên
        itemNCC1.addActionListener(e -> {
            frmNhaCungCapCapNhat ncc = new frmNhaCungCapCapNhat();
            // Xóa tất cả các phần cũ 
            mainPanel.removeAll();
            // Đặt layout cho mainPanel
            mainPanel.setLayout(new java.awt.BorderLayout());

            // Thêm NhaCungCapCapNhat vào mainPanel
            mainPanel.add(ncc, java.awt.BorderLayout.CENTER);

            // Cập nhật lại giao diện
            mainPanel.revalidate();
            mainPanel.repaint();
        });
        itemNCC2.addActionListener(e -> {
            frmSearchNhaCungCap sncc = new frmSearchNhaCungCap();
            // Xóa tất cả các phần cũ 
            mainPanel.removeAll();
            // Đặt layout cho mainPanel
            mainPanel.setLayout(new java.awt.BorderLayout());

            // Thêm NhaCungCapCapNhat vào mainPanel
            mainPanel.add(sncc, java.awt.BorderLayout.CENTER);

            // Cập nhật lại giao diện
            mainPanel.revalidate();
            mainPanel.repaint();
        });
        itemKH1.addActionListener(e -> {
            frmKhachHangCapNhat kh = new frmKhachHangCapNhat();
            // Xóa tất cả các phần cũ 
            mainPanel.removeAll();
            // Đặt layout cho mainPanel
            mainPanel.setLayout(new java.awt.BorderLayout());

            // Thêm NhaCungCapCapNhat vào mainPanel
            mainPanel.add(kh, java.awt.BorderLayout.CENTER);

            // Cập nhật lại giao diện
            mainPanel.revalidate();
            mainPanel.repaint();
        });
        itemThuoc1.addActionListener(e -> {
            frmThuocCapNhat thuoc = new frmThuocCapNhat();
            // Xóa tất cả các phần cũ 
            mainPanel.removeAll();
            // Đặt layout cho mainPanel
            mainPanel.setLayout(new java.awt.BorderLayout());

            // Thêm NhaCungCapCapNhat vào mainPanel
            mainPanel.add(thuoc, java.awt.BorderLayout.CENTER);

            // Cập nhật lại giao diện
            mainPanel.revalidate();
            mainPanel.repaint();
        });
    }

    private void setFontForMenuItems(RoundedMenuItem[] items) {
        Font font = new Font("Segoe UI", Font.BOLD, 12); // Thiết lập font ở đây
        for (RoundedMenuItem item : items) {
            item.setFont(font);
        }
    }

    public Main(TaiKhoan tk) {
        this.tk = tk;
        initComponents();
        setLocationRelativeTo(null);
        addActionListeners(Arrays.asList(btnThongKe, btnHoaDon, btnKhachHang, btnNhaCungCap, btnNhanVien, btnPhieuNhap, btnTaiKhoan, btnThuoc, btnDangXuat));
    }

    private void changeButtonColor(ActionEvent e) {

        JButton sourceButton = (JButton) e.getSource();
        Component[] components = sourceButton.getParent().getComponents();

        // Đặt tất cả nút về màu mặc định
        for (Component component : components) {
            if (component instanceof JButton) {
                component.setBackground(Color.WHITE);
            }
        }

        // Đổi màu nút được chọn
        sourceButton.setBackground(new Color(0, 155, 118));
    }

    private void addActionListeners(List<JButton> buttons) {
        for (JButton button : buttons) {
            button.addActionListener(this::changeButtonColor);
        }
    }

    private void checkRole(String role) {
        if (role.equals("nvbh")) {
            btnNhanVien.setEnabled(false);
            btnTaiKhoan.setEnabled(false);
        }

        if (role.equals("nvql")) {
            btnThongKe.setEnabled(false);
            btnPhieuNhap.setEnabled(false);
            btnNhaCungCap.setEnabled(false);
            btnThuoc.setEnabled(false);
            btnHoaDon.setEnabled(false);
            btnPhieuDoiTra.setEnabled(false);
            btnKhachHang.setEnabled(false);
        }
    }

    private void loadLanguage(String language) {
        Locale locale = new Locale(language);
        String baseName = "Style." + (language.equals("vi") ? "VN" : "EN");
        messages = ResourceBundle.getBundle(baseName, locale);
        updateLanguage();
    }

    private void updateLanguage() {
        btnThongKe.setText(messages.getString("btnThongKe"));
        btnHoaDon.setText(messages.getString("btnHoaDon"));
        btnKhachHang.setText(messages.getString("btnKhachHang"));
        btnNhaCungCap.setText(messages.getString("btnNhaCungCap"));
        btnNhanVien.setText(messages.getString("btnNhanVien"));
        btnPhieuNhap.setText(messages.getString("btnPhieuNhap"));
        btnTaiKhoan.setText(messages.getString("btnTaiKhoan"));
        btnThuoc.setText(messages.getString("btnThuoc"));
        btnDangXuat.setText(messages.getString("btnDangXuat"));
        btnPhieuDoiTra.setText(messages.getString("btnPhieuDoiTra"));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        roundedPopupMenu1 = new Swing.RoundedPopupMenu();
        roundedPopupMenu2 = new Swing.RoundedPopupMenu();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        roundPanel1 = new Swing.RoundPanel();
        roundPanel2 = new Swing.RoundPanel();
        roundPanel4 = new Swing.RoundPanel();
        lblAvatar = new javax.swing.JLabel();
        roundPanel5 = new Swing.RoundPanel();
        lblName = new javax.swing.JLabel();
        lblRole = new javax.swing.JLabel();
        roundPanel3 = new Swing.RoundPanel();
        roundPanel7 = new Swing.RoundPanel();
        roundPanel6 = new Swing.RoundPanel();
        btnDangXuat = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        roundPanel8 = new Swing.RoundPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        roundPanel9 = new Swing.RoundPanel();
        btnThongKe = new javax.swing.JButton();
        btnPhieuNhap = new javax.swing.JButton();
        btnNhaCungCap = new javax.swing.JButton();
        btnThuoc = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel3 = new javax.swing.JLabel();
        btnHoaDon = new javax.swing.JButton();
        btnPhieuDoiTra = new javax.swing.JButton();
        btnKhachHang = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel4 = new javax.swing.JLabel();
        btnNhanVien = new javax.swing.JButton();
        btnTaiKhoan = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        label1 = new java.awt.Label();
        jComboBox1 = new javax.swing.JComboBox<>();
        mainPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(51, 51, 51));
        jPanel1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel2.setBackground(new java.awt.Color(81, 219, 185));
        jPanel2.setPreferredSize(new java.awt.Dimension(230, 454));

        roundPanel1.setBackground(new java.awt.Color(81, 219, 185));
        roundPanel1.setAlignmentX(0.0F);
        roundPanel1.setAlignmentY(0.0F);

        roundPanel2.setBackground(new java.awt.Color(255, 255, 255));
        roundPanel2.setPreferredSize(new java.awt.Dimension(200, 100));
        roundPanel2.setLayout(new java.awt.BorderLayout());

        roundPanel4.setBackground(new java.awt.Color(255, 255, 255));
        roundPanel4.setPreferredSize(new java.awt.Dimension(60, 100));
        roundPanel4.setLayout(new java.awt.GridBagLayout());

        lblAvatar.setIcon(new FlatSVGIcon("./icon/man.svg"));
        lblAvatar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblAvatar.setName(""); // NOI18N
        roundPanel4.add(lblAvatar, new java.awt.GridBagConstraints());

        roundPanel2.add(roundPanel4, java.awt.BorderLayout.LINE_START);

        roundPanel5.setBackground(new java.awt.Color(255, 255, 255));

        lblName.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblName.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblName.setText("ADMIN");
        lblName.setAlignmentY(0.0F);

        lblRole.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblRole.setForeground(new java.awt.Color(102, 102, 102));
        lblRole.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblRole.setText("admin");
        lblRole.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout roundPanel5Layout = new javax.swing.GroupLayout(roundPanel5);
        roundPanel5.setLayout(roundPanel5Layout);
        roundPanel5Layout.setHorizontalGroup(
            roundPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblRole, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(roundPanel5Layout.createSequentialGroup()
                .addComponent(lblName, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        roundPanel5Layout.setVerticalGroup(
            roundPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel5Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(lblName)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblRole, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(29, 29, 29))
        );

        roundPanel2.add(roundPanel5, java.awt.BorderLayout.CENTER);

        roundPanel3.setLayout(new java.awt.BorderLayout());

        roundPanel7.setPreferredSize(new java.awt.Dimension(188, 50));
        roundPanel7.setLayout(new java.awt.BorderLayout());

        roundPanel6.setBackground(new java.awt.Color(204, 204, 204));
        roundPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnDangXuat.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnDangXuat.setIcon(new FlatSVGIcon("./icon/logout.svg"));
        btnDangXuat.setText("ĐĂNG XUẤT");
        btnDangXuat.setBorderPainted(false);
        btnDangXuat.setMaximumSize(new java.awt.Dimension(208, 42));
        btnDangXuat.setMinimumSize(new java.awt.Dimension(208, 42));
        btnDangXuat.setPreferredSize(new java.awt.Dimension(208, 42));
        roundPanel6.add(btnDangXuat, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 200, 30));

        roundPanel7.add(roundPanel6, java.awt.BorderLayout.CENTER);
        roundPanel7.add(jSeparator1, java.awt.BorderLayout.PAGE_START);

        roundPanel3.add(roundPanel7, java.awt.BorderLayout.PAGE_END);

        roundPanel8.setBackground(new java.awt.Color(204, 204, 204));

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane1.setBorder(null);
        jScrollPane1.setForeground(new java.awt.Color(255, 255, 255));
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setAlignmentX(0.0F);
        jScrollPane1.setAlignmentY(0.0F);
        jScrollPane1.setBorder(new javax.swing.border.EmptyBorder(5, 5, 5, 5) {
            @Override
            public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.drawRoundRect(x, y, width - 1, height - 1, 15, 15);
            }
        });

        roundPanel9.setBackground(new java.awt.Color(204, 204, 204));
        roundPanel9.setAlignmentY(0.0F);
        roundPanel9.setLayout(new javax.swing.BoxLayout(roundPanel9, javax.swing.BoxLayout.Y_AXIS));

        btnThongKe.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnThongKe.setText("Thống Kê");
        btnThongKe.setAlignmentY(0.0F);
        btnThongKe.setBorderPainted(false);
        btnThongKe.setHideActionText(true);
        btnThongKe.setMaximumSize(new java.awt.Dimension(208, 42));
        btnThongKe.setMinimumSize(new java.awt.Dimension(208, 42));
        btnThongKe.setPreferredSize(new java.awt.Dimension(208, 42));
        roundPanel9.add(btnThongKe);

        btnPhieuNhap.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnPhieuNhap.setText("Phiếu Nhập");
        btnPhieuNhap.setAlignmentY(0.0F);
        btnPhieuNhap.setBorderPainted(false);
        btnPhieuNhap.setMaximumSize(new java.awt.Dimension(208, 42));
        btnPhieuNhap.setMinimumSize(new java.awt.Dimension(208, 42));
        btnPhieuNhap.setPreferredSize(new java.awt.Dimension(208, 42));
        roundPanel9.add(btnPhieuNhap);

        btnNhaCungCap.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnNhaCungCap.setText("Nhà Cung Cấp");
        btnNhaCungCap.setAlignmentY(0.0F);
        btnNhaCungCap.setBorderPainted(false);
        btnNhaCungCap.setMaximumSize(new java.awt.Dimension(208, 42));
        btnNhaCungCap.setMinimumSize(new java.awt.Dimension(208, 42));
        btnNhaCungCap.setPreferredSize(new java.awt.Dimension(208, 42));
        roundPanel9.add(btnNhaCungCap);

        btnThuoc.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnThuoc.setText("Thuốc");
        btnThuoc.setAlignmentY(0.0F);
        btnThuoc.setBorderPainted(false);
        btnThuoc.setMaximumSize(new java.awt.Dimension(208, 42));
        btnThuoc.setMinimumSize(new java.awt.Dimension(208, 42));
        btnThuoc.setPreferredSize(new java.awt.Dimension(208, 42));
        btnThuoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThuocActionPerformed(evt);
            }
        });
        roundPanel9.add(btnThuoc);

        jLabel1.setMaximumSize(new java.awt.Dimension(200, 3));
        jLabel1.setMinimumSize(new java.awt.Dimension(200, 3));
        jLabel1.setPreferredSize(new java.awt.Dimension(200, 3));
        roundPanel9.add(jLabel1);

        jSeparator2.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator2.setForeground(new java.awt.Color(153, 153, 153));
        jSeparator2.setAlignmentX(0.0F);
        jSeparator2.setAlignmentY(0.0F);
        jSeparator2.setMaximumSize(new java.awt.Dimension(200, 3));
        jSeparator2.setMinimumSize(new java.awt.Dimension(200, 3));
        jSeparator2.setPreferredSize(new java.awt.Dimension(200, 3));
        roundPanel9.add(jSeparator2);

        jLabel3.setMaximumSize(new java.awt.Dimension(200, 3));
        jLabel3.setMinimumSize(new java.awt.Dimension(200, 3));
        jLabel3.setPreferredSize(new java.awt.Dimension(200, 3));
        roundPanel9.add(jLabel3);

        btnHoaDon.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnHoaDon.setText("Hoá Đơn");
        btnHoaDon.setAlignmentY(0.0F);
        btnHoaDon.setBorderPainted(false);
        btnHoaDon.setMaximumSize(new java.awt.Dimension(208, 42));
        btnHoaDon.setMinimumSize(new java.awt.Dimension(208, 42));
        btnHoaDon.setPreferredSize(new java.awt.Dimension(208, 42));
        btnHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHoaDonActionPerformed(evt);
            }
        });
        roundPanel9.add(btnHoaDon);

        btnPhieuDoiTra.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnPhieuDoiTra.setText("Phiếu đổi trả");
        btnPhieuDoiTra.setAlignmentY(0.0F);
        btnPhieuDoiTra.setBorderPainted(false);
        btnPhieuDoiTra.setMaximumSize(new java.awt.Dimension(208, 42));
        btnPhieuDoiTra.setMinimumSize(new java.awt.Dimension(208, 42));
        btnPhieuDoiTra.setPreferredSize(new java.awt.Dimension(208, 42));
        roundPanel9.add(btnPhieuDoiTra);

        btnKhachHang.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnKhachHang.setText("Khách Hàng");
        btnKhachHang.setAlignmentY(0.0F);
        btnKhachHang.setBorderPainted(false);
        btnKhachHang.setMaximumSize(new java.awt.Dimension(208, 42));
        btnKhachHang.setMinimumSize(new java.awt.Dimension(208, 42));
        btnKhachHang.setPreferredSize(new java.awt.Dimension(208, 42));
        roundPanel9.add(btnKhachHang);

        jLabel2.setMaximumSize(new java.awt.Dimension(200, 3));
        jLabel2.setMinimumSize(new java.awt.Dimension(200, 3));
        jLabel2.setPreferredSize(new java.awt.Dimension(200, 3));
        roundPanel9.add(jLabel2);

        jSeparator3.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        jSeparator3.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator3.setForeground(new java.awt.Color(153, 153, 153));
        jSeparator3.setAlignmentX(0.0F);
        jSeparator3.setAlignmentY(0.0F);
        jSeparator3.setMaximumSize(new java.awt.Dimension(200, 3));
        jSeparator3.setMinimumSize(new java.awt.Dimension(200, 3));
        jSeparator3.setPreferredSize(new java.awt.Dimension(200, 3));
        roundPanel9.add(jSeparator3);

        jLabel4.setMaximumSize(new java.awt.Dimension(200, 3));
        jLabel4.setMinimumSize(new java.awt.Dimension(200, 3));
        jLabel4.setPreferredSize(new java.awt.Dimension(200, 3));
        roundPanel9.add(jLabel4);

        btnNhanVien.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnNhanVien.setText("Nhân Viên");
        btnNhanVien.setBorderPainted(false);
        btnNhanVien.setMaximumSize(new java.awt.Dimension(208, 42));
        btnNhanVien.setMinimumSize(new java.awt.Dimension(208, 42));
        btnNhanVien.setPreferredSize(new java.awt.Dimension(208, 42));
        roundPanel9.add(btnNhanVien);

        btnTaiKhoan.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnTaiKhoan.setText("Tài Khoản");
        btnTaiKhoan.setAlignmentY(0.0F);
        btnTaiKhoan.setBorderPainted(false);
        btnTaiKhoan.setMaximumSize(new java.awt.Dimension(208, 42));
        btnTaiKhoan.setMinimumSize(new java.awt.Dimension(208, 42));
        btnTaiKhoan.setPreferredSize(new java.awt.Dimension(208, 42));
        roundPanel9.add(btnTaiKhoan);

        jLabel5.setAlignmentY(0.0F);
        jLabel5.setMaximumSize(new java.awt.Dimension(200, 50));
        jLabel5.setMinimumSize(new java.awt.Dimension(200, 50));
        jLabel5.setPreferredSize(new java.awt.Dimension(200, 40));
        roundPanel9.add(jLabel5);

        jPanel3.setBackground(new java.awt.Color(204, 204, 204));
        jPanel3.setAlignmentX(0.0F);
        jPanel3.setAlignmentY(0.0F);
        jPanel3.setMaximumSize(new java.awt.Dimension(200, 25));
        jPanel3.setMinimumSize(new java.awt.Dimension(200, 25));
        jPanel3.setPreferredSize(new java.awt.Dimension(200, 25));
        jPanel3.setLayout(new java.awt.BorderLayout());

        label1.setAlignment(java.awt.Label.CENTER);
        label1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        label1.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        label1.setName(""); // NOI18N
        label1.setPreferredSize(new java.awt.Dimension(100, 20));
        label1.setText("Select language:");
        jPanel3.add(label1, java.awt.BorderLayout.LINE_START);

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Vietnamese", "English" }));
        jComboBox1.setMinimumSize(new java.awt.Dimension(75, 22));
        jComboBox1.setPreferredSize(new java.awt.Dimension(75, 22));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });
        jPanel3.add(jComboBox1, java.awt.BorderLayout.CENTER);

        roundPanel9.add(jPanel3);

        jScrollPane1.setViewportView(roundPanel9);

        javax.swing.GroupLayout roundPanel8Layout = new javax.swing.GroupLayout(roundPanel8);
        roundPanel8.setLayout(roundPanel8Layout);
        roundPanel8Layout.setHorizontalGroup(
            roundPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE)
        );
        roundPanel8Layout.setVerticalGroup(
            roundPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 477, Short.MAX_VALUE)
        );

        roundPanel3.add(roundPanel8, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout roundPanel1Layout = new javax.swing.GroupLayout(roundPanel1);
        roundPanel1.setLayout(roundPanel1Layout);
        roundPanel1Layout.setHorizontalGroup(
            roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(roundPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(roundPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        roundPanel1Layout.setVerticalGroup(
            roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel1Layout.createSequentialGroup()
                .addComponent(roundPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(roundPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(roundPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(roundPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel1.add(jPanel2, java.awt.BorderLayout.LINE_START);

        mainPanel.setBackground(new java.awt.Color(0, 51, 51));

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 829, Short.MAX_VALUE)
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 624, Short.MAX_VALUE)
        );

        jPanel1.add(mainPanel, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        String selectedLanguage = (String) jComboBox1.getSelectedItem();
        if ("Vietnamese".equals(selectedLanguage)) {
            loadLanguage("vi");
        } else {
            loadLanguage("en");
        }
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void btnThuocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThuocActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnThuocActionPerformed

    private void btnHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHoaDonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnHoaDonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDangXuat;
    private javax.swing.JButton btnHoaDon;
    private javax.swing.JButton btnKhachHang;
    private javax.swing.JButton btnNhaCungCap;
    private javax.swing.JButton btnNhanVien;
    private javax.swing.JButton btnPhieuDoiTra;
    private javax.swing.JButton btnPhieuNhap;
    private javax.swing.JButton btnTaiKhoan;
    private javax.swing.JButton btnThongKe;
    private javax.swing.JButton btnThuoc;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private java.awt.Label label1;
    private javax.swing.JLabel lblAvatar;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblRole;
    private javax.swing.JPanel mainPanel;
    private Swing.RoundPanel roundPanel1;
    private Swing.RoundPanel roundPanel2;
    private Swing.RoundPanel roundPanel3;
    private Swing.RoundPanel roundPanel4;
    private Swing.RoundPanel roundPanel5;
    private Swing.RoundPanel roundPanel6;
    private Swing.RoundPanel roundPanel7;
    private Swing.RoundPanel roundPanel8;
    private Swing.RoundPanel roundPanel9;
    private Swing.RoundedPopupMenu roundedPopupMenu1;
    private Swing.RoundedPopupMenu roundedPopupMenu2;
    // End of variables declaration//GEN-END:variables
}

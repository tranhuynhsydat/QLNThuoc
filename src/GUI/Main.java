/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI;

import Entity.TaiKhoan;
import GUI.form.formDoiMkTK;
import GUI.page.ThongKe.frmThongKe;
import GUI.page.frmHoaDonCapNhat;
import GUI.page.frmSearchHoaDon;
import GUI.page.frmPhieuDoiCapNhat;
import GUI.page.frmPhieuTraCapNhat;
import GUI.page.frmSearchPhieuDoi;
import GUI.page.frmSearchPhieuTra;
import GUI.page.frmPhieuNhapCapNhat;
import GUI.page.frmSearchPhieuNhap;
import GUI.page.frmNhaCungCapCapNhat;
import GUI.page.frmSearchNhaCungCap;
import GUI.page.frmNhanVienCapNhat;
import GUI.page.frmKhachHangCapNhat;
import GUI.page.frmSearchKhachHang;
import GUI.page.frmSearchNhanVien;
import GUI.page.frmSearchTaiKhoan;
import GUI.page.frmSearchThuoc;
import GUI.page.frmTaiKhoanCapNhat;
import GUI.page.frmThuocCapNhat;
import Swing.RoundedMenuItem;
import Swing.RoundedPopupMenu;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingUtilities;

/**
 *
 * @author roxan
 */
public class Main extends javax.swing.JFrame {

    private TaiKhoan taiKhoan;
    private ResourceBundle messages;
    private RoundedPopupMenu popupMenuThuoc;
    private RoundedPopupMenu popupMenuNCC;
    private RoundedPopupMenu popupMenuKhachHang;
    private RoundedPopupMenu popupMenuNhanVien;
    private RoundedPopupMenu popupMenuTaiKhoan;
    private RoundedPopupMenu popupMenuHoaDon;
    private RoundedPopupMenu popupMenuHoaDonDoiTra;
    private RoundedPopupMenu popupMenuPhieuNhap;

    public Main(TaiKhoan tk) {
        this.taiKhoan = tk;
        setTitle("Phần mềm quản lý hiệu thuốc tây Ba Tri - Bến Tre");

        initComponents();
        setLocationRelativeTo(null);
        addActionListeners(Arrays.asList(btnThongKe, btnHoaDon, btnKhachHang, btnNhaCungCap, btnNhanVien, btnPhieuNhap,
                btnTaiKhoan, btnThuoc, btnDangXuat, btnPhieuDoiTra));
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
        // Menu hóa đơn
        popupMenuHoaDon = new RoundedPopupMenu();
        RoundedMenuItem itemHoaDon1 = new RoundedMenuItem("Cập Nhật");
        popupMenuHoaDon.add(itemHoaDon1);
        popupMenuHoaDon.add(new JSeparator());

        RoundedMenuItem itemHoaDon2 = new RoundedMenuItem("Tìm Kiếm");
        popupMenuHoaDon.add(itemHoaDon2);
        popupMenuHoaDon.add(new JSeparator());

        btnHoaDon.addActionListener(e -> popupMenuHoaDon.show(btnHoaDon, btnHoaDon.getWidth(), 2));
        setFontForMenuItems(new RoundedMenuItem[]{itemHoaDon1, itemHoaDon2});
        // Menu hóa đơn đổi trả với cách hiển thị thay thế
        popupMenuHoaDonDoiTra = new RoundedPopupMenu();
        RoundedPopupMenu popupMenuHoaDonDoi = new RoundedPopupMenu();
        RoundedPopupMenu popupMenuHoaDonTra = new RoundedPopupMenu();

        // Tạo menu chính với 2 mục
        RoundedMenuItem menuHoaDonDoi = new RoundedMenuItem("Phiếu Đổi");
        popupMenuHoaDonDoiTra.add(menuHoaDonDoi);
        popupMenuHoaDonDoiTra.add(new JSeparator());

        RoundedMenuItem menuHoaDonTra = new RoundedMenuItem("Phiếu Trả");
        popupMenuHoaDonDoiTra.add(menuHoaDonTra);

        // Tạo menu phụ Hóa Đơn Đổi
        RoundedMenuItem itemCapNhatHoaDonDoi = new RoundedMenuItem("Cập nhật phiếu đổi");
        popupMenuHoaDonDoi.add(itemCapNhatHoaDonDoi);
        popupMenuHoaDonDoi.add(new JSeparator());

        RoundedMenuItem itemTimKiemHoaDonDoi = new RoundedMenuItem("Tìm kiếm phiếu đổi");
        popupMenuHoaDonDoi.add(itemTimKiemHoaDonDoi);

        // Tạo menu phụ Hóa Đơn Trả
        RoundedMenuItem itemCapNhatHoaDonTra = new RoundedMenuItem("Cập nhật phiếu trả");
        popupMenuHoaDonTra.add(itemCapNhatHoaDonTra);
        popupMenuHoaDonTra.add(new JSeparator());

        RoundedMenuItem itemTimKiemHoaDonTra = new RoundedMenuItem("Tìm kiếm phiếu trả");
        popupMenuHoaDonTra.add(itemTimKiemHoaDonTra);

        // Thiết lập sự kiện cho menu cấp 1
        menuHoaDonDoi.addActionListener(e -> {
            // Đóng menu cấp 1
            popupMenuHoaDonDoiTra.setVisible(false);

            // Hiển thị menu cấp 2 tại vị trí tương tự
            popupMenuHoaDonDoi.show(btnPhieuDoiTra, btnPhieuDoiTra.getWidth(), 2);
        });

        menuHoaDonTra.addActionListener(e -> {
            // Đóng menu cấp 1
            popupMenuHoaDonDoiTra.setVisible(false);

            // Hiển thị menu cấp 2 tại vị trí tương tự
            popupMenuHoaDonTra.show(btnPhieuDoiTra, btnPhieuDoiTra.getWidth(), 2);
        });

        // Sự kiện cho menu chính
        btnPhieuDoiTra.addActionListener(e -> popupMenuHoaDonDoiTra.show(btnPhieuDoiTra, btnPhieuDoiTra.getWidth(), 2));

        // Thiết lập font
        setFontForMenuItems(new RoundedMenuItem[]{menuHoaDonDoi, menuHoaDonTra});
        setFontForMenuItems(new RoundedMenuItem[]{itemCapNhatHoaDonDoi, itemTimKiemHoaDonDoi,
            itemCapNhatHoaDonTra, itemTimKiemHoaDonTra});
        // Menu phiếu nhập
        popupMenuPhieuNhap = new RoundedPopupMenu();
        RoundedMenuItem itemPhieuNhap1 = new RoundedMenuItem("Cập nhật");
        popupMenuPhieuNhap.add(itemPhieuNhap1);
        popupMenuPhieuNhap.add(new JSeparator());

        RoundedMenuItem itemPhieuNhap2 = new RoundedMenuItem("Tìm kiếm");
        popupMenuPhieuNhap.add(itemPhieuNhap2);
        popupMenuPhieuNhap.add(new JSeparator());

        btnPhieuNhap.addActionListener(e -> popupMenuPhieuNhap.show(btnPhieuNhap, btnPhieuNhap.getWidth(), 2));
        setFontForMenuItems(new RoundedMenuItem[]{itemPhieuNhap1, itemPhieuNhap2});
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

        // sự kiện cập nhật nhân viên
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
        // sự kiện tìm kiếm nhân viên
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
        // sự kiện tìm kiếm nhà cung cấp
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
        // Sự kiện tìm kiếm nhà cung cấp
        itemNCC2.addActionListener(e -> {
            frmSearchNhaCungCap ncc = new frmSearchNhaCungCap();
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
        // sự kiện cập nhật khách hàng
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
        // sự kiện tìm kiếm khách hàng
        itemKH2.addActionListener(e -> {
            frmSearchKhachHang kh = new frmSearchKhachHang();
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
        // sự kiện cập nhật thuốc
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
        // sự kiện tìm kiếm thuốc
        itemThuoc2.addActionListener(e -> {
            frmSearchThuoc thuoc2 = new frmSearchThuoc();
            // Xóa tất cả các phần cũ
            mainPanel.removeAll();
            // Đặt layout cho mainPanel
            mainPanel.setLayout(new java.awt.BorderLayout());

            // Thêm NhaCungCapCapNhat vào mainPanel
            mainPanel.add(thuoc2, java.awt.BorderLayout.CENTER);

            // Cập nhật lại giao diện
            mainPanel.revalidate();
            mainPanel.repaint();
        });
        // sự kiện cập nhật phiếu nhập
        itemPhieuNhap1.addActionListener(e -> {
            frmPhieuNhapCapNhat pn1 = new frmPhieuNhapCapNhat();
            // Xóa tất cả các phần cũ
            mainPanel.removeAll();
            // Đặt layout cho mainPanel
            mainPanel.setLayout(new java.awt.BorderLayout());

            // Thêm NhaCungCapCapNhat vào mainPanel
            mainPanel.add(pn1, java.awt.BorderLayout.CENTER);

            // Cập nhật lại giao diện
            mainPanel.revalidate();
            mainPanel.repaint();
        });
        // sự kiện tìm kiếm phiếu nhập
        itemPhieuNhap2.addActionListener(e -> {
            frmSearchPhieuNhap pn2 = new frmSearchPhieuNhap();
            // Xóa tất cả các phần cũ
            mainPanel.removeAll();
            // Đặt layout cho mainPanel
            mainPanel.setLayout(new java.awt.BorderLayout());

            // Thêm NhaCungCapCapNhat vào mainPanel
            mainPanel.add(pn2, java.awt.BorderLayout.CENTER);

            // Cập nhật lại giao diện
            mainPanel.revalidate();
            mainPanel.repaint();
        });
        // sự kiện cập nhật hóa đơn
        itemHoaDon1.addActionListener(e -> {
            frmHoaDonCapNhat hoadon1 = new frmHoaDonCapNhat();
            // Xóa tất cả các phần cũ
            mainPanel.removeAll();
            // Đặt layout cho mainPanel
            mainPanel.setLayout(new java.awt.BorderLayout());

            // Thêm NhaCungCapCapNhat vào mainPanel
            mainPanel.add(hoadon1, java.awt.BorderLayout.CENTER);

            // Cập nhật lại giao diện
            mainPanel.revalidate();
            mainPanel.repaint();
        });
        // sự kiện tìm kiếm hóa đơn
        itemHoaDon2.addActionListener(e -> {
            frmSearchHoaDon hoadon2 = new frmSearchHoaDon();
            // Xóa tất cả các phần cũ
            mainPanel.removeAll();
            // Đặt layout cho mainPanel
            mainPanel.setLayout(new java.awt.BorderLayout());

            // Thêm TimHoaDon vào mainPanel
            mainPanel.add(hoadon2, java.awt.BorderLayout.CENTER);

            // Cập nhật lại giao diện
            mainPanel.revalidate();
            mainPanel.repaint();
        });
        // Sự kiện cập nhật hóa đơn đổi
        itemCapNhatHoaDonDoi.addActionListener(e -> {
            frmPhieuDoiCapNhat doitra1 = new frmPhieuDoiCapNhat();
            // Xóa tất cả các phần cũ
            mainPanel.removeAll();
            // Đặt layout cho mainPanel
            mainPanel.setLayout(new java.awt.BorderLayout());

            // Thêm form vào mainPanel
            mainPanel.add(doitra1, java.awt.BorderLayout.CENTER);

            // Cập nhật lại giao diện
            mainPanel.revalidate();
            mainPanel.repaint();
        });

        // Sự kiện tìm kiếm hóa đơn đổi
        itemTimKiemHoaDonDoi.addActionListener(e -> {
            frmSearchPhieuDoi doitra2 = new frmSearchPhieuDoi();
            // Xóa tất cả các phần cũ
            mainPanel.removeAll();
            // Đặt layout cho mainPanel
            mainPanel.setLayout(new java.awt.BorderLayout());

            // Thêm form vào mainPanel
            mainPanel.add(doitra2, java.awt.BorderLayout.CENTER);

            // Cập nhật lại giao diện
            mainPanel.revalidate();
            mainPanel.repaint();
        });

        // Sự kiện cập nhật hóa đơn trả
        itemCapNhatHoaDonTra.addActionListener(e -> {
            frmPhieuTraCapNhat doitra4 = new frmPhieuTraCapNhat();
            // Xóa tất cả các phần cũ
            mainPanel.removeAll();
            // Đặt layout cho mainPanel
            mainPanel.setLayout(new java.awt.BorderLayout());

            // Thêm form vào mainPanel
            mainPanel.add(doitra4, java.awt.BorderLayout.CENTER);

            // Cập nhật lại giao diện
            mainPanel.revalidate();
            mainPanel.repaint();
        });

        // Sự kiện tìm kiếm hóa đơn trả
        itemTimKiemHoaDonTra.addActionListener(e -> {
            frmSearchPhieuTra doitra5 = new frmSearchPhieuTra();
            // Xóa tất cả các phần cũ
            mainPanel.removeAll();
            // Đặt layout cho mainPanel
            mainPanel.setLayout(new java.awt.BorderLayout());

            // Thêm form vào mainPanel
            mainPanel.add(doitra5, java.awt.BorderLayout.CENTER);

            // Cập nhật lại giao diện
            mainPanel.revalidate();
            mainPanel.repaint();
        });
        // sự kiện cập nhật tài khoản
        itemTK1.addActionListener(e -> {
            frmTaiKhoanCapNhat tkcn = new frmTaiKhoanCapNhat();
            // Xóa tất cả các phần cũ
            mainPanel.removeAll();
            // Đặt layout cho mainPanel
            mainPanel.setLayout(new java.awt.BorderLayout());

            // Thêm NhaCungCapCapNhat vào mainPanel
            mainPanel.add(tkcn, java.awt.BorderLayout.CENTER);

            // Cập nhật lại giao diện
            mainPanel.revalidate();
            mainPanel.repaint();
        });
        // sự kiện tìm kiếm tài khoản
        itemTK2.addActionListener(e -> {
            frmSearchTaiKhoan tksearch = new frmSearchTaiKhoan();
            // Xóa tất cả các phần cũ
            mainPanel.removeAll();
            // Đặt layout cho mainPanel
            mainPanel.setLayout(new java.awt.BorderLayout());

            // Thêm NhaCungCapCapNhat vào mainPanel
            mainPanel.add(tksearch, java.awt.BorderLayout.CENTER);

            // Cập nhật lại giao diện
            mainPanel.revalidate();
            mainPanel.repaint();
        });
        // sựu kiện thống kê
        btnThongKe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                // Mở trang thống kê khi nhấn nút "Thống kê"
                frmThongKe thongKe = new frmThongKe();

                // Xóa các phần cũ trong mainPanel
                mainPanel.removeAll();

                // Đặt layout cho mainPanel
                mainPanel.setLayout(new java.awt.BorderLayout());

                // Thêm frmThongKe vào mainPanel
                mainPanel.add(thongKe, java.awt.BorderLayout.CENTER);

                // Cập nhật lại giao diện
                mainPanel.revalidate();
                mainPanel.repaint();
            }
        });
        xuLyPhanQuyen();
    }

    private void xuLyPhanQuyen() {
        String chucVu = taiKhoan.getNhanVien().getChucVu();

        if (!chucVu.equalsIgnoreCase("Quản lý")) {
            btnThongKe.setEnabled(false);
            btnNhanVien.setEnabled(false);
            btnTaiKhoan.setEnabled(false);
        }
        if (chucVu.equalsIgnoreCase("Quản lý")) {
            btnHoaDon.setEnabled(false);
            btnKhachHang.setEnabled(false);
            btnNhaCungCap.setEnabled(false);
            btnThuoc.setEnabled(false);
            btnPhieuDoiTra.setEnabled(false);
            btnPhieuNhap.setEnabled(false);
        }
        lblAvatar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Frame parentFrame = (Frame) SwingUtilities.getWindowAncestor(lblAvatar);
                formDoiMkTK form = new formDoiMkTK(parentFrame, true, taiKhoan);
                form.setLocationRelativeTo(parentFrame);
                form.setVisible(true);

            }
        });
        capNhatThongTinNguoiDung();
    }

    private void capNhatThongTinNguoiDung() {
        lblName.setText(taiKhoan.getNhanVien().getHoTen());
        lblRole.setText(taiKhoan.getNhanVien().getChucVu());

        String gioiTinh = taiKhoan.getNhanVien().getGioiTinh();
        if (gioiTinh != null && gioiTinh.equalsIgnoreCase("Nữ")) {
            lblAvatar.setIcon(new FlatSVGIcon("./icon/woman.svg"));
        } else {
            lblAvatar.setIcon(new FlatSVGIcon("./icon/man.svg"));
        }
    }

    private void setFontForMenuItems(RoundedMenuItem[] items) {
        Font font = new Font("Segoe UI", Font.BOLD, 12); // Thiết lập font ở đây
        for (RoundedMenuItem item : items) {
            item.setFont(font);
        }
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

    public void replaceMainPanel(JPanel newPanel) {
        // Xóa tất cả các phần cũ trong mainPanel
        mainPanel.removeAll();

        // Đặt layout cho mainPanel
        mainPanel.setLayout(new java.awt.BorderLayout());

        // Thêm panel mới vào mainPanel
        mainPanel.add(newPanel, java.awt.BorderLayout.CENTER);

        // Cập nhật lại giao diện
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private void addActionListeners(List<JButton> buttons) {
        for (JButton button : buttons) {
            button.addActionListener(this::changeButtonColor);
        }
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
        mainPanel = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();

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
        btnDangXuat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDangXuatActionPerformed(evt);
            }
        });
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
        btnPhieuDoiTra.setText("Phiếu Đổi/Trả");
        btnPhieuDoiTra.setAlignmentY(0.0F);
        btnPhieuDoiTra.setBorderPainted(false);
        btnPhieuDoiTra.setMaximumSize(new java.awt.Dimension(208, 42));
        btnPhieuDoiTra.setMinimumSize(new java.awt.Dimension(208, 42));
        btnPhieuDoiTra.setPreferredSize(new java.awt.Dimension(208, 42));
        btnPhieuDoiTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPhieuDoiTraActionPerformed(evt);
            }
        });
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
        mainPanel.setLayout(new java.awt.BorderLayout());

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/resized_output.jpg"))); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(1024, 1024));
        mainPanel.add(jLabel6, java.awt.BorderLayout.CENTER);

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

    private void btnDangXuatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDangXuatActionPerformed
        this.dispose();
        Login login = new Login();
        login.setVisible(true);
    }//GEN-LAST:event_btnDangXuatActionPerformed

    private void btnThuocActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnThuocActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_btnThuocActionPerformed

    private void btnHoaDonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnHoaDonActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_btnHoaDonActionPerformed

    private void btnPhieuDoiTraActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnPhieuDoiTraActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_btnPhieuDoiTraActionPerformed

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
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
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

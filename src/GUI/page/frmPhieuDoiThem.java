/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package GUI.page;

import DAO.ChiTietHoaDonDAO;
import DAO.DanhMucDAO;
import DAO.PhieuDoiDAO;
import DAO.NhanVienDAO;
import DAO.ThuocDAO;
import Entity.ChiTietHoaDon;
import Entity.Thuoc;
import Entity.ChiTietPhieuDoi;
import Entity.DanhMuc;
import Entity.HoaDon;
import Entity.KhachHang;
import Entity.PhieuDoi;
import Entity.NhanVien;
import GUI.Main;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableCellRenderer;

//import utils.Formatter;
//import utils.MessageDialog;
//import utils.RandomGenerator;
//import utils.TableSorter;
//import utils.Validation;
//import utils.WritePDF
/**
 *
 * @author ADMIN
 */
public class frmPhieuDoiThem extends javax.swing.JPanel {

        private String maKH; // Mã khách hàng
        private String maNV; // Mã nhân viên

        /**
         * Creates new form frmHoaDonCapNhat
         */
        private int startIndex = 0; // Track the starting index for data loading
        private JLabel lblThongTinThuoc;
        private boolean isSearching = false;

        public frmPhieuDoiThem() {
                initComponents();
                configureTable();
                initEvent();
                loadDataToTable();
                addTableSelectionListener();
                // Tạo và hiển thị mã hóa đơn mới
                String maHoaDonDoi = PhieuDoiDAO.taoMaHoaDonDoi(); // Lấy mã hóa đơn mới
                txtMaHoaDonDoi.setText(maHoaDonDoi); // Gán vào ô txtMaHoaDon

                // Tạo và hiển thị thời gian hiện tại
                String thoiGian = getCurrentTime(); // Lấy thời gian hiện tại
                txtThoiGian.setText(thoiGian); // Gán vào ô txtThoiGian

                jScrollPane1.getVerticalScrollBar().addAdjustmentListener(e -> {
                        // Nếu đang tìm kiếm thì không tải thêm dữ liệu
                        if (isSearching) {
                                return;
                        }

                        JScrollBar vertical = jScrollPane1.getVerticalScrollBar();
                        int max = vertical.getMaximum();
                        int current = vertical.getValue();
                        int visible = vertical.getVisibleAmount();

                        // Kiểm tra nếu người dùng đã cuộn đến cuối bảng
                        if (current + visible >= max) {
                                startIndex += 13; // Tăng chỉ mục bắt đầu để tải dữ liệu tiếp theo
                                loadDataToTable(); // Tải thêm dữ liệu
                        }
                });
        }
        // Phương thức lấy thời gian hiện tại và định dạng theo yêu cầu

        private String getCurrentTime() {
                LocalDateTime now = LocalDateTime.now(); // Lấy thời gian hiện tại
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"); // Định dạng thời gian
                return now.format(formatter); // Trả về thời gian đã định dạng
        }

        private void configureTable() {
                // Ngăn không cho phép người dùng chỉnh sửa bảng
                jTable1.setDefaultEditor(Object.class, null); // Điều này vô hiệu hóa khả năng chỉnh sửa của bất kỳ ô
                                                              // nào trong bảng.

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
                // Lấy dữ liệu thuốc với batch tiếp theo (10 dòng)
                SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                        @Override
                        protected Void doInBackground() throws Exception {
                                // Lấy danh sách NCC từ cơ sở dữ liệu (10 dòng bắt đầu từ startIndex)
                                List<Thuoc> thuocList = ThuocDAO.getThuocBatch(startIndex, 13); // startIndex là chỉ mục
                                                                                                // bắt đầu
                                SwingUtilities.invokeLater(() -> {
                                        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();

                                        // Chỉ thêm dữ liệu mới vào bảng, không xóa dữ liệu cũ
                                        for (Thuoc thuoc : thuocList) {
                                                model.addRow(new Object[] {
                                                                thuoc.getId(),
                                                                thuoc.getTenThuoc(),
                                                                thuoc.getThanhPhan(),
                                                                thuoc.getGiaNhap(),
                                                                thuoc.getDonGia(),
                                                                thuoc.getHsd(),
                                                                thuoc.getDanhMuc() != null ? thuoc.getDanhMuc().getTen()
                                                                                : null,
                                                                thuoc.getDonViTinh() != null
                                                                                ? thuoc.getDonViTinh().getTen()
                                                                                : null,
                                                                thuoc.getXuatXu() != null ? thuoc.getXuatXu().getTen()
                                                                                : null,
                                                                thuoc.getSoLuong()
                                                });
                                        }
                                });
                                return null;
                        }
                };
                worker.execute();
        }

        private void addTableSelectionListener() {
                // Thêm listener xử lý sự kiện khi chọn dòng trong bảng
                jTable1.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                        @Override
                        public void valueChanged(ListSelectionEvent e) {
                                if (!e.getValueIsAdjusting()) { // Chỉ xử lý khi sự kiện chọn đã hoàn tất
                                        int selectedRow = jTable1.getSelectedRow();
                                        if (selectedRow != -1) {
                                                // Lấy mã thuốc từ dòng đã chọn
                                                String maThuoc = jTable1.getValueAt(selectedRow, 0) != null
                                                                ? jTable1.getValueAt(selectedRow, 0).toString()
                                                                : "";

                                                // Tìm thông tin thuốc theo mã thuốc
                                                Thuoc thuoc = ThuocDAO.getThuocByMaThuoc(maThuoc);

                                                if (thuoc != null) {
                                                        // Cập nhật các trường thông tin với dữ liệu thuốc đã chọn
                                                        txtMaThuoc.setText(thuoc.getId());
                                                        txtTenThuoc.setText(thuoc.getTenThuoc());
                                                        txtThanhPhan.setText(thuoc.getThanhPhan());
                                                        txtDonGia.setText(String.valueOf(thuoc.getDonGia()));

                                                        // Hiển thị ảnh thuốc
                                                        byte[] anhThuoc = thuoc.getHinhAnh();
                                                        if (anhThuoc != null && anhThuoc.length > 0) {
                                                                ImageIcon icon = new ImageIcon(anhThuoc);
                                                                lblAnh.setIcon(icon); // Cập nhật ảnh vào lblAnh
                                                        } else {
                                                                // Nếu không có ảnh, hiển thị ảnh mặc định
                                                                lblAnh.setIcon(new FlatSVGIcon("./icon/image.svg"));
                                                        }
                                                }
                                        }
                                }
                        }
                });
        }

        private void setupCategoryComboBox() {
                // Tạo model cho ComboBox
                DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>();

                // Thêm tùy chọn "Tất cả" vào đầu danh sách
                comboBoxModel.addElement("Tất cả");

                try {
                        // Lấy danh sách các danh mục từ cơ sở dữ liệu
                        List<DanhMuc> danhMucList = DanhMucDAO.getDanhMucList();

                        // Thêm tên các danh mục vào ComboBox
                        for (DanhMuc danhMuc : danhMucList) {
                                comboBoxModel.addElement(danhMuc.getTen());
                        }
                } catch (Exception ex) {
                        System.err.println("Lỗi khi tải danh mục: " + ex.getMessage());
                        ex.printStackTrace();

                        // Thêm một số danh mục mặc định nếu không thể tải từ cơ sở dữ liệu
                        comboBoxModel.addElement("Thuốc đau đầu");
                        comboBoxModel.addElement("Thuốc tim mạch");
                        comboBoxModel.addElement("Thuốc kháng sinh");
                        comboBoxModel.addElement("Thuốc bổ");
                        comboBoxModel.addElement("Thuốc da dày");
                }

                // Cập nhật model cho ComboBox
                jComboBox1.setModel(comboBoxModel);

                // Đặt lựa chọn mặc định là "Tất cả"
                jComboBox1.setSelectedItem("Tất cả");
        }
        // 3. Thêm phương thức lọc theo danh mục - phải được đặt bên trong lớp, ngang
        // hàng với các phương thức khác

        private void filterByCategory(String category) {
                DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
                model.setRowCount(0);
                model.addRow(new Object[] { "Đang lọc...", "", "", "", "", "", "", "", "", "" });

                SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                        private List<Thuoc> filteredList = new ArrayList<>();

                        @Override
                        protected Void doInBackground() throws Exception {
                                // Lấy tất cả thuốc từ cơ sở dữ liệu
                                List<Thuoc> allThuoc = ThuocDAO.getAllThuoc();

                                // Lọc danh sách thuốc theo danh mục
                                for (Thuoc thuoc : allThuoc) {
                                        if (thuoc.getDanhMuc() != null
                                                        && thuoc.getDanhMuc().getTen().equals(category)) {
                                                filteredList.add(thuoc);
                                        }
                                }

                                return null;
                        }

                        @Override
                        protected void done() {
                                SwingUtilities.invokeLater(() -> {
                                        model.setRowCount(0);

                                        if (filteredList.isEmpty()) {
                                                model.addRow(new Object[] { "Không có dữ liệu", "", "", "", "", "", "",
                                                                "", "", "" });
                                                return;
                                        }

                                        // Thêm dữ liệu đã lọc vào bảng theo cấu trúc mới
                                        for (Thuoc thuoc : filteredList) {
                                                model.addRow(new Object[] {
                                                                thuoc.getId(),
                                                                thuoc.getTenThuoc(),
                                                                thuoc.getThanhPhan(),
                                                                thuoc.getGiaNhap(),
                                                                thuoc.getDonGia(),
                                                                thuoc.getHsd(),
                                                                thuoc.getDanhMuc() != null ? thuoc.getDanhMuc().getTen()
                                                                                : null,
                                                                thuoc.getDonViTinh() != null
                                                                                ? thuoc.getDonViTinh().getTen()
                                                                                : null,
                                                                thuoc.getXuatXu() != null ? thuoc.getXuatXu().getTen()
                                                                                : null,
                                                                thuoc.getSoLuong()
                                                });
                                        }

                                        // Cập nhật giao diện
                                        jTable1.revalidate();
                                        jTable1.repaint();
                                });
                        }
                };

                worker.execute();
        }

        private void searchThuoc(String keyword) {
                // Tạo câu lệnh SQL để tìm thuốc có tên chứa từ khóa
                List<Thuoc> thuocList = ThuocDAO.getThuocByKeyword(keyword); // Tìm thuốc qua ThuocDAO

                // Lấy model của bảng
                DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
                model.setRowCount(0); // Xóa hết dữ liệu cũ trong bảng

                // Thêm các kết quả tìm kiếm vào bảng
                for (Thuoc thuoc : thuocList) {
                        model.addRow(new Object[] {
                                        thuoc.getId(),
                                        thuoc.getTenThuoc(),
                                        thuoc.getThanhPhan(),
                                        thuoc.getGiaNhap(),
                                        thuoc.getDonGia(),
                                        thuoc.getHsd(),
                                        thuoc.getDanhMuc() != null ? thuoc.getDanhMuc().getTen() : null,
                                        thuoc.getDonViTinh() != null ? thuoc.getDonViTinh().getTen() : null,
                                        thuoc.getXuatXu() != null ? thuoc.getXuatXu().getTen() : null,
                                        thuoc.getSoLuong()
                        });
                }
        }

        private void tinhTongTienHoaDon() {
                DefaultTableModel chiTietModel = (DefaultTableModel) jTable2.getModel();
                double tongTien = 0.0;

                // Lặp qua tất cả các dòng trong bảng để tính tổng thành tiền
                for (int i = 0; i < chiTietModel.getRowCount(); i++) {
                        // Lấy giá trị cột "Thành tiền" (giả sử là cột thứ 5, index = 4)
                        Object thanhTienObj = chiTietModel.getValueAt(i, 5); // Cột Thành tiền (index = 5)

                        if (thanhTienObj != null) {
                                try {
                                        double thanhTien = Double.parseDouble(thanhTienObj.toString()); // Chuyển giá
                                                                                                        // trị thành số
                                                                                                        // thực
                                        tongTien += thanhTien; // Cộng dồn vào tổng
                                } catch (NumberFormatException e) {
                                        System.err.println("Lỗi khi chuyển đổi thành tiền ở dòng " + i + ": "
                                                        + e.getMessage());
                                }
                        }
                }

                // Cập nhật tổng tiền vào txtTong
                txtTong.setText(String.format("%.0f", tongTien)); // Hiển thị tổng tiền, định dạng theo số nguyên
        }

        // Make sure to also remove any references to FlatSVGIcon in your button setup
        // code
        // For example, replace:
        // btnSearchKH.setIcon(new FlatSVGIcon("./icon/search.svg"));
        // with just:
        // btnSearchKH.setText("Search"); // or whatever text you want
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
        // <editor-fold defaultstate="collapsed" desc="Generated
        // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        label1 = new java.awt.Label();
        jOptionPane1 = new javax.swing.JOptionPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jPanel28 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jPanel29 = new javax.swing.JPanel();
        JPanhThuoc = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        lblAnh = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jPanel30 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        txtMaThuoc = new javax.swing.JTextField();
        jPanel14 = new javax.swing.JPanel();
        jPanel19 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jPanel18 = new javax.swing.JPanel();
        txtTenThuoc = new javax.swing.JTextField();
        jPanel15 = new javax.swing.JPanel();
        jPanel44 = new javax.swing.JPanel();
        jPanel24 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jPanel22 = new javax.swing.JPanel();
        jPanel45 = new javax.swing.JPanel();
        jPanel46 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        txtThanhPhan = new javax.swing.JTextArea();
        jPanel47 = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        jPanel21 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jPanel48 = new javax.swing.JPanel();
        txtDonGia = new javax.swing.JTextField();
        jPanel49 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel50 = new javax.swing.JPanel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jPanel51 = new javax.swing.JPanel();
        txtThuoc = new javax.swing.JTextField();
        btnTimKiem = new javax.swing.JButton();
        jPanel52 = new javax.swing.JPanel();
        label2 = new java.awt.Label();
        txtSoLuong = new javax.swing.JTextField();
        jPanel57 = new javax.swing.JPanel();
        btnThem = new javax.swing.JButton();
        jPanel53 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        billPanel = new javax.swing.JPanel();
        jPanel41 = new javax.swing.JPanel();
        jPanel42 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jPanel43 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jPanel54 = new javax.swing.JPanel();
        btnXoa = new javax.swing.JButton();
        billInfoPanel = new javax.swing.JPanel();
        jPanel34 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jPanel58 = new javax.swing.JPanel();
        btnHuy = new javax.swing.JButton();
        btnThanhToan = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jPanel35 = new javax.swing.JPanel();
        jPanel36 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        txtMaHoaDonDoi = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        txtMaHD = new javax.swing.JTextField();
        btnAddHoaDon = new javax.swing.JButton();
        jPanel37 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        txtSdtKH = new javax.swing.JTextField();
        jPanel38 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        txtTenKH = new javax.swing.JTextField();
        txtGioiTinh = new javax.swing.JTextField();
        jPanel8 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        txtThoiGian = new javax.swing.JTextField();
        jPanel59 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        txtNV = new javax.swing.JTextField();
        btnSearchNV = new javax.swing.JButton();
        jPanel60 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        txtLyDo = new javax.swing.JTextField();
        jSeparator4 = new javax.swing.JSeparator();
        jScrollPane5 = new javax.swing.JScrollPane();
        jPanel39 = new javax.swing.JPanel();
        jPanel40 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        txtTong = new javax.swing.JTextField();
        jPanel55 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        txtTienKhachDua = new javax.swing.JTextField();
        jPanel56 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        txtTienThua = new javax.swing.JTextField();

        label1.setText("label1");

        setBackground(new java.awt.Color(204, 204, 204));
        setMaximumSize(new java.awt.Dimension(829, 624));
        setMinimumSize(new java.awt.Dimension(829, 624));
        setPreferredSize(new java.awt.Dimension(829, 624));
        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.X_AXIS));

        jPanel1.setBackground(new java.awt.Color(81, 219, 185));
        jPanel1.setPreferredSize(new java.awt.Dimension(480, 624));
        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.Y_AXIS));

        jPanel9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(81, 219, 185), 5));
        jPanel9.setMinimumSize(new java.awt.Dimension(470, 352));
        jPanel9.setPreferredSize(new java.awt.Dimension(470, 362));
        jPanel9.setLayout(new java.awt.BorderLayout());

        jPanel28.setBackground(new java.awt.Color(0, 120, 92));
        jPanel28.setPreferredSize(new java.awt.Dimension(521, 40));
        jPanel28.setLayout(new java.awt.BorderLayout());

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Thông tin thuốc");
        jPanel28.add(jLabel5, java.awt.BorderLayout.CENTER);

        jPanel9.add(jPanel28, java.awt.BorderLayout.NORTH);

        jPanel29.setMinimumSize(new java.awt.Dimension(470, 262));
        jPanel29.setPreferredSize(new java.awt.Dimension(470, 262));
        jPanel29.setLayout(new javax.swing.BoxLayout(jPanel29, javax.swing.BoxLayout.X_AXIS));

        JPanhThuoc.setMinimumSize(new java.awt.Dimension(262, 262));

        lblAnh.setIcon(new FlatSVGIcon("./icon/image.svg"));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblAnh, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblAnh, javax.swing.GroupLayout.DEFAULT_SIZE, 284, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout JPanhThuocLayout = new javax.swing.GroupLayout(JPanhThuoc);
        JPanhThuoc.setLayout(JPanhThuocLayout);
        JPanhThuocLayout.setHorizontalGroup(
            JPanhThuocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JPanhThuocLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        JPanhThuocLayout.setVerticalGroup(
            JPanhThuocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JPanhThuocLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jPanel29.add(JPanhThuoc);

        jPanel12.setPreferredSize(new java.awt.Dimension(323, 262));
        jPanel12.setLayout(new javax.swing.BoxLayout(jPanel12, javax.swing.BoxLayout.Y_AXIS));

        jPanel13.setEnabled(false);
        jPanel13.setMinimumSize(new java.awt.Dimension(400, 30));
        jPanel13.setPreferredSize(new java.awt.Dimension(400, 30));
        jPanel13.setLayout(new java.awt.BorderLayout());

        jPanel30.setName(""); // NOI18N
        jPanel30.setPreferredSize(new java.awt.Dimension(200, 30));
        jPanel30.setLayout(new java.awt.BorderLayout());

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("Mã thuốc:");
        jPanel30.add(jLabel14, java.awt.BorderLayout.CENTER);

        jPanel13.add(jPanel30, java.awt.BorderLayout.LINE_START);

        jPanel17.setMinimumSize(new java.awt.Dimension(300, 50));
        jPanel17.setPreferredSize(new java.awt.Dimension(300, 50));
        jPanel17.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 5));

        txtMaThuoc.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtMaThuoc.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 120, 92)));
        txtMaThuoc.setMinimumSize(new java.awt.Dimension(164, 27));
        txtMaThuoc.setName(""); // NOI18N
        txtMaThuoc.setPreferredSize(new java.awt.Dimension(150, 25));
        jPanel17.add(txtMaThuoc);

        jPanel13.add(jPanel17, java.awt.BorderLayout.CENTER);

        jPanel12.add(jPanel13);

        jPanel14.setEnabled(false);
        jPanel14.setPreferredSize(new java.awt.Dimension(400, 30));
        jPanel14.setLayout(new java.awt.BorderLayout());

        jPanel19.setMinimumSize(new java.awt.Dimension(100, 100));
        jPanel19.setName(""); // NOI18N
        jPanel19.setPreferredSize(new java.awt.Dimension(200, 30));
        jPanel19.setLayout(new java.awt.BorderLayout());

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("Tên thuốc:");
        jPanel19.add(jLabel15, java.awt.BorderLayout.CENTER);

        jPanel14.add(jPanel19, java.awt.BorderLayout.LINE_START);

        jPanel18.setMinimumSize(new java.awt.Dimension(300, 50));
        jPanel18.setPreferredSize(new java.awt.Dimension(300, 50));
        jPanel18.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 5));

        txtTenThuoc.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtTenThuoc.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 120, 92)));
        txtTenThuoc.setMinimumSize(new java.awt.Dimension(164, 27));
        txtTenThuoc.setName(""); // NOI18N
        txtTenThuoc.setPreferredSize(new java.awt.Dimension(150, 25));
        jPanel18.add(txtTenThuoc);

        jPanel14.add(jPanel18, java.awt.BorderLayout.CENTER);

        jPanel12.add(jPanel14);

        jPanel15.setMinimumSize(new java.awt.Dimension(348, 172));
        jPanel15.setPreferredSize(new java.awt.Dimension(400, 172));
        jPanel15.setLayout(new java.awt.BorderLayout());

        jPanel44.setLayout(new java.awt.BorderLayout());

        jPanel24.setPreferredSize(new java.awt.Dimension(200, 40));
        jPanel24.setLayout(new java.awt.BorderLayout());

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("Thành phần:");
        jPanel24.add(jLabel16, java.awt.BorderLayout.CENTER);

        jPanel44.add(jPanel24, java.awt.BorderLayout.LINE_START);

        jPanel15.add(jPanel44, java.awt.BorderLayout.NORTH);

        jPanel22.setMinimumSize(new java.awt.Dimension(100, 100));
        jPanel22.setName(""); // NOI18N
        jPanel22.setPreferredSize(new java.awt.Dimension(200, 142));
        jPanel22.setLayout(new javax.swing.BoxLayout(jPanel22, javax.swing.BoxLayout.LINE_AXIS));

        jPanel45.setPreferredSize(new java.awt.Dimension(60, 142));

        javax.swing.GroupLayout jPanel45Layout = new javax.swing.GroupLayout(jPanel45);
        jPanel45.setLayout(jPanel45Layout);
        jPanel45Layout.setHorizontalGroup(
            jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 60, Short.MAX_VALUE)
        );
        jPanel45Layout.setVerticalGroup(
            jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 142, Short.MAX_VALUE)
        );

        jPanel22.add(jPanel45);

        jPanel46.setLayout(new java.awt.BorderLayout());

        jScrollPane4.setMinimumSize(new java.awt.Dimension(234, 66));
        jScrollPane4.setPreferredSize(new java.awt.Dimension(234, 66));

        txtThanhPhan.setColumns(20);
        txtThanhPhan.setRows(5);
        jScrollPane4.setViewportView(txtThanhPhan);

        jPanel46.add(jScrollPane4, java.awt.BorderLayout.CENTER);

        jPanel22.add(jPanel46);

        jPanel47.setPreferredSize(new java.awt.Dimension(60, 142));

        javax.swing.GroupLayout jPanel47Layout = new javax.swing.GroupLayout(jPanel47);
        jPanel47.setLayout(jPanel47Layout);
        jPanel47Layout.setHorizontalGroup(
            jPanel47Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 60, Short.MAX_VALUE)
        );
        jPanel47Layout.setVerticalGroup(
            jPanel47Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 142, Short.MAX_VALUE)
        );

        jPanel22.add(jPanel47);

        jPanel15.add(jPanel22, java.awt.BorderLayout.PAGE_END);

        jPanel12.add(jPanel15);

        jPanel16.setEnabled(false);
        jPanel16.setPreferredSize(new java.awt.Dimension(400, 30));
        jPanel16.setLayout(new java.awt.BorderLayout());

        jPanel21.setMinimumSize(new java.awt.Dimension(100, 100));
        jPanel21.setName(""); // NOI18N
        jPanel21.setPreferredSize(new java.awt.Dimension(200, 30));
        jPanel21.setLayout(new java.awt.BorderLayout());

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("Đơn giá:");
        jPanel21.add(jLabel17, java.awt.BorderLayout.CENTER);

        jPanel16.add(jPanel21, java.awt.BorderLayout.LINE_START);

        jPanel48.setMinimumSize(new java.awt.Dimension(300, 50));
        jPanel48.setPreferredSize(new java.awt.Dimension(300, 50));
        jPanel48.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 5));

        txtDonGia.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtDonGia.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 120, 92)));
        txtDonGia.setMinimumSize(new java.awt.Dimension(164, 27));
        txtDonGia.setName(""); // NOI18N
        txtDonGia.setPreferredSize(new java.awt.Dimension(150, 25));
        jPanel48.add(txtDonGia);

        jPanel16.add(jPanel48, java.awt.BorderLayout.CENTER);

        jPanel12.add(jPanel16);

        jPanel29.add(jPanel12);

        jPanel9.add(jPanel29, java.awt.BorderLayout.CENTER);

        jPanel49.setBackground(new java.awt.Color(0, 120, 92));
        jPanel49.setPreferredSize(new java.awt.Dimension(521, 40));

        javax.swing.GroupLayout jPanel49Layout = new javax.swing.GroupLayout(jPanel49);
        jPanel49.setLayout(jPanel49Layout);
        jPanel49Layout.setHorizontalGroup(
            jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 919, Short.MAX_VALUE)
        );
        jPanel49Layout.setVerticalGroup(
            jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );

        jPanel9.add(jPanel49, java.awt.BorderLayout.SOUTH);

        jPanel1.add(jPanel9);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(81, 219, 185), 5));
        jPanel4.setMaximumSize(new java.awt.Dimension(2147483647, 2147483647));
        jPanel4.setMinimumSize(new java.awt.Dimension(420, 50));
        jPanel4.setPreferredSize(new java.awt.Dimension(420, 50));
        jPanel4.setLayout(new javax.swing.BoxLayout(jPanel4, javax.swing.BoxLayout.LINE_AXIS));

        jPanel50.setMaximumSize(new java.awt.Dimension(120, 50));
        jPanel50.setMinimumSize(new java.awt.Dimension(120, 50));
        jPanel50.setPreferredSize(new java.awt.Dimension(120, 50));
        jPanel50.setRequestFocusEnabled(false);
        jPanel50.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 10, 10));

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 120, 92)));
        jComboBox1.setMaximumSize(new java.awt.Dimension(72, 32));
        jComboBox1.setMinimumSize(new java.awt.Dimension(72, 32));
        jComboBox1.setPreferredSize(new java.awt.Dimension(72, 32));
        jPanel50.add(jComboBox1);

        jPanel4.add(jPanel50);

        jPanel51.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jPanel51.setMaximumSize(new java.awt.Dimension(240, 50));
        jPanel51.setMinimumSize(new java.awt.Dimension(240, 50));
        jPanel51.setPreferredSize(new java.awt.Dimension(240, 50));
        jPanel51.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 10, 10));

        txtThuoc.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        txtThuoc.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtThuoc.setActionCommand("<Not Set>");
        txtThuoc.setMinimumSize(new java.awt.Dimension(140, 32));
        txtThuoc.setPreferredSize(new java.awt.Dimension(140, 32));
        txtThuoc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtThuocKeyReleased(evt);
            }
        });
        jPanel51.add(txtThuoc);

        btnTimKiem.setText("Tìm");
        btnTimKiem.setPreferredSize(new java.awt.Dimension(60, 32));
        btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemActionPerformed(evt);
            }
        });
        jPanel51.add(btnTimKiem);

        jPanel4.add(jPanel51);

        jPanel52.setFocusTraversalPolicyProvider(true);
        jPanel52.setMinimumSize(new java.awt.Dimension(70, 50));
        jPanel52.setPreferredSize(new java.awt.Dimension(70, 50));
        jPanel52.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 10, 10));

        label2.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        label2.setText("Số lượng:");
        jPanel52.add(label2);

        txtSoLuong.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        txtSoLuong.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtSoLuong.setText("1");
        txtSoLuong.setPreferredSize(new java.awt.Dimension(70, 32));
        txtSoLuong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSoLuongActionPerformed(evt);
            }
        });
        jPanel52.add(txtSoLuong);

        jPanel4.add(jPanel52);

        jPanel57.setMaximumSize(new java.awt.Dimension(130, 50));
        jPanel57.setMinimumSize(new java.awt.Dimension(130, 50));
        jPanel57.setPreferredSize(new java.awt.Dimension(130, 50));

        btnThem.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        btnThem.setForeground(new java.awt.Color(0, 120, 92));
        btnThem.setText("Thêm");
        btnThem.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 120, 92)));
        btnThem.setPreferredSize(new java.awt.Dimension(130, 32));
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });
        jPanel57.add(btnThem);

        jPanel4.add(jPanel57);

        jPanel1.add(jPanel4);

        jPanel53.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(81, 219, 185), 5));
        jPanel53.setMinimumSize(new java.awt.Dimension(470, 352));
        jPanel53.setPreferredSize(new java.awt.Dimension(470, 362));
        jPanel53.setLayout(new java.awt.BorderLayout());

        jScrollPane1.setPreferredSize(new java.awt.Dimension(200, 452));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã thuốc", "Tên thuốc", "Thành phần", "Giá nhập", "Giá bán", "HSD", "Danh mục", "Đơn vị tính", "Xuất xứ", "Số lượng"
            }
        ));
        jTable1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTable1.setShowHorizontalLines(true);
        jScrollPane1.setViewportView(jTable1);

        jPanel53.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel53);

        add(jPanel1);

        billPanel.setBackground(new java.awt.Color(230, 245, 245));
        billPanel.setMinimumSize(new java.awt.Dimension(400, 624));
        billPanel.setLayout(new javax.swing.BoxLayout(billPanel, javax.swing.BoxLayout.Y_AXIS));

        jPanel41.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(81, 219, 185), 5));
        jPanel41.setPreferredSize(new java.awt.Dimension(400, 200));
        jPanel41.setLayout(new java.awt.BorderLayout());

        jPanel42.setBackground(new java.awt.Color(0, 120, 92));
        jPanel42.setPreferredSize(new java.awt.Dimension(400, 40));
        jPanel42.setLayout(new java.awt.BorderLayout());

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Chi tiết phiếu đổi");
        jPanel42.add(jLabel10, java.awt.BorderLayout.CENTER);

        jPanel41.add(jPanel42, java.awt.BorderLayout.PAGE_START);

        jPanel43.setPreferredSize(new java.awt.Dimension(400, 320));
        jPanel43.setLayout(new java.awt.BorderLayout());

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã thuốc mới", "Tên thuốc mới", "Số lượng mới", "Giá bán", "Thành tiền"
            }
        ));
        jScrollPane3.setViewportView(jTable2);
        if (jTable2.getColumnModel().getColumnCount() > 0) {
            jTable2.getColumnModel().getColumn(5).setResizable(false);
        }

        jPanel43.add(jScrollPane3, java.awt.BorderLayout.CENTER);

        jPanel41.add(jPanel43, java.awt.BorderLayout.CENTER);

        jPanel54.setBackground(new java.awt.Color(0, 120, 92));
        jPanel54.setPreferredSize(new java.awt.Dimension(400, 40));
        jPanel54.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 20, 5));

        btnXoa.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnXoa.setForeground(new java.awt.Color(255, 103, 102));
        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });
        jPanel54.add(btnXoa);

        jPanel41.add(jPanel54, java.awt.BorderLayout.PAGE_END);

        billPanel.add(jPanel41);

        billInfoPanel.setBackground(new java.awt.Color(255, 255, 255));
        billInfoPanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(81, 219, 185), 5, true));
        billInfoPanel.setPreferredSize(new java.awt.Dimension(400, 424));
        billInfoPanel.setLayout(new java.awt.BorderLayout());

        jPanel34.setBackground(new java.awt.Color(255, 255, 255));
        jPanel34.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 120, 92)));
        jPanel34.setForeground(new java.awt.Color(0, 120, 92));
        jPanel34.setMinimumSize(new java.awt.Dimension(100, 60));
        jPanel34.setPreferredSize(new java.awt.Dimension(400, 40));
        jPanel34.setLayout(new java.awt.BorderLayout());

        jLabel9.setBackground(new java.awt.Color(0, 120, 92));
        jLabel9.setFont(new java.awt.Font("Roboto Medium", 1, 24)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 120, 92));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Phiếu đổi");
        jPanel34.add(jLabel9, java.awt.BorderLayout.CENTER);

        billInfoPanel.add(jPanel34, java.awt.BorderLayout.NORTH);

        jPanel58.setBackground(new java.awt.Color(255, 255, 255));
        jPanel58.setMinimumSize(new java.awt.Dimension(400, 52));
        jPanel58.setPreferredSize(new java.awt.Dimension(400, 52));

        btnHuy.setBackground(new java.awt.Color(255, 102, 102));
        btnHuy.setFont(new java.awt.Font("Roboto Mono Medium", 0, 16)); // NOI18N
        btnHuy.setForeground(new java.awt.Color(255, 255, 255));
        btnHuy.setText("HỦY BỎ");
        btnHuy.setBorderPainted(false);
        btnHuy.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnHuy.setFocusPainted(false);
        btnHuy.setFocusable(false);
        btnHuy.setPreferredSize(new java.awt.Dimension(200, 40));
        btnHuy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHuyActionPerformed(evt);
            }
        });
        jPanel58.add(btnHuy);

        btnThanhToan.setBackground(new java.awt.Color(0, 204, 51));
        btnThanhToan.setFont(new java.awt.Font("Roboto Mono Medium", 0, 16)); // NOI18N
        btnThanhToan.setForeground(new java.awt.Color(255, 255, 255));
        btnThanhToan.setText("THANH TOÁN");
        btnThanhToan.setBorderPainted(false);
        btnThanhToan.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnThanhToan.setFocusPainted(false);
        btnThanhToan.setFocusable(false);
        btnThanhToan.setPreferredSize(new java.awt.Dimension(200, 40));
        btnThanhToan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThanhToanActionPerformed(evt);
            }
        });
        jPanel58.add(btnThanhToan);

        billInfoPanel.add(jPanel58, java.awt.BorderLayout.PAGE_END);

        jScrollPane2.setPreferredSize(new java.awt.Dimension(400, 200));

        jPanel35.setBackground(new java.awt.Color(255, 255, 255));
        jPanel35.setPreferredSize(new java.awt.Dimension(400, 200));
        jPanel35.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 5, 4));

        jPanel36.setBackground(new java.awt.Color(255, 255, 255));
        jPanel36.setPreferredSize(new java.awt.Dimension(440, 270));
        jPanel36.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setMinimumSize(new java.awt.Dimension(335, 40));
        jPanel7.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 0));

        jLabel11.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jLabel11.setText("Phiếu đổi:");
        jLabel11.setPreferredSize(new java.awt.Dimension(90, 40));
        jPanel7.add(jLabel11);

        txtMaHoaDonDoi.setEditable(false);
        txtMaHoaDonDoi.setFont(new java.awt.Font("Roboto Mono", 1, 14)); // NOI18N
        txtMaHoaDonDoi.setFocusable(false);
        txtMaHoaDonDoi.setPreferredSize(new java.awt.Dimension(90, 40));
        jPanel7.add(txtMaHoaDonDoi);

        jLabel21.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jLabel21.setText("Hóa đơn:");
        jLabel21.setPreferredSize(new java.awt.Dimension(60, 40));
        jPanel7.add(jLabel21);

        txtMaHD.setPreferredSize(new java.awt.Dimension(90, 40));
        jPanel7.add(txtMaHD);

        btnAddHoaDon.setIcon(new FlatSVGIcon("./icon/search.svg"));
        btnAddHoaDon.setBorderPainted(false);
        btnAddHoaDon.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAddHoaDon.setFocusPainted(false);
        btnAddHoaDon.setFocusable(false);
        btnAddHoaDon.setPreferredSize(new java.awt.Dimension(40, 40));
        btnAddHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddHoaDonActionPerformed(evt);
            }
        });
        jPanel7.add(btnAddHoaDon);

        jPanel36.add(jPanel7);

        jPanel37.setBackground(new java.awt.Color(255, 255, 255));
        jPanel37.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 0));

        jLabel8.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jLabel8.setText("Số điện thoại:");
        jLabel8.setPreferredSize(new java.awt.Dimension(120, 40));
        jPanel37.add(jLabel8);

        txtSdtKH.setBackground(new java.awt.Color(242, 242, 242));
        txtSdtKH.setPreferredSize(new java.awt.Dimension(200, 40));
        txtSdtKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSdtKHActionPerformed(evt);
            }
        });
        jPanel37.add(txtSdtKH);

        jPanel36.add(jPanel37);

        jPanel38.setBackground(new java.awt.Color(255, 255, 255));
        jPanel38.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 0));

        jLabel12.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jLabel12.setText("Tên khách hàng:");
        jLabel12.setMaximumSize(new java.awt.Dimension(44, 40));
        jLabel12.setPreferredSize(new java.awt.Dimension(120, 40));
        jPanel38.add(jLabel12);

        txtTenKH.setEditable(false);
        txtTenKH.setFont(new java.awt.Font("Roboto Mono", 1, 14)); // NOI18N
        txtTenKH.setFocusable(false);
        txtTenKH.setPreferredSize(new java.awt.Dimension(200, 40));
        txtTenKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTenKHActionPerformed(evt);
            }
        });
        jPanel38.add(txtTenKH);

        txtGioiTinh.setEditable(false);
        txtGioiTinh.setFont(new java.awt.Font("Roboto Mono", 1, 14)); // NOI18N
        txtGioiTinh.setFocusable(false);
        jPanel38.add(txtGioiTinh);

        jPanel36.add(jPanel38);

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 0));

        jLabel19.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jLabel19.setText("Thời gian:");
        jLabel19.setPreferredSize(new java.awt.Dimension(120, 40));
        jPanel8.add(jLabel19);

        txtThoiGian.setEditable(false);
        txtThoiGian.setFont(new java.awt.Font("Roboto Mono", 1, 14)); // NOI18N
        txtThoiGian.setFocusable(false);
        txtThoiGian.setPreferredSize(new java.awt.Dimension(200, 40));
        jPanel8.add(txtThoiGian);

        jPanel36.add(jPanel8);

        jPanel59.setBackground(new java.awt.Color(255, 255, 255));
        jPanel59.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 0));

        jLabel20.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jLabel20.setText("Nhân viên:");
        jLabel20.setPreferredSize(new java.awt.Dimension(120, 40));
        jPanel59.add(jLabel20);

        txtNV.setPreferredSize(new java.awt.Dimension(200, 40));
        jPanel59.add(txtNV);

        btnSearchNV.setIcon(new FlatSVGIcon("./icon/search.svg"));
        btnSearchNV.setBorderPainted(false);
        btnSearchNV.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSearchNV.setFocusPainted(false);
        btnSearchNV.setFocusable(false);
        btnSearchNV.setPreferredSize(new java.awt.Dimension(40, 40));
        btnSearchNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchNVActionPerformed(evt);
            }
        });
        jPanel59.add(btnSearchNV);

        jPanel36.add(jPanel59);

        jPanel60.setBackground(new java.awt.Color(255, 255, 255));
        jPanel60.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 0));

        jLabel22.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jLabel22.setText("Lý do:");
        jLabel22.setPreferredSize(new java.awt.Dimension(120, 40));
        jPanel60.add(jLabel22);

        txtLyDo.setPreferredSize(new java.awt.Dimension(200, 40));
        txtLyDo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLyDoActionPerformed(evt);
            }
        });
        jPanel60.add(txtLyDo);

        jPanel36.add(jPanel60);

        jPanel35.add(jPanel36);

        jSeparator4.setPreferredSize(new java.awt.Dimension(400, 3));
        jPanel35.add(jSeparator4);

        jScrollPane5.setMinimumSize(new java.awt.Dimension(440, 150));
        jScrollPane5.setPreferredSize(new java.awt.Dimension(440, 150));

        jPanel39.setBackground(new java.awt.Color(255, 255, 255));
        jPanel39.setMinimumSize(new java.awt.Dimension(440, 150));
        jPanel39.setPreferredSize(new java.awt.Dimension(440, 150));
        jPanel39.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jPanel40.setBackground(new java.awt.Color(255, 255, 255));
        jPanel40.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 0));

        jLabel7.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 51, 0));
        jLabel7.setText("Tổng hóa đơn:");
        jLabel7.setPreferredSize(new java.awt.Dimension(120, 40));
        jPanel40.add(jLabel7);

        txtTong.setEditable(false);
        txtTong.setFont(new java.awt.Font("Roboto Mono Medium", 0, 14)); // NOI18N
        txtTong.setForeground(new java.awt.Color(255, 51, 0));
        txtTong.setFocusable(false);
        txtTong.setPreferredSize(new java.awt.Dimension(200, 40));
        jPanel40.add(txtTong);

        jPanel39.add(jPanel40);

        jPanel55.setBackground(new java.awt.Color(255, 255, 255));
        jPanel55.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 0));

        jLabel13.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jLabel13.setText("Tiền khách đưa:");
        jLabel13.setPreferredSize(new java.awt.Dimension(120, 40));
        jPanel55.add(jLabel13);

        txtTienKhachDua.setPreferredSize(new java.awt.Dimension(200, 40));
        txtTienKhachDua.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTienKhachDuaKeyReleased(evt);
            }
        });
        jPanel55.add(txtTienKhachDua);

        jPanel39.add(jPanel55);

        jPanel56.setBackground(new java.awt.Color(255, 255, 255));
        jPanel56.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 0));

        jLabel18.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jLabel18.setText("Tiền thừa:");
        jLabel18.setPreferredSize(new java.awt.Dimension(120, 40));
        jPanel56.add(jLabel18);

        txtTienThua.setEditable(false);
        txtTienThua.setFont(new java.awt.Font("Roboto Mono Medium", 0, 14)); // NOI18N
        txtTienThua.setFocusable(false);
        txtTienThua.setPreferredSize(new java.awt.Dimension(200, 40));
        jPanel56.add(txtTienThua);

        jPanel39.add(jPanel56);

        jScrollPane5.setViewportView(jPanel39);

        jPanel35.add(jScrollPane5);

        jScrollPane2.setViewportView(jPanel35);

        billInfoPanel.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        billPanel.add(billInfoPanel);

        add(billPanel);
    }// </editor-fold>//GEN-END:initComponents

    private void txtTenKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTenKHActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTenKHActionPerformed

    private void txtLyDoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLyDoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtLyDoActionPerformed

    private void btnAddHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddHoaDonActionPerformed
            String maHD = txtMaHD.getText().trim(); // Lấy mã hóa đơn từ ô nhập

            if (maHD.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Vui lòng nhập mã hóa đơn!");
                    return;
            }

            // Lấy thông tin hóa đơn từ DAO (giả sử DAO.HoaDonDAO.getHoaDonById trả về đối
            // tượng HoaDon)
            HoaDon hoaDon = DAO.HoaDonDAO.getHoaDonByMaHD(maHD);
            if (hoaDon == null) {
                    JOptionPane.showMessageDialog(this, "Không tìm thấy hóa đơn với mã: " + maHD);
                    return;
            }

            // Hiển thị thông tin khách hàng
            KhachHang kh = hoaDon.getKhachHang(); // Giả sử hóa đơn có liên kết với khách hàng
            if (kh != null) {
                    txtTenKH.setText(kh.getHoTen());
                    txtSdtKH.setText(kh.getSdt());
                    txtGioiTinh.setText(kh.getGioiTinh());
                    maKH = kh.getId();
            }

            // Gọi DAO để lấy danh sách chi tiết hóa đơn từ database
            List<ChiTietHoaDon> danhSachChiTiet = DAO.ChiTietHoaDonDAO.getChiTietByHoaDonId(maHD);

            if (danhSachChiTiet.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Không tìm thấy chi tiết hóa đơn với mã: " + maHD);
                    return;
            }

            // Xóa dữ liệu cũ trên bảng
            DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
            model.setRowCount(0);

            // Đổ dữ liệu mới vào bảng
            for (ChiTietHoaDon chiTiet : danhSachChiTiet) {
                    model.addRow(new Object[] {
                                    maHD,
                                    chiTiet.getIdThuoc(),
                                    chiTiet.getThuoc(),
                                    chiTiet.getSoLuong(),
                                    chiTiet.getDonGia(),
                                    chiTiet.getThanhTien()
                    });
            }

            tinhTongTienHoaDon();
            JOptionPane.showMessageDialog(this, "Đã tải thông tin hóa đơn thành công!");
    }//GEN-LAST:event_btnAddHoaDonActionPerformed

        private void txtSoLuongActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txtSoLuongActionPerformed
                // TODO add your handling code here:
        }// GEN-LAST:event_txtSoLuongActionPerformed

        private void btnHuyActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnHuyActionPerformed
                try {
                        // Tạo đối tượng frmHoaDonCapNhat
                        frmPhieuDoiCapNhat formCapNhat = new frmPhieuDoiCapNhat();

                        // Lấy đối tượng Main (parent frame)
                        Main parentFrame = (Main) SwingUtilities.getWindowAncestor(this);

                        // Gọi phương thức replaceMainPanel để thay thế nội dung trong mainPanel
                        parentFrame.replaceMainPanel(formCapNhat);

                } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this,
                                        "Không thể quay lại form cập nhật hóa đơn: " + ex.getMessage(),
                                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
        }// GEN-LAST:event_btnHuyActionPerformed

        private void txtTienKhachDuaKeyReleased(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_txtTienKhachDuaKeyReleased
                try {
                        // Lấy tổng tiền từ ô txtTong
                        double tongTien = Double.parseDouble(txtTong.getText().replace(",", "")); // Đảm bảo là số
                        // Lấy tiền khách đưa từ ô txtTienKhachDua
                        double tienKhachDua = Double.parseDouble(txtTienKhachDua.getText().replace(",", "")); // Đảm bảo
                                                                                                              // là số

                        // Tính tiền thừa
                        double tienThua = tienKhachDua - tongTien;

                        // Hiển thị tiền thừa vào txtTienThua
                        txtTienThua.setText(String.format("%.0f", tienThua)); // Hiển thị dưới dạng số nguyên
                } catch (NumberFormatException e) {
                        // Nếu có lỗi trong việc chuyển đổi, đảm bảo không có lỗi hiển thị
                        txtTienThua.setText("");
                }
        }// GEN-LAST:event_txtTienKhachDuaKeyReleased

        private void btnThanhToanActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnThanhToanActionPerformed
                try {
                        // 1. Kiểm tra bảng chi tiết
                        DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
                        if (model.getRowCount() == 0) {
                                JOptionPane.showMessageDialog(this, "Vui lòng thêm sản phẩm vào phiếu đổi.",
                                                "Thông báo", JOptionPane.WARNING_MESSAGE);
                                return;
                        }

                        // 2. Kiểm tra các trường cần thiết
                        if (txtTenKH.getText().trim().isEmpty() || txtTienKhachDua.getText().trim().isEmpty()
                                        || txtMaHD.getText().trim().isEmpty()) {
                                JOptionPane.showMessageDialog(this,
                                                "Vui lòng nhập đầy đủ thông tin khách hàng, hóa đơn gốc và tiền khách đưa.",
                                                "Thông báo", JOptionPane.WARNING_MESSAGE);
                                return;
                        }

                        // 3. Kiểm tra tiền khách đưa đủ chưa
                        double tongTien = Double.parseDouble(txtTong.getText().replace(",", ""));
                        double tienKhachDua = Double.parseDouble(txtTienKhachDua.getText().replace(",", ""));
                        if (tienKhachDua < tongTien) {
                                JOptionPane.showMessageDialog(this, "Tiền khách đưa không đủ.", "Thông báo",
                                                JOptionPane.WARNING_MESSAGE);
                                return;
                        }
                        txtTienThua.setText(String.format("%,.0f", tienKhachDua - tongTien));

                        // 4. Kiểm tra mã khách hàng và nhân viên
                        if (maKH == null || maNV == null) {
                                JOptionPane.showMessageDialog(this,
                                                "Vui lòng tìm kiếm khách hàng và nhân viên trước khi thanh toán!",
                                                "Thông báo", JOptionPane.WARNING_MESSAGE);
                                return;
                        }

                        // 5. Chuẩn bị dữ liệu phiếu đổi
                        String maPD = PhieuDoiDAO.taoMaHoaDonDoi();
                        String thoiGian = getCurrentTime();
                        String maHD = txtMaHD.getText().trim();

                        PhieuDoi phieuDoi = new PhieuDoi();
                        phieuDoi.setId(maPD);
                        phieuDoi.setMaHD(maHD);
                        phieuDoi.setNgayLap(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(thoiGian));
                        phieuDoi.setIdKhachHang(maKH);
                        phieuDoi.setIdNhanVien(maNV);
                        phieuDoi.setLyDo(txtLyDo.getText().trim());

                        // 6. Lấy chi tiết phiếu đổi
                        List<ChiTietPhieuDoi> chiTietList = layChiTietHoaDonDoiTuBang(maPD, maHD);
                        if (chiTietList.isEmpty()) {
                                JOptionPane.showMessageDialog(this, "Không lấy được chi tiết phiếu đổi.", "Lỗi",
                                                JOptionPane.ERROR_MESSAGE);
                                return;
                        }
                        phieuDoi.setChiTietHoaDonDoi(chiTietList);

                        // 7. Lưu phiếu đổi + cập nhật kho
                        if (PhieuDoiDAO.them(phieuDoi)) {
                                JOptionPane.showMessageDialog(this, "Thanh toán phiếu đổi thành công!", "Thông báo",
                                                JOptionPane.INFORMATION_MESSAGE);
                                resetForm();
                        } else {
                                JOptionPane.showMessageDialog(this, "Không thể lưu phiếu đổi!", "Lỗi",
                                                JOptionPane.ERROR_MESSAGE);
                        }

                } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, "Lỗi khi thanh toán: " + e.getMessage(), "Lỗi",
                                        JOptionPane.ERROR_MESSAGE);
                        e.printStackTrace();
                }
        }// GEN-LAST:event_btnThanhToanActionPerformed

        private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnThemActionPerformed
                int selectedRow = jTable1.getSelectedRow(); // Lấy dòng đã chọn từ bảng jTable1
                if (selectedRow != -1) { // Kiểm tra xem có dòng nào được chọn không
                        // Lấy dữ liệu từ dòng đã chọn
                        String maThuoc = jTable1.getValueAt(selectedRow, 0).toString(); // Mã thuốc
                        String tenThuoc = jTable1.getValueAt(selectedRow, 1).toString(); // Tên thuốc
                        double donGia = Double.parseDouble(jTable1.getValueAt(selectedRow, 4).toString()); // Giá bán
                        int soLuong = Integer.parseInt(txtSoLuong.getText().trim()); // Số lượng từ ô nhập

                        // Tính thành tiền
                        double thanhTien = donGia * soLuong;

                        // Lấy model của bảng chi tiết hóa đơn (jTable2)
                        DefaultTableModel chiTietModel = (DefaultTableModel) jTable2.getModel();

                        // Kiểm tra nếu thuốc đã có trong bảng chi tiết hóa đơn
                        boolean daTonTai = false;
                        int rowIndex = -1;
                        for (int i = 0; i < chiTietModel.getRowCount(); i++) {
                                if (chiTietModel.getValueAt(i, 1).toString().equals(maThuoc)) {
                                        daTonTai = true;
                                        rowIndex = i;
                                        break;
                                }
                        }

                        if (daTonTai) {
                                // Nếu thuốc đã có trong bảng, cập nhật số lượng và thành tiền
                                int soLuongCu = Integer.parseInt(chiTietModel.getValueAt(rowIndex, 3).toString());
                                soLuongCu += soLuong; // Cập nhật số lượng mới
                                chiTietModel.setValueAt(soLuongCu, rowIndex, 3); // Cập nhật số lượng trong bảng

                                // Cập nhật lại thành tiền
                                thanhTien = soLuongCu * donGia;
                                chiTietModel.setValueAt(thanhTien, rowIndex, 5); // Cập nhật thành tiền
                        } else {
                                // Nếu thuốc chưa có trong bảng, thêm mới
                                int stt = chiTietModel.getRowCount() + 1; // Số thứ tự (STT)
                                chiTietModel.addRow(new Object[] {
                                                stt, // STT
                                                maThuoc, // Mã thuốc
                                                tenThuoc, // Tên thuốc
                                                soLuong, // Số lượng
                                                donGia, // Giá bán
                                                thanhTien // Thành tiền
                                });
                        }

                        // Cập nhật tổng tiền hóa đơn
                        tinhTongTienHoaDon();
                } else {
                        // Nếu không có dòng nào được chọn, hiển thị thông báo
                        JOptionPane.showMessageDialog(null, "Vui lòng chọn thuốc để thêm vào hóa đơn.");
                }
        }// GEN-LAST:event_btnThemActionPerformed

        private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnXoaActionPerformed
                // Lấy model của bảng chi tiết hóa đơn (jTable2)
                DefaultTableModel chiTietModel = (DefaultTableModel) jTable2.getModel();

                // Lấy chỉ số dòng được chọn
                int selectedRow = jTable2.getSelectedRow();

                // Kiểm tra nếu có dòng được chọn trong bảng
                if (selectedRow != -1) {
                        // Xóa dòng đã chọn
                        chiTietModel.removeRow(selectedRow);

                        // Cập nhật tổng tiền hóa đơn sau khi xóa dòng
                        tinhTongTienHoaDon();
                } else {
                        // Nếu không có dòng nào được chọn, hiển thị thông báo
                        JOptionPane.showMessageDialog(null, "Vui lòng chọn dòng cần xóa.");
                }
        }// GEN-LAST:event_btnXoaActionPerformed

        private void btnSearchNVActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnSearchNVActionPerformed
        String manv = txtNV.getText().trim();

        if (!manv.isEmpty()) {
            NhanVien nv = NhanVienDAO.getNhanVienByMaNV(manv);

            if (nv != null) {
                // Hiển thị tên và chức vụ nhân viên
                txtNV.setText(nv.getHoTen());

                // Lưu maNV vào biến tạm để sử dụng sau khi thanh toán
                maNV = nv.getId();
            } else {
                JOptionPane.showMessageDialog(this, "Không tìm thấy nhân viên!", "Thông báo",
                        JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã nhân viên!", "Thông báo",
                    JOptionPane.WARNING_MESSAGE);
        }
        }// GEN-LAST:event_btnSearchNVActionPerformed


        private void txtThuocKeyReleased(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_txtThuocKeyReleased
                String keyword = txtThuoc.getText().trim(); // Lấy từ khóa tìm kiếm từ ô txtThuoc

                if (keyword.isEmpty()) {
                        // Nếu ô tìm kiếm rỗng, tải lại toàn bộ dữ liệu trong bảng
                        loadDataToTable(); // Gọi lại hàm để tải toàn bộ dữ liệu vào bảng
                } else {
                        // Nếu có từ khóa, tìm kiếm và hiển thị kết quả
                        searchThuoc(keyword);
                }
        }// GEN-LAST:event_txtThuocKeyReleased

        private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnTimKiemActionPerformed
        }// GEN-LAST:event_btnTimKiemActionPerformed

        // 1. Cập nhật phương thức initEvent() hiện có
        private void initEvent() {

                // Sự kiện tìm kiếm theo mã thuốc (giữ nguyên)
                btnTimKiem.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                String maThuoc = txtThuoc.getText().trim();

                                if (!maThuoc.isEmpty()) {
                                        Thuoc thuoc = ThuocDAO.getThuocByMaThuoc(maThuoc);
                                        if (thuoc != null) {
                                                DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
                                                model.setRowCount(0);
                                                model.addRow(new Object[] {
                                                                1,
                                                                thuoc.getId(),
                                                                thuoc.getTenThuoc(),
                                                                thuoc.getDanhMuc() != null ? thuoc.getDanhMuc().getTen()
                                                                                : "",
                                                                thuoc.getDonViTinh() != null
                                                                                ? thuoc.getDonViTinh().getTen()
                                                                                : "",
                                                                thuoc.getXuatXu() != null ? thuoc.getXuatXu().getTen()
                                                                                : "",
                                                                thuoc.getSoLuong(),
                                                                thuoc.getGiaNhap()
                                                });
                                        } else {
                                                JOptionPane.showMessageDialog(null,
                                                                "Không tìm thấy thuốc với mã: " + maThuoc);
                                        }
                                } else {
                                        JOptionPane.showMessageDialog(null, "Vui lòng nhập mã thuốc cần tìm");
                                }
                        }
                });
                setupCategoryComboBox();
                // THÊM MỚI: Sự kiện lọc dữ liệu khi thay đổi giá trị ComboBox
                final boolean[] hasFiltered = { false };

                // Thêm listener cho ComboBox
                jComboBox1.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                if (jComboBox1.getSelectedItem() != null) {
                                        String selectedCategory = jComboBox1.getSelectedItem().toString();
                                        System.out.println("Đã chọn danh mục: " + selectedCategory);

                                        // Nếu chọn "Tất cả", hiển thị tất cả dữ liệu
                                        if (selectedCategory.equals("Tất cả")) {
                                                // Chỉ reset bảng nếu trước đó đã thực hiện lọc
                                                if (hasFiltered[0]) {
                                                        System.out.println("Reset bảng và tải lại tất cả dữ liệu");
                                                        resetAndLoadAllData();
                                                        hasFiltered[0] = false;
                                                }
                                                return;
                                        }

                                        // Đánh dấu đã lọc
                                        hasFiltered[0] = true;

                                        // Lọc dữ liệu theo danh mục đã chọn
                                        filterByCategory(selectedCategory);
                                }
                        }
                });
        }

        private void resetAndLoadAllData() {
                // Lấy model hiện tại của bảng
                DefaultTableModel model = (DefaultTableModel) jTable1.getModel();

                // Xóa tất cả dữ liệu hiện tại
                model.setRowCount(0);

                // Thêm thông báo đang tải
                model.addRow(new Object[] { "Đang tải lại tất cả dữ liệu...", "", "", "", "", "", "", "", "", "" });
                jTable1.repaint();

                // Tải lại tất cả dữ liệu
                SwingWorker<List<Thuoc>, Void> worker = new SwingWorker<List<Thuoc>, Void>() {
                        @Override
                        protected List<Thuoc> doInBackground() throws Exception {
                                return ThuocDAO.getAllThuoc();
                        }

                        @Override
                        protected void done() {
                                try {
                                        List<Thuoc> thuocList = get();
                                        System.out.println("Đã tải lại: " + (thuocList != null ? thuocList.size() : 0)
                                                        + " dòng");

                                        SwingUtilities.invokeLater(() -> {
                                                model.setRowCount(0);

                                                if (thuocList == null || thuocList.isEmpty()) {
                                                        model.addRow(new Object[] { "Không có dữ liệu", "", "", "", "",
                                                                        "", "", "", "", "" });
                                                        return;
                                                }

                                                // Thêm dữ liệu vào bảng
                                                for (Thuoc thuoc : thuocList) {
                                                        model.addRow(new Object[] {
                                                                        thuoc.getId(),
                                                                        thuoc.getTenThuoc(),
                                                                        thuoc.getThanhPhan(),
                                                                        thuoc.getGiaNhap(),
                                                                        thuoc.getDonGia(),
                                                                        thuoc.getHsd(),
                                                                        thuoc.getDanhMuc() != null
                                                                                        ? thuoc.getDanhMuc().getTen()
                                                                                        : null,
                                                                        thuoc.getDonViTinh() != null
                                                                                        ? thuoc.getDonViTinh().getTen()
                                                                                        : null,
                                                                        thuoc.getXuatXu() != null
                                                                                        ? thuoc.getXuatXu().getTen()
                                                                                        : null,
                                                                        thuoc.getSoLuong()
                                                        });
                                                }

                                                // Cập nhật giao diện
                                                model.fireTableDataChanged();
                                                jTable1.revalidate();
                                                jTable1.repaint();
                                                jScrollPane1.revalidate();
                                        });
                                } catch (Exception e) {
                                        e.printStackTrace();
                                        SwingUtilities.invokeLater(() -> {
                                                model.setRowCount(0);
                                                model.addRow(new Object[] { "Lỗi: " + e.getMessage(), "", "", "", "",
                                                                "", "", "", "", "" });
                                        });
                                }
                        }
                };

                worker.execute();

        }

        // Phương thức để lấy chi tiết hóa đơn từ bảng hiển thị
private List<ChiTietPhieuDoi> layChiTietHoaDonDoiTuBang(String maPD, String maHD) {
    List<ChiTietPhieuDoi> chiTietList = new ArrayList<>();
    DefaultTableModel model = (DefaultTableModel) jTable2.getModel();

    // Lấy danh sách thuốc cũ từ mã hóa đơn
    List<ChiTietHoaDon> thuocCuList = ChiTietHoaDonDAO.getChiTietByHoaDonId(maHD);
    if (thuocCuList.size() != model.getRowCount()) {
        JOptionPane.showMessageDialog(this, "Số lượng thuốc cũ không khớp với thuốc mới.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        return new ArrayList<>();
    }

    for (int i = 0; i < model.getRowCount(); i++) {
        // Thuốc cũ từ DB
        ChiTietHoaDon thuocCu = thuocCuList.get(i);
        String maThuocCu = thuocCu.getIdThuoc();
        int soLuongCu = thuocCu.getSoLuong();
        double donGiaCu = thuocCu.getDonGia();

        // Thuốc mới từ bảng
        String maThuocMoi = model.getValueAt(i, 1).toString();
        int soLuongMoi = Integer.parseInt(model.getValueAt(i, 3).toString());
        double donGiaMoi = Double.parseDouble(model.getValueAt(i, 4).toString());
        double tongTien = Double.parseDouble(model.getValueAt(i, 5).toString());

        ChiTietPhieuDoi chiTiet = new ChiTietPhieuDoi();
        chiTiet.setMaPD(maPD);
        chiTiet.setMaThuocCu(maThuocCu);
        chiTiet.setSoLuongCu(soLuongCu);
        chiTiet.setDonGiaCu(donGiaCu);
        chiTiet.setMaThuocMoi(maThuocMoi);
        chiTiet.setSoLuongMoi(soLuongMoi);
        chiTiet.setDonGiaMoi(donGiaMoi);
        chiTiet.setTongTien(tongTien);

        chiTietList.add(chiTiet);
    }

    return chiTietList;
}


        // Phương thức reset form sau khi thanh toán
        private void resetForm() {
                // Xóa thông tin khách hàng
                txtTenKH.setText("");
                txtSdtKH.setText("");
                txtNV.setText("");

                // Xóa thông tin thanh toán
                txtTienKhachDua.setText("");
                txtTienThua.setText("");
                txtTong.setText("0");

                // Xóa bảng chi tiết hóa đơn
                DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
                model.setRowCount(0);
        }

        private void txtSdtKHActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txtSdtKHActionPerformed
                // TODO add your handling code here:
        }// GEN-LAST:event_txtSdtKHActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel JPanhThuoc;
    private javax.swing.JPanel billInfoPanel;
    private javax.swing.JPanel billPanel;
    private javax.swing.JButton btnAddHoaDon;
    private javax.swing.JButton btnHuy;
    private javax.swing.JButton btnSearchNV;
    private javax.swing.JButton btnThanhToan;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JOptionPane jOptionPane1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel34;
    private javax.swing.JPanel jPanel35;
    private javax.swing.JPanel jPanel36;
    private javax.swing.JPanel jPanel37;
    private javax.swing.JPanel jPanel38;
    private javax.swing.JPanel jPanel39;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel40;
    private javax.swing.JPanel jPanel41;
    private javax.swing.JPanel jPanel42;
    private javax.swing.JPanel jPanel43;
    private javax.swing.JPanel jPanel44;
    private javax.swing.JPanel jPanel45;
    private javax.swing.JPanel jPanel46;
    private javax.swing.JPanel jPanel47;
    private javax.swing.JPanel jPanel48;
    private javax.swing.JPanel jPanel49;
    private javax.swing.JPanel jPanel50;
    private javax.swing.JPanel jPanel51;
    private javax.swing.JPanel jPanel52;
    private javax.swing.JPanel jPanel53;
    private javax.swing.JPanel jPanel54;
    private javax.swing.JPanel jPanel55;
    private javax.swing.JPanel jPanel56;
    private javax.swing.JPanel jPanel57;
    private javax.swing.JPanel jPanel58;
    private javax.swing.JPanel jPanel59;
    private javax.swing.JPanel jPanel60;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private java.awt.Label label1;
    private java.awt.Label label2;
    private javax.swing.JLabel lblAnh;
    private javax.swing.JTextField txtDonGia;
    private javax.swing.JTextField txtGioiTinh;
    private javax.swing.JTextField txtLyDo;
    private javax.swing.JTextField txtMaHD;
    private javax.swing.JTextField txtMaHoaDonDoi;
    private javax.swing.JTextField txtMaThuoc;
    private javax.swing.JTextField txtNV;
    private javax.swing.JTextField txtSdtKH;
    private javax.swing.JTextField txtSoLuong;
    private javax.swing.JTextField txtTenKH;
    private javax.swing.JTextField txtTenThuoc;
    private javax.swing.JTextArea txtThanhPhan;
    private javax.swing.JTextField txtThoiGian;
    private javax.swing.JTextField txtThuoc;
    private javax.swing.JTextField txtTienKhachDua;
    private javax.swing.JTextField txtTienThua;
    private javax.swing.JTextField txtTong;
    // End of variables declaration//GEN-END:variables

        public void refreshData() {
                startIndex = 0; // Reset to first page
                loadDataToTable(); // Reload data
        }
}

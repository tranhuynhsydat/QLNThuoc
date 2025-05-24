/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package GUI.page;
import DAO.DanhMucDAO;
import DAO.NhaCungCapDAO;
import DAO.NhanVienDAO;
import DAO.PhieuNhapDAO;
import DAO.ThuocDAO;
import Entity.ChiTietPhieuNhap;
import Entity.DanhMuc;
import Entity.NhaCungCap;
import Entity.NhanVien;
import Entity.PhieuNhap;
import Entity.Thuoc;
import GUI.Main;
import GUI.form.formThemNCC;
import GUI.page.frmPhieuNhapCapNhat;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
//import controller.ChiTietPhieuNhapController;
//import controller.NhaCungCapController;
//import controller.PhieuNhapController;
//import controller.ThuocController;
//import entities.ChiTietPhieuNhap;
//import entities.NhaCungCap;
//import entities.NhanVien;
//import entities.PhieuNhap;
//import entities.TaiKhoan;
//import entities.Thuoc;
//import gui.MainLayout;
//import gui.dialog.CreateNhaCungCapDialog;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
//import utils.Formatter;
//import utils.MessageDialog;
//import utils.RandomGenerator;
//import utils.TableSorter;
//import utils.Validation;
//import utils.WritePDF;
/**
 *
 * @author Admin
 */
public class frmPhieuNhapThem extends javax.swing.JPanel {
    private String maNCC;  // Mã khách hàng
    private String maNV;  // Mã nhân viên

    /**
     * Creates new form frmPhieuNhapCapNhat
     */
    private int startIndex = 0; // Track the starting index for data loading
    private JLabel lblThongTinThuoc;
    private boolean isSearching = false;

    public frmPhieuNhapThem() {
        initComponents();
        configureTable();
        initEvent();
        loadDataToTable();
        addTableSelectionListener();
        // Tạo và hiển thị mã hóa đơn mới
        String maPhieuNhap = PhieuNhapDAO.taoMaPhieuNhap(); // Lấy mã hóa đơn mới
        txtMaPhieuNhap.setText(maPhieuNhap); // Gán vào ô txtMaPhieuNhap

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
        LocalDateTime now = LocalDateTime.now();  // Lấy thời gian hiện tại
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"); // Định dạng thời gian
        return now.format(formatter); // Trả về thời gian đã định dạng
    }

    private void configureTable() {
        // Ngăn không cho phép người dùng chỉnh sửa bảng
        jTable1.setDefaultEditor(Object.class, null);  // Điều này vô hiệu hóa khả năng chỉnh sửa của bất kỳ ô nào trong bảng.

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
                List<Thuoc> thuocList = ThuocDAO.getThuocBatch(startIndex, 13);  // startIndex là chỉ mục bắt đầu
                SwingUtilities.invokeLater(() -> {
                    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();

                    // Chỉ thêm dữ liệu mới vào bảng, không xóa dữ liệu cũ
                    for (Thuoc thuoc : thuocList) {
                        model.addRow(new Object[]{
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
                                ? jTable1.getValueAt(selectedRow, 0).toString() : "";

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
                                lblAnh.setIcon(icon);  // Cập nhật ảnh vào lblAnh
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
    
    private void filterByCategory(String category) {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);
        model.addRow(new Object[]{"Đang lọc...", "", "", "", "", "", "", "", "", ""});

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
                        model.addRow(new Object[]{"Không có dữ liệu", "", "", "", "", "", "", "", "", ""});
                        return;
                    }

                    // Thêm dữ liệu đã lọc vào bảng theo cấu trúc mới
                    for (Thuoc thuoc : filteredList) {
                        model.addRow(new Object[]{
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
        List<Thuoc> thuocList = ThuocDAO.getThuocByKeyword(keyword);  // Tìm thuốc qua ThuocDAO

        // Lấy model của bảng
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0); // Xóa hết dữ liệu cũ trong bảng

        // Thêm các kết quả tìm kiếm vào bảng
        for (Thuoc thuoc : thuocList) {
            model.addRow(new Object[]{
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
    // Phương thức thêm thuốc vào chi tiết hóa đơn
    private void themThuocVaoChiTietPhieuNhap(String maThuoc, String tenThuoc, int soLuong, double giaNhap) {
        try {
            // Lấy model của bảng chi tiết hóa đơn
            DefaultTableModel chiTietModel = (DefaultTableModel) jTable2.getModel();

            // Kiểm tra thuốc đã có trong hóa đơn chưa
            boolean daTonTai = false;
            int rowIndex = -1;

            for (int i = 0; i < chiTietModel.getRowCount(); i++) {
                Object maThuocObj = chiTietModel.getValueAt(i, 1);
                if (maThuocObj != null && maThuoc.equals(maThuocObj.toString())) {
                    daTonTai = true;
                    rowIndex = i;
                    break;
                }
            }

            if (daTonTai) {
                // Nếu thuốc đã tồn tại, cập nhật số lượng
                Object soLuongObj = chiTietModel.getValueAt(rowIndex, 3);
                int soLuongCu = 0;

                if (soLuongObj != null) {
                    try {
                        soLuongCu = Integer.parseInt(soLuongObj.toString());
                    } catch (NumberFormatException e) {
                        System.err.println("Lỗi chuyển đổi số lượng: " + e.getMessage());
                    }
                }

                int soLuongMoi = soLuongCu + soLuong;

                // Cập nhật số lượng mới
                chiTietModel.setValueAt(soLuongMoi, rowIndex, 3);

                // Tính và cập nhật thành tiền mới
                double thanhTien = soLuongMoi * giaNhap;
                chiTietModel.setValueAt(thanhTien, rowIndex, 5);
            } else {
                // Nếu thuốc chưa có trong hóa đơn, thêm mới
                int stt = chiTietModel.getRowCount() + 1;
                double thanhTien = soLuong * giaNhap;

                // Thêm dòng mới vào bảng
                chiTietModel.addRow(new Object[]{
                    stt, // STT
                    maThuoc, // Mã thuốc
                    tenThuoc, // Tên thuốc
                    soLuong, // Số lượng
                    giaNhap, // Giá bán
                    thanhTien // Thành tiền
                });
            }

            // Cập nhật tổng tiền hóa đơn
            tinhTongTienPhieuNhap();

        } catch (Exception e) {
            System.err.println("Lỗi khi thêm thuốc vào chi tiết hóa đơn: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Lỗi khi thêm thuốc vào chi tiết hóa đơn: " + e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    // Phương thức tính tổng tiền phiếu nhập

    private void tinhTongTienPhieuNhap() {
        DefaultTableModel chiTietModel = (DefaultTableModel) jTable2.getModel();
        double tongTien = 0.0;

        // Lặp qua tất cả các dòng trong bảng để tính tổng thành tiền
        for (int i = 0; i < chiTietModel.getRowCount(); i++) {
            // Lấy giá trị cột "Thành tiền" (giả sử là cột thứ 5, index = 4)
            Object thanhTienObj = chiTietModel.getValueAt(i, 5);  // Cột Thành tiền (index = 5)

            if (thanhTienObj != null) {
                try {
                    double thanhTien = Double.parseDouble(thanhTienObj.toString());  // Chuyển giá trị thành số thực
                    tongTien += thanhTien;  // Cộng dồn vào tổng
                } catch (NumberFormatException e) {
                    System.err.println("Lỗi khi chuyển đổi thành tiền ở dòng " + i + ": " + e.getMessage());
                }
            }
        }

        // Cập nhật tổng tiền vào txtTong
        txtTong.setText(String.format("%.0f", tongTien));  // Hiển thị tổng tiền, định dạng theo số nguyên
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

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
        jLabel9 = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        txtMaThuoc = new javax.swing.JTextField();
        jPanel14 = new javax.swing.JPanel();
        jPanel19 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jPanel18 = new javax.swing.JPanel();
        txtTenThuoc = new javax.swing.JTextField();
        jPanel15 = new javax.swing.JPanel();
        jPanel31 = new javax.swing.JPanel();
        jPanel24 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jPanel22 = new javax.swing.JPanel();
        jPanel32 = new javax.swing.JPanel();
        jPanel33 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        txtThanhPhan = new javax.swing.JTextArea();
        jPanel34 = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        jPanel21 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jPanel35 = new javax.swing.JPanel();
        txtDonGia = new javax.swing.JTextField();
        jPanel36 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel53 = new javax.swing.JPanel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jPanel54 = new javax.swing.JPanel();
        txtThuoc = new javax.swing.JTextField();
        btnTimKiem = new javax.swing.JButton();
        jPanel60 = new javax.swing.JPanel();
        label2 = new java.awt.Label();
        txtSoLuong = new javax.swing.JTextField();
        jPanel57 = new javax.swing.JPanel();
        btnThem = new javax.swing.JButton();
        jPanel40 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        billPanel = new javax.swing.JPanel();
        jPanel41 = new javax.swing.JPanel();
        jPanel42 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jPanel43 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jPanel44 = new javax.swing.JPanel();
        btnXoa = new javax.swing.JButton();
        billInfoPanel = new javax.swing.JPanel();
        jPanel46 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jPanel58 = new javax.swing.JPanel();
        btnHuy = new javax.swing.JButton();
        btnThanhToan = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jPanel47 = new javax.swing.JPanel();
        jPanel48 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        txtMaPhieuNhap = new javax.swing.JTextField();
        jPanel49 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        txtSdtNCC = new javax.swing.JTextField();
        btnSearchNCC = new javax.swing.JButton();
        btnAddNCC = new javax.swing.JButton();
        jPanel50 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        txtTenNCC = new javax.swing.JTextField();
        jPanel8 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        txtThoiGian = new javax.swing.JTextField();
        jPanel59 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        txtNV = new javax.swing.JTextField();
        btnSearchNV = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JSeparator();
        jPanel51 = new javax.swing.JPanel();
        jPanel52 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        txtTong = new javax.swing.JTextField();
        jPanel55 = new javax.swing.JPanel();
        jPanel56 = new javax.swing.JPanel();

        setBackground(new java.awt.Color(81, 219, 185));
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
                .addContainerGap(11, Short.MAX_VALUE))
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

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Mã thuốc:");
        jPanel30.add(jLabel9, java.awt.BorderLayout.CENTER);

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

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("Tên thuốc:");
        jPanel19.add(jLabel13, java.awt.BorderLayout.CENTER);

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

        jPanel31.setLayout(new java.awt.BorderLayout());

        jPanel24.setPreferredSize(new java.awt.Dimension(200, 40));
        jPanel24.setLayout(new java.awt.BorderLayout());

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("Thành phần:");
        jPanel24.add(jLabel15, java.awt.BorderLayout.CENTER);

        jPanel31.add(jPanel24, java.awt.BorderLayout.LINE_START);

        jPanel15.add(jPanel31, java.awt.BorderLayout.NORTH);

        jPanel22.setMinimumSize(new java.awt.Dimension(100, 100));
        jPanel22.setName(""); // NOI18N
        jPanel22.setPreferredSize(new java.awt.Dimension(200, 142));
        jPanel22.setLayout(new javax.swing.BoxLayout(jPanel22, javax.swing.BoxLayout.LINE_AXIS));

        jPanel32.setPreferredSize(new java.awt.Dimension(60, 142));

        javax.swing.GroupLayout jPanel32Layout = new javax.swing.GroupLayout(jPanel32);
        jPanel32.setLayout(jPanel32Layout);
        jPanel32Layout.setHorizontalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 60, Short.MAX_VALUE)
        );
        jPanel32Layout.setVerticalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 142, Short.MAX_VALUE)
        );

        jPanel22.add(jPanel32);

        jPanel33.setLayout(new java.awt.BorderLayout());

        jScrollPane4.setMinimumSize(new java.awt.Dimension(234, 66));
        jScrollPane4.setPreferredSize(new java.awt.Dimension(234, 66));

        txtThanhPhan.setColumns(20);
        txtThanhPhan.setRows(5);
        jScrollPane4.setViewportView(txtThanhPhan);

        jPanel33.add(jScrollPane4, java.awt.BorderLayout.CENTER);

        jPanel22.add(jPanel33);

        jPanel34.setPreferredSize(new java.awt.Dimension(60, 142));

        javax.swing.GroupLayout jPanel34Layout = new javax.swing.GroupLayout(jPanel34);
        jPanel34.setLayout(jPanel34Layout);
        jPanel34Layout.setHorizontalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 60, Short.MAX_VALUE)
        );
        jPanel34Layout.setVerticalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 142, Short.MAX_VALUE)
        );

        jPanel22.add(jPanel34);

        jPanel15.add(jPanel22, java.awt.BorderLayout.PAGE_END);

        jPanel12.add(jPanel15);

        jPanel16.setEnabled(false);
        jPanel16.setPreferredSize(new java.awt.Dimension(400, 30));
        jPanel16.setLayout(new java.awt.BorderLayout());

        jPanel21.setMinimumSize(new java.awt.Dimension(100, 100));
        jPanel21.setName(""); // NOI18N
        jPanel21.setPreferredSize(new java.awt.Dimension(200, 30));
        jPanel21.setLayout(new java.awt.BorderLayout());

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("Đơn giá:");
        jPanel21.add(jLabel16, java.awt.BorderLayout.CENTER);

        jPanel16.add(jPanel21, java.awt.BorderLayout.LINE_START);

        jPanel35.setMinimumSize(new java.awt.Dimension(300, 50));
        jPanel35.setPreferredSize(new java.awt.Dimension(300, 50));
        jPanel35.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 5));

        txtDonGia.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtDonGia.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 120, 92)));
        txtDonGia.setMinimumSize(new java.awt.Dimension(164, 27));
        txtDonGia.setName(""); // NOI18N
        txtDonGia.setPreferredSize(new java.awt.Dimension(150, 25));
        jPanel35.add(txtDonGia);

        jPanel16.add(jPanel35, java.awt.BorderLayout.CENTER);

        jPanel12.add(jPanel16);

        jPanel29.add(jPanel12);

        jPanel9.add(jPanel29, java.awt.BorderLayout.CENTER);

        jPanel36.setBackground(new java.awt.Color(0, 120, 92));
        jPanel36.setPreferredSize(new java.awt.Dimension(521, 40));

        javax.swing.GroupLayout jPanel36Layout = new javax.swing.GroupLayout(jPanel36);
        jPanel36.setLayout(jPanel36Layout);
        jPanel36Layout.setHorizontalGroup(
            jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 774, Short.MAX_VALUE)
        );
        jPanel36Layout.setVerticalGroup(
            jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );

        jPanel9.add(jPanel36, java.awt.BorderLayout.SOUTH);

        jPanel1.add(jPanel9);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(81, 219, 185), 5));
        jPanel4.setMaximumSize(new java.awt.Dimension(2147483647, 2147483647));
        jPanel4.setMinimumSize(new java.awt.Dimension(420, 50));
        jPanel4.setPreferredSize(new java.awt.Dimension(420, 50));
        jPanel4.setLayout(new javax.swing.BoxLayout(jPanel4, javax.swing.BoxLayout.LINE_AXIS));

        jPanel53.setMaximumSize(new java.awt.Dimension(120, 50));
        jPanel53.setMinimumSize(new java.awt.Dimension(120, 50));
        jPanel53.setPreferredSize(new java.awt.Dimension(120, 50));
        jPanel53.setRequestFocusEnabled(false);
        jPanel53.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 10, 10));

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 120, 92)));
        jComboBox1.setMaximumSize(new java.awt.Dimension(72, 32));
        jComboBox1.setMinimumSize(new java.awt.Dimension(72, 32));
        jComboBox1.setPreferredSize(new java.awt.Dimension(72, 32));
        jPanel53.add(jComboBox1);

        jPanel4.add(jPanel53);

        jPanel54.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jPanel54.setMaximumSize(new java.awt.Dimension(240, 50));
        jPanel54.setMinimumSize(new java.awt.Dimension(240, 50));
        jPanel54.setPreferredSize(new java.awt.Dimension(240, 50));
        jPanel54.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 10, 10));

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
        jPanel54.add(txtThuoc);

        btnTimKiem.setText("Tìm");
        btnTimKiem.setPreferredSize(new java.awt.Dimension(60, 32));
        btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemActionPerformed(evt);
            }
        });
        jPanel54.add(btnTimKiem);

        jPanel4.add(jPanel54);

        jPanel60.setFocusTraversalPolicyProvider(true);
        jPanel60.setMinimumSize(new java.awt.Dimension(70, 50));
        jPanel60.setPreferredSize(new java.awt.Dimension(70, 50));
        jPanel60.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 10, 10));

        label2.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        label2.setText("Số lượng:");
        jPanel60.add(label2);

        txtSoLuong.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        txtSoLuong.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtSoLuong.setText("1");
        txtSoLuong.setPreferredSize(new java.awt.Dimension(70, 32));
        txtSoLuong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSoLuongActionPerformed(evt);
            }
        });
        jPanel60.add(txtSoLuong);

        jPanel4.add(jPanel60);

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

        jPanel40.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(81, 219, 185), 5));
        jPanel40.setMinimumSize(new java.awt.Dimension(470, 352));
        jPanel40.setPreferredSize(new java.awt.Dimension(470, 362));
        jPanel40.setLayout(new java.awt.BorderLayout());

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

        jPanel40.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel40);

        add(jPanel1);

        billPanel.setBackground(new java.awt.Color(230, 245, 245));
        billPanel.setMinimumSize(new java.awt.Dimension(400, 624));
        billPanel.setPreferredSize(new java.awt.Dimension(400, 624));
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
        jLabel10.setText("Chi tiết phiếu nhập");
        jPanel42.add(jLabel10, java.awt.BorderLayout.CENTER);

        jPanel41.add(jPanel42, java.awt.BorderLayout.PAGE_START);

        jPanel43.setPreferredSize(new java.awt.Dimension(400, 320));
        jPanel43.setLayout(new java.awt.BorderLayout());

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã thuốc", "Tên thuốc", "Số lượng", "Giá nhập", "Thành tiền"
            }
        ));
        jScrollPane3.setViewportView(jTable2);

        jPanel43.add(jScrollPane3, java.awt.BorderLayout.CENTER);

        jPanel41.add(jPanel43, java.awt.BorderLayout.CENTER);

        jPanel44.setBackground(new java.awt.Color(0, 120, 92));
        jPanel44.setPreferredSize(new java.awt.Dimension(400, 40));
        jPanel44.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 20, 5));

        btnXoa.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnXoa.setForeground(new java.awt.Color(255, 103, 102));
        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });
        jPanel44.add(btnXoa);

        jPanel41.add(jPanel44, java.awt.BorderLayout.PAGE_END);

        billPanel.add(jPanel41);

        billInfoPanel.setBackground(new java.awt.Color(255, 255, 255));
        billInfoPanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(81, 219, 185), 5, true));
        billInfoPanel.setPreferredSize(new java.awt.Dimension(400, 424));
        billInfoPanel.setLayout(new java.awt.BorderLayout());

        jPanel46.setBackground(new java.awt.Color(255, 255, 255));
        jPanel46.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 120, 92)));
        jPanel46.setForeground(new java.awt.Color(0, 120, 92));
        jPanel46.setMinimumSize(new java.awt.Dimension(100, 60));
        jPanel46.setPreferredSize(new java.awt.Dimension(400, 40));
        jPanel46.setLayout(new java.awt.BorderLayout());

        jLabel12.setBackground(new java.awt.Color(0, 120, 92));
        jLabel12.setFont(new java.awt.Font("Roboto Medium", 1, 24)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 120, 92));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("Phiếu nhập");
        jPanel46.add(jLabel12, java.awt.BorderLayout.CENTER);

        billInfoPanel.add(jPanel46, java.awt.BorderLayout.NORTH);

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

        jPanel47.setBackground(new java.awt.Color(255, 255, 255));
        jPanel47.setPreferredSize(new java.awt.Dimension(400, 200));
        jPanel47.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 5, 8));

        jPanel48.setBackground(new java.awt.Color(255, 255, 255));
        jPanel48.setPreferredSize(new java.awt.Dimension(440, 230));
        jPanel48.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 0));

        jLabel11.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jLabel11.setText("Mã phiếu nhập: ");
        jLabel11.setPreferredSize(new java.awt.Dimension(120, 40));
        jPanel7.add(jLabel11);

        txtMaPhieuNhap.setEditable(false);
        txtMaPhieuNhap.setFont(new java.awt.Font("Roboto Mono", 1, 14)); // NOI18N
        txtMaPhieuNhap.setText("Z2NX8CN1A");
        txtMaPhieuNhap.setFocusable(false);
        txtMaPhieuNhap.setPreferredSize(new java.awt.Dimension(200, 40));
        jPanel7.add(txtMaPhieuNhap);

        jPanel48.add(jPanel7);

        jPanel49.setBackground(new java.awt.Color(255, 255, 255));
        jPanel49.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 0));

        jLabel8.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jLabel8.setText("Số điện thoại:");
        jLabel8.setPreferredSize(new java.awt.Dimension(120, 40));
        jPanel49.add(jLabel8);

        txtSdtNCC.setPreferredSize(new java.awt.Dimension(200, 40));
        txtSdtNCC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSdtNCCActionPerformed(evt);
            }
        });
        jPanel49.add(txtSdtNCC);

        btnSearchNCC.setIcon(new FlatSVGIcon("./icon/search.svg"));
        btnSearchNCC.setBorderPainted(false);
        btnSearchNCC.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSearchNCC.setFocusPainted(false);
        btnSearchNCC.setFocusable(false);
        btnSearchNCC.setPreferredSize(new java.awt.Dimension(40, 40));
        btnSearchNCC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchNCCActionPerformed(evt);
            }
        });
        jPanel49.add(btnSearchNCC);

        btnAddNCC.setIcon(new FlatSVGIcon("./icon/add-customer.svg"));
        btnAddNCC.setBorderPainted(false);
        btnAddNCC.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAddNCC.setFocusPainted(false);
        btnAddNCC.setFocusable(false);
        btnAddNCC.setPreferredSize(new java.awt.Dimension(40, 40));
        btnAddNCC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddNCCActionPerformed(evt);
            }
        });
        jPanel49.add(btnAddNCC);

        jPanel48.add(jPanel49);

        jPanel50.setBackground(new java.awt.Color(255, 255, 255));
        jPanel50.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 0));

        jLabel14.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jLabel14.setText("Tên nhà cung cấp:");
        jLabel14.setMaximumSize(new java.awt.Dimension(44, 40));
        jLabel14.setPreferredSize(new java.awt.Dimension(120, 40));
        jPanel50.add(jLabel14);

        txtTenNCC.setPreferredSize(new java.awt.Dimension(200, 40));
        jPanel50.add(txtTenNCC);

        jPanel48.add(jPanel50);

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

        jPanel48.add(jPanel8);

        jPanel59.setBackground(new java.awt.Color(255, 255, 255));
        jPanel59.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 0));

        jLabel20.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jLabel20.setText("Nhân viên:");
        jLabel20.setPreferredSize(new java.awt.Dimension(120, 40));
        jPanel59.add(jLabel20);

        txtNV.setPreferredSize(new java.awt.Dimension(200, 40));
        txtNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNVActionPerformed(evt);
            }
        });
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

        jPanel48.add(jPanel59);

        jPanel47.add(jPanel48);

        jSeparator4.setPreferredSize(new java.awt.Dimension(400, 3));
        jPanel47.add(jSeparator4);

        jPanel51.setBackground(new java.awt.Color(255, 255, 255));
        jPanel51.setPreferredSize(new java.awt.Dimension(440, 50));
        jPanel51.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jPanel52.setBackground(new java.awt.Color(255, 255, 255));
        jPanel52.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 0));

        jLabel7.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 51, 0));
        jLabel7.setText("Tổng hóa đơn:");
        jLabel7.setPreferredSize(new java.awt.Dimension(120, 40));
        jPanel52.add(jLabel7);

        txtTong.setEditable(false);
        txtTong.setFont(new java.awt.Font("Roboto Mono Medium", 0, 14)); // NOI18N
        txtTong.setForeground(new java.awt.Color(255, 51, 0));
        txtTong.setText("1000000");
        txtTong.setFocusable(false);
        txtTong.setPreferredSize(new java.awt.Dimension(200, 40));
        jPanel52.add(txtTong);

        jPanel51.add(jPanel52);

        jPanel55.setBackground(new java.awt.Color(255, 255, 255));
        jPanel55.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 0));
        jPanel51.add(jPanel55);

        jPanel56.setBackground(new java.awt.Color(255, 255, 255));
        jPanel56.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 0));
        jPanel51.add(jPanel56);

        jPanel47.add(jPanel51);

        jScrollPane2.setViewportView(jPanel47);

        billInfoPanel.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        billPanel.add(billInfoPanel);

        add(billPanel);
    }// </editor-fold>//GEN-END:initComponents
    private void btnThanhToanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThanhToanActionPerformed
        try {
            // Kiểm tra tính hợp lệ của phiếu nhập
            DefaultTableModel chiTietModel = (DefaultTableModel) jTable2.getModel();
            if (chiTietModel.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "Vui lòng thêm sản phẩm vào phiếu nhập trước khi thanh toán",
                        "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Kiểm tra thông tin khách hàng và tiền khách đưa
            if (txtTenNCC.getText().trim().isEmpty() ) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin nhà cung cấp",
                        "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            double tongTien = Double.parseDouble(txtTong.getText().replace(",", ""));
            

            
            // Kiểm tra đã có mã khách hàng và nhân viên chưa
            if (maNCC == null || maNV == null) {
                JOptionPane.showMessageDialog(this, "Vui lòng tìm kiếm nhà cung cấp và nhân viên trước khi thanh toán!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Tạo mã hóa đơn mới và lấy thời gian hiện tại
            String maPN = PhieuNhapDAO.taoMaPhieuNhap();  // Tạo mã hóa đơn mới
            String thoiGian = getCurrentTime();  // Lấy thời gian hiện tại

            // Tạo đối tượng hóa đơn với mã khách hàng và nhân viên đã tìm được
            PhieuNhap phieuNhap = new PhieuNhap(maPN, new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(thoiGian), maNV, maNCC);

            // Lấy chi tiết hóa đơn từ bảng (jTable2)
            List<ChiTietPhieuNhap> chiTietList = layChiTietPhieuNhapTuBang(maPN);
            phieuNhap.setChiTietPhieuNhap(chiTietList);

            // Kiểm tra xem có chi tiết hóa đơn không
            if (phieuNhap.getChiTietPhieuNhap().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Không có sản phẩm trong phiếu nhập", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Lưu Phiếu nhập vào cơ sở dữ liệu
                        boolean isAdded =PhieuNhapDAO.them(phieuNhap);

                        if (isAdded) {
                                JOptionPane.showMessageDialog(this, "Thanh toán thành công", "Thông báo",
                                                JOptionPane.INFORMATION_MESSAGE);
                                resetForm();

                                // Quay lại giao diện cập nhật hóa đơn
                                frmPhieuNhapCapNhat formCapNhat = new frmPhieuNhapCapNhat();
                                Main parentFrame = (Main) SwingUtilities.getWindowAncestor(this);
                                parentFrame.replaceMainPanel(formCapNhat);
                        } else {
                                JOptionPane.showMessageDialog(this, "Không thể lưu hóa đơn", "Lỗi",
                                                JOptionPane.ERROR_MESSAGE);
                        }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi thanh toán: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnThanhToanActionPerformed

    private void btnHuyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHuyActionPerformed
        try {
            // Tạo đối tượng frmHoaDonCapNhat
            frmPhieuNhapCapNhat formCapNhat = new frmPhieuNhapCapNhat();

            // Lấy đối tượng Main (parent frame)
            Main parentFrame = (Main) SwingUtilities.getWindowAncestor(this);

            // Gọi phương thức replaceMainPanel để thay thế nội dung trong mainPanel
            parentFrame.replaceMainPanel(formCapNhat);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Không thể quay lại form cập nhật phiếu nhập: " + ex.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }                  
    }//GEN-LAST:event_btnHuyActionPerformed

    private void btnSearchNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchNVActionPerformed
        String input = txtNV.getText().trim();

        if (!input.isEmpty()) {
    // Tìm nhân viên theo mã, tên hoặc số điện thoại
            NhanVien nv = NhanVienDAO.getNhanVienByHoTen(input);

        if (nv != null) {
        // Hiển thị tên nhân viên
            txtNV.setText(nv.getHoTen());

        // Lưu mã nhân viên để sử dụng sau khi thanh toán
        maNV = nv.getId();
            } else {
                JOptionPane.showMessageDialog(this, "Không tìm thấy nhân viên!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập thông tin nhân viên!", "Thông báo", JOptionPane.WARNING_MESSAGE);
        }

    }//GEN-LAST:event_btnSearchNVActionPerformed

    private void txtNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNVActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNVActionPerformed

    private void btnAddNCCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddNCCActionPerformed
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        formThemNCC dialog = new formThemNCC(parentFrame, true);  // Mở formThemNV
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }//GEN-LAST:event_btnAddNCCActionPerformed

    private void btnSearchNCCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchNCCActionPerformed
        String sdt = txtSdtNCC.getText().trim();

        if (!sdt.isEmpty()) {
            NhaCungCap ncc = NhaCungCapDAO.getNhaCungCapBySdt(sdt);

            if (ncc != null) {
                // Hiển thị tên và giới tính khách hàng
                txtTenNCC.setText(ncc.getTenNhaCungCap());

                // Lưu maKH vào biến tạm để sử dụng sau khi thanh toán
                maNCC = ncc.getId();
            } else {
                JOptionPane.showMessageDialog(this, "Không tìm thấy nhà cung cấp!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập số điện thoại nhà cung cấp!", "Thông báo", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnSearchNCCActionPerformed

    private void txtSdtNCCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSdtNCCActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSdtNCCActionPerformed

    private void txtThuocKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtThuocKeyReleased
        String keyword = txtThuoc.getText().trim();  // Lấy từ khóa tìm kiếm từ ô txtThuoc

        if (keyword.isEmpty()) {
            // Nếu ô tìm kiếm rỗng, tải lại toàn bộ dữ liệu trong bảng
            loadDataToTable();  // Gọi lại hàm để tải toàn bộ dữ liệu vào bảng
        } else {
            // Nếu có từ khóa, tìm kiếm và hiển thị kết quả
            searchThuoc(keyword);
        }
    }//GEN-LAST:event_txtThuocKeyReleased
    // GEN-FIRST:event_btnTimKiemActionPerformed
    // GEN-LAST:event_btnTimKiemActionPerformed


    private void setThoiGianThuc() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        txtThoiGian.setText(now.format(formatter));
    }

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
                        model.addRow(new Object[]{
                            1,
                            thuoc.getId(),
                            thuoc.getTenThuoc(),
                            thuoc.getDanhMuc() != null ? thuoc.getDanhMuc().getTen() : "",
                            thuoc.getDonViTinh() != null ? thuoc.getDonViTinh().getTen() : "",
                            thuoc.getXuatXu() != null ? thuoc.getXuatXu().getTen() : "",
                            thuoc.getSoLuong(),
                            thuoc.getGiaNhap()
                        });
                    } else {
                        JOptionPane.showMessageDialog(null, "Không tìm thấy thuốc với mã: " + maThuoc);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập mã thuốc cần tìm");
                }
            }
        });
        setupCategoryComboBox();
        // THÊM MỚI: Sự kiện lọc dữ liệu khi thay đổi giá trị ComboBox
        final boolean[] hasFiltered = {false};

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
        model.addRow(new Object[]{"Đang tải lại tất cả dữ liệu...", "", "", "", "", "", "", "", "", ""});
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
                    System.out.println("Đã tải lại: " + (thuocList != null ? thuocList.size() : 0) + " dòng");

                    SwingUtilities.invokeLater(() -> {
                        model.setRowCount(0);

                        if (thuocList == null || thuocList.isEmpty()) {
                            model.addRow(new Object[]{"Không có dữ liệu", "", "", "", "", "", "", "", "", ""});
                            return;
                        }

                        // Thêm dữ liệu vào bảng
                        for (Thuoc thuoc : thuocList) {
                            model.addRow(new Object[]{
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
                        model.addRow(new Object[]{"Lỗi: " + e.getMessage(), "", "", "", "", "", "", "", "", ""});
                    });
                }
            }
        };

        worker.execute();

    }

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnTimKiemActionPerformed

    private void txtSoLuongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSoLuongActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSoLuongActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        int selectedRow = jTable1.getSelectedRow();
        if (selectedRow != -1) {
            Object maThuocObj = jTable1.getValueAt(selectedRow, 0);
            Object tenThuocObj = jTable1.getValueAt(selectedRow, 1);
            Object giaNhapObj = jTable1.getValueAt(selectedRow, 3);
            String soLuongText = txtSoLuong.getText().trim();

            if (maThuocObj == null || tenThuocObj == null || giaNhapObj == null || soLuongText.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Một số trường dữ liệu bị thiếu hoặc chưa được nhập.");
                return;
            }

            String maThuoc = maThuocObj.toString();
            String tenThuoc = tenThuocObj.toString();
            double giaNhap;
            int soLuong;

            try {
                giaNhap = Double.parseDouble(giaNhapObj.toString());
                soLuong = Integer.parseInt(soLuongText);
                if (soLuong <= 0) {
                    JOptionPane.showMessageDialog(null, "Số lượng phải lớn hơn 0.");
                    return;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Sai định dạng số cho giá nhập hoặc số lượng.");
                return;
            }

            double thanhTien = giaNhap * soLuong;

            DefaultTableModel chiTietModel = (DefaultTableModel) jTable2.getModel();
            boolean daTonTai = false;
            int rowIndex = -1;

            for (int i = 0; i < chiTietModel.getRowCount(); i++) {
                Object value = chiTietModel.getValueAt(i, 1);
                if (value != null && value.toString().equals(maThuoc)) {
                    daTonTai = true;
                    rowIndex = i;
                    break;
                }
            }

            if (daTonTai) {
                int soLuongCu = Integer.parseInt(chiTietModel.getValueAt(rowIndex, 3).toString());
                soLuongCu += soLuong;
                chiTietModel.setValueAt(soLuongCu, rowIndex, 3);
                chiTietModel.setValueAt(soLuongCu * giaNhap, rowIndex, 5);
            } else {
                int stt = chiTietModel.getRowCount() + 1;
                chiTietModel.addRow(new Object[]{stt, maThuoc, tenThuoc, soLuong, giaNhap, thanhTien});
            }

            tinhTongTienPhieuNhap();
        } else {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn thuốc để thêm vào phiếu nhập.");
        }

    }//GEN-LAST:event_btnThemActionPerformed
    
    
    private void jTextField1ActionPerformed(ActionEvent evt) {
        // Thực hiện hành động khi người dùng nhấn Enter trong JTextField
        String text = txtThuoc.getText();
        System.out.println("Text field value: " + text);
    }
    
    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        // Lấy model của bảng chi tiết hóa đơn (jTable2)
        DefaultTableModel chiTietModel = (DefaultTableModel) jTable2.getModel();

        // Lấy chỉ số dòng được chọn
        int selectedRow = jTable2.getSelectedRow();

        // Kiểm tra nếu có dòng được chọn trong bảng
        if (selectedRow != -1) {
            // Xóa dòng đã chọn
            chiTietModel.removeRow(selectedRow);

            // Cập nhật tổng tiền hóa đơn sau khi xóa dòng
            tinhTongTienPhieuNhap();
        } else {
            // Nếu không có dòng nào được chọn, hiển thị thông báo
            JOptionPane.showMessageDialog(null, "Vui lòng chọn dòng cần xóa.");
        }
    }//GEN-LAST:event_btnXoaActionPerformed
    
    // Phương thức để lấy chi tiết hóa đơn từ bảng hiển thị
    private List<ChiTietPhieuNhap> layChiTietPhieuNhapTuBang(String maPN) {
        List<ChiTietPhieuNhap> chiTietList = new ArrayList<>();
        DefaultTableModel model = (DefaultTableModel) jTable2.getModel();

        for (int i = 0; i < model.getRowCount(); i++) {
            String maThuoc = model.getValueAt(i, 1).toString();
            String tenThuoc = model.getValueAt(i, 2).toString();
            int soLuong = Integer.parseInt(model.getValueAt(i, 3).toString());
            double giaNhap = Double.parseDouble(model.getValueAt(i, 4).toString());
            double thanhTien = Double.parseDouble(model.getValueAt(i, 5).toString());

            ChiTietPhieuNhap chiTiet = new ChiTietPhieuNhap(
                    maPN, maThuoc, tenThuoc, soLuong, giaNhap, thanhTien);

            chiTietList.add(chiTiet);
        }

        return chiTietList;
    }
    
    // Phương thức cập nhật số lượng tồn kho sau khi bán thuốc
    private boolean capNhatSoLuongTonKho(String maThuoc, int soLuongNhap) {
        try {
            // Lấy thông tin thuốc hiện tại
            // Thuoc thuoc = ThuocDAO.timThuocTheoMa(maThuoc);

            // Cập nhật số lượng tồn kho
            // int soLuongMoi = thuoc.getSoLuongTon() - soLuongBan;
            // thuoc.setSoLuongTon(soLuongMoi);
            // Lưu thông tin mới vào CSDL
            // return ThuocDAO.capNhatThuoc(thuoc);
            // Hoặc thực hiện trực tiếp:
            // return ThuocDAO.capNhatSoLuongTon(maThuoc, soLuongBan, false);
            // Phương thức này tùy thuộc vào cấu trúc ThuocDAO của bạn
            // Tạm thời return true để code biên dịch được
            return true;

        } catch (Exception e) {
            System.err.println("Lỗi khi cập nhật số lượng tồn kho: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    // Phương thức tạo mã hóa đơn mới
// Phương thức reset form sau khi thanh toán
    private void resetForm() {
        // Xóa thông tin khách hàng
        txtTenNCC.setText("");
        txtSdtNCC.setText("");
        txtNV.setText("");

        // Xóa thông tin thanh toán
        txtTong.setText("0");

        // Xóa bảng chi tiết hóa đơn
        DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
        model.setRowCount(0);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel JPanhThuoc;
    private javax.swing.JPanel billInfoPanel;
    private javax.swing.JPanel billPanel;
    private javax.swing.JButton btnAddNCC;
    private javax.swing.JButton btnHuy;
    private javax.swing.JButton btnSearchNCC;
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
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
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
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel32;
    private javax.swing.JPanel jPanel33;
    private javax.swing.JPanel jPanel34;
    private javax.swing.JPanel jPanel35;
    private javax.swing.JPanel jPanel36;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel40;
    private javax.swing.JPanel jPanel41;
    private javax.swing.JPanel jPanel42;
    private javax.swing.JPanel jPanel43;
    private javax.swing.JPanel jPanel44;
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
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private java.awt.Label label2;
    private javax.swing.JLabel lblAnh;
    private javax.swing.JTextField txtDonGia;
    private javax.swing.JTextField txtMaPhieuNhap;
    private javax.swing.JTextField txtMaThuoc;
    private javax.swing.JTextField txtNV;
    private javax.swing.JTextField txtSdtNCC;
    private javax.swing.JTextField txtSoLuong;
    private javax.swing.JTextField txtTenNCC;
    private javax.swing.JTextField txtTenThuoc;
    private javax.swing.JTextArea txtThanhPhan;
    private javax.swing.JTextField txtThoiGian;
    private javax.swing.JTextField txtThuoc;
    private javax.swing.JTextField txtTong;
    // End of variables declaration//GEN-END:variables
    public void refreshData() {
        startIndex = 0; // Reset to first page
        loadDataToTable(); // Reload data
    }
}

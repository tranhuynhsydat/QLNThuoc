/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package GUI.page;

import DAO.ChiTietHoaDonDAO;
import DAO.DanhMucDAO;
import DAO.HoaDonDAO;
import DAO.ThuocDAO;
import Entity.Thuoc;
import Entity.ChiTietHoaDon;
import Entity.DanhMuc;
import Entity.HoaDon;

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
import java.util.Date;
import javax.swing.DefaultComboBoxModel;
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
public class frmHoaDonThem extends javax.swing.JPanel {
    /**
     * Creates new form frmHoaDonCapNhat
     */
    private int startIndex = 0; // Track the starting index for data loading
    private JLabel lblThongTinThuoc;
    private boolean isSearching = false;
    public frmHoaDonThem() {
        initComponents();
        configureTable();
        initEvent();
        loadDataToTable();
        addTableSelectionListener();
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
// private void loadTableChiTietHoaDon(String maHoaDon) {
//     try {
//         // Lấy model của bảng chi tiết hóa đơn
//         DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
        
//         // Xóa dữ liệu cũ
//         model.setRowCount(0);
        
//         // Nếu là hóa đơn mới (maHoaDon == null), không cần tải dữ liệu
//         if (maHoaDon == null || maHoaDon.isEmpty()) {
//             return;
//         }
        
//         // Lấy danh sách chi tiết hóa đơn từ CSDL
//         List<ChiTietHoaDon> chiTietList = ChiTietHoaDonDAO.getChiTietByHoaDonId(maHoaDon);
        
//         // Kiểm tra danh sách trống
//         if (chiTietList == null || chiTietList.isEmpty()) {
//             System.out.println("Không có chi tiết hóa đơn cho mã: " + maHoaDon);
//             return;
//         }
        
//         // Thêm dữ liệu vào bảng
//         int stt = 1;
//         for (ChiTietHoaDon chiTiet : chiTietList) {
//             // Lấy thông tin chi tiết hóa đơn
//             String maThuoc = chiTiet.getIdThuoc();
//             String tenThuoc = chiTiet.getThuoc();
//             int soLuong = chiTiet.getSoLuong();
//             double donGia = chiTiet.getDonGia();
//             double thanhTien = chiTiet.getThanhTien();
            
//             // Thêm dòng mới vào bảng
//             model.addRow(new Object[]{
//                 stt,          // STT
//                 maThuoc,      // Mã thuốc
//                 tenThuoc,     // Tên thuốc
//                 soLuong,      // Số lượng
//                 donGia,       // Giá bán
//                 thanhTien     // Thành tiền
//             });
            
//             stt++;
//         }
        
//         // Tính tổng tiền hóa đơn
//         tinhTongTienHoaDon();
        
//     } catch (Exception e) {
//         System.err.println("Lỗi khi tải chi tiết hóa đơn: " + e.getMessage());
//         e.printStackTrace();
//         JOptionPane.showMessageDialog(this, "Lỗi khi tải chi tiết hóa đơn: " + e.getMessage(), 
//                                      "Lỗi", JOptionPane.ERROR_MESSAGE);
//     }
// }

    private void addTableSelectionListener() {
    // Thêm listener xử lý sự kiện khi chọn dòng trong bảng
    jTable1.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting()) { // Chỉ xử lý khi sự kiện chọn đã hoàn tất
                int selectedRow = jTable1.getSelectedRow();
                if (selectedRow != -1) {
                    // Lấy dữ liệu từ dòng đã chọn
                    String maThuoc = jTable1.getValueAt(selectedRow, 0) != null ? 
                                    jTable1.getValueAt(selectedRow, 0).toString() : "";
                    String tenThuoc = jTable1.getValueAt(selectedRow, 1) != null ?
                                     jTable1.getValueAt(selectedRow, 1).toString() : "";
                    String donGia = jTable1.getValueAt(selectedRow, 4) != null ?
                                  jTable1.getValueAt(selectedRow, 4).toString() : "";
                    
                    // Giả định lấy thành phần từ DAO
                    String thanhPhan = jTable1.getValueAt(selectedRow, 2) != null ?
                                  jTable1.getValueAt(selectedRow, 2).toString() : "";
                    // Cập nhật các trường văn bản với dữ liệu đã chọn
                    txtMaThuoc.setText(maThuoc);
                    txtTenThuoc.setText(tenThuoc);
                    txtThanhPhan.setText(thanhPhan);
                    txtDonGia.setText(donGia);
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
// 3. Thêm phương thức lọc theo danh mục - phải được đặt bên trong lớp, ngang hàng với các phương thức khác
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
                if (thuoc.getDanhMuc() != null && 
                    thuoc.getDanhMuc().getTen().equals(category)) {
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
                
                // Cập nhật giao diện
                jTable1.revalidate();
                jTable1.repaint();
            });
        }
    };
    
    worker.execute();
}
// Phương thức thêm thuốc vào chi tiết hóa đơn
private void themThuocVaoChiTietHoaDon(String maThuoc, String tenThuoc, int soLuong, double donGia) {
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
            double thanhTien = soLuongMoi * donGia;
            chiTietModel.setValueAt(thanhTien, rowIndex, 5);
        } else {
            // Nếu thuốc chưa có trong hóa đơn, thêm mới
            int stt = chiTietModel.getRowCount() + 1;
            double thanhTien = soLuong * donGia;

            // Thêm dòng mới vào bảng
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

    } catch (Exception e) {
        System.err.println("Lỗi khi thêm thuốc vào chi tiết hóa đơn: " + e.getMessage());
        e.printStackTrace();
        JOptionPane.showMessageDialog(this,
                "Lỗi khi thêm thuốc vào chi tiết hóa đơn: " + e.getMessage(),
                "Lỗi", JOptionPane.ERROR_MESSAGE);
    }
}
// Phương thức tính tổng tiền hóa đơn
private void tinhTongTienHoaDon() {
    DefaultTableModel chiTietModel = (DefaultTableModel) jTable2.getModel();
    double tongTien = 0.0;

    // Lấy số cột trong bảng để tránh lỗi IndexOutOfBounds
    int columnCount = chiTietModel.getColumnCount();

    // Xác định chỉ mục cột thành tiền
    int thanhTienColumnIndex = 4; // Chỉ mục mặc định, cần điều chỉnh

    // Đảm bảo chỉ mục không vượt quá số cột
    if (thanhTienColumnIndex >= columnCount) {
        System.err.println("Lỗi: Chỉ mục cột thành tiền (" + thanhTienColumnIndex +
                ") vượt quá số cột trong bảng (" + columnCount + ")");

        // Tìm chỉ mục cột dựa trên tên cột (nếu có)
        for (int i = 0; i < columnCount; i++) {
            String columnName = chiTietModel.getColumnName(i);
            if (columnName != null && columnName.toLowerCase().contains("thành tiền")) {
                thanhTienColumnIndex = i;
                break;
            }
        }

        // Nếu vẫn không tìm thấy, sử dụng cột cuối cùng
        if (thanhTienColumnIndex >= columnCount) {
            thanhTienColumnIndex = columnCount - 1;
        }

        System.out.println("Đã điều chỉnh chỉ mục cột thành tiền thành: " + thanhTienColumnIndex);
    }

    // Duyệt qua tất cả các dòng để tính tổng tiền
    for (int i = 0; i < chiTietModel.getRowCount(); i++) {
        try {
            // Kiểm tra thành tiền từ cột thành tiền hoặc tính toán từ số lượng và đơn giá
            double thanhTien = 0.0;

            // Nếu có cột thành tiền
            if (thanhTienColumnIndex < columnCount) {
                Object thanhTienObj = chiTietModel.getValueAt(i, thanhTienColumnIndex);
                if (thanhTienObj != null) {
                    try {
                        thanhTien = Double.parseDouble(thanhTienObj.toString());
                    } catch (NumberFormatException e) {
                        System.err.println("Lỗi chuyển đổi thành tiền ở dòng " + i + ": " + e.getMessage());

                        // Thử tính thành tiền từ số lượng và đơn giá
                        int soLuongColumnIndex = 3; // Giả sử cột số lượng là 3
                        int donGiaColumnIndex = 4; // Giả sử cột đơn giá là 4

                        if (soLuongColumnIndex < columnCount && donGiaColumnIndex < columnCount) {
                            Object soLuongObj = chiTietModel.getValueAt(i, soLuongColumnIndex);
                            Object donGiaObj = chiTietModel.getValueAt(i, donGiaColumnIndex);

                            if (soLuongObj != null && donGiaObj != null) {
                                try {
                                    int soLuong = Integer.parseInt(soLuongObj.toString());
                                    double donGia = Double.parseDouble(donGiaObj.toString());
                                    thanhTien = soLuong * donGia;
                                } catch (NumberFormatException ex) {
                                    System.err
                                            .println("Lỗi tính thành tiền từ số lượng và đơn giá: " + ex.getMessage());
                                }
                            }
                        }
                    }
                }
            } else {
                // Tính thành tiền từ số lượng và đơn giá nếu không có cột thành tiền
                int soLuongIndex = Math.min(3, columnCount - 1); // Lấy chỉ mục an toàn
                int donGiaIndex = Math.min(4, columnCount - 1); // Lấy chỉ mục an toàn

                Object soLuongObj = chiTietModel.getValueAt(i, soLuongIndex);
                Object donGiaObj = chiTietModel.getValueAt(i, donGiaIndex);

                if (soLuongObj != null && donGiaObj != null) {
                    try {
                        int soLuong = Integer.parseInt(soLuongObj.toString());
                        double donGia = Double.parseDouble(donGiaObj.toString());
                        thanhTien = soLuong * donGia;
                    } catch (NumberFormatException e) {
                        System.err.println("Lỗi tính thành tiền: " + e.getMessage());
                    }
                }
            }

            tongTien += thanhTien;
        } catch (Exception e) {
            System.err.println("Lỗi khi xử lý dòng " + i + ": " + e.getMessage());
        }
    }

    // Hiển thị tổng tiền lên trường tổng hóa đơn
    // if (txtTong != null) {
    //     // Sử dụng NumberFormat để định dạng tiền tệ
    //     NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
    //     txtTong.setText(currencyFormat.format(tongTien));
    // }
}

// Phương thức tính tiền thừa
private void tinhTienThua() {
    try {
        double tongTien = Double.parseDouble(txtTong.getText().replace(",", ""));
        double tienKhachDua = Double.parseDouble(txtTienKhachDua.getText().replace(",", ""));
        
        double tienThua = tienKhachDua - tongTien;
        
        // Hiển thị tiền thừa
        txtTienThua.setText(String.format("%.0f", tienThua));
        
        // Đổi màu nếu tiền thừa âm (khách đưa thiếu)
//        if (tienThua < 0) {
//            txtTienThua.setForeground(Color.RED);
//        } else {
//            txtTienThua.setForeground(Color.BLACK);
//        }
    } catch (NumberFormatException e) {
        txtTienThua.setText("");
    }
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
        jTextField1 = new javax.swing.JTextField();
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
        jButton2 = new javax.swing.JButton();
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
        txtMaHoaDon = new javax.swing.JTextField();
        jPanel37 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        txtSdtKH = new javax.swing.JTextField();
        btnSearchKH = new javax.swing.JButton();
        btnAddCustomer = new javax.swing.JButton();
        jPanel38 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        txtTenKH = new javax.swing.JTextField();
        cboxGioiTinhKH = new javax.swing.JComboBox<>();
        jPanel8 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        txtThoiGian = new javax.swing.JTextField();
        jPanel59 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        txtNV = new javax.swing.JTextField();
        btnSearchKH1 = new javax.swing.JButton();
        btnAddCustomer1 = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JSeparator();
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

        JPanhThuoc.setBackground(new java.awt.Color(255, 255, 255));
        JPanhThuoc.setMinimumSize(new java.awt.Dimension(262, 262));

        javax.swing.GroupLayout JPanhThuocLayout = new javax.swing.GroupLayout(JPanhThuoc);
        JPanhThuoc.setLayout(JPanhThuocLayout);
        JPanhThuocLayout.setHorizontalGroup(
            JPanhThuocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 262, Short.MAX_VALUE)
        );
        JPanhThuocLayout.setVerticalGroup(
            JPanhThuocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 304, Short.MAX_VALUE)
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

        jPanel50.setPreferredSize(new java.awt.Dimension(90, 69));
        jPanel50.setRequestFocusEnabled(false);
        jPanel50.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 10, 10));

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 120, 92)));
        jComboBox1.setPreferredSize(new java.awt.Dimension(130, 32));
        jPanel50.add(jComboBox1);

        jPanel4.add(jPanel50);

        jPanel51.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jPanel51.setMinimumSize(new java.awt.Dimension(100, 69));
        jPanel51.setPreferredSize(new java.awt.Dimension(150, 69));
        jPanel51.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 10, 10));

        jTextField1.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        jTextField1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField1.setActionCommand("<Not Set>");
        jTextField1.setMinimumSize(new java.awt.Dimension(140, 32));
        jTextField1.setPreferredSize(new java.awt.Dimension(140, 32));
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        jPanel51.add(jTextField1);

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
        jPanel52.setPreferredSize(new java.awt.Dimension(70, 69));
        jPanel52.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 10, 10));

        label2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        label2.setText("Số lượng:");
        jPanel52.add(label2);

        txtSoLuong.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        txtSoLuong.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtSoLuong.setPreferredSize(new java.awt.Dimension(70, 32));
        txtSoLuong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSoLuongActionPerformed(evt);
            }
        });
        jPanel52.add(txtSoLuong);

        jPanel4.add(jPanel52);

        jPanel57.setPreferredSize(new java.awt.Dimension(80, 100));

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
        jLabel10.setText("Chi tiết hóa đơn");
        jPanel42.add(jLabel10, java.awt.BorderLayout.CENTER);

        jPanel41.add(jPanel42, java.awt.BorderLayout.PAGE_START);

        jPanel43.setPreferredSize(new java.awt.Dimension(400, 320));
        jPanel43.setLayout(new java.awt.BorderLayout());

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã thuốc", "Tên thuốc", "Số lượng", "Giá bán", "Thành tiền"
            }
        ));
        jScrollPane3.setViewportView(jTable2);

        jPanel43.add(jScrollPane3, java.awt.BorderLayout.CENTER);

        jPanel41.add(jPanel43, java.awt.BorderLayout.CENTER);

        jPanel54.setBackground(new java.awt.Color(0, 120, 92));
        jPanel54.setPreferredSize(new java.awt.Dimension(400, 40));
        jPanel54.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 20, 5));

        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 103, 102));
        jButton2.setText("Xóa");
        jPanel54.add(jButton2);

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
        jLabel9.setText("Hóa đơn");
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
        jPanel35.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 5, 8));

        jPanel36.setBackground(new java.awt.Color(255, 255, 255));
        jPanel36.setPreferredSize(new java.awt.Dimension(440, 230));
        jPanel36.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 0));

        jLabel11.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jLabel11.setText("Mã hóa đơn ");
        jLabel11.setPreferredSize(new java.awt.Dimension(120, 40));
        jPanel7.add(jLabel11);

        txtMaHoaDon.setEditable(false);
        txtMaHoaDon.setFont(new java.awt.Font("Roboto Mono", 1, 14)); // NOI18N
        txtMaHoaDon.setText("Z2NX8CN1A");
        txtMaHoaDon.setFocusable(false);
        txtMaHoaDon.setPreferredSize(new java.awt.Dimension(200, 40));
        jPanel7.add(txtMaHoaDon);

        jPanel36.add(jPanel7);

        jPanel37.setBackground(new java.awt.Color(255, 255, 255));
        jPanel37.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 0));

        jLabel8.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jLabel8.setText("Số điện thoại:");
        jLabel8.setPreferredSize(new java.awt.Dimension(120, 40));
        jPanel37.add(jLabel8);

        txtSdtKH.setPreferredSize(new java.awt.Dimension(200, 40));
        txtSdtKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSdtKHActionPerformed(evt);
            }
        });
        jPanel37.add(txtSdtKH);

        btnSearchKH.setIcon(new FlatSVGIcon("./icon/man.svg"));
        btnSearchKH.setBorderPainted(false);
        btnSearchKH.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSearchKH.setFocusPainted(false);
        btnSearchKH.setFocusable(false);
        btnSearchKH.setPreferredSize(new java.awt.Dimension(40, 40));
        btnSearchKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchKHActionPerformed(evt);
            }
        });
        jPanel37.add(btnSearchKH);

        btnAddCustomer.setIcon(new FlatSVGIcon("./icon/add-customer.svg"));
        btnAddCustomer.setBorderPainted(false);
        btnAddCustomer.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAddCustomer.setFocusPainted(false);
        btnAddCustomer.setFocusable(false);
        btnAddCustomer.setPreferredSize(new java.awt.Dimension(40, 40));
        btnAddCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddCustomerActionPerformed(evt);
            }
        });
        jPanel37.add(btnAddCustomer);

        jPanel36.add(jPanel37);

        jPanel38.setBackground(new java.awt.Color(255, 255, 255));
        jPanel38.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 0));

        jLabel12.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jLabel12.setText("Tên khách hàng:");
        jLabel12.setMaximumSize(new java.awt.Dimension(44, 40));
        jLabel12.setPreferredSize(new java.awt.Dimension(120, 40));
        jPanel38.add(jLabel12);

        txtTenKH.setPreferredSize(new java.awt.Dimension(200, 40));
        jPanel38.add(txtTenKH);

        jPanel36.add(jPanel38);

        cboxGioiTinhKH.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nam", "Nữ" }));
        cboxGioiTinhKH.setPreferredSize(new java.awt.Dimension(90, 40));
        jPanel36.add(cboxGioiTinhKH);

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

        btnSearchKH1.setIcon(new FlatSVGIcon("./icon/search.svg"));
        btnSearchKH1.setBorderPainted(false);
        btnSearchKH1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSearchKH1.setFocusPainted(false);
        btnSearchKH1.setFocusable(false);
        btnSearchKH1.setPreferredSize(new java.awt.Dimension(40, 40));
        btnSearchKH1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchKH1ActionPerformed(evt);
            }
        });
        jPanel59.add(btnSearchKH1);

        btnAddCustomer1.setIcon(new FlatSVGIcon("./icon/add-customer.svg"));
        btnAddCustomer1.setBorderPainted(false);
        btnAddCustomer1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAddCustomer1.setFocusPainted(false);
        btnAddCustomer1.setFocusable(false);
        btnAddCustomer1.setPreferredSize(new java.awt.Dimension(40, 40));
        btnAddCustomer1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddCustomer1ActionPerformed(evt);
            }
        });
        jPanel59.add(btnAddCustomer1);

        jPanel36.add(jPanel59);

        jPanel35.add(jPanel36);

        jSeparator4.setPreferredSize(new java.awt.Dimension(400, 3));
        jPanel35.add(jSeparator4);

        jPanel39.setBackground(new java.awt.Color(255, 255, 255));
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
        txtTong.setText("1000000");
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

        jPanel35.add(jPanel39);

        jScrollPane2.setViewportView(jPanel35);

        billInfoPanel.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        billPanel.add(billInfoPanel);

        add(billPanel);
    }// </editor-fold>//GEN-END:initComponents

    private void txtSoLuongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSoLuongActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSoLuongActionPerformed

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnTimKiemActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_btnTimKiemActionPerformed

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
                String maThuoc = jTextField1.getText().trim();

                if (!maThuoc.isEmpty()) {
                    Thuoc thuoc = ThuocDAO.getThuocByMaThuoc(maThuoc);
                    if (thuoc != null) {
                        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
                        model.setRowCount(0);
                        model.addRow(new Object[] {
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
private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {
    try {
        // 1. Lấy thuốc được chọn từ bảng
        int selectedRow = jTable1.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một thuốc trước khi thêm", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return; 
        }
        
        // 2. Lấy số lượng từ ô nhập
        String soLuongText = txtSoLuong.getText().trim();
        if (soLuongText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập số lượng", "Thông báo", JOptionPane.WARNING_MESSAGE);
            txtSoLuong.requestFocus();
            return;
        }
        
        // 3. Kiểm tra số lượng là số nguyên dương
        int soLuong;
        try {
            soLuong = Integer.parseInt(soLuongText);
            if (soLuong <= 0) {
                JOptionPane.showMessageDialog(this, "Số lượng phải lớn hơn 0", "Lỗi", JOptionPane.ERROR_MESSAGE);
                txtSoLuong.requestFocus();
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Số lượng phải là số nguyên", "Lỗi", JOptionPane.ERROR_MESSAGE);
            txtSoLuong.requestFocus();
            return;
        }
        
        // 4. Lấy thông tin thuốc từ bảng
            String maThuoc = "";
            if (jTable1.getValueAt(selectedRow, 0) != null) {
                maThuoc = jTable1.getValueAt(selectedRow, 0).toString();
            }

            String tenThuoc = "";
            if (jTable1.getValueAt(selectedRow, 1) != null) {
                tenThuoc = jTable1.getValueAt(selectedRow, 1).toString();
            }

            double donGia = 0.0;
            if (jTable1.getValueAt(selectedRow, 4) != null) {
                try {
                    donGia = Double.parseDouble(jTable1.getValueAt(selectedRow, 4).toString());
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Giá trị đơn giá không hợp lệ", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }    
        // 5. Kiểm tra số lượng tồn kho
        int soLuongTonKho = Integer.parseInt(jTable1.getValueAt(selectedRow, 9).toString());
        if (soLuong > soLuongTonKho) {
            JOptionPane.showMessageDialog(this, "Số lượng yêu cầu vượt quá số lượng tồn kho (" + soLuongTonKho + ")", 
                    "Thông báo", JOptionPane.WARNING_MESSAGE);
            txtSoLuong.requestFocus();
            return;
        }
        
        // 6. Thêm thuốc vào bảng chi tiết hóa đơn
        themThuocVaoChiTietHoaDon(maThuoc, tenThuoc, soLuong, donGia);
        
        // 7. Reset ô số lượng
        txtSoLuong.setText("");
        
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Lỗi khi thêm thuốc: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
}
    private void jTextField1ActionPerformed(ActionEvent evt) {
        // Thực hiện hành động khi người dùng nhấn Enter trong JTextField
        String text = jTextField1.getText();
        System.out.println("Text field value: " + text);
    }

    // Sự kiện khi nhấn nút "THANH TOÁN"
    private void btnThanhToanActionPerformed(java.awt.event.ActionEvent evt) {
    //     try {
    //         // Kiểm tra đã có sản phẩm trong chi tiết hóa đơn chưa
    //         DefaultTableModel chiTietModel = (DefaultTableModel) jTable2.getModel();
    //         if (chiTietModel.getRowCount() == 0) {
    //             JOptionPane.showMessageDialog(this, "Vui lòng thêm sản phẩm vào hóa đơn trước khi thanh toán",
    //                     "Thông báo", JOptionPane.WARNING_MESSAGE);
    //             return;
    //         }

    //         // Kiểm tra đã nhập đủ thông tin khách hàng chưa
    //         if (txtTenKH.getText().trim().isEmpty()) {
    //             JOptionPane.showMessageDialog(this, "Vui lòng nhập tên khách hàng", "Thông báo",
    //                     JOptionPane.WARNING_MESSAGE);
    //             txtTenKH.requestFocus();
    //             return;
    //         }

    //         // Kiểm tra đã nhập tiền khách đưa chưa
    //         if (txtTienKhachDua.getText().trim().isEmpty()) {
    //             JOptionPane.showMessageDialog(this, "Vui lòng nhập số tiền khách đưa", "Thông báo",
    //                     JOptionPane.WARNING_MESSAGE);
    //             txtTienKhachDua.requestFocus();
    //             return;
    //         }

    //         // Kiểm tra tiền khách đưa có đủ không
    //         double tongTien = Double.parseDouble(txtTong.getText().replace(",", ""));
    //         double tienKhachDua = Double.parseDouble(txtTienKhachDua.getText().replace(",", ""));

    //         if (tienKhachDua < tongTien) {
    //             JOptionPane.showMessageDialog(this, "Tiền khách đưa không đủ", "Thông báo",
    //                     JOptionPane.WARNING_MESSAGE);
    //             txtTienKhachDua.requestFocus();
    //             return;
    //         }

    //         // THÊM MỚI: Tạo và lưu hóa đơn trước
    //         HoaDon hoaDon = taoHoaDon();
    //         boolean result = HoaDonDAO.them(hoaDon);

    //         if (!result) {
    //             JOptionPane.showMessageDialog(this, "Không thể lưu hóa đơn. Vui lòng thử lại.",
    //                     "Lỗi", JOptionPane.ERROR_MESSAGE);
    //             return;
    //         }

    //         // THÊM MỚI: Lưu các chi tiết hóa đơn vào CSDL
    //         boolean chiTietResult = luuChiTietHoaDon(hoaDon.getId());

    //         if (!chiTietResult) {
    //             // Nếu không lưu được chi tiết, xóa hóa đơn để tránh dữ liệu mồ côi
    //             HoaDonDAO.xoa(hoaDon.getId());
    //             JOptionPane.showMessageDialog(this,
    //                     "Không thể lưu chi tiết hóa đơn. Vui lòng thử lại.",
    //                     "Lỗi", JOptionPane.ERROR_MESSAGE);
    //             return;
    //         }

    //         // Nếu mọi thứ OK, hiển thị thông báo thành công
    //         JOptionPane.showMessageDialog(this,
    //                 "Thanh toán hóa đơn thành công!\nMã hóa đơn: " + hoaDon.getId(),
    //                 "Thành công", JOptionPane.INFORMATION_MESSAGE);

    //         // Tính tiền thừa và hiển thị
    //         double tienThua = tienKhachDua - tongTien;
    //         txtTienThua.setText(String.format("%.0f", tienThua));

    //         // Có thể in hóa đơn ở đây
    //         // inHoaDon(hoaDon.getId());

    //         // Reset form sau khi thanh toán thành công
    //         resetForm();

    //     } catch (Exception e) {
    //         System.err.println("Lỗi khi thanh toán hóa đơn: " + e.getMessage());
    //         e.printStackTrace();
    //         JOptionPane.showMessageDialog(this,
    //                 "Lỗi khi thanh toán hóa đơn: " + e.getMessage(),
    //                 "Lỗi", JOptionPane.ERROR_MESSAGE);
    //     }
    }

// Phương thức tạo đối tượng hóa đơn từ dữ liệu trên form
// private HoaDon taoHoaDon() {
//     // Tạo mã hóa đơn mới
//     String maHD = generateMaHD();

//     // Lấy thông tin từ form
//     String tenKH = txtTenKH.getText().trim();
//     String sdtKH = txtSdtKH.getText().trim();
//     // String diaChi = txtDiaChi.getText().trim(); // Có vẻ như không có trường địa
//     // chỉ trong form
//     double tongTien = Double.parseDouble(txtTong.getText().replace(",", ""));

//     String idNhanVien = txtNV.getText().trim(); // Phương thức cần được thực hiện

//     // Tạo hoặc lấy ID khách hàng
//     // String idKhachHang = timHoacTaoKhachHang(tenKH, sdtKH); // Phương thức cần được thực hiện

//     // Tạo đối tượng hóa đơn theo cấu trúc lớp HoaDon.java
//     HoaDon hoaDon = new HoaDon();
//     hoaDon.setId(maHD);
//     hoaDon.setNgayLap(new Date());
//     hoaDon.setIdNhanVien(idNhanVien);
//     hoaDon.setIdKhachHang(idKhachHang);
//     hoaDon.setTongTien(tongTien);

//     return hoaDon;
// }


// Phương thức lưu tất cả chi tiết hóa đơn vào CSDL
private boolean luuChiTietHoaDon(String maHD) {
    try {
        DefaultTableModel chiTietModel = (DefaultTableModel) jTable2.getModel();
        int rowCount = chiTietModel.getRowCount();

        // Duyệt qua từng dòng trong bảng chi tiết
        for (int i = 0; i < rowCount; i++) {
            String maThuoc = chiTietModel.getValueAt(i, 1).toString();
            String tenThuoc = chiTietModel.getValueAt(i, 2).toString(); // Lấy tên thuốc từ bảng
            int soLuong = Integer.parseInt(chiTietModel.getValueAt(i, 3).toString());
            double donGia = Double.parseDouble(chiTietModel.getValueAt(i, 4).toString());
            double thanhTien = soLuong * donGia; // Tính thành tiền

            // Tạo đối tượng chi tiết hóa đơn
            ChiTietHoaDon chiTiet = new ChiTietHoaDon(
                    maHD, // Mã hóa đơn
                    maThuoc, // Mã thuốc
                    tenThuoc, // Tên thuốc (lấy từ bảng hiển thị)
                    soLuong, // Số lượng
                    donGia, // Đơn giá
                    thanhTien // Thành tiền
            );

            // Lưu vào CSDL
            boolean result = ChiTietHoaDonDAO.themChiTietHoaDon(chiTiet);

            if (!result) {
                System.err.println("Lỗi: Không thể thêm chi tiết hóa đơn cho mã thuốc " + maThuoc);
                return false;
            }

            // Cập nhật số lượng tồn kho của thuốc
            boolean updateKho = capNhatSoLuongTonKho(maThuoc, soLuong);
            if (!updateKho) {
                System.err.println("Cảnh báo: Không thể cập nhật số lượng tồn kho cho thuốc " + maThuoc);
            }
        }

        return true;

    } catch (Exception e) {
        System.err.println("Lỗi khi lưu chi tiết hóa đơn: " + e.getMessage());
        e.printStackTrace();
        return false;
    }
}

// Phương thức cập nhật số lượng tồn kho sau khi bán thuốc
private boolean capNhatSoLuongTonKho(String maThuoc, int soLuongBan) {
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
private String generateMaHD() {
    // Ví dụ tạo mã theo định dạng: HD + yyyyMMddHHmmss
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
    return "HD" + sdf.format(new Date());
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
    private void btnHuyActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnHuyActionPerformed
        // if (MessageDialog.confirm(this, "Xác nhận hủy hóa đơn?", "Hủy hóa đơn")) {
        // for (ChiTietHoaDon cthd : listCTHD) {
        // Thuoc thuocCTHD = cthd.getThuoc();
        // Thuoc thuoc = listThuoc.get(listThuoc.indexOf(thuocCTHD));
        // int updatedSoLuongTon = thuoc.getSoLuongTon() + cthd.getSoLuong();
        // THUOC_CON.updateSoLuongTon(thuoc, updatedSoLuongTon);
        // }
        //
        // main.setPanel(new HoaDonPage(main));
        // }
    }// GEN-LAST:event_btnHuyActionPerformed

    private void txtTienKhachDuaKeyReleased(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_txtTienKhachDuaKeyReleased
        // if (isValidHoaDon()) {
        // Double tong = Formatter.unformatVND(txtTong.getText());
        // Double tienKhachDua = Double.valueOf(txtTienKhachDua.getText());
        // Double tienThua = tienKhachDua - tong;
        //
        // if (tienThua <= 0) {
        // tienThua = 0.0;
        // }
        //
        // txtTienThua.setText(Formatter.FormatVND(tienThua));
        // }
    }// GEN-LAST:event_txtTienKhachDuaKeyReleased

    private void btnAddCustomer1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnAddCustomer1ActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_btnAddCustomer1ActionPerformed

    private void btnSearchKH1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnSearchKH1ActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_btnSearchKH1ActionPerformed

    private void txtSdtKH1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txtSdtKH1ActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_txtSdtKH1ActionPerformed

    private void btnAddCustomerActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnAddCustomerActionPerformed
        // CreateKhachHangDialog dialog = new CreateKhachHangDialog(null, true, new
        // KhachHangPage());
        // dialog.setVisible(true);
    }// GEN-LAST:event_btnAddCustomerActionPerformed

    private void btnSearchKHActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnSearchKHActionPerformed
        // KhachHang kh = new KhachHangController().selectBySdt(txtSdtKH.getText());
        //
        // if (kh == null) {
        // MessageDialog.error(this, "Không tìm thấy khách hàng!");
        // txtHoTenKH.setText("");
        // cboxGioiTinhKH.setSelectedIndex(0);
        // txtHoTenKH.setEnabled(true);
        // cboxGioiTinhKH.setEnabled(true);
        // } else {
        // txtHoTenKH.setText(kh.getHoTen());
        // cboxGioiTinhKH.setSelectedItem(kh.getGioiTinh());
        // txtHoTenKH.setEnabled(false);
        // cboxGioiTinhKH.setEnabled(false);
        // }
    }// GEN-LAST:event_btnSearchKHActionPerformed

    private void txtSdtKHActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txtSdtKHActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_txtSdtKHActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel JPanhThuoc;
    private javax.swing.JPanel billInfoPanel;
    private javax.swing.JPanel billPanel;
    private javax.swing.JButton btnAddCustomer;
    private javax.swing.JButton btnAddCustomer1;
    private javax.swing.JButton btnHuy;
    private javax.swing.JButton btnSearchKH;
    private javax.swing.JButton btnSearchKH1;
    private javax.swing.JButton btnThanhToan;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JComboBox<String> cboxGioiTinhKH;
    private javax.swing.JButton jButton2;
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
    private javax.swing.JTextField jTextField1;
    private java.awt.Label label1;
    private java.awt.Label label2;
    private javax.swing.JTextField txtDonGia;
    private javax.swing.JTextField txtMaHoaDon;
    private javax.swing.JTextField txtMaThuoc;
    private javax.swing.JTextField txtNV;
    private javax.swing.JTextField txtSdtKH;
    private javax.swing.JTextField txtSoLuong;
    private javax.swing.JTextField txtTenKH;
    private javax.swing.JTextField txtTenThuoc;
    private javax.swing.JTextArea txtThanhPhan;
    private javax.swing.JTextField txtThoiGian;
    private javax.swing.JTextField txtTienKhachDua;
    private javax.swing.JTextField txtTienThua;
    private javax.swing.JTextField txtTong;
    // End of variables declaration//GEN-END:variables

    public void refreshData() {
        startIndex = 0; // Reset to first page
        loadDataToTable(); // Reload data
    }
}

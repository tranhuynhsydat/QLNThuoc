/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package GUI.page;

import DAO.NhanVienDAO;
import DAO.ThuocDAO;
import Entity.NhanVien;
import Entity.Thuoc;
import GUI.form.formThongTinThuoc;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.swing.ImageIcon;
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
 * @author Admin
 */
public class frmSearchThuoc extends javax.swing.JPanel {

    private int startIndex;
    private boolean isSearching = false;

    /**
     * Creates new form frmSearchThuoc
     */
    public frmSearchThuoc() {
        initComponents();
        configureTable();
        startIndex = 0;
        loadDataToTable();
        // Thêm sự kiện cuộn bảng
        jScrollPane3.getVerticalScrollBar().addAdjustmentListener(e -> {
            // Nếu đang tìm kiếm thì không tải thêm dữ liệu
            if (isSearching) {
                return;
            }

            JScrollBar vertical = jScrollPane3.getVerticalScrollBar();
            int max = vertical.getMaximum();
            int current = vertical.getValue();
            int visible = vertical.getVisibleAmount();

            // Kiểm tra nếu người dùng đã cuộn đến cuối bảng
            if (current + visible >= max) {
                startIndex += 13; // Tăng chỉ mục bắt đầu để tải dữ liệu tiếp theo
                loadDataToTable();  // Tải thêm dữ liệu
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
        // Tạo một worker để tải dữ liệu trong nền
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                // Lấy danh sách thuốc từ cơ sở dữ liệu
                List<Thuoc> thuocList = ThuocDAO.getThuocBatch(startIndex, 13);  // startIndex là chỉ mục bắt đầu
                if (thuocList != null && !thuocList.isEmpty()) {
                    SwingUtilities.invokeLater(() -> {
                        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
                        if (startIndex == 0) {
                            model.setRowCount(0);  // Xóa tất cả dữ liệu hiện tại trong bảng khi tải lại từ đầu
                        }

                        // Thêm dữ liệu mới vào bảng
                        for (Thuoc thuoc : thuocList) {
                            Object[] rowData = {
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
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jPanel33 = new javax.swing.JPanel();
        jPanel34 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jPanel35 = new javax.swing.JPanel();
        txtTenThuoc = new javax.swing.JTextField();
        jPanel8 = new javax.swing.JPanel();
        jPanel17 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jPanel18 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtThanhPhanThuoc = new javax.swing.JTextArea();
        jPanel11 = new javax.swing.JPanel();
        jPanel23 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jPanel24 = new javax.swing.JPanel();
        txtGiaNhap = new javax.swing.JTextField();
        jPanel14 = new javax.swing.JPanel();
        jPanel29 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jPanel30 = new javax.swing.JPanel();
        txtGiaBan = new javax.swing.JTextField();
        jPanel9 = new javax.swing.JPanel();
        jPanel19 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jPanel20 = new javax.swing.JPanel();
        DateHSD = new com.toedter.calendar.JDateChooser();
        jPanel15 = new javax.swing.JPanel();
        jPanel36 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jPanel37 = new javax.swing.JPanel();
        comboDanhMuc = new javax.swing.JComboBox<>();
        jPanel16 = new javax.swing.JPanel();
        jPanel38 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jPanel39 = new javax.swing.JPanel();
        comboDVT = new javax.swing.JComboBox<>();
        jPanel21 = new javax.swing.JPanel();
        jPanel40 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jPanel41 = new javax.swing.JPanel();
        comboXuatXu = new javax.swing.JComboBox<>();
        jPanel13 = new javax.swing.JPanel();
        jPanel27 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jPanel28 = new javax.swing.JPanel();
        txtSoLuong = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jPanel42 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel43 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        btnPanel = new javax.swing.JPanel();
        btnTimKiem = new javax.swing.JButton();
        btnChiTiet = new javax.swing.JButton();

        setMinimumSize(new java.awt.Dimension(829, 624));
        setPreferredSize(new java.awt.Dimension(829, 624));
        setLayout(new java.awt.BorderLayout());

        jPanel1.setBackground(new java.awt.Color(255, 102, 102));
        jPanel1.setMinimumSize(new java.awt.Dimension(829, 312));
        jPanel1.setPreferredSize(new java.awt.Dimension(829, 400));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel3.setBackground(new java.awt.Color(0, 120, 92));
        jPanel3.setMinimumSize(new java.awt.Dimension(829, 50));
        jPanel3.setPreferredSize(new java.awt.Dimension(829, 50));
        jPanel3.setLayout(new java.awt.BorderLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Nhập thông tin tìm kiếm");
        jPanel3.add(jLabel1, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel3, java.awt.BorderLayout.PAGE_START);

        jPanel4.setToolTipText("");
        jPanel4.setMinimumSize(new java.awt.Dimension(829, 300));
        jPanel4.setPreferredSize(new java.awt.Dimension(829, 300));
        jPanel4.setLayout(new javax.swing.BoxLayout(jPanel4, javax.swing.BoxLayout.Y_AXIS));

        jPanel33.setMinimumSize(new java.awt.Dimension(829, 38));
        jPanel33.setLayout(new java.awt.BorderLayout());

        jPanel34.setMinimumSize(new java.awt.Dimension(300, 38));
        jPanel34.setPreferredSize(new java.awt.Dimension(500, 38));
        jPanel34.setLayout(new java.awt.BorderLayout());

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel11.setText("Tên thuốc:");
        jLabel11.setMaximumSize(new java.awt.Dimension(170, 38));
        jLabel11.setMinimumSize(new java.awt.Dimension(170, 38));
        jLabel11.setPreferredSize(new java.awt.Dimension(170, 38));
        jPanel34.add(jLabel11, java.awt.BorderLayout.CENTER);

        jPanel33.add(jPanel34, java.awt.BorderLayout.LINE_START);

        jPanel35.setMinimumSize(new java.awt.Dimension(669, 38));
        jPanel35.setPreferredSize(new java.awt.Dimension(680, 38));
        jPanel35.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 7));

        txtTenThuoc.setPreferredSize(new java.awt.Dimension(350, 30));
        txtTenThuoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTenThuocActionPerformed(evt);
            }
        });
        jPanel35.add(txtTenThuoc);

        jPanel33.add(jPanel35, java.awt.BorderLayout.CENTER);

        jPanel4.add(jPanel33);

        jPanel8.setMinimumSize(new java.awt.Dimension(829, 38));
        jPanel8.setLayout(new java.awt.BorderLayout());

        jPanel17.setMinimumSize(new java.awt.Dimension(300, 38));
        jPanel17.setPreferredSize(new java.awt.Dimension(500, 38));
        jPanel17.setLayout(new java.awt.BorderLayout());

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Thành phần thuốc:");
        jLabel4.setMaximumSize(new java.awt.Dimension(170, 38));
        jLabel4.setMinimumSize(new java.awt.Dimension(170, 38));
        jLabel4.setOpaque(true);
        jLabel4.setPreferredSize(new java.awt.Dimension(170, 38));
        jPanel17.add(jLabel4, java.awt.BorderLayout.CENTER);

        jPanel8.add(jPanel17, java.awt.BorderLayout.LINE_START);

        jPanel18.setMinimumSize(new java.awt.Dimension(669, 232));
        jPanel18.setPreferredSize(new java.awt.Dimension(680, 38));
        jPanel18.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 10));

        jScrollPane2.setPreferredSize(new java.awt.Dimension(349, 232));

        txtThanhPhanThuoc.setColumns(20);
        txtThanhPhanThuoc.setRows(5);
        txtThanhPhanThuoc.setMinimumSize(new java.awt.Dimension(16, 16));
        txtThanhPhanThuoc.setPreferredSize(new java.awt.Dimension(349, 32));
        jScrollPane2.setViewportView(txtThanhPhanThuoc);

        jPanel18.add(jScrollPane2);

        jPanel8.add(jPanel18, java.awt.BorderLayout.CENTER);

        jPanel4.add(jPanel8);

        jPanel11.setMinimumSize(new java.awt.Dimension(829, 38));
        jPanel11.setLayout(new java.awt.BorderLayout());

        jPanel23.setMinimumSize(new java.awt.Dimension(300, 38));
        jPanel23.setPreferredSize(new java.awt.Dimension(500, 38));
        jPanel23.setLayout(new java.awt.BorderLayout());

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Giá nhập:");
        jLabel7.setMaximumSize(new java.awt.Dimension(170, 38));
        jLabel7.setMinimumSize(new java.awt.Dimension(170, 38));
        jLabel7.setPreferredSize(new java.awt.Dimension(170, 38));
        jPanel23.add(jLabel7, java.awt.BorderLayout.CENTER);

        jPanel11.add(jPanel23, java.awt.BorderLayout.LINE_START);

        jPanel24.setMinimumSize(new java.awt.Dimension(669, 38));
        jPanel24.setPreferredSize(new java.awt.Dimension(680, 38));
        jPanel24.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 7));

        txtGiaNhap.setPreferredSize(new java.awt.Dimension(350, 30));
        jPanel24.add(txtGiaNhap);

        jPanel11.add(jPanel24, java.awt.BorderLayout.CENTER);

        jPanel4.add(jPanel11);

        jPanel14.setMinimumSize(new java.awt.Dimension(829, 38));
        jPanel14.setLayout(new java.awt.BorderLayout());

        jPanel29.setMinimumSize(new java.awt.Dimension(300, 38));
        jPanel29.setPreferredSize(new java.awt.Dimension(500, 38));
        jPanel29.setLayout(new java.awt.BorderLayout());

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText("Giá bán:");
        jLabel10.setToolTipText("");
        jLabel10.setMaximumSize(new java.awt.Dimension(170, 38));
        jLabel10.setMinimumSize(new java.awt.Dimension(170, 38));
        jLabel10.setPreferredSize(new java.awt.Dimension(170, 38));
        jPanel29.add(jLabel10, java.awt.BorderLayout.CENTER);

        jPanel14.add(jPanel29, java.awt.BorderLayout.LINE_START);

        jPanel30.setMinimumSize(new java.awt.Dimension(669, 38));
        jPanel30.setPreferredSize(new java.awt.Dimension(680, 38));
        jPanel30.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 7));

        txtGiaBan.setPreferredSize(new java.awt.Dimension(350, 30));
        jPanel30.add(txtGiaBan);

        jPanel14.add(jPanel30, java.awt.BorderLayout.CENTER);

        jPanel4.add(jPanel14);

        jPanel9.setMinimumSize(new java.awt.Dimension(829, 38));
        jPanel9.setLayout(new java.awt.BorderLayout());

        jPanel19.setMinimumSize(new java.awt.Dimension(300, 38));
        jPanel19.setPreferredSize(new java.awt.Dimension(500, 38));
        jPanel19.setLayout(new java.awt.BorderLayout());

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("HSD:");
        jLabel5.setMaximumSize(new java.awt.Dimension(170, 38));
        jLabel5.setMinimumSize(new java.awt.Dimension(170, 38));
        jLabel5.setPreferredSize(new java.awt.Dimension(170, 38));
        jPanel19.add(jLabel5, java.awt.BorderLayout.CENTER);

        jPanel9.add(jPanel19, java.awt.BorderLayout.LINE_START);

        jPanel20.setMinimumSize(new java.awt.Dimension(669, 38));
        jPanel20.setPreferredSize(new java.awt.Dimension(680, 38));
        jPanel20.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 7));

        DateHSD.setPreferredSize(new java.awt.Dimension(88, 30));
        jPanel20.add(DateHSD);

        jPanel9.add(jPanel20, java.awt.BorderLayout.CENTER);

        jPanel4.add(jPanel9);

        jPanel15.setMinimumSize(new java.awt.Dimension(829, 38));
        jPanel15.setLayout(new java.awt.BorderLayout());

        jPanel36.setMinimumSize(new java.awt.Dimension(300, 38));
        jPanel36.setPreferredSize(new java.awt.Dimension(500, 38));
        jPanel36.setLayout(new java.awt.BorderLayout());

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel12.setText("Danh mục:");
        jLabel12.setMaximumSize(new java.awt.Dimension(170, 38));
        jLabel12.setMinimumSize(new java.awt.Dimension(170, 38));
        jLabel12.setPreferredSize(new java.awt.Dimension(170, 38));
        jPanel36.add(jLabel12, java.awt.BorderLayout.CENTER);

        jPanel15.add(jPanel36, java.awt.BorderLayout.LINE_START);

        jPanel37.setMinimumSize(new java.awt.Dimension(669, 38));
        jPanel37.setPreferredSize(new java.awt.Dimension(680, 38));
        jPanel37.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 7));

        comboDanhMuc.setPreferredSize(new java.awt.Dimension(350, 22));
        comboDanhMuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboDanhMucActionPerformed(evt);
            }
        });
        jPanel37.add(comboDanhMuc);

        jPanel15.add(jPanel37, java.awt.BorderLayout.CENTER);

        jPanel4.add(jPanel15);

        jPanel16.setMinimumSize(new java.awt.Dimension(829, 38));
        jPanel16.setLayout(new java.awt.BorderLayout());

        jPanel38.setMinimumSize(new java.awt.Dimension(300, 38));
        jPanel38.setPreferredSize(new java.awt.Dimension(500, 38));
        jPanel38.setLayout(new java.awt.BorderLayout());

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel13.setText("Đơn vị tính:");
        jLabel13.setMaximumSize(new java.awt.Dimension(170, 38));
        jLabel13.setMinimumSize(new java.awt.Dimension(170, 38));
        jLabel13.setPreferredSize(new java.awt.Dimension(170, 38));
        jPanel38.add(jLabel13, java.awt.BorderLayout.CENTER);

        jPanel16.add(jPanel38, java.awt.BorderLayout.LINE_START);

        jPanel39.setMinimumSize(new java.awt.Dimension(669, 38));
        jPanel39.setPreferredSize(new java.awt.Dimension(680, 38));
        jPanel39.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 7));

        comboDVT.setPreferredSize(new java.awt.Dimension(350, 22));
        comboDVT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboDVTActionPerformed(evt);
            }
        });
        jPanel39.add(comboDVT);

        jPanel16.add(jPanel39, java.awt.BorderLayout.CENTER);

        jPanel4.add(jPanel16);

        jPanel21.setMinimumSize(new java.awt.Dimension(829, 38));
        jPanel21.setLayout(new java.awt.BorderLayout());

        jPanel40.setMinimumSize(new java.awt.Dimension(300, 38));
        jPanel40.setPreferredSize(new java.awt.Dimension(500, 38));
        jPanel40.setLayout(new java.awt.BorderLayout());

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel14.setText("Xuất xứ:");
        jLabel14.setMaximumSize(new java.awt.Dimension(170, 38));
        jLabel14.setMinimumSize(new java.awt.Dimension(170, 38));
        jLabel14.setPreferredSize(new java.awt.Dimension(170, 38));
        jPanel40.add(jLabel14, java.awt.BorderLayout.CENTER);

        jPanel21.add(jPanel40, java.awt.BorderLayout.LINE_START);

        jPanel41.setMinimumSize(new java.awt.Dimension(669, 38));
        jPanel41.setPreferredSize(new java.awt.Dimension(680, 38));
        jPanel41.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 7));

        comboXuatXu.setPreferredSize(new java.awt.Dimension(350, 22));
        comboXuatXu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboXuatXuActionPerformed(evt);
            }
        });
        jPanel41.add(comboXuatXu);

        jPanel21.add(jPanel41, java.awt.BorderLayout.CENTER);

        jPanel4.add(jPanel21);

        jPanel13.setMinimumSize(new java.awt.Dimension(829, 38));
        jPanel13.setLayout(new java.awt.BorderLayout());

        jPanel27.setMinimumSize(new java.awt.Dimension(300, 38));
        jPanel27.setPreferredSize(new java.awt.Dimension(500, 38));
        jPanel27.setLayout(new java.awt.BorderLayout(5, 10));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("Số lượng:");
        jLabel9.setMaximumSize(new java.awt.Dimension(170, 38));
        jLabel9.setMinimumSize(new java.awt.Dimension(170, 38));
        jLabel9.setPreferredSize(new java.awt.Dimension(180, 38));
        jPanel27.add(jLabel9, java.awt.BorderLayout.CENTER);

        jPanel13.add(jPanel27, java.awt.BorderLayout.LINE_START);

        jPanel28.setMinimumSize(new java.awt.Dimension(669, 38));
        jPanel28.setPreferredSize(new java.awt.Dimension(680, 38));
        jPanel28.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 7));

        txtSoLuong.setMinimumSize(new java.awt.Dimension(350, 22));
        txtSoLuong.setPreferredSize(new java.awt.Dimension(350, 30));
        jPanel28.add(txtSoLuong);

        jPanel13.add(jPanel28, java.awt.BorderLayout.CENTER);

        jPanel4.add(jPanel13);

        jPanel1.add(jPanel4, java.awt.BorderLayout.CENTER);

        add(jPanel1, java.awt.BorderLayout.PAGE_START);

        jPanel2.setBackground(new java.awt.Color(255, 255, 153));
        jPanel2.setPreferredSize(new java.awt.Dimension(829, 174));
        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel42.setBackground(new java.awt.Color(0, 120, 92));
        jPanel42.setPreferredSize(new java.awt.Dimension(829, 50));
        jPanel42.setLayout(new java.awt.BorderLayout());

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Danh sách thuốc");
        jPanel42.add(jLabel3, java.awt.BorderLayout.CENTER);

        jPanel2.add(jPanel42, java.awt.BorderLayout.PAGE_START);

        jPanel43.setMinimumSize(new java.awt.Dimension(452, 125));
        jPanel43.setPreferredSize(new java.awt.Dimension(452, 125));
        jPanel43.setLayout(new java.awt.BorderLayout());

        jScrollPane3.setPreferredSize(new java.awt.Dimension(200, 452));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã thuốc", "Tên thuốc", "Thành phần", "Giá nhập", "Giá bán", "HSD", "Danh mục", "Đơn vị tính", "Xuất xứ", "Số lượng"
            }
        ));
        jTable1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTable1.setShowHorizontalLines(true);
        jScrollPane3.setViewportView(jTable1);

        jPanel43.add(jScrollPane3, java.awt.BorderLayout.CENTER);

        jPanel2.add(jPanel43, java.awt.BorderLayout.CENTER);

        add(jPanel2, java.awt.BorderLayout.CENTER);

        btnPanel.setBackground(new java.awt.Color(222, 222, 222));
        btnPanel.setPreferredSize(new java.awt.Dimension(829, 50));
        java.awt.FlowLayout flowLayout1 = new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 100, 8);
        flowLayout1.setAlignOnBaseline(true);
        btnPanel.setLayout(flowLayout1);

        btnTimKiem.setBackground(new java.awt.Color(0, 120, 92));
        btnTimKiem.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnTimKiem.setForeground(new java.awt.Color(255, 255, 255));
        btnTimKiem.setText("Tìm kiếm");
        btnTimKiem.setMaximumSize(new java.awt.Dimension(85, 35));
        btnTimKiem.setMinimumSize(new java.awt.Dimension(85, 35));
        btnTimKiem.setPreferredSize(new java.awt.Dimension(105, 35));
        btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemActionPerformed(evt);
            }
        });
        btnPanel.add(btnTimKiem);

        btnChiTiet.setBackground(new java.awt.Color(0, 120, 92));
        btnChiTiet.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnChiTiet.setForeground(new java.awt.Color(255, 255, 255));
        btnChiTiet.setText("Chi tiết");
        btnChiTiet.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnChiTiet.setMaximumSize(new java.awt.Dimension(85, 35));
        btnChiTiet.setMinimumSize(new java.awt.Dimension(85, 35));
        btnChiTiet.setPreferredSize(new java.awt.Dimension(105, 35));
        btnChiTiet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChiTietActionPerformed(evt);
            }
        });
        btnPanel.add(btnChiTiet);

        add(btnPanel, java.awt.BorderLayout.PAGE_END);
    }// </editor-fold>//GEN-END:initComponents

    private void txtTenThuocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTenThuocActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTenThuocActionPerformed

    private void comboDanhMucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboDanhMucActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboDanhMucActionPerformed

    private void comboDVTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboDVTActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboDVTActionPerformed

    private void comboXuatXuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboXuatXuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboXuatXuActionPerformed

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemActionPerformed
        // Đánh dấu là đang tìm kiếm
        isSearching = true;

        // Lấy giá trị từ các trường nhập liệu
        String tenThuoc = txtTenThuoc.getText().trim();
        String thanhPhanThuoc = txtThanhPhanThuoc.getText().trim();
        double giaNhap = -1;
        double giaBan = -1;

        // Kiểm tra giá trị của "Giá nhập" và "Giá bán"
        try {
            if (!txtGiaNhap.getText().isEmpty()) {
                giaNhap = Double.parseDouble(txtGiaNhap.getText().trim());
            }
            if (!txtGiaBan.getText().isEmpty()) {
                giaBan = Double.parseDouble(txtGiaBan.getText().trim());
            }
        } catch (NumberFormatException e) {
            // Nếu có lỗi, giaNhap và giaBan đã được mặc định là -1 (không lọc)
        }

        // Chuyển đổi java.util.Date sang java.sql.Date (cho trường HSD)
        java.util.Date utilDate = DateHSD.getDate();
        java.sql.Date hsd = utilDate != null ? new java.sql.Date(utilDate.getTime()) : null;

        // Lấy giá trị từ các comboBox
        String danhMuc = comboDanhMuc.getSelectedItem() != null ? comboDanhMuc.getSelectedItem().toString() : "";
        String donViTinh = comboDVT.getSelectedItem() != null ? comboDVT.getSelectedItem().toString() : "";
        String xuatXu = comboXuatXu.getSelectedItem() != null ? comboXuatXu.getSelectedItem().toString() : "";

        // Lấy giá trị từ "Số lượng"
        int soLuong = 0;
        try {
            if (!txtSoLuong.getText().isEmpty()) {
                soLuong = Integer.parseInt(txtSoLuong.getText().trim());
            }
        } catch (NumberFormatException e) {
            soLuong = 0;  // Nếu không nhập, coi như không lọc
        }

        // Gọi phương thức tìm kiếm thuốc từ DAO
        List<Thuoc> thuocList = ThuocDAO.searchThuoc(tenThuoc, thanhPhanThuoc, giaNhap, giaBan, hsd, danhMuc, donViTinh, xuatXu);

        // Cập nhật bảng với kết quả tìm kiếm
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);  // Xóa dữ liệu cũ trong bảng

        // Thêm dữ liệu vào bảng
        if (thuocList.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Không tìm thấy thuốc phù hợp!");
        } else {
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

        // Đánh dấu tìm kiếm xong

    }//GEN-LAST:event_btnTimKiemActionPerformed

    private void btnChiTietActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChiTietActionPerformed
        int selectedRow = jTable1.getSelectedRow();  // Lấy dòng được chọn trong bảng

        if (selectedRow != -1) {
            // Lấy mã thuốc từ cột đầu tiên (mã thuốc)
            String maThuoc = jTable1.getValueAt(selectedRow, 0).toString();

            // Mở form "Thông tin thuốc" và truyền mã thuốc vào constructor
            JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            formThongTinThuoc dialog = new formThongTinThuoc(parentFrame, true, maThuoc);  // Truyền mã thuốc vào constructor
            dialog.setLocationRelativeTo(this);
            dialog.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn thuốc để xem chi tiết!");
        }

    }//GEN-LAST:event_btnChiTietActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser DateHSD;
    private javax.swing.JButton btnChiTiet;
    private javax.swing.JPanel btnPanel;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JComboBox<String> comboDVT;
    private javax.swing.JComboBox<String> comboDanhMuc;
    private javax.swing.JComboBox<String> comboXuatXu;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel33;
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
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField txtGiaBan;
    private javax.swing.JTextField txtGiaNhap;
    private javax.swing.JTextField txtSoLuong;
    private javax.swing.JTextField txtTenThuoc;
    private javax.swing.JTextArea txtThanhPhanThuoc;
    // End of variables declaration//GEN-END:variables
}

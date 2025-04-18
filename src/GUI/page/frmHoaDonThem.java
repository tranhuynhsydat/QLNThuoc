/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package GUI.page;

import DAO.ThuocDAO;
import Entity.Thuoc;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import com.formdev.flatlaf.extras.FlatSVGIcon;

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

    public frmHoaDonThem() {
        initComponents();
        configureTable();
        loadDataToTable();
        addTableSelectionListener();
        initEvent();
        // Add scroll listener to load more data when user scrolls to bottom
        jScrollPane1.getVerticalScrollBar().addAdjustmentListener(e -> {
            JScrollBar vertical = jScrollPane1.getVerticalScrollBar();
            int max = vertical.getMaximum();
            int current = vertical.getValue();
            int visible = vertical.getVisibleAmount();
            // Check if user has scrolled to the bottom
            if (current + visible >= max) {
                startIndex += 13; // Increase start index to load next batch
                loadDataToTable(); // Load more data
            }
        });
    }

    private void configureTable() {
        // Configure table with column names
        DefaultTableModel model = new DefaultTableModel(
                new Object[][] {},
                new String[] {
                        "STT", "Mã thuốc", "Tên thuốc", "Danh mục", "Đơn vị", "Xuất xứ", "Số lượng", "Giá nhập"
                });
        jTable1.setModel(model);
        jTable1.setMinimumSize(new java.awt.Dimension(470, 217));
        jTable1.setPreferredSize(new java.awt.Dimension(470, 217));
    }

    private void loadDataToTable() {
        // Load medicine data in batches using SwingWorker to prevent UI freezing
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                List<Thuoc> thuocList = ThuocDAO.getThuocBatch(startIndex, 13);
                System.out.println("Received data from DB: " + thuocList.size() + " rows");
                if (thuocList == null || thuocList.isEmpty()) {
                    System.out.println("No data to load.");
                } else {
                    SwingUtilities.invokeLater(() -> {
                        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
                        if (startIndex == 0) {
                            model.setRowCount(0); // Clear all existing data when reloading from beginning
                        }
                        // Add new data to table
                        int stt = startIndex + 1;
                        for (Thuoc thuoc : thuocList) {
                            // Get the category, unit, and origin names safely
                            String danhMucName = "";
                            if (thuoc.getDanhMuc() != null) {
                                // Use the field directly or a getter that exists
                                // For example, if the field is directly accessible:
                                danhMucName = thuoc.getDanhMuc().getTen(); // Adjust if needed
                            }

                            String donViName = "";
                            if (thuoc.getDonViTinh() != null) {
                                // Use the field directly or a getter that exists
                                donViName = thuoc.getDonViTinh().getTen(); // Adjust if needed
                            }

                            String xuatXuName = "";
                            if (thuoc.getXuatXu() != null) {
                                // Use the field directly or a getter that exists
                                xuatXuName = thuoc.getXuatXu().getTen(); // Adjust if needed
                            }

                            Object[] rowData = {
                                    stt++,
                                    thuoc.getId(),
                                    thuoc.getTenThuoc(),
                                    danhMucName,
                                    donViName,
                                    xuatXuName,
                                    thuoc.getSoLuong(),
                                    thuoc.getGiaNhap()
                            };
                            model.addRow(rowData);
                        }
                        model.fireTableDataChanged(); // Ensure table is refreshed
                        jTable1.revalidate(); // Update table
                        jTable1.repaint(); // Repaint table
                    });
                }
                return null;
            }
        };
        worker.execute();
    }

    private void addTableSelectionListener() {
        // Add a selection listener to the JTable
        jTable1.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int selectedRow = jTable1.getSelectedRow();
                if (selectedRow != -1) {
                    // Get data from selected row
                    String maThuoc = jTable1.getValueAt(selectedRow, 1).toString();
                    String tenThuoc = jTable1.getValueAt(selectedRow, 2).toString();
                    String thanhPhan = "Công thức thuốc ví dụ"; // Add actual data
                    String donGia = jTable1.getValueAt(selectedRow, 7).toString();

                    // Update text fields with the selected row's data
                    txtMaThuoc.setText(maThuoc);
                    txtTenThuoc.setText(tenThuoc);
                    txtThanhPhan.setText(thanhPhan);
                    txtDonGia.setText(donGia);
                }
            }
        });
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
    // Code">//GEN-BEGIN:initComponents
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
        jTextField2 = new javax.swing.JTextField();
        jPanel57 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
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
        txtHoTenKH = new javax.swing.JTextField();
        cboxGioiTinhKH = new javax.swing.JComboBox<>();
        jPanel8 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        txtThoiGian = new javax.swing.JTextField();
        jPanel59 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        txtSdtKH1 = new javax.swing.JTextField();
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
                        .addGap(0, 262, Short.MAX_VALUE));
        JPanhThuocLayout.setVerticalGroup(
                JPanhThuocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 304, Short.MAX_VALUE));

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
                        .addGap(0, 60, Short.MAX_VALUE));
        jPanel45Layout.setVerticalGroup(
                jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 142, Short.MAX_VALUE));

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
                        .addGap(0, 60, Short.MAX_VALUE));
        jPanel47Layout.setVerticalGroup(
                jPanel47Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 142, Short.MAX_VALUE));

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
                        .addGap(0, 919, Short.MAX_VALUE));
        jPanel49Layout.setVerticalGroup(
                jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 40, Short.MAX_VALUE));

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

        jComboBox1.setModel(
                new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
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
        jTextField1.setText("Nhập mã thuốc...");
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

        jTextField2.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        jTextField2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField2.setText("Số lượng...");
        jTextField2.setPreferredSize(new java.awt.Dimension(70, 32));
        jPanel52.add(jTextField2);

        jPanel4.add(jPanel52);

        jPanel57.setPreferredSize(new java.awt.Dimension(80, 100));

        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jButton1.setForeground(new java.awt.Color(0, 120, 92));
        jButton1.setText("Thêm");
        jButton1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 120, 92)));
        jButton1.setPreferredSize(new java.awt.Dimension(130, 32));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel57.add(jButton1);

        jPanel4.add(jPanel57);

        jPanel1.add(jPanel4);

        jPanel53.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(81, 219, 185), 5));
        jPanel53.setMinimumSize(new java.awt.Dimension(470, 352));
        jPanel53.setPreferredSize(new java.awt.Dimension(470, 362));
        jPanel53.setLayout(new java.awt.BorderLayout());

        jScrollPane1.setMinimumSize(new java.awt.Dimension(470, 217));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(470, 217));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {
                        { null, null, null, null, null, null, null, null },
                        { null, null, null, null, null, null, null, null },
                        { null, null, null, null, null, null, null, null },
                        { null, null, null, null, null, null, null, null }
                },
                new String[] {
                        "STT", "Mã thuốc", "Tên thuốc", "Danh mục", "Đơn vị", "Xuất xứ", "Số lượng", "Giá nhập"
                }));
        jTable1.setMinimumSize(new java.awt.Dimension(470, 217));
        jTable1.setPreferredSize(new java.awt.Dimension(470, 217));
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
                new Object[][] {
                        { null, null, null, null },
                        { null, null, null, null },
                        { null, null, null, null },
                        { null, null, null, null }
                },
                new String[] {
                        "STT", "Tên thuốc", "Số lượng", "Giá nhập"
                }));
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

        txtHoTenKH.setPreferredSize(new java.awt.Dimension(200, 40));
        jPanel38.add(txtHoTenKH);

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

        txtSdtKH1.setPreferredSize(new java.awt.Dimension(200, 40));
        txtSdtKH1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSdtKH1ActionPerformed(evt);
            }
        });
        jPanel59.add(txtSdtKH1);

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

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnTimKiemActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_btnTimKiemActionPerformed

    private void setThoiGianThuc() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        txtThoiGian.setText(now.format(formatter));
    }

    private void initEvent() {
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
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_jButton1ActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_jTextField2ActionPerformed

    private void jTextField1ActionPerformed(ActionEvent evt) {
        // Thực hiện hành động khi người dùng nhấn Enter trong JTextField
        String text = jTextField1.getText();
        System.out.println("Text field value: " + text);
    }

    private void btnThanhToanActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnThanhToanActionPerformed
        // if (isValidHoaDon() && isValidPayment()) {
        // if (MessageDialog.confirm(this, "Xác nhận thanh toán?", "Lập hóa đơn")) {
        // HoaDon hd = getInputHoaDon();
        // HD_CON.create(hd);
        // CTHD_CON.create(listCTHD);
        // MessageDialog.info(this, "Lập hóa đơn thành công!");
        //
        // // In hóa đơn
        // if (MessageDialog.confirm(this, "Bạn có muốn in hóa đơn không?", "In hóa
        // đơn")) {
        // new WritePDF().printHoaDon(hd, listCTHD);
        // }
        //
        // // Trở về trang hóa đơn
        // main.setPanel(new HoaDonPage(main));
        // }
        // }
    }// GEN-LAST:event_btnThanhToanActionPerformed

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
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JComboBox<String> cboxGioiTinhKH;
    private javax.swing.JButton jButton1;
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
    private javax.swing.JTextField jTextField2;
    private java.awt.Label label1;
    private javax.swing.JTextField txtDonGia;
    private javax.swing.JTextField txtHoTenKH;
    private javax.swing.JTextField txtMaHoaDon;
    private javax.swing.JTextField txtMaThuoc;
    private javax.swing.JTextField txtSdtKH;
    private javax.swing.JTextField txtSdtKH1;
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

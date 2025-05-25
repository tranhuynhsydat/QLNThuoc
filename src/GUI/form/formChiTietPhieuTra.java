/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package GUI.form;

import DAO.ThuocDAO;
import Entity.ChiTietPhieuTra;
import Entity.KhachHang;
import Entity.NhanVien;
import Entity.PhieuTra;
import Entity.Thuoc;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ADMIN
 */
public class formChiTietPhieuTra extends javax.swing.JPanel {
        private PhieuTra phieuTra;
        private List<ChiTietPhieuTra> chiTietPhieuTraList;
        private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        private final DecimalFormat currencyFormat = new DecimalFormat("#,### VNĐ");

        /**
         * Creates new form formChiTietPhieuTra
         */
        public formChiTietPhieuTra(PhieuTra phieuTra, List<ChiTietPhieuTra> chiTietPhieuTraList) {
                this.phieuTra = phieuTra;
                this.chiTietPhieuTraList = chiTietPhieuTraList;
                initComponents();
                loadData();
                loadDataToTable();
                configureTable();
        }

        private void loadData() {
                if (phieuTra == null) {
                        System.out.println("PhieuTra is null");
                        return;
                }

                // Update PhieuTra information
                KhachHang kh = phieuTra.getKhachHang();
                NhanVien nv = phieuTra.getNhanVien();
                tenKhachHang.setText(kh != null ? kh.getHoTen() : "N/A");
                sdtKhachHang.setText(kh != null ? kh.getSdt() : "N/A");
                tenNhanVien.setText(nv != null ? nv.getHoTen() : "N/A");
                thoiGianTra.setText(phieuTra.getNgayLap() != null
                                ? phieuTra.getNgayLap().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
                                                .format(dateFormatter)
                                : "N/A");
                TongTien.setText(currencyFormat.format(phieuTra.tinhTongTien()));
                maPT.setText(phieuTra.getMaPT() != null ? phieuTra.getMaPT() : "N/A");
                maHD.setText(phieuTra.getMaHD() != null ? phieuTra.getMaHD() : "N/A");
                lyDo.setText(phieuTra.getLyDo() != null ? phieuTra.getLyDo() : "Không có lý do"); // Assuming PhieuTra
                                                                                                  // has
                                                                                                  // getLyDo()
        }

        private void loadDataToTable() {
                DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
                model.setRowCount(0); // Xoá dữ liệu cũ

                ThuocDAO thuocDAO = new ThuocDAO();

                // Tạo map mã - tên thuốc
                Map<String, String> maTenThuocMap = new HashMap<>();
                for (Thuoc thuoc : thuocDAO.getAllThuoc()) {
                        maTenThuocMap.put(thuoc.getId(), thuoc.getTenThuoc());
                }

                int stt = 1;
                for (ChiTietPhieuTra ct : chiTietPhieuTraList) {
                        String tenThuoc = maTenThuocMap.get(ct.getMaThuoc()); // Không truy vấn DB nữa
                        int soLuong = ct.getSoLuong();
                        double donGia = ct.getDonGia();
                        double thanhTien = soLuong * donGia;

                        model.addRow(new Object[] {
                                        stt++,
                                        tenThuoc,
                                        soLuong,
                                        donGia,
                                        thanhTien
                        });
                }
        }

        private void configureTable() {
                // Prevent table editing
                jTable1.setDefaultEditor(Object.class, null);

                // Center align all cells
                DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
                centerRenderer.setHorizontalAlignment(JLabel.CENTER);

                for (int i = 0; i < jTable1.getColumnCount(); i++) {
                        jTable1.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
                }

                // Allow single row selection only
                jTable1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
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
        // Code">//GEN-BEGIN:initComponents
        private void initComponents() {

                jPanel1 = new javax.swing.JPanel();
                jPanel2 = new javax.swing.JPanel();
                jPanel10 = new javax.swing.JPanel();
                label1 = new java.awt.Label();
                label2 = new java.awt.Label();
                jPanel3 = new javax.swing.JPanel();
                jPanel4 = new javax.swing.JPanel();
                jPanel5 = new javax.swing.JPanel();
                label3 = new java.awt.Label();
                label4 = new java.awt.Label();
                jPanel6 = new javax.swing.JPanel();
                label5 = new java.awt.Label();
                jPanel7 = new javax.swing.JPanel();
                jPanel11 = new javax.swing.JPanel();
                jPanel12 = new javax.swing.JPanel();
                label6 = new java.awt.Label();
                label7 = new java.awt.Label();
                jPanel13 = new javax.swing.JPanel();
                jPanel14 = new javax.swing.JPanel();
                jPanel16 = new javax.swing.JPanel();
                label8 = new java.awt.Label();
                tenKhachHang = new java.awt.Label();
                label11 = new java.awt.Label();
                maPT = new java.awt.Label();
                jPanel15 = new javax.swing.JPanel();
                label10 = new java.awt.Label();
                sdtKhachHang = new java.awt.Label();
                label9 = new java.awt.Label();
                maHD = new java.awt.Label();
                jPanel17 = new javax.swing.JPanel();
                label12 = new java.awt.Label();
                thoiGianTra = new java.awt.Label();
                jPanel22 = new javax.swing.JPanel();
                label18 = new java.awt.Label();
                tenNhanVien = new java.awt.Label();
                jPanel18 = new javax.swing.JPanel();
                jScrollPane1 = new javax.swing.JScrollPane();
                jTable1 = new javax.swing.JTable();
                jPanel23 = new javax.swing.JPanel();
                label19 = new java.awt.Label();
                label20 = new java.awt.Label();
                lyDo = new javax.swing.JTextField();
                jPanel20 = new javax.swing.JPanel();
                jPanel21 = new javax.swing.JPanel();
                label16 = new java.awt.Label();
                label17 = new java.awt.Label();
                TongTien = new javax.swing.JTextField();

                setMaximumSize(new java.awt.Dimension(10000, 10000));
                setMinimumSize(new java.awt.Dimension(450, 600));
                setPreferredSize(new java.awt.Dimension(450, 600));

                jPanel1.setMaximumSize(new java.awt.Dimension(10000, 10000));
                jPanel1.setMinimumSize(new java.awt.Dimension(450, 600));
                jPanel1.setPreferredSize(new java.awt.Dimension(450, 600));
                jPanel1.setLayout(new java.awt.BorderLayout());

                jPanel2.setMaximumSize(new java.awt.Dimension(10000, 10000));
                jPanel2.setMinimumSize(new java.awt.Dimension(450, 50));
                jPanel2.setPreferredSize(new java.awt.Dimension(450, 50));
                jPanel2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 5, 10));

                jPanel10.setMaximumSize(new java.awt.Dimension(450, 30));
                jPanel10.setMinimumSize(new java.awt.Dimension(450, 30));
                jPanel10.setPreferredSize(new java.awt.Dimension(450, 30));
                jPanel10.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 20, -5));

                label1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
                label1.setPreferredSize(new java.awt.Dimension(183, 28));
                label1.setText("NHÀ THUỐC BA TRI");
                jPanel10.add(label1);

                label2.setAlignment(java.awt.Label.RIGHT);
                label2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
                label2.setMinimumSize(new java.awt.Dimension(200, 36));
                label2.setName(""); // NOI18N
                label2.setPreferredSize(new java.awt.Dimension(200, 36));
                label2.setText("PHIẾU TRẢ");
                jPanel10.add(label2);

                jPanel2.add(jPanel10);

                jPanel3.setMaximumSize(new java.awt.Dimension(450, 50));
                jPanel3.setMinimumSize(new java.awt.Dimension(450, 50));
                jPanel3.setPreferredSize(new java.awt.Dimension(450, 50));
                jPanel3.setRequestFocusEnabled(false);
                jPanel3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 5, -10));

                jPanel4.setMaximumSize(new java.awt.Dimension(450, 30));
                jPanel4.setMinimumSize(new java.awt.Dimension(450, 30));
                jPanel4.setNextFocusableComponent(label4);
                jPanel4.setPreferredSize(new java.awt.Dimension(450, 30));
                jPanel4.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 5, 0));

                jPanel5.setMaximumSize(new java.awt.Dimension(200, 30));
                jPanel5.setMinimumSize(new java.awt.Dimension(200, 30));
                jPanel5.setPreferredSize(new java.awt.Dimension(200, 30));
                jPanel5.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

                label3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
                label3.setText("Địa chỉ:");
                jPanel5.add(label3);

                label4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
                label4.setText("Ba Tri, Bến Tre");
                jPanel5.add(label4);

                jPanel4.add(jPanel5);

                jPanel6.setMaximumSize(new java.awt.Dimension(200, 30));
                jPanel6.setMinimumSize(new java.awt.Dimension(200, 30));
                jPanel6.setPreferredSize(new java.awt.Dimension(200, 30));
                jPanel6.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 5, 3));

                label5.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
                label5.setText("Dược phẩm");
                jPanel6.add(label5);

                jPanel4.add(jPanel6);

                jPanel3.add(jPanel4);

                jPanel7.setMaximumSize(new java.awt.Dimension(450, 30));
                jPanel7.setMinimumSize(new java.awt.Dimension(450, 30));
                jPanel7.setPreferredSize(new java.awt.Dimension(450, 30));
                jPanel7.setRequestFocusEnabled(false);

                jPanel11.setMaximumSize(new java.awt.Dimension(450, 30));
                jPanel11.setMinimumSize(new java.awt.Dimension(450, 30));
                jPanel11.setNextFocusableComponent(label4);
                jPanel11.setPreferredSize(new java.awt.Dimension(450, 30));
                java.awt.FlowLayout flowLayout1 = new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 5, 0);
                flowLayout1.setAlignOnBaseline(true);
                jPanel11.setLayout(flowLayout1);

                jPanel12.setMaximumSize(new java.awt.Dimension(200, 30));
                jPanel12.setMinimumSize(new java.awt.Dimension(200, 30));
                jPanel12.setPreferredSize(new java.awt.Dimension(200, 30));
                jPanel12.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, -5));

                label6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
                label6.setText("SĐT:");
                jPanel12.add(label6);

                label7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
                label7.setText("0800XXXXXX");
                jPanel12.add(label7);

                jPanel11.add(jPanel12);

                jPanel13.setMaximumSize(new java.awt.Dimension(200, 40));
                jPanel13.setMinimumSize(new java.awt.Dimension(200, 40));
                jPanel13.setPreferredSize(new java.awt.Dimension(200, 40));
                jPanel13.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));
                jPanel11.add(jPanel13);

                jPanel7.add(jPanel11);

                jPanel3.add(jPanel7);

                jPanel2.add(jPanel3);

                jPanel14.setMaximumSize(new java.awt.Dimension(450, 110));
                jPanel14.setMinimumSize(new java.awt.Dimension(450, 110));
                jPanel14.setPreferredSize(new java.awt.Dimension(450, 110));

                jPanel16.setMaximumSize(new java.awt.Dimension(450, 24));
                jPanel16.setMinimumSize(new java.awt.Dimension(450, 24));
                jPanel16.setName("Tên khách hàng:"); // NOI18N
                jPanel16.setPreferredSize(new java.awt.Dimension(450, 24));
                jPanel16.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEADING, 10, -5));

                label8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
                label8.setText("Khách hàng:");
                jPanel16.add(label8);

                tenKhachHang.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
                tenKhachHang.setMinimumSize(new java.awt.Dimension(150, 24));
                tenKhachHang.setName(""); // NOI18N
                tenKhachHang.setPreferredSize(new java.awt.Dimension(150, 24));
                jPanel16.add(tenKhachHang);

                label11.setAlignment(java.awt.Label.RIGHT);
                label11.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
                label11.setMaximumSize(new java.awt.Dimension(90, 24));
                label11.setMinimumSize(new java.awt.Dimension(90, 24));
                label11.setText("Mã PT:");
                jPanel16.add(label11);

                maPT.setAlignment(java.awt.Label.RIGHT);
                maPT.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
                jPanel16.add(maPT);

                jPanel14.add(jPanel16);

                jPanel15.setMaximumSize(new java.awt.Dimension(450, 24));
                jPanel15.setMinimumSize(new java.awt.Dimension(450, 24));
                jPanel15.setPreferredSize(new java.awt.Dimension(450, 24));
                jPanel15.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 10, -5));

                label10.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
                label10.setMinimumSize(new java.awt.Dimension(150, 24));
                label10.setName(""); // NOI18N
                label10.setText("Số điện thoại:");
                jPanel15.add(label10);

                sdtKhachHang.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
                sdtKhachHang.setMinimumSize(new java.awt.Dimension(140, 24));
                sdtKhachHang.setPreferredSize(new java.awt.Dimension(140, 24));
                jPanel15.add(sdtKhachHang);

                label9.setAlignment(java.awt.Label.RIGHT);
                label9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
                label9.setText("Mã HD:");
                jPanel15.add(label9);

                maHD.setAlignment(java.awt.Label.RIGHT);
                maHD.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
                jPanel15.add(maHD);

                jPanel14.add(jPanel15);

                jPanel17.setMaximumSize(new java.awt.Dimension(450, 24));
                jPanel17.setMinimumSize(new java.awt.Dimension(450, 24));
                jPanel17.setPreferredSize(new java.awt.Dimension(450, 24));
                jPanel17.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 10, -5));

                label12.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
                label12.setText("Thời gian trả:");
                jPanel17.add(label12);

                thoiGianTra.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
                jPanel17.add(thoiGianTra);

                jPanel14.add(jPanel17);

                jPanel22.setMaximumSize(new java.awt.Dimension(450, 24));
                jPanel22.setMinimumSize(new java.awt.Dimension(450, 24));
                jPanel22.setPreferredSize(new java.awt.Dimension(450, 24));
                jPanel22.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 10, -5));

                label18.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
                label18.setText("Nhân viên:");
                jPanel22.add(label18);

                tenNhanVien.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
                jPanel22.add(tenNhanVien);

                jPanel14.add(jPanel22);

                jPanel2.add(jPanel14);

                jPanel18.setMaximumSize(new java.awt.Dimension(410, 220));
                jPanel18.setMinimumSize(new java.awt.Dimension(410, 220));
                jPanel18.setPreferredSize(new java.awt.Dimension(410, 220));
                jPanel18.setLayout(new java.awt.BorderLayout());

                jScrollPane1.setMinimumSize(new java.awt.Dimension(400, 70));
                jScrollPane1.setPreferredSize(new java.awt.Dimension(400, 70));

                jTable1.setModel(new javax.swing.table.DefaultTableModel(
                                new Object[][] {
                                                { null, null, null, null, null },
                                                { null, null, null, null, null },
                                                { null, null, null, null, null },
                                                { null, null, null, null, null }
                                },
                                new String[] {
                                                "STT", "TÊN HÀNG", "SỐ LƯỢNG", "ĐƠN GIÁ", "THÀNH TIỀN"
                                }));
                jTable1.setMaximumSize(new java.awt.Dimension(410, 220));
                jTable1.setMinimumSize(new java.awt.Dimension(410, 220));
                jTable1.setPreferredSize(new java.awt.Dimension(410, 220));
                jScrollPane1.setViewportView(jTable1);
                if (jTable1.getColumnModel().getColumnCount() > 0) {
                        jTable1.getColumnModel().getColumn(0).setMinWidth(50);
                        jTable1.getColumnModel().getColumn(0).setPreferredWidth(50);
                        jTable1.getColumnModel().getColumn(0).setMaxWidth(50);
                }

                jPanel18.add(jScrollPane1, java.awt.BorderLayout.CENTER);

                jPanel2.add(jPanel18);

                jPanel23.setMaximumSize(new java.awt.Dimension(450, 30));
                jPanel23.setMinimumSize(new java.awt.Dimension(450, 30));
                jPanel23.setPreferredSize(new java.awt.Dimension(450, 30));

                label19.setAlignment(java.awt.Label.CENTER);
                label19.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
                label19.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
                label19.setMaximumSize(new java.awt.Dimension(140, 40));
                label19.setMinimumSize(new java.awt.Dimension(140, 40));
                label19.setText("Lý do:");
                jPanel23.add(label19);

                label20.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
                jPanel23.add(label20);

                lyDo.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
                lyDo.setHorizontalAlignment(javax.swing.JTextField.LEFT);
                lyDo.setToolTipText("");
                lyDo.setMaximumSize(new java.awt.Dimension(300, 24));
                lyDo.setMinimumSize(new java.awt.Dimension(300, 24));
                lyDo.setPreferredSize(new java.awt.Dimension(300, 24));
                lyDo.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                lyDoActionPerformed(evt);
                        }
                });
                jPanel23.add(lyDo);

                jPanel2.add(jPanel23);

                jPanel20.setMaximumSize(new java.awt.Dimension(450, 50));
                jPanel20.setMinimumSize(new java.awt.Dimension(450, 50));
                jPanel20.setPreferredSize(new java.awt.Dimension(450, 50));

                jPanel21.setMaximumSize(new java.awt.Dimension(450, 50));
                jPanel21.setMinimumSize(new java.awt.Dimension(450, 50));
                jPanel21.setPreferredSize(new java.awt.Dimension(450, 50));
                jPanel21.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 10));

                label16.setAlignment(java.awt.Label.CENTER);
                label16.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
                label16.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
                label16.setMaximumSize(new java.awt.Dimension(140, 40));
                label16.setMinimumSize(new java.awt.Dimension(140, 40));
                label16.setText("TỔNG TIỀN:");
                jPanel21.add(label16);
                label16.getAccessibleContext().setAccessibleName("Tổng tiền:");

                label17.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
                jPanel21.add(label17);

                TongTien.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
                TongTien.setHorizontalAlignment(javax.swing.JTextField.CENTER);
                TongTien.setMaximumSize(new java.awt.Dimension(200, 40));
                TongTien.setMinimumSize(new java.awt.Dimension(200, 40));
                TongTien.setPreferredSize(new java.awt.Dimension(200, 40));
                jPanel21.add(TongTien);

                javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
                jPanel20.setLayout(jPanel20Layout);
                jPanel20Layout.setHorizontalGroup(
                                jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel20Layout.createSequentialGroup()
                                                                .addGap(0, 0, Short.MAX_VALUE)
                                                                .addComponent(jPanel21,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(0, 0, Short.MAX_VALUE)));
                jPanel20Layout.setVerticalGroup(
                                jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel20Layout.createSequentialGroup()
                                                                .addGap(0, 0, Short.MAX_VALUE)
                                                                .addComponent(jPanel21,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(0, 0, Short.MAX_VALUE)));

                jPanel2.add(jPanel20);

                jPanel1.add(jPanel2, java.awt.BorderLayout.CENTER);

                javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
                this.setLayout(layout);
                layout.setHorizontalGroup(
                                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
                layout.setVerticalGroup(
                                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        }// </editor-fold>//GEN-END:initComponents

        private void lyDoActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_lyDoActionPerformed
                // TODO add your handling code here:
        }// GEN-LAST:event_lyDoActionPerformed

        // Variables declaration - do not modify//GEN-BEGIN:variables
        private javax.swing.JTextField TongTien;
        private javax.swing.JPanel jPanel1;
        private javax.swing.JPanel jPanel10;
        private javax.swing.JPanel jPanel11;
        private javax.swing.JPanel jPanel12;
        private javax.swing.JPanel jPanel13;
        private javax.swing.JPanel jPanel14;
        private javax.swing.JPanel jPanel15;
        private javax.swing.JPanel jPanel16;
        private javax.swing.JPanel jPanel17;
        private javax.swing.JPanel jPanel18;
        private javax.swing.JPanel jPanel2;
        private javax.swing.JPanel jPanel20;
        private javax.swing.JPanel jPanel21;
        private javax.swing.JPanel jPanel22;
        private javax.swing.JPanel jPanel23;
        private javax.swing.JPanel jPanel3;
        private javax.swing.JPanel jPanel4;
        private javax.swing.JPanel jPanel5;
        private javax.swing.JPanel jPanel6;
        private javax.swing.JPanel jPanel7;
        private javax.swing.JScrollPane jScrollPane1;
        private javax.swing.JTable jTable1;
        private java.awt.Label label1;
        private java.awt.Label label10;
        private java.awt.Label label11;
        private java.awt.Label label12;
        private java.awt.Label label16;
        private java.awt.Label label17;
        private java.awt.Label label18;
        private java.awt.Label label19;
        private java.awt.Label label2;
        private java.awt.Label label20;
        private java.awt.Label label3;
        private java.awt.Label label4;
        private java.awt.Label label5;
        private java.awt.Label label6;
        private java.awt.Label label7;
        private java.awt.Label label8;
        private java.awt.Label label9;
        private javax.swing.JTextField lyDo;
        private java.awt.Label maHD;
        private java.awt.Label maPT;
        private java.awt.Label sdtKhachHang;
        private java.awt.Label tenKhachHang;
        private java.awt.Label tenNhanVien;
        private java.awt.Label thoiGianTra;
        // End of variables declaration//GEN-END:variables
}

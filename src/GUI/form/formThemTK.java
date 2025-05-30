/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package GUI.form;

import DAO.NhanVienDAO;
import DAO.TaiKhoanDAO;
import Entity.NhanVien;
import Entity.TaiKhoan;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

/**
 *
 * @author Admin
 */
public class formThemTK extends javax.swing.JDialog {

    Map<String, NhanVien> nhanVienMap = new HashMap<>();

    /**
     * Creates new form formThemNV
     */
    public formThemTK(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        loadComboboxData();
    }

    private void loadComboboxData() {
        // Lấy dữ liệu từ cơ sở dữ liệu và thêm vào comboNhanVien
        List<NhanVien> nhanVienList = NhanVienDAO.getAllNhanVien();
        for (NhanVien nv : nhanVienList) {
            cboNhanVien.addItem(nv.getHoTen());  // Thêm tên nhân viên vào ComboBox
            nhanVienMap.put(nv.getHoTen(), nv);  // Lưu ánh xạ giữa tên và đối tượng NhanVien vào Map
        }
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
        roundPanel1 = new Swing.RoundPanel();
        topRoundedPanel1 = new Swing.TopRoundedPanel();
        lblThemTK = new javax.swing.JLabel();
        bottomRoundedPanel1 = new Swing.BottomRoundedPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        txtUserName = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        txtPassword = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        txtRePassword = new javax.swing.JTextField();
        jPanel8 = new javax.swing.JPanel();
        jPanel18 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jPanel19 = new javax.swing.JPanel();
        cboNhanVien = new javax.swing.JComboBox<String>();
        jPanel9 = new javax.swing.JPanel();
        jPanel20 = new javax.swing.JPanel();
        jPanel21 = new javax.swing.JPanel();
        bottomRoundedPanel2 = new Swing.BottomRoundedPanel();
        btnHuy = new javax.swing.JButton();
        btnThem = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(255, 0, 0));

        jPanel1.setBackground(new java.awt.Color(81, 219, 185));

        roundPanel1.setBackground(new java.awt.Color(0, 120, 92));
        roundPanel1.setLayout(new java.awt.BorderLayout());

        topRoundedPanel1.setPreferredSize(new java.awt.Dimension(569, 75));
        topRoundedPanel1.setLayout(new java.awt.BorderLayout());

        lblThemTK.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblThemTK.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblThemTK.setText("Thêm tài khoản");
        lblThemTK.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblThemTK.setMaximumSize(new java.awt.Dimension(190, 32));
        lblThemTK.setMinimumSize(new java.awt.Dimension(190, 32));
        lblThemTK.setPreferredSize(new java.awt.Dimension(190, 32));
        topRoundedPanel1.add(lblThemTK, java.awt.BorderLayout.CENTER);

        roundPanel1.add(topRoundedPanel1, java.awt.BorderLayout.PAGE_START);

        bottomRoundedPanel1.setPreferredSize(new java.awt.Dimension(569, 400));
        bottomRoundedPanel1.setLayout(new java.awt.BorderLayout());

        jPanel2.setPreferredSize(new java.awt.Dimension(567, 350));
        jPanel2.setLayout(new javax.swing.BoxLayout(jPanel2, javax.swing.BoxLayout.Y_AXIS));

        jPanel3.setPreferredSize(new java.awt.Dimension(622, 50));
        jPanel3.setLayout(new java.awt.BorderLayout());

        jPanel10.setPreferredSize(new java.awt.Dimension(150, 50));
        jPanel10.setLayout(new java.awt.BorderLayout());
        jPanel3.add(jPanel10, java.awt.BorderLayout.LINE_START);

        jPanel11.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 17));
        jPanel3.add(jPanel11, java.awt.BorderLayout.CENTER);

        jPanel2.add(jPanel3);

        jPanel5.setPreferredSize(new java.awt.Dimension(622, 50));
        jPanel5.setLayout(new java.awt.BorderLayout());

        jPanel12.setPreferredSize(new java.awt.Dimension(150, 50));
        jPanel12.setLayout(new java.awt.BorderLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Tên tài khoản:");
        jPanel12.add(jLabel1, java.awt.BorderLayout.CENTER);

        jPanel5.add(jPanel12, java.awt.BorderLayout.LINE_START);

        jPanel13.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 17));

        txtUserName.setPreferredSize(new java.awt.Dimension(350, 22));
        jPanel13.add(txtUserName);

        jPanel5.add(jPanel13, java.awt.BorderLayout.CENTER);

        jPanel2.add(jPanel5);

        jPanel6.setPreferredSize(new java.awt.Dimension(622, 50));
        jPanel6.setLayout(new java.awt.BorderLayout());

        jPanel14.setPreferredSize(new java.awt.Dimension(150, 50));
        jPanel14.setLayout(new java.awt.BorderLayout());

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel11.setText("Mật khẩu:");
        jPanel14.add(jLabel11, java.awt.BorderLayout.CENTER);

        jPanel6.add(jPanel14, java.awt.BorderLayout.LINE_START);

        jPanel15.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 17));

        txtPassword.setPreferredSize(new java.awt.Dimension(350, 22));
        jPanel15.add(txtPassword);

        jPanel6.add(jPanel15, java.awt.BorderLayout.CENTER);

        jPanel2.add(jPanel6);

        jPanel7.setPreferredSize(new java.awt.Dimension(622, 50));
        jPanel7.setLayout(new java.awt.BorderLayout());

        jPanel16.setPreferredSize(new java.awt.Dimension(150, 50));
        jPanel16.setLayout(new java.awt.BorderLayout());

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel12.setText("Nhập lại mật khẩu:");
        jPanel16.add(jLabel12, java.awt.BorderLayout.CENTER);

        jPanel7.add(jPanel16, java.awt.BorderLayout.LINE_START);

        jPanel17.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 17));

        txtRePassword.setPreferredSize(new java.awt.Dimension(350, 22));
        jPanel17.add(txtRePassword);

        jPanel7.add(jPanel17, java.awt.BorderLayout.CENTER);

        jPanel2.add(jPanel7);

        jPanel8.setPreferredSize(new java.awt.Dimension(622, 50));
        jPanel8.setLayout(new java.awt.BorderLayout());

        jPanel18.setPreferredSize(new java.awt.Dimension(150, 50));
        jPanel18.setLayout(new java.awt.BorderLayout());

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel13.setText("Nhân viên:");
        jPanel18.add(jLabel13, java.awt.BorderLayout.CENTER);

        jPanel8.add(jPanel18, java.awt.BorderLayout.LINE_START);

        jPanel19.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 17));

        cboNhanVien.setPreferredSize(new java.awt.Dimension(350, 22));
        jPanel19.add(cboNhanVien);

        jPanel8.add(jPanel19, java.awt.BorderLayout.CENTER);

        jPanel2.add(jPanel8);

        jPanel9.setPreferredSize(new java.awt.Dimension(622, 50));
        jPanel9.setLayout(new java.awt.BorderLayout());

        jPanel20.setPreferredSize(new java.awt.Dimension(150, 50));
        jPanel20.setLayout(new java.awt.BorderLayout());
        jPanel9.add(jPanel20, java.awt.BorderLayout.LINE_START);

        jPanel21.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 17));
        jPanel9.add(jPanel21, java.awt.BorderLayout.CENTER);

        jPanel2.add(jPanel9);

        bottomRoundedPanel1.add(jPanel2, java.awt.BorderLayout.CENTER);

        bottomRoundedPanel2.setPreferredSize(new java.awt.Dimension(567, 80));
        bottomRoundedPanel2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 150, 25));

        btnHuy.setBackground(new java.awt.Color(255, 103, 102));
        btnHuy.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnHuy.setForeground(new java.awt.Color(255, 255, 255));
        btnHuy.setText("Huỷ");
        btnHuy.setPreferredSize(new java.awt.Dimension(90, 35));
        btnHuy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHuyActionPerformed(evt);
            }
        });
        bottomRoundedPanel2.add(btnHuy);

        btnThem.setBackground(new java.awt.Color(15, 204, 102));
        btnThem.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnThem.setForeground(new java.awt.Color(255, 255, 255));
        btnThem.setText("Thêm");
        btnThem.setPreferredSize(new java.awt.Dimension(90, 35));
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });
        bottomRoundedPanel2.add(btnThem);

        bottomRoundedPanel1.add(bottomRoundedPanel2, java.awt.BorderLayout.PAGE_END);

        roundPanel1.add(bottomRoundedPanel1, java.awt.BorderLayout.PAGE_END);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(roundPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 622, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(roundPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 532, Short.MAX_VALUE)
                .addContainerGap())
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        // Lấy thông tin từ các trường nhập liệu
        String userName = txtUserName.getText().trim();
        String passWord = txtPassword.getText().trim();
        String rePassWord = txtRePassword.getText().trim();
        String nvName = (String) cboNhanVien.getSelectedItem();  // Lấy tên nhân viên từ ComboBox

        // Tìm đối tượng NhanVien từ tên nhân viên
        NhanVien maNV = nhanVienMap.get(nvName);  // Tìm đối tượng NhanVien dựa trên tên

        // Kiểm tra nếu nhân viên đã có tài khoản
        // Kiểm tra thông tin nhập
        if (userName.isEmpty() || passWord.isEmpty() || rePassWord.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin tài khoản!");
            return;
        }
        // Kiểm tra tài khoản đã tồn tại
        if (TaiKhoanDAO.kiemTraTenTaiKhoanTonTai(userName)) {
            JOptionPane.showMessageDialog(this, "Tên tài khoản đã tồn tại. Vui lòng chọn tên khác!");
            return;
        }
        // Kiểm tra nếu nhân viên đã có tài khoản
        if (TaiKhoanDAO.kiemTraNhanVienDaCoTaiKhoan(maNV.getId())) {
            JOptionPane.showMessageDialog(this, "Nhân viên này đã có tài khoản!");
            return;
        }

        if (!passWord.equals(rePassWord)) {
            JOptionPane.showMessageDialog(this, "Mật khẩu không khớp. Vui lòng nhập lại!");
            return;
        }

        // Kiểm tra độ mạnh của mật khẩu
        String pattern = "^(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-={}\\[\\]:;\"'<>,.?/~`]).{8,}$";
        if (!passWord.matches(pattern)) {
            JOptionPane.showMessageDialog(this,
                "Mật khẩu phải có ít nhất 8 ký tự, bao gồm ít nhất một chữ in hoa, một chữ số và một ký tự đặc biệt!");
            return;
        }


        // Tạo đối tượng tài khoản
        TaiKhoan tk = new TaiKhoan(
                TaiKhoanDAO.TaoMaTaiKhoan(), // Sinh mã tài khoản tự động
                userName,
                passWord,
                maNV // truyền vào đối tượng NhanVien
        );

        // Thêm tài khoản vào cơ sở dữ liệu
        if (TaiKhoanDAO.Them(tk)) {
            JOptionPane.showMessageDialog(this, "Thêm tài khoản thành công!");
            dispose();  // Đóng form thêm tài khoản
        } else {
            JOptionPane.showMessageDialog(this, "Có lỗi xảy ra khi sửa tài khoản.");
        }
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnHuyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHuyActionPerformed
        dispose();        // TODO add your handling code here:
    }//GEN-LAST:event_btnHuyActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(formThemTK.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(formThemTK.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(formThemTK.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(formThemTK.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                formThemTK dialog = new formThemTK(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private Swing.BottomRoundedPanel bottomRoundedPanel1;
    private Swing.BottomRoundedPanel bottomRoundedPanel2;
    private javax.swing.JButton btnHuy;
    private javax.swing.JButton btnThem;
    private javax.swing.JComboBox<String> cboNhanVien;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
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
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JLabel lblThemTK;
    private Swing.RoundPanel roundPanel1;
    private Swing.TopRoundedPanel topRoundedPanel1;
    private javax.swing.JTextField txtPassword;
    private javax.swing.JTextField txtRePassword;
    private javax.swing.JTextField txtUserName;
    // End of variables declaration//GEN-END:variables
}

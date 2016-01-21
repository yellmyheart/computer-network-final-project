import java.io.ObjectOutputStream;
import java.net.Socket;
import java.awt.Toolkit;
import java.awt.event.*;
import java.util.ArrayList;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.lang.Thread;
import java.awt.*;
import java.util.concurrent.ConcurrentLinkedQueue;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author yuchiang
 */
public class LoginFrame extends javax.swing.JFrame implements Runnable{
    static Socket socket;
    static ConcurrentLinkedQueue<Header> queue;
    static ConcurrentLinkedQueue<byte[]> filequeue;
<<<<<<< HEAD
    static MessagerGUI m;
    static User user;
=======
>>>>>>> 37c522c15fbdc823e208388cd7edad7a040d933c
    /**
     * Creates new form LoginFrame
     */
    public LoginFrame() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        jLabel4 = new javax.swing.JLabel();
        userField = new javax.swing.JTextField();
        LoginButton = new javax.swing.JButton();
        passwdField = new javax.swing.JPasswordField();
        UserLabel = new javax.swing.JLabel();
        PasswdLabel = new javax.swing.JLabel();
        TitleLabel = new javax.swing.JLabel();
        SignupButton = new javax.swing.JButton();

        jLabel4.setText("jLabel4");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        userField.setForeground(new java.awt.Color(153, 153, 153));

        LoginButton.setText("Login");
        LoginButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LoginButtonActionPerformed(evt);
            }
        });

        UserLabel.setText("User");

        PasswdLabel.setText("Password");

        TitleLabel.setText("Welcome to Messenger!");

        SignupButton.setText("Sign up");
        SignupButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SignupButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(106, 106, 106)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(SignupButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(LoginButton))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(UserLabel)
                            .addComponent(PasswdLabel))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(passwdField)
                            .addComponent(userField, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(TitleLabel)))
                .addContainerGap(112, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addComponent(TitleLabel)
                .addGap(38, 38, 38)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(userField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(UserLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(passwdField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(PasswdLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LoginButton)
                    .addComponent(SignupButton))
                .addContainerGap(90, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>

    public void close(){
        WindowEvent winClosingEvent = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(winClosingEvent);
    }
    private void LoginButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        String name = userField.getText();
        String password = passwdField.getText();
        Header h = new Header();
        User u = new User(name,password);
        h.setUser(u);
        h.setType(Command.LOGIN);
        h.setOwner(u.getUsername());
        ObjectOutputStream objectOutput;
        try{
            objectOutput = new ObjectOutputStream(socket.getOutputStream());
            objectOutput.writeObject(h);
        }catch(IOException reg){
            System.out.println("login FAILURE_REG");
        }
    }
    public void setqueue(ConcurrentLinkedQueue<Header> queue){
        this.queue = queue;
    }
    public void setfile(ConcurrentLinkedQueue<byte[]> by){
        this.filequeue = by;
    }
    public void reaction(){
        Header temp_h;
        temp_h = queue.poll();
        if(temp_h == null)
            return;
        System.out.println(temp_h.getType());
        if(temp_h.getType() == Command.SUCCESS_LOG || temp_h.getType() == Command.SUCCESS_REG){
          System.out.println("SUCCESS_REG");
          close();
          Curinfo c = temp_h.c;
          ArrayList<String> t;
          user = temp_h.getUser();
          m = new MessagerGUI(c,temp_h.getUser(),socket);
          if(temp_h.getUser() != null){
              User temp_user = temp_h.getUser();
              ArrayList<Integer> roomlist  = temp_user.roomlist;
          }
          m.setVisible(true);
        }else if(temp_h.getType() == Command.FAILURE_LOG){
          Warning w = new Warning("Wrong username or password, please try again.");
          w.setVisible(true);
        }else if(temp_h.getType() == Command.FAILURE_REG){
          Warning w = new Warning("Someone else use the same info, please try again.");
          w.setVisible(true);
        }else if(temp_h.getType() == Command.SEND_MSG){
            System.out.println("SEND_MSG");
            //
            String sender = temp_h.getOwner();
            String messege = temp_h.getMsg();
            m.getMessage(sender,messege);

        }else if(temp_h.getType() == Command.MSG_SYNC){
            String sender = temp_h.getOwner();
            String messege = temp_h.getMsg();
            m.getMessage(sender,messege);            
        }
        /*else if(temp_h.getType() == Command.SEND_FILE){
            System.out.println("SEND_FILE");
            //
            Curinfo c = temp_h.c;
     //       SingleChat nowRoom = c.getRoom();
            // add addFile API
            return;
        }*/
        return;
    }
    private void SignupButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        System.out.println("Sign");
        String name = userField.getText();
        String password = passwdField.getText();
        Header h = new Header();
        User u = new User(name,password);
        h.setUser(u);
        h.setType(Command.REGISTER);
        h.setOwner(u.getUsername());
        ObjectOutputStream objectOutput;
        try{
            objectOutput = new ObjectOutputStream(socket.getOutputStream());
            objectOutput.writeObject(h);
        }catch(IOException reg){
            System.out.println("reg FAILURE_REG");
        }
    }
    /**
     * @param args the command line arguments
     */
    public void setSocket(Socket s){
        this.socket = s;
    }
    public void run(){
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(LoginFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LoginFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LoginFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoginFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
                new LoginFrame().setVisible(true);
    }
   // public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        
        //</editor-fold>

        /* Create and display the form */
     //   java.awt.EventQueue.invokeLater(new Runnable() {
       //     public void run() {
                
   //         }
    //    });
    //}

    // Variables declaration - do not modify
    private javax.swing.JButton LoginButton;
    private javax.swing.JLabel PasswdLabel;
    private javax.swing.JButton SignupButton;
    private javax.swing.JLabel TitleLabel;
    private javax.swing.JLabel UserLabel;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPasswordField passwdField;
    private javax.swing.JTextField userField;
    // End of variables declaration
}
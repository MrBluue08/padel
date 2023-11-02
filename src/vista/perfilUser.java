package vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import Controllers.*;
import Models.usuarios;

public class perfilUser {


    public JPanel panel1;
    public JLabel dniTxt;
    public JLabel nameTxt;
    public JLabel mailTxt;
    private JButton editBtn;
    private JButton backBtn;
    private JTextField editName;
    private JTextField editMail;
    private JButton guardarBtn;
    public JLabel passwdTxt;
    private JPasswordField editPasswd;


    controlador controlador = new controlador();
    Icon icon = new ImageIcon("img/backArrow.png");
    public void viewMode(){
        editMail.setVisible(false);
        editName.setVisible(false);
        editPasswd.setVisible(false);
        guardarBtn.setVisible(false);
        dniTxt.setVisible(true);
        nameTxt.setVisible(true);
        mailTxt.setVisible(true);
        passwdTxt.setVisible(true);
        editBtn.setVisible(true);
        backBtn.setIcon(icon);
    }

    public void editMode(){
        editBtn.setVisible(false);
        guardarBtn.setVisible(true);
        editMail.setVisible(true);
        editName.setVisible(true);
        editPasswd.setVisible(true);
        nameTxt.setVisible(false);
        mailTxt.setVisible(false);
        passwdTxt.setVisible(false);
        editMail.setText(mailTxt.getText());
        editName.setText(nameTxt.getText());
        String passwd = (String) passwdTxt.getClientProperty("passwd");
        editPasswd.setText(passwd);
    }

    public perfilUser() {
        editBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editMode();
            }
        });
        guardarBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] nombre = editName.getText().split(" ");
                try {
                    controlador.updateUser(dniTxt.getText(),editMail.getText(),nombre[0],nombre[1], editPasswd.getText(),true);
                    viewMode();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                usuarios user = new usuarios();
                try{
                    String[] nombre = editName.getText().split(" ");
                    user = new usuarios(dniTxt.getText(), editMail.getText(),nombre[0],nombre[1], editPasswd.getText());
                }catch (ArrayIndexOutOfBoundsException ex){
                    String[] nombre = nameTxt.getText().split(" ");
                    String passwd = (String) passwdTxt.getClientProperty("passwd");
                    user = new usuarios(dniTxt.getText(),mailTxt.getText(),nombre[0],nombre[1],passwd);
                }
                controlador.openMainUser(user);
            }
        });
    }
}

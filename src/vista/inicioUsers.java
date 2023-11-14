package vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.image.BufferedImage;

import Controllers.*;

public class inicioUsers {
    public JPanel panel1;
    private JTextField dniTxt;
    private JPasswordField passwdTxt;
    private JLabel dniLbl;
    private JLabel passwdLbl;
    private JButton startSesionBtn;
    public JLabel errorLbl;
    private JButton adminBtn;
    private JLabel logoPos;
    Icon icon = new ImageIcon("img/puntos.png");
    Icon logo = new ImageIcon("img/padelLogo.png");

    controlador c = new controlador();



    public inicioUsers() {
        adminBtn.setIcon(icon);
        logoPos.setIcon(logo);
        startSesionBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    c.checkUser(dniTxt.getText(),passwdTxt.getText());
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        adminBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c.openInicioAdmin();
            }
        });
    }
}

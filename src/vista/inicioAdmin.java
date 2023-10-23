package vista;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import Controllers.controlador;

public class inicioAdmin extends JFrame{
    controlador c = new controlador();
    public JPanel panel1;
    private JLabel tituloLbl;
    private JLabel idLbl;

    private JLabel paswdLbl;
    private JTextField idTxt;
    private JButton inicioBtn;
    private JPasswordField passwdTxt;

    public inicioAdmin() {
        inicioBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                try
                {
                    c.checkAdmin(getIdTxt().getText(),getPasswdTxt().getText());
                }catch (SQLException ex)
                {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    public JTextField getIdTxt() {
        return idTxt;
    }

    public void setIdTxt(JTextField idTxt) {
        this.idTxt = idTxt;
    }

    public JPasswordField getPasswdTxt() {
        return passwdTxt;
    }

    public void setPasswdTxt(JPasswordField passwdTxt) {
        this.passwdTxt = passwdTxt;
    }
}

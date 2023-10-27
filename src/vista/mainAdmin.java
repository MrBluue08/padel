package vista;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import Controllers.controlador;

public class mainAdmin {
    controlador c = new controlador();
    private JButton pistasBtn;
    public JPanel panel1;
    private JButton userBtn;
    private JButton closeBtn;

    public mainAdmin() {
        pistasBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    c.openPistas();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        userBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    c.openListUsers();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}

package vista;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Controllers.*;

public class index {
    controlador c = new controlador();
    private JButton startUserBtn;
    public JPanel panel1;
    private JButton startAdminBtn;

    public index() {
        startUserBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c.openInicioUsers(false,"");
            }
        });
        startAdminBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c.openInicioAdmin();
            }
        });
    }
}

package vista;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Controllers.*;
import Models.usuarios;

public class mainUsuarios {
    private JButton miPerfilBtn;
    public JPanel panel1;
    private JButton reservasBtn;
    controlador c = new controlador();
    public static usuarios user = new usuarios();



    public mainUsuarios() {
        miPerfilBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c.openPerfil(user);
            }
        });

    }
}

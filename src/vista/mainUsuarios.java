package vista;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import Controllers.*;
import Models.usuarios;

public class mainUsuarios {
    private JButton miPerfilBtn;
    public JPanel panel1;
    private JButton reservasBtn;
    private JButton closeBtn;
    controlador c = new controlador();
    public static usuarios user = new usuarios();



    public mainUsuarios() {
        miPerfilBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c.openPerfil(user);
            }
        });

        closeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                 c.cerrarSesion();
            }
        });
        reservasBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    c.openReservas();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }
}

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
    private JButton misReservasButton;
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
                    c.openReservas(null,"09:00","10:00", new java.util.Date(),user);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        misReservasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    c.openMisReservas(user);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }
}

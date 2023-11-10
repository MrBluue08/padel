package vista;

import Controllers.controlador;
import Models.usuarios;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

public class misReservas {

    controlador c = new controlador();
    public JPanel panel1;
    private JLabel misReservarLBL;
    private JList list1;
    private JButton cancelarBtn;
    private JButton backBtn;
    Icon icon = new ImageIcon("img/backArrow.png");

    public misReservas(usuarios user) throws SQLException {
        backBtn.setIcon(icon);
        ArrayList<ArrayList<String>> reservas = c.selectReservas(user.getDni());
        DefaultListModel<String> modelo = new DefaultListModel<>();
        for (ArrayList<String> reserva:reservas) {
            String reservaTxt = String.join(", ",reserva);

            modelo.addElement(reservaTxt);
        }
        list1.setModel(modelo);

        cancelarBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] info = list1.getSelectedValue().toString().split(",");
                popUp ventana = new popUp("Estas seguro de que quieres cancelar la siguiente reserva: "+info[0]+ " para el " +info[1]+ " de " +info[2] +" a"+ info[3] , info[0], user);
                placeHolder placeHolder = new placeHolder();
                placeHolder.setContentPane(ventana.panel1);
                placeHolder.setSize(800,250);
                placeHolder.setVisible(true);

            }
        });
        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c.openMainUser(user);
            }
        });
    }
}

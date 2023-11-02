package vista;

import javax.swing.*;
import Controllers.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

public class popUp {
    controlador c = new controlador();
    public JPanel panel1;
    private JButton acceptBtn;
    private JLabel lblTexto;

    public popUp(String mensaje, ArrayList<String> datos){
        lblTexto.setText(mensaje);

        acceptBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    c.reservar(datos);
                    JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(panel1);
                    frame.dispose();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }
}

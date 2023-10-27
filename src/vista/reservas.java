package vista;

import com.toedter.calendar.JCalendar;
import javax.swing.*;
import Controllers.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class reservas {
    public JPanel panel1;
    private JScrollPane contenedorScroll;
    private JPanel container = new JPanel(new WrapLayout());
    private controlador c = new controlador();

    public reservas() throws SQLException{
        ResultSet pistas = c.pistasReservar();

        while(pistas.next()){
            JButton boton = new JButton();
            JPanel panel = new JPanel();
            panel.add(boton);
            container.add(panel);
        }
        contenedorScroll.add(container);
    }
}

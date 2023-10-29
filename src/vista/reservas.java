package vista;

import com.toedter.calendar.JCalendar;
import javax.swing.*;
import Controllers.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class reservas {
    public JPanel panel1;
    private JPanel lista;
    private JScrollPane contenedorScroll;
    private controlador c = new controlador();

    public reservas() throws SQLException{
        lista.setLayout(new GridLayout(0,3,0,10));
//        listado.setLayout(new FlowLayout(FlowLayout.LEFT,10,10));

        ResultSet pistas = c.pistasReservar();
        while(pistas.next()){
            JButton boton = new JButton("Hey");
            JPanel panel = new JPanel();
            panel.add(boton);
            lista.add(panel);
        }

    }


}

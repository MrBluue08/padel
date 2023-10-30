package vista;

import com.toedter.calendar.JCalendar;
import javax.swing.*;
import Controllers.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;

public class reservas {
    public JPanel panel1;
    private JPanel lista;
    private JScrollPane contenedorScroll;
    private controlador c = new controlador();

    public reservas() throws SQLException{
        lista.setLayout(new GridLayout(0,3,0,10));

        ResultSet pistas = c.pistasReservar();
        while(pistas.next()){
            JPanel panel = new JPanel(new GridLayout(3,2,5,5));

            JButton boton = new JButton(pistas.getString(1));
            JCalendar calendario = new JCalendar();

            ArrayList<String> listaHoras = new ArrayList<>();
            for (int hora = 9; hora <= 22; hora++) {
                for (int minuto = 0; minuto <= 30; minuto += 30) {
                    String horaStr = String.format("%02d:%02d", hora, minuto);
                    listaHoras.add(horaStr);
                }
            }

            JComboBox<String> horaInicio = new JComboBox<>(listaHoras.toArray(new String[0]));
            JComboBox<String> horaFin = new JComboBox<>(listaHoras.toArray(new String[0]));

            boton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e){
                    if(calendario.isVisible()){
                        calendario.setVisible(false);
                    }else{
                        calendario.setVisible(true);
                    }
                }
            });
            panel.add(boton);
            panel.add(calendario);
            calendario.setVisible(false);
            panel.add(horaInicio);
            panel.add(horaFin);
            lista.add(panel);
        }

    }


}

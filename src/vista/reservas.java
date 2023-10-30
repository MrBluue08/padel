package vista;

import com.toedter.calendar.JCalendar;
import javax.swing.*;
import Controllers.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class reservas {
    public JPanel main;
    private controlador c = new controlador();


    public reservas() throws SQLException{
        main.setLayout(new FlowLayout());

        ArrayList<JButton> botones = new ArrayList<>();
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

        JPanel panelPistas = new JPanel(new GridLayout(2,2));


        main.add(calendario);
        main.add(horaInicio);
        main.add(horaFin);
        main.add(panelPistas);
        panelPistas.setVisible(false);

        horaFin.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e){
                for (JButton boton:botones) {
                    panelPistas.remove(boton);
                }
                panelPistas.setVisible(true);
                ArrayList<String> pistas = null;
                try {
                    pistas = c.pistasDisponibles(horaInicio.getSelectedItem().toString(), horaFin.getSelectedItem().toString());
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                for (String pista: pistas) {
                    JButton boton = new JButton(pista);
                    botones.add(boton);
                    panelPistas.add(boton);
                }
            }
        });

    }




}

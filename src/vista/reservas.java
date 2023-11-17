package vista;

import Models.usuarios;
import com.toedter.calendar.JCalendar;
import javax.swing.*;
import Controllers.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class reservas {
    public JPanel panel1;
    private JPanel main;
    private JButton backBtn;
    private JScrollPane botones;
    private JPanel panelBtns;
    private controlador c = new controlador();
    Icon icon = new ImageIcon("img/backArrow.png");


    public reservas(ArrayList<JButton> buttons, String start, String end, Date fecha, usuarios user){
        main.setLayout(new FlowLayout());

        JCalendar calendario = new JCalendar();
        calendario.setDate(fecha);
        calendario.setForeground(Color.decode("#0C5149"));



        JButton pistasDisponiblesBtn = new JButton("Pistas disponibles");
        ArrayList<String> listaHoras = new ArrayList<>();
        for (int hora = 9; hora <= 22; hora++) {
            for (int minuto = 0; minuto <= 30; minuto += 30) {
                String horaStr = String.format("%02d:%02d", hora, minuto);
                listaHoras.add(horaStr);
            }
        }

        JComboBox<String> horaInicio = new JComboBox<>(listaHoras.toArray(new String[0]));
        JComboBox<String> horaFin = new JComboBox<>(listaHoras.toArray(new String[0]));
        horaInicio.setSelectedItem(start);
        horaFin.setSelectedItem(end);
        JPanel panelPistas = new JPanel(new GridLayout(2,2));

        try{
            for (JButton boton:buttons) {
                panelPistas.add(boton);
            }
        } catch (NullPointerException e){}

        main.add(calendario);
        main.add(horaInicio);
        main.add(horaFin);
        main.add(pistasDisponiblesBtn);
        panelBtns.add(panelPistas);
        backBtn.setIcon(icon);
        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c.openMainUser(user);
            }
        });

        pistasDisponiblesBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<String> pistas = null;
                try {
                    pistas = c.pistasDisponibles(calendario.getDate(), horaInicio.getSelectedItem().toString());
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                } catch (ParseException ex) {
                    throw new RuntimeException(ex);
                }
                ArrayList<JButton> botones = new ArrayList<>();
                for (String pista:pistas) {
                    JButton boton = new JButton(pista);
                    boton.setBackground(Color.decode("#07F9A2"));
                    boton.setForeground(Color.decode("#0C5149"));

                    botones.add(boton);
                    boton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            ArrayList<String> datos = new ArrayList<>();
                            SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
                            String fecha = formatoFecha.format(calendario.getDate());
                            datos.add(fecha);
                            datos.add(horaInicio.getSelectedItem().toString());
                            datos.add(horaFin.getSelectedItem().toString());
                            datos.add(user.getDni());
                            datos.add(boton.getText());
                            popUp ventana = new popUp("Reservar pista "+boton.getText()+" de "+horaInicio.getSelectedItem().toString()
                                    +" a "+horaFin.getSelectedItem().toString()+" el "+fecha+ "?",datos);
                            placeHolder placeHolder = new placeHolder();
                            placeHolder.setContentPane(ventana.panel1);
                            placeHolder.setSize(500,250);
                            placeHolder.setVisible(true);

                        }
                    });
                }
                try {
                    c.openReservas(botones, horaInicio.getSelectedItem().toString(), horaFin.getSelectedItem().toString(),calendario.getDate(), user);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

    }




}

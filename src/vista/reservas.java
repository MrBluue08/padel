package vista;

import Models.calendario;
import com.toedter.calendar.JCalendar;


import javax.swing.*;

public class reservas {
    public JPanel panel1;
    private JPanel calendario;
    JCalendar calendar = new JCalendar();
    public void setCalendario(){
        calendario.add(calendar);
    }
}

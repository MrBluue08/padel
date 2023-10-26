package Models;

import com.toedter.calendar.JCalendar;
import javax.swing.*;


public class calendario extends JComponent {
    private JCalendar JcalendarComponent;

    public calendario(){
        JcalendarComponent = new JCalendar();
        add(JcalendarComponent);
    }
}

package vista;

import javax.swing.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.ArrayList;
import Controllers.*;

public class formPistas {
    public controlador c = new controlador();
    public JList<String> listadoPistas = new JList(cargarIds());


    public DefaultListModel cargarIds() throws SQLException {
        DefaultListModel pistasModelo = new DefaultListModel();
        ArrayList<Integer> ids = c.listPistas();
        for(int i = 0; i<ids.size(); i++) {
            pistasModelo.addElement(ids.get(i));
            System.out.println(ids.get(i));
        }
        listadoPistas.setModel(pistasModelo);

        return pistasModelo;
    }



    public JPanel panel1;
    private JCheckBox activoCheckBox;
    private JTextField precioTxt;
    private JButton addBtn;
    private JButton selectBtn;
    private JButton editBtn;
    private JLabel idLbl;
    private JLabel precioLbl;
    private JButton backBtn;
    private JLabel idTxt;
    private JTextField buscadorTxt;
    private JButton buscadorBtn;
    private JLabel buscadorLbl;

    public formPistas() throws SQLException {
        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c.openMainAdmin();
            }
        });
        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    c.addPista(precioTxt.getText(),activoCheckBox.isSelected());
                    c.openPistas();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        selectBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                System.out.println(listadoPistas.getSelectedValue());
            }
        });
        buscadorBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Integer id = c.selectPista(buscadorTxt.getText()).getId();
                    Float precio = c.selectPista(buscadorTxt.getText()).getPrecioHora();
                    Integer activo = c.selectPista(buscadorTxt.getText()).getActivo();
                    idTxt.setText(id.toString());
                    precioTxt.setText(precio.toString());
                    if (activo==1){
                        activoCheckBox.setSelected(true);
                    }else{
                        activoCheckBox.setSelected(false);
                    }

                } catch (SQLException ex) {
                    idTxt.setText("Sin resultados");
                    throw new RuntimeException(ex);
                }
            }
        });
        editBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = idTxt.getText();
                String precio = precioTxt.getText();
                Boolean activo = activoCheckBox.isSelected();
                try {
                    c.updatePista(id,precio,activo);
                    c.openPistas();
                } catch (SQLException ex) {

                    throw new RuntimeException(ex);
                }
            }
        });
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}

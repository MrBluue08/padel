package vista;

import javax.swing.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.ArrayList;
import Controllers.*;
import Models.pistas;

public class formPistas {
    public controlador c = new controlador();
    public JList listadoPistas;
    public JPanel panel1;
    private JCheckBox activoCheckBox;
    private JTextField precioTxt;
    private JButton addBtn;
    private JButton editBtn;
    private JLabel idLbl;
    private JLabel precioLbl;
    private JButton backBtn;
    private JTextField buscadorTxt;
    private JButton buscadorBtn;
    private JLabel buscadorLbl;

    private JTextField condicionTxt;
    private JLabel condicionLbl;
    private JTextField idTxt;
    private JButton saveBtn;

    Icon icon = new ImageIcon("img/backArrow.png");
    String idPlaceholder = null;

    public void cargarIds() throws SQLException {
        DefaultListModel pistasModelo = new DefaultListModel();
        ArrayList<Integer> ids = c.listPistas();
        for(int i = 0; i<ids.size(); i++) {
            pistasModelo.addElement(ids.get(i));
            System.out.println(ids.get(i));
        }
        listadoPistas.setModel(pistasModelo);

    }

    public void fillDatos(String id){
        try {
            pistas pista = c.selectPista(id);
            String ID_pista = pista.getId();
            String condicion = pista.getCondicion();
            Float precio = pista.getPrecioHora();
            Integer activo = pista.getActivo();
            idTxt.setText(ID_pista);
            condicionTxt.setText(condicion);
            condicionTxt.setText(condicion);
            precioTxt.setText(precio.toString());
            if (activo==1){
                activoCheckBox.setSelected(true);
            }else{
                activoCheckBox.setSelected(false);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public formPistas() throws SQLException {
        backBtn.setIcon(icon);
        cargarIds();
        saveBtn.setVisible(false);
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
                    c.addPista(idTxt.getText(),condicionTxt.getText(),precioTxt.getText(),activoCheckBox.isSelected());
                    c.openPistas();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        buscadorBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                idPlaceholder = buscadorTxt.getText();
                fillDatos(buscadorTxt.getText());
                saveBtn.setVisible(true);
                addBtn.setVisible(false);

            }
        });
        editBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] datos = listadoPistas.getSelectedValue().toString().split(" ");
                idPlaceholder = datos[1];
                fillDatos(datos[1]);
                saveBtn.setVisible(true);
                addBtn.setVisible(false);
            }
        });
        saveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveBtn.setVisible(false);
                addBtn.setVisible(true);
                String nuevoID = idTxt.getText();
                String precio = precioTxt.getText();
                Boolean activo = activoCheckBox.isSelected();
                try {
                    c.updatePista(idPlaceholder,nuevoID,precio,activo);
                    c.openPistas();
                } catch (SQLException ex) {

                    throw new RuntimeException(ex);
                }
            }
        });
    }


}

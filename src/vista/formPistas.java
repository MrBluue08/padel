package vista;

import javax.swing.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.ArrayList;
import Controllers.*;
import Models.pistas;

public class formPistas {
    public controlador c = new controlador();
    public JList<String> listadoPistas = new JList(cargarIds());
    public String id;


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

    public void editMode(){
        buscadorBtn.setVisible(true);
        buscadorLbl.setVisible(true);
        buscadorTxt.setVisible(true);
        editBtn.setVisible(true);
        editModeBtn.setVisible(false);
        addModeBtn.setVisible(true);
        addBtn.setVisible(false);
    }
    public void addMode(){
        addModeBtn.setVisible(false);
        addBtn.setVisible(true);
        buscadorBtn.setVisible(false);
        buscadorLbl.setVisible(false);
        buscadorTxt.setVisible(false);
        editBtn.setVisible(false);
        editModeBtn.setVisible(true);
    }



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
    private JButton editModeBtn;
    private JButton addModeBtn;
    private JTextField condicionTxt;
    private JLabel condicionLbl;
    private JTextField idTxt;

    Icon icon = new ImageIcon("img/backArrow.png");

    public formPistas() throws SQLException {
        backBtn.setIcon(icon);
        addMode();
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
                try {
                    id = buscadorTxt.getText();
                    pistas pista = c.selectPista(buscadorTxt.getText());
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
        });
        editBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nuevoID = idTxt.getText();
                String precio = precioTxt.getText();
                Boolean activo = activoCheckBox.isSelected();
                try {
                    c.updatePista(id,nuevoID,precio,activo);
                    c.openPistas();
                } catch (SQLException ex) {

                    throw new RuntimeException(ex);
                }
            }
        });
        editModeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editMode();

            }
        });
        addModeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addMode();
            }
        });
    }


}

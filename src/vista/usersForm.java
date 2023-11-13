package vista;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

import Controllers.*;
import Models.usuarios;

public class usersForm {
    public JPanel panel1;
    private JTextField dniTxt;
    private JTextField mailTxt;
    private JTextField nameTxt;
    private JTextField surnameTxt;
    private JButton addBtn;
    private JTextField buscadorTxt;
    private JButton buscarBtn;
    private JLabel buscadorLbl;
    private JLabel dniLbl;
    private JLabel mailLbl;
    private JLabel nombreLbl;
    private JLabel apellidoLbl;
    private JLabel passwdLbl;
    private JPasswordField passwdTxt;
    private JButton editBtn;
    private JLabel activeLbl;
    private JCheckBox checkActive;
    public JList listaDni;
    private JButton backBtn;
    private JLabel mostrarDni;

    controlador c = new controlador();
    Icon icon = new ImageIcon("img/backArrow.png");


    public void cargarDni() throws SQLException {
        DefaultListModel pistasModelo = new DefaultListModel();
        ArrayList<Integer> ids = c.listUser();
        for(int i = 0; i<ids.size(); i++) {
            pistasModelo.addElement(ids.get(i));
            System.out.println(ids.get(i));
        }
        listaDni.setModel(pistasModelo);

    }

    public void fillInfoUser(String DNI)throws SQLException{
        dniTxt.setVisible(false);
        mostrarDni.setVisible(true);
        usuarios u = c.selectUsuarios(DNI);
        mostrarDni.setText(u.getDni());
        mailTxt.setText(u.getMail());
        nameTxt.setText(u.getNombre());
        surnameTxt.setText(u.getApellidos());
        if(u.getActive()){
            checkActive.setSelected(true);
        }
        passwdTxt.setText("······");
    }

    public usersForm() throws SQLException {

        backBtn.setIcon(icon);
        editBtn.setVisible(false);
        mostrarDni.setVisible(false);
        cargarDni();

        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    c.addUser(dniTxt.getText(),mailTxt.getText(),nameTxt.getText(),surnameTxt.getText(),
                            passwdTxt.getText(),checkActive.isSelected() );
                    c.openListUsers();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c.openMainAdmin();
            }
        });
        editBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    c.updateUser(mostrarDni.getText(),mailTxt.getText(),nameTxt.getText(),surnameTxt.getText(),
                            "",checkActive.isSelected() );
                    popUp ventana = new popUp("Usuario actualizado correctamente");
                    placeHolder vista = new placeHolder();
                    vista.setContentPane(ventana.panel1);
                    vista.setSize(500,200);
                    vista.setVisible(true);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        buscarBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    fillInfoUser(buscadorTxt.getText());
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }


            }
        });

        listaDni.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                addBtn.setVisible(false);
                editBtn.setVisible(true);
                String dato = listaDni.getSelectedValue().toString();
                String[] datos = dato.split(" ");
                try {
                    fillInfoUser(datos[1]);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }





}

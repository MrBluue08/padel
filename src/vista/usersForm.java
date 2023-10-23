package vista;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

import Controllers.*;

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
    controlador c = new controlador();

    public JList<String> listaDni = new JList(cargarDni());
    private JButton backBtn;

    public usersForm() throws SQLException {
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
                    c.updateUser(dniTxt.getText(),mailTxt.getText(),nameTxt.getText(),surnameTxt.getText(),
                            passwdTxt.getText(),checkActive.isSelected() );
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        buscarBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    dniTxt.setText(c.selectUsuarios(buscadorTxt.getText()).getDni());
                    mailTxt.setText(c.selectUsuarios(buscadorTxt.getText()).getMail());
                    nameTxt.setText(c.selectUsuarios(buscadorTxt.getText()).getNombre());
                    surnameTxt.setText(c.selectUsuarios(buscadorTxt.getText()).getApellidos());
                    passwdTxt.setText(c.selectUsuarios(buscadorTxt.getText()).getPasswd());
                    c.openListUsers();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }


            }
        });
    }


    public DefaultListModel cargarDni() throws SQLException {
        DefaultListModel pistasModelo = new DefaultListModel();
        ArrayList<Integer> ids = c.listUser();
        for(int i = 0; i<ids.size(); i++) {
            pistasModelo.addElement(ids.get(i));
            System.out.println(ids.get(i));
        }
        listaDni.setModel(pistasModelo);

        return pistasModelo;
    }


}

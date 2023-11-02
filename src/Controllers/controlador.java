package Controllers;

import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import vista.*;
import Models.*;

import javax.swing.*;

public class controlador {
    Funciones f = new Funciones();

    public static placeHolder view = new placeHolder();

    public static void main(String[] args){
        index vista = new index();
        view.setContentPane(vista.panel1);
        view.setSize(500,500);
        view.setVisible(true);
    }

    public void addPista(String id,String condicion,String precio, Boolean activo) throws SQLException {

        Float cost = Float.parseFloat(precio);
        Integer flag = 0;

        if(activo){
            flag = 1;
        }

        String query = "INSERT INTO `pistas`(`ID_pista`, `condicion`, `Precio_por_hora`, `activa`) " +
                "VALUES ('"+id+"','"+condicion+"','"+cost+"','"+flag+"')";
        System.out.println(query);
        f.update(query);
    }

    public void updatePista(String id, String idNuevo,String precio, Boolean activo) throws SQLException {
        String active = "";
        if(activo==true){
            active = "1";
        }else{
            active = "0";
        }
        String query = "UPDATE `pistas` SET  `ID_pista`='"+idNuevo+"',`Precio_por_hora`='"+precio+"',`activa`='"+active+"' WHERE ID_pista LIKE '"+id+"';";
        f.update(query);
    }

    public pistas selectPista(String id) throws SQLException {
        String query = "SELECT * FROM pistas;";
        if(id.equals("")){
            query = "SELECT * FROM pistas;";
        }else{
            query = "SELECT * FROM pistas WHERE ID_pista LIKE '"+id+"';";
        }
        ResultSet pista = f.ejecutarQuery(query);
        pista.next();
        String ID = pista.getString(1);
        String condicion = pista.getString(2);
        Float precio = pista.getFloat(3);
        Integer activo = pista.getInt(4);
        pistas placeholder = new pistas(ID,condicion,precio,activo);
        return placeholder;
    }

    public ResultSet pistasReservar() throws SQLException {
        String query = "SELECT * FROM pistas WHERE activa LIKE '1'";
        ResultSet pistas = f.ejecutarQuery(query);
        return pistas;
    }

    public ArrayList<String> pistasDisponibles(java.util.Date date, String horaInicio) throws SQLException, ParseException {
        SimpleDateFormat formatoFecha = new SimpleDateFormat("E MMM dd zz yyyy");
        String fecha = formatoFecha.format(date);
        String query = "SELECT ID_pista,horaFin FROM reservas WHERE Fecha LIKE '"+fecha+"'";
        System.out.println(query);
        ResultSet pistasReservadas = f.ejecutarQuery(query);
        String ids = "";
        while(pistasReservadas.next()){
            String id = pistasReservadas.getString(1);
            SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm");
            Boolean horaOk = formatoHora.parse(horaInicio).before(formatoHora.parse(pistasReservadas.getString(2)));
            System.out.println(horaOk);
            if(horaOk){
                ids += ",'"+id+"'";
            }
        }
        query = "SELECT * FROM pistas WHERE ID_pista NOT IN (''"+ids+")";
        System.out.println(query);
        ResultSet pistasLibres = f.ejecutarQuery(query);
        ArrayList<String> listaID = new ArrayList<>();
        while(pistasLibres.next()){
            listaID.add(pistasLibres.getString(1));
        }
        return listaID;
    }

    public void reservar(ArrayList<String> datos) throws SQLException {
        String fecha = datos.get(0);
        String horaInicio = datos.get(1);
        String horaFin = datos.get(2);
        String dni = datos.get(3);
        String ID_pista = datos.get(4);
        String query = "INSERT INTO `reservas`(`Fecha`, `horaInicio`, `horaFin`, `dni`, `ID_pista`) " +
                "VALUES ('"+fecha+"','"+horaInicio+"','"+horaFin+"','"+dni+"','"+ID_pista+"')";
        f.update(query);


    }


    public void addUser(String dni, String mail, String nombre, String apellidos, String passwd, Boolean activo) throws SQLException {
        String active = "";
        if(activo){
             active = "1";
        }else{
             active = "0";
        }
        String query = "";
        if(!dni.equals("")){
            query = "INSERT INTO `usuarios`(`dni`, `Email`, `Nombre`, `Apellidos`, `passwd`, `active`)" +
                    "VALUES ('"+dni+"',"+"'"+mail+"','"+nombre+"','"+apellidos+"','"+passwd+"','"+active+"')";
            f.update(query);
        }

    }

    public void updateUser(String dni, String mail, String nombre, String apellido, String passwd,Boolean activo) throws SQLException {
        String active = "";
        if(activo==true){
            active = "1";
        }else{
            active = "0";
        }
        String query = "UPDATE `usuarios` SET `Email`='"+mail+"',`Nombre`='"+nombre+"',`Apellidos`='"+apellido+"',`password`='"+passwd+"',"
                + "`active`='"+active+"' " + "WHERE id LIKE '"+dni+"';";
        f.update(query);
    }

    public usuarios selectUsuarios(String DNI) throws SQLException {
        String query = "SELECT * FROM usuarios WHERE dni LIKE '"+DNI+"';";
        ResultSet user = f.ejecutarQuery(query);
        user.next();
        String dni = user.getString(1);
        String mail = user.getString(2);
        String nombre = user.getString(3);
        String apellidos = user.getString(4);
        String passwd = user.getString(5);
        usuarios placeholder = new usuarios(dni,mail,nombre,apellidos,passwd);
        return placeholder;
    }
    public void checkAdmin(String id, String passwd) throws SQLException {
        Boolean correcta = false;
        String query = "SELECT password FROM administradores WHERE id LIKE '" + id + "';";
        ResultSet resultado = f.ejecutarQuery(query);
        resultado.next();
        String pass = resultado.getString(1);
        if (passwd.equals(pass)) {
            correcta = true;
        }

        if (correcta){ openMainAdmin(); }
    }

    public void checkUser(String id, String passwd) throws SQLException {
        String query = "SELECT * FROM usuarios WHERE id LIKE '" + id + "';";
        ResultSet resultado = f.ejecutarQuery(query);
        resultado.next();
        String pass = resultado.getString(5);
        usuarios user = new usuarios(resultado.getString(1),resultado.getString(2),resultado.getString(3),resultado.getString(4),resultado.getString(5));
        String activo = resultado.getString(6);
        if (passwd.equals(pass) && Integer.parseInt(activo) == 1) {
            openMainUser(user);
        }else if(Integer.parseInt(activo) == 0) {
            openInicioUsers(true, "Usuario desactivado temporalmente");
        }
        else{
            openInicioUsers(true,"Identificador y/o contraseña incorrecto(s)");
        }
    }

    public void openMainUser(usuarios user){
        mainUsuarios main = new mainUsuarios();
        view.setContentPane(main.panel1);
        view.setSize(500,500);
        mainUsuarios.user = user;
        view.setVisible(true);
    }

    public void openMainAdmin(){
        mainAdmin main = new mainAdmin();
        view.setContentPane(main.panel1);
        view.setSize(500,500);
        view.setVisible(true);
    }

    public void openReservas(ArrayList<JButton> btns, String start, String end, java.util.Date fecha, usuarios user) throws SQLException {
        reservas main = new reservas(btns,start,end,fecha,user);
        view.setContentPane(main.main);
        view.setSize(750,500);
        view.setVisible(true);
    }

    public void fillInfoUser(usuarios u, perfilUser perfil){
        perfil.nameTxt.setText(u.getNombre()+" "+u.getApellidos());
        perfil.dniTxt.setText(u.getDni());
        perfil.mailTxt.setText(u.getMail());
        perfil.passwdTxt.putClientProperty("passwd",u.getPasswd());
        perfil.passwdTxt.setText("####");
        System.out.println(u.getPasswd());
        System.out.println(u.getDni());
    }

    public void openPerfil(usuarios user){
        perfilUser perfil = new perfilUser();
        view.setContentPane(perfil.panel1);
        view.setSize(500,500);
        fillInfoUser(user, perfil);
        perfil.viewMode();
        view.setVisible(true);
    }

    public void openPistas() throws SQLException{
        formPistas form = new formPistas();
        view.setContentPane(form.panel1);
        view.setSize(750,500);
        view.setVisible(true);
    }

    public void openListUsers() throws SQLException{
        usersForm form = new usersForm();
        view.setContentPane(form.panel1);
        view.setSize(800,500);
        form.start();
        view.setVisible(true);
    }

    public void openInicioUsers(Boolean error, String msg){
        inicioUsers form = new inicioUsers();
        view.setContentPane(form.panel1);
        view.setSize(500,500);
        form.errorLbl.setVisible(error);
        form.errorLbl.setText(msg);
        view.setVisible(true);
    }

    public void openInicioAdmin(){
        inicioAdmin form = new inicioAdmin();
        view.setContentPane(form.panel1);
        view.setSize(500,500);
        view.setVisible(true);
    }


    public ArrayList listPistas() throws SQLException{
        ResultSet pistasResult = f.ejecutarQuery("SELECT * FROM pistas");
        ArrayList<String> pista = new ArrayList<>();
        while(pistasResult.next()){
            pistas p = new pistas(pistasResult.getString(1),pistasResult.getInt(4));
            pista.add(p.toString());
        }
        return pista;
    }


    public ArrayList listUser() throws SQLException {
        ResultSet dniResult = f.ejecutarQuery("SELECT id FROM usuarios");
        ArrayList<String> dni = new ArrayList<>();
        while(dniResult.next()){
            usuarios u = new usuarios(dniResult.getString(1));
            dni.add(u.toString());
        }
        return dni;
    }

    public void cerrarSesion() {
        index form = new index();
        view.setContentPane(form.panel1);
        view.setSize(500,500);
        view.setVisible(true);
    }
}

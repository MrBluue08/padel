package Controllers;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import vista.*;
import Models.*;

import javax.swing.*;

public class controlador {
    static Funciones f = new Funciones();

    public static placeHolder view = new placeHolder();

    public static void main(String[] args){
        openInicioUsers(false,"");
    }

    public static void addPista(String id,String condicion,String precio, Boolean activo) throws SQLException {

        Float cost = Float.parseFloat(precio);
        Integer flag = 0;

        if(activo){
            flag = 1;
        }

        String query = "INSERT INTO `pistas`(`ID_pista`, `condicion`, `Precio_por_hora`, `activa`) " +
                "VALUES ('"+id+"','"+condicion+"','"+cost+"','"+flag+"')";
         f.update(query);
    }

    public static void updatePista(String id, String idNuevo, String condicion,String precio, Boolean activo) throws SQLException {
        String active = "";
        if(activo==true){
            active = "1";
        }else{
            active = "0";
        }
        String updateReservas = "UPDATE `reservas` SET `ID_pista`='0' WHERE ID_pista LIKE '"+id+"';";
        String query = "UPDATE `pistas` SET  `ID_pista`='"+idNuevo+"',`condicion`='"+condicion+"',`Precio_por_hora`='"+precio+"',`activa`='"+active+"' WHERE ID_pista LIKE '"+id+"';";
        System.out.println(updateReservas);
        System.out.println(query);
        f.update(updateReservas);
        f.update(query);
        updateReservas = "UPDATE `reservas` SET `ID_pista`='"+idNuevo+"' WHERE ID_pista LIKE '0';";
        f.update(updateReservas);

    }

    public static pistas selectPista(String id) throws SQLException {
        String query = "SELECT * FROM pistas;";
        if(!id.equals("")){
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

    public static ArrayList<String> pistasDisponibles(java.util.Date date, String horaInicio) throws SQLException, ParseException {
        SimpleDateFormat formatoFecha = new SimpleDateFormat("E MMM dd zz yyyy");
        String fecha = formatoFecha.format(date);
        String query = "SELECT ID_pista,horaFin FROM reservas WHERE Fecha LIKE '"+fecha+"'";
         ResultSet pistasReservadas = f.ejecutarQuery(query);
        String ids = "";
        while(pistasReservadas.next()){
            String id = pistasReservadas.getString(1);
            SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm");
            Boolean horaOk = formatoHora.parse(horaInicio).before(formatoHora.parse(pistasReservadas.getString(2)));
                  if(horaOk){
                ids += ",'"+id+"'";
            }
        }
        query = "SELECT * FROM pistas WHERE ID_pista NOT IN (''"+ids+")";
         ResultSet pistasLibres = f.ejecutarQuery(query);
        ArrayList<String> listaID = new ArrayList<>();
        while(pistasLibres.next()){
            listaID.add(pistasLibres.getString(1));
        }
        return listaID;
    }

    public static void reservar(ArrayList<String> datos) throws SQLException {
        String fecha = datos.get(0);
        String horaInicio = datos.get(1);
        String horaFin = datos.get(2);
        String dni = datos.get(3);
        String ID_pista = datos.get(4);
        String query = "INSERT INTO `reservas`(`Fecha`, `horaInicio`, `horaFin`, `dni`, `ID_pista`) " +
                "VALUES ('"+fecha+"','"+horaInicio+"','"+horaFin+"','"+dni+"','"+ID_pista+"')";
        f.update(query);


    }


    public static void addUser(String dni, String mail, String nombre, String apellidos, String passwd, Boolean activo) throws SQLException {
        String active = "";
        if(activo){
             active = "1";
        }else{
             active = "0";
        }
        String query = "";


        try{
            String passEncripted = encriptarPasswd(passwd);

            if(!dni.equals("")){
                query = "INSERT INTO `usuarios`(`id`, `Email`, `Nombre`, `Apellidos`, `password`, `active`)" +
                        "VALUES ('"+dni+"',"+"'"+mail+"','"+nombre+"','"+apellidos+"','"+passEncripted+"','"+active+"')";
                f.update(query);
            }
        }catch(NoSuchAlgorithmException e){

        }


    }

    public static String encriptarPasswd(String passwd) throws NoSuchAlgorithmException {
        MessageDigest encriptador = MessageDigest.getInstance("MD5");
        byte[] passwordBytes = passwd.getBytes();
        encriptador.update(passwordBytes);
        byte[] encriptado = encriptador.digest();

        StringBuilder sb = new StringBuilder();

        for(byte b : encriptado){
            sb.append(String.format("%02x", b));
        }
        String passEncripted = sb.toString();

        return passEncripted;
    }

    public static usuarios updateUser(String dni, String mail, String nombre, String apellido, String passwd,Boolean activo) throws SQLException {
        String active = "";
        usuarios user = new usuarios(dni,mail,nombre,apellido,passwd);
        if(activo==true){
            active = "1";
        }else{
            active = "0";
        }
        String passEnriptada = null;
        try{
            passEnriptada = encriptarPasswd(passwd);
        }catch(NoSuchAlgorithmException e){}
        String query = null;
        if(!passwd.equals("")){
             query = "UPDATE `usuarios` SET `Email`='"+mail+"',`Nombre`='"+nombre+"',`Apellidos`='"+apellido+"',`password`='"+passEnriptada+"',"
                    + "`active`='"+active+"' " + "WHERE id LIKE '"+dni+"';";
        }else{
             query = "UPDATE `usuarios` SET `Email`='"+mail+"',`Nombre`='"+nombre+"',`Apellidos`='"+apellido+"',"
                    + "`active`='"+active+"' " + "WHERE id LIKE '"+dni+"';";
        }
        f.update(query);

        return user;
    }

    public static usuarios selectUsuarios(String DNI) throws SQLException {
        String query = "SELECT * FROM usuarios WHERE id LIKE '"+DNI+"';";
        ResultSet user = f.ejecutarQuery(query);
        user.next();
        String dni = user.getString(1);
        String mail = user.getString(2);
        String nombre = user.getString(3);
        String apellidos = user.getString(4);
        String passwd = user.getString(5);
        String active = user.getString(6);
        Boolean activo = null;
        if(active.equals("1")){
            activo = true;
        }else{
            activo = false;
        }
        usuarios placeholder = new usuarios(dni,mail,nombre,apellidos,passwd,activo);
        return placeholder;
    }
    public static void checkAdmin(String id, String passwd) throws SQLException {
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

    public static void checkUser(String id, String passwd) throws SQLException {
        String query = "SELECT * FROM usuarios WHERE id LIKE '" + id + "';";
        ResultSet resultado = f.ejecutarQuery(query);
        String pass = null;
        try{
            resultado.next();
            pass = resultado.getString(5);
        } catch (SQLException e){
            openInicioUsers(true, "Identificador y/o contraseña incorrecto(s)");
        }
        String encriptado = null;
        try {
            encriptado = encriptarPasswd(passwd);
        } catch (NoSuchAlgorithmException e) {}

        usuarios user = new usuarios(resultado.getString(1), resultado.getString(2), resultado.getString(3), resultado.getString(4), resultado.getString(5));
        String activo = resultado.getString(6);
        System.out.println(pass);
        System.out.println(encriptado);
        if (pass.equals(encriptado) && Integer.parseInt(activo) == 1) {
            openMainUser(user);
        } else if (Integer.parseInt(activo) == 0) {
            openInicioUsers(true, "Usuario desactivado temporalmente");
        } else {
            openInicioUsers(true, "Identificador y/o contraseña incorrecto(s)");
        }
    }

    public static void openMainUser(usuarios user){
        mainUsuarios main = new mainUsuarios();
        view.setContentPane(main.panel1);
        view.setSize(500,500);
        mainUsuarios.user = user;
        view.setVisible(true);
    }

    public static void openMainAdmin(){
        mainAdmin main = new mainAdmin();
        view.setContentPane(main.panel1);
        view.setSize(500,500);
        view.setVisible(true);
    }

    public static void openReservas(ArrayList<JButton> btns, String start, String end, java.util.Date fecha, usuarios user) throws SQLException {
        reservas main = new reservas(btns,start,end,fecha,user);
        view.setContentPane(main.panel1);
        view.setSize(750,500);
        view.setVisible(true);
    }

    public static void fillInfoUser(usuarios u, perfilUser perfil){
        perfil.nameTxt.setText(u.getNombre()+" "+u.getApellidos());
        perfil.dniTxt.setText(u.getDni());
        perfil.mailTxt.setText(u.getMail());
        perfil.passwdTxt.putClientProperty("passwd",u.getPasswd());
        perfil.passwdTxt.setText("####");

    }

    public static void openPerfil(usuarios user){
        perfilUser perfil = new perfilUser();
        view.setContentPane(perfil.panel1);
        view.setSize(500,500);
        fillInfoUser(user, perfil);
        perfil.viewMode();
        view.setVisible(true);
    }

    public static void openMisReservas(usuarios user) throws SQLException {
        misReservas vista = new misReservas(user);
        view.setContentPane(vista.panel1);
        view.setSize(500,500);
        view.setVisible(true);
    }

    public static ArrayList<ArrayList<String>> selectReservas(String dni) throws SQLException{
        String query = "SELECT * FROM reservas WHERE dni LIKE '"+dni+"';";
        ResultSet reservas = f.ejecutarQuery(query);
        ArrayList<ArrayList<String>> listaReservas = new ArrayList<>();
        while(reservas.next()){
            ArrayList<String> datos = new ArrayList<>();
            datos.add(reservas.getString(1));
            String fecha = reservas.getString(2);
            datos.add(fecha);
            datos.add(reservas.getString(3));
            datos.add(reservas.getString(4));
            datos.add(reservas.getString(6));
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate diaReserva = LocalDate.parse(fecha,formato);
            System.out.println(LocalDate.now());
            if(diaReserva.isAfter(LocalDate.now())){
                listaReservas.add(datos);
            }
        }

        return listaReservas;

    }

    public static void dropReserva(String id) throws SQLException {
        String query = "DELETE FROM reservas WHERE id_reserva LIKE '"+id+"';";
        f.update(query);
    }

    public static void openPistas() throws SQLException{
        formPistas form = new formPistas();
        view.setContentPane(form.panel1);
        view.setSize(750,500);
        view.setVisible(true);
    }

    public static void openListUsers() throws SQLException{
        usersForm form = new usersForm();
        view.setContentPane(form.panel1);
        view.setSize(800,500);
        view.setVisible(true);
    }

    public static void openInicioUsers(Boolean error, String msg){
        inicioUsers form = new inicioUsers();
        view.setContentPane(form.panel1);
        view.setSize(300,400);
        form.errorLbl.setVisible(error);
        form.errorLbl.setText(msg);
        view.setVisible(true);
    }

    public static void openInicioAdmin(){
        inicioAdmin form = new inicioAdmin();
        view.setContentPane(form.panel1);
        view.setSize(500,500);
        view.setVisible(true);
    }


    public static ArrayList listPistas() throws SQLException{
        ResultSet pistasResult = f.ejecutarQuery("SELECT * FROM pistas");
        ArrayList<String> pista = new ArrayList<>();
        while(pistasResult.next()){
            pistas p = new pistas(pistasResult.getString(1),pistasResult.getInt(4));
            pista.add(p.toString());
        }
        return pista;
    }


    public static ArrayList listUser() throws SQLException {
        ResultSet dniResult = f.ejecutarQuery("SELECT id FROM usuarios");
        ArrayList<String> dni = new ArrayList<>();
        while(dniResult.next()){
            usuarios u = new usuarios(dniResult.getString(1));
            dni.add(u.toString());
        }
        return dni;
    }

}

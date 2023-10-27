package Models;

import java.awt.*;

public class pistas {

    public pistas(){};

    public pistas(String id, int activo) {
        this.id = id;
        this.activo = activo;
    }


    @Override
    public String toString() {
        String active = "";
        if(activo==1){
            active = "Si";
        }else{
            active = "No";
        }
        return  "ID: " + id +
                " ,Activo: " +active+"    ";
    }

    public pistas(String id, String condicion,float precioHora, int activo){
        this.id = id;
        this.condicion = condicion;
        this.precioHora = precioHora;
        this.activo = activo;
    };

    //variables
    private String id;
    private String name;
    private String condicion;
    private float precioHora;
    private int activo;


    //setter & getter

    public String getId() {return id;}
    public void setId(String id) {this.id = id;}

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}


    public String getCondicion() {return condicion;}
    public void setCondicion(String condicion) {this.condicion = condicion;}

    public float getPrecioHora() {return precioHora;}
    public void setPrecioHora(int precioHora) {this.precioHora = precioHora;}


    public int getActivo() {return activo;}
    public void setActivo(int Activo) {this.activo = Activo;}


}

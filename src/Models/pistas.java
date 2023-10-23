package Models;

import java.awt.*;

public class pistas {

    public pistas(){};


    @Override
    public String toString() {
        String active = "";
        if(activo==1){
            active = "Si";
        }else{
            active = "No";
        }
        return  "ID: " + id +
                " ,Precio: " + precioHora +
                " ,Activo: " + active+"    ";
    }

    public pistas(int id, float precioHora, int activo){
        this.id = id;
        this.precioHora = precioHora;
        this.activo = activo;
    };
    private int id;
    private float precioHora;
    private int activo;

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}


    public float getPrecioHora() {return precioHora;}

    public void setPrecioHora(int precioHora) {this.precioHora = precioHora;}

    public int getActivo() {return activo;}

    public void setActivo(int Activo) {this.activo = Activo;}


}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package papeleria.model;

import java.sql.Timestamp;

/**
 *
 * @author heber
 */
public class Venta {

    private int idVenta;
    private boolean existe;
    private Timestamp fecha;

    public Venta(VentaBuilder build) {
        this.existe = build.existe;
        this.fecha = build.fecha;
        this.idVenta = build.idVenta;
    }

    public int getIdVenta() {
        return idVenta;
    }

    public boolean isExiste() {
        return existe;
    }

    public void setExiste(boolean existe) {
        this.existe = existe;
    }

    public Timestamp getFecha() {
        return fecha;
    }
    
    @Override
    public String toString(){
        return "IDVenta: " + idVenta + "    Fecha: " + fecha + "    Existe: " + existe;
    }
    

    public static class VentaBuilder {

        private int idVenta;
        private boolean existe;
        private Timestamp fecha;
        
        public VentaBuilder existe(boolean existe){
            this.existe = existe;
            return this;
        }
        
        public VentaBuilder fecha(Timestamp fecha){
            this.fecha = fecha;
            return this;
        }
        
        public VentaBuilder idVenta(int idVenta){
            this.idVenta = idVenta;
            return this;
        }
        
        public Venta build(){
            return new Venta(this);
        }
    }

}

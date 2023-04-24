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
public class VentaTipo {

    private String nombreTipo;
    private double cantidadTipo;
    private int idTipo;
    private Venta venta;

    public VentaTipo(VentaBuilder build) {
        this.nombreTipo = build.nombreTipo;
        this.cantidadTipo = build.cantidadTipo;
        this.idTipo = build.idTipo;
        this.venta = build.venta;
        
    }

    public void setIdTipo(int idTipo) {
        this.idTipo = idTipo;
    }

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }


    public int getIdTipo() {
        return idTipo;
    }

    public String getNombreTipo() {
        return nombreTipo;
    }

    public void setNombreTipo(String nombreTipo) {
        this.nombreTipo = nombreTipo;
    }

    public double getCantidadTipo() {
        return cantidadTipo;
    }

    public void setCantidadTipo(double cantidadTipo) {
        this.cantidadTipo = cantidadTipo;
    }

    @Override
    public String toString() {
        System.out.println(venta.getIdVenta());
        System.out.println(venta.getFecha());
        return  "ID Venta: " + venta.getIdVenta() + "      FechaVenta: " + venta.getFecha() + "ID Tipo: " + idTipo + "     Nombre Tipo: " + nombreTipo + ",    CantidadTipo: " + cantidadTipo;
    }

    public static class VentaBuilder {

        private double cantidadTipo;
        private String nombreTipo;
        private int idTipo;
        private Venta venta;
        
        public VentaBuilder venta(Venta venta){
            this.venta = venta;
            return this;
        }

        public VentaBuilder cantidadTipo(double cantidadTipo) {
            this.cantidadTipo = cantidadTipo;
            return this;
        }

        public VentaBuilder nombreTipo(String nombreTipo) {
            this.nombreTipo = nombreTipo;
            return this;
        }

        public VentaBuilder idTipo(int idTipo) {
            this.idTipo = idTipo;
            return this;
        }

        public VentaTipo build() {
            return new VentaTipo(this);
        }
    }

}

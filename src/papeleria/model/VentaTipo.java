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

    private double cantidadTipo;
    private Venta venta;
    private Base tipo;

    public VentaTipo(VentaBuilder build) {
        this.cantidadTipo = build.cantidadTipo;
        this.venta = build.venta;
        this.tipo = build.tipo;
        
    }

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    public Base getTipo() {
        return tipo;
    }

    public void setTipo(Base tipo) {
        this.tipo = tipo;
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
        return "ID Venta: " + venta.getIdVenta() + "      FechaVenta: " + venta.getFecha() + "ID Tipo: " + tipo.getIdBase() + "     Nombre Tipo: " + tipo.getNombreBase() + ",    CantidadTipo: " + cantidadTipo;
    }

    public static class VentaBuilder {

        private double cantidadTipo;
        private Base tipo;
        private Venta venta;

        public VentaBuilder venta(Venta venta) {
            this.venta = venta;
            return this;
        }

        public VentaBuilder tipo(Base tipo) {
            this.tipo = tipo;
            return this;
        }

        public VentaBuilder cantidadTipo(double cantidadTipo) {
            this.cantidadTipo = cantidadTipo;
            return this;
        }

        public VentaTipo build() {
            return new VentaTipo(this);
        }
    }

}

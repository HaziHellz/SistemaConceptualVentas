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
public class Gasto {

    private int idGasto;
    private int idTipo;
    private int idProveedor;
    private Timestamp fecha;
    private double cantidad;

    private String concepto;
    private String tienda;

    public Gasto(GastoBuilder builder) {
        this.idGasto = builder.idGasto;
        this.idTipo = builder.idTipo;
        this.idProveedor = builder.idProveedor;
        this.fecha = builder.fecha;
        this.cantidad = builder.cantidad;
    }

    public Gasto(double cantidad, String concepto, String tienda) {
        this.cantidad = cantidad;
        this.concepto = concepto;
        this.tienda = tienda;
    }

    public Gasto() {

    }

    public String getConcepto() {
        return concepto;
    }

    public String getTienda() {
        return tienda;
    }


    
    public int getIdGasto() {
        return idGasto;
    }

    public int getIdTipo() {
        return idTipo;
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public Timestamp getFecha() {
        return fecha;
    }

    public double getCantidad() {
        return cantidad;
    }

    public static class GastoBuilder {

        int idGasto;
        int idTipo;
        int idProveedor;
        Timestamp fecha;
        double cantidad;

        public GastoBuilder id(int id) {
            this.idGasto = id;
            return this;
        }

        public GastoBuilder idTipo(int idTipo) {
            this.idTipo = idTipo;
            return this;
        }

        public GastoBuilder idProveedor(int idProveedor) {
            this.idProveedor = idProveedor;
            return this;
        }

        public GastoBuilder fecha(Timestamp fecha) {
            this.fecha = fecha;
            return this;
        }

        public GastoBuilder cantidad(double cantidad) {
            this.cantidad = cantidad;
            return this;
        }

        public Gasto build() {
            return new Gasto(this);
        }

    }
}

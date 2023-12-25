/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package papeleria.model;

import java.sql.Date;
import java.util.List;

/**
 *
 * @author heber
 */
public class Apartado {

    private Cliente cliente;
    private Date fechaApartado;
     private Date fechaMaxima;
    private String descripcion;
    private int idTipo;
    private double totalPagar;
    private boolean cancelado;
    private List<VentaTipo> abonos;
    private double pagado;
    
    public Apartado(ApartadoBuilder built) {
        cliente = built.cliente;
        fechaApartado = built.fechaApartado;
        fechaMaxima = built.fechaMaxima;
        descripcion = built.descripcion;
        idTipo = built.idTipo;
        totalPagar = built.totalPagar;
        cancelado = built.cancelado;
        abonos = AbonoDAO.getAbonos(cliente, this);
        calcularPagado();
    }
    
    private void calcularPagado(){
        pagado = 0;
        for (int i = 0; i < abonos.size(); i++) {
            pagado += abonos.get(i).getCantidadTipo();
        }
    }

    //EL CLIENTE VACIO INDICA QUE NO HAY CLIENTE NI APARTADOS
    public Apartado() {
        cliente = new Cliente();
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Date getFechaApartado() {
        return fechaApartado;
    }

    public void setFechaApartado(Date fechaApartado) {
        this.fechaApartado = fechaApartado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(int idTipo) {
        this.idTipo = idTipo;
    }

    public double getTotalPagar() {
        return totalPagar;
    }

    public void setTotalPagar(double totalPagar) {
        this.totalPagar = totalPagar;
    }

    public boolean isCancelado() {
        return cancelado;
    }

    public void setCancelado(boolean cancelado) {
        this.cancelado = cancelado;
    }    

    public List<VentaTipo> getAbonos() {
        return abonos;
    }
    
    public double getPagado(){
        return pagado;
    }
    
    public double getRestante(){
        return totalPagar - pagado;
    }

    public Date getFechaMaxima() {
        return fechaMaxima;
    }
    
   
    @Override
    public String toString(){
        return cliente.toString() + " | " + getTotalPagar() + " | " + getDescripcion() + " | " + getFechaApartado().toString();
    }

    public static class ApartadoBuilder {

        private Cliente cliente;
        private Date fechaApartado;
        private String descripcion;
        private int idTipo;
        private double totalPagar;
        private boolean cancelado;
        private Date fechaMaxima;

        public ApartadoBuilder setCliente (Cliente cliente){
            this.cliente = cliente;
            return this;
        }
        
        public ApartadoBuilder setFechaApartado (Date fechaApartado){
            this.fechaApartado = fechaApartado;
            return this;
        }
        
        public ApartadoBuilder setDescripcion (String descripcion){
            this.descripcion = descripcion;
            return this;
        }
        
        public ApartadoBuilder setIdTipo (int idTipo){
            this.idTipo = idTipo;
            return this;
        }
        
        public ApartadoBuilder setTotalPagar (double totalPagar){
            this.totalPagar = totalPagar;
            return this;
        }
        
        public ApartadoBuilder setCancelado (boolean cancelado){
            this.cancelado = cancelado;
            return this;
        }
        
        public ApartadoBuilder setFechaMaxima(Date fechaMaxima){
            this.fechaMaxima = fechaMaxima;
            return this;
        }
        
        public Apartado build(){
            return new Apartado(this);
        }
    }

}

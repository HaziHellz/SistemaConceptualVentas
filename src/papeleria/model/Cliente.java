/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package papeleria.model;

/**
 *
 * @author heber
 */
public class Cliente {

    private String apellido;
    private String nombre;
    private String telefono;
    
    public Cliente(ClienteBuilder builder){
        apellido = builder.apellido;
        nombre = builder.nombre;
        telefono = builder.telefono;
    }

    public Cliente() {
        nombre = "vacio";
        apellido = "vacio";
        telefono = "vacio";
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
    

    public static class ClienteBuilder {

        private String apellido;
        private String nombre;
        private String telefono;

        public ClienteBuilder setApellido(String apellido) {
            this.apellido = apellido;
            return this;
        }

        public ClienteBuilder setNombre(String nombre) {
            this.nombre = nombre;
            return this;
        }

        public ClienteBuilder setTelefono(String telefono) {
            this.telefono = telefono;
            return this;
        }

        public Cliente build() {
            return new Cliente(this);
        }

    }
}

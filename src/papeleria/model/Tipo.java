/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package papeleria.model;

/**
 *
 * @author heber
 */
public class Tipo {
    
    
    
    private int id_tipo;
    private String nombre_tipo;
    private boolean existe_tipo;

    public Tipo(TipoBuilder build) {
        this.id_tipo = build.id_tipo;
        this.nombre_tipo = build.nombre_tipo;
        this.existe_tipo = build.existe_tipo;
    }
    
    public Tipo(){
        
    }

    public int getIdTipo() {
        return id_tipo;
    }

    public void setIdTipo(int idBase) {
        this.id_tipo = idBase;
    }

    public String getNombreTipo() {
        return nombre_tipo;
    }

    public void setNombreTipo(String nombreTipo) {
        this.nombre_tipo = nombreTipo;
    }

    public boolean isExists() {
        return existe_tipo;
    }

    public void setExists(boolean exists) {
        this.existe_tipo = exists;
    }
    
    @Override
    public String toString(){
        StringBuilder string = new StringBuilder();
        string.append("Tipo{ ").append("idTipo= ").append(id_tipo).append(", nameTipo= ").append(nombre_tipo).append(", exists= ").append(existe_tipo).append("}");
        return string.toString();
    }

    public static class TipoBuilder {

        private int id_tipo;
        private String nombre_tipo;
        private boolean existe_tipo;

        public TipoBuilder idTipo(int idTipo) {
            this.id_tipo = idTipo;
            return this;
        }

        public TipoBuilder nameTipo(String nameTipo) {
            this.nombre_tipo = nameTipo;
            return this;
        }

        public TipoBuilder exists(boolean exists) {
            this.existe_tipo = exists;
            return this;
        }

        public Tipo build() {
            return new Tipo(this);
        }

    }
    
}

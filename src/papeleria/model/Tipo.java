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

    private int idTipo;
    private String nombreTipo;
    private boolean exists;

    public Tipo(TipoBuilder build) {
        this.idTipo = build.idType;
        this.nombreTipo = build.nameType;
        this.exists = build.exists;
    }

    public int getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(int idTipo) {
        this.idTipo = idTipo;
    }

    public String getNombreTipo() {
        return nombreTipo;
    }

    public void setNombreTipo(String nombreTipo) {
        this.nombreTipo = nombreTipo;
    }

    public boolean isExists() {
        return exists;
    }

    public void setExists(boolean exists) {
        this.exists = exists;
    }
    
    @Override
    public String toString(){
        StringBuilder string = new StringBuilder();
        string.append("Type{ ").append("idType= ").append(idTipo).append(", nameType= ").append(nombreTipo).append(", exists= ").append(exists).append("}");
        return string.toString();
    }

    public static class TipoBuilder {

        private int idType;
        private String nameType;
        private boolean exists;

        public TipoBuilder idType(int idType) {
            this.idType = idType;
            return this;
        }

        public TipoBuilder nameType(String nameType) {
            this.nameType = nameType;
            return this;
        }

        public TipoBuilder exists(boolean exists) {
            this.exists = exists;
            return this;
        }

        public Tipo build() {
            return new Tipo(this);
        }

    }
}

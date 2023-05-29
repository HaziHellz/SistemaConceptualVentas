/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package papeleria.model;

/**
 *
 * @author heber
 */
public class Base {

    private int idBase;
    private String nombreBase;
    private boolean exists;

    public Base(TipoBuilder build) {
        this.idBase = build.idBase;
        this.nombreBase = build.nameBase;
        this.exists = build.exists;
    }

    public int getIdBase() {
        return idBase;
    }

    public void setIdBase(int idBase) {
        this.idBase = idBase;
    }

    public String getNombreBase() {
        return nombreBase;
    }

    public void setNombreBase(String nombreBase) {
        this.nombreBase = nombreBase;
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
        string.append("Base{ ").append("idBase= ").append(idBase).append(", nameBase= ").append(nombreBase).append(", exists= ").append(exists).append("}");
        return string.toString();
    }

    public static class TipoBuilder {

        private int idBase;
        private String nameBase;
        private boolean exists;

        public TipoBuilder idBase(int idBase) {
            this.idBase = idBase;
            return this;
        }

        public TipoBuilder nameBase(String nameBase) {
            this.nameBase = nameBase;
            return this;
        }

        public TipoBuilder exists(boolean exists) {
            this.exists = exists;
            return this;
        }

        public Base build() {
            return new Base(this);
        }

    }
}

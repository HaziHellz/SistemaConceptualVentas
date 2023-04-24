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

    private int idType;
    private String nameType;
    private boolean exists;

    public Tipo(TypeBuilder build) {
        this.idType = build.idType;
        this.nameType = build.nameType;
        this.exists = build.exists;
    }

    public int getIdType() {
        return idType;
    }

    public void setIdType(int idType) {
        this.idType = idType;
    }

    public String getNameType() {
        return nameType;
    }

    public void setNameType(String nameType) {
        this.nameType = nameType;
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
        string.append("Type{ ").append("idType= ").append(idType).append(", nameType= ").append(nameType).append(", exists= ").append(exists).append("}");
        return string.toString();
    }

    public static class TypeBuilder {

        private int idType;
        private String nameType;
        private boolean exists;

        public TypeBuilder idType(int idType) {
            this.idType = idType;
            return this;
        }

        public TypeBuilder nameType(String nameType) {
            this.nameType = nameType;
            return this;
        }

        public TypeBuilder exists(boolean exists) {
            this.exists = exists;
            return this;
        }

        public Tipo build() {
            return new Tipo(this);
        }

    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package papeleria;

import papeleria.view.GUI;

/**
 *
 * @author heber
 */
public class Papeleria {
    
    private static String version = "0.9.10";
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new GUI().setVisible(true);
    }

    public static String getVersion() {
        return version;
    }

}

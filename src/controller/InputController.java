/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.awt.event.KeyEvent;

/**
 *
 * @author heber
 */
public class InputController {

    public static void validarNumero(KeyEvent e, String text) {
        boolean consume = false;
        int point = 1;
        for (int i = 0; i < text.length(); i++) {
            if ('.' == text.charAt(i)) {
                point += 1;
            }
        }
        if (e.getKeyChar() < '0' || e.getKeyChar() > '9') {
            consume = true;
            if (e.getKeyChar() == '.') {
                consume = false;
            }
        }

        if ((point > 1 && e.getKeyChar() == '.') || consume == true) {
            e.consume();
        }
    }

    public static void validarNumeroCompuesto(KeyEvent e, String text) {
        boolean consume = false;
        int point = 1;
        int x = 1;
        for (int i = 0; i < text.length(); i++) {
            if ('.' == text.charAt(i)) {
                point += 1;
            }

            if ('X' == text.charAt(i) || 'x' == text.charAt(i)) {
                x += 1;
            }
        }

        if ((e.getKeyChar() < '0' || e.getKeyChar() > '9') && !(e.getKeyChar() == 'X' || e.getKeyChar() == 'x')) {
            consume = true;
            if (e.getKeyChar() == '.' || 'X' == e.getKeyChar() || 'x' == e.getKeyChar()) {
                consume = false;
            }
        }

        if ((point > 1 && e.getKeyChar() == '.') || consume == true || (x > 1 && ('X' == e.getKeyChar() || 'x' == e.getKeyChar()))) {
            e.consume();
        }
    }

    //SEPARA Y CONVIERTE LA ENTRADA STRING EN DOUBLE EN MULTIPLICACIONES POR EJEMPLO PARA ENTRADA 20X3 LA SALIDA SERIA 60
    public static double getDouble(String numero) {
        boolean ladoUno = true;
        boolean completo = false;
        String numeroUno = "";
        String numeroDos = "";
        for (int i = 0; i < numero.length(); i++) {
            if (ladoUno) {
                if (!(numero.charAt(i) == 'x' || numero.charAt(i) == 'X')) {
                    numeroUno += numero.charAt(i);
                } else{
                    ladoUno = false;
                }
            } else{
                completo = true;
                numeroDos += numero.charAt(i);
            }
        }

        if (!ladoUno && completo) {
            return Double.parseDouble(numeroUno) * Double.parseDouble(numeroDos);
        } else {
            return Double.parseDouble(numeroUno);
        }
    }
}

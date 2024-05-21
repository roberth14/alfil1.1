/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

/**
 *
 * @author Roberth
 */
public class Alfil {

    private String color;
    private int i_alfil;
    private int j_alfil;

    public Alfil() {
    }

    public Alfil(int i_alfil, int j_alfil, String color) {
        this.color = color;
        this.i_alfil = i_alfil;
        this.j_alfil = j_alfil;
    }

    public Alfil(int i_alfil, int j_alfil) {
        this.i_alfil = i_alfil;
        this.j_alfil = j_alfil;
    }

    public int getI_alfil() {
        return i_alfil;
    }

    public void setI_alfil(int i_alfil) {
        this.i_alfil = i_alfil;
    }

    public int getJ_alfil() {
        return j_alfil;
    }

    public void setJ_alfil(int j_alfil) {
        this.j_alfil = j_alfil;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

}

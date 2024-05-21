
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
public class Peon {

    private String color;
    private int i_peon;
    private int j_peon;

    public Peon() {
    }

    public Peon(int i_peon, int j_peon) {
        this.i_peon = i_peon;
        this.j_peon = j_peon;
    }

    public Peon(int i_peon, int j_peon, String color) {
        this.i_peon = i_peon;
        this.j_peon = j_peon;
        this.color = color;
    }

    public int getI_peon() {
        return i_peon;
    }

    public void setI_peon(int i_peon) {
        this.i_peon = i_peon;
    }

    public int getJ_peon() {
        return j_peon;
    }

    public void setJ_peon(int j_peon) {
        this.j_peon = j_peon;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

}

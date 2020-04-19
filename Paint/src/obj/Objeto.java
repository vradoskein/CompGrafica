package obj;

import java.awt.Point;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author pedro
 */
public class Objeto {

    private ArrayList<Point> pontos;
    private String op;
    private String nome;

    public Objeto(ArrayList<Point> p, String c, String nome) {
        this.pontos = p;
        this.op = c;
        this.nome = nome;
    }

    public ArrayList<Point> getPontos() {
        return this.pontos;
    }

    public void addPonto(Point p) {
        this.pontos.add(p);
    }

    /**
     * @return the op
     */
    public String getOp() {
        return op;
    }

    /**
     * @param op the op to set
     */
    public void setOp(String op) {
        this.op = op;
    }

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }
}

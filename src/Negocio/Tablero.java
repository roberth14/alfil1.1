package Negocio;

import Modelo.Alfil;
import Modelo.Peon;
import Util.PDFGenerator;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa el tablero de ajedrez. Desarrollado por: Roberth David
 * Caicedo
 */
public class Tablero {

    private char[][] tableroMatriz;
    private boolean direccionPeon; // true abajo-arriba   false arriba-abajo
    private Peon peon;
    private Alfil alfil;
    public static ArrayList<char[][]> table = new ArrayList<>();
    private static Tablero instancia;
    public static String cadenaPdf = "";
    private static List<String> movimientos = new ArrayList<>();

    private Tablero() {
    }

    public static Tablero getInstancia(int i_alfil, int j_alfil, int i_peon, int j_peon, String colorAlfil) {
        if (instancia == null) {
            instancia = new Tablero(i_alfil, j_alfil, i_peon, j_peon, colorAlfil);
        }
        return instancia;
    }

    public static String getCadenaPdf() {
        return cadenaPdf;
    }

    public static void setCadenaPdf(String cadenaPdf) {
        Tablero.cadenaPdf = cadenaPdf;
    }

    public Tablero(int i_alfil, int j_alfil, int i_peon, int j_peon, String colorAlfil) {
        this.tableroMatriz = new char[8][8];
        if (colorAlfil.equalsIgnoreCase("negro")) {
            this.peon = new Peon(i_peon, j_peon, "blanco");
            this.alfil = new Alfil(i_alfil, j_alfil, "negro");
            this.direccionPeon = false;
            posicionarAlfil();
            posicionarPeon();
            cargueInicial();
            System.out.println("EL PEÓN ATACARÁ HACIA ABAJO");
        } else if (colorAlfil.equalsIgnoreCase("blanco")) {
            this.peon = new Peon(i_peon, j_peon, "negro");
            this.alfil = new Alfil(i_alfil, j_alfil, "blanco");
            this.direccionPeon = true;
            posicionarAlfil();
            posicionarPeon();
            cargueInicial();
            System.out.println("EL PEÓN ATACARÁ HACIA ARRIBA");
        }
    }

    public void posicionarPeon() {
        tableroMatriz[peon.getI_peon()][peon.getJ_peon()] = 'P';
    }

    public void posicionarAlfil() {
        tableroMatriz[alfil.getI_alfil()][alfil.getJ_alfil()] = 'A';
    }

    public void imprimirMatriz() {
        System.out.println("    0   1   2   3   4   5   6   7");
        for (int i = 0; i < tableroMatriz.length; i++) {
            System.out.print(i);
            for (int j = 0; j < tableroMatriz[i].length; j++) {
                System.out.print("   " + tableroMatriz[i][j] + " ");
            }
            System.out.println(); // Agrega un salto de línea después de imprimir cada fila
        }
        System.out.println("");
    }

    public void cargueInicial() {
        for (int i = 0; i < tableroMatriz.length; i++) {
            for (int j = 0; j < tableroMatriz[i].length; j++) {
                tableroMatriz[i][j] = '-';
            }
        }
    }

    public boolean jugar() {
        return jugar(alfil.getI_alfil(), alfil.getJ_alfil(), peon.getI_peon(), peon.getJ_peon());
    }

    public boolean jugar(int i_alfil, int j_alfil, int i_peon, int j_peon) {
        System.out.println("PEON SE ENCUENTRA EN LA POSICION [" + i_peon + "][" + j_peon + "]");
        System.out.println("ALFIL SE ENCUENTRA EN LA POSICION [" + i_alfil + "][" + j_alfil + "]");
        posicionarAlfil();
        posicionarPeon();
        imprimirMatriz();
        char[][] tn = new char[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                tn[i][j] = tableroMatriz[i][j];
            }
        }
        table.add(tn);
        movimientos.add("A(" + i_alfil + "," + j_alfil + ")-P(" + i_peon + "," + j_peon + ")");
        try {
            Thread.sleep(2000); // Pausa de 2 segundos
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (this.direccionPeon == false) {
            if (i_peon == 7) {
                imprimirMatriz();
                System.out.println("GANA EL PEÓN BLANCO");
                PDFGenerator.generarPDF("src/resources/reporte_partida_ajedrez.pdf", table, movimientos);
                return true;
            } else {
                if (i_alfil == i_peon + 1 && j_alfil == j_peon - 1 || i_alfil == i_peon + 1 && j_alfil == j_peon + 1) {
                    peon.setI_peon(alfil.getI_alfil());
                    peon.setJ_peon(alfil.getJ_alfil());
                    posicionarPeon();
                    imprimirMatriz();
                    System.out.println("GAME OVER, EL PEÓN SE COMIÓ AL ALFIL");
                    PDFGenerator.generarPDF("src/resources/reporte_partida_ajedrez.pdf", table, movimientos);
                    return true;
                } else {
                    if (i_peon + 1 == i_alfil && j_peon == j_alfil) {
                        System.out.println("EL PEON NO TIENE UN MOVIMINETO POSIBLE");
                        peon.setI_peon(i_peon);
                        peon.setJ_peon(j_peon);
                        posicionarPeon();

                    } else {
                        tableroMatriz[i_peon][j_peon] = '-';
                        tableroMatriz[i_peon + 1][j_peon] = 'P';
                        peon.setI_peon(i_peon + 1);
                        peon.setJ_peon(j_peon);
                        tableroMatriz[i_peon + 1][j_peon] = 'P';
                    }

                    if (!proximoMovimientoAlfil()) {
                        alfil.setI_alfil(peon.getI_peon());
                        alfil.setJ_alfil(peon.getJ_peon());
                        posicionarAlfil();
                        imprimirMatriz();
                        System.out.println("GAME OVER, EL ALFIL SE COMIÓ AL PEÓN");
                        PDFGenerator.generarPDF("src/resources/reporte_partida_ajedrez.pdf", table, movimientos);
                        return true;
                    }

                    borrarDiagonales();
                    return jugar(alfil.getI_alfil(), alfil.getJ_alfil(), i_peon + 1, j_peon);
                }
            }
        } else {
            if (i_peon == 0) {
                System.out.println("GANA EL PEÓN NEGRO");
                imprimirListaMatrices();
                PDFGenerator.generarPDF("src/resources/reporte_partida_ajedrez.pdf", table, movimientos);
                return true;
            } else {
                if (!proximoMovimientoAlfil()) {
                    alfil.setI_alfil(peon.getI_peon());
                    alfil.setJ_alfil(peon.getJ_peon());
                    posicionarAlfil();
                    imprimirMatriz();
                    System.out.println("GAME OVER, EL ALFIL SE COMIÓ AL PEÓN");
                    PDFGenerator.generarPDF("src/resources/reporte_partida_ajedrez.pdf", table, movimientos);
                    return true;
                } else {
                    if (i_alfil == i_peon - 1 && j_alfil == j_peon - 1 || i_alfil == i_peon + 1 && j_alfil == j_peon + 1) {
                        peon.setI_peon(alfil.getI_alfil());
                        peon.setJ_peon(alfil.getJ_alfil());
                        posicionarPeon();
                        imprimirMatriz();
                        imprimirListaMatrices();
                        System.out.println("GAME OVER, EL PEÓN SE COMIÓ AL ALFIL");
                        PDFGenerator.generarPDF("src/resources/reporte_partida_ajedrez.pdf", table, movimientos);
                        return true;
                    } else {
                        if (i_peon - 1 == i_alfil && j_peon == j_alfil) {
                            System.out.println("EL PEON NO TIENE UN MOVIMINETO POSIBLE");
                            peon.setI_peon(i_peon);
                            peon.setJ_peon(j_peon);
                            posicionarPeon();

                        } else {
                            tableroMatriz[i_peon][j_peon] = '-';
                            tableroMatriz[i_peon - 1][j_peon] = 'P';
                            peon.setI_peon(i_peon - 1);
                            peon.setJ_peon(j_peon);
                            tableroMatriz[i_peon - 1][j_peon] = 'P';
                        }
                    }
                }
                borrarDiagonales();
                return jugar(alfil.getI_alfil(), alfil.getJ_alfil(), i_peon - 1, j_peon);
            }
        }
    }

    public static void imprimirListaMatrices() {
        for (int i = 0; i < table.size(); i++) {
            cadenaPdf += "Tablero " + (i + 1) + ":\n";
            System.out.println("Tablero " + (i + 1) + ":");
            cadenaPdf += "\n";
            imprimirM(table.get(i));
            System.out.println(); // Salto de línea entre matrices
        }
    }

    public static void imprimirM(char[][] matriz) {
        cadenaPdf += "  0 1 2 3 4 5 6 7 \n ";
        System.out.println("  0 1 2 3 4 5 6 7");
        for (int i = 0; i < matriz.length; i++) {
            System.out.print(i);
            for (int j = 0; j < matriz[i].length; j++) {
                cadenaPdf += " " + matriz[i][j] + "\n";
                System.out.print(" " + matriz[i][j]);
            }
            System.out.println(); // Salto de línea al final de cada fila
        }
        cadenaPdf += "******************************** \n";
        System.out.println("");
    }

    private void cargue() {
        for (int i = 0; i < tableroMatriz.length; i++) {
            for (int j = 0; j < tableroMatriz[i].length; j++) {
                tableroMatriz[i][j] = '-';
            }
        }
    }

    public boolean proximoMovimientoAlfil() {
        try {
            boolean p = marcarDiagonales();
            if (p) {
                return false;
            }
            //DIAGONALES SUPERIORES
            for (int i = alfil.getI_alfil() - 1, j = alfil.getJ_alfil() - 1; i >= 0 && j >= 0; i--, j--) {
                tableroMatriz[i + 1][j + 1] = '-';
                tableroMatriz[i][j] = 'A';
                borrarDiagonales();
                marcarDiag(i, j);
 
                if (!direccionPeon) {
                    if (peon.getI_peon() == 7 && i > 0 && tableroMatriz[i - 1][j] == '-' || peon.getI_peon() == 7 && i == 0 || tableroMatriz[peon.getI_peon() + 1][peon.getJ_peon()] == '-' && i > 0 && tableroMatriz[i - 1][j] == '-' || tableroMatriz[peon.getI_peon() + 1][peon.getJ_peon()] == '-' && i == 0) {
                        alfil.setI_alfil(i);
                        alfil.setJ_alfil(j);
                        tableroMatriz[i][j] = 'A';
                        return true;
                    }
                } else {
                    if (peon.getI_peon() == 0 && i < 7 && tableroMatriz[i + 1][j] == '-' || peon.getI_peon() == 0 && i == 7 || tableroMatriz[peon.getI_peon() - 1][peon.getJ_peon()] == '-' && i > 0 && tableroMatriz[i + 1][j] == '-' || tableroMatriz[peon.getI_peon() - 1][peon.getJ_peon()] == '-' && i == 7) {
                        alfil.setI_alfil(i);
                        alfil.setJ_alfil(j);
                        tableroMatriz[i][j] = 'A';
                        return true;
                    }
                }
                borrarDiag(i, j);
                tableroMatriz[i][j] = '-';
            }
            for (int i = alfil.getI_alfil() - 1, j = alfil.getJ_alfil() + 1; i >= 0 && j < tableroMatriz.length; i--, j++) {
                tableroMatriz[i + 1][j - 1] = '-';
                tableroMatriz[i][j] = 'A';
                borrarDiagonales();
                marcarDiag(i, j);
                System.out.println("SEGUNDO");

                if (!direccionPeon) {
                    if (peon.getI_peon() == 7 && i > 0 && tableroMatriz[i - 1][j] == '-' || peon.getI_peon() == 7 && i == 0 || tableroMatriz[peon.getI_peon() + 1][peon.getJ_peon()] == '-' && i > 0 && tableroMatriz[i - 1][j] == '-' || tableroMatriz[peon.getI_peon() + 1][peon.getJ_peon()] == '-' && i == 0) {
                        alfil.setI_alfil(i);
                        alfil.setJ_alfil(j);
                        tableroMatriz[i][j] = 'A';
                        return true;
                    }
                } else {
                    if (peon.getI_peon() == 0 && i < 7 && tableroMatriz[i + 1][j] == '-' || peon.getI_peon() == 0 && i == 7 || tableroMatriz[peon.getI_peon() - 1][peon.getJ_peon()] == '-' && i > 0 && tableroMatriz[i + 1][j] == '-' || tableroMatriz[peon.getI_peon() - 1][peon.getJ_peon()] == '-' && i == 7) {
                        alfil.setI_alfil(i);
                        alfil.setJ_alfil(j);
                        tableroMatriz[i][j] = 'A';
                        return true;
                    }
                }
                borrarDiag(i, j);
                tableroMatriz[i][j] = '-';
            }

            //DIAGONALES INFERIORES
            for (int i = alfil.getI_alfil() + 1, j = alfil.getJ_alfil() - 1; i < tableroMatriz.length && j >= 0; i++, j--) {
                tableroMatriz[i - 1][j + 1] = '-';
                tableroMatriz[i][j] = 'A';
                borrarDiagonales();
                marcarDiag(i, j);
                System.out.println("TERCERO");

                if (!direccionPeon) {
                    if (peon.getI_peon() == 7 && i > 0 && tableroMatriz[i - 1][j] == '-' || peon.getI_peon() == 7 && i == 0 || tableroMatriz[peon.getI_peon() + 1][peon.getJ_peon()] == '-' && i > 0 && tableroMatriz[i - 1][j] == '-' || tableroMatriz[peon.getI_peon() + 1][peon.getJ_peon()] == '-' && i == 0) {
                        alfil.setI_alfil(i);
                        alfil.setJ_alfil(j);
                        tableroMatriz[i][j] = 'A';
                        return true;
                    }
                } else {
                    if (peon.getI_peon() == 0 && i < 7 && tableroMatriz[i + 1][j] == '-' || peon.getI_peon() == 0 && i == 7 || tableroMatriz[peon.getI_peon() - 1][peon.getJ_peon()] == '-' && i > 0 && tableroMatriz[i + 1][j] == '-' || tableroMatriz[peon.getI_peon() - 1][peon.getJ_peon()] == '-' && i == 7) {
                        alfil.setI_alfil(i);
                        alfil.setJ_alfil(j);
                        tableroMatriz[i][j] = 'A';
                        return true;
                    }
                }
                borrarDiag(i, j);
                tableroMatriz[i][j] = '-';
            }

            for (int i = alfil.getI_alfil() + 1, j = alfil.getJ_alfil() + 1; i < tableroMatriz.length && j < tableroMatriz.length; i++, j++) {
                tableroMatriz[i - 1][j - 1] = '-';
                tableroMatriz[i][j] = 'A';
                borrarDiagonales();
                marcarDiag(i, j);

                System.out.println("CUARTO");
                if (!direccionPeon) {
                    if (peon.getI_peon() == 7 && i > 0 && tableroMatriz[i - 1][j] == '-' || peon.getI_peon() == 7 && i == 0 || tableroMatriz[peon.getI_peon() + 1][peon.getJ_peon()] == '-' && i > 0 && tableroMatriz[i - 1][j] == '-' || tableroMatriz[peon.getI_peon() + 1][peon.getJ_peon()] == '-' && i == 0) {
                        alfil.setI_alfil(i);
                        alfil.setJ_alfil(j);
                        tableroMatriz[i][j] = 'A';
                        return true;
                    }
                } else {
                    if (peon.getI_peon() == 0 && i < 7 && tableroMatriz[i + 1][j] == '-' || peon.getI_peon() == 0 && i == 7 || tableroMatriz[peon.getI_peon() - 1][peon.getJ_peon()] == '-' && i > 0 && tableroMatriz[i + 1][j] == '-' || tableroMatriz[peon.getI_peon() - 1][peon.getJ_peon()] == '-' && i == 7) {
                        alfil.setI_alfil(i);
                        alfil.setJ_alfil(j);
                        tableroMatriz[i][j] = 'A';
                        return true;
                    }
                }
                borrarDiag(i, j);
                tableroMatriz[i][j] = '-';
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }

        borrarDiagonales();
        System.out.println("NO SE ENCONTRÓ UNA POSICIÓN");
        return false;
    }

    public boolean marcarDiagonales() {
        //DIAGONALES SUPERIORES
        for (int i = alfil.getI_alfil() - 1, j = alfil.getJ_alfil() - 1; i >= 0 && j >= 0; i--, j--) {
            if (tableroMatriz[i][j] == 'P') {
                return true;
            } else {
                tableroMatriz[i][j] = '*';
            }
        }

        for (int i = alfil.getI_alfil() - 1, j = alfil.getJ_alfil() + 1; i >= 0 && j < tableroMatriz.length; i--, j++) {
            if (tableroMatriz[i][j] == 'P') {
                return true;
            } else {
                tableroMatriz[i][j] = '*';
            }
        }

        //DIAGONALES INFERIORES
        for (int i = alfil.getI_alfil() + 1, j = alfil.getJ_alfil() - 1; i < tableroMatriz.length && j >= 0; i++, j--) {
            if (tableroMatriz[i][j] == 'P') {
                return true;
            } else {
                tableroMatriz[i][j] = '*';
            }
        }

        for (int i = alfil.getI_alfil() + 1, j = alfil.getJ_alfil() + 1; i < tableroMatriz.length && j < tableroMatriz.length; i++, j++) {
            if (tableroMatriz[i][j] == 'P') {
                return true;
            } else {
                tableroMatriz[i][j] = '*';
            }
        }
        return false;
    }

    public void borrarDiagonales() {
        //DIAGONALES SUPERIORES
        for (int i = alfil.getI_alfil() - 1, j = alfil.getJ_alfil() - 1; i >= 0 && j >= 0; i--, j--) {
            if (tableroMatriz[i][j] == 'P') {
                tableroMatriz[i][j] = 'P';
            } else {
                tableroMatriz[i][j] = '-';
            }
        }

        for (int i = alfil.getI_alfil() - 1, j = alfil.getJ_alfil() + 1; i >= 0 && j < tableroMatriz.length; i--, j++) {
            if (tableroMatriz[i][j] == 'P') {
                tableroMatriz[i][j] = 'P';
            } else {
                tableroMatriz[i][j] = '-';
            }
        }

        //DIAGONALES INFERIORES
        for (int i = alfil.getI_alfil() + 1, j = alfil.getJ_alfil() - 1; i < tableroMatriz.length && j >= 0; i++, j--) {
            if (tableroMatriz[i][j] == 'P') {
                tableroMatriz[i][j] = 'P';
            } else {
                tableroMatriz[i][j] = '-';
            }
        }

        for (int i = alfil.getI_alfil() + 1, j = alfil.getJ_alfil() + 1; i < tableroMatriz.length && j < tableroMatriz.length; i++, j++) {
            if (tableroMatriz[i][j] == 'P') {
                tableroMatriz[i][j] = 'P';
            } else {
                tableroMatriz[i][j] = '-';
            }
        }
    }

    public void marcarDiag(int i, int j) {
        //DIAGONALES SUPERIORES
        for (int a = i - 1, b = j - 1; a >= 0 && b >= 0; a--, b--) {
            if (tableroMatriz[a][b] == 'P') {
                tableroMatriz[a][b] = 'P';
            } else {
                tableroMatriz[a][b] = '*';
            }
        }
        for (int a = i - 1, b = j + 1; a >= 0 && b < tableroMatriz.length; a--, b++) {
            if (tableroMatriz[a][b] == 'P') {
                tableroMatriz[a][b] = 'P';
            } else {
                tableroMatriz[a][b] = '*';
            }
        }
        //DIAGONALES INFERIORES
        for (int a = i + 1, b = j - 1; a < tableroMatriz.length && b >= 0; a++, b--) {
            if (tableroMatriz[a][b] == 'P') {
                tableroMatriz[a][b] = 'P';
            } else {
                tableroMatriz[a][b] = '*';
            }
        }

        for (int a = i + 1, b = j + 1; a < tableroMatriz.length && b < tableroMatriz.length; a++, b++) {
            if (tableroMatriz[a][b] == 'P') {
                tableroMatriz[a][b] = 'P';
            } else {
                tableroMatriz[a][b] = '*';
            }
        }
    }

    public void borrarDiag(int i, int j) {
        //DIAGONALES SUPERIORES
        for (int a = i - 1, b = j - 1; a >= 0 && b >= 0; a--, b--) {
            if (tableroMatriz[a][b] == 'P') {
                tableroMatriz[a][b] = 'P';
            } else {
                tableroMatriz[a][b] = '-';
            }
        }
        for (int a = i - 1, b = j + 1; a >= 0 && b < tableroMatriz.length; a--, b++) {
            if (tableroMatriz[a][b] == 'P') {
                tableroMatriz[a][b] = 'P';
            } else {
                tableroMatriz[a][b] = '-';
            }
        }
        //DIAGONALES INFERIORES
        for (int a = i + 1, b = j - 1; a < tableroMatriz.length && b >= 0; a++, b--) {
            if (tableroMatriz[a][b] == 'P') {
                tableroMatriz[a][b] = 'P';
            } else {
                tableroMatriz[a][b] = '-';
            }
        }

        for (int a = i + 1, b = j + 1; a < tableroMatriz.length && b < tableroMatriz.length; a++, b++) {
            if (tableroMatriz[a][b] == 'P') {
                tableroMatriz[a][b] = 'P';
            } else {
                tableroMatriz[a][b] = '-';
            }
        }
    }

    public char[][] getTableroMatriz() {
        return tableroMatriz;
    }

    public void setTableroMatriz(char[][] tableroMatriz) {
        this.tableroMatriz = tableroMatriz;
    }

    public boolean isDireccionPeon() {
        return direccionPeon;
    }

    public void setDireccionPeon(boolean direccionPeon) {
        this.direccionPeon = direccionPeon;
    }

    public Peon getPeon() {
        return peon;
    }

    public void setPeon(Peon peon) {
        this.peon = peon;
    }

    public Alfil getAlfil() {
        return alfil;
    }

    public void setAlfil(Alfil alfil) {
        this.alfil = alfil;
    }
}
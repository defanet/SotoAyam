/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JQC;

import Function.Mainfunction;
import Grid.*;
import Jama.Matrix;
import Posisi.Geo;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Agung Danu Wijaya
 */
public class DFT {


    public void DFT(String geo) throws InterruptedException {
        Mainfunction master = new Mainfunction();
        Grid a = new Grid(geo, master);
        HashMap<Integer, HashMap<Integer, double[]>> points = a.points;
        Map<Integer, getdata.datakHF> bfs = master.gdata.get(geo);
        double[][] datagrid = a.setbfamps(master, bfs, points);

        master.intg.one(geo);
        Map<String, Geo.datageo> data = master.geo.data;
        double S[][] = master.intg.S;
        double T[][] = master.intg.EK;
        double V[][] = master.intg.EV;
        double H[][] = master.matrixOp.adddot(V, T);
        double C[][] = master.gev.gev(S, H);
        double G[][][][] = null;
        master.intg.two(geo);
        G = master.intg.ints;

        double Cold[][];
        double kali = 0.5;
        int panjang = master.geo.tengah(data.get(geo));
        double D[][] = new double[C.length][panjang];
        for (int i = 0; i < C.length; i++) {
            for (int j = 0; j < panjang; j++) {
                D[i][j] = C[i][j];
            }
        }
        
        Matrix U = new Matrix(D);
        Matrix DB = U.times(U.transpose());
        double P[][] = DB.getArray();

        double enold = 0;
        int stop = 0;
        while (stop == 0) {

            double J[][] = new double[S.length][S[0].length];
            for (int i = 0; i < S.length; i++) {
                for (int j = 0; j < S.length; j++) {
                    for (int k = 0; k < S.length; k++) {
                        for (int l = 0; l < S.length; l++) {
                            J[i][j] += G[i][j][k][l] * P[k][l];
                        }
                    }
                }
            }

            double Ej = 2 * master.matrixOp.sum(master.matrixOp.multiplydot(J, P));
            double Eh = 2 * master.matrixOp.sum(master.matrixOp.multiplydot(H, P));
        
//================Awal dari functional=========================================//
            Exc_Functional FX=new Exc_Functional();
            FX.LDA(P, a, master, datagrid, points);
            double Vxc[][] = FX.Vxc;
            double Exc = FX.Exc;
//================Akhir dari functional=========================================//
            
            double RP[][] = master.matrixOp.adddot(master.matrixOp.multiplydot(J, 2), Vxc);
            double F[][] = master.matrixOp.adddot(H, RP);
            C = master.gev.gev(S, F);
            for (int i = 0; i < C.length; i++) {
                for (int j = 0; j < panjang; j++) {
                    D[i][j] = C[i][j];
                }
            }
            U = new Matrix(D);
            DB = U.times(U.transpose());
            Cold = master.matrixOp.copy(P);
            P = DB.getArray();
            P = master.matrixOp.adddot(master.matrixOp.multiplydot(P, 1 - kali), master.matrixOp.multiplydot(Cold, kali));
            double total = Eh + Exc + Ej + master.geo.energi(data.get(geo));
            System.out.println("Energi sistem "+total);

            if (Math.abs(total - enold) < 0.0001) {
                stop = 1;
            } else {
                enold = total;
            }

        }
    }
    public static void main(String args[]) throws InterruptedException{
    DFT a=new DFT();
    a.DFT("H2");
    }

}

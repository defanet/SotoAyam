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


    public void DFT(String nama) throws InterruptedException {
        Mainfunction master = new Mainfunction();
        Grid a = new Grid(nama, master);
        HashMap<Integer, HashMap<Integer, double[]>> points = a.points;
        Map<Integer, getdata.datakHF> bfs = master.gdata.get(nama);
        double[][] datagrid = a.setbfamps(master, bfs, points);

        master.intg.one(nama);
        Map<String, Geo.datageo> data = master.geo.data;
        double S[][] = master.intg.S;
        double T[][] = master.intg.EK;
        double V[][] = master.intg.EV;
        double H[][] = master.matrixOp.adddot(V, T);
        double C[][] = master.gev.gev(S, H);
        double G[][][][] = null;
        master.intg.two(nama);
        G = master.intg.ints;

        double Cold[][];
        double kali = 0.5;
        int panjang = master.geo.tengah(data.get(nama));
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

            double rho[] = new double[datagrid.length];
            for (int p = 0; p < datagrid.length; p++) {
                for (int i = 0; i < datagrid[p].length; i++) {
                    for (int j = 0; j < datagrid[p].length; j++) {
                        rho[p] += datagrid[p][i] * datagrid[p][j] * P[i][j];
                    }
                }
            }

            double alpha = 2.0 / 3.0;
            double fac = -2.25 * alpha * Math.pow(0.75 / Math.PI, 1. / 3.);
            double rho3[] = master.matrixOp.powdot(rho, 1. / 3.);
            
            double fx[] = master.matrixOp.multiplydot(master.matrixOp.multiplydot(rho, rho3), fac);
            double dfxdna[] = master.matrixOp.multiplydot(rho3, (4. / 3.) * fac);
            HashMap<Integer, double[]> pointmap = a.pointsmap(points);
            double VX[][] = new double[datagrid[0].length][datagrid[0].length];

            for (int p = 0; p < datagrid.length; p++) {
                for (int i = 0; i < datagrid[p].length; i++) {
                    for (int j = 0; j < datagrid[p].length; j++) {
                        double RG[] = pointmap.get(p);
                        VX[i][j] += RG[3] * dfxdna[p] * datagrid[p][i] * datagrid[p][j];
                    }
                }
            }

            double w[] = new double[datagrid.length];
            for (int p = 0; p < datagrid.length; p++) {
                double RG[] = pointmap.get(p);
                w[p] = RG[3];
            }
            double Exc = master.matrixOp.sum(master.matrixOp.multiplydot(w, master.matrixOp.multiplydot(fx, 2)));
            double RP[][] = master.matrixOp.adddot(master.matrixOp.multiplydot(J, 2), VX);
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
            double total = Eh + Exc + Ej + master.geo.energi(data.get(nama));
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
    a.DFT("H2O");
    }

}

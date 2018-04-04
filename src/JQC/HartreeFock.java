package JQC;

import Function.*;
import Jama.Matrix;
import Posisi.Geo;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Agung Danu Wijaya
 */
public class HartreeFock {

    Mainfunction master;

    public HartreeFock(Mainfunction master) {
        this.master = master;
    }

    public void RHF(String nama) throws IOException, ClassNotFoundException, InterruptedException {
        master.intg.one(nama);
        Map<String, Geo.datageo> data = master.geo.data;
        double S[][] = master.intg.S;
        double T[][] = master.intg.EK;
        double V[][] = master.intg.EV;
        double H[][] = master.matrixOp.adddot(V, T);
        double C[][] = master.gev.gev(S, H);

        //jika ingin load G dari data yang disimpan, harap dicoment
        master.intg.two(nama);
        double G[][][][] = master.intg.ints;

        //load G dari data yang disimpan
        /*double G[][][][] = null;
        try {
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("/media/agung/Arbitrary/G"));
            G = (double[][][][]) inputStream.readObject();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(HartreeFock.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(HartreeFock.class.getName()).log(Level.SEVERE, null, ex);
        }
         */
        double Cold[][];
        double kali = 0.6;
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
            double RP[][] = new double[S.length][S[0].length];
            for (int i = 0; i < S.length; i++) {
                for (int j = 0; j < S.length; j++) {
                    for (int k = 0; k < S.length; k++) {
                        for (int l = 0; l < S.length; l++) {
                            RP[i][j] += (2 * G[i][j][k][l] - G[i][k][j][l]) * P[k][l];
                        }
                    }
                }
            }

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
            double En = 0;
            En = master.matrixOp.sum(master.matrixOp.multiplydot(master.matrixOp.adddot(H, F), P)); //saboo 3.184
            if (Math.abs(En - enold) < 0.0001) {
                stop = 1;
            } else {
                enold = En;
            }
            System.out.println(En + master.geo.energi(data.get(nama)) + " " + master.geo.energi(data.get(nama)) + " " + master.matrixOp.sum(master.matrixOp.multiplydot(RP, P)) + " " + 2 * master.matrixOp.sum(master.matrixOp.multiplydot(T, P)) + " " + 2 * master.matrixOp.sum(master.matrixOp.multiplydot(V, P)));

        }

    }
    
    //untuk simpan nilai G   
    public void RHFgetdata(String nama) throws ClassNotFoundException, InterruptedException {
        master.intg.one(nama);
        master.intg.two(nama);
        Map<String, Geo.datageo> data = master.geo.data;
        double S[][] = master.intg.S;
        double T[][] = master.intg.EK;
        double V[][] = master.intg.EV;
        double G[][][][] = master.intg.ints;
        ObjectOutputStream outputStream;
        try {
            outputStream = new ObjectOutputStream(new FileOutputStream("/media/agung/Arbitrary/G"));
            outputStream.writeObject(G);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(HartreeFock.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(HartreeFock.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void main(String[] args) throws IOException, InterruptedException {
        Mainfunction main = new Mainfunction();
        HartreeFock HF = new HartreeFock(main);
        try {
            HF.RHF("C6H6");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(HartreeFock.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}

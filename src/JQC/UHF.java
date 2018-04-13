package JQC;

import Function.*;
import Jama.Matrix;
import Posisi.Geo;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Agung Danu Wijaya
 */
public class UHF {

    Mainfunction master;

    public UHF(Mainfunction master) {
        this.master = master;
    }

    public void URHF(String geo) throws IOException, ClassNotFoundException, InterruptedException {
        master.intg.one(geo);
        Map<String, Geo.datageo> data = master.geo.data;
        double S[][] = master.intg.S;
        double T[][] = master.intg.EK;
        double V[][] = master.intg.EV;
        double H[][] = master.matrixOp.adddot(V, T);
        double C[][] = master.gev.gev(S, H);
        master.intg.two(geo);
        double G[][][][] = master.intg.ints;
        double kali = 0.0;
        int panjangu = 5;
        int panjangd = 4;
        double DU[][] = new double[C.length][panjangu];
        double DD[][] = new double[C.length][panjangd];
        for (int i = 0; i < C.length; i++) {
            for (int j = 0; j < panjangu; j++) {
                DU[i][j] = C[i][j];
            }
            for (int j = 0; j < panjangd; j++) {
                DD[i][j] = C[i][j];
            }
        }
        Matrix UU = new Matrix(DU);
        Matrix DUB = UU.times(UU.transpose());
        Matrix UD = new Matrix(DD);
        Matrix DDB = UD.times(UD.transpose());
        double PU[][] = DUB.getArray();
        double PD[][] = DDB.getArray();
        double CUold[][] = master.matrixOp.copy(PU);
        double CDold[][] = master.matrixOp.copy(PD);
        double enold = 0;
        int stop = 0;
        while (stop == 0) {
            double RPU[][][] = new double[2][S.length][S.length];
            double RPD[][][] = new double[2][S.length][S.length];
            for (int i = 0; i < S.length; i++) {
                for (int j = 0; j < S.length; j++) {
                    for (int k = 0; k < S.length; k++) {
                        for (int l = 0; l < S.length; l++) {
                            RPU[0][i][j] += G[i][j][k][l] * PU[k][l];
                            RPU[1][i][j] += G[i][k][j][l] * PU[k][l];
                            RPD[0][i][j] += G[i][j][k][l] * PD[k][l];
                            RPD[1][i][j] += G[i][k][j][l] * PD[k][l];
                        }
                    }
                }
            }

            double FU[][] = master.matrixOp.adddot(H, master.matrixOp.adddot(RPU[0], master.matrixOp.adddot(master.matrixOp.multiplydot(RPU[1], -1), RPD[0])));
            double FD[][] = master.matrixOp.adddot(H, master.matrixOp.adddot(RPD[0], master.matrixOp.adddot(master.matrixOp.multiplydot(RPD[1], -1), RPU[0])));

            double CU[][] = master.gev.gev(S, FU);
            double CD[][] = master.gev.gev(S, FD);
            for (int i = 0; i < CU.length; i++) {
                for (int j = 0; j < panjangu; j++) {
                    DU[i][j] = CU[i][j];
                }
            }
            for (int i = 0; i < CD.length; i++) {
                for (int j = 0; j < panjangd; j++) {
                    DD[i][j] = CD[i][j];
                }
            }

            UU = new Matrix(DU);
            DUB = UU.times(UU.transpose());
            UD = new Matrix(DD);
            DDB = UD.times(UD.transpose());
            PU = DUB.getArray();
            PD = DDB.getArray();
            PU = master.matrixOp.adddot(master.matrixOp.multiplydot(PU, 1 - kali), master.matrixOp.multiplydot(CUold, kali));
            PD = master.matrixOp.adddot(master.matrixOp.multiplydot(PD, 1 - kali), master.matrixOp.multiplydot(CDold, kali));
            CUold = master.matrixOp.copy(PU);
            CDold = master.matrixOp.copy(PD);
            double En = 0;
            En = master.matrixOp.sum(master.matrixOp.multiplydot(master.matrixOp.adddot(H, FU), PU)); //saboo 3.184
            En += master.matrixOp.sum(master.matrixOp.multiplydot(master.matrixOp.adddot(H, FD), PD)); //saboo 3.184
            En /= 2;
            if (Math.abs(En - enold) < 0.0001) {
                stop = 1;
            } else {
                enold = En;
            }
            System.out.println(En + master.geo.energi(data.get(geo)));
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        Mainfunction main = new Mainfunction();
        UHF HF = new UHF(main);
        try {
            HF.URHF("OH");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UHF.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}

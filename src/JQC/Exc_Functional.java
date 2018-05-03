package JQC;

import Function.Mainfunction;
import Grid.Grid;
import java.util.HashMap;

/**
 *
 * @author Agung Danu Wijaya, Farhamsa D
 */
public class Exc_Functional {

    public double Vxc[][];
    public double Exc;

    public void LDA(double P[][], Grid a, Mainfunction master, double datagrid[][], HashMap<Integer, HashMap<Integer, double[]>> points) {

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
        double Fx[] = master.matrixOp.multiplydot(master.matrixOp.multiplydot(rho, rho3), fac);
        double[] dFxdn = master.matrixOp.multiplydot(rho3, (4. / 3.) * fac);
        HashMap<Integer, double[]> pointmap = a.pointsmap(points);
        double Vxc[][] = new double[datagrid[0].length][datagrid[0].length];
        for (int p = 0; p < datagrid.length; p++) {
            for (int i = 0; i < datagrid[p].length; i++) {
                for (int j = 0; j < datagrid[p].length; j++) {
                    double RG[] = pointmap.get(p);
                    Vxc[i][j] += RG[3] * dFxdn[p] * datagrid[p][i] * datagrid[p][j];
                }
            }
        }
        double w[] = new double[datagrid.length];
        for (int p = 0; p < datagrid.length; p++) {
            double RG[] = pointmap.get(p);
            w[p] = RG[3];
        }
        double Exc = master.matrixOp.sum(master.matrixOp.multiplydot(w, master.matrixOp.multiplydot(Fx, 2)));
        this.Exc = Exc;
        this.Vxc = Vxc;
    }

}

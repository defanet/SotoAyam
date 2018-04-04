/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Grid;

import Function.Mainfunction;
import Jama.Matrix;
import Posisi.Geo;
import java.util.HashMap;

/**
 *
 * @author ict
 */
public class becke_reweight_atoms {

    public class data {

        public double[] R;      //position atom
        public double batom;

        public data(double[] R, double batom) {
            this.R = R;
            this.batom = batom;
        }
    }

    Geo.datageo geo;
    double[][] R;
    int[] batoms;

    public void becke_reweight_atoms(Mainfunction master, String nama, HashMap<Integer, HashMap<Integer, double[]>> points) {
        this.geo = master.geo.data.get(nama);
        this.R = geo.R;
        this.batoms = geo.numProton;

        for (int j = 0; j < points.size(); j++) {
            for (int l = 0; l < points.get(j).size(); l++) {
                double[] Ps = new double[R.length];
                for (int i = 0; i < R.length; i++) {
                    data atj = new data(R[i], batoms[i]);
                    Ps[i] = becke_atomic_grid_p(points.get(j).get(l), atj);
                }
                double  P = Ps[j]/master.matrixOp.sum(Ps);
                //master.matrixOp.disp(Ps);
                System.err.println(P);
            }
        }
    }

    public double becke_atomic_grid_p(double[] xyz, data ati) {
        Bragg f = new Bragg();
        boolean do_becke_hetero = true;
        double sprod = 1;
        double mu = 0;
        double rip = norm2(ati.R, xyz);
        for (int i = 0; i < R.length; i++) {
            data atj = new data(R[i], batoms[i]);
            if (atj != ati) {
                double rij = norm2(ati.R, atj.R);
                double rjp = norm2(atj.R, xyz);
                mu = (rip - rjp) / rij;
            }
            if (do_becke_hetero == true & ati.batom != atj.batom) {
                double chi = f.Bragg[(int) ati.batom] / f.Bragg[(int) atj.batom];
                double u = (chi - 1.) / (chi + 1.);
                double a = u / (u * u - 1);
                a = Math.min(a, 0.5);
                a = Math.max(a, -0.5);
                mu += a * (1 - mu * mu);
                sprod *= sbecke(mu);
            }
        }
        return sprod;
    }

    double sbecke(double x) {
        double n = 3;
        return 0.5 * (1 - fbecke(x, n));
    }

    double fbecke(double x, double n) {
        for (int i = 0; i < n; i++) {
            x = pbecke(x);
        }
        return x;
    }

    double pbecke(double x) {
        return 1.5 * x - 0.5 * Math.pow(x, 3);
    }

    double norm2(double[] a, double b[]) {
        double k[][] = new double[3][1];
        double c[][] = new double[3][1];
        for (int i = 0; i < k.length; i++) {
            k[i][0] = a[i];
            c[i][0] = b[i];
        }
        Matrix d = new Matrix(k).minus(new Matrix(c));
        return d.norm2();
    }

    public void test() {
        Mainfunction master = new Mainfunction();
        this.geo = master.geo.data.get("H2O");
        this.R = geo.R;
        this.batoms = geo.numProton;
        double V[] = {1.42296788, 0., -0.98120955};
        data ati = new data(V, 1);
        double xyz[] = {1.1, 1.2, 1.3};
        double d = becke_atomic_grid_p(xyz, ati);
        System.err.println(d);
    }

    public static void main(String args[]) {
        becke_reweight_atoms a = new becke_reweight_atoms();
        a.test();
    }
}
package Integral;

import Function.*;

/**
 *
 * @author Agung Danu Wijaya
 */
public class Tarikanelektron {

    Mainfunction main;

    public Tarikanelektron(Mainfunction main) {
        this.main = main;
    }

    public double X(
            double a1,
            double a2,
            double l1[],
            double l2[],
            double ra[],
            double rb[],
            double rc[],
            int in,
            int i
    ) {
        double y = a1 + a2;
        double rac[] = main.matrixOp.multiplydot(ra, a1);
        double rbc[] = main.matrixOp.multiplydot(rb, a2);
        double rp[] = main.matrixOp.adddot(rac, rbc);
        rp = main.matrixOp.multiplydot(rp, 1.0 / y);
        double AB = main.d.distances(ra, rb);
        double PA[][] = new double[3][ra.length];
        for (int j = 0; j < ra.length; j++) {
            PA[0][j] = main.d.distance(rp[j], ra[j]);
            PA[1][j] = main.d.distance(rp[j], rb[j]);
            PA[2][j] = main.d.distance(rp[j], rc[j]);
        }

        double PCz = main.d.distance(rp[2], rc[2]);
        double epsilon = 1.0 / (4.0 * y);
        double x = 0;
        for (int l = 0; l <= l1[in] + l2[in]; l++) {
            for (int q = 0; q <= l / 2.0; q++) {
                int L = l - 2 * q;
                for (int t = 0; t <= 0.5 * L; t++) {
                    if (L - t == i) {
                        x += (main.specialFunc.fac(l) * main.b.fk(l, l1[in], l2[in], PA[0][in], PA[1][in])
                                / (main.specialFunc.fac(q) * main.specialFunc.fac(t) * main.specialFunc.fac(L - 2 * t)))
                                * Math.pow(-1, t)
                                * Math.pow(epsilon, q + t)
                                * Math.pow(-1, l) * Math.pow(PA[2][in], L - 2 * t);
                    }
                }
            }
        }
        return x;
    }

    public double NAI(
            double a1,
            double a2,
            double l1[],
            double l2[],
            double ra[],
            double rb[],
            double rc[]
    ) {
        double y = a1 + a2;
        double rac[] = main.matrixOp.multiplydot(ra, a1);
        double rbc[] = main.matrixOp.multiplydot(rb, a2);
        double rp[] = main.matrixOp.adddot(rac, rbc);
        rp = main.matrixOp.multiplydot(rp, 1.0 / y);
        double AB = main.d.distances(ra, rb);
        double PC = main.d.distances(rp, rc);
        double K = Math.exp(-a1 * a2 * AB * AB / y);
        double epsilon = 1.0 / (4.0 * y);
        double NAI = 2.0 * Math.PI * K / y;
        double sum = 0;
        for (int i = 0; i <= l1[0] + l2[0]; i++) {
            for (int j = 0; j <= l1[1] + l2[1]; j++) {
                for (int k = 0; k <= l1[2] + l2[2]; k++) {
                    sum += X(a1, a2, l1, l2, ra, rb, rc, 0, i)
                            * X(a1, a2, l1, l2, ra, rb, rc, 1, j)
                            * X(a1, a2, l1, l2, ra, rb, rc, 2, k)
                            * main.g.F(i + j + k, y * PC * PC);
                }
            }
        }
        NAI *= sum;
        return NAI;
    }

}

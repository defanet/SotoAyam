/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Integral;

import Function.*;

/**
 *
 * @author Agung Danu Wijaya
 */
public class Tolakanelektron {

    Mainfunction main;

    public Tolakanelektron(Mainfunction main) {
        this.main = main;
    }

    public double X(int i,
            int in,
            double l1[],
            double l2[],
            double l3[],
            double l4[],
            double epsilon1,
            double epsilon2,
            double QA[][],
            double PA[][],
            double VPQ[],
            double del
    ) {
        double G = 0;
        for (int l = 0; l <= l1[in] + l2[in]; l++) {
            for (int laksen = 0; laksen <= l3[in] + l4[in]; laksen++) {
                for (int q = 0; q <= l / 2.0; q++) {
                    for (int qaksen = 0; qaksen <= laksen / 2.0; qaksen++) {
                        int L = l - 2 * q;
                        int Laksen = laksen - 2 * qaksen;
                        for (int t = 0; t <= 0.5 * (L + Laksen); t++) {
                            if (i == L + Laksen - t) {
                                G += (Math.pow(-1, l) * main.b.fk(l, l1[in], l2[in], PA[0][in], PA[1][in]))
                                        * (Math.pow(epsilon1, l - q) * main.specialFunc.fac(l)
                                        / (main.specialFunc.fac(q) * main.specialFunc.fac(L)))
                                        * (main.b.fk(laksen, l3[in], l4[in], QA[0][in], QA[1][in]))
                                        * (Math.pow(epsilon2, laksen - qaksen) * main.specialFunc.fac(laksen)
                                        / (main.specialFunc.fac(qaksen) * main.specialFunc.fac(Laksen)))
                                        * main.specialFunc.fac(L + Laksen) * Math.pow(-1, t) * Math.pow(VPQ[in], L + Laksen - 2 * t)
                                        / (main.specialFunc.fac(t) * main.specialFunc.fac(L + Laksen - 2 * t) * Math.pow(del, L + Laksen - t));
                            }
                        }
                    }
                }
            }
        }
        return G;
    }

    public double ERI(
            double l1[],
            double l2[],
            double l3[],
            double l4[],
            double a1,
            double a2,
            double a3,
            double a4,
            double ra[],
            double rb[],
            double rc[],
            double rd[]
    ) {

        double y1 = a1 + a2;
        double y2 = a3 + a4;
        double epsilon1 = 1.0 / (4.0 * y1);
        double epsilon2 = 1.0 / (4.0 * y2);
        double rac[] = main.matrixOp.multiplydot(ra, a1);
        double rbc[] = main.matrixOp.multiplydot(rb, a2);
        double rp[] = main.matrixOp.adddot(rac, rbc);
        double rac1[] = main.matrixOp.multiplydot(rc, a3);
        double rbc1[] = main.matrixOp.multiplydot(rd, a4);
        double rq[] = main.matrixOp.adddot(rac1, rbc1);
        rp = main.matrixOp.multiplydot(rp, 1.0 / y1);
        rq = main.matrixOp.multiplydot(rq, 1.0 / y2);
        double AB = main.d.distances(ra, rb);
        double CD = main.d.distances(rc, rd);
        double PQ = main.d.distances(rp, rq);
        double K1 = Math.exp(-a1 * a2 * AB * AB / y1);
        double K2 = Math.exp(-a3 * a4 * CD * CD / y2);
        double PA[][] = new double[2][ra.length];
        double QA[][] = new double[2][ra.length];
        double VPQ[] = new double[ra.length];
        for (int j = 0; j < ra.length; j++) {
            VPQ[j] = main.d.distance(rp[j], rq[j]);
            PA[0][j] = main.d.distance(rp[j], ra[j]);
            PA[1][j] = main.d.distance(rp[j], rb[j]);
            QA[0][j] = main.d.distance(rq[j], rc[j]);
            QA[1][j] = main.d.distance(rq[j], rd[j]);
        }
        double del = epsilon1 + epsilon2;
        double sum = 0;
        for (int i = 0; i <= l1[0] + l2[0] + l3[0] + l4[0]; i++) {
            for (int j = 0; j <= l1[1] + l2[1] + l3[1] + l4[1]; j++) {
                for (int k = 0; k <= l1[2] + l2[2] + l3[2] + l4[2]; k++) {
                    sum += X(i, 0, l1, l2, l3, l4, epsilon1, epsilon2, QA, PA, VPQ, del)
                            * X(j, 1, l1, l2, l3, l4, epsilon1, epsilon2, QA, PA, VPQ, del)
                            * X(k, 2, l1, l2, l3, l4, epsilon1, epsilon2, QA, PA, VPQ, del)
                            * main.g.F(i + j + k, PQ * PQ / (4 * del));
                }
            }
        }
        sum *= 2 * Math.pow(Math.PI, 2.5) * K1 * K2 / (y1 * y2 * Math.pow(y1 + y2, 0.5));
        return sum;
    }

}

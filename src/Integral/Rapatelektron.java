/* Source : Computational Techniques in Quantum Chemistry
 * and Molecular Physics : The overlap integral for unnormalized GTF 
 * see equation 25 to 26 on page 365
*/
package Integral;

import Function.*;

/**
 *
 * @author Agung Danu Wijaya
 */
public class Rapatelektron {

    Mainfunction a;

    public Rapatelektron(
            Mainfunction a
    ) {
        this.a = a;
    }

    public double I(
            double PAx,
            double PBx,
            double l1, double l2, double y) {
        double Ix = 0;
        for (int i = 0; i <= (l1 + l2) * 0.5; i++) {
            Ix += a.b.fk(2 * i, l1, l2, PAx, PBx) * Math.pow(y, -(2 * i + 1) * 0.5) * a.specialFunc.gamma((2 * i + 1) * 0.5);
        }
        return Ix;
    }

    public double S(
            double a1,
            double a2,
            double l1[],
            double l2[],
            double ra[],
            double rb[]
    ) {
        double y = a1 + a2;
        double rac[] = a.matrixOp.multiplydot(ra, a1);
        double rbc[] = a.matrixOp.multiplydot(rb, a2);
        double rp[] = a.matrixOp.adddot(rac, rbc);
        rp = a.matrixOp.multiplydot(rp, 1.0 / y);
        double AB = a.d.distances(ra, rb);
        double S = Math.exp(-a1 * a2 * AB * AB / y);
        double Ix = 1;
        for (int i = 0; i < ra.length; i++) {
            double PAx = a.d.distance(rp[i] ,ra[i]);
            double PBx = a.d.distance(rp[i] ,rb[i]);
            Ix *= I(PAx, PBx, l1[i], l2[i], y);
        }
        Ix *= S;
        return Ix;
    }

}

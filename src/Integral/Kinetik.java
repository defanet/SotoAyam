/* Source : Computational Techniques in Quantum Chemistry
 * and Molecular Physics : The kinetic energy integral for unnormalized GTF (unsymmetric form) 
 * see equation 28 and 31 on page 360
*/
package Integral;

import Function.Mainfunction;

/**
 *
 * @author Agung Danu Wijaya
 */
public class Kinetik {

    Mainfunction a;

    public Kinetik(
            Mainfunction a
    ) {
        this.a = a;
    }

    public double I(
            double a1,
            double a2,
            double l1[],
            double l2[],
            double ra[],
            double rb[],
            int i
    ) {

        double l1copy[] = a.matrixOp.copy(l1);
        double l2copy[] = a.matrixOp.copy(l2);
        double Ix = 0;
        l1copy[i] += -1;
        l2copy[i] += -1;
        Ix = l1[i] * l2[i] * a.rhoe.S(a1, a2, l1copy, l2copy, ra, rb);

        l1copy = a.matrixOp.copy(l1);
        l2copy = a.matrixOp.copy(l2);
        l1copy[i] += 1;
        l2copy[i] += 1;
        Ix += 4 * a1 * a2 * a.rhoe.S(a1, a2, l1copy, l2copy, ra, rb);

        l1copy = a.matrixOp.copy(l1);
        l2copy = a.matrixOp.copy(l2);
        l1copy[i] += 1;
        l2copy[i] += -1;
        Ix -= 2 * a1 * l2[i] * a.rhoe.S(a1, a2, l1copy, l2copy, ra, rb);

        l1copy = a.matrixOp.copy(l1);
        l2copy = a.matrixOp.copy(l2);
        l1copy[i] += -1;
        l2copy[i] += 1;
        Ix -= 2 * a2 * l1[i] * a.rhoe.S(a1, a2, l1copy, l2copy, ra, rb);
        return 0.5 * Ix;
    }

    public double EK(
            double a1,
            double a2,
            double l1[],
            double l2[],
            double ra[],
            double rb[]
    ) {
        double Energikinetik = 0;
        for (int i = 0; i < ra.length; i++) {
            Energikinetik += I(a1, a2, l1, l2, ra, rb, i);
        }
        return Energikinetik;
    }
}

/* Source : Computational Techniques in Quantum Chemistry
 * and Molecular Physics
 * see equation on page 360
*/
package Integral;

import Function.*;

/**
 *
 * @author Agung Danu Wijaya
 */
public class Fk {

    Mainfunction ma;

    public Fk(Mainfunction ma) {
        this.ma = ma;
    }

    public double per(double a, double b) {
        double r = ma.specialFunc.fac(a) / (ma.specialFunc.fac(a - b) * ma.specialFunc.fac(b));
        return r;
    }

    public double fk(int k, double l1, double l2, double PAx, double PBx) {
        double jumlah = 0;
        for (int i = 0; i <= l1; i++) {
            for (int j = 0; j <= l2; j++) {
                if (i + j == k) {
                    jumlah = jumlah + Math.pow(PAx, l1 - i)
                            * per(l1, i) * Math.pow(PBx, l2 - j)
                            * per(l2, j);
                }
            }
        }
        return jumlah;
    }

}

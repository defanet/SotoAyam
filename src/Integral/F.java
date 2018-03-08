/* Source : Computational Techniques in Quantum Chemistry
 * and Molecular Physics
 * see equation number 35 on page 366
*/
package Integral;

import Function.*;

/**
 *
 * @author Agung Danu Wijaya
 */
public class F {

    Mainfunction a;

    public F(Mainfunction a) {
        this.a = a;
    }

    public double F(double m, double w) {
        double F = 0;
        int batas = 20;
        if (w == 0) {
            F = 1.0 / (2 * m + 1);
        } else if (m == 0 & w > 1) {
            F = Math.pow(w, -0.5);
            F *= a.specialFunc.erf(Math.pow(w, 0.5)) * Math.pow(Math.PI, 0.5) / 2.0;
        } else {
            for (double i = 0; i < batas; i++) {
                F += (Math.pow(w, i) / (a.specialFunc.gamma(m + i + 1.5)));
            }
            F *= 0.5 * a.specialFunc.gamma(m + 0.5) * Math.exp(-w);
        }
        return F;
    }
}

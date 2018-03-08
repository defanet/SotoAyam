/**
 * Source :, taketa1966
 * see page 2315 equation 2.2
 */
package Integral;

import Function.Mainfunction;

/**
 * 
 * @author Agung Danu Wijaya
 */
public class Normalize {

    Mainfunction a;

    public Normalize(Mainfunction a) {
        this.a = a;
    }

    public double norm(double alpa, double l[]) {
        double norm = Math.sqrt(Math.pow(2, 2 * (l[0] + l[1] + l[2]) + 1.5)
                * Math.pow(alpa, l[0] + l[1] + l[2] + 1.5)
                / (a.specialFunc.fac(a.specialFunc.fac(2 * l[0] - 1)) * a.specialFunc.fac(a.specialFunc.fac(2 * l[1] - 1))
                * a.specialFunc.fac(a.specialFunc.fac(2 * l[2] - 1)) * Math.pow(Math.PI, 1.5)));
        return norm;
    }

}

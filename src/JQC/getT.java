/**
 * Find Energy Kinetic h(r)
 */
package JQC;

import Function.Mainfunction;
import JQC.GetData.datakHF;
import java.util.Map;

/**
 *
 * @author Agung Danu Wijaya
 */
public class getT {

    Mainfunction master;

    public getT(Mainfunction master) {
        this.master = master;
    }

    public double sumbasis(double ca[], double cb[], double la[], double lb[], double alphaa[], double alphab[], double Ra[], double Rb[]) {
        double sum = 0;
        for (int i = 0; i < cb.length; i++) {
            for (int m = 0; m < ca.length; m++) {
                double a = alphaa[m];
                double b = alphab[i];
                double res = master.T.EK(a, b, la, lb, Ra, Rb);
                sum += cb[i] * ca[m] * res * master.cn.norm(a, la) * master.cn.norm(b, lb);
            }
        }
        return sum;
    }

    public double getEK(datakHF a, datakHF b) {
        return sumbasis(a.c, b.c, a.l, b.l, a.alpa, b.alpa, a.R, b.R);
    }

   /* public static void main(String[] args) {
        Mainfunction s = new Mainfunction();
        getT b = new getT(s);
        Map<Integer, datakHF> datahf = s.gdata.get("H2O");
        System.out.println(b.getEK(datahf.get(0), datahf.get(1)));
    }*/

}

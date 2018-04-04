package JQC;

import Function.Mainfunction;
import JQC.getdata.datakHF;

/**
 *
 * @author Agung Danu Wijaya
 */
public class getS {

    Mainfunction master;

    public getS(Mainfunction master) {
        this.master = master;
    }

    public double sumbasis(double ca[], double cb[], double la[], double lb[], double alphaa[], double alphab[], double Ra[], double Rb[]) {
        double sum = 0;
        for (int i = 0; i < cb.length; i++) {
            for (int m = 0; m < ca.length; m++) {
                double a = alphaa[m];
                double b = alphab[i];
                double res = master.rhoe.S(a, b, la, lb, Ra, Rb);
                sum += cb[i] * ca[m] * res *master.cn.norm(a, la) * master.cn.norm(b, lb);
            }
        }
        return sum;
    }

    public double getS(datakHF a, datakHF b) {
        return sumbasis(a.c, b.c, a.l, b.l, a.alpa, b.alpa, a.R, b.R);
    }
}

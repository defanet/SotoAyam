package JQC;

import Function.Mainfunction;
import JQC.getdata.datakHF;
import java.util.Map;

/**
 *
 * @author Agung Danu Wijaya
 */
public class getpointvalue {

    Mainfunction master;

    public getpointvalue(Mainfunction master) {
        this.master = master;
    }

    public double GTO(double indeks, double r[], double l[]) {
        double besarr = Math.pow(r[0], 2) + Math.pow(r[1], 2) + Math.pow(r[2], 2);
        return Math.pow(r[0], l[0]) * Math.pow(r[1], l[1]) * Math.pow(r[2], l[2]) * Math.exp(-indeks * besarr);
    }

    public double sumbasis(double ca[], double la[], double alphaa[], double Ra[]) {
        double sum = 0;
        for (int m = 0; m < ca.length; m++) {
            double a = alphaa[m];
            double res = GTO(a, Ra, la);
            sum += ca[m] * res * master.cn.norm(a, la);
        }
        return sum;
    }

    public double bf(datakHF a, double R[]) {
        double r[] = master.matrixOp.adddot(R, master.matrixOp.multiplydot(a.R, -1));
        return sumbasis(a.c, a.l, a.alpa, r);
    }

    public static void main(String args[]) {
        Mainfunction master = new Mainfunction();
        Map<Integer, datakHF> datahf = master.gdata.get("H2O");
        getpointvalue a = new getpointvalue(master);
        double[] R = {0.3, 0.1, 0.5};
        System.out.println(a.bf(datahf.get(3), R));
    }
}

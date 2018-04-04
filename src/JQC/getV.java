/*
 * Note HF eq 3.152
 */
package JQC;

import Function.Mainfunction;
import JQC.getdata.datakHF;
import Posisi.Geo;
import java.util.Map;

/**
 *
 * @author Agung Danu Wijaya
 */
public class getV {

    Mainfunction master;

    public getV(Mainfunction master) {
        this.master = master;
    }

    public double sumbasis(double ca[], double cb[], double la[], double lb[], double alphaa[], double alphab[], double Ra[], double Rb[], double[] Rc) {
        double sum = 0;
        for (int i = 0; i < cb.length; i++) {
            for (int m = 0; m < ca.length; m++) {
                double a = alphaa[m];
                double b = alphab[i];
                double res = -1 * master.nai.NAI(a, b, la, lb, Ra, Rb, Rc);
                sum += cb[i] * ca[m] * res * master.cn.norm(a, la) * master.cn.norm(b, lb);
            }
        }
        return sum;
    }

    public double getV(datakHF nu, datakHF v, double Ra[]) {
        return sumbasis(nu.c, v.c, nu.l, v.l, nu.alpa, v.alpa, nu.R, v.R, Ra);
    }

   /* public static void main(String[] args) {
        Mainfunction s = new Mainfunction();
        getV b = new getV(s);
        Map<Integer, datakHF> datahf = s.gdata.get("H2O");
        Geo.datageo geo = s.geo.data.get("H2O");
        double[][] R = geo.R;
        int[] batom = geo.numProton;
        s.matrixOp.disp(datahf.get(1).R);
        s.matrixOp.disp(datahf.get(1).alpa);
        s.matrixOp.disp(datahf.get(1).c);
        s.matrixOp.disp(datahf.get(1).l);
        s.matrixOp.disp(datahf.get(0).R);
        s.matrixOp.disp(datahf.get(0).alpa);
        s.matrixOp.disp(datahf.get(0).c);
        s.matrixOp.disp(datahf.get(0).l);
        System.out.println(b.getV(datahf.get(1), datahf.get(0),R[0]));
    }*/
}

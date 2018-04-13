/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JQC;

import Function.Mainfunction;
import JQC.getdata.datakHF;

/**
 *
 * @author Agung Danu Wijaya
 */
public class getG {

    Mainfunction master;

    public getG(Mainfunction master) {
        this.master = master;
    }

    public double sumbasis(double ca[], double cb[], double la[], double lb[], double alphaa[], double alphab[], double Ra[], double Rb[],
            double ca1[], double cb1[], double la1[], double lb1[], double alphaa1[], double alphab1[], double Ra1[], double Rb1[]
    ) {
        double sum = 0;
        for (int i = 0; i < ca.length; i++) {
            for (int m = 0; m < cb.length; m++) {
                for (int i1 = 0; i1 < ca1.length; i1++) {
                    for (int m1 = 0; m1 < cb1.length; m1++) {
                        double a = alphaa[i];
                        double b = alphab[i1];
                        double c = alphaa1[m];
                        double d = alphab1[m1];
                        double res = master.eri.ERI(la, la1, lb, lb1, a, c, b, d, Ra, Ra1, Rb, Rb1);
                        sum += ca[i] * cb[i1] * ca1[m] * cb1[m1] * res * master.cn.norm(a, la) * master.cn.norm(c, la1) * master.cn.norm(b, lb) * master.cn.norm(d, lb1);
                    }
                }
            }
        }
        return sum;
    }

    public double getG(datakHF a, datakHF b, datakHF c, datakHF d) {
        return sumbasis(a.c, b.c, a.l, b.l, a.alpa, b.alpa, a.R, b.R, c.c, d.c, c.l, d.l, c.alpa, d.alpa, c.R, d.R);
    }

    /*public static void main(String[] args) {
        Mainfunction s = new Mainfunction();
        Map<Integer, datakHF> datahf = s.gdata.get("H2O");
        getG ER = new getG(s);
        System.err.println(ER.getG(datahf.get(0), datahf.get(0), datahf.get(5), datahf.get(0)));
    }*/
}

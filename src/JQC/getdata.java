package JQC;

import Basis.Data;
import Function.Mainfunction;
import Posisi.Geo;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Agung Danu Wijaya
 */
public class getdata {

    Mainfunction master;

    public getdata(Mainfunction master) {
        this.master = master;
    }

    public class datakHF {

        public String discibe;  //discribe number electron, atom's name, state
        public double[] R;      //position atom
        public double[] c;
        public double[] l;
        public double[] alpa;
        public double batom;

        public datakHF(String discribe, double[] R, double[] c, double[] l, double[] alpa, double batom) {
            this.discibe = discribe;
            this.R = R;
            this.c = c;
            this.l = l;
            this.alpa = alpa;
            this.batom = batom;
        }
    }

    public Map<Integer, datakHF> get(String namageo) {
        Map<Integer, datakHF> datahf = new HashMap<>();
        Geo.datageo geo = master.geo.data.get(namageo);
        double[][] R = geo.R;
        int[] batom = geo.numProton;
        String[] naom = geo.atom;
        int index = 0;
        for (int k = 0; k < batom.length; k++) {
            Data.basis[] dbasis = master.basisdata.data.get(batom[k]);
            for (Data.basis dbasi : dbasis) {
                double[][] coefexp = dbasi.b;
                String kulit = dbasi.a;
                double lmn[][] = master.pkt.data.get(kulit);
                double[] coef = new double[coefexp.length];
                double[] exp = new double[coefexp.length];
                for (int i = 0; i < coefexp.length; i++) {
                    coef[i] = coefexp[i][1];
                    exp[i] = coefexp[i][0];
                }
                for (int i = 0; i < lmn.length; i++) {
                    datahf.put(index, new datakHF(batom[k] + " " + naom[k] + " " + kulit, R[k], coef, lmn[i], exp, batom[k]));
                    index++;
                }
            }
        }
        return datahf;
    }

      public static void main(String[] args) {
        Mainfunction a = new Mainfunction();
        getdata b = new getdata(a);
        Map<Integer, datakHF> datahf = b.get("He");
        for (int i = 0; i < datahf.size(); i++) {
            a.matrixOp.disp(datahf.get(i).discibe);
            a.matrixOp.disp(datahf.get(i).R);
            a.matrixOp.disp(datahf.get(i).alpa);
            a.matrixOp.disp(datahf.get(i).l);
            a.matrixOp.disp(datahf.get(i).c);
            a.matrixOp.disp("------------");
        }
    }
}

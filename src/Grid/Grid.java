/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Grid;

import Function.Mainfunction;
import JQC.getdata;
import Posisi.Geo;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Agung danu wijaya
 */
public class Grid {

    public HashMap<Integer, HashMap<Integer, double[]>> points = new HashMap<>();
    GridAtom grd = new GridAtom();

    public Grid(String nama, Mainfunction a) {
        Map<Integer, getdata.datakHF> datahf = a.gdata.get(nama);
        Geo.datageo geo = a.geo.data.get("H2O");
        String namaatom[] = geo.atom;
        for (int j = 0; j < namaatom.length; j++) {
            HashMap<Integer, double[]> point1 = new HashMap<>();
            HashMap<Integer, double[]> point = new HashMap<>();
            for (int i = 0; i < datahf.size(); i++) {
                String h[] = (datahf.get(i).discibe).split(" ");
                if (h[1].equals(namaatom[j])) {
                    double batom = (datahf.get(i).batom);
                    double[] xyz = (datahf.get(i).R);
                    point = grd.rungrid(batom, xyz);
                }
                int in = point1.size();
                for (int k = 0; k < point.size(); k++) {
                    double data[] = point.get(k);
                    point1.put(k + in, data);
                }
            }

             points.put(j, point1);
        }
        //Pekerjaan selanjutnya
        becke_reweight_atoms d = new becke_reweight_atoms();
        d.becke_reweight_atoms(a, nama, points);
        //System.out.println((int)d.sbecke(3));
    }

}

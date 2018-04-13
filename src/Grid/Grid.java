/**
 * Setting grid untuk menghitung Exc dan Vxc
 */
package Grid;

import Function.Mainfunction;
import JQC.getdata;
import Posisi.Geo;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Agung Danu Wijaya
 */
public class Grid {

    public HashMap<Integer, HashMap<Integer, double[]>> points = new HashMap<>();
    GridAtom grd = new GridAtom();

    public Grid(String nama, Mainfunction a) {
        Map<Integer, getdata.datakHF> datahf = a.gdata.get(nama);
        Geo.datageo geo = a.geo.data.get(nama);
        String namaatom[] = geo.atom;
        for (int j = 0; j < namaatom.length; j++) {
            HashMap<Integer, double[]> point = new HashMap<>();
            double batom = geo.numProton[j];
            double[] xyz = geo.R[j];
            point = grd.rungrid(batom, xyz);
            points.put(j, point);
        }
        becke_reweight_atoms d = new becke_reweight_atoms();
        d.becke_reweight_atoms(a, nama, points);
    }

    public double[][] setbfamps(Mainfunction master, Map<Integer, getdata.datakHF> bfs, HashMap<Integer, HashMap<Integer, double[]>> points) {
        int nbf = bfs.size();
        double[][] bfamps = new double[points.get(0).size() * points.size()][nbf];
        for (int j = 0; j < nbf; j++) {
            int index = 0;
            for (int i = 0; i < points.size(); i++) {
                for (int k = 0; k < points.get(i).size(); k++) {
                    double R[] = new double[3];
                    double RW[] = points.get(i).get(k);
                    for (int l = 0; l < R.length; l++) {
                        R[l] = RW[l];
                    }
                    bfamps[index][j] = master.gpoint.bf(bfs.get(j), R);
                    index++;
                }
            }
        }
        return bfamps;
    }

    public HashMap<Integer, double[]> pointsmap(HashMap<Integer, HashMap<Integer, double[]>> points) {
        HashMap<Integer, double[]> data = new HashMap<>();
        int index = 0;
        for (int i = 0; i < points.size(); i++) {
            for (int k = 0; k < points.get(i).size(); k++) {
                data.put(index, points.get(i).get(k));
                index++;
            }
        }
        return data;
    }

}

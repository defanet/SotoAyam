/**
 * Setting koordinat atom --> interface
 */
package Posisi;

import Function.Mainfunction;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Agung Danu Wijaya, Farhamsa D
 */
public class Geo {

    public Map<String, datageo> data;
    Mainfunction master;

    public class datageo {
        public String[] atom;  //atom's name
        public int[] numProton; //number electron or proton each atom
        public double[][] R;   //atom position

        public datageo(int[] numElect, String[] atom, double[][] R) {
            this.numProton = numElect;
            this.atom = atom;
            this.R = R;
        }
    }

    public Geo(Mainfunction master) {
        this.master = master;
        double bondLenght = 1.889725989; //Bond lengths in Bohr (Angstrom)
        Map<String, datageo> data = new HashMap<>();
        
        /*------------------------- H2O --------------------------------------*/
        double[][] R_H2O = {{0.00000000, 0.00000000, 0.04851804},
        {0.75300223, 0.00000000, -0.51923377},
        {-0.75300223, 0.00000000, -0.51923377}};
        String atom_H2O[] = {"O", "H", "H"};
        int numE_H2O[] = {8, 1, 1};
        datageo H2O = new datageo(numE_H2O, atom_H2O, master.matrixOp.multiplydot(R_H2O, bondLenght));
        data.put("H2O", H2O);
        
        /*------------------------- H2 ---------------------------------------*/
        double[][] R_H2 = {{0.00000000, 0.00000000, 0.36628549},
        {0.00000000, 0.00000000, -0.36628549}};
        String atom_H2[] = {"H", "H"};
        int numE_H2[] = {1, 1};
        datageo H2 = new datageo(numE_H2, atom_H2, master.matrixOp.multiplydot(R_H2, bondLenght));
        data.put("H2", H2);
        this.data = data;
        
        /*------------------------- C6H6 -------------------------------------*/
        double[][] R_C6H6 = {{0.98735329, 0.98735329, 0.00000000},
        {1.34874967, -0.36139639, 0.00000000},
        {0.36139639, -1.34874967, 0.00000000},
        {-0.98735329, -0.98735329, 0.00000000},
        {-1.34874967, 0.36139639, 0.00000000},
        {-0.36139639, 1.34874967, 0.00000000},
        {1.75551741, 1.75551741, 0.00000000},
        {2.39808138, -0.64256397, 0.00000000},
        {0.64256397, -2.39808138, 0.00000000},
        {-1.75551741, -1.75551741, 0.00000000},
        {-2.39808138, 0.64256397, 0.00000000},
        {-0.64256397, 2.39808138, 0.00000000}};
        String[] atoms_C6H6 = {"C", "C", "C", "C", "C", "C", "H", "H", "H", "H", "H", "H"};
        int[] numA_C6H6 = {6, 6, 6, 6, 6, 6, 1, 1, 1, 1, 1, 1};
        datageo geoC6H6 = new datageo(numA_C6H6, atoms_C6H6, master.matrixOp.multiplydot(R_C6H6, bondLenght));
        data.put("C6H6", geoC6H6);

        /*--------------------------- O2 -------------------------------------*/
        double[][] R_O2 = {{0.00000000, 0.00000000, 0},
        {0.00000000, 0, 2.38}};
        String[] atom_O2 = {"O", "O"};
        int[] numA_O2 = {8, 8};
        datageo O2 = new datageo(numA_O2, atom_O2, R_O2);
        data.put("O2", O2);
        
        /*-------------------------- CH4 -------------------------------------*/
        double[][] R_CH4 = {{0.00000000, 0.00000000, 0.00000000},
        {0.62558332, -0.62558332, 0.62558332},
        {-0.62558332, 0.62558332, 0.62558332},
        {0.62558332, 0.62558332, -0.62558332},
        {-0.62558332, -0.62558332, -0.62558332}};
        String[] atom_CH4 = {"C", "H", "H", "H", "H"};
        int[] numA_CH4 = {6, 1, 1, 1, 1};
        datageo CH4 = new datageo(numA_CH4, atom_CH4, master.matrixOp.multiplydot(R_CH4, bondLenght));
        data.put("CH4", CH4);
        
        /*---------------------------- O -------------------------------------*/
        double[][] R_O = {{0.0, 0.0, 0}};
        String[] atom_O = {"O"};
        int[] numA_O = {8};
        datageo O = new datageo(numA_O, atom_O, R_O);
        data.put("O", O);
        
        /*---------------------------- C -------------------------------------*/
        double[][] R_C = {{0.0, 0.0, 0}};
        String[] atom_C = {"C"};
        int[] numA_C = {6};
        datageo C = new datageo(numA_C, atom_C, R_C);
        data.put("C", C);
        
        this.data = data;
    }

    public double energi(datageo v) {
        double energi = 0;
        double d1[][] = v.R;
        int c[] = v.numProton;
        for (int i = 0; i < d1.length; i++) {
            for (int j = 0; j < d1.length; j++) {
                if (i != j) {
                    energi += c[i] * c[j] * 1.0 / master.d.distances(d1[i], d1[j]);
                }
            }
        }
        return (energi / 2.0);
    }

    public int tengah(datageo v) {
        return (int) master.matrixOp.sum(v.numProton) / 2;
    }

    public static void main(String[] args) {
        Mainfunction master = new Mainfunction();
        Geo geo = new Geo(master);
        System.out.println(geo.tengah(geo.data.get("H2O")));
    }
}

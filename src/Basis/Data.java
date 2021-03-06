/* Source : Modern Quantum Chemistry: Introduction to Advanced Electronic Structure Theory
 * By Attila Szabo, Neil S. Ostlund
 * See section 3.5.1 page 153 
 */
package Basis;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Agung Danu Wijaya
 * @author Dedy Farhamsa
 */
public class Data {

    public Map<Integer, basis[]> data;

    public class basis {

        public String a;
        public double[][] b;

        public basis(String a, double[][] b) {
            this.a = a;
            this.b = b;
        }
    }

    public Data() {
        /*source https://bse.pnl.gov/bse/portal*/
        Map<Integer, basis[]> data = new HashMap<>();
        double[][] Z1S = {{3.4252509099999999, 0.15432897000000001},
        {0.62391373000000006, 0.53532813999999995},
        {0.16885539999999999, 0.44463454000000002}};
        basis b1[] = {new basis("S", Z1S)};
        data.put(1, b1);
        /*---------------------------------------------------------------------*/
        double[][] Z2S = {{6.3624213899999997, 0.15432897000000001},
        {1.1589229999999999, 0.53532813999999995},
        {0.31364978999999998, 0.44463454000000002}};
        basis b2[] = {new basis("S", Z2S)};
        data.put(2, b2);
        /*---------------------------------------------------------------------*/
        double[][] Z8_1S = {{130.70931999999999, 0.15432897000000001},
        {23.808861, 0.53532813999999995},
        {6.4436083000000002, 0.44463454000000002}};
        double[][] Z8_2S = {{5.0331513000000001, -0.099967230000000004},
        {1.1695960999999999, 0.39951282999999999},
        {0.38038899999999998, 0.70011546999999996}};
        double[][] Z8_2P = {{5.0331513000000001, 0.15591627},
        {1.1695960999999999, 0.60768372000000004},
        {0.38038899999999998, 0.39195739000000002}};
        basis b3[] = {new basis("S", Z8_1S), new basis("S", Z8_2S), new basis("P", Z8_2P)};
        data.put(8, b3);
        /*---------------------------------------------------------------------*/
        double[][] Z6_1S = {{71.616837000000004, 0.15432897000000001},
        {13.045095999999999, 0.53532813999999995},
        {3.5305122, 0.44463454000000002}};
        double[][] Z6_2S = {{2.9412493999999998, -0.099967230000000004},
        {0.68348310000000001, 0.39951282999999999},
        {0.22228990000000001, 0.70011546999999996}};
        double[][] Z6_2P = {{2.9412493999999998, 0.15591627},
        {0.68348310000000001, 0.60768372000000004},
        {0.22228990000000001, 0.39195739000000002}};
        basis Z6[] = {new basis("S", Z6_1S), new basis("S", Z6_2S), new basis("P", Z6_2P)};
        data.put(6, Z6);

        this.data = data;

    }
}

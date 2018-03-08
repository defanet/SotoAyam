/* Source : Computational Techniques in Quantum Chemistry
 * and Molecular Physics
 * see equation 3 on page 350
*/
package Basis;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Agung Danu Wijaya
 */
public class Pangkat {

    public Map<String, double[][]> data;

    public Pangkat() {

        Map<String, double[][]> data = new HashMap<>();
        double[][] S_lmn = {{0, 0, 0}};
        data.put("S", S_lmn);

        double[][] P_lmn = {{1, 0, 0}, {0, 1, 0}, {0, 0, 1}};
        data.put("P", P_lmn);
        this.data = data;
        
        /*double[][] D_lmn = {{2, 0, 0}, {1, 1, 0}, {0, 0, 1}};
        data.put("D", D_lmn);
        this.data = data;
        */
    }

}

/*calculate distance of two position of dot or vector*/
package Posisi;

/**
 *
 * @author Agung Danu Wijaya
 */
public class Posisi {

    public double distances(double a[], double b[]) {
        double sum = 0;
        for (int i = 0; i < a.length; i++) {
            sum += Math.pow(a[i] - b[i], 2);
        }
        return Math.sqrt(sum);
    }
    
     public double distance(double a, double b) {
        return a-b;
    }    
}

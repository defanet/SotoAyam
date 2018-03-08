package JQC;

import Function.*;
import JQC.getdata.datakHF;
import Posisi.Geo;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Agung Danu Wijaya
 */
public class Mainintegral {

    int s = 0;
    int ak;
    int akk;
    public double ints[][][][];
    public double[][] EV;
    public double[][] EK;
    public double[][] S;
    Mainfunction master;
    LinkedList<int[]> uniq;

    public Mainintegral(Mainfunction master) {
        this.master = master;
        uniq = new LinkedList();
        int[] def = {-1, -1, -1, -1};
        uniq.addFirst(def);
    }

    public void one(String nama) {
        Geo.datageo geo = master.geo.data.get(nama);
        double[][] R = geo.R;
        int[] Zp = geo.numProton;
        Map<Integer, datakHF> datahf = master.gdata.get(nama);
        getV AE = new getV(master);
        getS den = new getS(master);
        getT ki = new getT(master);
        int N = datahf.size();
        double[][] EV = new double[N][N];                                       //energi potensial nuclie 3.152
        double[][] EK = new double[N][N];                                       //energi Kinetik 3.151
        double[][] S = new double[N][N];                                        //S --> overlap orthogonal [1,0] 3.136
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                for (int k = 0; k < geo.numProton.length; k++) {
                    EV[i][j] += AE.getV(datahf.get(i), datahf.get(j), R[k]) * Zp[k];
                }
                S[i][j] = den.getS(datahf.get(i), datahf.get(j)); //3.136
                EK[i][j] = ki.getEK(datahf.get(i), datahf.get(j));
            }
        }
        this.EV = EV;
        this.EK = EK;
        this.S = S;

    }

    public class Counter {

        public synchronized void input(double R, int i, int j, int k, int l) {
            ints[i][j][k][l] = ints[j][i][k][l] = ints[i][j][l][k] = ints[j][i][l][k]
                    = ints[k][l][i][j] = ints[l][k][i][j] = ints[k][l][j][i]
                    = ints[l][k][j][i] = R;
            akk++;
        }
    }

    public class worker extends Thread {

        datakHF a, b, c, d;
        int i, j, k, l;
        Counter e;

        public worker(Counter e, datakHF a, datakHF b, datakHF c, datakHF d, int i, int j, int k, int l) {
            this.a = a;
            this.b = b;
            this.c = c;
            this.d = d;
            this.i = i;
            this.j = j;
            this.k = k;
            this.l = l;
            this.e = e;
        }

        public void run() {
            getG ER = new getG(master);
            double R = ER.getG(a, b, c, d);
            e.input(R, i, j, k, l);
        }
    }

    public void two(String nama) throws InterruptedException {
        getG ER = new getG(master);
        String a = "";
        double iteration = 0;
        Map<Integer, datakHF> datahf = master.gdata.get(nama);
        int n = datahf.size();
        double ints[][][][] = new double[n][n][n][n];
        this.ints = ints;
        double cek[][][][] = new double[n][n][n][n];
        Counter con = new Counter();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.println((double) (iteration++ / (n * n)) * 100.0);
                for (int k = 0; k < n; k++) {
                    for (int l = 0; l < n; l++) {
                        if (cek[i][j][k][l] == 0) {
                            ak++;
                            //jangan melebihi batas thread
                            while (Thread.activeCount() > 800) {
                            }
                            Thread r = new worker(con, datahf.get(i), datahf.get(k), datahf.get(j), datahf.get(l), i, j, k, l);
                            r.start();

                            cek[i][j][k][l] = cek[j][i][k][l] = cek[i][j][l][k] = cek[j][i][l][k]
                                    = cek[k][l][i][j] = cek[l][k][i][j] = cek[k][l][j][i]
                                    = cek[l][k][j][i] = 10;
                        }
                    }
                }
            }
        }
        //tunggu sampai semua thread selesai
        while (ak != akk) {
            String h = (ak + " " + akk + " " + Thread.activeCount());
        }
        // this.ints = ints;
    }

    /*
     public String cektwo(int n) {
     String a = "";
     String b = "";
     //int jumlahintegral=0;
     for (int i = 0; i < n; i++) {
     for (int j = 0; j < n; j++) {
     for (int k = 0; k < n; k++) {
     for (int l = 0; l < n; l++) {
     String h = i + " " + j + " " + k + " " + l;
     if (a.contains(h) == false) {
     //System.out.println("int 2 cek unik: "+jumlahintegral++);
     b += h + "\n";
     a += h + "\n";
     h = j + " " + i + " " + k + " " + l;
     a += h + "\n";
     h = i + " " + j + " " + l + " " + k;
     a += h + "\n";
     h = j + " " + i + " " + l + " " + k;
     a += h + "\n";
     h = k + " " + l + " " + i + " " + j;
     a += h + "\n";
     h = l + " " + k + " " + i + " " + j;
     a += h + "\n";
     h = k + " " + l + " " + j + " " + i;
     a += h + "\n";
     h = l + " " + k + " " + j + " " + i;
     a += h + "\n";
     }
     }
     }
     }

     }
     return b;
     }
     
    public void two1(String nama) {
        double iteration = 0.0;
        //int jumlahintegral=0;
        Map<Integer, datakHF> datahf = master.gdata.get(nama);
        getG ER = new getG(master);
        int n = datahf.size();
        double ints[][][][] = new double[n][n][n][n];
        //String dataint = cektwo(n);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.println((double) (iteration++ / (n * n)) * 100.0);
                for (int k = 0; k < n; k++) {
                    for (int l = 0; l < n; l++) {
                        String h = i + ";" + j + ";" + k + ";" + l;
                        //System.out.println(h);
                        // if (isUniq(n, h)) {
                        //System.err.println(h);
                        //jumlahintegral++;
                        //System.out.println("int 2 : "+jumlahintegral);
                        //ints[i][j][k][l]= ER.getG(datahf.get(i), datahf.get(k), datahf.get(j), datahf.get(l));
                        double R = ER.getG(datahf.get(i), datahf.get(k), datahf.get(j), datahf.get(l));
                         ints[i][j][k][l] = ints[j][i][k][l] = ints[i][j][l][k] = ints[j][i][l][k]
                         = ints[k][l][i][j] = ints[l][k][i][j] = ints[k][l][j][i]
                         = ints[l][k][j][i] = R;
                        //}
                    }
                }
            }
        }
        this.ints = ints;
    }

    public boolean isUniq(int n, String test) {
        boolean IsUniq = false;
        int i = 0, j = 1, k = 2, l = 3;
        //System.err.println("------>"+test);
        String[] str = test.split(";");
        int[] t = {Integer.parseInt(str[0]), Integer.parseInt(str[1]), Integer.parseInt(str[2]), Integer.parseInt(str[3])};
        for (int a = 0; a < uniq.size(); a++) {
            int[] x = uniq.get(a);
            //System.out.print(x[0]+";"+x[1]+";"+x[2]+";"+x[3]);
            //System.out.print(" ---- ");
            //System.out.println(t[0]+";"+t[1]+";"+t[2]+";"+t[3]);

            if (x[j] == t[i] && x[i] == t[j] && x[k] == t[k] && x[l] == t[l]) {
                IsUniq = false;
            } else if (x[i] == t[i] && x[j] == t[j] && x[l] == t[k] && x[k] == t[l]) {
                IsUniq = false;
            } else if (x[j] == t[i] && x[i] == t[j] && x[l] == t[k] && x[k] == t[l]) {
                IsUniq = false;
            } else if (x[k] == t[i] && x[l] == t[j] && x[i] == t[k] && x[j] == t[l]) {
                IsUniq = false;
            } else if (x[l] == t[i] && x[k] == t[j] && x[i] == t[k] && x[j] == t[l]) {
                IsUniq = false;
            } else if (x[k] == t[i] && x[l] == t[j] && x[j] == t[k] && x[i] == t[l]) {
                IsUniq = false;
            } else if (x[l] == t[i] && x[k] == t[j] && x[j] == t[k] && x[i] == t[l]) {
                IsUniq = false;
            } else {
                IsUniq = true;
            }
            if (!IsUniq) {
                break;
            }
        }
        if (IsUniq) {
            uniq.addLast(t);
            //System.out.println(t[0]+";"+t[1]+";"+t[2]+";"+t[3]);
        }
        return IsUniq;
    }

    public String cekone(int n) {
        String a = "";
        String b = "";
        //int jumlahintegral=0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                String h = i + " " + j;
                if (a.contains(h) == false) {
                    //jumlahintegral++;
                    b += h + "\n";
                    a += h + "\n";
                    h = j + " " + i;
                    a += h + "\n";
                }
            }

        }
        //System.out.println("int 1 : "+ jumlahintegral);
        return b;
    }
     */

}

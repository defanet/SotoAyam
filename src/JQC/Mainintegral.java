package JQC;

import Function.*;
import JQC.getdata.datakHF;
import Posisi.Geo;
import java.util.Map;

/**
 *
 * @author Agung Danu Wijaya
 */
public class Mainintegral {

    int s = 0;
    int ak;
    int akk;
    int ak1;
    int akk1;
    public double ints[][][][];
    public double[][] EV;
    public double[][] EK;
    public double[][] S;
    Mainfunction master;


    public Mainintegral(Mainfunction master) {
        this.master = master;
    }

    public void one(String nama) {
        Counter1 con = new Counter1();
        Geo.datageo geo = master.geo.data.get(nama);
        double[][] R = geo.R;
        int[] Zp = geo.numProton;
        Map<Integer, datakHF> datahf = master.gdata.get(nama);
        getS den = new getS(master);
        getT ki = new getT(master);
        int N = datahf.size();
        System.out.println(N);
        double[][] EV = new double[N][N];                                       //energi potensial nuclie 3.152
        this.EV = EV;
        double[][] EK = new double[N][N];                                       //energi Kinetik 3.151
        double[][] S = new double[N][N];                                        //S --> overlap orthogonal [1,0] 3.136
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                for (int k = 0; k < geo.numProton.length; k++) {
                    ak1++;
                    while (Thread.activeCount() > 2000) {
                    }
                    Thread r = new worker1(con, datahf.get(i), datahf.get(j), R[k], i, j, Zp[k]);
                    r.start();
                }
                S[i][j] = den.getS(datahf.get(i), datahf.get(j)); //3.136
                EK[i][j] = ki.getEK(datahf.get(i), datahf.get(j));
            }
        }
        while (ak1 != akk1) {
            String h = (ak + " " + akk + " " + Thread.activeCount());
        }

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

    public class Counter1 {

        public synchronized void input(double NAI, int i, int j) {
            EV[i][j] += NAI;
            akk1++;
        }
    }

    public class worker1 extends Thread {

        datakHF a, b;
        Counter1 e;
        double R[];
        int i, j;
        double atom;

        public worker1(Counter1 e, datakHF a, datakHF b, double R[], int i, int j, double atom) {
            this.a = a;
            this.b = b;
            this.R = R;
            this.e = e;
            this.i = i;
            this.j = j;
            this.atom = atom;
        }

        public void run() {
            getV AE = new getV(master);
            double NAI = AE.getV(a, b, R) * atom;
            e.input(NAI, i, j);
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
                            while (Thread.activeCount() > 2000) {
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
    }

  }

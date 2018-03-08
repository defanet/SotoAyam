/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JQC;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author root
 */
public class cobatread {

    int data = 0;
     int data1 = 0;

    public class Counter {

        long count = 0;

        public synchronized void add(long value) {
            this.count += value;
            data1++;
        }
    }

    public class CounterThread extends Thread {

        protected Counter counter = null;
        int i;

        public CounterThread(Counter counter, int i) {
            this.counter = counter;
            this.i = i;
        }

        public void run() {
            counter.add(1);
        }
    }

    public void main() {
        Counter counter = new Counter();
        for (int i = 0; i < 1000; i++) {
            data++;
            Thread threadA = new CounterThread(counter, i);
            threadA.start();
        }
        while (data!=counter.count) {
            System.out.println(data1+" "+data+" "+counter.count);
        }
    }

    public static void main(String[] args) {
        cobatread a = new cobatread();
        a.main();
    }

}

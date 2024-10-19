package org.openjdk.jol.samples;

import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.vm.VM;

import java.io.PrintWriter;

import static java.lang.System.out;

/**
 * @author Aleksey Shipilev
 */
public class JOLSample_22_Promotion {

    /*
     * The example of object promotion.
     *
     * Once the object survives the garbage collections, it is getting
     * promoted to another generation. In this example, we can track
     * the addresses of the objects, as it changes over time.
     *
     * VM also needs to record the "age" (that is, the number of GC
     * cycles the object had survived) of the object somewhere, and
     * it is stored in mark word as well. See how particular mark word
     * bits change with each promotion.
     *
     * Run with test with smaller heap (about 1 GB) for best results.
     */

    static volatile Object sink;

    public static void main(String[] args) {
        out.println(VM.current().details());

        PrintWriter pw = new PrintWriter(System.out, true);

        Object o = new Object();

        ClassLayout layout = ClassLayout.parseInstance(o);

        long lastAddr = VM.current().addressOf(o);
        pw.printf("*** Fresh object is at %x%n", lastAddr);
        out.println(layout.toPrintable());

        int moves = 0;
        for (int i = 0; i < 100000; i++) {
            long cur = VM.current().addressOf(o);
            if (cur != lastAddr) {
                moves++;
                pw.printf("*** Move %2d, object is at %x%n", moves, cur);
                out.println(layout.toPrintable());
                lastAddr = cur;
            }

            // make garbage
            for (int c = 0; c < 10000; c++) {
                sink = new Object();
            }
        }

        long finalAddr = VM.current().addressOf(o);
        pw.printf("*** Final object is at %x%n", finalAddr);
        out.println(layout.toPrintable());

        pw.close();
    }

}
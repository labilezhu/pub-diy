package com.mygraphql.jvm.insider.safepoint;

import org.openjdk.jol.samples.TestClass;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

/**
 bash -c 'echo $$ > /tmp/jvm-insider.pid && exec setarch $(uname -m) --addr-no-randomize /home/labile/opensource/jdk/build/linux-x86_64-server-slowdebug-hsdis/jdk/bin/java -XX:+AlwaysPreTouch  -Xms100m -Xmx100m -XX:MaxTenuringThreshold=5 -server -XX:+UseSerialGC  -XX:-UseCompressedOops -XX:+UnlockDiagnosticVMOptions "-Xlog:gc*=debug::tid" -Xlog:safepoint=debug::tid -cp /home/labile/pub-diy/jvm-insider-book/memory/java-obj-layout/out/production/java-obj-layout com.mygraphql.jvm.insider.safepoint.SafepointGDB'

 */
public class SafepointGDB {
    public static void printThread() {
        long nativeThreadId = Thread.currentThread().threadId();
        System.out.printf("threadName=%s, threadId=%d%n", Thread.currentThread().getName(), nativeThreadId );
    }

    public static void startRunningThread() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                printThread();
                int n = 1;
                while (true) {
                    n++;
                    n--;
                }
            }
        };
        Thread thread = new Thread(runnable);
        thread.setName("PollingSafepoint");
        thread.start();

    }



    public static void main(String[] args) throws Exception {
        startRunningThread();

        printThread();
        long processID = ProcessHandle.current().pid();
        System.out.println("PID: " + processID);

        Object o = new TestClass();

        for (int i = 1; ; i++) {

            System.out.println(i + ": Please use jhsdb to get addr object: scanoops 0xXXXXXX 0x0XXXXXX org.openjdk.jol.samples.TestClass");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String s = reader.readLine();
            if( s == null ) {
                break;
            }

            // make garbage
            byte[] bs = new byte[1024*1024*5];//Mb

            // System.out.println("GC run. :" + i);
        }

        o.toString();
    }
}

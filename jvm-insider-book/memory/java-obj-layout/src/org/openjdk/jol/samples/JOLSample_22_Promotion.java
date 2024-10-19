package org.openjdk.jol.samples;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 bash -c 'echo $$ > /tmp/jvm-insider.pid && exec setarch $(uname -m) --addr-no-randomize /home/labile/opensource/jdk/build/linux-x86_64-server-slowdebug-hsdis/jdk/bin/java -XX:+AlwaysPreTouch  -Xms100m -Xmx100m -XX:MaxTenuringThreshold=5 -server -XX:+UseSerialGC  -XX:-UseCompressedOops -XX:+UnlockDiagnosticVMOptions "-Xlog:gc*=debug::tid" -Xlog:safepoint=debug::tid -cp /home/labile/pub-diy/jvm-insider-book/memory/java-obj-layout/out/production/java-obj-layout org.openjdk.jol.samples.JOLSample_22_Promotion'

 sudo /home/labile/opensource/jdk/build/linux-x86_64-server-slowdebug-hsdis/jdk/bin/jhsdb clhsdb --pid $(cat /tmp/jvm-insider.pid)


 universe
 Heap Parameters:
Gen 0:   eden [0x00007fffdac00000,0x00007fffdb406a10,0x00007fffdc6b0000) space capacity = 27983872, 30.07360811255855 used
  from [0x00007fffdc6b0000,0x00007fffdc6b0000,0x00007fffdca00000) space capacity = 3473408, 0.0 used
  to   [0x00007fffdca00000,0x00007fffdca00000,0x00007fffdcd50000) space capacity = 3473408, 0.0 usedInvocations: 0

Gen 1:   old  [0x00007fffdcd50000,0x00007fffdcd50000,0x00007fffe1000000) space capacity = 69926912, 0.0 usedInvocations: 0

 scanoops 0x00007fffdac00000 0x00007fffdcd50000 org.openjdk.jol.samples.TestClass
 0x00007fffdb33edc0 org/openjdk/jol/samples/TestClass

 inspect 0x00007fffdb33edc0
 mem 0x00007fffdb33edc0/2
0x00007fffdb33edc0: 0x0000000000000001 = binary:age:0000 0 01

 detach
 ...
 reattach


hsdb> scanoops 0x00007fffdac00000 0x00007fffdcd50000 org.openjdk.jol.samples.TestClass
0x00007fffdca40180 org/openjdk/jol/samples/TestClass = Young to area

inspect 0x00007fffdca40180
mem 0x00007fffdca40180/2
0x00007fffdca40180: 0x0000000000000009 = binary:age:0001 0 01


sudo gdb --init-eval-command="handle SIGSEGV noprint nostop" --pid $(cat /tmp/jvm-insider.pid)

p ((oopDesc*)0x00007fffdca40180)->_mark._value

 */
public class JOLSample_22_Promotion {


    public static void main(String[] args) throws IOException {
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
```bash
cd /home/labile/opensource/jdk/jvm-insider/simple-object
/home/labile/opensource/jdk/build/linux-x86_64-server-slowdebug-hsdis/jdk/bin/javac ./*.java

> https://wiki.openjdk.org/display/HotSpot/PrintAssembly
> https://jpbempel.github.io/2015/12/30/printassembly-output-explained.html
> https://blogs.oracle.com/javamagazine/post/java-hotspot-hsdis-disassembler


cd /home/labile/opensource/jdk/jvm-insider/simple-object
rm ./round3/mylogfile.log
rm ./round3/java.out
setarch `uname -m` --addr-no-randomize /home/labile/opensource/jdk/build/linux-x86_64-server-slowdebug-hsdis/jdk/bin/java -server -XX:+UseSerialGC -XX:+PreserveFramePointer -Xcomp -XX:-TieredCompilation -XX:-BackgroundCompilation -XX:-UseCompressedOops -XX:+UnlockDiagnosticVMOptions -XX:+PrintAssembly -XX:PrintAssemblyOptions=intel -XX:CompileCommand=dontinline -Xlog:class+load=info -XX:+LogCompilation -XX:LogFile=./round3/mylogfile.log -XX:+DebugNonSafepoints -XX:+PrintInterpreter  -cp . SimpleObj | tee ./round3/java.out


513808


pmap -X 513808 | tee /home/labile/opensource/jdk/jvm-insider/simple-object/round3/pmap.txt
pmap -XX 513808 | tee /home/labile/opensource/jdk/jvm-insider/simple-object/round3/pmapXX.txt
ps  -T -p 513808 | tee /home/labile/opensource/jdk/jvm-insider/simple-object/round3/threads.txt

cd /home/labile/opensource/jdk/jvm-insider/simple-object/round3
sudo gdb -p 513808
(gdb) gcore /home/labile/opensource/jdk/jvm-insider/simple-object/round3/core.core



/home/labile/opensource/jdk/build/linux-x86_64-server-slowdebug-hsdis/jdk/bin/jhsdb hsdb --core /home/labile/opensource/jdk/jvm-insider/simple-object/round3/core.core --exe /home/labile/opensource/jdk/build/linux-x86_64-server-slowdebug-hsdis/jdk/bin/java 

gdb /home/labile/opensource/jdk/build/linux-x86_64-server-slowdebug-hsdis/jdk/bin/java /home/labile/opensource/jdk/jvm-insider/simple-object/round3/core.core

```

```
java -XX:+PrintAssembly -Xcomp -XX:CompileCommand=dontinline,*Bar.sum -XX:Compile-Command=compileonly,*Bar.sum test.Bar
```
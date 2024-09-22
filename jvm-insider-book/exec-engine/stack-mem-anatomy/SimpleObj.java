public class SimpleObj {

    public static TestStaticObjA testStaticObj = new TestStaticObjA();

    public static void main(String[] args) throws InterruptedException {
        TestObjA testObjA = new TestObjA();
        System.out.println("Hello SimpleObj: " + ProcessHandle.current().pid());
        Thread.sleep(1000*1000*1000);
    }
}
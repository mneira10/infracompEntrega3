import java.util.concurrent.CyclicBarrier;

public class tryoutSS {

    public static void main(String[] args) {
        CyclicBarrier cb = new CyclicBarrier(2);

        for (int i = 0 ; i<2 ; i++){
            System.out.println("entra al for " + i);
     //       new Thread(new ClienteSS(1,1,1,cb)).start();
//            w.start();


        }
        System.out.println("main acaba");
    }
}

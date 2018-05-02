//import java.util.concurrent.CyclicBarrier;
//
//public class tryout {
//
//    public static void main(String[] args)  {
//
//        int[] nThreads = {1,2,8};
//        int[] cargas = {400,200,80};
//        int[] tiempos = {20,40,100};
//
//
//        for( int hilo : nThreads) {
//
//            CyclicBarrier cbM = new CyclicBarrier(1);
//
//            for(int k = 0 ; k <hilo ; k++) {
//
//
//                    CyclicBarrier cb = new CyclicBarrier(2);
//
//
//                    Thread th = new Thread(new Cliente(1, 1, 1, cb));
//                    th.start();
//
//
//                }
//            }
//
//
//            cbM.await();
//        }
//
//
//        for (int i = 0 ; i<2 ; i++){
//            CyclicBarrier cb = new CyclicBarrier(2);
//
////            System.out.println("entra al for " + i);
//            new Thread(new Cliente(1,1,1,cb)).start();
////            w.start();
//
//
//        }
//        System.out.println("main acaba");
//    }
//}

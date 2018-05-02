import uniandes.gload.core.LoadGenerator;
import uniandes.gload.core.Task;

public class MainSS {
    public static void main(String[] args) {

        int[] nThreads = {1,2,8};
        int[] cargas = {400,200,80};
        int[] tiempos = {20,40,100};


//        for(int it =0; it<10; it++){
        for( int hilo : nThreads){
            for(int i = 0 ; i<1 ; i++){

                Task task = new ClientTaskSS(hilo,cargas[i],tiempos[i]);
                System.out.println("Probando " + hilo + " threads con carga " + cargas[i] + " y tiempo " + tiempos[i] + ", iteración: " );//+ it);
                /*
                De la documentacion de gload:
                constructor: LoadGenerator(String nameP, int executorsNumberP, int loadUnitsP, Task unitP, long timeGapP)
                 */
                LoadGenerator generador = new LoadGenerator("Test ClienteSS ServidorSS",
                        hilo,
                        cargas[i],
                        task,
                        tiempos[i]);
                generador.generate();



            }
        }
//        }
//
//        Task task = new ClientTaskSS(1,1,0);
//        System.out.println("Probando " + 1 + " threads con carga " + 1 + " y tiempo " + 0);
//                /*
//                De la documentacion de gload:
//                constructor: LoadGenerator(String nameP, int executorsNumberP, int loadUnitsP, Task unitP, long timeGapP)
//                 */
//        LoadGenerator generador = new LoadGenerator("Test ClienteSS ServidorSS",
//                1,
//                1,
//                task,
//                0);
//        generador.generate();
//
//
//        System.out.println("jhjh");

    }
}

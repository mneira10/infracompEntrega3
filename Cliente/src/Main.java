import uniandes.gload.core.LoadGenerator;
import uniandes.gload.core.Task;

public class Main {
    public static void main(String[] args) {

        int[] nThreads = {1,2,8};
        int[] cargas = {400,200,80};
        int[] tiempos = {20,40,100};


//        for(int it =0; it<10; it++){
        for( int hilo : nThreads){
            for(int i = 0 ; i<cargas.length ; i++){

                Task task = new ClientTask(hilo,cargas[i],tiempos[i]);
                System.out.println("Probando " + hilo + " threads con carga " + cargas[i] + " y tiempo " + tiempos[i] + ", iteración: " );//+ it);
                /*
                De la documentacion de gload:
                constructor: LoadGenerator(String nameP, int executorsNumberP, int loadUnitsP, Task unitP, long timeGapP)
                 */
                LoadGenerator generador = new LoadGenerator("Test Cliente Servidor",
                        hilo,
                        cargas[i],
                        task,
                        tiempos[i]);
                generador.generate();



            }
        }
//        }
//
//        Task task = new ClientTask(1,1,0);
//        System.out.println("Probando " + 1 + " threads con carga " + 1 + " y tiempo " + 0);
//                /*
//                De la documentacion de gload:
//                constructor: LoadGenerator(String nameP, int executorsNumberP, int loadUnitsP, Task unitP, long timeGapP)
//                 */
//        LoadGenerator generador = new LoadGenerator("Test Cliente Servidor",
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

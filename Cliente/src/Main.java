import uniandes.gload.core.LoadGenerator;
import uniandes.gload.core.Task;

public class Main {
    public static void main(String[] args) {

        int[] nThreads = {1,2,8};
        int[] cargas = {80,200,400};
        int[] tiempos = {20,40,100};

        for( int hilo : nThreads){
            for(int i = 0 ; i<cargas.length ; i++){

                Task task = new ClientTask(hilo,cargas[i],tiempos[i]);
                System.out.println("Probando " + hilo + " threads con carga " + cargas[i] + " y tiempo " + tiempos[i]);
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
    }
}

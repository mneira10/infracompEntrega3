import uniandes.gload.core.Task;

public class ClientTaskSS extends Task{

    int nThreads;
    int carga;
    int iteracion;

    public ClientTaskSS(int nThreads, int carga, int iteracion) {
        this.nThreads = nThreads;
        this.carga = carga;
        this.iteracion = iteracion;
    }

    @Override
    public void execute() {
        new Thread(new ClienteSS(nThreads,carga,iteracion)).run();
        System.out.println("lalalala");

    }

    @Override
    public void fail() {
        System.out.println(Task.MENSAJE_FAIL);
    }

    @Override
    public void success() {
        System.out.println(Task.OK_MESSAGE);
    }
}

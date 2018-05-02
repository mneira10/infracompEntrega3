import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.xml.transform.sax.SAXSource;
//import java.lang.management.ManagementFactory;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;

public class MonitorTareasSS {
    public static void main(String[] args) throws Exception {

        for( int  i = 0 ; i < 100 ; i++){
//            System.out.println("load1 " +  getProcessCpuLoad());
//            System.out.println("load2 " + getProcessCpuLoad2());
//            System.out.println("load3 " + getProcessCpuLoad3());
            System.out.println("load4 " + getProcessCpuLoad4());
        }
    }

    public static double getProcessCpuLoad() throws Exception {

        MBeanServer mbs    = ManagementFactory.getPlatformMBeanServer();
        ObjectName name    = ObjectName.getInstance("java.lang:type=OperatingSystem");
        AttributeList list = mbs.getAttributes(name, new String[]{ "ProcessCpuLoad" });

        if (list.isEmpty())     return Double.NaN;

        Attribute att = (Attribute)list.get(0);
        Double value  = (Double)att.getValue();

        // usually takes a couple of seconds before we get real values
        if (value == -1.0)      return Double.NaN;
        // returns a percentage value with 1 decimal point precision
        return ((int)(value * 1000) / 10.0);
    }
    public static double getProcessCpuLoad2(){
        com.sun.management.OperatingSystemMXBean os  = (com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        return os.getSystemLoadAverage();
    }
    public static double getProcessCpuLoad3(){
        com.sun.management.OperatingSystemMXBean os  = (com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        return os.getProcessCpuLoad();
    }

    public static double getProcessCpuLoad4() throws IOException {


        ProcessBuilder pb = new ProcessBuilder("/bin/bash", "-c", "grep 'cpu ' /proc/stat | awk '{usage=($2+$4)*100/($2+$4+$5)} END {print usage}'");

        Process p = pb.start();




        try {
            p.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try(BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()))){
            return Double.parseDouble(br.readLine());
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return -1;
    }
}


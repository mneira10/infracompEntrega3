

import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509v1CertificateBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.math.BigInteger;
import java.net.Socket;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.Random;


public class Main {


    static KeyPair llaves;
    static PublicKey serverKey;
    static SecretKey simmetricKey;
    static String sim;
    static String hmacPost;


    public static void main(String[] args) throws Exception {

        Socket s = null;
        PrintWriter escritor = null;
        BufferedReader lector = null;

        String ip = "localhost";
        int puerto = 8084;

        //concetarse a ip:ip en el puerto:puerto
        try {
            s = new Socket(ip, puerto);
            escritor = new PrintWriter(s.getOutputStream(), true);
            lector = new BufferedReader(new InputStreamReader(
                    s.getInputStream()));

        }
        //en caso de no poder conectarse
        catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
            System.exit(1);
        }

        boolean valido =false;
        while(!valido) {
            System.out.println("Especifique qué algoritmos de cifrado desea:");
            System.out.println("Cifrado simétrico: (Escriba: AES ó Blowfish)");
            sim = (new BufferedReader(new InputStreamReader(System.in))).readLine();
            System.out.println("HMAC: (Escriba: MD5 ó SHA1 ó SHA256)");
            hmacPost = (new BufferedReader(new InputStreamReader(System.in))).readLine();
            if((sim.equals("AES") || sim.equals("Blowfish")) && (hmacPost.equals("MD5") ||
                    hmacPost.equals("SHA1") || hmacPost.equals("SHA256") )){
                valido = true;
            }else System.out.println("Algoritmos inválidos");

        }

//        System.out.println("Postfix: " + hmacPost.substring(4));
        //ejecucion del protocolo de comunicacion
        etapa1(lector, escritor);
        //mandar el certificado
        etapa2(lector,escritor,s);
        //etapa3
        etapa3(lector,escritor,s);
        //etapa4
        etapa4(lector,escritor,s);




        escritor.close();
        lector.close();

    }



    public static void imprimirServerConsola(BufferedReader lector) throws Exception {
        String fromServer;
        if ((fromServer = lector.readLine()) != null) {
            System.out.println("Servidor: " + fromServer);
        }
    }

    public static void etapa1(BufferedReader lector, PrintWriter escritor) throws Exception     {
        System.out.println("INICIO DE ETAPA1");
        escritor.println("HOLA");

        imprimirServerConsola(lector);

        escritor.println("ALGORITMOS:" + sim.toUpperCase() + ":RSA:HMAC"  + hmacPost);

        imprimirServerConsola(lector);

        System.out.println("FIN DE ETAPA1");
    }

    public static void etapa2(BufferedReader lector, PrintWriter escritor,Socket s) throws Exception {

        System.out.println("INICIO DE ETAPA2");
        escritor.println("CERTCLNT");

        Security.addProvider(new BouncyCastleProvider());
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(1024);
        llaves = keyGen.generateKeyPair();
        java.security.cert.X509Certificate cert = certificado(llaves);

        System.out.println(cert.toString());
        byte[] mybyte = cert.getEncoded();
        OutputStream outStream = s.getOutputStream();
        outStream.write(mybyte);
        outStream.flush();


        imprimirServerConsola(lector);

        System.out.println("FIN DE ETAPA2");
    }

    public static void etapa3(BufferedReader lector, PrintWriter escritor,Socket s) throws Exception {
        System.out.println("INICIO DE ETAPA3");
        imprimirServerConsola(lector);


        CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
        byte[] certificadoClienteBytes = new byte[5000];
        s.getInputStream().read(certificadoClienteBytes);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(certificadoClienteBytes);
        X509Certificate certificadoServer = null;
        try {
            certificadoServer = (X509Certificate)certFactory.generateCertificate(inputStream);
        } catch (CertificateException var75) {
            var75.printStackTrace();
        }

        serverKey = certificadoServer.getPublicKey();
        System.out.println("Server public key:");
        System.out.println(serverKey);

        escritor.println("ESTADO:OK");
        System.out.println("FIN DE ETAPA3");
    }

    public static void etapa4(BufferedReader lector, PrintWriter escritor,Socket s) throws Exception{

        String etapa41 = lector.readLine().split(":")[1];


        byte[] etapa411 = DatatypeConverter.parseHexBinary(etapa41);



        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, llaves.getPrivate());
        byte [] clearText = cipher.doFinal(etapa411);



        simmetricKey = new SecretKeySpec( clearText, 0, clearText.length, sim );

        String coordenadas = "41 24.2028, 2 10.441";

        //Cifrado simetrico

        //Mandar coordenadas con cifrado asimetrico  - ACT1
        byte [] cT = coordenadas.getBytes();



        Cipher cypher = Cipher.getInstance(sim);
        cypher.init(Cipher.ENCRYPT_MODE, simmetricKey);

        byte[] cipheredBytes    = cypher.doFinal(cT);

        String sToSend  = DatatypeConverter.printHexBinary(cipheredBytes);

        sToSend = sToSend.toUpperCase();

        sToSend = "ACT1:"+sToSend;
        escritor.println(sToSend);

        //mandar hash de la posicion  - ACT2


        //hash
        Mac mac = Mac.getInstance( "Hmac" + hmacPost);
        mac.init( simmetricKey );
        byte[] semiFinal = mac.doFinal( cT );

        Cipher cifradoFinal = Cipher.getInstance("RSA");
        cifradoFinal.init(Cipher.ENCRYPT_MODE, serverKey);
        byte [] finalText = cifradoFinal.doFinal(semiFinal);

        sToSend = "ACT2:" + DatatypeConverter.printHexBinary(finalText).toUpperCase();
        escritor.println(sToSend);



        imprimirServerConsola(lector);


    }

    public static X509Certificate certificado(KeyPair keyPair) throws Exception {

        long now = System.currentTimeMillis();
        Date comienzo = new Date(now-1000*60*60*24*365);
        System.out.println(comienzo.toString());
        Date finals = new Date(now + 365 * 24 * 60 * 60 * 1000);
        System.out.println(finals.toString());

        X500NameBuilder nm = new X500NameBuilder();
        nm.addRDN(BCStyle.C, "Colombia");
        nm.addRDN(BCStyle.ST, "Bogota");
        nm.addRDN(BCStyle.O, "Universidad de los Andes");
        org.bouncycastle.asn1.x500.X500Name x500name = nm.build();

        BigInteger certSerialNum = new BigInteger(128, new Random());

        ContentSigner contentSigner = new JcaContentSignerBuilder("SHA256WithRSAEncryption").build(llaves.getPrivate());


        JcaX509v1CertificateBuilder certBuilder = new JcaX509v1CertificateBuilder(
                x500name,
                certSerialNum,
                comienzo,
                finals,
                x500name,
                llaves.getPublic()
        );

        return new JcaX509CertificateConverter().setProvider("BC").getCertificate(certBuilder.build(contentSigner));
    }



}
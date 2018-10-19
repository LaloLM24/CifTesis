import java.math.BigInteger;

public class main
{


    public  static void  main(String [] args)
    {
        Cifrar c = new Cifrar("Esta es mi vida relatada en 2000 minutos", new BigInteger("65535"));
        String cifrado = c.Controlador();
        System.out.println(cifrado);
    }
}

import java.math.BigInteger;

public class Cifrar {
    private String Mensaje;
    private BigInteger Clave;


    public Cifrar(String Mensaje, BigInteger Clave) {
        this.Mensaje = Mensaje;
        this.Clave = Clave;
    }


    public String DividirMensaje(String Mens) {
        String Bloque = "";
        if(Mens.length() < 16)
        {
            Mens = Relleno(Mens);
            //System.out.println(Mens);
        }
        Bloque = Mens.substring(0, 16);
        return Bloque;
    }

    public char[][] MensajeBloques(String MensajeDividido) {
        int contador = 0;
        char[][] TextoEnBloques = new char[4][4];

        for (int ContA = 0; ContA < 4; ContA++) {
            for (int CountB = 0; CountB < 4; CountB++) {
                TextoEnBloques[ContA][CountB] = MensajeDividido.charAt(contador);
                contador++;
            }
        }
        return TextoEnBloques;
    }

    public BigInteger[][] PassBloques() {
        String LlaveHex = this.Clave.toString(16);
        BigInteger[][] ContBloques = new BigInteger[4][4];
        boolean comp = false;
        int c1 = 0;
        int c2 = 2;

        do {

            if (LlaveHex.length() < 32) {
                char temp = LlaveHex.charAt(LlaveHex.length() - 1);
                LlaveHex = LlaveHex + Character.toString(temp);
            } else comp = true;


        } while (comp == false);

        //System.out.println(LlaveHex);

        for (int ContA = 0; ContA < 4; ContA++) {
            for (int ContB = 0; ContB < 4; ContB++) {
                ContBloques[ContA][ContB] = new BigInteger(LlaveHex.substring(c1, c2), 16);
                c1 = c1 + 2;
                c2 = c2 + 2;
                //System.out.print(" " + ContBloques[ContA][ContB]);
            }
            //System.out.print("\n");
        }

        return ContBloques;
    }


    char[][] Transposición(char[][] ArregloTransponer) {
        int ContadorInverso = 3;
        char[][] ArregloTranspuesto = new char[4][4];

        for (int ContA = 0; ContA < 4; ContA++) {
            for (int ContB = 0; ContB < 4; ContB++) {
                ArregloTranspuesto[ContA][ContB] = ArregloTransponer[ContA][ContadorInverso];
                ContadorInverso--;
            }
            ContadorInverso = 3;
        }
        return ArregloTranspuesto;
    }


    BigInteger[][] XOR(char[][] MensajeTranspuesto, BigInteger[][] Llave) {
        BigInteger ArrXOR[][] = new BigInteger[4][4];
        for (int CA = 0; CA < 4; CA++) {
            for (int CB = 0; CB < 4; CB++) {
               ArrXOR[CA][CB] = Llave[CA][CB].xor(BigInteger.valueOf(MensajeTranspuesto[CA][CB]));
            }
        }
        return ArrXOR;
    }

    String MensajeCifrado(BigInteger[][] MenXOR)
    {
        String Mensaje = "";
        for(int ContA = 0; ContA < 4; ContA ++)
        {
            for(int ContB = 0; ContB < 4; ContB ++)
            {
              //Mensaje = Mensaje +  MenXOR[ContA][ContB].toString(16) + " ";
                Mensaje = Mensaje +  MenXOR[ContA][ContB].toString(16);
            }
        }
        return Mensaje;
    }

    String Relleno(String Mens)
    {
        char temp = Mens.charAt(Mens.length()-1);
        while(Mens.length()<16)
        {
            Mens = Mens + temp;
        }
        return Mens;
    }

    String Controlador()
    {
        String MensajeFinal = "";

        //Tratamiento de mensaje
        String Men =this.Mensaje;
        String MensajeD;
        char [][] MensajeEnBloques;
        char [][] MensajeT;

        //Tratamiento de Llave
        BigInteger [][] KeyBloque;

        //Cifrado
        BigInteger [][] MensXOR;

        //Mensaje Final
        String MensajeF;

        while(Men.length() > 0)
        {
            //Tratamiento de mensaje
            MensajeD = DividirMensaje(Men);

            if(Men.length() > 16)
            {
                Men = Men.substring(17);
            }
            else
            {
                Men = "";
            }

            MensajeEnBloques = MensajeBloques(MensajeD);
            MensajeT = Transposición(MensajeEnBloques);

            //Tratamiento de Pass
            KeyBloque = PassBloques();

            //Cifrado de mensaje
            MensXOR = XOR(MensajeT, KeyBloque);

            MensajeF = MensajeCifrado(MensXOR);

            MensajeFinal = MensajeFinal + MensajeF;
            //System.out.println(MensajeFinal);
        }
        return MensajeFinal;
    }

    public void setMensaje(String mensaje) {
        Mensaje = mensaje;
    }

    public BigInteger getClave()
    {
        return Clave;
    }

    public String getMensaje()
    {
        return Mensaje;
    }


}

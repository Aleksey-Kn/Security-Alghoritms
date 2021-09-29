package cipher;

import java.util.Random;

public class Vernam {
    private final Random random;

    public Vernam(){
        random = new Random();
    }

    public byte[] encoding(byte m){
        byte[] result = new byte[2];
        result[0] = (byte) random.nextInt();
        result[1] = (byte) (result[0] ^ m);
        return result;
    }

    public static byte decoding(byte[] code){
        return (byte) (code[0] ^ code[1]);
    }
}

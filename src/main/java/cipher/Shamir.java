package cipher;

import baseAlghoritms.Environ;
import baseAlghoritms.SpecialMath;

import java.math.BigInteger;
import java.util.Random;

public class Shamir {
    private int c;
    private int d;
    private final int p;

    public Shamir(){
        Random random = new Random();
        p = Environ.getInstance().getP();
        int[] t;
        do {
            do {
                c = Math.abs(random.nextInt());
                t = SpecialMath.nod(c, p - 1);
            } while (t[0] != 1);
            d = t[1];
            if(d < 0)
                d = d % (p - 1) + (p - 1);
        } while (BigInteger.valueOf(c).multiply(BigInteger.valueOf(d))
                        .mod(BigInteger.valueOf(p - 1)).intValue() != 1);
    }

    public int encoding(byte message){
        int m = message + 255;
        return SpecialMath.powOnModule(m, c, p);
    }

    public int generateX2(int x1){
        return SpecialMath.powOnModule(x1, c, p);
    }

    public int generateX3(int x2){
        return SpecialMath.powOnModule(x2, d, p);
    }

    public byte decoding(int x3){
        return (byte) (SpecialMath.powOnModule(x3, d, p) - 255);
    }
}

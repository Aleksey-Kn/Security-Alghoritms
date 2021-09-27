package shifrs;

import baseAlghoritms.Environ;
import baseAlghoritms.SpecialMath;

import java.util.Random;

public class Shamir {
    private int c, d, p;

    public Shamir(){
        Random random = new Random();
        p = Environ.getInstance().getP();
        int[] t;
        do {
            do {
                c = Math.abs(random.nextInt()) % (p - 1) + 1;
                t = SpecialMath.nod(c, p - 1);
            } while (t[0] != 1);
            d = t[1];
        } while (d <= 0 || d >= p);
    }

    public int encoding(byte m){
        return SpecialMath.powOnModule(m, c, p);
    }

    public int generateX2(int x1){
        return SpecialMath.powOnModule(x1, c, p);
    }

    public int generateX3(int x2){
        return SpecialMath.powOnModule(x2, d, p);
    }

    public byte decoding(int x3){
        return (byte) SpecialMath.powOnModule(x3, d, p);
    }
}

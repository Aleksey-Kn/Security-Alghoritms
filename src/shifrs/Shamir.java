package shifrs;

import baseAlghoritms.Environ;
import baseAlghoritms.SpecialMath;

import java.util.Random;

public class Shamir {
    private int c, d;

    public Shamir(){
        Random random = new Random();
        int p = Environ.getInstance().getP();
        int[] t;
        do {
            c = random.nextInt();
            t = SpecialMath.nod(c, p - 1);
        } while (t[0] != 1);
        d = t[1];
    }


}

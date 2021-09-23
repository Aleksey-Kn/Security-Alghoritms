package shifrs;

import baseAlghoritms.Environ;
import baseAlghoritms.SpecialMath;

import java.util.Random;

public class Shamir {
    public Shamir(){
        Random random = new Random();
        int p = Environ.getInstance().getP();
        int c, d;
        int[] t;
        do {
            c = random.nextInt();
            t = SpecialMath.nod(c, p - 1);
        } while (t[0] != 1);
        d = t[1];
    }


}

import java.math.BigInteger;
import java.util.Random;
import java.util.function.UnaryOperator;

public class SpecialMath {
    public static double log2(int v) {
        return Math.log(v) / Math.log(2);
    }

    public static int powOnModule(int a, int x, int p) {
        int lim = (int)log2(x) + 1;
        long[] intermediate = new long[lim];
        BigInteger result = BigInteger.ONE;

        intermediate[0] = a % p;
        for(int i = 1; i < lim; i++){
            intermediate[i] = BigInteger.valueOf(intermediate[i - 1]).pow(2).mod(BigInteger.valueOf(p)).longValue();
        }

        for (int i = 0; x != 0; i++, x = x >> 1){
            if((x & 1) == 1)
                result = result.multiply(BigInteger.valueOf(intermediate[i]));
        }
        return result.mod(BigInteger.valueOf(p)).intValue();
    }

    public static long nod(long a, long b){
        if(b > a){
            long t = a;
            a = b;
            b = t;
        }
        long q;

        long[] u = new long[3];
        long[] v = new long[3];
        long[] t = new long[3];

        u[0] = a;
        u[1] = 1;
        v[0] = b;
        v[2] = 1;

        do {
            q = u[0] / v[0];

            t[0] = u[0] % v[0];
            t[1] = u[1] - q * v[1];
            t[2] = u[2] - q * v[2];

            u = v.clone();
            v = t.clone();
        } while (t[0] != 0);

        return u[0];
    }

    public static int diffihellman(Coder z){
        Random random = new Random();
        int x = Math.abs(random.nextInt()) % (Environ.getInstance().getP() - 2) + 1;
        while (powOnModule(x, Environ.getInstance().getQ(), Environ.getInstance().getP()) == 1)
            x = Math.abs(random.nextInt()) % (Environ.getInstance().getP() - 2) + 1;

        int y = powOnModule(Environ.getInstance().getG(), x, Environ.getInstance().getP());
        z.setX(x);
        return y;
    }
}

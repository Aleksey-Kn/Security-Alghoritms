package cipher;

import baseAlghoritms.Environ;
import baseAlghoritms.SpecialMath;

import java.util.Random;

public class ElGamal {
    private final int p, g, c, publicKey;
    private final Random random;
    private int otherKey;

    public ElGamal() {
        p = Environ.getInstance().getP();
        g = Environ.getInstance().getG();
        random = new Random();
        c = Math.abs(random.nextInt()) % (p - 3) + 2;
        publicKey = SpecialMath.powOnModule(g, c, p);
    }

    public int[] encoding(byte message) {
        int m = message + 255;
        int k = Math.abs(random.nextInt()) % (p - 2) + 1;
        int r = SpecialMath.powOnModule(g, k, p);
        int e = (int) ((long)(m % p) * SpecialMath.powOnModule(otherKey, k, p) % p);
        return new int[]{r, e};
    }

    public byte decoding(int[] data) {
        return (byte) ((long)(data[1] % p) * SpecialMath.powOnModule(data[0], p - 1 - c, p) % p - 255);
    }

    public int getPublicKey() {
        return publicKey;
    }

    public void setOtherKey(int otherKey) {
        this.otherKey = otherKey;
    }
}

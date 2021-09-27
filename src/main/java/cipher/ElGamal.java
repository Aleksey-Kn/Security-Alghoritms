package cipher;

import baseAlghoritms.Environ;
import baseAlghoritms.SpecialMath;

import java.math.BigInteger;
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

    public int[] encoding(byte m) {
        int k = Math.abs(random.nextInt()) % (p - 2) + 1;
        int r = SpecialMath.powOnModule(g, k, p);
        int e = BigInteger.valueOf(m % p)
                .multiply(BigInteger.valueOf(SpecialMath.powOnModule(otherKey, k, p)))
                .mod(BigInteger.valueOf(p)).intValue();
        return new int[]{r, e};
    }

    public byte decoding(int[] data) {
        return BigInteger.valueOf(data[1] % p)
                .multiply(BigInteger.valueOf(SpecialMath.powOnModule(data[0], p - 1 - c, p)))
                .mod(BigInteger.valueOf(p)).byteValue();
    }

    public int getPublicKey() {
        return publicKey;
    }

    public void setOtherKey(int otherKey) {
        this.otherKey = otherKey;
    }
}

package cipher;

import baseAlghoritms.Environ;
import baseAlghoritms.SpecialMath;
import hashFunction.HashFile;
import org.w3c.dom.ls.LSOutput;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Random;

public class ElGamal {
    private final int c, publicKey;
    private final Random random;
    private int p, g, otherKey;

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
        int e = (int) ((long) (m % p) * SpecialMath.powOnModule(otherKey, k, p) % p);
        return new int[]{r, e};
    }

    public byte decoding(int[] data) {
        return (byte) ((long) (data[1] % p) * SpecialMath.powOnModule(data[0], p - 1 - c, p) % p - 255);
    }

    public int[][] encodingHash(HashFile hashFile) {
        int k, r, u, s, inversion;
        int[][] result = new int[hashFile.getData().length][2];
        int[] arr;
        for (int i = 0; i < result.length; i++) {
            do {
                k = Math.abs(random.nextInt()) % (p - 2) + 1;
                arr = SpecialMath.nod(p - 1, k);
                inversion = (arr[2] > 0 ? arr[2] : arr[2] % (p - 1) + (p - 1));
            } while (arr[0] != 1);
            r = SpecialMath.powOnModule(g, k, p);
            u = (int) (hashFile.getData()[i] - (long) c * r % (p - 1));
            if (u < 0)
                u = u % (p - 1) + (p - 1);
            s = (int) ((long) inversion * u % (p - 1));
            result[i][0] = r;
            result[i][1] = s;
        }
        return result;
    }

    public int[] decodingHash(int[][] encoding) {
        int[] result = new int[encoding.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = (int) ((long) SpecialMath.powOnModule(otherKey, encoding[i][0], p)
                    * SpecialMath.powOnModule(encoding[i][0], encoding[i][1], p) % p);
        }
        return result;
    }

    public int getPublicKey() {
        return publicKey;
    }

    public void setOtherKey(int otherKey) {
        this.otherKey = otherKey;
    }

    public void setG(int g) {
        this.g = g;
    }

    public void setP(int p) {
        this.p = p;
    }

    public int getG() {
        return g;
    }

    public int getP() {
        return p;
    }
}

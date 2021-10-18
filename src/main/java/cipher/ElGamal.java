package cipher;

import baseAlghoritms.Environ;
import baseAlghoritms.SpecialMath;
import hashFunction.HashFile;

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
                arr = SpecialMath.nod(k, p - 1);
            } while (arr[0] == 1);
            inversion = (arr[1] > 0 ? arr[1] : arr[1] % (p - 1) + (p - 1));
            r = SpecialMath.powOnModule(g, k, p);
            u = (int)((hashFile.getData()[i] - (long)c * r) % (p - 1));
            if(u < 0)
                u += p - 1;
            s = inversion * u % (p - 1);
            result[i][0] = r;
            result[i][1] = s;
        }
        return result;
    }

    public int[] decodingHash(int[][] encoding){
        int[] result = new int[encoding.length];
        for(int i = 0; i < result.length; i++){
            result[i] = (int) ((Math.pow(otherKey, encoding[i][0]) * Math.pow(encoding[i][0], encoding[i][1])) % p);
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

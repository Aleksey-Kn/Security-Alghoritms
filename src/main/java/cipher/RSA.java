package cipher;

import baseAlghoritms.PrimeNumber;
import baseAlghoritms.SpecialMath;

import java.util.Random;

public class RSA {
    private int otherKey, publicKey, otherN, c;
    private final int n;

    public RSA(){
        int p, q, fi;
        Random random = new Random();
        int[] nodResult;
        p = PrimeNumber.getInstance().randomPrime(23, 10000);
        q = PrimeNumber.getInstance().randomPrime(23, 10000);
        n = q * p;
        fi = (p - 1) * (q - 1);
        do {
            publicKey = Math.abs(random.nextInt()) % (n - 1) + 1;
            nodResult = SpecialMath.nod(fi, publicKey);
            c = (nodResult[1] > 0? nodResult[1]: nodResult[1] % publicKey + publicKey);
        } while (nodResult[0] != 1 || ((long) c * publicKey) % fi != 1);
    }

    public int encoding(byte message){
        int m = message + 255;
        return SpecialMath.powOnModule(m, otherKey, otherN);
    }

    public byte decoding(int code){
        return (byte) (SpecialMath.powOnModule(code, c, n) - 255);
    }

    public int getPublicKey() {
        return publicKey;
    }

    public void setOtherKey(int otherKey) {
        this.otherKey = otherKey;
    }

    public int getN() {
        return n;
    }

    public void setOtherN(int otherN) {
        this.otherN = otherN;
    }
}
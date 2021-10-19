package cipher;

import hashFunction.HashFile;

import java.math.BigInteger;
import java.util.Random;

public class Gost {
    private BigInteger p;
    private BigInteger q;
    private BigInteger x;
    private BigInteger y;
    private BigInteger a;
    private Random random;

    public Gost(){
        random = new Random();
        q = BigInteger.probablePrime(256, random);
        BigInteger b;
        do {
            b = new BigInteger(768, random);
            p = q.multiply(b).add(BigInteger.ONE);
        } while (p.bitLength() != 1024 || !p.isProbablePrime(7));
        do{
            a = new BigInteger(Math.abs(random.nextInt()) % 1022 + 1, random).modPow(b, p);
        } while (!a.modPow(q, p).equals(BigInteger.ONE));
        x = new BigInteger(Math.abs(random.nextInt()) % 255 + 1, random);
        y = a.modPow(x, p);
    }

    public Gost(BigInteger otherA, BigInteger otherQ, BigInteger otherP, BigInteger otherY){
        p = otherP;
        q = otherQ;
        y = otherY;
        a = otherA;
    }

    public String[][] encoding(HashFile hashFile){
        BigInteger k, r, s;
        String[][] result = new String[hashFile.getData().length][2];
        for (int i = 0; i < result.length; i++) {
            do {
                k = new BigInteger(Math.abs(random.nextInt()) % 255 + 1, random);
                r = a.modPow(k, p).mod(q);
                s = k.multiply(BigInteger.valueOf(hashFile.getData()[i])).add(x.multiply(r)).mod(q);
            } while (r.equals(BigInteger.ZERO) || k.equals(BigInteger.ZERO));
            result[i][0] = r.toString();
            result[i][1] = s.toString();
        }
        return result;
    }

    public boolean validSignature(BigInteger[][] encoding, HashFile hashFile){
        BigInteger u1, u2, v, inversion;
        if(encoding.length == 0)
            return false;
        for(int i = 0; i < encoding.length; i++){
            if(encoding[i][0].equals(BigInteger.ZERO)
                    || encoding[i][1].equals(BigInteger.ZERO)
                    || encoding[i][1].compareTo(q) > 0){
                return false;
            }
            inversion = BigInteger.valueOf(hashFile.getData()[i]).modInverse(q);
            u1 = encoding[i][1].multiply(inversion).mod(q);
            u2 = encoding[i][0].multiply(BigInteger.valueOf(-1)).multiply(inversion).mod(q);
            v = a.modPow(u1, p).multiply(y.modPow(u2, p)).mod(p).mod(q);
            if(!v.equals(encoding[i][0]))
                return false;
        }
        return true;
    }

    public BigInteger getP() {
        return p;
    }

    public BigInteger getQ() {
        return q;
    }

    public BigInteger getY() {
        return y;
    }

    public BigInteger getA() {
        return a;
    }
}

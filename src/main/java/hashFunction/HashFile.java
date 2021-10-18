package hashFunction;

import baseAlghoritms.SpecialMath;
import org.apache.commons.lang3.ArrayUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class HashFile{
    private final byte[] data;

    public HashFile(File file) throws IOException, NoSuchAlgorithmException {
        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] temp = new byte[fileInputStream.available()];
        fileInputStream.read(temp);
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        data = digest.digest(temp);
        for(int i = 0; i < data.length; i++){
            data[i] = (byte) Math.abs(data[i]);
        }
    }

    public byte[] getData() {
        return data;
    }

    public boolean validHash(byte[] decodingData){
        return Arrays.equals(decodingData, data);
    }

    public boolean validHashElGamal(int[] decodingData, int g, int p){
        return Arrays.equals(decodingData,
                Arrays.stream(ArrayUtils.toObject(data)).mapToInt(h -> SpecialMath.powOnModule(g, h, p)).toArray());
    }
}

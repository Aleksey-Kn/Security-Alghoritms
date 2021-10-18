package hashFunction;

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
    }

    public byte[] getData() {
        return data;
    }

    public boolean validHash(byte[] hash){
        return Arrays.equals(hash, data);
    }
}

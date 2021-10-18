package hashFunction;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.function.Consumer;

public class HashFile implements Iterable<Byte>{
    private final byte[] data;
    private int nowState = 0;

    HashFile(File file) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        data = new byte[fileInputStream.available()];
        fileInputStream.read(data);
    }

    @Override
    public Iterator<Byte> iterator() {
        return new Iterator<Byte>() {
            @Override
            public boolean hasNext() {
                return nowState < data.length;
            }

            @Override
            public Byte next() {
                return data[nowState++];
            }
        };
    }

    @Override
    public void forEach(Consumer<? super Byte> action) {
        for(byte b: data){
            action.accept(b);
        }
    }
}

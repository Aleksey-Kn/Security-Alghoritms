import cipher.ElGamal;
import cipher.RSA;
import cipher.Shamir;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String fileName, decodingName;
        boolean flag = true;
        FileInputStream inputStream;
        FileOutputStream outputStream;
        while (flag) {
            System.out.println("Выберите номер алгоритма одной цифрой без дополнительных символов");
            System.out.println("0. Exit");
            System.out.println("1. Шамир");
            System.out.println("2. Эль-Гамаль");
            System.out.println("3. RSA");
            try {
                switch (Integer.parseInt(scanner.next())) {
                    case 1:
                        System.out.print("File name: ");
                        fileName = scanner.next();
                        decodingName = "Decoding" + fileName;
                        Shamir first = new Shamir();
                        Shamir second = new Shamir();
                        inputStream = new FileInputStream(fileName);
                        outputStream = new FileOutputStream(decodingName);
                        while (inputStream.available() > 0) {
                            outputStream.write(second.decoding(first.generateX3(second.generateX2(
                                    first.encoding((byte) inputStream.read())))));
                        }
                        inputStream.close();
                        outputStream.close();
                        System.out.println("Equals file: " +
                                FileUtils.contentEquals(new File(fileName), new File(decodingName)));
                        break;
                    case 2:
                        System.out.print("File name: ");
                        fileName = scanner.next();
                        decodingName = "Decoding" + fileName;
                        ElGamal fir = new ElGamal();
                        ElGamal sec = new ElGamal();
                        fir.setOtherKey(sec.getPublicKey());
                        sec.setOtherKey(fir.getPublicKey());
                        inputStream = new FileInputStream(fileName);
                        outputStream = new FileOutputStream(decodingName);
                        while (inputStream.available() > 0) {
                            outputStream.write(sec.decoding(fir.encoding((byte)inputStream.read())));
                        }
                        inputStream.close();
                        outputStream.close();
                        System.out.println("Equals file: " +
                                FileUtils.contentEquals(new File(fileName), new File(decodingName)));
                        break;
                    case 3:
                        System.out.print("File name: ");
                        fileName = scanner.next();
                        decodingName = "Decoding" + fileName;
                        RSA f = new RSA();
                        RSA s = new RSA();
                        f.setOtherN(s.getN());
                        f.setOtherKey(s.getPublicKey());
                        s.setOtherKey(f.getPublicKey());
                        s.setOtherN(f.getN());
                        inputStream = new FileInputStream(fileName);
                        outputStream = new FileOutputStream(decodingName);
                        while (inputStream.available() > 0) {
                            outputStream.write(s.decoding(f.encoding((byte)inputStream.read())));
                        }
                        inputStream.close();
                        outputStream.close();
                        System.out.println("Equals file: " +
                                FileUtils.contentEquals(new File(fileName), new File(decodingName)));
                        break;
                    case 0:
                        flag = false;
                }
            } catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
    }
}

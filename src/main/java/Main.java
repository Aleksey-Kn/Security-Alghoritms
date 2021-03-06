import cipher.ElGamal;
import cipher.RSA;
import cipher.Shamir;
import cipher.Vernam;
import hashFunction.HashFile;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String fileName, decodingName, signatureName;
        boolean flag = true;
        FileInputStream inputStream;
        FileOutputStream outputStream;
        FileWriter fileWriter;
        Scanner fileScanner;
        while (flag) {
            System.out.println("???????? ????? ????????? ????? ?????? ??? ?????????????? ????????");
            System.out.println("0. Exit");
            System.out.println("1. ?????");
            System.out.println("2. ???-??????");
            System.out.println("3. RSA");
            System.out.println("4. ??????");
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
                    case 4:
                        System.out.print("File name: ");
                        fileName = scanner.next();
                        decodingName = "Decoding" + fileName;
                        Vernam vernam = new Vernam();
                        inputStream = new FileInputStream(fileName);
                        outputStream = new FileOutputStream(decodingName);
                        while (inputStream.available() > 0) {
                            outputStream.write(Vernam.decoding(vernam.encoding((byte)inputStream.read())));
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
                e.printStackTrace();
            }
        }
    }
}

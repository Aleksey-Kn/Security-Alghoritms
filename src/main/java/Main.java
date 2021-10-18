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
            System.out.println("Выберите номер алгоритма одной цифрой без дополнительных символов");
            System.out.println("0. Exit");
            System.out.println("1. Шамир");
            System.out.println("2. Эль-Гамаль");
            System.out.println("3. RSA");
            System.out.println("4. Вернам");
            System.out.println("5. Подпись RSA");
            System.out.println("6. Проверка подписи RSA");
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
                    case 5:
                        RSA rsa = new RSA();
                        System.out.print("File name: ");
                        fileName = scanner.next();
                        System.out.print("Save signature to file: ");
                        signatureName = scanner.next();
                        fileWriter = new FileWriter(signatureName);
                        for(int i: rsa.encodingHash(new HashFile(new File(fileName)))){
                            fileWriter.write(i + "\n");
                        }
                        fileWriter.close();
                        System.out.print("Save keys user to file: ");
                        fileWriter = new FileWriter(scanner.next());
                        fileWriter.write(rsa.getPublicKey() + "\n");
                        fileWriter.write(rsa.getN() + "\n");
                        fileWriter.close();
                        break;
                    case 6:
                        RSA rsa1 = new RSA();
                        System.out.print("File name: ");
                        fileName = scanner.next();
                        System.out.print("Signature file: ");
                        signatureName = scanner.next();
                        System.out.print("Keys user file: ");
                        fileScanner = new Scanner(new File(scanner.next()));
                        rsa1.setOtherKey(fileScanner.nextInt());
                        rsa1.setOtherN(fileScanner.nextInt());
                        fileScanner.close();

                        LinkedList<Integer> list = new LinkedList<>();
                        fileScanner = new Scanner(new File(signatureName));
                        while (fileScanner.hasNextInt()){
                            list.add(fileScanner.nextInt());
                        }
                        fileScanner.close();

                        System.out.println("Valid signature: " +
                                new HashFile(new File(fileName)).validHash(rsa1.decodingHash(list.toArray(Integer[]::new))));
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

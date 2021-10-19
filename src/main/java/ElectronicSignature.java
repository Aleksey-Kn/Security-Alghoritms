import baseAlghoritms.SpecialMath;
import cipher.ElGamal;
import cipher.Gost;
import cipher.RSA;
import hashFunction.HashFile;

import java.io.File;
import java.io.FileWriter;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

public class ElectronicSignature {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String fileName, signatureName;
        boolean flag = true;
        FileWriter fileWriter;
        Scanner fileScanner;
        while (flag) {
            System.out.println("Выберите номер алгоритма одной цифрой без дополнительных символов");
            System.out.println("0. Exit");
            System.out.println("1. Подпись RSA");
            System.out.println("2. Проверка подписи RSA");
            System.out.println("3. Подпись Эль-Гамаль");
            System.out.println("4. Проверка подписи Эль-Гамаль");
            System.out.println("5. Подпсь ГОСТ");
            System.out.println("6. Проверка подписи ГОСТ");
            try {
                switch (Integer.parseInt(scanner.next())) {
                    case 1:
                        RSA rsa = new RSA();
                        System.out.print("File name: ");
                        fileName = scanner.next();
                        System.out.print("Save signature to file: ");
                        signatureName = scanner.next();
                        fileWriter = new FileWriter(signatureName);
                        for (int i : rsa.encodingHash(new HashFile(new File(fileName)))) {
                            fileWriter.write(i + "\n");
                        }
                        fileWriter.close();

                        System.out.print("Save keys user to file: ");
                        fileWriter = new FileWriter(scanner.next());
                        fileWriter.write(rsa.getPublicKey() + "\n");
                        fileWriter.write(rsa.getN() + "\n");
                        fileWriter.close();
                        break;
                    case 2:
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
                        while (fileScanner.hasNextInt()) {
                            list.add(fileScanner.nextInt());
                        }
                        fileScanner.close();

                        System.out.println("Valid signature: " +
                                new HashFile(new File(fileName)).validHash(rsa1.decodingHash(list.toArray(Integer[]::new))));
                        break;
                    case 3:
                        ElGamal elGamal = new ElGamal();
                        System.out.print("File name: ");
                        fileName = scanner.next();
                        System.out.print("Save signature to file: ");
                        signatureName = scanner.next();
                        System.out.print("Save keys user to file: ");
                        fileWriter = new FileWriter(scanner.next());
                        fileWriter.write(elGamal.getPublicKey() + "\n");
                        fileWriter.write(elGamal.getG() + "\n");
                        fileWriter.write(elGamal.getP() + "\n");
                        fileWriter.close();

                        fileWriter = new FileWriter(signatureName);
                        for (int[] i : elGamal.encodingHash(new HashFile(new File(fileName)))) {
                            fileWriter.write(i[0] + "\n");
                            fileWriter.write(i[1] + "\n");
                        }
                        fileWriter.close();
                        break;
                    case 4:
                        ElGamal elGamal1 = new ElGamal();
                        System.out.print("File name: ");
                        fileName = scanner.next();
                        System.out.print("Signature file: ");
                        signatureName = scanner.next();
                        System.out.print("Keys user file: ");
                        fileScanner = new Scanner(new File(scanner.next()));
                        elGamal1.setOtherKey(fileScanner.nextInt());
                        elGamal1.setG(fileScanner.nextInt());
                        elGamal1.setP(fileScanner.nextInt());
                        fileScanner.close();

                        fileScanner = new Scanner(new File(signatureName));
                        LinkedList<int[]> list1 = new LinkedList<>();
                        int[] temp;
                        while (fileScanner.hasNextInt()) {
                            temp = new int[2];
                            temp[0] = fileScanner.nextInt();
                            temp[1] = fileScanner.nextInt();
                            list1.add(temp);
                        }
                        fileScanner.close();

                        System.out.println("Valid signature: " +
                                new HashFile(new File(fileName))
                                        .validHashElGamal(elGamal1
                                                        .decodingHash(list1.toArray(int[][]::new)),
                                                elGamal1.getG(),
                                                elGamal1.getP()));
                        break;
                    case 5:
                        Gost gost = new Gost();
                        System.out.print("File name: ");
                        fileName = scanner.next();
                        System.out.print("Save signature to file: ");
                        signatureName = scanner.next();
                        System.out.print("Save keys user to file: ");
                        fileWriter = new FileWriter(scanner.next());
                        fileWriter.write(gost.getA() + "\n");
                        fileWriter.write(gost.getQ().toString() + "\n");
                        fileWriter.write(gost.getP().toString() + "\n");
                        fileWriter.write(gost.getY().toString() + "\n");
                        fileWriter.close();

                        fileWriter = new FileWriter(signatureName);
                        for(String[] s: gost.encoding(new HashFile(new File(fileName)))){
                            fileWriter.write(s[0] + "\n");
                            fileWriter.write(s[1] + "\n");
                        }
                        fileWriter.close();
                        break;
                    case 6:
                        System.out.print("File name: ");
                        fileName = scanner.next();
                        System.out.print("Signature file: ");
                        signatureName = scanner.next();
                        System.out.print("Keys user file: ");
                        fileScanner = new Scanner(new File(scanner.next()));
                        Gost gost1 = new Gost(new BigInteger(fileScanner.next()),
                                new BigInteger(fileScanner.next()),
                                new BigInteger(fileScanner.next()),
                                new BigInteger(fileScanner.next()));
                        fileScanner.close();

                        fileScanner = new Scanner(new File(signatureName));
                        LinkedList<BigInteger[]> gostList = new LinkedList<>();
                        BigInteger[] temp1;
                        while (fileScanner.hasNext()) {
                            temp1 = new BigInteger[2];
                            temp1[0] = new BigInteger(fileScanner.next());
                            temp1[1] = new BigInteger(fileScanner.next());
                            gostList.add(temp1);
                        }
                        fileScanner.close();

                        System.out.println("Valid signature: " +
                                gost1.validSignature(gostList.toArray(BigInteger[][]::new),
                                        new HashFile(new File(fileName))));
                        break;
                    case 0:
                        flag = false;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

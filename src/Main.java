import baseAlghoritms.Coder;
import baseAlghoritms.PrimeNumber;
import baseAlghoritms.SpecialMath;
import cipher.Shamir;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(System.in);
        String fileName;
        boolean flag = true;
        while (flag) {
            System.out.println("Выберите номер алгоритма одной цифрой без дополнительных символов");
            System.out.println("1. Шамир");
            try {
                switch (Integer.parseInt(scanner.next())) {
                    case 1:
                        System.out.print("File name: ");
                        fileName = scanner.next();
                        Shamir first = new Shamir();
                        Shamir second = new Shamir();
                        FileInputStream inputStream = new FileInputStream(fileName);
                        FileOutputStream outputStream = new FileOutputStream("Encoding" + fileName);
                        while (inputStream.available() > 0) {
                            outputStream.write(second.decoding(first.generateX3(second.generateX2(
                                    first.encoding((byte) inputStream.read())))));
                        }
                        inputStream.close();
                        outputStream.close();
                    default:
                        flag = false;
                }
            } catch (Exception e){
                System.out.println("\nНекорректный ввод!\n");
            }
        }
    }
}

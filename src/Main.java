import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        int[] data;
        int result, result2;
        Scanner scanner = new Scanner(System.in);
        Coder first, second;
        boolean flag = true;
        while (flag) {
            System.out.println("Выберите номер алгоритма одной цифрой без дополнительных символов");
            System.out.println("1. Быстрое возведение в степень по модулю");
            System.out.println("2. Обобщенный алгоритм Евклида");
            System.out.println("3. Построение общего ключа для двух абонентов по схеме Диффи-Хеллмана");
            System.out.println("4. Нахождение дискретного логарифма при помощи алгоритма «Шаг младенца, шаг великана»");
            try {
                switch (Integer.parseInt(scanner.next())) {
                    case 1:
                        System.out.println("1. Данные из файла\n2. Случайные числа");
                        if (scanner.nextInt() == 1) {
                            data = readFile(scanner);
                        } else
                            data = new int[]{random(), random(), random()};
                        System.out.printf("a = %d, x = %d, b = %d\n", data[0], data[1], data[2]);
                        result = SpecialMath.powOnModule(data[0], data[1], data[2]);
                        System.out.println("Mod: " + result);
                        break;
                    case 2:
                        System.out.println("1. Данные из файла\n2. Случайные числа");
                        if (scanner.nextInt() == 1) {
                            data = readFile(scanner);
                        } else
                            data = new int[]{random(), random()};
                        System.out.printf("a = %d, b = %d\n", data[0], data[1]);
                        result = SpecialMath.nod(data[0], data[1]);
                        System.out.println("НОД: " + result);
                        break;
                    case 3:
                        first = new Coder();
                        second = new Coder();
                        result = SpecialMath.diffihellman(first);
                        result2 = SpecialMath.diffihellman(second);
                        System.out.println("Кодовое число первого абонента: " + first.apply(result2));
                        System.out.println("Кодовое число второго абонента: " + second.apply(result));
                        break;
                    case 4:
                        System.out.println("1. Данные из файла\n2. Случайные числа");
                        if (scanner.nextInt() == 1) {
                            data = readFile(scanner);
                        } else {
                            data = new int[]{random(), SieveEratosfen.getInstance().randomPrime(), random()};
                            while (data[2] >= data[1] || SpecialMath.nod(data[1], data[2]) != 1) {
                                data[1] = SieveEratosfen.getInstance().randomPrime();
                                data[2] = random();
                            }
                        }
                        System.out.printf("a = %d, p = %d, y = %d\n", data[0], data[1], data[2]);
                        result = SpecialMath.steps(data[0], data[1], data[2]);
                        System.out.println("Степень: " + result);
                        break;
                    default:
                        flag = false;
                }
            } catch (Exception e){
                System.out.println("\nНекорректный ввод! Вы не слишком умный пользователь!\n");
            }
        }
    }

    private static int random(){
        return (int)(Math.random() * 1_000_000_000);
    }

    public static int[] readFile(Scanner scan) throws FileNotFoundException {
        System.out.println("Введите имя файла");
        Scanner fileScan = new Scanner( new File(scan.next()));
        return Arrays.stream(fileScan.nextLine().trim().split(" ")).mapToInt(Integer::parseInt).toArray();
    }
}

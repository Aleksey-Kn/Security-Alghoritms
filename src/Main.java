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
            System.out.println("�������� ����� ��������� ����� ������ ��� �������������� ��������");
            System.out.println("1. ������� ���������� � ������� �� ������");
            System.out.println("2. ���������� �������� �������");
            System.out.println("3. ���������� ������ ����� ��� ���� ��������� �� ����� �����-��������");
            System.out.println("4. ���������� ����������� ��������� ��� ������ ��������� ���� ��������, ��� ��������");
            try {
                switch (Integer.parseInt(scanner.next())) {
                    case 1:
                        System.out.println("1. ������ �� �����\n2. ��������� �����");
                        if (scanner.nextInt() == 1) {
                            data = readFile(scanner);
                        } else
                            data = new int[]{random(), random(), random()};
                        System.out.printf("a = %d, x = %d, b = %d\n", data[0], data[1], data[2]);
                        result = SpecialMath.powOnModule(data[0], data[1], data[2]);
                        System.out.println("Mod: " + result);
                        break;
                    case 2:
                        System.out.println("1. ������ �� �����\n2. ��������� �����");
                        if (scanner.nextInt() == 1) {
                            data = readFile(scanner);
                        } else
                            data = new int[]{random(), random()};
                        System.out.printf("a = %d, b = %d\n", data[0], data[1]);
                        result = SpecialMath.nod(data[0], data[1]);
                        System.out.println("���: " + result);
                        break;
                    case 3:
                        first = new Coder();
                        second = new Coder();
                        result = SpecialMath.diffihellman(first);
                        result2 = SpecialMath.diffihellman(second);
                        System.out.println("������� ����� ������� ��������: " + first.apply(result2));
                        System.out.println("������� ����� ������� ��������: " + second.apply(result));
                        break;
                    case 4:
                        System.out.println("1. ������ �� �����\n2. ��������� �����");
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
                        System.out.println("�������: " + result);
                        break;
                    default:
                        flag = false;
                }
            } catch (Exception e){
                System.out.println("\n������������ ����! �� �� ������� ����� ������������!\n");
            }
        }
    }

    private static int random(){
        return (int)(Math.random() * 1_000_000_000);
    }

    public static int[] readFile(Scanner scan) throws FileNotFoundException {
        System.out.println("������� ��� �����");
        Scanner fileScan = new Scanner( new File(scan.next()));
        return Arrays.stream(fileScan.nextLine().trim().split(" ")).mapToInt(Integer::parseInt).toArray();
    }
}

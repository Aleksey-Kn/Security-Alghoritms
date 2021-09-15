import java.util.Scanner;
import java.util.function.UnaryOperator;

public class Main {

    public static void main(String[] args) {
        //Scanner scanner = new Scanner(System.in);
        Coder first = new Coder();
        Coder second = new Coder();
        int y1 = SpecialMath.diffihellman(first);
        int y2 = SpecialMath.diffihellman(second);
        System.out.println(first.apply(y2));
        System.out.println(second.apply(y1));
    }
}

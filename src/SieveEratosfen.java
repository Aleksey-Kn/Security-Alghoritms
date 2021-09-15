import java.util.Arrays;
import java.util.Random;

public class SieveEratosfen {
    private final static SieveEratosfen instance = new SieveEratosfen();
    private final boolean[] b = new boolean[2_100_000_000]; // 2106589167 -- максимальное
    private final Random random;

    private SieveEratosfen(){
        int less = 10;
        boolean flag = true;
        Arrays.fill(b, true);
        System.out.print("\u001B[34m");
        for(int j = 4; j < b.length; j+=2){ // сразу отбрасываем чётные
            b[j] = false;
        }
        for(int i = 3; i < b.length; i+=2){
            if(b[i]) { // запускаем внутренний цикл только для простых чисел

                if(flag && i > less){
                    System.out.println("Find all prime less " + less + "...");
                    if(Integer.MAX_VALUE / 10 > less) // избежать переполнение
                        less *= 10;
                    else flag = false;
                }

                for (int j = i * 2; j < b.length && j > 0; j += i) {
                    b[j] = false;
                }
            }
        }
        System.out.println("Sieve is done!\u001B[0m");
        random = new Random();
    }

    public boolean isPrime(int v){
        return b[v];
    }

    private int positiveRandom(){
        return Math.abs(random.nextInt());
    }

    public int randomPrime(int min, int max){
        int delta = max - min;
        int now = positiveRandom() % delta + min;
        while (!isPrime(now)){
            now = positiveRandom() % delta + min;
        }
        return now;
    }

    public int randomPrime(){
        int now = positiveRandom();
        while (!isPrime(now)){
            now = positiveRandom();
        }
        return now;
    }

    public static SieveEratosfen getInstance() {
        return instance;
    }
}

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by Дмитрий on 19.02.2016.
 */
public class Main {
    public static void main(String[] args) {

        Random random = new Random();
        for (int i = 10; i <= 10; i *= 10) {
            Integer[] array = new Integer[i];
            for (int j = 0; j < i; j++) {
                array[j] = random.nextInt(i);
            }
            List<Integer> input = Arrays.asList(array);
            check(input);
        }
    }

    private static <T extends Comparable> void check(List<T> input) {
        System.out.println();
        for (T t : input) {
            System.out.print(t + ",");
        }
        System.out.println();

        long start;

//        start = System.nanoTime();
//        List<T> concRes = Merge.sortConc(input);
//        System.out.println("Concurrent " + (System.nanoTime() - start) + " nanoseconds");
//        System.out.println();
//        for (T t : concRes) {
//            System.out.print(t + ",");
//        }
//        System.out.println();

        start = System.nanoTime();
        List<T> seqRes = Merge.sortSeq(input);
        System.out.println("Not concurrent " + (System.nanoTime() - start) + " nanoseconds");
        System.out.println();
        for (T t : seqRes) {
            System.out.print(t + ",");
        }
        System.out.println();

        start = System.nanoTime();
        T[] array = (T[]) input.toArray();
        Arrays.sort(array, 0, input.size(), null);
        List<T> basic = Arrays.asList(array);
        System.out.println("Standart " + (System.nanoTime() - start) + " nanoseconds");
        System.out.println();
        for (T t : basic) {
            System.out.print(t + ",");
        }
        System.out.println();

        System.out.println((seqRes.equals(basic)));
        //System.out.println((concRes.equals(basic)));
    }

    private static void testMerge(){
            BigInteger[] arr1 = {new BigInteger("1"),new BigInteger("2"),new BigInteger("6"),new BigInteger("8")};
        BigInteger[] arr2 = {new BigInteger("3"),new BigInteger("4"),new BigInteger("7"),new BigInteger("9")};
        BigInteger[] arr3 =Merge.merge(arr1,arr2);
        for(BigInteger i : arr3){
            System.out.println(i +",");
        }
    }
}

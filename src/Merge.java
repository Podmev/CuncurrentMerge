import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by Дмитрий on 19.02.2016.
 */

public class Merge {
    private static ExecutorService executor = Executors.newCachedThreadPool();

    public static <T> T[] newArray(T exampleObject, int length) {

        Class<?> clazz = exampleObject.getClass();
        T[] newArray = (T[]) java.lang.reflect.Array.newInstance
                (clazz, length);
        return newArray;
    }

    public static <T extends Comparable> List<T> sortConc(List<T> elements) {
        T[] array = (T[]) elements.toArray();
        int blockSize = (int) Math.round(Math.sqrt(elements.size()));
        T[] sorted = sortConc(array, 0, array.length - 1, blockSize);
        return Arrays.asList(sorted);
    }

    public static <T extends Comparable> List<T> sortSeq(List<T> elements) {
        T[] array = (T[]) elements.toArray();
        int blockSize = (int) Math.round(Math.sqrt(elements.size()));
        T[] sorted = sortSeq(array, 0, array.length - 1);
        return Arrays.asList(sorted);
    }

    private static <T extends Comparable> T[] sortConc(final T[] array, final int start, final int finish, final int maxBlock) {
        if (maxBlock >= finish - start) {
            Arrays.sort(array, start, finish, null);
            return Arrays.copyOfRange(array, start, finish);
        }
        final int mid = start + (finish - start) / 2;
        Future<T[]> future1 = executor.submit(new Callable<T[]>() {
            public T[] call() {
                return sortConc(array, start, mid, maxBlock);
            }
        });
        Future<T[]> future2 = executor.submit(new Callable<T[]>() {
            public T[] call() {
                return sortConc(array, mid + 1, finish, maxBlock);
            }
        });
        try {
            return merge(future1.get(), future2.get());
        } catch (ExecutionException e1) {
            System.out.println(e1);
        } catch (InterruptedException e2) {
            System.out.println(e2);
        }
        return null;
    }

    private static <T extends Comparable> T[] sortSeq(T[] array, int start, int finish) {
        int range = finish - start;

        final int mid = start + range / 2;
        return merge(sortSeq(array, start, mid), sortSeq(array, mid + 1, finish));
    }

    public static <T extends Comparable> T[] merge(T[] array1, T[] array2) {
        int len1 = array1.length;
        int len2 = array2.length;
        T[] result = newArray(array1[0], len1 + len2);
        int i1 = 0, i2 = 0;
        while (i1 < len1 || i2 < len2) {
            if (i1 < len1 && i2 < len2) {
                if (array1[i1].compareTo(array2[i2]) < 0) {
                    result[i1 + i2] = array1[i1];
                    i1++;
                } else {
                    result[i1 + i2] = array2[i2];
                    i2++;
                }
            } else if (i2 >= len2) {
                result[i1 + i2] = array1[i1];
                i1++;
            } else {
                result[i1 + i2] = array2[i2];
                i2++;
            }
        }
        return result;
    }
}
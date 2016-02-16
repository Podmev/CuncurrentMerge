import java.util.List;
/**
 * Created by Дмитрий on 16.02.2016.
 */
public interface Sortable<T> {

    List<T> sort(List<T> list);
}

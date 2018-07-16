package recipebook.util;

public interface Crud<T> {
    T create(Object t);

    T getById(Long id);

    T update(T t1, T t2);

    boolean removeById(Long id);

    void removeAll();
}

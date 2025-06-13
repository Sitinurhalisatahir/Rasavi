package temurasa.controllers;

import java.util.List;

public interface IBaseController<T> {
    boolean tambah(T obj);
    List<T> getAll();
}

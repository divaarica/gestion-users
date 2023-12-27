package entities.dao;

import entities.User;

import java.util.List;

public interface Base <T> {
    public T saisie(T t);
    public int add(T t);
    public int delete(int id);
    public int update(T t);
    public T get(int id);
    public List<T> list();
    public int showListe();
}


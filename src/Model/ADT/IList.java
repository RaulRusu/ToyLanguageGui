package Model.ADT;

import CustomException.MyException;

import java.util.List;

public interface IList<TElement> {
    void pushFront(TElement element);
    void pushBack(TElement element);
    TElement popFront() throws MyException;
    TElement popBack() throws MyException;
    void clear();
    boolean isEmpty();
    int size();
    List<TElement> getValues();
}

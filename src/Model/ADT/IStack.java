package Model.ADT;

import CustomException.MyException;

import java.util.List;
import java.util.ListIterator;

public interface IStack<TElement> {
    TElement pop() throws MyException;
    void push(TElement element);
    boolean isEmpty();
    ListIterator getIterator();
}

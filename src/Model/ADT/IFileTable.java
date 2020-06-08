package Model.ADT;

import CustomException.MyException;

import java.util.HashMap;
import java.util.List;

public interface IFileTable<TElementFirst, TElementSecond> {
    boolean isDefined(TElementFirst first);
    void add(TElementFirst first, TElementSecond second);
    TElementSecond getValue(TElementFirst first) throws MyException;
    void remove(TElementFirst first);
    HashMap<TElementFirst, TElementSecond> getContent();
}

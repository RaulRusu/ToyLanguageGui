package Model.ADT;

import CustomException.MyException;

import java.util.HashMap;
import java.util.Hashtable;

public interface IDictionary<TElementFirst, TElementSecond> {
    boolean isDefined(TElementFirst first);

    TElementSecond getValue(TElementFirst first) throws MyException;

    void update(TElementFirst first, TElementSecond second);

    HashMap<TElementFirst, TElementSecond> getContent();
}

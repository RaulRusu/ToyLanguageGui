package Model.ADT;

import CustomException.MyException;

import java.util.HashMap;
import java.util.Map;

public interface IHeap<IValue> {
    boolean isDefined(int key);
    void update(int key, IValue value);
    int getNextFree();
    int add(IValue value);
    IValue getValue(int key) throws MyException;
    void remove(int key);
    HashMap<Integer, IValue> getContent();
    void setContent(Map<Integer, IValue> hash);
}

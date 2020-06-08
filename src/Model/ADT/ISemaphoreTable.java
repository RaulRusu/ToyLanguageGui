package Model.ADT;

import CustomException.MyException;
import Model.Value.IValue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ISemaphoreTable {
    boolean isDefined(int key);
    //void update(int key, IValue value);
    public Pair<Integer, List<Integer>> getValue(int key);
    public HashMap<Integer, Pair<Integer, List<Integer>>> getContent();
    int getNextFree();
    int add(int n);
}

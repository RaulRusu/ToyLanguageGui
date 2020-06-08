package Model.ADT;

import java.util.HashMap;
import java.util.Map;

import CustomException.MyException;

public class Heap<IValue> implements IHeap<IValue> {
    private HashMap<Integer, IValue> hashMap;
    private Integer nextFree;

    public Heap() {
        this.hashMap = new HashMap<Integer, IValue>();
        this.nextFree = 1;
    }

    private void setNextFree() {
        this.nextFree++;
    }

    public int getNextFree() {
        return this.nextFree;
    }

    @Override
    public boolean isDefined(int key) {
        return this.hashMap.containsKey(key);
    }

    @Override
    public int add(IValue value) {
        this.hashMap.put(nextFree, value);
        int prevFree = this.getNextFree();
        this.setNextFree();
        return prevFree;
    }

    @Override
    public void update(int key, IValue value) {
        this.hashMap.put(key, value);
    }

    @Override
    public IValue getValue(int key) throws MyException {
        if (!this.isDefined(key))
            throw new MyException("Heap exception: " + key + " is not a key");
        return this.hashMap.get(key);
    }

    @Override
    public void remove(int key) {
        this.hashMap.remove(key);
    }

    @Override
    public HashMap<Integer, IValue> getContent() {
        return this.hashMap;
    }

    @Override
    public void setContent(Map<Integer, IValue> hash) {
        this.hashMap = (HashMap<Integer, IValue>) hash;
    }

    @Override
    public String toString() {
        String string = "";
        for (Integer key : this.hashMap.keySet()) {
            string = string + key.toString() + " " + this.hashMap.get(key) + "\n";
        }

        return string;
    }


}

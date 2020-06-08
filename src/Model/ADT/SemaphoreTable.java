package Model.ADT;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SemaphoreTable implements ISemaphoreTable {
    private HashMap<Integer, Pair<Integer, List<Integer>>> hashMap;
    private Integer nextFree;

    public SemaphoreTable() {
        this.hashMap = new HashMap<>();
        this.nextFree = 1;
    }

    @Override
    public boolean isDefined(int key) {
        return this.hashMap.containsKey(key);
    }

    @Override
    public synchronized Pair<Integer, List<Integer>> getValue(int key) {
        return this.hashMap.get(key);
    }

    @Override
    public HashMap<Integer, Pair<Integer, List<Integer>>> getContent() {
        return this.hashMap;
    }

    private synchronized void setNextFree() {
        this.nextFree++;
    }

    @Override
    public synchronized int getNextFree() {
        return this.nextFree;
    }

    @Override
    public synchronized int add(int n) {
        this.hashMap.put(this.nextFree, new Pair(n, new ArrayList<>()));
        int prev = this.getNextFree();
        this.setNextFree();
        return prev;
    }

    @Override
    public String toString() {
        String string = "";
        for (Integer key : this.hashMap.keySet()) {
            String idString = "";
            for(Integer value: this.hashMap.get(key).getSecond())
                idString += value + " ";
            string = string + key.toString() + " " + "( idx:" + this.hashMap.get(key).getFirst() + "->" + idString + ")" + "\n";
        }

        return string;
    }
}

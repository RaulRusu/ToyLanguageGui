package Model.ADT;

import CustomException.MyException;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;


public class FileTable<TElementFirst, TElementSecond> implements IFileTable<TElementFirst, TElementSecond> {
    private HashMap<TElementFirst, TElementSecond> dictionary;

    public FileTable() {
        this.dictionary = new HashMap<TElementFirst, TElementSecond>();
    }

    @Override
    public boolean isDefined(TElementFirst first) {
        return this.dictionary.containsKey(first);
    }

    @Override
    public void add(TElementFirst first, TElementSecond second) {
        this.dictionary.put(first, second);
    }

    @Override
    public TElementSecond getValue(TElementFirst first) throws MyException {
        if (!this.isDefined(first))
            throw new MyException("FileTable exception: " + first + " is not a key");
        return this.dictionary.get(first);
    }

    @Override
    public void remove(TElementFirst first) {
        this.dictionary.remove(first);
    }

    @Override
    public HashMap<TElementFirst, TElementSecond> getContent() {
        return this.dictionary;
    }

    @Override
    public String toString() {
        String string = "";
        for (TElementFirst key : this.dictionary.keySet()) {
            string = string + key.toString() + "\n";
        }

        return string;
    }
}

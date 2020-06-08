package Model.ADT;

import java.util.HashMap;
import java.util.Hashtable;

import CustomException.MyException;
import Model.Type.IType;

public class Dictionary<TElementFirst, TElementSecond> implements IDictionary<TElementFirst, TElementSecond> {
    private HashMap<TElementFirst, TElementSecond> dictionary;

    public Dictionary() {
        this.dictionary = new HashMap<TElementFirst, TElementSecond>();
    }

    @Override
    public boolean isDefined(TElementFirst first) {
        return this.dictionary.containsKey(first);
    }

    @Override
    public TElementSecond getValue(TElementFirst first) throws MyException {
        if (!this.isDefined(first))
            throw new MyException("Dictionary exception: " + first + " is not a key");
        return this.dictionary.get(first);
    }

    @Override
    public void update(TElementFirst first, TElementSecond second) {
        this.dictionary.put(first, second);
    }

    @Override
    public HashMap<TElementFirst, TElementSecond> getContent() {
        return this.dictionary;
    }

    @Override
    public String toString() {
        String string = "";
        for (TElementFirst key : this.dictionary.keySet()) {
            string = string + key.toString() + " " + this.dictionary.get(key) + "\n";
        }

        return string;
    }
}

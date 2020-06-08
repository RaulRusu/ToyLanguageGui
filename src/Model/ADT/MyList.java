package Model.ADT;

import java.util.LinkedList;
import java.util.List;

import CustomException.MyException;

public class MyList<TElement> implements IList<TElement> {
    private LinkedList<TElement> list;

    public MyList() {
        this.list = new LinkedList<TElement>();
    }

    @Override
    public void pushFront(TElement element) {
        this.list.addFirst(element);
    }

    @Override
    public void pushBack(TElement element) {
        this.list.addLast(element);
    }

    @Override
    public TElement popFront() throws MyException {
        if (this.isEmpty())
            throw new MyException("List exception: list is empty");
        return this.list.removeFirst();
    }

    @Override
    public TElement popBack() throws MyException {
        if (this.isEmpty())
            throw new MyException("List exception: list is empty");
        return this.list.removeLast();
    }

    @Override
    public void clear() {
        this.list.clear();
    }

    @Override
    public boolean isEmpty() {
        return this.list.isEmpty();
    }

    @Override
    public int size() {
        return this.list.size();
    }

    @Override
    public List<TElement> getValues() {
        return this.list;
    }

    @Override
    public String toString() {
        String string = "";

        for (TElement element : this.list) {
            string = string + element.toString() + "\n";
        }

        return string;
    }
}

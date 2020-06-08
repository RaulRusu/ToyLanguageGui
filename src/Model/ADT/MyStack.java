package Model.ADT;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Stack;
import CustomException.MyException;
import View.TextMenu;

public class MyStack<TElement> implements IStack<TElement> {
    private Stack<TElement> stack;

    public MyStack() {
        this.stack = new Stack<TElement>();
    }

    @Override
    public boolean isEmpty() {
        return this.stack.isEmpty();
    }

    @Override
    public TElement pop() throws MyException {
        if (this.isEmpty())
            throw new MyException("Stack exception: stack is empty");
        return this.stack.pop();
    }

    @Override
    public void push(TElement element) {
        this.stack.push(element);
    }

    @Override
    public ListIterator getIterator() {
        return stack.listIterator(this.stack.size());
    }

    @Override
    public String toString() {
        String string = "";

        ListIterator element = stack.listIterator(this.stack.size());

        while(element.hasPrevious()) {
            string = string + element.previous().toString() + "\n";
        }

        return string;
    }
}

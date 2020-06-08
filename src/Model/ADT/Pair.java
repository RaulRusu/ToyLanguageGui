package Model.ADT;

public class Pair<TF, TS> {
    private TF first;
    private TS second;
    public Pair(TF f, TS s) {
        this.first = f;
        this.second = s;
    }

    public TF getFirst() {
        return first;
    }

    public TS getSecond() {
        return second;
    }

    public void setFirst(TF newFirst) {
        this.first = newFirst;
    }

    public void setSecond(TS newSecond) {
        this.second = newSecond;
    }

    @Override
    public String toString() {
        return "idx:" + this.first + "->" + this.second.toString();
    }
}

package common;

public class Pair<T1, T2> {
    T1 value1;
    T2 value2;

    public Pair(T1 var1, T2 var2) { value1 = var1; value2 = var2; }

    @Override
    public String toString() {
        return "Pair{" +
                "value1=" + value1 +
                ", value2=" + value2 +
                '}';
    }
}

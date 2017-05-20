package lt.vu.feedback_system.utils.abstractions;


public final class Tuple<T, S> {

    private final T first;

    private final S second;

    public Tuple(T first, S second) {
        this.first = first;
        this.second = second;
    }

    public T first() { return first; }

    public S second() { return second; }

    @Override
    public String toString() {
        return String.format("Tuple(%s, %s)", first, second);
    }

}
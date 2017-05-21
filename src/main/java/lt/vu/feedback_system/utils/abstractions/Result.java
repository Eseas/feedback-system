package lt.vu.feedback_system.utils.abstractions;

import java.util.NoSuchElementException;
import java.util.function.Function;


/**
 * Abstraction inspired by Scala Try[T]
 * value is an object to be contained on successful operation
 * failureMsg is a failure message identifying operation failure
 * @param <T>
 */
public class Result<T> {

    private final T value;

    private final String failureMsg;

    private Result(T value, String failureMsg) {
        this.value = value;
        this.failureMsg = failureMsg;
    }

    public static <T> Result<T> Success(T value) {
        return new Result<>(value, null);
    }

    public static <T> Result<T> Failure(String errorMsg) {
        return new Result<>(null, errorMsg);
    }

    public boolean isSuccess() { return this.value != null; }

    public boolean isFailure() { return !this.isSuccess(); }

    public <R> Result<R> map(Function<T, R> function) {
        return isSuccess() ? Result.Success(function.apply(value)) : Result.Failure(failureMsg);
    }

    public <R> Result<R> flatMap(Function<T, Result<R>> function) {
        return isSuccess() ? function.apply(value) : Result.Failure(failureMsg);
    }

    public T get() {
        if (isFailure()) throw new NoSuchElementException("No value present");
        else return this.value;
    }

    public String getFailureMsg() {
        if (isSuccess()) throw new NoSuchElementException("No error message present");
        else return this.failureMsg;
    }

    @Override
    public String toString() {
        return isSuccess() ? String.format("Success(%s)", value) : String.format("Failure(%s)", failureMsg);
    }
}

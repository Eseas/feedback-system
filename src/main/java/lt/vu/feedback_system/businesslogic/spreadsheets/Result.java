package lt.vu.feedback_system.businesslogic.spreadsheets;

import java.util.NoSuchElementException;
import java.util.function.Function;

public class Result<T> {

    private final T value;

    private final String errorMsg;

    private Result(T value, String errorMsg) {
        this.value = value;
        this.errorMsg = errorMsg;
    }

    public static <T> Result<T> success(T value) {
        return new Result<>(value, null);
    }

    public static <T> Result<T> failure(String errorMsg) {
        return new Result<>(null, errorMsg);
    }

    public boolean isSuccess() { return this.value != null; }

    public boolean isFailure() { return !this.isSuccess(); }

    public <R> Result<R> map(Function<T, R> function) {
        return isSuccess() ? Result.success(function.apply(value)) : Result.failure(errorMsg);
    }

    public <R> Result<R> flatMap(Function<T, Result<R>> function) {
        return isSuccess() ? function.apply(value) : Result.failure(errorMsg);
    }

    public T get() {
        if (isFailure()) throw new NoSuchElementException("No value present");
        else return this.value;
    }

    public String getErrorMsg() {
        if (isSuccess()) throw new NoSuchElementException("No error message present");
        else return this.errorMsg;
    }

    @Override
    public String toString() {
        return isSuccess() ? String.format("Value: %s", value) : String.format("Error message: %s", errorMsg);
    }
}

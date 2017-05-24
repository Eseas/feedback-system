package lt.vu.feedback_system.utils;

import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

public final class CollectionUtils {

    public static <T> Optional<T> findFirst(Stream<T> stream, Predicate<T> predicate) {
        return stream.filter(predicate).findFirst();
    }

}

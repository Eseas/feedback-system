package lt.vu.asynchronous;

import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.ejb.Stateful;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.concurrent.Future;

@SessionScoped
@Stateful
public class CompB implements Serializable {

    @Asynchronous
    public Future<String> sayHello() {
        System.out.println("CompB starts working on a big task...");
        try {
            Thread.sleep(10000); // sleep for 10 seconds
        } catch (InterruptedException e) {
        }
        System.out.println("CompB: big task completed.");
        return new AsyncResult<>("BIG result from component B :)");
    }

}

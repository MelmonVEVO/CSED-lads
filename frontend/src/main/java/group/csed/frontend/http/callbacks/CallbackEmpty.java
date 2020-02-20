package group.csed.frontend.http.callbacks;

import group.csed.frontend.http.Status;

public interface CallbackEmpty extends Callback<Object> {

    void run(Status status);

    default void run(Status status, Object response) {
        run(status);
    }
}
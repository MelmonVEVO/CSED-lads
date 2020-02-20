package group.csed.frontend.http.callbacks;

import group.csed.frontend.http.Status;

public interface Callback<T> {

    void run(Status status, T response);
}

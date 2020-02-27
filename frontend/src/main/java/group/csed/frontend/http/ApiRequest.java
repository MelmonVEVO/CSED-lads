package group.csed.frontend.http;

import group.csed.frontend.http.callbacks.Callback;
import group.csed.frontend.http.callbacks.CallbackEmpty;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.asynchttpclient.Response;
import org.json.JSONObject;

import java.util.concurrent.Future;

public class ApiRequest {

    private static AsyncHttpClient asyncHttpClient = new DefaultAsyncHttpClient();

    private static Response post(JSONObject body, String... uri) throws Exception {
        final Future<Response> future = asyncHttpClient.preparePost(UrlBuilder.build(uri))
                .addHeader("content-type", "application/json").setBody(body.toString()).execute();
        return future.get();
    }

    public static void login(String email, String password, CallbackEmpty callback) {
        try {
            final Response response = post(new JSONObject().put("email", email).put("password", password), "accounts", "login");
            if(postRequestSuccessful(response)) {
                runCallback(callback, Status.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        runCallback(callback, Status.FAIL);
    }

    public static void createAccount(String email, String firstName, String lastName, String password, String dob, CallbackEmpty callback) {
        try {
            final Response response = post(new JSONObject()
                    .put("email", email)
                    .put("firstName", firstName)
                    .put("lastName", lastName)
                    .put("password", password)
                    .put("dob", dob), "accounts", "create");
            if(postRequestSuccessful(response)) {
                runCallback(callback, Status.OK);
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        runCallback(callback, Status.FAIL);
    }

    public static void trackMood(int accountId, String description, CallbackEmpty callback) {
        try {
            final Response response = post(new JSONObject().put("id", accountId).put("description", description), "mood");
            if(postRequestSuccessful(response)) {
                runCallback(callback, Status.OK);
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        runCallback(callback, Status.FAIL);
    }

    public static void savePeriodData(int accountId, String started, int lasted, int cycleLength, CallbackEmpty callback) {
        try {
            final Response response = post(new JSONObject()
                    .put("id", accountId)
                    .put("started", started)
                    .put("lasted", lasted)
                    .put("cycleLength", cycleLength), "period-tracker", "create");
            if(postRequestSuccessful(response)) {
                runCallback(callback, Status.OK);
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        runCallback(callback, Status.FAIL);
    }



    private static boolean postRequestSuccessful(Response response) {
        if(response.getStatusCode() == 200) {
            final JSONObject responseBody = new JSONObject(response.getResponseBody());
            if(responseBody.getBoolean("success")) {
                return true;
            }
        }
        return false;
    }

    private static void runCallback(Callback<?> callback, Status status) {
        runCallback(callback, status, null);
    }

    private static <T> void runCallback(Callback<T> callback, Status status, T response) {
        callback.run(status, response);
    }
}
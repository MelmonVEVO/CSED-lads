package group.csed.frontend.http;

import com.google.gson.Gson;
import group.csed.frontend.http.callbacks.Callback;
import group.csed.frontend.http.callbacks.CallbackEmpty;
import group.csed.frontend.http.models.PeriodData;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.asynchttpclient.Response;
import org.json.JSONObject;

import java.util.concurrent.Future;

public class ApiRequest {

    private static Gson gson = new Gson();
    private static AsyncHttpClient asyncHttpClient = new DefaultAsyncHttpClient();

    private static Response post(JSONObject body, String... uri) {
        try {
            final Future<Response> future = asyncHttpClient.preparePost(UrlBuilder.build(uri))
                    .addHeader("content-type", "application/json").setBody(body.toString()).execute();
            return future.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Response get(String... uri) {
        try {
            Future<Response> future = asyncHttpClient.prepareGet(UrlBuilder.build(uri)).execute();
            return future.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void login(String email, String password, Callback<Integer> callback) {
        final Response response = post(new JSONObject().put("email", email).put("password", password), "accounts", "login");
        if(postRequestSuccessful(response)) {
            runCallback(callback, Status.OK, new JSONObject(response.getResponseBody()).getInt("id"));
            return;
        }
        runCallback(callback, Status.FAIL);
    }

    public static void createAccount(String email, String firstName, String lastName, String password, String dob, CallbackEmpty callback) {
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
        runCallback(callback, Status.FAIL);
    }

    public static void trackMood(int accountId, String description, CallbackEmpty callback) {
        final Response response = post(new JSONObject().put("id", accountId).put("description", description), "mood");
        if(postRequestSuccessful(response)) {
            runCallback(callback, Status.OK);
            return;
        }
        runCallback(callback, Status.FAIL);
    }

    public static void savePeriodData(int accountId, String started, int lasted, int cycleLength, CallbackEmpty callback) {
        final Response response = post(new JSONObject()
                .put("id", accountId)
                .put("started", started)
                .put("lasted", lasted)
                .put("cycleLength", cycleLength), "period-tracker", "create");
        if(postRequestSuccessful(response)) {
            runCallback(callback, Status.OK);
            return;
        }
        runCallback(callback, Status.FAIL);
    }

    public static void getPeriodData(int accountId, Callback<PeriodData> callback) {
        final Response response = get("period-tracker", String.valueOf(accountId));
        if(response.getStatusCode() == 200) {
            final PeriodData data = gson.fromJson(response.getResponseBody(), PeriodData.class);
            if(data != null) {
                runCallback(callback, Status.OK, data);
                return;
            }
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
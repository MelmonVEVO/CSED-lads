package group.csed.frontend.http;

public class UrlBuilder {

    private static final String BASE = "http://localhost:3000";

    public static String build(String... parts) {
        StringBuffer buffer = new StringBuffer(BASE);
        if(parts == null) {
            buffer.append("/");
            return buffer.toString();
        }
        for(String part : parts) {
            buffer.append("/");
            buffer.append(part);
        }
        return buffer.toString();
    }
}
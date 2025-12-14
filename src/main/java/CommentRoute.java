import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.OutputStream;

public class CommentRoute {

    public static void handleComment(BufferedReader in, OutputStream out) throws Exception {
        String line;
        int contentLength = 0;

        while (!(line = in.readLine()).isEmpty()) {
            if (line.startsWith("Content-Length:")) {
                contentLength = Integer.parseInt(line.split(":")[1].trim());
            }
        }

        char[] body = new char[contentLength];
        in.read(body);

        JSONObject json = new JSONObject(new String(body));

        int tradeId = json.getInt("tradeId");
        int userId = json.getInt("userId");
        String text = json.getString("text");

        System.out.println("COMENTÁRIO → trade=" + tradeId +
                ", user=" + userId +
                ", texto=\"" + text + "\"");

        String response =
                "{\"status\":\"comment_saved\"}";

        String httpResponse =
                "HTTP/1.1 200 OK\r\n" +
                        "Content-Type: application/json\r\n" +
                        "Content-Length: " + response.length() + "\r\n" +
                        "\r\n" +
                        response;

        out.write(httpResponse.getBytes());
    }
}

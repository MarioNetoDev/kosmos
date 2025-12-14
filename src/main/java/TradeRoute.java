import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.OutputStream;

public class TradeRoute {

    public static void handleTrade(BufferedReader in, OutputStream out) throws Exception {
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

        int buyerId = json.getInt("buyerId");
        int productId = json.getInt("productId");
        int quantity = json.getInt("quantity");

        System.out.println("TRADE CRIADA → buyer=" + buyerId +
                ", product=" + productId +
                ", quantity=" + quantity);

        String response =
                "{\"status\":\"trade_confirmed\"}";

        String httpResponse =
                "HTTP/1.1 200 OK\r\n" +
                        "Content-Type: application/json\r\n" +
                        "Content-Length: " + response.length() + "\r\n" +
                        "\r\n" +
                        response;

        out.write(httpResponse.getBytes());
    }
}

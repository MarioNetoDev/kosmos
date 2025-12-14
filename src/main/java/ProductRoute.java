import java.io.OutputStream;
import java.nio.charset.StandardCharsets;


public class ProductRoute {

    public static void handleGetProducts(OutputStream out, String query) throws Exception {

        String productsJson = """
        [
          {
            "id": 1,
            "title": "Arte Cósmica",
            "description": "Ilustração digital espacial",
            "price": "R$ 120,00",
            "image": "/marketplace/imagesMarket/art1.png"
          },
          {
            "id": 2,
            "title": "Nebulosa Azul",
            "description": "Arte abstrata em tons de azul",
            "price": "R$ 90,00",
            "image": "/marketplace/imagesMarket/art2.png"
          }
        ]
        """;

        String response =
                "HTTP/1.1 200 OK\r\n" +
                        "Content-Type: application/json\r\n" +
                        "Content-Length: " + productsJson.getBytes().length + "\r\n" +
                        "\r\n" +
                        productsJson;

        out.write(response.getBytes(StandardCharsets.UTF_8));
    }
}

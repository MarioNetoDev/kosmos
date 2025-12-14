
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class KosmosServer {
    private final int port;
    private final Path resourcePath;

    public KosmosServer(int port) {
        this.port = port;
        this.resourcePath = Paths.get(System.getProperty("user.dir"), "src", "main", "resources");
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(this.port)) {
            System.out.println("O Kosmos está rodando em http://localhost:" + port);
            while (true) {
                Socket client = serverSocket.accept();
                handleClient(client);
            }

        } catch(IOException e){
            e.printStackTrace();
        }
    }

    private void handleClient(Socket client) {
        try {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(client.getInputStream())
            );
            OutputStream out = client.getOutputStream();

            String requestLine = in.readLine();
            System.out.println("REQ → " + requestLine);

            if (requestLine == null) {
                client.close();
                return;
            }

            if (requestLine.startsWith("GET /api/products")) {
                ProductRoute.handleGetProducts(out, requestLine);

            } else if (requestLine.startsWith("POST /api/login")) {
                LoginRoute.handleLogin(in, out);

            } else if (requestLine.startsWith("POST /api/trade")) {
                TradeRoute.handleTrade(in, out);

            } else if (requestLine.startsWith("POST /api/comment")) {
                CommentRoute.handleComment(in, out);

            } else if (requestLine.startsWith("POST /api/rating")) {
                RatingRoute.handleRating(in, out);

            } else if (requestLine.startsWith("GET ")) {
                String fullPath = requestLine.split(" ")[1];

                String path = fullPath.split("\\?")[0];

                send(out, path);
            }
            else {
                sendNotFound(out);
            }

            client.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private void send(OutputStream out, String request) throws IOException {

        if (request.contains("?")) {
            request = request.substring(0, request.indexOf("?"));
        }

        if (request.equals("/")) {
            request = "index.html";
        }


        if (request.startsWith("/")) {
            request = request.substring(1);
        }

        Path filePath = Paths.get(resourcePath.toString(), request);
        String mimeType = getContentType(request);

        System.out.println("ARQUIVO BUSCADO → " + filePath.toAbsolutePath());

        if (Files.exists(filePath)) {
            byte[] fileBytes = Files.readAllBytes(filePath);

            String headers = "HTTP/1.1 200 OK\r\n"
                    + "Content-Type: " + mimeType + "\r\n"
                    + "Content-Length: " + fileBytes.length + "\r\n"
                    + "\r\n";

            out.write(headers.getBytes(StandardCharsets.UTF_8));
            out.write(fileBytes);
        } else {
            System.out.println(" NÃO ENCONTRADO");
            sendNotFound(out);
        }
    }



    private void sendNotFound(OutputStream out) throws IOException {
        String response = "HTTP/1.1 404 Not Found\r\n"
                + "Content-Type: text/plain; charset=utf-8\r\n"
                + "Content-Length: 9\r\n"
                + "\r\n"
                + "Not Found";
        out.write(response.getBytes(StandardCharsets.UTF_8));
    }


    public String getContentType(String filePath) {
        String[] filePathArray = filePath.split("\\.");

        if (filePathArray.length == 1) {
            return "application/octet-stream";
        }

        String extension = filePathArray[1].toLowerCase();

        return switch (extension) {
            case "html" -> "text/html; charset=utf-8";
            case "css" -> "text/css";
            case "js" -> "application/javascript";
            case "jpg", ".jpeg" -> "image/jpeg";
            case "png" -> "image/png";
            case "mp4" -> "video/mp4";
            default -> "application/octet-stream";
        };
    }
}

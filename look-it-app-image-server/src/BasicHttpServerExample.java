import com.sun.net.httpserver.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import javax.imageio.ImageIO;

public class BasicHttpServerExample {
    String Link ="";

    public static void main(String[] args) throws IOException {
        System.out.println("Server Started");
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        //HttpContext context = server.createContext("/img");
        server.createContext("/img", new ImageHandler());
        server.createContext("/get", new LinkHandler());
        server.createContext("/test", new StaticHandler("/test", "C:/Users/Nikita/IdeaProjects/look-it-app-image-server_v2/web"));
        server.setExecutor(null);

        //context.setHandler(BasicHttpServerExample::handleRequest);
        server.start();
    }

    public static void stop(HttpServer server) throws IOException
    {
        server.stop(2);
    }

    static class LinkHandler implements HttpHandler {
        public void handle(HttpExchange exchange) throws IOException {
            //FileInputStream fileInputStream = new FileInputStream("C:/Users/Nikita/IdeaProjects/look-it-app-image-server_v2/web/BigLink.txt");
            //BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream, 200);
            //URI requestURI = exchange.getRequestURI();
            //String response = requestURI.toString();

            File file = new File("C:/Users/Nikita/IdeaProjects/look-it-app-image-server_v2/web/BigLink.txt");
            //создаем объект FileReader для объекта File
            FileReader fr = new FileReader(file);
            //создаем BufferedReader с существующего FileReader для построчного считывания
            BufferedReader reader = new BufferedReader(fr);
            String str = reader.readLine();
            System.out.println(str);
            // считаем сначала первую строку
            exchange.sendResponseHeaders(200, str.length());
            OutputStream os = exchange.getResponseBody();

            for(int i = 0; i < str.length(); i++)
            {
                os.write(str.getBytes());
            }
            os.close();
        }

    }

    static class ImageHandler implements HttpHandler {
        public void handle(HttpExchange exchange) throws IOException {
            int l = 0;
            URI requestURI = exchange.getRequestURI();
            Headers requestHeaders = exchange.getRequestHeaders();

            String response = requestURI.toString();
            System.out.println("Start out");
            exchange.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
            System.out.println("End out");
            System.out.println("Start img");
            String base64String = requestHeaders.get("img").toString();
            String directory = "C:/Users/Nikita/IdeaProjects/look-it-app-image-server_v2/web";

            l = base64String.length();

            String base = base64String.substring(1, l - 1);
            byte[] resByteArray = Base64.decode(base);
            System.out.println("Buff img");
            BufferedImage resultImage = ImageIO.read(new ByteArrayInputStream(resByteArray));

            ImageIO.write(resultImage, "jpg", new File(directory, "resultImage.jpg"));

            String link = "http://g02.a.alicdn.com/kf/HTB13cogIVXXXXXiapXXq6xXFXXXF/Male-formal-shoes-2015-men-business-casual-leather-work-shoes-flats-daily-casual-leather-shoes-for.jpg";
            System.out.println("Start link");
            DelLink(link);

        }

        private static  void FileLink(String str_big_link) throws IOException {
            System.out.println(str_big_link);
            FileOutputStream fos = new FileOutputStream("C:/Users/Nikita/IdeaProjects/look-it-app-image-server_v2/web/BigLink.txt");
            fos.write(str_big_link.getBytes());
            fos.flush();
            fos.close();
        }

        private static void BackgroundDel()
        {
            String directory = "C:/Users/Nikita/Desktop/Java";

            String UrlLink = "";

            List<String> params = java.util.Arrays.asList("Path", "-arg1", "-arg2");
            ProcessBuilder b = new ProcessBuilder(params);
        }

        private static void DelLink(String link) throws IOException {
            ProcessBuilder procBuilder = new ProcessBuilder("C:/Users/Nikita/Desktop/parser/parser.exe ", link);

            procBuilder.redirectErrorStream(true);
            Process process = procBuilder.start();

            InputStream stdout = process.getInputStream();
            InputStreamReader isrStdout = new InputStreamReader(stdout);
            BufferedReader brStdout = new BufferedReader(isrStdout);

            String x = brStdout.readLine();
            ArrayList m = new ArrayList();
            String[] str = {"www.massimodutti", "www.zara", "www.zalando","www.merkal","www.uterque",
                    "www.bimbaylola", "shopsy", "www.casasclub", "www.vigilanceandlegalcell" ,"www.pablosky","svclassic",
                    "www.spartoo", "www.hispanitas","www.hammerstream","www.zacaris", "lamoda","www.goldenspikepub",
                    "pandao","ru.aliexpress", "www.aliexpress","nike", "adidas", "sport-marafon", "www.flaviasilvaweb",
                    "kinash","brandshop","asics","elegantlook","WildBerries", "reebok", "ru.puma",
                    "asos","sportmaster","outventure-outdoor","www.decathlon","www.basketshop","toddshelton","www.endclothing"};

            boolean f = false;
            for (String i : x.split(",")) {
                i = i.replace("[", "");
                i = i.replace("]", "");
                i = i.replace("'", "");
                i = i.replace(" ", "");

                for(int j = 0; j < str.length; j++) {
                    if ((i.startsWith(str[j], 8) == true)||(i.startsWith(str[j], 7) == true)) {
                        f = true;
                        break;
                    }

                }
                if(f)
                {
                    m.add(i);
                    f = false;
                }
            }

            String str_big_link = "";
            for(int i = 0; i < m.size(); i++) {
                str_big_link = str_big_link  + m.get(i);
                if(i != m.size()-1)
                {
                    str_big_link += ";";
                }
                System.out.println(m.get(i));
            }
            FileLink(str_big_link);
            System.out.println("All");

        }

    }


    public static class StaticHandler implements HttpHandler {

        private String routePath;
        private String fsPath;

        private Map<String, String> headers = new HashMap<String, String>(){{
            put("html", "text/html");
            put("css", "text/css");
            put("js", "text/javascript");
            put("json", "application/json");
            put("svg", "image/svg+xml");
        }};

        public StaticHandler(String path, String filesystemPath) {
            routePath = path;
            fsPath = filesystemPath;
        }

        @Override
        public void handle(HttpExchange http) throws IOException {
            OutputStream outputStream = http.getResponseBody();
            http.getRequestBody();
            String request = http.getRequestURI().getRawPath();
            byte[] result;
            int code;
            try {

                try {
                    String path = fsPath + request.substring(routePath.length());
                    System.out.println("requested: " + path);
                    result = read(new FileInputStream(path)).toByteArray();
                    String ext = request.substring(request.lastIndexOf(".") + 1);
                    if (headers.containsKey(ext))
                        http.getResponseHeaders().add("Content-Type", headers.get(ext));
                    code = 200;
                } catch (IOException e) {
                    result = (404 + " " + request).getBytes();
                    code = 404;
                }

            } catch (Exception e) {
                StringWriter sw = new StringWriter();
                e.printStackTrace(new PrintWriter(sw));
                result = sw.getBuffer().toString().getBytes();
                code = 500;
            }

            http.sendResponseHeaders(code, result.length);
            outputStream.write(result);
            outputStream.close();
        }

        static ByteArrayOutputStream read(InputStream is) throws IOException {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            int nRead;
            byte[] data = new byte[1024];
            while ((nRead = is.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }
            buffer.flush();
            return buffer;
        }

    }


}
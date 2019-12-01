package com.company;

import com.sun.net.httpserver.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import javax.imageio.ImageIO;

public class BasicHttpServerExample {

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        HttpContext context = server.createContext("/example");
        context.setHandler(BasicHttpServerExample::handleRequest);
        server.start();
    }

    private static void handleRequest(HttpExchange exchange) throws IOException {
        URI requestURI = exchange.getRequestURI();
        System.out.println(requestURI.getQuery());
        System.out.println(exchange.getRequestURI());
        Headers requestHeaders = exchange.getRequestHeaders();

        String response = "This is the response at " + requestURI;
        exchange.sendResponseHeaders(200, response.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();

        String base64String = requestHeaders.get("img").toString();
        String directory = "C:/Users/Nikita/Desktop/Java";
        int l = 0;
        l = base64String.length();

        String base = base64String.substring(1,l-1);
        System.out.println(base);
        byte[] resByteArray = Base64.decode(base);

        BufferedImage resultImage = ImageIO.read(new ByteArrayInputStream(resByteArray));

        System.out.println("1 "+resultImage);
        ImageIO.write(resultImage, "jpg", new File(directory,"resultImage.jpg"));
    }
}
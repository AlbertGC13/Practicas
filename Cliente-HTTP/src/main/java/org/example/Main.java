package org.example;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Ingrese la URL (Asegurese de que incluya el protocolo HTTP/HTTPS");
        String url = sc.nextLine();
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet(url);
        HttpResponse httpResponse = httpClient.execute(httpGet);

        String contentType = httpResponse.getEntity().getContentType().getValue();
        System.out.println("Tipo de recurso: " + contentType);

        try {
            if (contentType.equalsIgnoreCase("text/html; charset=utf-8") ) {
                Document doc = Jsoup.connect(url).get();
                String html = doc.html();
                System.out.println("Cantidad de lineas: " + html.split("\n").length);

                Elements parrafos = doc.select("p");
                System.out.println("Cantidad de parrafos (p): " + parrafos.size());

                int imageCount = 0;
                for (Element parrafo : parrafos) {
                    imageCount += parrafo.select("img").size();
                }
                System.out.println("Cantidad de imagenes en los parrafos: " + imageCount);

                Elements forms = doc.select("form");
                System.out.println("Cantidad de formularios (form): " + forms.size());

                int postCount = 0;
                int getCount = 0;
                for (Element form : forms) {
                    String method = form.attr("method");
                    if (method.equalsIgnoreCase("post")) {
                        postCount++;
                    } else if (method.equalsIgnoreCase("get")) {
                        getCount++;
                    }
                }
                System.out.println("Cantidad de formularios por metodo: ");
                System.out.println("POST: " + postCount);
                System.out.println("GET: " + getCount);

                for (Element form : forms) {
                    System.out.println("Campos de formulario:");
                    Elements inputs = form.select("input");
                    for (Element input : inputs) {
                        System.out.println("Tipo: " + input.attr("type"));
                    }
                }

                for (Element form : forms) {
                    String method = form.attr("method");
                    if (method.equalsIgnoreCase("post")) {
                        Document response = Jsoup.connect(url)
                                .data("asignatura", "practica1")
                                .header("matricula-id", "matricula o id asignado")
                                .post();
                        System.out.println("Respuesta de la peticion POST: " + response.body().text());
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
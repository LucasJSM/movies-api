package br.com.omdb.service;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import br.com.omdb.util.Configuracao;

public class OmdbService {
    private static final String API_BASE_URL = "https://www.omdbapi.com/";
    private final CloseableHttpClient httpClient = HttpClients.createDefault();

    public String buscarFilmePorTitulo(String titulo) throws IOException {
        if (titulo == null || titulo.trim().isEmpty()) {
            return "{\"Error\":\"Título não pode ser vazio.\"}";
        }

        String apiKey = Configuracao.getApiKey();
        String tituloEncode = URLEncoder.encode(titulo, StandardCharsets.UTF_8.name());
        String url = String.format("%s?apikey=%s&t=%s", API_BASE_URL, apiKey, tituloEncode);

        HttpGet request = new HttpGet(url);

        try (CloseableHttpResponse response = httpClient.execute(request)) {
            int statusCode = response.getStatusLine().getStatusCode();
            
            if (statusCode != 200) {
                
                System.err.println("Erro ao chamar a API OMDB. Status Code: " + statusCode);
                System.err.println("Resposta completa: " + EntityUtils.toString(response.getEntity()));
                
                return "{\"Error\":\"Falha na comunicação com a API OMDB.\"}";
            }
            
            HttpEntity entity = response.getEntity();
            return EntityUtils.toString(entity);
        }
    }
}
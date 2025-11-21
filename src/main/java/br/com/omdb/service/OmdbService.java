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

    public String buscarFilmes(String titulo) throws IOException {
    	String apiKey = Configuracao.getApiKey();
    	String tituloEncode = URLEncoder.encode(titulo, StandardCharsets.UTF_8.name());
    	String url = String.format("%s?apikey=%s&s=%s", API_BASE_URL, apiKey, tituloEncode);
    	return executarRequisicao(url);
    }
    
    public String buscarDetalhesPorId(String imdbId) throws IOException {
    	String apiKey = Configuracao.getApiKey();
    	String url = String.format("%s?apikey=%s&i=%s", API_BASE_URL, apiKey, imdbId);
    	return executarRequisicao(url);
    }
    
    public String executarRequisicao(String url) throws IOException {
    	HttpGet request = new HttpGet(url);
    	try (CloseableHttpResponse response = httpClient.execute(request)) {
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                return "{\"Error\":\"Falha na comunicação com a API OMDB. Código: " + statusCode + "\"}";
            }
            HttpEntity entity = response.getEntity();
            return EntityUtils.toString(entity);
        }
    }
}
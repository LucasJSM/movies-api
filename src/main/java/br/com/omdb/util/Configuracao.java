package br.com.omdb.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Configuracao {
    private static Properties props = new Properties();

    static {
        try (InputStream input = Configuracao.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                System.err.println("Arquivo config.properties não encontrado.");
            } else {
                props.load(input);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static String getApiKey() {
        return props.getProperty("omdb.api.key");
    }
}
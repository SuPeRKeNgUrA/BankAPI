package com.sberbank.bankapi.utils;

import com.sberbank.bankapi.BankApiApplicationTests;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

@Component
public class JSONParser {

    public static String parseJsonToString(String file) throws IOException, URISyntaxException {
        URL resource = BankApiApplicationTests.class.getClassLoader().getResource(file);
        assert resource != null;
        byte[] bytes = Files.readAllBytes(Paths.get(resource.toURI()));
        return new String(bytes);
    }

}

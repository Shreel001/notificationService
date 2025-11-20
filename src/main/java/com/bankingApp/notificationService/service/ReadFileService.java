package com.bankingApp.notificationService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class ReadFileService {

    @Autowired
    private ResourceLoader resourceLoader;

    public String readFileFromResource(String filename) throws IOException {

        Resource resource = resourceLoader.getResource("classpath:" + filename);

        InputStream inputStream = resource.getInputStream();

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        StringBuilder stringBuilder = new StringBuilder();
        String line;

        while((line = reader.readLine()) != null){
            stringBuilder.append(line).append(System.lineSeparator());
        }
        reader.close();

        return stringBuilder.toString();
    }

}

package com.example.MyAPP.config;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.youtube.YouTube;
import com.sun.jdi.connect.Transport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.security.GeneralSecurityException;

@Configuration
public class YoutubeConfig {

    @Bean
    public YouTube youtubeClient() throws GeneralSecurityException, IOException{
        NetHttpTransport transport=GoogleNetHttpTransport.newTrustedTransport();
        JsonFactory jsonFactory=GsonFactory.getDefaultInstance();
        return new YouTube.Builder(transport,jsonFactory,null)
                .setApplicationName("BhaktiApp")
                .build();
    }
}

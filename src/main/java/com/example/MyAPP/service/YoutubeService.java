package com.example.MyAPP.service;

import com.example.MyAPP.entity.Bhajan;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.*;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class YoutubeService {

    @Autowired
    private YouTube youtubeClient;

    @Value("${youtube.api.key}")
    private String youtubeApiKey;


    public Video getVideoDetails(String videoId) throws IOException {
        // 1. Build the list request (snippet and statistics)
        YouTube.Videos.List list= youtubeClient.videos().list(Arrays.asList("snippet", "statistics"));
        // 2. Set the ID (your videoId)
        list.setId(Collections.singletonList(videoId));
        // 3. Set the Key (your apiKey)
        list.setKey(youtubeApiKey);
        // 4. Execute the call
        VideoListResponse videoListResponse=list.execute();
        // 5. Check if items exist and return the first one
        List<Video> items = videoListResponse.getItems();
        if (items != null && !items.isEmpty()) {
            return items.get(0);
        }
        throw  new IOException("no results");

    }


    public List<SearchResult> searchThere(String query) {
        try{
            YouTube.Search.List searchRequest= youtubeClient.search().list(Collections.singletonList("snippet"))
                    .setQ(query)
                    .setKey(youtubeApiKey)
                    .setType(Collections.singletonList("video"))
                    .setMaxResults(5L);
            SearchListResponse response = searchRequest.execute();
            List<SearchResult> results = response.getItems();

            return results;

        }catch (IOException e){
            log.error("got an exception in youtube search",e);

        }
        return Collections.emptyList();

    }

}

package com.example.MyAPP.controller;

import com.example.MyAPP.entity.Bhajan;
import com.example.MyAPP.service.BhajanService;
import com.example.MyAPP.service.TrendingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/Trending")
public class TrendingController {

    private final TrendingService trendingService;

    public TrendingController(TrendingService trendingService) {
        this.trendingService = trendingService;
    }

    @GetMapping("/global")
    public List<Bhajan> getGlobalTrending() {
        return trendingService.getCommonTrending();
    }

    @GetMapping("/youtube")
    public List<Bhajan> getYouTubeTrending() {
        return trendingService.getYouTubeTrending();
    }

    @GetMapping("/local")
    public List<Bhajan> getLocalTrending() {
        return trendingService.getLocalTrending();
    }
}

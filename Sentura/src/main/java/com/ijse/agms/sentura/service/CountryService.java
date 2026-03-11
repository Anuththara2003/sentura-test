package com.ijse.agms.sentura.service;

import com.ijse.agms.sentura.dto.CountryDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class CountryService {
    private List<CountryDTO> cache = new ArrayList<>();
    private long lastFetchedTime = 0;
    private static final long CACHE_DURATION = 10 * 60 * 1000; // 10 minutes

    public List<CountryDTO> getAllCountries() {
        long currentTime = System.currentTimeMillis();

        if (cache.isEmpty() || (currentTime - lastFetchedTime) > CACHE_DURATION) {
            fetchFromApi();
        }
        return cache;
    }

    private void fetchFromApi() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://restcountries.com/v3.1/all";
        Object[] response = restTemplate.getForObject(url, Object[].class);

        this.cache = // process data...
                this.lastFetchedTime = System.currentTimeMillis();
    }
}
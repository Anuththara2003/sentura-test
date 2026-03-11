package com.ijse.agms.sentura.service;

import com.ijse.agms.sentura.dto.CountryDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CountryService {
    private List<CountryDTO> cache = new ArrayList<>();
    private long lastFetchedTime = 0;
    private static final long CACHE_DURATION = 10 * 60 * 1000;

    public List<CountryDTO> getAllCountries() {
        long currentTime = System.currentTimeMillis();

        if (cache.isEmpty() || (currentTime - lastFetchedTime) > CACHE_DURATION) {
            fetchFromApi();
        }
        return cache;
    }

    private void fetchFromApi() {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String url = "https://restcountries.com/v3.1/all?fields=name,capital,region,population,flags";
            List<Map<String, Object>> response = restTemplate.getForObject(url, List.class);

            if (response != null) {
                this.cache = response.stream().map(map -> {
                    CountryDTO dto = new CountryDTO();
                    try {

                        Map<String, Object> nameMap = (Map<String, Object>) map.get("name");
                        dto.setName(nameMap != null && nameMap.get("common") != null ? nameMap.get("common").toString() : "Unknown");


                        dto.setRegion(map.get("region") != null ? map.get("region").toString() : "N/A");


                        dto.setPopulation(map.get("population") != null ? Long.parseLong(map.get("population").toString()) : 0);


                        Map<String, Object> flagsMap = (Map<String, Object>) map.get("flags");
                        dto.setFlag(flagsMap != null && flagsMap.get("png") != null ? flagsMap.get("png").toString() : "");


                        List<String> capitals = (List<String>) map.get("capital");
                        dto.setCapital(capitals != null && !capitals.isEmpty() ? capitals.get(0) : "N/A");

                    } catch (Exception e) {

                    }
                    return dto;
                }).collect(Collectors.toList());

                this.lastFetchedTime = System.currentTimeMillis();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
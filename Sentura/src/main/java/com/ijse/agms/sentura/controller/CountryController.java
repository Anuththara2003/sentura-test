package com.ijse.agms.sentura.controller;

import com.ijse.agms.sentura.dto.CountryDTO;
import com.ijse.agms.sentura.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/countries")
@CrossOrigin(origins = "http://localhost:3000") // React port එක
public class CountryController {
    @Autowired
    private CountryService countryService;

    @GetMapping
    public List<CountryDTO> getCountries(@RequestParam(defaultValue = "") String search) {
        List<CountryDTO> all = countryService.getAllCountries();
        if (search == null || search.isEmpty()) {
            return all;
        }
        return all.stream()
                .filter(c -> c.getName() != null && c.getName().toLowerCase().contains(search.toLowerCase()))
                .collect(Collectors.toList());
    }
}
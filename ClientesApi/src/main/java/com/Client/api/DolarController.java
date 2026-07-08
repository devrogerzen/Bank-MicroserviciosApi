package com.Client.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dolar")
public class DolarController {

    private final DolarService dolarService;

    public DolarController(DolarService dolarService) {
        this.dolarService = dolarService;
    }

    @GetMapping("/oficial")
    public DolarResponse getDolarOficial() {
        return dolarService.obtenerDolarOficial();
    }

    @GetMapping("/mep")
    public DolarResponse getDolarMep() {
        return dolarService.obtenerDolarMep();
    }
}

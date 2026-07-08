package com.Client.api;

import org.springframework.stereotype.Service;

@Service
public class DolarService {

    private final DolarClient dolarClient;

    public DolarService(DolarClient dolarClient) {
        this.dolarClient = dolarClient;
    }

    public DolarResponse obtenerDolarOficial() {
        return dolarClient.getDolarOficial();
    }

    public DolarResponse obtenerDolarMep() {
        return dolarClient.getDolarMep();
    }
}

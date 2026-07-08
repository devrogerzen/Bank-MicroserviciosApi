package com.tarjetas.api.dolar;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "dolar-service", url = "${dolar.api.url}")
public interface DolarClient {

    @GetMapping("/oficial")
    Dolar getDolarOficial();

    @GetMapping("/mep")
    Dolar getDolarMep();
}

package com.Client.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "dolar-service", url = "${dolar.api.url}")
public interface DolarClient {

    @GetMapping("/oficial")
    DolarResponse getDolarOficial();

    @GetMapping("/bolsa")
    DolarResponse getDolarMep();
}

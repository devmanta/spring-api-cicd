package com.manta.demo.service;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@Component
public class TestService {

    public String getManta() {
        return "Hello I'm Manta";
    }
}

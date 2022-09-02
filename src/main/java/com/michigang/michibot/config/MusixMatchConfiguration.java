package com.michigang.michibot.config;


import com.michigang.michibot.apis.musixmatch.MichiMusixMatch;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MusixMatchConfiguration {

    @Value("${musixmatch.token}")
    private String token;

    @Bean
    public MichiMusixMatch musixMatch() {
        return new MichiMusixMatch(token);
    }

}
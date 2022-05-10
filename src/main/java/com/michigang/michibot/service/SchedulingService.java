package com.michigang.michibot.service;

import discord4j.core.GatewayDiscordClient;
import discord4j.core.object.presence.ClientActivity;
import discord4j.core.object.presence.ClientPresence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class SchedulingService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Value("${michibot.url}")
    private String michibotURL;

            @Autowired
    GatewayDiscordClient gatewayDiscordClient;

    // Prevent Heroku to set bot in sleep mode
    @Scheduled(fixedDelay = 300000L)
    private void pingSelf() {
        try {
            LOGGER.error(michibotURL);
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.getForEntity(michibotURL, String.class);

        } catch (HttpClientErrorException e) {
            try {
                LOGGER.error(String.valueOf(e.getStatusCode()));
                if (e.getStatusCode() != HttpStatus.NOT_FOUND)
                    throw e;
                boolean dateParityEven = (System.currentTimeMillis() / 60000) % 2 == 0;
                String varDingDong = dateParityEven ? "Ding" : "Dong";
                LOGGER.info("El michi respira: " + varDingDong);
                gatewayDiscordClient.updatePresence(
                        ClientPresence.online(ClientActivity.listening(varDingDong))).block();
            } catch (Exception e2) {
                errorMessage(e2);
            }
        } catch (Exception e) {
            errorMessage(e);
        }
    }

    private void errorMessage(Exception e){
        LOGGER.error("EL MICHI NO RESPIRA!");
        LOGGER.error(String.valueOf(e.getClass()));
        LOGGER.error(e.getMessage());
    }


}

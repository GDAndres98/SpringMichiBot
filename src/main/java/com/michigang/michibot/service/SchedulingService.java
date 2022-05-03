package com.michigang.michibot.service;

import discord4j.core.GatewayDiscordClient;
import discord4j.core.object.presence.ClientActivity;
import discord4j.core.object.presence.ClientPresence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class SchedulingService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    GatewayDiscordClient gatewayDiscordClient;

    // Prevent Heroku to set bot in sleep mode
    @Scheduled(fixedDelay = 300000L)
    private void pingSelf() {
        try {
            boolean dateParityEven = (System.currentTimeMillis() / 60000) % 2 == 0;
            String varDingDong = dateParityEven ? "Ding" : "Dong";
            LOGGER.info("El michi respira: " + varDingDong);
            gatewayDiscordClient.updatePresence(
                    ClientPresence.online(ClientActivity.listening(varDingDong))).block();
        } catch (Exception e) {
            LOGGER.error("EL MICHI NO RESPIRA!");
        }
    }


}

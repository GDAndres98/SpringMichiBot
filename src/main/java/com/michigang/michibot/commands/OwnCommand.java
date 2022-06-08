package com.michigang.michibot.commands;

import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Component
public class OwnCommand implements SlashCommand {
    public final String CAT_API_URL = "https://api.thecatapi.com/v1/images/search";

    @Override
    public String getName() {
        return "own";
    }

    @Override
    public Mono<Void> handle(ChatInputInteractionEvent event) {
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<CatAPIResponse[]> responseCatAPI = restTemplate.getForEntity(CAT_API_URL, CatAPIResponse[].class);
        String catImageURL = Objects.requireNonNull(responseCatAPI.getBody())[0].getUrl();

        return event.reply()
                .withContent(catImageURL);
    }

    @Data
    static class CatAPIResponse {
        private String id;
        private String url;
    }
}
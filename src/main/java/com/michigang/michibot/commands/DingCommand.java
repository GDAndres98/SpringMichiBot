package com.michigang.michibot.commands;

import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class DingCommand implements SlashCommand {
    @Override
    public String getName() {
        return "ding";
    }

    @Override
    public Mono<Void> handle(ChatInputInteractionEvent event) {
        return event.reply()
//                .withEphemeral(true) para que solamente el command user vea el mensaje
                .withContent("Dong!");
    }
}
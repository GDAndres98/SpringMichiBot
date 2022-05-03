package com.michigang.michibot.commands;

import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import discord4j.core.object.command.ApplicationCommandInteractionOption;
import discord4j.core.object.command.ApplicationCommandInteractionOptionValue;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class OffendCommand implements SlashCommand {
    @Override
    public String getName() {
        return "offend";
    }

    @Override
    public Mono<Void> handle(ChatInputInteractionEvent event) {
        String hijueputa = event.getOption("hijueputa")
                .flatMap(ApplicationCommandInteractionOption::getValue)
                .map(ApplicationCommandInteractionOptionValue::asString)
                .get();

        return event.reply()
                .withContent(hijueputa + " es un perro hijueputa xd");
    }
}
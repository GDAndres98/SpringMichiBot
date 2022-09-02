package com.michigang.michibot.commands;

import com.michigang.michibot.apis.musixmatch.MichiMusixMatch;
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import discord4j.core.object.command.ApplicationCommandInteractionOption;
import discord4j.core.object.command.ApplicationCommandInteractionOptionValue;
import org.jmusixmatch.entity.lyrics.Lyrics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class LyricsCommand implements SlashCommand {

    @Autowired
    private MichiMusixMatch musixMatch;

    @Override
    public String getName() {
        return "lyrics";
    }

    @Override
    public Mono<Void> handle(ChatInputInteractionEvent event) {
        String cancion = event.getOption("cancion")
                .flatMap(ApplicationCommandInteractionOption::getValue)
                .map(ApplicationCommandInteractionOptionValue::asString)
                .get();

        String artista = event.getOption("artista")
                .flatMap(ApplicationCommandInteractionOption::getValue)
                .map(ApplicationCommandInteractionOptionValue::asString)
                .orElse("%");

        try {
            Lyrics lyrics = musixMatch.getMatchingLyrics(cancion, artista);
            return event.reply(lyrics.getLyricsBody() + "\n " +
                    "Para ver letra completa pague: https://buymeacoffee.com/gdandres98");
        } catch (Exception e) {
            return event.reply().withContent("Perdón, soy manco: " + e.getLocalizedMessage());
        }
    }
}
package com.michigang.michibot.apis.musixmatch;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import org.jmusixmatch.Helper;
import org.jmusixmatch.MusixMatch;
import org.jmusixmatch.MusixMatchException;
import org.jmusixmatch.config.Constants;
import org.jmusixmatch.entity.lyrics.Lyrics;
import org.jmusixmatch.entity.lyrics.get.LyricsGetMessage;
import org.jmusixmatch.http.MusixMatchRequest;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class MichiMusixMatch extends MusixMatch {

    private final String MATCHER_LYRICS_GET = "matcher.lyrics.get";

    /**
     * A musiXmatch API Key.
     */
    private final String apiKey;

    /**
     * MichiMusixMatch Constructor with API-Key.
     *
     * @param apiKey A musiXmatch API Key.
     */
    public MichiMusixMatch(String apiKey) {
        super(apiKey);
        this.apiKey = apiKey;
    }


    public Lyrics getMatchingLyrics(String q_track, String q_artist)
            throws MusixMatchException, InvocationTargetException, IllegalAccessException {
        Lyrics lyrics = null;
        Map<String, Object> params = new HashMap<String, Object>();
        LyricsGetMessage message = null;

        params.put(Constants.API_KEY, apiKey);
        params.put(Constants.QUERY_TRACK, q_track);
        params.put(Constants.QUERY_ARTIST, q_artist);

        String response = null;

        response = MusixMatchRequest.sendRequest(Helper.getURLString(
                MATCHER_LYRICS_GET, params));
        System.out.println(response);

        Gson gson = new Gson();

        try {
            message = gson.fromJson(response, LyricsGetMessage.class);
        } catch (JsonParseException jpe) {
            Method[] superMethods = super.getClass().getDeclaredMethods();
            for (Method method : superMethods)
                if (method.getName().equals("handleErrorResponse")) {
                    method.setAccessible(true);
                    method.invoke(this, response);
                }
        }

        if (message == null)
            throw new MusixMatchException("No encontré ni mierda");

        lyrics = message.getContainer().getBody().getLyrics();

        return lyrics;
    }


}

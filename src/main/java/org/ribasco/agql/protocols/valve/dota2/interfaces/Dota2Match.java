package org.ribasco.agql.protocols.valve.dota2.interfaces;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.ribasco.agql.protocols.valve.dota2.Dota2WebApiInterface;
import org.ribasco.agql.protocols.valve.dota2.adapters.Dota2TeamInfoAdapter;
import org.ribasco.agql.protocols.valve.dota2.exceptions.Dota2WebException;
import org.ribasco.agql.protocols.valve.dota2.interfaces.match.*;
import org.ribasco.agql.protocols.valve.dota2.pojos.*;
import org.ribasco.agql.protocols.valve.steam.webapi.SteamWebApiClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class Dota2Match extends Dota2WebApiInterface {

    private static final Logger log = LoggerFactory.getLogger(Dota2Match.class);

    public Dota2Match(SteamWebApiClient client) {
        super(client);
    }

    @Override
    protected void configureBuilder(GsonBuilder builder) {
        log.info("Registering Type Adapter");
        builder.registerTypeAdapter(Dota2MatchTeamInfo.class, new Dota2TeamInfoAdapter());
    }

    public CompletableFuture<List<Dota2League>> getLeagueListing() {
        CompletableFuture<JsonObject> json = sendRequest(new GetLeagueListing(VERSION_1));
        return json.thenApply(r -> asListOf(Dota2League.class, "leagues", r));
    }

    public CompletableFuture<List<Dota2LiveLeagueGame>> getLiveLeagueGames() {
        return getLiveLeagueGames(-1, -1);
    }

    public CompletableFuture<List<Dota2LiveLeagueGame>> getLiveLeagueGames(int leagueId, int matchId) {
        CompletableFuture<JsonObject> json = sendRequest(new GetLiveLeagueGames(VERSION_1, leagueId, matchId));
        return json.thenApply(r -> asListOf(Dota2LiveLeagueGame.class, "games", r));
    }

    public CompletableFuture<List<Dota2TopLiveGame>> getTopLiveGame(int partner) {
        CompletableFuture<JsonObject> json = sendRequest(new GetTopLiveGame(VERSION_1, partner));
        return json.thenApply(r -> asListOf(Dota2TopLiveGame.class, "game_list", r));
    }

    public CompletableFuture<Dota2MatchDetails> getMatchDetails(long matchId) {
        CompletableFuture<JsonObject> json = sendRequest(new GetMatchDetails(VERSION_1, matchId));
        return json.thenApply(r -> fromJson(getValidResult(r), Dota2MatchDetails.class));
    }

    public CompletableFuture<Dota2MatchHistory> getMatchHistory() {
        return getMatchHistory(null);
    }

    public CompletableFuture<Dota2MatchHistory> getMatchHistory(Dota2MatchHistoryCriteria criteria) {
        CompletableFuture<JsonObject> json = sendRequest(new GetMatchHistory(VERSION_1, criteria));
        return json.thenApply(r -> fromJson(getValidResult(r), Dota2MatchHistory.class));
    }

    public CompletableFuture<List<Dota2MatchDetails>> getMatchHistoryBySequenceNum(int startSeqNum, int matchesRequested) {
        CompletableFuture<JsonObject> json = sendRequest(new GetMatchHistoryBySequenceNum(VERSION_1, startSeqNum, matchesRequested));
        return json.thenApply(r -> asListOf(Dota2MatchDetails.class, "matches", r));
    }

    public CompletableFuture<List<Dota2MatchTeamInfo>> getTeamInfoById(int startTeamId, int maxTeamCount) {
        CompletableFuture<JsonObject> json = sendRequest(new GetTeamInfoByTeamID(VERSION_1, startTeamId, maxTeamCount));
        return json.thenApply(r -> asListOf(Dota2MatchTeamInfo.class, "teams", r));
    }

    //TODO: No test data available
    public CompletableFuture<Object> getTopWeekendTourneyGames() {
        throw new Dota2WebException("Not implemented");
    }

    //TODO: No test data available due to not having the right set of arguments
    public CompletableFuture<Object> getTournamentPlayerStats() {
        throw new Dota2WebException("Not implemented");
    }

    //TODO: Should still implement this? See http://dev.dota2.com/showthread.php?t=186998
    public CompletableFuture<Object> getScheduledLeagueGames() {
        throw new Dota2WebException("Not implemented");
    }
}
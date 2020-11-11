package mmbn.discord.bot.dto;

public class TournamentDto {

    private String api_key;

    private TournamentDetailDto tournament;

    private ParticipantDto participant;

    public String getApi_key() {
        return api_key;
    }

    public void setApi_key(String api_key) {
        this.api_key = api_key;
    }

    public TournamentDetailDto getTournament() {
        return tournament;
    }

    public void setTournament(TournamentDetailDto tournament) {
        this.tournament = tournament;
    }

    public ParticipantDto getParticipant() {
        return participant;
    }

    public void setParticipant(ParticipantDto participant) {
        this.participant = participant;
    }
}

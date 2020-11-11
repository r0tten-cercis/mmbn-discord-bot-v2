package mmbn.discord.bot.dto;

public class ParticipantDto {

    private String name;

    private String challonge_username;

    private String email;

    private String seed;

    private String misc;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getChallonge_username() {
        return challonge_username;
    }

    public void setChallonge_username(String challonge_username) {
        this.challonge_username = challonge_username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSeed() {
        return seed;
    }

    public void setSeed(String seed) {
        this.seed = seed;
    }

    public String getMisc() {
        return misc;
    }

    public void setMisc(String misc) {
        this.misc = misc;
    }
}

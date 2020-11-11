package mmbn.discord.bot.entity;

/**
 * トーナメント参加者情報Entityクラス
 */
public class ParticipantEntity {

    /** id */
    private Long id;

    /** 名前 */
    private String name;

    /** discord.id */
    private String discord_id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDiscord_id() {
        return discord_id;
    }

    public void setDiscord_id(String discord_id) {
        this.discord_id = discord_id;
    }
}

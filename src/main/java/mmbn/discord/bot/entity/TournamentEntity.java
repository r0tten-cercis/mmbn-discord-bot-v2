package mmbn.discord.bot.entity;

import java.util.List;

/**
 * トーナメント情報Entityクラス
 */
public class TournamentEntity {

    /** url */
    private String url;

    /** 参加者リスト */
    private List<ParticipantEntity> participants;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<ParticipantEntity> getParticipants() {
        return participants;
    }

    public void setParticipants(List<ParticipantEntity> participants) {
        this.participants = participants;
    }
}

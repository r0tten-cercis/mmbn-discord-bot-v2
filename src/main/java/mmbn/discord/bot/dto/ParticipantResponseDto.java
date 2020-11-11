package mmbn.discord.bot.dto;

public class ParticipantResponseDto {

    private ParticipantDetailResponseDto participant;

    public ParticipantDetailResponseDto getParticipant() {
        return participant;
    }

    public void setParticipant(ParticipantDetailResponseDto participant) {
        this.participant = participant;
    }
}

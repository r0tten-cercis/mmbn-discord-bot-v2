package mmbn.discord.bot.command.api;

import com.google.gson.Gson;
import mmbn.discord.bot.command.Command;
import mmbn.discord.bot.dto.ParticipantDto;
import mmbn.discord.bot.dto.ParticipantResponseDto;
import mmbn.discord.bot.dto.TournamentDto;
import mmbn.discord.bot.entity.ParticipantEntity;
import mmbn.discord.bot.entity.TournamentEntity;
import mmbn.discord.bot.util.JsonUtil;
import mmbn.discord.bot.util.PropertyUtil;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * joinコマンドクラス
 */
public class Join extends Command {

    /** log */
    private static final Logger log = LoggerFactory.getLogger(Join.class);

    /** 取得メッセージ */
    private final String[] args;

    /**
     * コンストラクタ
     *
     * @param event JDA GuildMessageReceivedEvent
     * @param args  command option
     */
    public Join(GuildMessageReceivedEvent event, String[] args) {
        super(event);
        this.args = args;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute() {

        if (args.length < 2) {
            EmbedBuilder eb = new EmbedBuilder();
            eb.setColor(Color.RED);
            eb.setTitle("エラー");
            eb.setDescription("参加名を入力してください。");
            sendEmbed(eb.build());
            return;
        }

        try {
            // json取得
            String filePath = "data/tournament.json";
            String json = JsonUtil.readJson(filePath);

            Gson gson = new Gson();
            TournamentEntity tournament = gson.fromJson(json, TournamentEntity.class);

            if (tournament == null) {
                EmbedBuilder eb = new EmbedBuilder();
                eb.setColor(Color.RED);
                eb.setTitle("エラー");
                eb.setDescription("トーナメントが登録されていません。");
                sendEmbed(eb.build());
                return;
            }

            if (tournament.getParticipants() != null) {
                for (ParticipantEntity participant : tournament.getParticipants()) {
                    if (participant.getName().equals(args[1])) {
                        EmbedBuilder eb = new EmbedBuilder();
                        eb.setColor(Color.RED);
                        eb.setTitle("エラー");
                        eb.setDescription("同名の参加者が既に存在しています。");
                        sendEmbed(eb.build());
                        return;
                    }
                    if (participant.getDiscord_id().equals(event.getAuthor().getId())) {
                        EmbedBuilder eb = new EmbedBuilder();
                        eb.setColor(Color.RED);
                        eb.setTitle("エラー");
                        eb.setDescription("既に参加申請が行われています。");
                        sendEmbed(eb.build());
                        return;
                    }
                }
            }

            final String URL = "https://api.challonge.com/v1/tournaments/" + tournament.getUrl() + "/participants.json";

            String apiKey = PropertyUtil.getProperty("api_key");

            // DTO作成
            ParticipantDto participantDto = new ParticipantDto();
            participantDto.setName(args[1]);

            TournamentDto dto = new TournamentDto();
            dto.setApi_key(apiKey);
            dto.setParticipant(participantDto);

            // POST
            RequestBody requestBody = RequestBody.create(JSON, gson.toJson(dto));
            Request request = new Request.Builder().url(URL).post(requestBody).build();
            OkHttpClient okHttpClient = new OkHttpClient.Builder().build();

            try (Response response = okHttpClient.newCall(request).execute()) {
                if (!response.isSuccessful() || response.body() == null) {
                    log.error("response code:" + response.code());
                    EmbedBuilder eb = new EmbedBuilder();
                    eb.setColor(Color.RED);
                    eb.setTitle("エラー");
                    eb.setDescription("参加申請でエラーが発生しました。");
                    sendEmbed(eb.build());
                    return;
                }

                ParticipantResponseDto responseDto = gson.fromJson(response.body().string(), ParticipantResponseDto.class);

                if (tournament.getParticipants() == null) {
                    List<ParticipantEntity> participantList = new ArrayList<>();
                    ParticipantEntity entity = new ParticipantEntity();
                    entity.setId(responseDto.getParticipant().getId());
                    entity.setName(args[1]);
                    entity.setDiscord_id(event.getAuthor().getId());
                    participantList.add(entity);
                    tournament.setParticipants(participantList);
                } else {
                    ParticipantEntity entity = new ParticipantEntity();
                    entity.setId(responseDto.getParticipant().getId());
                    entity.setName(args[1]);
                    entity.setDiscord_id(event.getAuthor().getId());
                    tournament.getParticipants().add(entity);
                }

                JsonUtil.writeJson(gson.toJson(tournament), filePath);

                EmbedBuilder eb = new EmbedBuilder();
                eb.setColor(Color.GREEN);
                eb.setTitle("申請完了");
                eb.setDescription(args[1] + "さんの参加申請を承りました。");
                sendEmbed(eb.build());
            }

        } catch (Exception e) {
            log.error("", e);
            sendMessage("> エラーが発生しました。");
        }
    }
}

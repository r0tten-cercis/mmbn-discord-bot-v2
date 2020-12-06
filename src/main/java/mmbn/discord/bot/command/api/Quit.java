package mmbn.discord.bot.command.api;

import com.google.gson.Gson;
import mmbn.discord.bot.command.Command;
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
import java.util.List;

/**
 * quitコマンドクラス
 */
public class Quit extends Command {

    /** log */
    private static final Logger log = LoggerFactory.getLogger(Quit.class);

    /**
     * コンストラクタ
     *
     * @param event JDA GuildMessageReceivedEvent
     */
    public Quit(GuildMessageReceivedEvent event) {
        super(event);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute() {

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

            Long participantId = null;
            boolean isExist = false;
            int i = 0;

            if (tournament.getParticipants() != null) {
                for (ParticipantEntity participant : tournament.getParticipants()) {
                    if (participant.getDiscord_id().equals(event.getAuthor().getId())) {
                        participantId = participant.getId();
                        isExist = true;
                        break;
                    }
                    i++;
                }
            }

            if (!isExist) {
                EmbedBuilder eb = new EmbedBuilder();
                eb.setColor(Color.RED);
                eb.setTitle("エラー");
                eb.setDescription("参加申請が行われていません。");
                sendEmbed(eb.build());
                return;
            }

            final String URL = "https://api.challonge.com/v1/tournaments/" + tournament.getUrl() + "/participants/" + participantId.toString() + ".json";

            String apiKey = PropertyUtil.getProperty("api_key");

            TournamentDto dto = new TournamentDto();
            dto.setApi_key(apiKey);

            // DELETE
            RequestBody requestBody = RequestBody.create(JSON, gson.toJson(dto));
            Request request = new Request.Builder().url(URL).delete(requestBody).build();
            OkHttpClient okHttpClient = new OkHttpClient.Builder().build();

            try (Response response = okHttpClient.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    log.error("response code:" + response.code());
                    EmbedBuilder eb = new EmbedBuilder();
                    eb.setColor(Color.RED);
                    eb.setTitle("エラー");
                    eb.setDescription("参加申請取り消しでエラーが発生しました。");
                    sendEmbed(eb.build());
                    return;
                }

                List<ParticipantEntity> participantList = tournament.getParticipants();
                participantList.remove(i);
                tournament.setParticipants(participantList);

                JsonUtil.writeJson(gson.toJson(tournament), filePath);

                EmbedBuilder eb = new EmbedBuilder();
                eb.setColor(Color.GREEN);
                eb.setTitle("参加申請取り消し");
                eb.setDescription("参加申請を取り消しました。");
                sendEmbed(eb.build());
            }

        } catch (Exception e) {
            log.error("", e);
            sendMessage("> エラーが発生しました。");
        }
    }
}

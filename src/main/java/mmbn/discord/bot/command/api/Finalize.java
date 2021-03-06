package mmbn.discord.bot.command.api;

import com.google.gson.Gson;
import mmbn.discord.bot.command.Command;
import mmbn.discord.bot.dto.TournamentDto;
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

/**
 * finalizeコマンドクラス
 */
public class Finalize extends Command {

    /** log */
    private static final Logger log = LoggerFactory.getLogger(Finalize.class);

    /**
     * コンストラクタ
     *
     * @param event JDA GuildMessageReceivedEvent
     */
    public Finalize(GuildMessageReceivedEvent event) {
        super(event);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute() {

        if (!isAdmin()) {
            EmbedBuilder eb = new EmbedBuilder();
            eb.setColor(Color.RED);
            eb.setTitle("エラー");
            eb.setDescription("管理者のみ使用可能なコマンドです。");
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

            final String URL = "https://api.challonge.com/v1/tournaments/" + tournament.getUrl() + "/finalize.json";

            String apiKey = PropertyUtil.getProperty("api_key");

            // DTO作成
            TournamentDto dto = new TournamentDto();
            dto.setApi_key(apiKey);

            // POST
            RequestBody requestBody = RequestBody.create(JSON, gson.toJson(dto));
            Request request = new Request.Builder().url(URL).post(requestBody).build();
            OkHttpClient okHttpClient = new OkHttpClient.Builder().build();

            try (Response response = okHttpClient.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    log.error("response code:" + response.code());
                    EmbedBuilder eb = new EmbedBuilder();
                    eb.setColor(Color.RED);
                    eb.setTitle("エラー");
                    eb.setDescription("トーナメント終了でエラーが発生しました。");
                    sendEmbed(eb.build());
                    return;
                }

                JsonUtil.writeJson("", filePath);

                EmbedBuilder eb = new EmbedBuilder();
                eb.setColor(Color.GREEN);
                eb.setTitle("トーナメント終了");
                eb.setDescription("トーナメントを終了しました。");
                eb.addField("URL", "https://challonge.com/" + tournament.getUrl(), false);
                sendEmbed(eb.build());
            }

        } catch (Exception e) {
            log.error("", e);
            sendMessage("> エラーが発生しました。");
        }
    }
}

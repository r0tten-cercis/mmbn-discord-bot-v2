package mmbn.discord.bot.command.api;

import com.google.gson.Gson;
import mmbn.discord.bot.command.Command;
import mmbn.discord.bot.dto.TournamentDto;
import mmbn.discord.bot.entity.TournamentEntity;
import mmbn.discord.bot.util.JsonUtil;
import mmbn.discord.bot.util.PropertyUtil;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * deleteコマンドクラス
 */
public class Delete extends Command {

    /** log */
    private static final Logger log = LoggerFactory.getLogger(Delete.class);

    /**
     * コンストラクタ
     *
     * @param event JDA GuildMessageReceivedEvent
     */
    public Delete(GuildMessageReceivedEvent event) {
        super(event);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute() {

        if (!isAdmin()) {
            sendMessage("> 管理者のみ使用可能なコマンドです。");
            return;
        }

        try {
            // json取得
            String filePath = "data/tournament.json";
            String json = JsonUtil.readJson(filePath);

            Gson gson = new Gson();
            TournamentEntity tournament = gson.fromJson(json, TournamentEntity.class);

            if (tournament == null) {
                sendMessage("> トーナメントが登録されていません。");
                return;
            }

            final String URL = "https://api.challonge.com/v1/tournaments/" + tournament.getUrl() + ".json";

            String apiKey = PropertyUtil.getProperty("api_key");

            // DTO作成
            TournamentDto dto = new TournamentDto();
            dto.setApi_key(apiKey);

            // DELETE
            RequestBody requestBody = RequestBody.create(JSON, gson.toJson(dto));
            Request request = new Request.Builder().url(URL).delete(requestBody).build();
            OkHttpClient okHttpClient = new OkHttpClient.Builder().build();

            try (Response response = okHttpClient.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    log.error("response code:" + response.code());
                    sendMessage("> トーナメント削除でエラーが発生しました。");
                    return;
                }

                JsonUtil.writeJson("", filePath);

                sendMessage("> トーナメントを削除しました。");
            }

        } catch (Exception e) {
            log.error("", e);
            sendMessage("> エラーが発生しました。");
        }
    }
}

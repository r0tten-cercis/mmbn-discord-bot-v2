package mmbn.discord.bot.command.api;

import com.google.gson.Gson;
import mmbn.discord.bot.command.Command;
import mmbn.discord.bot.dto.TournamentDetailDto;
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
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * createコマンドクラス
 */
public class Create extends Command {

    /** log */
    private static final Logger log = LoggerFactory.getLogger(Create.class);

    /** 取得メッセージ */
    private final String[] args;

    /**
     * コンストラクタ
     *
     * @param event JDA GuildMessageReceivedEvent
     * @param args  command option
     */
    public Create(GuildMessageReceivedEvent event, String[] args) {
        super(event);
        this.args = args;
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

        if (args.length < 2) {
            EmbedBuilder eb = new EmbedBuilder();
            eb.setColor(Color.RED);
            eb.setTitle("エラー");
            eb.setDescription("トーナメント名を入力してください。");
            sendEmbed(eb.build());
            return;
        }

        try {
            // json取得
            String filePath = "data/tournament.json";
            String json = JsonUtil.readJson(filePath);

            Gson gson = new Gson();
            TournamentEntity tournament = gson.fromJson(json, TournamentEntity.class);

            if (tournament != null) {
                EmbedBuilder eb = new EmbedBuilder();
                eb.setColor(Color.RED);
                eb.setTitle("エラー");
                eb.setDescription("既にトーナメントが登録されています。");
                sendEmbed(eb.build());
                return;
            }

            final String URL = "https://api.challonge.com/v1/tournaments.json";

            String apiKey = PropertyUtil.getProperty("api_key");

            // DTO作成
            TournamentDetailDto detailDto = new TournamentDetailDto();
            detailDto.setName(args[1].replace("-", " "));
            Date date = new Date();
            SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHHmm");
            String tournamentUrl = PropertyUtil.getProperty("tournament_url") + fmt.format(date);
            detailDto.setUrl(tournamentUrl);

            // トーナメント形式の設定
            if (args.length > 2) {
                switch (args[2]) {
                    case "single" :
                        detailDto.setTournament_type("single elimination");
                        detailDto.setHold_third_place_match(true);
                        break;

                    case "double" :
                        detailDto.setTournament_type("double elimination");
                        break;

                    case "round" :
                        detailDto.setTournament_type("round robin");
                        detailDto.setRanked_by("points difference");
                        break;

                    case "swiss" :
                        detailDto.setTournament_type("swiss");
                        break;
                }
            } else {
                // 形式が設定されていない場合はシングルエリミネーションとする
                detailDto.setTournament_type("single elimination");
                detailDto.setHold_third_place_match(true);
            }

            TournamentDto dto = new TournamentDto();
            dto.setApi_key(apiKey);
            dto.setTournament(detailDto);

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
                    eb.setDescription("トーナメント作成でエラーが発生しました。");
                    sendEmbed(eb.build());
                    return;
                }

                tournament = new TournamentEntity();
                tournament.setUrl(tournamentUrl);

                JsonUtil.writeJson(gson.toJson(tournament), filePath);

                EmbedBuilder eb = new EmbedBuilder();
                eb.setColor(Color.GREEN);
                eb.setTitle("トーナメント作成完了");
                eb.setDescription("参加希望者は**?join**コマンドで参加申請を行ってください。");
                eb.addField("大会名", dto.getTournament().getName(), false);
                eb.addField("URL", "https://challonge.com/" + tournamentUrl, false);
                sendEmbed(eb.build());
            }

        } catch (Exception e) {
            log.error("", e);
            sendMessage("> エラーが発生しました。");
        }
    }
}

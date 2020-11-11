package mmbn.discord.bot.command.api;

import mmbn.discord.bot.command.Command;
import mmbn.discord.bot.util.JsonUtil;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * clearコマンドクラス
 */
public class Clear extends Command {

    /** log */
    private static final Logger log = LoggerFactory.getLogger(Clear.class);

    /**
     * コンストラクタ
     *
     * @param event JDA GuildMessageReceivedEvent
     */
    public Clear(GuildMessageReceivedEvent event) {
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
            JsonUtil.writeJson("", filePath);
            sendMessage("> トーナメントを削除しました。");

        } catch (Exception e) {
            log.error("", e);
            sendMessage("> エラーが発生しました。");
        }
    }
}

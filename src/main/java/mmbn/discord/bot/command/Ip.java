package mmbn.discord.bot.command;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mmbn.discord.bot.entity.UserEntity;
import mmbn.discord.bot.util.JsonUtil;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

/**
 * ipコマンドクラス
 */
public class Ip extends Command {

    /** log */
    private static final Logger log = LoggerFactory.getLogger(Ip.class);

    /**
     * エラーメッセージ送信有無
     */
    private final boolean isErrorSend;

    /**
     * コンストラクタ
     *
     * @param event       JDA GuildMessageReceivedEvent
     * @param isErrorSend 登録IPが存在しない場合のエラーメッセージ送信有無
     */
    public Ip(GuildMessageReceivedEvent event, boolean isErrorSend) {
        super(event);
        this.isErrorSend = isErrorSend;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute() {
        try {
            // json取得
            String filePath = "data/user.json";
            String json = JsonUtil.readJson(filePath);

            List<UserEntity> list = JsonUtil.jsonToEntityList(json);

            String id = event.getAuthor().getId();

            if (list != null) {
                for (UserEntity entity : list) {
                    if (entity.getId().equals(id)) {
                        // user_idが一致した場合、登録されたIPを送信
                        String msg = "> " + entity.getIpAddr() + "\r" +
                                "> 登録日時 : " + entity.getRegistDate();
                        sendMessage(msg);
                        return;
                    }
                }
            }

            if (isErrorSend) {
                String sb = "> IPアドレスが登録されていません。\r" +
                        "> 下記サイトからグローバルIPアドレスを確認し、**?regist**コマンドで登録を行ってください。\r" +
                        "> https://www.cman.jp/network/support/go_access.cgi";
                sendMessage(sb);
            }

        } catch (Exception e) {
            log.error("", e);
            sendMessage("> エラーが発生しました。");
        }
    }
}

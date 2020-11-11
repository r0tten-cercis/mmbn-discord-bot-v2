package mmbn.discord.bot.command;

import java.net.ConnectException;
import java.net.Socket;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mmbn.discord.bot.entity.UserEntity;
import mmbn.discord.bot.util.JsonUtil;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

/**
 * port_checkコマンドクラス
 */
public class PortCheck extends Command {

    /** log */
    private static final Logger log = LoggerFactory.getLogger(PortCheck.class);

    /**
     * 取得メッセージ
     */
    private final String[] args;

    /**
     * コンストラクタ
     *
     * @param event JDA GuildMessageReceivedEvent
     * @param args  command option
     */
    public PortCheck(GuildMessageReceivedEvent event, String[] args) {
        super(event);
        this.args = args;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute() {

        Socket socket;
        String ipAddress = null;
        int port = 5738;

        try {
            // 接続先ポートを指定
            if (args.length > 1) {
                port = Integer.parseInt(args[1]);
            }

            // json取得
            String filePath = "data/user.json";
            String json = JsonUtil.readJson(filePath);

            List<UserEntity> list = JsonUtil.jsonToEntityList(json);

            String id = event.getAuthor().getId();

            if (list != null) {
                for (UserEntity entity : list) {
                    if (entity.getId().equals(id)) {
                        ipAddress = entity.getIpAddr();
                    }
                }
            }

            if (ipAddress == null) {
                String sb = "> IPアドレスが登録されていません。\r" +
                        "> 下記サイトからグローバルIPアドレスを確認し、**?regist**コマンドで登録を行ってください。\r" +
                        "> https://www.cman.jp/network/support/go_access.cgi";
                sendMessage(sb);
                return;
            }

            socket = new Socket(ipAddress, port);

            String msg = "> " + ipAddress + "のポート" + port + "は解放されています。";
            sendMessage(msg);

            socket.close();

        } catch (ConnectException e) {
            String msg = "> " + ipAddress + "のポート" + port + "は解放されていません。";
            sendMessage(msg);
        } catch (NumberFormatException e) {
            sendMessage("ポート番号は半角数字で入力してください。");
        } catch (Exception e) {
            log.error("", e);
            sendMessage("> エラーが発生しました。");
        }
    }
}

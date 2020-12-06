package mmbn.discord.bot.command;

import java.awt.*;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.List;

import net.dv8tion.jda.api.EmbedBuilder;
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

        Socket socket = new Socket();
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
                EmbedBuilder eb = new EmbedBuilder();
                eb.setColor(Color.RED);
                eb.setTitle("IPアドレスが登録されていません。");
                eb.setDescription("下記サイトからグローバルIPアドレスを確認し\n**?regist**コマンドで登録を行ってください。");
                eb.addField("URL", "https://www.cman.jp/network/support/go_access.cgi", false);
                sendEmbed(eb.build());
                return;
            }

            InetSocketAddress endPoint = new InetSocketAddress(ipAddress, port);
            socket.connect(endPoint, 1000);

            EmbedBuilder eb = new EmbedBuilder();
            eb.setColor(Color.GREEN);
            eb.setTitle("接続成功");
            eb.setDescription("接続テストに成功しました。");
            eb.addField("IPアドレス", ipAddress, true);
            eb.addField("ポート番号", String.valueOf(port), true);
            sendEmbed(eb.build());

            socket.close();

        } catch (SocketTimeoutException e) {
            EmbedBuilder eb = new EmbedBuilder();
            eb.setColor(Color.RED);
            eb.setTitle("接続失敗");
            eb.setDescription("接続テストに失敗しました。");
            eb.addField("IPアドレス", ipAddress, true);
            eb.addField("ポート番号", String.valueOf(port), true);
            sendEmbed(eb.build());

        } catch (NumberFormatException e) {
            EmbedBuilder eb = new EmbedBuilder();
            eb.setColor(Color.RED);
            eb.setTitle("エラー");
            eb.setDescription("ポート番号は半角数字で入力してください。");
            sendEmbed(eb.build());

        } catch (Exception e) {
            log.error("", e);
            sendMessage("> エラーが発生しました。");
        }
    }
}

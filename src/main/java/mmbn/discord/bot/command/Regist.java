package mmbn.discord.bot.command;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import mmbn.discord.bot.entity.UserEntity;
import mmbn.discord.bot.util.JsonUtil;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

/** registコマンドクラス */
public class Regist extends Command {

    /** log */
    private static final Logger log = LoggerFactory.getLogger(Regist.class);

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
    public Regist(GuildMessageReceivedEvent event, String[] args) {
        super(event);
        this.args = args;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute() {
        try {
            if (args.length < 2) {
                sendMessage("> 登録するIPアドレスが入力されていません。");
                return;
            }

            // json取得
            String filePath = "data/user.json";
            String json = JsonUtil.readJson(filePath);

            List<UserEntity> list = JsonUtil.jsonToEntityList(json);

            String id = event.getAuthor().getId();
            String ip = args[1];

            // IPアドレス形式チェック
            if (!ip.matches("(([1-9]?[0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([1-9]?[0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])")) {
                sendMessage("> IPアドレスの形式が間違っています。");
                return;
            }

            // TODO: 2020/11/02 プライベートIPを弾くようにする
            // 10.0.0.0 ~ 10.255.255.255
            // 172.16.0.0 ~ 172.31.255.255
            // 192.168.0.0 ~ 192.168.255.255

//            if (ip.matches("(^127\\.)|(^169\\.254\\.)|(^10\\.)|(^172\\.1[6-9]\\.)|(^172\\.2[0-9]\\.)|(^172\\.3[0-1]\\.)|(^192\\.168\\.)")) {
//                sendMessage("> IPアドレスの形式がプライベートIPアドレスになっています。");
//                return;
//            }

            boolean isRegist = false;

            if (list != null) {
                int i = 0;
                for (UserEntity entity : list) {
                    if (entity.getId().equals(id)) {
                        entity.setIpAddr(ip);
                        entity.setRegistDate(getDate());
                        list.set(i, entity);
                        isRegist = true;
                        break;
                    }
                    i++;
                }
            } else {
                list = new ArrayList<>();
            }

            if (!isRegist) {
                UserEntity entity = new UserEntity();
                entity.setId(id);
                entity.setIpAddr(ip);
                entity.setRegistDate(getDate());
                list.add(entity);
            }

            Gson gson = new Gson();
            JsonUtil.writeJson(gson.toJson(list), filePath);

            sendMessage("> 登録が完了しました。");

        } catch (Exception e) {
            log.error("", e);
            sendMessage("> エラーが発生しました。");
        }
    }

    /**
     * 現在日を文字列で取得
     *
     * @return Date -> String format[yyyy/mm/dd HH:mm]
     */
    private String getDate() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        return dateFormat.format(date);
    }
}

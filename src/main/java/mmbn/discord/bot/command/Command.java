package mmbn.discord.bot.command;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import okhttp3.MediaType;

import java.util.List;
import java.util.Objects;

/**
 * コマンド抽象クラス
 */
public abstract class Command {

    /** event */
    protected GuildMessageReceivedEvent event;

    /** json */
    protected static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    /**
     * コンストラクタ
     *
     * @param event JDA GuildMessageReceivedEvent
     */
    public Command(GuildMessageReceivedEvent event) {
        this.event = event;
    }

    /**
     * コマンドを実行
     */
    abstract public void execute();

    /**
     * メッセージを送信
     *
     * @param msg 送信メッセージ
     */
    protected void sendMessage(String msg) {
        event.getChannel().sendMessage(msg).queue();
    }

    /**
     * 埋め込みメッセージを送信
     *
     * @param msg 送信埋め込みメッセージ
     */
    protected void sendEmbed(MessageEmbed msg) {
        event.getChannel().sendMessage(msg).queue();
    }

    /**
     * ユーザが管理者権限を所持しているか判定する
     *
     * @return 管理者権限を所持している場合true
     */
    protected boolean isAdmin() {
        try {
            List<Role> roleList = Objects.requireNonNull(event.getMember()).getRoles();
            for (Role role : roleList) {
                if (role.hasPermission(Permission.ADMINISTRATOR)) {
                    return true;
                }
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }
}

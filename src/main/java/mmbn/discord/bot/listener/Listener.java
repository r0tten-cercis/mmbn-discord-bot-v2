package mmbn.discord.bot.listener;

import mmbn.discord.bot.command.Command;
import mmbn.discord.bot.command.Ip;
import mmbn.discord.bot.command.PortCheck;
import mmbn.discord.bot.command.Regist;
import mmbn.discord.bot.command.api.Clear;
import mmbn.discord.bot.command.api.Create;
import mmbn.discord.bot.command.api.Delete;
import mmbn.discord.bot.command.api.Finalize;
import mmbn.discord.bot.command.api.Join;
import mmbn.discord.bot.command.api.Quit;
import mmbn.discord.bot.command.api.Randomize;
import mmbn.discord.bot.command.api.Start;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/**
 * listener class
 */
public class Listener extends ListenerAdapter {

    /** コマンド接頭辞 */
    private static final String PREFIX = "?";

    /**
     * {@inheritDoc}
     */
    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        Command command = null;

        switch (args[0]) {
            case PREFIX + "ip":
                command = new Ip(event, true);
                break;

            case PREFIX + "regist":
                command = new Regist(event, args);
                break;

            case PREFIX + "port_check":
                command = new PortCheck(event, args);
                break;

            case PREFIX + "create":
                command = new Create(event, args);
                break;

            case PREFIX + "delete":
                command = new Delete(event);
                break;

            case PREFIX + "join":
                command = new Join(event, args);
                break;

            case PREFIX + "quit":
                command = new Quit(event);
                break;

            case PREFIX + "randomize":
                command = new Randomize(event);
                break;

            case PREFIX + "start":
                command = new Start(event);
                break;

            case PREFIX + "finalize":
                command = new Finalize(event);
                break;

            case PREFIX + "clear":
                command = new Clear(event);
                break;

            default:
                break;
        }

        // 末尾が募集で終わる場合はIPコマンドと同様の処理を行う
        if (command == null && event.getMessage().getContentRaw().endsWith("募集")) {
            command = new Ip(event, false);
        }

        // 対応したコマンドを実行
        if (command != null) {
            command.execute();
        }
    }
}

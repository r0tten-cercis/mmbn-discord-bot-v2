package mmbn.discord.bot;

import mmbn.discord.bot.listener.Listener;
import mmbn.discord.bot.util.PropertyUtil;
import net.dv8tion.jda.api.JDABuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * main class
 */
public class MmbnDiscordBot {

    /** log */
    private static final Logger log = LoggerFactory.getLogger(MmbnDiscordBot.class);

    /**
     * main method
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {

        // 文字コード:UTF-8
        System.setProperty("file.encoding", "UTF-8");

        try {
            // トークンを取得
            String token = PropertyUtil.getProperty("token");

            // bot起動
            JDABuilder.createDefault(token).addEventListeners(new Listener()).build();

        } catch (Exception e) {
            log.error("", e);
        }
    }
}

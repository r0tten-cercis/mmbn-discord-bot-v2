package mmbn.discord.bot.util;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * プロパティファイル読み込みクラス
 */
public class PropertyUtil {

    /** log */
    private static final Logger log = LoggerFactory.getLogger(PropertyUtil.class);

    /** filepath */
    private static final String INIT_FILE_PATH = "data/common.properties";

    /** property */
    private static final Properties properties;

    static {
        properties = new Properties();
        try {
            properties.load(Files.newBufferedReader(Paths.get(INIT_FILE_PATH), StandardCharsets.UTF_8));
        } catch (Exception e) {
            log.error("", e);
        }
    }

    /**
     * プロパティ値を取得
     *
     * @param key key
     * @return value
     */
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}

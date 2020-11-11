package mmbn.discord.bot.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import mmbn.discord.bot.entity.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.List;

/**
 * 共通処理クラス
 */
public class JsonUtil {

    /** log */
    private static final Logger log = LoggerFactory.getLogger(JsonUtil.class);

    /**
     * jsonファイルを読み込んで文字列を返却
     *
     * @param filePath jsonファイルパス
     * @return json文字列
     * @throws Exception json読み込みエラー
     */
    public static String readJson(String filePath) throws Exception {

        StringBuilder sb = new StringBuilder();

        try (FileInputStream fi = new FileInputStream(filePath);
             InputStreamReader in = new InputStreamReader(fi);
             BufferedReader br = new BufferedReader(in)) {

            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (Exception e) {
            log.error("json読み込み時にエラー発生", e);
            throw e;
        }

        return sb.toString();
    }

    /**
     * jsonファイルに書き込みを行う
     *
     * @param json     書き込み内容
     * @param filePath jsonファイルパス
     * @throws Exception json書き込みエラー
     */
    public static void writeJson(String json, String filePath) throws Exception {

        File f = new File(filePath);

        try (FileWriter fw = new FileWriter(f, false);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter pw = new PrintWriter(bw)) {

            pw.write(json);
        } catch (Exception e) {
            log.error("json書き込み時にエラー発生", e);
            throw e;
        }
    }

    /**
     * jsonをEntityListにして返却
     *
     * @param json json文字列
     * @return jsonEntity
     */
    public static List<UserEntity> jsonToEntityList(String json) {

        List<UserEntity> list;

        Gson gson = new Gson();
        Type listType = new TypeToken<List<UserEntity>>() {
        }.getType();

        list = gson.fromJson(json, listType);

        return list;
    }
}

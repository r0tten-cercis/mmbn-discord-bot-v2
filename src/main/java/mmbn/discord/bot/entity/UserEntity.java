package mmbn.discord.bot.entity;

/**
 * discordユーザ情報Entityクラス
 */
public class UserEntity {

    /** author.id */
    private String id;

    /** グローバルIPアドレス */
    private String ipAddr;

    /** 登録日 */
    private String registDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIpAddr() {
        return ipAddr;
    }

    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }

    public String getRegistDate() {
        return registDate;
    }

    public void setRegistDate(String registDate) {
        this.registDate = registDate;
    }
}

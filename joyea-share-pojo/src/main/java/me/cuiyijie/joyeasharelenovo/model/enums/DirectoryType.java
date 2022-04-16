package me.cuiyijie.joyeasharelenovo.model.enums;

/**
 * @author cyj976655@gmail.com
 * @date 2022/4/16 18:22
 */
public enum DirectoryType {

    SELF(1, "自己"),
    LENOVO(1, "联想晚盘");

    public Integer getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    private Integer key;
    private String value;

    DirectoryType(Integer key, String value) {
        this.key = key;
        this.value = value;
    }
}

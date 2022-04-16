package me.cuiyijie.joyeasharelenovo.enums;

/**
 * @author cyj976655@gmail.com
 * @date 2022/4/16 18:13
 */
public enum TranscodeVideoStatus {

    NEW(1,"未转码"),
    LOADING(2,"转码中"),
    LOADED(3,"已转码")
    ;

    public Integer getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    private Integer key;
    private String value;

    TranscodeVideoStatus(Integer key,String value) {
        this.key = key;
        this.value = value;
    }

}

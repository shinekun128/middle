package cn.ponyzhang.api.enums;

public enum StatusCode {

    SUCCESS(0,"成功"),
    FAIL(-1,"失败"),
    InvalidParams(201,"无效的参数"),
    InvalidGrantType(202,"非法的授权类型");

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    private Integer code;
    private String msg;

    StatusCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}

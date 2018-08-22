package org.demo.imstargram.school.model;

public enum ResultCode {
    SAVE_SUCCESS("100", "success", "저장에 성공했습니다."),
    MODIFY_SUCCESS("101","success","수정에 성공했습니다."),
    REMOVE_SUCCESS("102","success","삭제에 성공했습니다."),
    UNKNOWN_FAIL("200", "fail", "알수없는 이유로 실패했습니다."),
    INVALID("201", "fail", "입력값이 유효하지 않습니다"),
    ALREADY_EXIST("203","fail", "이미 같은 정보가 존재합니다");


    ResultCode(String code, String result, String msg) {
        this.code = code;
        this.msg = msg;
        this.result = result;
    }

    private String code;
    private String msg;
    private String result;

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public String getResult() {
        return result;
    }
}

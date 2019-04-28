package com.skm.exa.common.exception;

import com.skm.exa.common.enums.Msg;
import com.skm.exa.common.object.Result;

import java.util.List;

/**
 * @author dhc
 * 2019-03-10 13:21
 */
public class BizException extends RuntimeException {
    private int code;
    private String message;
    private List<String> messages;
    private Object content;

    public BizException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public BizException(Msg msg) {
        super(msg.message);
        this.code = msg.code();
        this.message = msg.message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getMessages() {
        return messages;
    }

    public BizException setMessages(List<String> messages) {
        this.messages = messages;
        return this;
    }

    public Object getContent() {
        return content;
    }

    public BizException setContent(Object content) {
        this.content = content;
        return this;
    }

    public Result toResult() {
        return Result.error(code, message).setContent(content);
    }
}

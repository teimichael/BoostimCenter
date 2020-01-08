package stu.napls.boostim.core.response;

import lombok.Data;
import stu.napls.boostim.core.dictionary.ResponseCode;

import java.io.Serializable;

/**
 * @Author Tei Michael
 * @Date 12/29/2019
 */
@Data
public class Response implements Serializable {

    private int code;
    private String message;
    private Object data;

    private Response(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static Response success(Object data) {
        return new Response(ResponseCode.SUCCESS, "ok", data);
    }

    public static Response success(String message) {
        return new Response(ResponseCode.SUCCESS, message, null);
    }

    public static Response success(String message, Object data) {
        return new Response(ResponseCode.SUCCESS, message, data);
    }

    public static Response failure(int code, String message) {
        return new Response(code, message, null);
    }

    public static Response failure(String message) {
        return failure(ResponseCode.FAILURE, message);
    }

    public String toString() {
        return "{code:\"" + code + "\", message:\"" + message + "\", data:\"" + (data != null ? data.toString() : null) + "\"}";
    }

}


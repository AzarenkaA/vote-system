package com.azarenka.votingsystem.to;

/**
 * Response message.
 * <p>
 * (c) ant-azarenko@mail.ru 2020
 * </p>
 *
 * @author Anton Azarenka
 * Date 22.11.2020
 */
public class ResponseMessage {

    private String message;

    /**
     * Constructor.
     *
     * @param message message
     */
    public ResponseMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

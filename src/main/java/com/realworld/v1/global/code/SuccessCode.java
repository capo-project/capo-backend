package com.realworld.v1.global.code;

import lombok.Getter;

@Getter
public enum SuccessCode {
    /**
     * Success CodeList
     */
    SELECT_SUCCESS(200, "200","SELECT SUCCESS"),
    DELETE_SUCCESS(200,"200","DELETE SUCCESS"),
    INSERT_SUCCESS(201,"201","INSERT SUCCESS"),
    UPDATE_SUCCESS(204,"204","UPDATE SUCCESS")
    ; // End

    /**
     * Success Code Constructor
     */
    private final int status;
    private final String code;
    private final String message;
    SuccessCode(int status, String code, String message){
        this.status = status;
        this.code = code;
        this.message = message;
    }


}

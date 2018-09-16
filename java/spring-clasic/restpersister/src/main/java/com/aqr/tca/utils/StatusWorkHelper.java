package com.aqr.tca.utils;

public class StatusWorkHelper {

    public static StatusWork buildOkStatus(String id) {
        StringBuilder builder = new StringBuilder();
        return new StatusWork(0, builder.append("ok ").append(id).toString());
    }

    public static StatusWork buildErrorStatus(String id) {
        StringBuilder builder = new StringBuilder();
        return new StatusWork(1, builder.append("error ").append(id).toString());
    }

    public static boolean isOkStatus(StatusWork status) {
        return (status.getCode().intValue()==0);
    }

    public static boolean isErrorStatus(StatusWork status) {
        return !isOkStatus(status);
    }

}

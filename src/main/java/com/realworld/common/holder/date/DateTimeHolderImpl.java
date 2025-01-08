package com.realworld.common.holder.date;

import java.time.LocalDateTime;

public class DateTimeHolderImpl implements DateTimeHolder {

    @Override
    public LocalDateTime generate() {
        return LocalDateTime.now();
    }

}

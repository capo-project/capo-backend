package com.realworld.common.holder.date;

import java.time.LocalDateTime;

@FunctionalInterface
public interface DateTimeHolder {

    LocalDateTime generate();

}

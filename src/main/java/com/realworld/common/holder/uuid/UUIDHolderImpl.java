package com.realworld.common.holder.uuid;

import java.util.UUID;

public class UUIDHolderImpl implements UUIDHolder {

    @Override
    public String generate() {
        return UUID.randomUUID().toString();
    }

}

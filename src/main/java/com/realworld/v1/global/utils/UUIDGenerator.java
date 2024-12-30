package com.realworld.v1.global.utils;

import com.fasterxml.uuid.Generators;

import java.util.UUID;

public class UUIDGenerator {

    static public UUID createUUIDV2() {
        UUID uuidV1 = Generators.timeBasedGenerator().generate();

        String[] uuidV1Parts = uuidV1.toString().split("-");
        String sequentialUUID = uuidV1Parts[2] + "-" + uuidV1Parts[1] + "-" + uuidV1Parts[0] + "-" + uuidV1Parts[3] + "-" + uuidV1Parts[4];

        return UUID.fromString(sequentialUUID);
    }
}

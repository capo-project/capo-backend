package com.realworld.common.type.file;

import lombok.Getter;

@Getter
public enum FileFormat {

    IMAGE("image/", ".");

    private final String prefix;
    private final String extensionSeparator;

    FileFormat(String prefix, String extensionSeparator) {
        this.prefix = prefix;
        this.extensionSeparator = extensionSeparator;
    }

    public String generateContentType(String extension) {
        return prefix + extension.toLowerCase();
    }

    public String generateFileName(String uuid, String extension) {
        return uuid + extensionSeparator + extension;
    }

}

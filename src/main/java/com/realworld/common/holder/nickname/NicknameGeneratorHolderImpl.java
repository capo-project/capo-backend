package com.realworld.common.holder.nickname;

import java.util.Random;

import static com.realworld.common.holder.nickname.Nickname.*;


public class NicknameGeneratorHolderImpl implements NicknameGeneratorHolder{

    private final Random random = new Random();

    @Override
    public String generate() {
        return prefix[random.nextInt(prefix.length)]+" "+names[(random.nextInt(names.length))];
    }

}

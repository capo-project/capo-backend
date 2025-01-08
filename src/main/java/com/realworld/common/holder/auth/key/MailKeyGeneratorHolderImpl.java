package com.realworld.common.holder.auth.key;

import java.util.Random;

public class MailKeyGeneratorHolderImpl implements MailKeyGeneratorHolder{

    @Override
    public String generate() {
        StringBuilder key = new StringBuilder();
        Random rnd = new Random();

        for (int i = 0; i < 8; i++) {
            int index = rnd.nextInt(3); // 0~2 까지 랜덤

            switch (index) {
                case 0:
                    key.append((char) (rnd.nextInt(26) + 97));
                    // a~z (ex. 1+97 = 98 => (char)98 = 'b')
                    break;
                case 1:
                    key.append((char) (rnd.nextInt(26) + 65));
                    // A~Z
                    break;
                case 2:
                    key.append((rnd.nextInt(10)));
                    break;
            }
        }
        return key.toString().toLowerCase();
    }

}

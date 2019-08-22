package io.github.oxnz.Ingrid.avatar;

import org.junit.Test;

public class AvatarTest {

    @Test
    public void toStringT() {
        Avatar avatar = new Avatar(128, "alt");
        System.out.println(avatar.toString());
    }
}
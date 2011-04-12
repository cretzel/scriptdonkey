package com.github.scriptdonkey.web.util;

import com.google.appengine.api.users.User;

public class Users {

    public static boolean equal(final User user1, final User user2) {
        if (user1 == null && user2 == null) {
            return false;
        }
        if (user1 == null || user2 == null) {
            return false;
        }
        final String id1 = user1.getNickname();
        final String id2 = user2.getNickname();
        return id1.equals(id2);
    }
}

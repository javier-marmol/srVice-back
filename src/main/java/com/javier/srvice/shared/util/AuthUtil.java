package com.javier.srvice.shared.util;

import com.javier.srvice.security.domain.User;

public class AuthUtil {
    public static void checkAuth(User user, String emailAuth) throws Exception {
        if(user.getEmail()!= emailAuth) throw new Exception("You are not logged in as that user");
    }
}

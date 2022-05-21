package com.javier.srvice.shared.util;

import com.javier.srvice.security.domain.Rol;
import com.javier.srvice.security.domain.User;
import com.javier.srvice.shared.enums.RolName;

import java.util.List;
import java.util.Set;

public class AuthUtil {
    public static void checkAuth(User user, String emailAuth) throws Exception {
        if(!user.getEmail().equals(emailAuth) && !userAdmin(user)) throw new Exception("You are not logged in as that user");
    }
    private static Boolean userAdmin(User user){
        Set<Rol> roles = user.getRols();
        if (roles.stream().anyMatch(rol -> rol.getRolName().equals(RolName.ROLE_ADMIN))) return true;
        return false;
    }
}

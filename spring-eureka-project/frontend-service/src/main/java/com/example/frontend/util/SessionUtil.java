package com.example.frontend.util;

import javax.servlet.http.HttpSession;

public class SessionUtil {

    public static void setUser(HttpSession session,
                               String role,
                               Integer userId,
                               String userName,
                               String jwtToken) {
        session.setAttribute("role", role);
        session.setAttribute("userId", userId);
        session.setAttribute("userName", userName);
        session.setAttribute("jwtToken", jwtToken);
    }

    public static void setUser(HttpSession session,
                               String role,
                               Integer userId,
                               String userName) {
        setUser(session, role, userId, userName, null);
        System.out.println("DEBUG: setting session user = " + userName);
    }
    

    public static String getUserName(HttpSession session) {
        Object o = session.getAttribute("userName");
        System.out.println("DEBUG: getUserName from session = " + o);
        return o != null ? o.toString() : null;
    }


    public static Integer getUserId(HttpSession session) {
        Object obj = session.getAttribute("userId");
        if (obj instanceof Integer) {
            return (Integer) obj;
        }
        return null;
    }

    public static String getJwtToken(HttpSession session) {
        Object obj = session.getAttribute("jwtToken");
        return (obj != null) ? obj.toString() : null;
    }

    public static void setJwtToken(HttpSession session, String jwtToken) {
        session.setAttribute("jwtToken", jwtToken);
    }

    public static void clear(HttpSession session) {
        session.invalidate();
    }
}

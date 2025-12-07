package com.example.frontend.util;

import javax.servlet.http.HttpSession; 

public class SessionUtil {

    public static void setUser(HttpSession session,
                               String role,
                               Integer userId,
                               String userName) {
        session.setAttribute("role", role);
        session.setAttribute("userId", userId);
        session.setAttribute("userName", userName);
    }

    public static String getUserName(HttpSession session) {
        Object obj = session.getAttribute("userName");
        return (obj != null) ? obj.toString() : null;
    }

    public static Integer getUserId(HttpSession session) {
        Object obj = session.getAttribute("userId");
        if (obj instanceof Integer) {
            return (Integer) obj;
        }
        return null;
    }

    public static void clear(HttpSession session) {
        session.invalidate();
    }
}

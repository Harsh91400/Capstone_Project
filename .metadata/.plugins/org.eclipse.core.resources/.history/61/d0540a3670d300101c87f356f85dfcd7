package com.example.frontend.util;

import javax.servlet.http.HttpSession;

public class SessionUtil {
    public static void setUser(HttpSession session, String role, Integer id, String userName) {
        session.setAttribute("role", role);
        session.setAttribute("userId", id);
        session.setAttribute("userName", userName);
    }
    public static void clear(HttpSession session) {
        session.invalidate();
    }
}

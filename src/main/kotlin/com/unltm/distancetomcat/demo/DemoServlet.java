package com.unltm.distancetomcat.demo;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "DemoServlet", value = "/DemoServlet")
public class DemoServlet extends HttpServlet {
    private final List<User> list = new ArrayList<>();
    private static final String KEY_ID = "id";
    private static final String KEY_USERNAME = "username";


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        int id = Integer.parseInt(request.getParameter(KEY_ID));
        String username = request.getParameter(KEY_USERNAME);
        User user = new User(username, id);
        if (list.contains(user)) {
            response.getWriter().append("已存在");
        } else {
            list.add(user);
            response.getWriter().append(new Gson().toJson(list));
        }
    }
}

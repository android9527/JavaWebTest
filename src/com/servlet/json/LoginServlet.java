package com.servlet.json;

import com.servlet.BaseServlet;
import com.servlet.json.entity.User;
import com.servlet.json.entity.UserModel;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by chenfeiyue on 16/8/31.
 * LoginServlet
 */
public class LoginServlet extends BaseServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
        ServletOutputStream outputStream = resp.getOutputStream();
        String userName = req.getParameter("username");
        String password = req.getParameter("password");
        User user = new User();
        if (StringUtils.isEmpty(userName)) {
            user.setError("用户名不能为空!");
            outputStream.write(gson.toJson(user).getBytes("UTF-8"));
            return;
        }
        if (StringUtils.isEmpty(password)) {
            user.setError("密码不能为空!");
            outputStream.write(gson.toJson(user).getBytes("UTF-8"));
            return;
        }
        user = Sqlite3Util.login(userName, password);
        if (user != null) {
            user.setError("");
            outputStream.write(gson.toJson(user).getBytes("UTF-8"));
        } else {
            user = new User();
            user.setError("密码错误!");
            outputStream.write(gson.toJson(user).getBytes("UTF-8"));
        }
    }
}

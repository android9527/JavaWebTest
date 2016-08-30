package com.servlet.test;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Request对象实现请求转发
 */
public class RequestDemo06 extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String data = "大家好，我是孤傲苍狼，我正在总结JavaWeb";
        /**
         * 将数据存放到request对象中,此时把request对象当作一个Map容器来使用
         */
        request.setAttribute("data", data);
        //客户端访问RequestDemo06这个Servlet后，RequestDemo06通知服务器将请求转发(forward)到test.jsp页面进行处理
        request.getRequestDispatcher("/AdminLTE-master/index.html").forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
package com.servlet;

import com.google.gson.Gson;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by chenfeiyue on 16/8/30.
 * BaseServlet
 */
public class BaseServlet extends HttpServlet {

    public static Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        addHeader(response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        addHeader(response);

    }


    private void addHeader(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS, PUT, PATCH, DELETE");
        response.setHeader("Access-Control-Allow-Headers", "X-Requested-With,content-type");
        response.setHeader("Access-Control-Allow-Credentials", "true");
    }
}

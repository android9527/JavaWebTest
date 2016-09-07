package com.servlet.json;

import com.servlet.BaseServlet;
import com.servlet.json.entity.AvgData;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by chenfeiyue on 16/9/2.
 * GetAvgDataServlet
 */
public class GetAvgDataServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request, response);
        long hourTimeMillis = 60 * 60 * 1000;
        ServletOutputStream outputStream = response.getOutputStream();
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH");
            String curr = format.format(new Date());
            Date date = format.parse(curr);
            long currStartTime = date.getTime() - hourTimeMillis;
            long currEndTime = currStartTime - hourTimeMillis;

            curr = format.format(currStartTime);
            curr = format.format(currEndTime);

            AvgData data = Sqlite3Util.getAvgData(1, currStartTime, currEndTime);
            outputStream.write(gson.toJson(data).getBytes("UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

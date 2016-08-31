package com.servlet.json;

import com.servlet.BaseServlet;
import com.servlet.json.entity.PMEntity;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by chenfeiyue on 16/8/31.
 * PostPmServlet
 */
public class PostPmServlet extends BaseServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);

        String userId = req.getParameter("user_id");
        String value = req.getParameter("value"); // Pm value
        String other = req.getParameter("other");
        String temperature = req.getParameter("temperature");
        String humidity = req.getParameter("humidity");
        PMEntity entity = new PMEntity();
        if (StringUtils.isEmpty(userId))
            userId = "1";
        entity.setUserId(Integer.parseInt(userId));
        entity.setValue(value);
        entity.setOther(other);
        entity.setTime(System.currentTimeMillis() + "");
        entity.setTemperature(temperature);
        entity.setHumidity(humidity);
        boolean result = Sqlite3Util.insertPMEntity(entity);
        ServletOutputStream outputStream = resp.getOutputStream();
        outputStream.write(("{\"result\": " + result + " }").getBytes("UTF-8"));
    }
}

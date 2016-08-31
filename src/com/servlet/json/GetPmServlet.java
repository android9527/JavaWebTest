package com.servlet.json;

import com.servlet.BaseServlet;
import com.servlet.json.entity.PMEntity;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetPmServlet extends BaseServlet {

    private static final long serialVersionUID = 1L;

    public GetPmServlet() {
        super();
    }

    public void destroy() {
        super.destroy();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        super.doGet(request, response);
        doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        super.doPost(request, response);
        getData(request, response);
    }

    public void getData(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            ServletOutputStream outputStream = response.getOutputStream();
            String id = request.getParameter("id");
            String user_id = request.getParameter("user_id");
            if (StringUtils.isEmpty(user_id)) {
                user_id = "1";
            }
            ArrayList<PMEntity> entities = new ArrayList<>();
            if (StringUtils.isEmpty(id) || id.equals("0")) {
                entities.addAll(Sqlite3Util.getLastPMEntity(Integer.parseInt(user_id)));
            } else {
                entities.addAll(Sqlite3Util.getBeforePMEntity(Integer.parseInt(id), Integer.parseInt(user_id)));
            }
            outputStream.write(gson.toJson(entities).getBytes("UTF-8"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void init() throws ServletException {
    }
}

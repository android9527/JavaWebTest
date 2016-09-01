package com.servlet.json;

import com.servlet.BaseServlet;
import com.servlet.json.entity.PMEntity;
import com.servlet.json.entity.PMModel;

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
        getData(request, response);
    }

    public void getData(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            String draw = request.getParameter("draw");
            String start = request.getParameter("start");
            String length = request.getParameter("length");
//            String id = request.getParameter("id");
            String user_id = request.getParameter("user_id");
            if (StringUtils.isEmpty(user_id)) {
                user_id = "1";
            }

            int userId = Integer.parseInt(user_id);
            int total = Sqlite3Util.getPMCount(userId);
            PMModel model = new PMModel();
            model.setDraw(draw);
            model.setRecordsFiltered(total);
            model.setRecordsTotal(total);
            ArrayList<PMEntity> entities = new ArrayList<>();
            ServletOutputStream outputStream = response.getOutputStream();
            if (total <= 0) {
                model.setEntities(entities);
                outputStream.write(gson.toJson(model).getBytes("UTF-8"));
                return;
            }
            if (StringUtils.isEmpty(draw)) {
                draw = "1";
            }
            if (StringUtils.isEmpty(start)) {
                start = "0";
            }
            if (StringUtils.isEmpty(length)) {
                length = "10";
            }
            entities.addAll(Sqlite3Util.getBeforePMEntity(Integer.parseInt(draw), Integer.parseInt(start), Integer.parseInt(length), userId));
            model.setEntities(entities);
            outputStream.write(gson.toJson(model).getBytes("UTF-8"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

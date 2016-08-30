package com.servlet.json;

import com.servlet.BaseServlet;
import com.servlet.json.entity.PMEntity;
import com.servlet.json.entity.UserModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JSONServlet extends BaseServlet {

    private static final long serialVersionUID = 1L;

    public JSONServlet() {
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
        //使用JSONArray测试
//        JSONArray jsonArray = new JSONArray();
//        jsonArray.add("MCA");
//        jsonArray.add("kevin");
//        jsonArray.add("15-12-1998");
//        jsonArray.add(new Double(12.3));
//        List<String> list = new ArrayList<String>();
//        list.add("a collection added");
//        list.add("kevin collection test");
//        jsonArray.addAll(list);
//
//        //页面输出JSONArray的内容
//        PrintWriter out = response.getWriter();
//        out.print(jsonArray);
//        out.println("======================================");
//        for (int i = 0; i < jsonArray.size(); i++) {
//            out.print(jsonArray.getString(i));
//        }


        test(request, response);
    }

    public void test(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserModel model = new UserModel();
        model.setAge("19");
        model.setUserName("test水电费");
        String result = gson.toJson(model);
        //页面输出JSONArray的内容
//        PrintWriter out = response.getWriter();
//        out.print(result);
        ServletOutputStream outputStream = response.getOutputStream();
//        outputStream.write(result.getBytes("UTF-8"));


//        try {
////            Order order = Sqlite3Util.get(1);
//            ArrayList<Order> orders = Sqlite3Util.getAllOrder();
//            outputStream.write(new Gson().toJson(orders).getBytes("UTF-8"));
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        try {
            ArrayList<PMEntity> entities = Sqlite3Util.getAllPMEntity(1);
            entities.addAll(entities);
            entities.addAll(entities);
            entities.addAll(entities);
            entities.addAll(entities);
            outputStream.write(gson.toJson(entities).getBytes("UTF-8"));
        } catch (SQLException e) {
            e.printStackTrace();
        }


//        response.setContentType("text/json; charset=UTF-8");
    }

    public void init() throws ServletException {
    }
}

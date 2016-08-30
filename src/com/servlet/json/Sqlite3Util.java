package com.servlet.json;

import com.servlet.json.entity.Order;
import com.servlet.json.entity.PMEntity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;
import java.util.ArrayList;

public class Sqlite3Util {

    public static Connection connection;

    public static Connection getConnection() {

        if (connection != null) {
            return connection;
        }
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + Constant.DB_NAME);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            connection = null;
        }

        return connection;
    }

    private static final String savesql = "insert into Orders(Customer, OrderDate, OrderPrice) values (?, ?, ?);";

    private static final String updatesql = "update t_idioms set idiom=?, description=?, level=?, image=?, difficulty=?, pinyin=?, sortOrder=? where id=?";

    private static final String GET_ALL_PM_DATA = "select * from pm where user_id = {0}";

    /**
     * 保存
     *
     * @param idiom
     * @return
     * @throws SQLException
     */
//    public static Idiom save(Idiom idiom) throws SQLException {
//
//        Connection connection = getConnection();
//
//        PreparedStatement prep = connection.prepareStatement(savesql);
//
//        prep.setString(1, idiom.getIdiom());
//        prep.setString(2, idiom.getDescription());
//        prep.setInt(3, idiom.getLevel());
//        prep.setBytes(4, idiom.getImage());
//        prep.setInt(5, idiom.getDifficulty());
//        prep.setString(6, idiom.getPinyin());
//        prep.setInt(7, idiom.getSortOrder());
//        prep.addBatch();
//
//        connection.setAutoCommit(false);
//        prep.executeBatch();
//        connection.setAutoCommit(true);
//
//        return idiom;
//    }

    /**
     * 修改
     *
     * @param idiom
     * @throws SQLException
     */
//    public static void update(Idiom idiom) throws SQLException {
//        Connection connection = getConnection();
//
//        PreparedStatement prep = connection.prepareStatement(updatesql);
//
//        prep.setString(1, idiom.getIdiom());
//        prep.setString(2, idiom.getDescription());
//        prep.setInt(3, idiom.getLevel());
//        prep.setBytes(4, idiom.getImage());
//        prep.setInt(5, idiom.getDifficulty());
//        prep.setString(6, idiom.getPinyin());
//        prep.setInt(7, idiom.getSortOrder());
//        prep.setInt(8, idiom.getId());
//
//        connection.setAutoCommit(false);
//        prep.executeUpdate();
//        connection.setAutoCommit(true);
//    }

    /**
     * 列表
     *
     * @param sql
     * @return
     * @throws SQLException
     */
//    public static List<Idiom> list(String sql) throws SQLException {
//
//        Connection connection = getConnection();
//        Statement stat = connection.createStatement();
//        ResultSet rs = stat.executeQuery(sql);
//
//        List<Idiom> idioms = new ArrayList<>();
//
//        Idiom idiom;
//        while (rs.next()) {
//            idiom = new Idiom();
//            readResult(rs, idiom);
//            idioms.add(idiom);
//        }
//        rs.close();
//
//        return idioms;
//    }

    /**
     * 获取
     *
     * @return
     * @throws SQLException
     */
    public static Order get(int id) throws SQLException {

        Connection connection = getConnection();
        Statement stat = connection.createStatement();
        ResultSet rs = stat.executeQuery(MessageFormat.format("select * from Orders where O_id = {0}", id));
        Order order;
        if (rs.next()) {
            order = readResult(rs);
        } else {
            order = null;
        }
        rs.close();

        return order;
    }


    public static ArrayList<Order> getAllOrder() throws SQLException {

        Connection connection = getConnection();
        Statement stat = connection.createStatement();
        ResultSet rs = null;

        ArrayList<Order> orders = new ArrayList<>();
        try {
            rs = stat.executeQuery("select * from Orders");
            while (rs.next()) {
                Order order = readResult(rs);
                orders.add(order);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return orders;
    }

    private static Order readResult(ResultSet rs) throws SQLException {
        Order order = new Order();
        order.setId(rs.getInt("O_id"));
        order.setCustomer(rs.getString("Customer"));
        order.setOrderDate(rs.getString("OrderDate"));
        order.setOrderPrice(rs.getString("OrderPrice"));
        return order;
    }


    private static PMEntity getPMEntityFromResult(ResultSet rs) throws SQLException {
        PMEntity entity = new PMEntity();
        entity.setId(rs.getInt("id"));
        entity.setTime(rs.getString("time"));
        entity.setValue(rs.getString("value"));
        entity.setOther(rs.getString("other"));
        entity.setUserId(rs.getInt("user_id"));
        return entity;
    }


    public static ArrayList<PMEntity> getAllPMEntity(int userId) throws SQLException {

        Connection connection = getConnection();
        Statement stat = connection.createStatement();
        ResultSet rs = null;

        ArrayList<PMEntity> entities = new ArrayList<>();
        try {
            rs = stat.executeQuery(MessageFormat.format(GET_ALL_PM_DATA, userId));
            while (rs.next()) {
                PMEntity entity = getPMEntityFromResult(rs);
                entities.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return entities;
    }


    /**
     * 执行Sql
     *
     * @param sql
     * @throws SQLException
     */
    public static void execute(String sql) throws SQLException {

        Connection connection = getConnection();

        Statement statement = connection.createStatement();

        statement.execute(sql);

    }

}
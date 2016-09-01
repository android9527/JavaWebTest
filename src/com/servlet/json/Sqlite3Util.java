package com.servlet.json;

import com.servlet.json.entity.Order;
import com.servlet.json.entity.PMEntity;
import com.servlet.json.entity.User;

import org.apache.commons.lang3.StringUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;
import java.util.ArrayList;

/**
 * 数据库连接 sql
 */
public class Sqlite3Util {

    private static final String TABLE_PM = "pm";
    private static final String TABLE_USER = "user_table";

    private static final int PAGE_SIZE = 10;

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

    private static final String GET_ALL_PM_DATA = "select * from " + TABLE_PM + " where user_id = {0} ORDER BY id DESC LIMIT {1}";

    private static final String GET_PM_COUNT = "SELECT COUNT(*) as total FROM " + TABLE_PM + " where user_id = {0}";

    private static final String GET_BEFORE_PM_DATA = "select * from " + TABLE_PM + " where user_id = {0} ORDER BY id DESC LIMIT {1}";
    private static final String LOGIN_SQL = "select * from " + TABLE_USER + " where name = ''{0}'' AND passwd = ''{1}''";

    private static final String INSET_PM_SQL = MessageFormat.format("insert into {0} (time, value, user_id, other, temperature, humidity) values (?, ?, ?, ?, ?, ?);", TABLE_PM);

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


    /**
     * 获取所有订单信息
     *
     * @return
     * @throws SQLException
     */
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
        String temperature = rs.getString("temperature");
        entity.setTemperature(StringUtils.isEmpty(temperature) ? "" : temperature);
        String humidity = rs.getString("humidity");
        entity.setHumidity(StringUtils.isEmpty(humidity) ? "" : humidity);
        return entity;
    }


    /**
     * 分页获取所有pm数据
     *
     * @param userId
     * @return
     * @throws SQLException
     */
    public static ArrayList<PMEntity> getBeforePMEntity(int draw, int start, int length, int userId) throws SQLException {

        Connection connection = getConnection();
        Statement stat = connection.createStatement();
        ResultSet rs = null;
        ArrayList<PMEntity> entities = new ArrayList<>();
        try {
            // select * from pm where user_id = 1 ORDER BY id DESC LIMIT start, draw * length;
            String sql = MessageFormat.format(GET_BEFORE_PM_DATA, userId, start + ", " + draw * length);
            rs = stat.executeQuery(sql);
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
     * 获取所有pm数据
     *
     * @param userId
     * @return
     * @throws SQLException
     */
    public static ArrayList<PMEntity> getLastPMEntity(int userId) throws SQLException {

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

    public static int getPMCount(int userId) {
        ResultSet rs = null;
        try {
            Connection connection = getConnection();
            Statement stat = connection.createStatement();
            rs = stat.executeQuery(MessageFormat.format(GET_PM_COUNT, userId));
            if (rs.next()) {
                return rs.getInt("total");
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
        return 0;
    }


    /**
     * login
     *
     * @param userName
     * @param password
     * @return
     */
    public static User login(String userName, String password) {
        Connection connection = getConnection();
        ResultSet rs = null;
        try {
            Statement stat = connection.createStatement();
            rs = stat.executeQuery(MessageFormat.format(LOGIN_SQL, userName, password));
            if (rs != null && rs.next())
                return getUserFromResult(rs);
        } catch (SQLException e) {
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
        return null;
    }

    /**
     * 获取一条数据
     *
     * @param rs
     * @return
     * @throws SQLException
     */
    private static User getUserFromResult(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setName(rs.getString("name"));
        user.setPassword(rs.getString("passwd"));
        user.setImg(rs.getString("img"));
        return user;
    }

    /**
     * 插入pm数据到数据库
     *
     * @param entity
     * @return
     */
    public static boolean insertPMEntity(PMEntity entity) {
        Connection connection = getConnection();
        PreparedStatement prep = null;
        try {
            prep = connection.prepareStatement(INSET_PM_SQL);
            prep.setString(1, entity.getTime());
            prep.setString(2, entity.getValue());
            prep.setInt(3, entity.getUserId());
            prep.setString(4, entity.getOther());
            prep.setString(5, entity.getTemperature());
            prep.setString(6, entity.getHumidity());
            prep.addBatch();
            connection.setAutoCommit(false);
            prep.executeBatch();
            connection.setAutoCommit(true);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (prep != null) {
                try {
                    prep.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
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
package com.javaspring.schedule;


import java.sql.*;
import java.util.Date;

public class ScheduleMdlCourse {

    private static final String DB_URL = "jdbc:mysql://localhost:3308/moodle";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    private static final String MY_DB_URL = "jdbc:mysql://localhost:3306/moodle";
    private static final String MY_DB_USER = "root";
    private static final String MY_DB_PASSWORD = "ductu1005";

    public static String getMdlCourse() throws Exception {

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Connection myConnection = DriverManager.getConnection(MY_DB_URL, MY_DB_USER, MY_DB_PASSWORD)) {

            Timestamp timestamp = null;
            String sqlMyTime = "select max(timefetch) as timefetch\n" +
                    "from course c";
            try (PreparedStatement preparedStatement = myConnection.prepareStatement(sqlMyTime);
                 ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    timestamp = resultSet.getTimestamp("timefetch");
                }
            } catch (Exception e) {
                throw e;
            }

            String sqlQuery = "SELECT * FROM mdl_course mc WHERE mc.visible = 1\n";

            if (timestamp != null) {
                sqlQuery += " AND (mc.timecreated >= ? OR mc.timemodified >= ?)";
            }

            try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
                if (timestamp != null) {
                    preparedStatement.setLong(1, timestamp.getTime() / 1000);
                    preparedStatement.setLong(2, timestamp.getTime() / 1000);
                }

                ResultSet result = preparedStatement.executeQuery();
                while (result.next()) {
                    insertDataIntoCourse(result, myConnection);
                }
                return "Thành công";
            } catch (Exception e) {
                throw e;
            }
        } catch (Exception e) {
            throw e;
        }
    }

    private static void insertDataIntoCourse(ResultSet resultSet, Connection connection) throws Exception {
        Date now = new Date();
        Timestamp currentTimestamp = new Timestamp(now.getTime());
        String sqlQuery = "SELECT * FROM course c \n";
        sqlQuery += " WHERE c.course_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery, ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_UPDATABLE)) {
            preparedStatement.setLong(1, resultSet.getLong("id"));
            ResultSet result = preparedStatement.executeQuery();
            result.last();
            int rowCount = result.getRow();
            result.beforeFirst();
            if (rowCount == 1 && result.next()) {
                String myInsertQuery = "UPDATE course\n" +
                        "SET category = ?, \n" +
                        "    fullname = ?, \n" +
                        "    shortname = ?, \n" +
                        "    startdate = ?, \n" +
                        "    enddate = ?, \n" +
                        "    visible = ?, \n" +
                        "    visibleold = ?, \n" +
                        "    timemodified = ?, \n" +
                        "    cacherev = ?,\n" +
                        "    timefetch = ?\n" +
                        "WHERE course_id = ?;\n";
                try (PreparedStatement myInsertStatement = connection.prepareStatement(myInsertQuery)) {
                    myInsertStatement.setLong(1, resultSet.getLong("category"));
                    myInsertStatement.setString(2, resultSet.getString("fullname"));
                    myInsertStatement.setString(3, resultSet.getString("shortname"));
                    myInsertStatement.setLong(4, resultSet.getLong("startdate"));
                    myInsertStatement.setLong(5, resultSet.getLong("enddate"));
                    myInsertStatement.setInt(6, resultSet.getInt("visible"));
                    myInsertStatement.setInt(7, resultSet.getInt("visibleold"));
                    myInsertStatement.setLong(8, resultSet.getLong("timemodified"));
                    myInsertStatement.setLong(9, resultSet.getLong("cacherev"));
                    myInsertStatement.setTimestamp(10, currentTimestamp);
                    myInsertStatement.setLong(11, resultSet.getLong("id"));
                    myInsertStatement.executeUpdate();
                } catch (Exception e) {
                    throw e;
                }
            } else if (rowCount == 0) {
                String myInsertQuery = "insert into course (course_id, category, fullname, shortname, startdate, enddate, visible, visibleold, timecreated, timemodified, cacherev, timefetch) \n" +
                        "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                try (PreparedStatement myInsertStatement = connection.prepareStatement(myInsertQuery)) {
                    myInsertStatement.setLong(1, resultSet.getLong("id"));
                    myInsertStatement.setLong(2, resultSet.getLong("category"));
                    myInsertStatement.setString(3, resultSet.getString("fullname"));
                    myInsertStatement.setString(4, resultSet.getString("shortname"));
                    myInsertStatement.setLong(5, resultSet.getLong("startdate"));
                    myInsertStatement.setLong(6, resultSet.getLong("enddate"));
                    myInsertStatement.setInt(7, resultSet.getInt("visible"));
                    myInsertStatement.setInt(8, resultSet.getInt("visibleold"));
                    myInsertStatement.setLong(9, resultSet.getLong("timecreated"));
                    myInsertStatement.setLong(10, resultSet.getLong("timemodified"));
                    myInsertStatement.setLong(11, resultSet.getLong("cacherev"));
                    myInsertStatement.setTimestamp(12, currentTimestamp);
                    myInsertStatement.executeUpdate();
                } catch (Exception e) {
                    throw e;
                }
            } else
                throw new Exception("Tồn tại 2 user trùng nhau");
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }

}

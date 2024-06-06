package com.javaspring.schedule;


import java.sql.*;
import java.util.Date;

public class ScheduleMdlUser {

    private static final String DB_URL = "jdbc:mysql://localhost:3308/moodle";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";


    private static final String MY_DB_URL = "jdbc:mysql://localhost:3306/moodle";
    private static final String MY_DB_USER = "root";
    private static final String MY_DB_PASSWORD = "ductu1005";

    public static String getMdlUser() throws Exception {

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Connection myConnection = DriverManager.getConnection(MY_DB_URL, MY_DB_USER, MY_DB_PASSWORD)) {

            Timestamp timestamp = null;
            String sqlMyTime = "select max(timefetch) as timefetch\n" +
                    "from user u";
            try (PreparedStatement preparedStatement = myConnection.prepareStatement(sqlMyTime);
                 ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    timestamp = resultSet.getTimestamp("timefetch");
                }
            } catch (Exception e) {
                throw e;
            }

            String sqlQuery = "SELECT * FROM mdl_user mu WHERE deleted = 0\n";

            if (timestamp != null) {
                sqlQuery += " AND (mu.timecreated >= ? OR mu.timemodified >= ?)";
            }

            try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
                if (timestamp != null) {
                    preparedStatement.setLong(1, timestamp.getTime() / 1000);
                    preparedStatement.setLong(2, timestamp.getTime() / 1000);
                }

                ResultSet result = preparedStatement.executeQuery();
                while (result.next()) {
                    insertDataIntoUser(result, myConnection);
                }
                return "Thành công";
            } catch (Exception e) {
                throw e;
            }
        } catch (Exception e) {
            throw e;
        }
    }

    private static void insertDataIntoUser(ResultSet resultSet, Connection connection) throws Exception {
        Date now = new Date();
        Timestamp currentTimestamp = new Timestamp(now.getTime());

        String sqlQuery = "SELECT * FROM user u \n";
        sqlQuery += " WHERE u.user_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery, ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_UPDATABLE)) {
            preparedStatement.setLong(1, resultSet.getLong("id"));
            ResultSet result = preparedStatement.executeQuery();
            result.last();
            int rowCount = result.getRow();
            result.beforeFirst();
            if (rowCount == 1 && result.next()) {
                String myInsertQuery = "UPDATE user\n" +
                        "SET deleted = ?,\n" +
                        "    username = ?,\n" +
                        "    password = ?,\n" +
                        "    firstname = ?,\n" +
                        "    lastname = ?,\n" +
                        "    email = ?,\n" +
                        "    timecreated = ?,\n" +
                        "    timemodified = ?,\n" +
                        "    timefetch = ?\n" +
                        "WHERE user_id = ?;\n";
                try (PreparedStatement myInsertStatement = connection.prepareStatement(myInsertQuery)) {
                    myInsertStatement.setInt(1, resultSet.getInt("deleted"));
                    myInsertStatement.setString(2, resultSet.getString("username"));
                    myInsertStatement.setString(3, resultSet.getString("password"));
                    myInsertStatement.setString(4, resultSet.getString("firstname"));
                    myInsertStatement.setString(5, resultSet.getString("lastname"));
                    myInsertStatement.setString(6, resultSet.getString("email"));
                    myInsertStatement.setLong(7, resultSet.getLong("timecreated"));
                    myInsertStatement.setLong(8, resultSet.getLong("timemodified"));
                    myInsertStatement.setTimestamp(9, currentTimestamp);
                    myInsertStatement.setLong(10, resultSet.getLong("id"));

                    myInsertStatement.executeUpdate();
                } catch (Exception e) {
                    throw e;
                }
            } else if (rowCount == 0) {
                String myInsertQuery = "insert into user (user_id, deleted, username, password, firstname, lastname, email, timecreated, timemodified, timefetch)\n" +
                        "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                try (PreparedStatement myInsertStatement = connection.prepareStatement(myInsertQuery)) {
                    myInsertStatement.setLong(1, resultSet.getLong("id"));
                    myInsertStatement.setInt(2, resultSet.getInt("deleted"));
                    myInsertStatement.setString(3, resultSet.getString("username"));
                    myInsertStatement.setString(4, resultSet.getString("password"));
                    myInsertStatement.setString(5, resultSet.getString("firstname"));
                    myInsertStatement.setString(6, resultSet.getString("lastname"));
                    myInsertStatement.setString(7, resultSet.getString("email"));
                    myInsertStatement.setLong(8, resultSet.getLong("timecreated"));
                    myInsertStatement.setLong(9, resultSet.getLong("timemodified"));
                    myInsertStatement.setTimestamp(10, currentTimestamp);

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

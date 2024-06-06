package com.javaspring.schedule;


import java.sql.*;
import java.util.Date;

public class ScheduleRoleAss {

    private static final String DB_URL = "jdbc:mysql://localhost:3308/moodle";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";


    private static final String MY_DB_URL = "jdbc:mysql://localhost:3306/moodle";
    private static final String MY_DB_USER = "root";
    private static final String MY_DB_PASSWORD = "ductu1005";

    Date now = new Date();
    Timestamp currentTimestamp = new Timestamp(now.getTime());

    public String getMdlRoleAss() throws Exception {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Connection myConnection = DriverManager.getConnection(MY_DB_URL, MY_DB_USER, MY_DB_PASSWORD)) {

            Timestamp timestamp = null;
            String sqlMyTime = "select max(timefetch) as timefetch\n" +
                    "from role_assignments r";
            try (PreparedStatement preparedStatement = myConnection.prepareStatement(sqlMyTime);
                 ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    timestamp = resultSet.getTimestamp("timefetch");
                }
            } catch (Exception e) {
                throw e;
            }

            String sqlQuery = "select mra.id, mra.roleid, mu.id as userId, col.id as courseId, mra.timemodified, mra.modifierid\n" +
                    "from mdl_role_assignments mra\n" +
                    "         join mdl_user mu on (mu.id = mra.userid and mu.deleted = 0)\n" +
                    "         join mdl_context c on (c.id = mra.contextid)\n" +
                    "         join mdl_course col on (col.id = c.instanceid and c.contextlevel = 50 and col.visible = 1)\n" +
                    "WHERE 1 = 1\n";

            if (timestamp != null) {
                sqlQuery += " AND (mra.timemodified >= ?)";
            }

            try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
                if (timestamp != null) {
                    preparedStatement.setLong(1, timestamp.getTime() / 1000);
//                    preparedStatement.setLong(2, timestamp.getTime() / 1000);
                }

                ResultSet result = preparedStatement.executeQuery();
                while (result.next()) {
                    insertDataIntoRoleAss(result, myConnection);
                }
                return "Thành công";
            } catch (Exception e) {
                throw e;
            }
        } catch (Exception e) {
            throw e;
        }
    }

    private void insertDataIntoRoleAss(ResultSet resultSet, Connection connection) throws Exception {
        String sqlQuery = "select *\n" +
                "from role_assignments r where r.role_assignments_id = ?";
//        sqlQuery += " WHERE c.course_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery, ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_UPDATABLE)) {
            preparedStatement.setLong(1, resultSet.getLong("id"));
            ResultSet result = preparedStatement.executeQuery();
            result.last();
            int rowCount = result.getRow();   // Lấy vị trí của bản ghi hiện tại
            result.beforeFirst(); // Đưa con trỏ về vị trí trước khi đọc dữ liệu
            if (rowCount == 1 && result.next()) {
                String myInsertQuery = "UPDATE role_assignments\n" +
                        "SET\n" +
                        "  roleid = ?,\n" +
                        "  courseid = ?,\n" +
                        "  userid = ?,\n" +
                        "  timemodified = ?,\n" +
                        "  modifierid = ?,\n" +
                        "  timefetch = ?\n" +
                        "WHERE\n" +
                        "  role_assignments_id = ?;\n";
                try (PreparedStatement myInsertStatement = connection.prepareStatement(myInsertQuery)) {
                    myInsertStatement.setLong(1, resultSet.getLong("roleid"));
                    myInsertStatement.setLong(2, resultSet.getLong("courseid"));
                    myInsertStatement.setLong(3, resultSet.getLong("userid"));
                    myInsertStatement.setLong(4, resultSet.getLong("timemodified"));
                    myInsertStatement.setLong(5, resultSet.getLong("modifierid"));
                    myInsertStatement.setTimestamp(6, currentTimestamp);
                    myInsertStatement.setLong(7, resultSet.getLong("id"));
                    myInsertStatement.executeUpdate();
                } catch (Exception e) {
                    throw e;
                }
            } else if (rowCount == 0) {
                String myInsertQuery = "insert into role_assignments (roleid, courseid, userid, timemodified, modifierid, timefetch, role_assignments_id)\n" +
                        "values (?, ?, ?, ?, ?, ?, ?)";
                try (PreparedStatement myInsertStatement = connection.prepareStatement(myInsertQuery)) {
                    myInsertStatement.setLong(1, resultSet.getLong("roleid"));
                    myInsertStatement.setLong(2, resultSet.getLong("courseid"));
                    myInsertStatement.setLong(3, resultSet.getLong("userid"));
                    myInsertStatement.setLong(4, resultSet.getLong("timemodified"));
                    myInsertStatement.setLong(5, resultSet.getLong("modifierid"));
                    myInsertStatement.setTimestamp(6, currentTimestamp);
                    myInsertStatement.setLong(7, resultSet.getLong("id"));
                    myInsertStatement.executeUpdate();
                } catch (Exception e) {
                    throw e;
                }
            } else
                throw new Exception("Tồn tại user cung course mà role trùng nhau");
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }
}

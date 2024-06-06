package com.javaspring.schedule;


import java.sql.*;
import java.util.Date;

public class ScheduleUserCourse {

    private static final String DB_URL = "jdbc:mysql://localhost:3308/moodle";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";


    private static final String MY_DB_URL = "jdbc:mysql://localhost:3306/moodle";
    private static final String MY_DB_USER = "root";
    private static final String MY_DB_PASSWORD = "ductu1005";

    public static String getUserCourse() throws Exception {

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Connection myConnection = DriverManager.getConnection(MY_DB_URL, MY_DB_USER, MY_DB_PASSWORD)) {

            Timestamp timestamp = null;
            String sqlMyTime = "select max(timefetch) as timefetch\n" +
                    "from user_course c";
            try (PreparedStatement preparedStatement = myConnection.prepareStatement(sqlMyTime);
                 ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    timestamp = resultSet.getTimestamp("timefetch");
                }
            } catch (Exception e) {
                throw e;
            }

            String sqlQuery = "select mue.id as userCourseId,\n" +
                    "       mu.id as userId,\n" +
                    "       mc.id as courseId,\n" +
                    "       mue.status,\n" +
                    "       mue.timestart,\n" +
                    "       mue.timeend,\n" +
                    "       mue.modifierid,\n" +
                    "       mue.timecreated,\n" +
                    "       mue.timemodified\n" +
                    "from mdl_user_enrolments mue\n" +
                    "         join mdl_user mu on mu.id = mue.userid\n" +
                    "         join mdl_enrol me on me.id = mue.enrolid\n" +
                    "         join mdl_course mc on mc.id = me.courseid";

            if (timestamp != null) {
                sqlQuery += " AND (mue.timecreated >= ? OR mue.timemodified >= ?)";
            }

            try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
                if (timestamp != null) {
                    preparedStatement.setLong(1, timestamp.getTime() / 1000);
                    preparedStatement.setLong(2, timestamp.getTime() / 1000);
                }

                ResultSet result = preparedStatement.executeQuery();
                while (result.next()) {
                    insertDataIntoUserCourse(result, myConnection);
                }
                return "Thành công";
            } catch (Exception e) {
                throw e;
            }
        } catch (Exception e) {
            throw e;
        }
    }

    private static void insertDataIntoUserCourse(ResultSet resultSet, Connection connection) throws Exception {
        Date now = new Date();
        Timestamp currentTimestamp = new Timestamp(now.getTime());
        String sqlQuery = "SELECT * FROM user_course c \n";
        sqlQuery += " WHERE c.mdl_enrolment_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery, ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_UPDATABLE)) {
            preparedStatement.setLong(1, resultSet.getLong("userCourseId"));
            ResultSet result = preparedStatement.executeQuery();
            result.last();
            int rowCount = result.getRow();
            result.beforeFirst();
            if (rowCount == 1 && result.next()) {
                String myInsertQuery = "UPDATE user_course\n" +
                        "SET\n" +
                        "  status = ?,\n" +
                        "  timestart = ?,\n" +
                        "  timeend = ?,\n" +
                        "  modifierid = ?,\n" +
                        "  timemodified = ?,\n" +
                        "  timefetch = ?\n" +
                        "WHERE\n" +
                        "  userCourseId = ?\n";
                try (PreparedStatement myInsertStatement = connection.prepareStatement(myInsertQuery)) {
                    myInsertStatement.setLong(1, resultSet.getLong("status"));
                    myInsertStatement.setLong(2, resultSet.getLong("timestart"));
                    myInsertStatement.setLong(3, resultSet.getLong("timeend"));
                    myInsertStatement.setLong(4, resultSet.getLong("modifierid"));
                    myInsertStatement.setLong(5, resultSet.getLong("timemodified"));
                    myInsertStatement.setTimestamp(6, currentTimestamp);
                    myInsertStatement.setLong(7, resultSet.getLong("userCourseId"));
                    myInsertStatement.executeUpdate();
                } catch (Exception e) {
                    throw e;
                }
            } else if (rowCount == 0) {
                String myInsertQuery = "insert into user_course (status, courseid, userid, timestart, timeend, modifierid, timecreated, timemodified, mdl_enrolment_id, timefetch)\n" +
                        "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                try (PreparedStatement myInsertStatement = connection.prepareStatement(myInsertQuery)) {
                    myInsertStatement.setLong(1, resultSet.getLong("status"));
                    myInsertStatement.setLong(2, resultSet.getLong("courseId"));
                    myInsertStatement.setLong(3, resultSet.getLong("userId"));
                    myInsertStatement.setLong(4, resultSet.getLong("timestart"));
                    myInsertStatement.setLong(5, resultSet.getLong("timeend"));
                    myInsertStatement.setLong(6, resultSet.getLong("modifierid"));
                    myInsertStatement.setLong(7, resultSet.getLong("timecreated"));
                    myInsertStatement.setLong(8, resultSet.getLong("timemodified"));
                    myInsertStatement.setLong(9, resultSet.getLong("userCourseId"));
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

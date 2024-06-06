package com.javaspring;

import com.javaspring.schedule.ScheduleMdlCourse;
import com.javaspring.schedule.ScheduleMdlUser;
import com.javaspring.schedule.ScheduleRoleAss;
import com.javaspring.schedule.ScheduleUserCourse;

import java.sql.*;
import java.util.Date;


public class Main {

//    

    public static final String DB_URL = "jdbc:mysql://localhost:3308/moodle";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    private static final String MY_DB_URL = "jdbc:mysql://localhost:3306/moodle";
    private static final String MY_DB_USER = "root";
    private static final String MY_DB_PASSWORD = "ductu1005";

    public static void main(String[] args) throws Exception {
////        Class<SocketScheduleMoodle> socketScheduleMoodleClass = SocketScheduleMoodle.class;
//
        ScheduleMdlUser scheduleMdlUser = new ScheduleMdlUser();
        ScheduleMdlCourse scheduleMdlCourse = new ScheduleMdlCourse();
        ScheduleUserCourse scheduleUserCourse = new ScheduleUserCourse();
        ScheduleRoleAss scheduleRoleAss = new ScheduleRoleAss();

        System.out.println("{Fetch data user form moodle}: " + scheduleMdlUser.getMdlUser());
        System.out.println("{Fetch data course form moodle}: " + scheduleMdlCourse.getMdlCourse());
        System.out.println("{Fetch data user_course form moodle}: " + scheduleUserCourse.getUserCourse());
        System.out.println("{Fetch data role_user_course form moodle}: " + scheduleRoleAss.getMdlRoleAss());
    }
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

    /*
    Fetch Data Course
     */
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

    /*
    Fetch
     */
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
                sqlQuery += " AND (mc.timecreated >= ? OR mc.timemodified >= ?)";
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

    /*
    Role Ass
     */
    public String getMdlRoleAss() throws Exception {
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

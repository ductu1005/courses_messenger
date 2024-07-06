//package com.datn.schedule;
//
//
//import com.datn.entity.MdlCourseEntity;
//import com.datn.entity.MdlRoleAssignmentsEntity;
//import com.datn.entity.MdlUserEntity;
//import com.datn.entity.UserCourseEntity;
//import com.datn.repository.CourseRepository;
//import com.datn.repository.RoleAssignmentsRepository;
//import com.datn.repository.UserCourseRepository;
//import com.datn.repository.UserRepository;
//import lombok.extern.log4j.Log4j2;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.scheduling.annotation.EnableScheduling;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//
//@Configuration
//@EnableScheduling
//@Log4j2
//public class SocketScheduleMoodle {
//
//    @Autowired
//    UserRepository userRepository;
//
//    @Autowired
//    CourseRepository courseRepository;
//
//    @Autowired
//    RoleAssignmentsRepository roleAssignmentsRepository;
//
//    @Autowired
//    UserCourseRepository userCourseRepository;
//
//    private static final String DB_URL = "jdbc:mysql://localhost:3308/moodle";
//    private static final String DB_USER = "root";
//    private static final String DB_PASSWORD = "";
//
//    //    @Scheduled(fixedDelayString = "10000") pf1
//    public String getMdlUser() throws Exception {
//        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
//
//            // Thực hiện truy vấn SQL
//            String sqlQuery = "SELECT * FROM mdl_user WHERE deleted = 0";
//            try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
//                 ResultSet resultSet = preparedStatement.executeQuery()) {
//                // Xử lý kết quả truy vấn
//                while (resultSet.next()) {
//                    MdlUserEntity entity = new MdlUserEntity();
//                    entity.setUserId(resultSet.getLong("id"));
//                    entity.setUsername(resultSet.getString("username"));
//                    entity.setEmail(resultSet.getString("email"));
//                    entity.setPassword(resultSet.getString("password"));
//                    userRepository.save(entity);
//                }
//                return "Thành công";
//            } catch (Exception e) {
//                throw e;
//            }
//        } catch (Exception e) {
//            throw e;
//        }
//    }
//
//    public String getMdlCourse() throws Exception {
//        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
//            // Thực hiện truy vấn SQL
//            String sqlQuery = "SELECT * FROM mdl_course WHERE visible = 1";
//            try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
//                 ResultSet resultSet = preparedStatement.executeQuery()) {
//
//                // Xử lý kết quả truy vấn
//                while (resultSet.next()) {
//                    MdlCourseEntity entity = new MdlCourseEntity();
//                    entity.setCourseId(resultSet.getLong("id"));
//                    entity.setFullname(resultSet.getString("fullname"));
//                    entity.setShortname(resultSet.getString("shortname"));
//                    entity.setCategory(resultSet.getLong("category"));
//                    entity.setCacherev(resultSet.getLong("cacherev"));
//                    entity.setEnddate(resultSet.getLong("enddate"));
//                    entity.setStartdate(resultSet.getLong("startdate"));
//                    entity.setTimemodified(resultSet.getLong("timemodified"));
////                    entity.setUpdateDate(resultSet.getTimestamp("update_date"));
//                    entity.setVisible(resultSet.getInt("visible"));
//                    entity.setVisibleold(resultSet.getInt("visibleold"));
//                    entity.setTimecreated(resultSet.getLong("timecreated"));
////                    courseRepository.save(entity);
//                }
//                return "Thành công";
//            } catch (Exception e) {
//                throw e;
//            }
//        } catch (Exception e) {
//            throw e;
//        }
//    }
//
//    public String getMdlRoleAssignments() throws Exception {
//        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
//            // Thực hiện truy vấn SQL
//            String sqlQuery = "select mra.roleid, mu.id as userId, col.id as courseId, mra.timemodified, mra.modifierid\n" +
//                    "from mdl_role_assignments mra\n" +
//                    "         join mdl_user mu on (mu.id = mra.userid and mu.deleted = 0)\n" +
//                    "         join mdl_context c on (c.id = mra.contextid)\n" +
//                    "         join mdl_course col on (col.id = c.instanceid and c.contextlevel = 50 and col.visible = 1)";
//            try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
//                 ResultSet resultSet = preparedStatement.executeQuery()) {
//
//                // Xử lý kết quả truy vấn
//                while (resultSet.next()) {
//                    MdlRoleAssignmentsEntity entity = new MdlRoleAssignmentsEntity();
//                    entity.setUserid(resultSet.getLong("userId"));
//                    entity.setCourseid(resultSet.getLong("courseId"));
//                    entity.setTimemodified(resultSet.getLong("timemodified"));
//                    entity.setModifierid(resultSet.getLong("modifierid"));
//                    roleAssignmentsRepository.save(entity);
//                }
//                return "Thành công";
//            } catch (Exception e) {
//                throw e;
//            }
//        } catch (Exception e) {
//            throw e;
//        }
//    }
//
//    public String getMdlUserCourse() throws Exception {
//        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
//            // Thực hiện truy vấn SQL
//            String sqlQuery = "select mue.id as userCourseId,\n" +
//                    "       mu.id as userId,\n" +
//                    "       mc.id as courseId,\n" +
//                    "       mue.status,\n" +
//                    "       mue.timestart,\n" +
//                    "       mue.timeend,\n" +
//                    "       mue.modifierid,\n" +
//                    "       mue.timecreated,\n" +
//                    "       mue.timemodified\n" +
//                    "from mdl_user_enrolments mue\n" +
//                    "         join mdl_user mu on mu.id = mue.userid\n" +
//                    "         join mdl_enrol me on me.id = mue.enrolid\n" +
//                    "         join mdl_course mc on mc.id = me.courseid";
//            try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
//                 ResultSet resultSet = preparedStatement.executeQuery()) {
//
//                // Xử lý kết quả truy vấn
//                while (resultSet.next()) {
//                    UserCourseEntity entity = new UserCourseEntity();
//                    entity.setMdlEnrolmentId(resultSet.getLong("userCourseId"));
//                    entity.setUserid(resultSet.getLong("userId"));
//                    entity.setCourseid(resultSet.getLong("courseId"));
//                    entity.setTimemodified(resultSet.getLong("timemodified"));
//                    entity.setModifierid(resultSet.getLong("modifierid"));
//                    entity.setTimestart(resultSet.getLong("timestart"));
//                    entity.setTimeend(resultSet.getLong("timeend"));
//                    entity.setTimecreated(resultSet.getLong("timecreated"));
//                    entity.setStatus(resultSet.getLong("status"));
//                    userCourseRepository.save(entity);
//                }
//                return "Thành công";
//            } catch (Exception e) {
//                throw e;
//            }
//        } catch (Exception e) {
//            throw e;
//        }
//    }
//}

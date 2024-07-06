//package com.datn.mapper;
//
//import com.datn.entity.MdlUserEntity;
//import org.springframework.jdbc.core.RowMapper;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
//public class MoodleUserRowMapper implements RowMapper<MdlUserEntity> {
//
//    @Override
//    public MdlUserEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
//        MdlUserEntity user = new MdlUserEntity();
//        user.setId(rs.getLong("id"));
//        user.setUsername(rs.getString("username"));
//        user.setEmail(rs.getString("email"));
//        user.setFirstname(rs.getString("firstname"));
//        user.setLastname(rs.getString("lastname"));
//        return user;
//    }
//}

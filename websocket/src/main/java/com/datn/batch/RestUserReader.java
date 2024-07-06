//package com.datn.batch;
//
//import com.datn.entity.MdlUserEntity;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.batch.item.ItemReader;
//import org.springframework.batch.item.database.JdbcCursorItemReader;
//import org.springframework.http.ResponseEntity;
//import org.springframework.jdbc.datasource.DriverManagerDataSource;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.Arrays;
//import java.util.List;
//
//@Slf4j
//public class RestUserReader implements ItemReader<MdlUserEntity> {
//
//    private final RestTemplate restTemplate;
//    private int nextUser;
//    private List<MdlUserEntity> userEntityList;
//
//    public RestUserReader(RestTemplate restTemplate) {
//        this.restTemplate = restTemplate;
//    }
//
//
//    @Override
//    public MdlUserEntity read() throws Exception {
//        if (this.userEntityList == null) {
//            userEntityList = fetchBooks();
//        }
//        MdlUserEntity userEntity = null;
//
//        if (nextUser < userEntityList.size()) {
//            userEntity = userEntityList.get(nextUser);
//            nextUser++;
//        } else {
//            nextUser = 0;
//            userEntityList = null;
//        }
//        return userEntity;
//    }
//
//    private List<MdlUserEntity> fetchBooks() {
//        JdbcCursorItemReader<MdlUserEntity> itemReader = new JdbcCursorItemReader<>();
//
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName("org.mariadb.jdbc.Driver");
//        dataSource.setUrl("jdbc:mariadb://localhost:3308");
//        dataSource.setUsername("root");
//        dataSource.setPassword("");
//
//        itemReader.setDataSource(dataSource);
//        itemReader.setSql("SELECT * FROM mdl_user WHERE deleted = 0");
//        itemReader.setRowMapper((resultSet, i) -> {
//            MdlUserEntity entity = new MdlUserEntity();
//            entity.setUserId(resultSet.getLong("id"));
//            entity.setUsername(resultSet.getString("username"));
//            entity.setEmail(resultSet.getString("email"));
//            entity.setPassword(resultSet.getString("password"));
//            log.info("{user}: " + entity);
//            return entity;
//        });
//
//        return null;
//    }
//}
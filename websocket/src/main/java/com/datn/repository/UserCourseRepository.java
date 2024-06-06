package com.datn.repository;

import com.datn.entity.UserCourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserCourseRepository extends JpaRepository<UserCourseEntity, Long> {

    @Query(value = "select uc.id,\n" +
            "       u.id,\n" +
            "       u.username,\n" +
            "       u.firstname,\n" +
            "       u.lastname,\n" +
            "       c.id,\n" +
            "       c.fullname,\n" +
            "       c.shortname,\n" +
            "       c.image\n" +
            "from user_course uc\n" +
            "         left join user u on u.user_id = uc.userid\n" +
            "         left join course c on c.course_id = uc.courseid\n" +
            "where 1 = 1\n" +
            "and :userId is null OR u.id = :userId", nativeQuery = true)
    List<Object[]> findByUserid(Long userId);
}

package com.datn.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "user_course", schema = "moodle", catalog = "")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserCourseEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private long id;
    @Basic
    @Column(name = "status")
    private long status;
    @Basic
    @Column(name = "courseid")
    private long courseid;
    @Basic
    @Column(name = "userid")
    private long userid;
    @Basic
    @Column(name = "timestart")
    private long timestart;
    @Basic
    @Column(name = "timeend")
    private long timeend;
    @Basic
    @Column(name = "modifierid")
    private long modifierid;
    @Basic
    @Column(name = "timecreated")
    private long timecreated;
    @Basic
    @Column(name = "timemodified")
    private long timemodified;
    @Basic
    @Column(name = "mdl_enrolment_id")
    private long mdlEnrolmentId;
    @Basic
    @Column(name = "timefetch")
    private Timestamp timefetch;
}

package com.datn.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Table(name = "course", schema = "moodle", catalog = "")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CourseEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private long id;
    @Basic
    @Column(name = "course_id")
    private long courseId;
    @Basic
    @Column(name = "category")
    private long category;
    @Basic
    @Column(name = "fullname")
    private String fullname;
    @Basic
    @Column(name = "shortname")
    private String shortname;
    @Basic
    @Column(name = "startdate")
    private long startdate;
    @Basic
    @Column(name = "enddate")
    private long enddate;
    @Basic
    @Column(name = "visible")
    private byte visible;
    @Basic
    @Column(name = "visibleold")
    private byte visibleold;
    @Basic
    @Column(name = "timecreated")
    private long timecreated;
    @Basic
    @Column(name = "timemodified")
    private long timemodified;
    @Basic
    @Column(name = "cacherev")
    private long cacherev;
    @Basic
    @Column(name = "timefetch")
    private Timestamp timefetch;
    @Basic
    @Column(name = "image")
    private String image;
}

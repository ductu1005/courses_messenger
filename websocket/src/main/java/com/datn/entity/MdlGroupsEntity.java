package com.datn.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Table(name = "mdl_groups", schema = "moodle", catalog = "")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MdlGroupsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Long id;
    @Basic
    @Column(name = "courseid")
    private Long courseid;
    @Basic
    @Column(name = "idnumber")
    private String idnumber;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "description")
    private String description;
    @Basic
    @Column(name = "descriptionformat")
    private Integer descriptionformat;
    @Basic
    @Column(name = "enrolmentkey")
    private String enrolmentkey;
    @Basic
    @Column(name = "picture")
    private Long picture;
    @Basic
    @Column(name = "hidepicture")
    private Integer hidepicture;
    @Basic
    @Column(name = "timecreated")
    private Long timecreated;
    @Basic
    @Column(name = "timemodified")
    private Long timemodified;
}

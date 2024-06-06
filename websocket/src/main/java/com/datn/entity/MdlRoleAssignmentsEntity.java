package com.datn.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Table(name = "mdl_role_assignments", schema = "moodle", catalog = "")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MdlRoleAssignmentsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Long id;
    @Basic
    @Column(name = "roleid")
    private Long roleid;
    @Basic
    @Column(name = "courseid")
    private Long courseid;
    @Basic
    @Column(name = "userid")
    private Long userid;
    @Basic
    @Column(name = "timemodified")
    private Long timemodified;
    @Basic
    @Column(name = "modifierid")
    private Long modifierid;
    @Basic
    @Column(name = "component")
    private String component;
    @Basic
    @Column(name = "itemid")
    private Long itemid;
    @Basic
    @Column(name = "sortorder")
    private Long sortorder;
}

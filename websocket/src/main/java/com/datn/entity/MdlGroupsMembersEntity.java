package com.datn.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Table(name = "mdl_groups_members", schema = "moodle", catalog = "")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MdlGroupsMembersEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Long id;
    @Basic
    @Column(name = "groupid")
    private Long groupid;
    @Basic
    @Column(name = "userid")
    private Long userid;
    @Basic
    @Column(name = "timeadded")
    private Long timeadded;
    @Basic
    @Column(name = "component")
    private String component;
    @Basic
    @Column(name = "itemid")
    private Long itemid;
}

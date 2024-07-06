package com.datn.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Table(name = "messages", schema = "moodle", catalog = "")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessagesEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Long id;
    @Basic
    @Column(name = "group_id")
    private Long groupId;
    @Basic
    @Column(name = "sender")
    private String sender;
    @Basic
    @Column(name = "message")
    private String message;
    @Basic
    @Column(name = "timestamp")
    private Long timestamp;
}

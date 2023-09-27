package com.onetwo.userservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@EntityListeners(AuditingEntityListener.class)
@Getter
@MappedSuperclass
public class BaseEntity {

    @CreatedDate
    @Column(updatable = false)
    @Convert(converter = Jsr310JpaConverters.InstantConverter.class)
    private Instant createdAt;

    @Column(nullable = false)
    private String createUser;

    @LastModifiedDate
    @Convert(converter = Jsr310JpaConverters.InstantConverter.class)
    private Instant updatedAt;

    @Column
    private String updateUser;
}
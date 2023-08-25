package com.prefere.core.domain.base

import org.hibernate.annotations.Immutable
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.mongodb.core.mapping.Field
import java.time.LocalDateTime

abstract class BaseTime {
    @Field("created_at")
    @CreatedDate
    @Immutable
    var createdAt: LocalDateTime = LocalDateTime.now()

    @Field("updated_at")
    @LastModifiedDate
    var updatedAt: LocalDateTime = LocalDateTime.now()
}
package com.prefere.core.domain.chat

import com.prefere.core.domain.base.BaseTime
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field

@Document("chat")
class Chat(

    @Id
    val id: Long? = null,

    val message: String,

    @Field("chat_room_id")
    val chatRoomId: String,

    @DBRef
    @Field("sender_id")
    val senderId: String,

): BaseTime() {

}
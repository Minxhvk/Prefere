package com.prefere.core.domain.chatRoom


import com.prefere.core.domain.base.BaseTime
import com.prefere.core.domain.member.Member
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("chat_room")
class ChatRoom(

    @Id
    val id: Long? = null,

    val members: MutableList<Member>
): BaseTime() {

}
package com.prefere.core.domain.chat

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface ChatRepository: MongoRepository<Chat, Long> {
}
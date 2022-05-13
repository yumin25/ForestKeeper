package com.ssafy.forestkeeper.util.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.forestkeeper.application.dto.chat.ChatMessageDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisSubscriber implements MessageListener {

    private final ObjectMapper objectMapper;
    private final RedisTemplate redisChatMessageTemplate;
    private final SimpMessageSendingOperations simpMessageSendingOperations;

    // Redis 에서 메시지가 발행(publish)되면 대기하고 있던 onMessage 가 해당 메시지를 받아 처리
    @Override
    public void onMessage(Message message, byte[] pattern) {

        try {
            // Redis 에서 발행된 데이터를 받아 deserialize
            String publishMessage = (String) redisChatMessageTemplate.getStringSerializer().deserialize(message.getBody());

            // ChatMessageDTO 객체로 매핑
            ChatMessageDTO chatMessageDTO = objectMapper.readValue(publishMessage, ChatMessageDTO.class);

            // WebSocket 구독자에게 채팅 메시지 Send
            simpMessageSendingOperations.convertAndSend("/topic/room/" + chatMessageDTO.getRoomId(), chatMessageDTO);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

    }

}

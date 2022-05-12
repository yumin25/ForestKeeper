package com.ssafy.forestkeeper.util.redis;

import com.ssafy.forestkeeper.application.dto.chat.ChatMessageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisPublisher {

    private final RedisTemplate<String, Object> redisChatMessageTemplate;

    public void publish(ChannelTopic topic, ChatMessageDTO chatMessageDTO) {

        redisChatMessageTemplate.convertAndSend(topic.getTopic(), chatMessageDTO);

    }

}

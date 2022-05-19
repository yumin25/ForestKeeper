package com.example.chat.api.controller;

import com.example.chat.application.dto.chat.ChatMessageDTO;
import com.example.chat.application.service.chat.ChatMessageService;
import com.example.chat.util.redis.RedisPublisher;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

@Api(value = "Chat API", tags = {"Chat"})
@CrossOrigin("*")
@Controller
@RequiredArgsConstructor
public class ChatController {
    private final RedisPublisher redisPublisher;
    private final ChatMessageService chatMessageService;

    @ApiOperation(value = "채팅방 입장")
    @MessageMapping("/enter")
    public void enter(String roomId) {
        chatMessageService.enterChatRoom(roomId);
    }

    @ApiOperation(value = "메시지 전송")
    @MessageMapping("/send")
    public void send(ChatMessageDTO chatMessageDTO) {
        chatMessageService.setChatMessageValue(chatMessageDTO.getRoomId(), chatMessageDTO);
        redisPublisher.publish(chatMessageService.getTopic(chatMessageDTO.getRoomId()), chatMessageDTO);
    }
}
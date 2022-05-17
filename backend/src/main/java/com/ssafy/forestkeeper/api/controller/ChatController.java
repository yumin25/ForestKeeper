package com.ssafy.forestkeeper.api.controller;

import com.ssafy.forestkeeper.application.dto.chat.ChatMessageDTO;
import com.ssafy.forestkeeper.application.service.chat.ChatMessageService;
import com.ssafy.forestkeeper.util.redis.RedisPublisher;
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
    @MessageMapping("/chat/enter")
    public void enter(String roomId) {

        chatMessageService.enterChatRoom(roomId);

    }

    @ApiOperation(value = "메시지 전송")
    @MessageMapping("/chat/room")
    public void send(ChatMessageDTO chatMessageDTO) {

        chatMessageService.setChatMessageValue(chatMessageDTO.getRoomId(), chatMessageDTO);

        redisPublisher.publish(chatMessageService.getTopic(chatMessageDTO.getRoomId()), chatMessageDTO);

    }

}


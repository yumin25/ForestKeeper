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
@CrossOrigin
@Controller
@RequiredArgsConstructor
public class ChatController {

    private final RedisPublisher redisPublisher;

    private final ChatMessageService chatMessageService;

    @MessageMapping("/chat/enter")
    @ApiOperation(value = "채팅방 입장")
    public void enter(String roomId) {

        chatMessageService.enterChatRoom(roomId);

    }

    @MessageMapping("/chat/room")
    @ApiOperation(value = "메시지 전송")
    public void send(ChatMessageDTO chatMessageDTO) {

        chatMessageService.setChatMessageValue(chatMessageDTO.getRoomId(), chatMessageDTO);

        redisPublisher.publish(chatMessageService.getTopic(chatMessageDTO.getRoomId()), chatMessageDTO);

    }

}


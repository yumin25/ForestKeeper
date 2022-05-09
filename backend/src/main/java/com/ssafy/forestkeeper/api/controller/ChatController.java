package com.ssafy.forestkeeper.api.controller;

import com.ssafy.forestkeeper.application.dto.request.chat.ChatMessageRequestDTO;
import com.ssafy.forestkeeper.application.service.chat.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
@Controller
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessagingTemplate template;

    private final ChatMessageService chatMessageService;

    @MessageMapping("/chat/enter")
    public void enter(ChatMessageRequestDTO chatMessageRequestDTO) {

        template.convertAndSend("/topic/room/" + chatMessageRequestDTO.getRoomId(), chatMessageService.enterChatRoom(chatMessageRequestDTO));

    }

    @MessageMapping("/chat/room")
//    public void send(ChatMessageRequestDTO chatMessageRequestDTO, @Header("Authorization") String accessToken) {
    public void send(ChatMessageRequestDTO chatMessageRequestDTO) {

//        System.out.println("AT : " + accessToken);
        System.out.println(chatMessageRequestDTO);

//        chatMessageService.sendChatMessage(chatMessageRequestDTO, accessToken);

        template.convertAndSend("/topic/room/" + chatMessageRequestDTO.getRoomId(), chatMessageRequestDTO);

    }

}


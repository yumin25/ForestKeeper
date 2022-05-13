package com.ssafy.forestkeeper.api.controller;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Api(value = "Chat Room API", tags = {"Chat Room"})
@CrossOrigin("*")
@Controller
@RequiredArgsConstructor
public class ChatRoomHomeController {

    @GetMapping("/chat/rooms")
    public String rooms() {
        return "room";
    }

    // 채팅방 입장 화면
    @GetMapping("/chat/room/enter/{roomId}")
    public String roomDetail(Model model, @PathVariable String roomId) {
        model.addAttribute("roomId", roomId);
        return "roomdetail";
    }

}

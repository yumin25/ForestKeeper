package com.ssafy.forestkeeper.api.controller;

import com.ssafy.forestkeeper.application.dto.request.chat.ChatRoomRegisterPostDTO;
import com.ssafy.forestkeeper.application.dto.request.chat.ChatRoomUserRequestDTO;
import com.ssafy.forestkeeper.application.dto.response.BaseResponseDTO;
import com.ssafy.forestkeeper.application.dto.response.chat.ChatRoomGetListWrapperResponseDTO;
import com.ssafy.forestkeeper.application.dto.response.chat.ChatRoomWithMessageResponseDTO;
import com.ssafy.forestkeeper.application.service.chat.ChatRoomService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(value = "Chat Room API", tags = {"Chat Room"})
@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat/room")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    @ApiOperation(value = "채팅방 생성")
    @PostMapping
    public ResponseEntity<? extends BaseResponseDTO> create(
            @ApiParam(value = "채팅방 정보") @RequestBody ChatRoomRegisterPostDTO chatRoomRegisterPostDTO
    ) {

        System.out.println(chatRoomRegisterPostDTO);
        chatRoomService.createChatRoom(chatRoomRegisterPostDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(BaseResponseDTO.of("채팅방이 생성되었습니다.", 201));

    }

    @ApiOperation(value = "채팅방 목록 조회")
    @GetMapping
    public ResponseEntity<? extends BaseResponseDTO> getList() {

        ChatRoomGetListWrapperResponseDTO chatRoomGetListWrapperResponseDTO = chatRoomService.getChatRoomList();

        return ResponseEntity
                .ok(ChatRoomGetListWrapperResponseDTO.of("채팅방 목록 조회에 성공했습니다.", 200, chatRoomGetListWrapperResponseDTO));

    }

    @ApiOperation(value = "채팅방 조회")
    @GetMapping("/{roomId}")
    public ResponseEntity<? extends BaseResponseDTO> get(@ApiParam(value = "채팅방 ID") @PathVariable String roomId) {

        ChatRoomWithMessageResponseDTO chatRoomWithMessageResponseDTO = chatRoomService.getChatRoom(roomId);

        return ResponseEntity.ok(ChatRoomWithMessageResponseDTO.of("채팅방 조회에 성공했습니다.", 200, chatRoomWithMessageResponseDTO));

    }

    @ApiOperation(value = "채팅방 인원 추가")
    @PatchMapping("/add/{roomId}")
    public ResponseEntity<? extends BaseResponseDTO> add(
            @ApiParam(value = "채팅방 ID") @PathVariable String roomId,
            @ApiParam(value = "채팅방에 추가할 유저") @RequestBody ChatRoomUserRequestDTO chatRoomUserRequestDTO
    ) throws Exception {

        chatRoomService.addUser(roomId, chatRoomUserRequestDTO);

        return ResponseEntity.ok(BaseResponseDTO.of("채팅방 인원 추가에 성공했습니다.", 200));

    }

    @ApiOperation(value = "채팅방 나가기")
    @DeleteMapping("/leave/{roomId}")
    public ResponseEntity<? extends BaseResponseDTO> leave(@ApiParam(value = "채팅방 ID") @PathVariable String roomId) {

        chatRoomService.leaveChatRoom(roomId);

        return ResponseEntity.ok(BaseResponseDTO.of("채팅방 나가기에 성공했습니다.", 200));

    }

}

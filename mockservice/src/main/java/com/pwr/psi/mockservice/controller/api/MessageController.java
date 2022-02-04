package com.pwr.psi.mockservice.controller.api;

import com.pwr.psi.mockservice.dto.MessageDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api")
public interface MessageController {
    @PostMapping("/message/send")
    ResponseEntity<MessageDto> sendMessage(@RequestBody MessageDto messageDto);
}

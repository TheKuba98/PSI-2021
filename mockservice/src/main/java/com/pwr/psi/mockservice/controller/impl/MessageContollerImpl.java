package com.pwr.psi.mockservice.controller.impl;

import com.pwr.psi.mockservice.controller.api.MessageController;
import com.pwr.psi.mockservice.dto.MessageDto;
import com.pwr.psi.mockservice.service.api.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageContollerImpl implements MessageController {

    @Autowired
    private MessageService messageService;

    @Override
    public ResponseEntity<MessageDto> sendMessage(MessageDto messageDto) {
        return ResponseEntity.ok(messageService.sendMessage(messageDto));
    }
}


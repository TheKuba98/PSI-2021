package com.pwr.psi.mockservice.service.impl;

import com.pwr.psi.mockservice.dto.MessageDto;
import com.pwr.psi.mockservice.service.api.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class MessageServiceImpl implements MessageService {

    private final static Logger LOG = LoggerFactory.getLogger(MessageServiceImpl.class);

    @Override
    public MessageDto sendMessage(MessageDto messageDto) {
        LOG.info("Received message to be sent...");
        messageDto.setMessageStatus("SENT");
        messageDto.setSendDate(Instant.now());
        return messageDto;
    }
}

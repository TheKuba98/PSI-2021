package com.pwr.psi.mockservice.service.api;

import com.pwr.psi.mockservice.dto.MessageDto;

public interface MessageService {
    MessageDto sendMessage(MessageDto messageDto);
}

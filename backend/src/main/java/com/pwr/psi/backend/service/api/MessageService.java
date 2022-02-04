package com.pwr.psi.backend.service.api;

import com.pwr.psi.backend.model.dto.MessageExternalDto;

public interface MessageService {

    MessageExternalDto sendMessage(String receiver, String sender, String message);
}

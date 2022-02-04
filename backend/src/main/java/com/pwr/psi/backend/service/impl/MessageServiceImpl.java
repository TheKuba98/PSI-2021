package com.pwr.psi.backend.service.impl;

import com.pwr.psi.backend.model.dto.MessageExternalDto;
import com.pwr.psi.backend.service.api.MessageService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
@Service
public class MessageServiceImpl implements MessageService {

    private static final Logger LOG = LoggerFactory.getLogger(MessageServiceImpl.class);
    private RestTemplate restTemplate = new RestTemplate();
    private String url = "http://localhost:8081/api/message/send";

    @Override
    public MessageExternalDto sendMessage(String receiver, String sender, String message) {

        LOG.info("Sending message to external system");
        ResponseEntity<MessageExternalDto> response = restTemplate.exchange(url, HttpMethod.POST,
                new HttpEntity<MessageExternalDto>(createMessage(receiver,sender,message)
                        ,createHeaders("technicaluser", "password")),
                MessageExternalDto.class);
        return response.getBody();
    }

    private MessageExternalDto createMessage(String receiver, String sender, String message) {
        MessageExternalDto messageExternalDto = new MessageExternalDto();
        messageExternalDto.setMessage(message);
        messageExternalDto.setReceiver(receiver);
        messageExternalDto.setSender(sender);
        return messageExternalDto;
    }

    private HttpHeaders createHeaders(String username, String password) {
        return new HttpHeaders() {{
            String auth = username + ":" + password;
            byte[] encodedAuth = Base64.encodeBase64(
                    auth.getBytes(StandardCharsets.US_ASCII));
            String authHeader = "Basic " + new String(encodedAuth);
            set("Authorization", authHeader);
        }};
    }
}

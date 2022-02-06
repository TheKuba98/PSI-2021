package com.pwr.psi.backend.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pwr.psi.backend.model.dto.MessageExternalDto;
import com.pwr.psi.backend.service.api.MessageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@SpringBootTest
public class MessageServiceTests {

    @Autowired
    private MessageService messageService;

    @Autowired
    private RestTemplate restTemplate;


    private MockRestServiceServer mockServer;
    private ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void init() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
        mapper.findAndRegisterModules();
    }

    @Test
    public void shouldSendMessageFromExternalSystem() throws JsonProcessingException, URISyntaxException {
        //given
        MessageExternalDto messageExternalDto = new MessageExternalDto();
        messageExternalDto.setSender("sender");
        messageExternalDto.setReceiver("sender");
        messageExternalDto.setMessage("message");
        messageExternalDto.setMessageStatus("SEND");
        messageExternalDto.setSendDate(Instant.now());

        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://localhost:8081/api/message/send")))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(messageExternalDto))
                );
        //when
        MessageExternalDto messageExternalDto1 = messageService.sendMessage(messageExternalDto.getReceiver(), messageExternalDto.getSender(), messageExternalDto.getMessage());

        //then
        mockServer.verify();
        assertThat(messageExternalDto1).extracting(MessageExternalDto::getMessage,
                MessageExternalDto::getMessageStatus,
                MessageExternalDto::getReceiver,
                MessageExternalDto::getSender,
                MessageExternalDto::getSendDate).contains(messageExternalDto.getMessage(),
                    messageExternalDto.getMessageStatus(),
                    messageExternalDto.getReceiver(),
                    messageExternalDto.getSender(),
                    messageExternalDto.getSendDate());
    }

}

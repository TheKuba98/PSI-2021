package com.pwr.psi.backend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageExternalDto {
    private String receiver;
    private Instant sendDate;
    private String sender;
    private String message;
    private String messageStatus;

    @Override
    public String toString() {
        return "MessageExternalDto{" +
                "receiver='" + receiver + '\'' +
                ", sendDate=" + sendDate +
                ", sender='" + sender + '\'' +
                ", message='" + message + '\'' +
                ", messageStatus='" + messageStatus + '\'' +
                '}';
    }
}

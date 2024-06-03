package com.luciorim.socnetserver.services;

import com.luciorim.socnetserver.dtos.RequestMessageDto;
import com.luciorim.socnetserver.dtos.ResponseMessageDto;

import java.util.List;

public interface MessageService {
    List<ResponseMessageDto> getAllMessages();

    void createMessage(RequestMessageDto responseMessageDto);

    void deleteMessage(Long id);

    void updateMessage(Long id, RequestMessageDto responseMessageDto);
}

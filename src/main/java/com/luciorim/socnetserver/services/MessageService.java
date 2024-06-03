package com.luciorim.socnetserver.services;

import com.luciorim.socnetserver.dtos.MessageDto;

import java.util.List;

public interface MessageService {
    List<MessageDto> getAllMessages();

    void createMessage(MessageDto messageDto);

    void deleteMessage(Long id);

    void updateMessage(Long id, MessageDto messageDto);
}

package com.luciorim.socnetserver.services.impl;

import com.luciorim.socnetserver.dtos.MessageDto;
import com.luciorim.socnetserver.entities.Message;
import com.luciorim.socnetserver.mappers.MessageMapper;
import com.luciorim.socnetserver.repositories.MessageRepository;
import com.luciorim.socnetserver.services.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final MessageMapper messageMapper;

    @Override
    public List<MessageDto> getAllMessages() {
        return messageRepository.findAll()
                .stream()
                .map(messageMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @SneakyThrows
    public void createMessage(MessageDto messageDto){
        if(!messageDto.getText().trim().isEmpty()){
            log.error("Requested empty message");
            throw new BadRequestException("Message is empty");
        }
        var message = messageMapper.toEntity(messageDto);

        log.info("Sending message: {}", message);
        messageRepository.save(message);
    }

    @Override
    @SneakyThrows
    public void deleteMessage(Long id) {
        var message = messageRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Message not found"));

        log.info("Deleting message: {}", message);
        messageRepository.delete(message);
    }

    @Override
    @SneakyThrows
    public void updateMessage(Long id, MessageDto messageDto) {

        if(!messageDto.getText().trim().isEmpty()){
            log.error("Requested empty message");
            throw new BadRequestException("Message is empty");
        }

        var message = messageRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Message not found"));

        log.info("Updating message: {}", message);
        message.setText(messageDto.getText());
        messageRepository.save(message);

    }


}

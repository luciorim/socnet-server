package com.luciorim.socnetserver.mappers;

import com.luciorim.socnetserver.dtos.MessageDto;
import com.luciorim.socnetserver.entities.Message;
import org.mapstruct.Mapper;

import java.time.LocalDateTime;

import static com.luciorim.socnetserver.constants.TimeConstant.ZONE_ID;

@Mapper(componentModel = "spring")
public interface MessageMapper extends BaseMapper<Message, MessageDto>{

    @Override
    default Message toEntity(MessageDto messageDto) {
        return Message.builder()
                .text(messageDto.getText())
                .sentAt(LocalDateTime.now(ZONE_ID))
                .build();
    }

}

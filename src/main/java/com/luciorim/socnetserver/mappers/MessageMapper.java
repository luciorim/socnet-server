package com.luciorim.socnetserver.mappers;

import com.luciorim.socnetserver.dtos.ResponseMessageDto;
import com.luciorim.socnetserver.entities.Message;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MessageMapper extends BaseMapper<Message, ResponseMessageDto>{

    @Override
    default ResponseMessageDto toDto(Message message) {
        if(message == null) {
            return null;
        }
        ResponseMessageDto responseMessageDto = new ResponseMessageDto();
        responseMessageDto.setId(message.getId());
        responseMessageDto.setText(message.getText());
        responseMessageDto.setLink(message.getLink());
        responseMessageDto.setLinkTitle(message.getLinkTitle());
        responseMessageDto.setLinkDescription(message.getLinkDescription());
        responseMessageDto.setLinkCover(message.getLinkCover());
        return responseMessageDto;
    }

}

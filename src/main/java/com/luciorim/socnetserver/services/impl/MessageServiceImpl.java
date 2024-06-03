package com.luciorim.socnetserver.services.impl;

import com.luciorim.socnetserver.dtos.RequestMessageDto;
import com.luciorim.socnetserver.dtos.ResponseMessageDto;
import com.luciorim.socnetserver.dtos.MetaDto;
import com.luciorim.socnetserver.entities.Message;
import com.luciorim.socnetserver.mappers.MessageMapper;
import com.luciorim.socnetserver.repositories.MessageRepository;
import com.luciorim.socnetserver.services.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

import static com.luciorim.socnetserver.constants.Regex.IMAGE_REGEX;
import static com.luciorim.socnetserver.constants.Regex.URL_REGEX;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final MessageMapper messageMapper;

    @Override
    public List<ResponseMessageDto> getAllMessages() {
        return messageRepository.findAll()
                .stream()
                .map(messageMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @SneakyThrows
    public void createMessage(RequestMessageDto requestMessageDto){
        if(requestMessageDto.getText().trim().isEmpty()){
            log.error("Requested empty message");
            throw new BadRequestException("Message is empty");
        }
        Message message = new Message();
        message.setText(requestMessageDto.getText());
        fillMetadata(message);

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
    public void updateMessage(Long id, RequestMessageDto requestMessageDto) {

        if(!requestMessageDto.getText().trim().isEmpty()){
            log.error("Requested empty message");
            throw new BadRequestException("Message is empty");
        }

        var message = messageRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Message not found"));

        fillMetadata(message);
        log.info("Updating message: {}", message);
        message.setText(requestMessageDto.getText());
        messageRepository.save(message);

    }

    private void fillMetadata(Message message) throws IOException {
        String text = message.getText();
        Matcher matcher = URL_REGEX.matcher(text);

        if(matcher.find()){
            String url = text.substring(matcher.start(), matcher.end());

            matcher = IMAGE_REGEX.matcher(url);

            message.setLink(url);

            if(matcher.find()){
                message.setLinkCover(url);
            }else if(!url.contains("youtu")){
                MetaDto metaDto = getMetadata(url);

                message.setLinkCover(metaDto.getCover());
                message.setLinkDescription(metaDto.getDescription());
                message.setLinkTitle(metaDto.getTitle());
            }
        }
    }

    private MetaDto getMetadata(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
        log.info(doc.title());
        Elements title = doc.select("meta[name$=title], meta[property$=title]");
        Elements description = doc.select("meta[name$=description], meta[property$=description]");
        Elements cover = doc.select("meta[name$=image], meta[property$=image ]");

        return MetaDto.builder()
                .title(getContent(title.first()))
                .description(getContent(description.first()))
                .cover(getContent(cover.getFirst()))
                .build();
    }

    private String getContent(Element element){
        return element == null ? "" : element.attr("content");
    }



}

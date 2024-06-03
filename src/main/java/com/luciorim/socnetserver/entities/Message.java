package com.luciorim.socnetserver.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "messages")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(of = {"id"})
@ToString(of = {"id", "text"})
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime sentAt;

    //if message has a link in text
    private String link;
    private String linkTitle;
    private String linkDescription;
    private String linkCover;
}

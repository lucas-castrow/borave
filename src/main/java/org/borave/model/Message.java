package org.borave.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

//        id: Identificador único da mensagem.
//        senderId: ID do remetente da mensagem.
//        recipientId: ID do destinatário da mensagem.
//        content: Conteúdo da mensagem (texto, imagem, vídeo, etc.).
//        timestamp: Data e hora de envio da mensagem.
//        read: Indicador se a mensagem foi lida pelo destinatário.
public class Message {
    @Id
    private ObjectId id;
    private ObjectId senderId;
    private ObjectId recipientId;
    private String content;
    private LocalDateTime timestamp;
    private boolean read;
}

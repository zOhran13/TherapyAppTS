//package ba.unsa.etf.ts.Therapy.dto.Mapper;
//
//
//import ba.unsa.etf.ts.Therapy.dto.MessageDto;
//import ba.unsa.etf.ts.Therapy.models.Message;
//import ba.unsa.etf.ts.Therapy.models.UserEntity;
//import ba.unsa.etf.ts.Therapy.repository.UserRepo;
//import org.mapstruct.Mapper;
//import org.mapstruct.factory.Mappers;
//import org.springframework.stereotype.Component;
//
//@Component
//public class MessageMapper {
//    private final UserRepo userRepo;
//
//    public MessageMapper(UserRepo userRepo) {
//        this.userRepo = userRepo;
//    }
//
//    public MessageDto toDto(Message message) {
//        if (message == null) {
//            return null;
//        }
//
//        MessageDto dto = new MessageDto();
//        dto.setContent(message.getContent());
//        dto.setCreatedAt(message.getCreatedAt());
//        dto.setSenderId(message.getSender());
//        dto.setRecipientId(message.getRecipient());
//        return dto;
//    }
//
//    public Message fromDto(MessageDto messageDto) {
//        if (messageDto == null) {
//            return null;
//        }
//
//        Message message = new Message();
//        message.setContent(messageDto.getContent());
//        message.setCreatedAt(messageDto.getCreatedAt());
//        User sender = userRepo.findById(messageDto.getSenderId())
//                .orElseThrow(() -> new IllegalArgumentException("Sender not found with ID: " + messageDto.getSenderId()));
//        User recipient = userRepo.findById(messageDto.getRecipientId())
//                .orElseThrow(() -> new IllegalArgumentException("Recipient not found with ID: " + messageDto.getRecipientId()));
//        message.setSender(sender);
//        message.setRecipient(recipient);
//        return message;
//    }
//}

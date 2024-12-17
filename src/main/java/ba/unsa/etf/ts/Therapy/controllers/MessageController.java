package ba.unsa.etf.ts.Therapy.controllers;

import ba.unsa.etf.ts.Therapy.dto.MessageDto;
import ba.unsa.etf.ts.Therapy.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/messages")
public class MessageController {

    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<MessageDto>> getAllMessagesForPatientOrPsychologist(@PathVariable String patientId) {
        List<MessageDto> messages = messageService.getAllMessagesForPatientOrPsychologist(patientId);
        return ResponseEntity.ok(messages);
    }

    @PostMapping("/create/{patientId}")
    public ResponseEntity<MessageDto> createMessage(@RequestBody MessageDto message, @PathVariable String patientId) {
        MessageDto createdMessage = messageService.createMessage(message, patientId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMessage);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteMessage(@RequestBody MessageDto message) {
        messageService.deleteMessage(message);
        return ResponseEntity.ok("Message deleted successfully.");
    }

    private Map<String, Object> createErrorResponse(String status, String message) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("status", status);
        errorResponse.put("errors", createErrorDetails(message));
        return errorResponse;
    }

    private Map<String, Object> createErrorDetails(String message) {
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("timestamp", LocalDateTime.now());
        errorDetails.put("message", message);
        errorDetails.put("details", message);
        return errorDetails;
    }
}

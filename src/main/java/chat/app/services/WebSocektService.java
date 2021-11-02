package chat.app.services;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import chat.app.models.Message;

@Service
public class WebSocektService {
  private final SimpMessagingTemplate simpMessagingTemplate;

  public WebSocektService(SimpMessagingTemplate simpMessagingTemplate) {
    this.simpMessagingTemplate = simpMessagingTemplate;
  }

  public void sendMessage(String channelId, Message message) {
    simpMessagingTemplate.convertAndSend("/topic/" + channelId, message);
  }
}
package chat.app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import chat.app.models.Message;
import chat.app.models.MessageBody;
import chat.app.models.User;
import chat.app.services.ChannelService;
import chat.app.services.MessageService;

@RestController
@RequestMapping("/messages")
public class MessageController {
  @Autowired
  private MessageService messageService;
  @Autowired
  private ChannelService channelService;

  @GetMapping
  public List<Message> getMessageFromChannel(@RequestParam(required = false) Integer page,
      @RequestParam(required = false) String channel) {
    return messageService.getMessageFromChannel(page, channel);
  }

  @PostMapping
  public Message createMessage(@RequestHeader("Authorization") String authorization,
      @RequestBody MessageBody messageBody) {
    User user = channelService.getUserFromRequest(authorization);
    return messageService.createMessage(user, messageBody);
  }

  @DeleteMapping("/{id}")
  public String deleteMessage(@RequestHeader("Authorization") String authorization, @PathVariable String id) {
    User user = channelService.getUserFromRequest(authorization);
    messageService.deleteMessage(user, id);
    return "deleted";
  }
}

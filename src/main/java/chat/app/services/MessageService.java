package chat.app.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;

import chat.app.errors.badrequest.BadRequestException;
import chat.app.errors.notfound.NotFoundException;
import chat.app.errors.forbidden.FrobiddenException;
import chat.app.models.Channel;
import chat.app.models.Message;
import chat.app.models.MessageBody;
import chat.app.models.User;
import chat.app.repositories.ChannelRepository;
import chat.app.repositories.MessageRepository;

@Service
public class MessageService {
  @Autowired
  private MessageRepository messageRepository;
  @Autowired
  private ChannelRepository channelRepository;
  @Autowired
  private WebSocektService webSocektService;

  public List<Message> getMessageFromChannel(Integer page, String channel) {
    return messageRepository.findAllInChannel(channel == null ? "general" : channel, PageRequest.of(page == null ? 0 : page, 20));
  }

  public Message createMessage(User user, MessageBody messageBody) {
    if (messageBody.getMessage() == null) {
      throw new BadRequestException("Cannot post empty message");
    }
    Optional<Channel> channelOptional = channelRepository.findById(messageBody.getChannelId());
    if (channelOptional.isEmpty()) {
      throw new NotFoundException("Channel not found");
    }
    Message message = new Message(messageBody.getMessage(), channelOptional.get(), user);
    message = messageRepository.insert(message);
    Message socektMessage = new Message(message.getMessage(), null, user);
    socektMessage.setId(message.getId());
    webSocektService.sendMessage(channelOptional.get().getId(), socektMessage);
    return message;
  }

  public void deleteMessage(User user, String id) {
    Optional<Message> messageOptional = messageRepository.findById(id);
    if (!messageOptional.isEmpty()) {
      Message message = messageOptional.get();
      if (!message.getChannel().getCreatedBy().getId().equals(user.getId()) && !message.getUser().getId().equals(user.getId())) {
        throw new FrobiddenException("Not allow to delete message");
      }
      messageRepository.deleteById(id);
    }
  } 
}

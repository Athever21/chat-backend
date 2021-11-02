package chat.app.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import chat.app.errors.duplicate.DuplicateException;
import chat.app.errors.notfound.NotFoundException;
import chat.app.errors.unauthorized.UnauthorizedException;
import chat.app.errors.badrequest.BadRequestException;
import chat.app.errors.forbidden.FrobiddenException;
import chat.app.jwt.JwtUtils;
import chat.app.models.Channel;
import chat.app.models.ChannelBody;
import chat.app.models.User;
import chat.app.repositories.ChannelRepository;
import chat.app.repositories.MessageRepository;
import chat.app.repositories.UserRepository;

@Service
public class ChannelService {
  @Autowired
  private ChannelRepository channelRepository;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private MessageRepository messageRepository;

  public List<Channel> getAllChannels() {
    return channelRepository.findAll();
  }

  public Channel getChannel(String id) {
    Optional<Channel> channel = channelRepository.findById(id);
    if (channel.isEmpty()) {
      throw new NotFoundException("Channel not found");
    }
    return channel.get();
  }

  public User getUserFromRequest(String auth) {
    if (auth == null || !auth.startsWith("Bearer ", 0)) {
      throw new UnauthorizedException("Invalid auth token");
    }
    String id = JwtUtils.getIdFromToken(auth.substring(7), false);
    Optional<User> user = userRepository.findById(id);
    if (user.isEmpty()) {
      throw new NotFoundException("User not found");
    }
    return user.get();
  }

  public Channel createChannel(User creator, ChannelBody channelBody) {
    if (channelRepository.findByName(channelBody.getName()) != null) {
      throw new DuplicateException("Channel already in use.");
    }
    Channel channel = new Channel(channelBody.getName(), creator);
    return channelRepository.insert(channel);
  }

  public Channel changeChannel(String id, ChannelBody channelBody, User user) {
    Optional<Channel> channelOptional = channelRepository.findById(id);
    if (channelOptional.isEmpty()) {
      throw new NotFoundException("Channel not found");
    }
    if (channelBody.getType() == null) {
      throw new BadRequestException("Missing change type");
    }
    Channel channel = channelOptional.get();
    System.out.println(channelRepository.findByMember(user.getId()));
    switch(channelBody.getType()) {
      case "change_name": {
        if (!user.getId().equals(channel.getCreatedBy().getId())) {
          throw new FrobiddenException("Not allowed to change this channel");
        }
        if (channelBody.getName() != null) {
          channel.setName(channelBody.getName());
        }
        break;
      }
      case "add_member": {
        if (!channel.getMembers().contains(user)) {
          channel.getMembers().add(user);
        }
        break;
      }
      case "remove_member": {
        if (channel.getMembers().contains(user)) {
          channel.getMembers().remove(user);
        }
        break;
      }
    }
    return channelRepository.save(channel);
  }

  public void deleteChannel(String id, User user) {
    Optional<Channel> channelOptional = channelRepository.findById(id);
    if (!channelOptional.isEmpty()) {
      Channel channel = channelOptional.get();
      if (!channel.getCreatedBy().getId().equals(user.getId())) {
        throw new FrobiddenException("Unable to delete this channel");
      }
      messageRepository.deleteByChannel(id);
      channelRepository.deleteById(id);
    }
  }
}

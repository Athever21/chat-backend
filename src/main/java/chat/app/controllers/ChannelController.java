package chat.app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import chat.app.models.Channel;
import chat.app.models.ChannelBody;
import chat.app.models.User;
import chat.app.services.ChannelService;

@RestController
@RequestMapping("/channels")
public class ChannelController {
  @Autowired
  private ChannelService channelService;
  
  @GetMapping
  public List<Channel> getChannels() {
    return channelService.getAllChannels();
  }

  @PostMapping
  public Channel createChannel(@RequestHeader("Authorization") String authorization, @RequestBody ChannelBody channelBody) {
    User user = channelService.getUserFromRequest(authorization);
    return channelService.createChannel(user, channelBody);
  }

  @GetMapping("/{id}")
  public Channel getChannel(@PathVariable String id) {
    return channelService.getChannel(id);
  }

  @PutMapping("/{id}")
  public Channel changeChannel(@RequestHeader("Authorization") String authorization,@PathVariable String id, @RequestBody ChannelBody channelBody) {
    User user = channelService.getUserFromRequest(authorization);
    return channelService.changeChannel(id, channelBody, user);
  }

  @DeleteMapping("/{id}")
  public String deleteChannel(@RequestHeader("Authorization") String authorization,@PathVariable String id) {
    User user = channelService.getUserFromRequest(authorization);
    channelService.deleteChannel(id, user);
    return "deleted";
  }
}

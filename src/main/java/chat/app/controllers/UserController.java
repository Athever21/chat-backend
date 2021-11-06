package chat.app.controllers;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import chat.app.models.TokenResponse;
import chat.app.models.User;
import chat.app.models.UserBody;
import chat.app.services.ChannelService;
import chat.app.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
  @Autowired
  private UserService userService;
  @Autowired
  private ChannelService channelService;

  @GetMapping()
  public List<User> getUsers() {
    return userService.getAllUsers();
  }

  @PostMapping()
  public User createUser(@RequestBody UserBody user) {
    return userService.createUser(user.getUsername(), user.getPassword());
  }

  @PostMapping("/refresh_token")
  public TokenResponse refreshToken(@CookieValue(name = "refresh_token", defaultValue = "") String refreshCookie) {
    return userService.refreshToken(refreshCookie);
  }

  @PostMapping("/login")
  public TokenResponse loginUser(HttpServletResponse res, @RequestBody UserBody user) {
    return userService.loginUser(res, user);
  }

  @PutMapping("/{id}")
  public User changeUser(@RequestHeader("Authorization") String authorization, @PathVariable String id,
      @RequestBody UserBody userBody) {
    User user = channelService.getUserFromRequest(authorization);
    return userService.changeUser(user, id, userBody);
  }

  @DeleteMapping("/{id}")
  public String changeUser(@RequestHeader("Authorization") String authorization, @PathVariable String id) {
    User user = channelService.getUserFromRequest(authorization);
    userService.deleteUser(id, user);
    return "deleted";
  }
}

package chat.app.services;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import chat.app.errors.duplicate.DuplicateException;
import chat.app.errors.forbidden.FrobiddenException;
import chat.app.errors.notfound.NotFoundException;
import chat.app.errors.unauthorized.UnauthorizedException;
import chat.app.errors.badrequest.BadRequestException;
import chat.app.jwt.Jwt;
import chat.app.jwt.JwtUtils;
import chat.app.models.User;
import chat.app.models.UserBody;
import chat.app.repositories.ChannelRepository;
import chat.app.repositories.MessageRepository;
import chat.app.repositories.UserRepository;

@Service
public class UserService {
  @Autowired
  private PasswordEncoder passwordEncoder;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private MessageRepository messageRepository;
  @Autowired
  private ChannelRepository channelRepository;

  public User createUser(String username, String password) {
    password = passwordEncoder.encode(password);
    if (userRepository.findByUsername(username) != null) {
      throw new DuplicateException("Username already in use.");
    }
    User user = new User(username, password);
    return userRepository.insert(user);
  }

  public List<User> getAllUsers() {
    return userRepository.findAll();
  }

  public Jwt loginUser(HttpServletResponse res,UserBody logUser) {
    User user = userRepository.findByUsername(logUser.getUsername());
    if (user == null) {
      throw new NotFoundException("User not found.");
    }
    if (!passwordEncoder.matches(logUser.getPassword(), user.getPassword())) {
      throw new BadRequestException("Invalid password.");
    }
    Cookie refreshCookie = JwtUtils.createRefreshToken(user.getId());
    res.addCookie(refreshCookie);
    return JwtUtils.generateToken(user.getId(), false);
  }

  public Jwt refreshToken(String refreshToken) {
    if (refreshToken == "") {
      throw new UnauthorizedException("Invalid refresh token");
    }
    String id = JwtUtils.getIdFromToken(refreshToken, true);
    Optional<User> user = userRepository.findById(id);
    if (user.isEmpty()) {
      throw new NotFoundException("User not found");
    }
    return JwtUtils.generateToken(user.get().getId(), false);
  }

  public User changeUser(User user, String id, UserBody userBody) {
    if (!user.getId().equals(id)) {
      throw new FrobiddenException("Not allowed to change this user");
    }
    if (userBody.getUsername() != null) {
      if (userRepository.findByUsername(userBody.getUsername()) != null) {
        throw new BadRequestException("Username already in use");
      }
      user.setUsername(userBody.getUsername());
    }
    if (userBody.getPassword() != null) {
      user.setPassword(passwordEncoder.encode(userBody.getPassword()));
    }
    if (userBody.getImg() != null) {
      user.setImg(userBody.getImg());
    }
    return userRepository.save(user);
  }

  public void deleteUser(String id, User user) {
    if (!id.equals(user.getId())) {
      throw new FrobiddenException("Not allowed to delete user");
    }
    messageRepository.deleteByUser(id);
    channelRepository.deleteByUser(id);
    userRepository.deleteById(id);
  }
}

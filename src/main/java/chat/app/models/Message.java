package chat.app.models;

import java.util.Date;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Entity
public class Message {
  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private String id;

  private String message;

  @DBRef
  private Channel channel;
  @DBRef
  private User user;

  private Date createdAt;

  public Message() {
  }

  public Message(String message, Channel channel, User user) {
    this.message = message;
    this.channel = channel;
    this.user = user;
    this.createdAt = new Date();
  }

  public String getId() {
    return this.id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getMessage() {
    return this.message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public Channel getChannel() {
    return this.channel;
  }

  public void setChannel(Channel channel) {
    this.channel = channel;
  }

  public Date getCreatedAt() {
    return this.createdAt;
  }

  public User getUser() {
    return this.user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Message id(String id) {
    setId(id);
    return this;
  }

  public Message message(String message) {
    setMessage(message);
    return this;
  }

  public Message channel(Channel channel) {
    setChannel(channel);
    return this;
  }

  public Message user(User user) {
    setUser(user);
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this)
      return true;
    if (!(o instanceof Message)) {
      return false;
    }
    Message message = (Message) o;
    return Objects.equals(id, message.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public String toString() {
    return "{" + " id='" + getId() + "'" + ", message='" + getMessage() + ", user='" + getUser() + "'" + "}";
  }
}

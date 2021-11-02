package chat.app.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Entity
public class Channel {
  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private String id;

  private String name;

  @DBRef
  private User createdBy;

  @DBRef
  private List<User> members;

  public Channel(String name, User createdBy) {
    this.name = name;
    this.createdBy = createdBy;
    List<User> m = new ArrayList<>();
    m.add(createdBy);
    this.members = m;
  }

  public String getId() {
    return this.id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public User getCreatedBy() {
    return this.createdBy;
  }

  public void setCreatedBy(User createdBy) {
    this.createdBy = createdBy;
  }

  public List<User> getMembers() {
    return this.members;
  }

  public void setMembers(List<User> members) {
    this.members = members;
  }

  public Channel id(String id) {
    setId(id);
    return this;
  }

  public Channel createdBy(User createdBy) {
    setCreatedBy(createdBy);
    return this;
  }

  public Channel members(List<User> members) {
    setMembers(members);
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this)
      return true;
    if (!(o instanceof Channel)) {
      return false;
    }
    Channel channel = (Channel) o;
    return Objects.equals(id, channel.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public String toString() {
    return "{" +
      " id='" + getId() + "'" +
      ", name='" + getName() + "'" +
      ", createdBy='" + getCreatedBy() + "'" +
      ", members='" + getMembers() + "'" +
      "}";
  }

}

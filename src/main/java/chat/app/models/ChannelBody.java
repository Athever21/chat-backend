package chat.app.models;

import java.util.List;

public class ChannelBody {
  private String name;
  private List<User> members;
  private String type;

  public ChannelBody() {
  }

  public ChannelBody(String name, List<User> members, String type) {
    this.name = name;
    this.members = members;
    this.type = type;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<User> getMembers() {
    return this.members;
  }

  public void setMembers(List<User> members) {
    this.members = members;
  }

  public String getType() {
    return this.type;
  }

  public void setType(String type) {
    this.type = type;
  }
}
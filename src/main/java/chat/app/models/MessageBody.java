package chat.app.models;

public class MessageBody {
  private String channelId;
  private String message;

  public MessageBody() {
  }

  public MessageBody(String channelId, String message) {
    this.channelId = channelId;
    this.message = message;
  }

  public String getChannelId() {
    return this.channelId;
  }

  public void setChannelId(String channelId) {
    this.channelId = channelId;
  }

  public String getMessage() {
    return this.message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

}

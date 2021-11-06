package chat.app.models;

public class TokenResponse {
  private User user;
  private String token;

  public TokenResponse(User user, String token) {
    this.user = user;
    this.token = token;
  }

  public User getUser() {
    return this.user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public String getToken() {
    return this.token;
  }

  public void setToken(String token) {
    this.token = token;
  }
}

package Body;

public class RegUser {
    private String success;
    private User user;
    private String accessToken;
    private String refreshToken;
    private String errorMessage;

    public RegUser(String accessToken) {
        this.accessToken = accessToken;
    }
    public RegUser(){

    }
    public String isSuccess() {
        return success;
    }

    public User getUser() {
        return user;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}

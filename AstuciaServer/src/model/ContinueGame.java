package model;

public class ContinueGame {

    public String type = "ContinueGame";
    private String body;

    public ContinueGame() {}

    public ContinueGame(String body) {
        this.body = body;
    }
    public String getBody() {
        return body;
    }
    public void setBody(String body) {
        this.body = body;
    }
    public String getType() { return type; }
}

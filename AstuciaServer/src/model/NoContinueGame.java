package model;

public class NoContinueGame {

    public String type = "NoContinueGame";
    private String body;

    public NoContinueGame() {}

    public NoContinueGame(String body) {
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

package model;

public class Win {
    public String type = "Win";
    private String body;

    public Win() {}

    public Win(String body) {
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

package model;

public class GiveUp {
    public String type = "GiveUp";
    private String body;

    public GiveUp() {}

    public GiveUp(String body) {
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

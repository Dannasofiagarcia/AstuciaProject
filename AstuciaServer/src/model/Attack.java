package model;

public class Attack {

    public String type = "Attack";
    private String body;

    public Attack() {}

    public Attack(String body) {
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

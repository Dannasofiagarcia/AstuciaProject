package model;

public class Restart {

    public String type = "Restart";
    private String body;

    public Restart() {}

    public Restart(String body) {
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

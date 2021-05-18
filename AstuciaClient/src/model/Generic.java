package model;

public class Generic {

    public String type;
    private String body;

    public Generic(String body) {
        this.body = body;
    }
    public String getBody() {
        return body;
    }
    public void setBody(String body) {
        this.body = body;
    }
    public String getType() { return type; }
    private void type(){
        if(body.startsWith("A")){
            type = "Attack";
        } else if (body.equals("GiveUp")){
            type = "GiveUp";
        } else if (body.equals("Restart")){
            type = "Restart";
        } else if (body.equals("Win")) {
            type = "Win";
        } else if(body.equals("ContinueGame")){
            type = "ContinueGame";
        } else if(body.equals("NoContinueGame")){
            type = "NoContinueGame";
        } else{
            type = "Message";
        }
    }
}

package control;

import comm.Receptor.OnMessageListener;

import java.util.*;

import com.google.gson.Gson;

import comm.TCPConnection;
import javafx.application.Platform;
import javafx.scene.control.*;
import model.*;
import model.Generic;
import sun.net.www.content.text.*;
import view.GameWindow;

public class GameController implements OnMessageListener {

    private GameWindow view;
    private TCPConnection connection;
    private int fil;
    private int col;

    public GameController(GameWindow view) {
        this.view = view;
        init();
    }

    public void init() {
        connection = TCPConnection.getInstance();
        connection.setListenerOfMessages(this);

        fil = (int) (3 * Math.random());
        col = (int) (3 * Math.random());
        view.drawWeakPointInRadar(fil, col);

        view.getSendNameBtn().setOnAction(event -> {
            Gson gson = new Gson();
            String json = gson.toJson(new Message(view.getNameTF().getText()));
            TCPConnection.getInstance().getEmisor().sendMessage(json);
            view.disableSendName();
        });

        view.getAtaque()[0][0].setOnAction(event -> {
            Gson gson = new Gson();
            String json = gson.toJson(new Attack("A00"));
            TCPConnection.getInstance().getEmisor().sendMessage(json);

			view.disableAttackInRadar(0,0);
            view.notMyTurn();
        });
        view.getAtaque()[0][1].setOnAction(event -> {
            Gson gson = new Gson();
            String json = gson.toJson(new Attack("A01"));
            TCPConnection.getInstance().getEmisor().sendMessage(json);
            view.disableAttackInRadar(0,1);
            view.notMyTurn();
        });
        view.getAtaque()[0][2].setOnAction(event -> {
            Gson gson = new Gson();
            String json = gson.toJson(new Attack("A02"));
            TCPConnection.getInstance().getEmisor().sendMessage(json);
            view.disableAttackInRadar(0,2);
            view.notMyTurn();
        });
        view.getAtaque()[1][0].setOnAction(event -> {
            Gson gson = new Gson();
            String json = gson.toJson(new Attack("A10"));
            TCPConnection.getInstance().getEmisor().sendMessage(json);
            view.disableAttackInRadar(1,0);
            view.notMyTurn();
        });
        view.getAtaque()[1][1].setOnAction(event -> {
            Gson gson = new Gson();
            String json = gson.toJson(new Attack("A11"));
            TCPConnection.getInstance().getEmisor().sendMessage(json);
            view.disableAttackInRadar(1,1);
            view.notMyTurn();
        });
        view.getAtaque()[2][2].setOnAction(event -> {
            Gson gson = new Gson();
            String json = gson.toJson(new Attack("A22"));
            TCPConnection.getInstance().getEmisor().sendMessage(json);
            view.disableAttackInRadar(2,2);
            view.notMyTurn();
        });
        view.getAtaque()[2][0].setOnAction(event -> {
            Gson gson = new Gson();
            String json = gson.toJson(new Attack("A20"));
            TCPConnection.getInstance().getEmisor().sendMessage(json);
            view.disableAttackInRadar(2,0);
            view.notMyTurn();
        });
        view.getAtaque()[2][1].setOnAction(event -> {
            Gson gson = new Gson();
            String json = gson.toJson(new Attack("A21"));
            TCPConnection.getInstance().getEmisor().sendMessage(json);
            view.disableAttackInRadar(2,1);
            view.notMyTurn();
        });

        view.getSurrenderBtn().setOnAction(event -> {
            Gson gson = new Gson();
            String json = gson.toJson(new GiveUp("GiveUp"));
            TCPConnection.getInstance().getEmisor().sendMessage(json);
            view.updateStatus("Te rendiste");
            view.disableGame();
            view.activateRestartBtn();
            view.desactivateSurrender();
        });

        view.getRestartBtn().setOnAction(event -> {
            Gson gson = new Gson();
            String json = gson.toJson(new Restart("Restart"));
            TCPConnection.getInstance().getEmisor().sendMessage(json);
        });

    }

    public void attacked(Attack attacked){
        view.myTurn();
        String[] pos = attacked.getBody().substring(1).split("");
        view.drawAttackInRadar(Integer.parseInt(pos[0]), Integer.parseInt(pos[1]));

        if (Integer.parseInt(pos[0]) == fil && Integer.parseInt(pos[1]) == col) {
            youLost();
        }

    }

    public void changeAdversaryName(Message adversaryName){
        view.changeAdversaryName(adversaryName.getBody());
    }

    public void youLost(){
        Gson gson = new Gson();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("¡Perdiste!");
        alert.setHeaderText(null);
        alert.setContentText("El adversario ganó la partida, si deseas jugar de nuevo reinicia");
        alert.show();

        view.updateStatus("¡Perdiste!");
        view.activateRestartBtn();
        view.disableGame();

        String json = gson.toJson(new Win("Win"));
        TCPConnection.getInstance().getEmisor().sendMessage(json);
    }

    public void giveUp(GiveUp gu){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("El adversario se rindió");
        alert.setHeaderText(null);
        alert.setContentText("El adversario se rindió, si desea jugar de nuevo reinicie");
        alert.show();

        view.updateStatus("El adversario se rindió");
        view.disableGame();
        view.activateRestartBtn();
        view.desactivateSurrender();
    }

    public void winner(Win win){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("¡Ganaste!");
        alert.setHeaderText(null);
        alert.setContentText("Le ha ganado al adversario, si desea jugar de nuevo reinicie");
        alert.show();

        view.updateStatus("¡Ganaste!");
        view.disableGame();
        view.activateRestartBtn();
    }

    public void restart(Restart restart){
        Gson gson = new Gson();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Reiniciar");
        alert.setHeaderText(null);
        alert.setContentText("El adversario desea reinciar el juego, ¿desea usted seguir jugando?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            view.restart();
            fil = (int) (3 * Math.random());
            col = (int) (3 * Math.random());
            view.drawWeakPointInRadar(fil, col);
            String json = gson.toJson(new ContinueGame("ContinueGame"));
            TCPConnection.getInstance().getEmisor().sendMessage(json);
        } else {
            String json = gson.toJson(new NoContinueGame("NoContinueGame"));
            TCPConnection.getInstance().getEmisor().sendMessage(json);
        }
    }

    public void continueGame(){
        Gson gson = new Gson();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Reiniciar");
        alert.setHeaderText(null);
        alert.setContentText("El adversario acepto reiniciar el juego");

        view.restart();
        fil = (int) (3 * Math.random());
        col = (int) (3 * Math.random());
        view.drawWeakPointInRadar(fil, col);
    }

    public void noContinueGame(){
        Gson gson = new Gson();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Reiniciar");
        alert.setHeaderText(null);
        alert.setContentText("El adversario no acepto reiniciar el juego");

    }


    @Override
    public void OnMessage(String msg) {
        Platform.runLater(() -> {
					Gson gson = new Gson();
					Generic msjObj = gson.fromJson(msg, Generic.class);

					switch (msjObj.getType()){
                        case "Message":
                            changeAdversaryName(gson.fromJson(msg, Message.class));
                            break;

                        case "Attack":
                            attacked(gson.fromJson(msg, Attack.class));
                            break;

                        case "GiveUp":
                            giveUp(gson.fromJson(msg, GiveUp.class));
                            break;

                        case "Win":
                            winner(gson.fromJson(msg, Win.class));
                            break;

                        case "Restart":
                            restart(gson.fromJson(msg, Restart.class));
                            break;

                        case "ContinueGame":
                            continueGame();
                            break;

                        case "NoContinueGame":
                            noContinueGame();
                            break;
                    }
				}
		);

	}
}

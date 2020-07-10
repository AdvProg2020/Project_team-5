package ApProject_OnlineShop.server;

import java.util.ArrayList;

public class RequestForServer {
    private String controller;
    private String function;
    private String token;
    private ArrayList<String> inputs;

    public RequestForServer(String controller, String function, String token, ArrayList<String> inputs) {
        this.controller = controller;
        this.function = function;
        this.token = token;
        this.inputs = inputs;
    }

    public String getController() {
        return controller;
    }

    public String getFunction() {
        return function;
    }

    public String getToken() {
        return token;
    }

    public ArrayList<String> getInputs() {
        return inputs;
    }
}

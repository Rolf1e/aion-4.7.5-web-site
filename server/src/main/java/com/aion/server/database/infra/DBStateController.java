package com.aion.server.database.infra;

public class DBStateController {

    private static DBStateController instance = null;

    private boolean state;

    public static DBStateController getInstance() {
        if (instance == null) {
            instance = new DBStateController();
        }
        return instance;
    }

    private DBStateController() {
        this.state = false;
    }

    public boolean getState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }
}

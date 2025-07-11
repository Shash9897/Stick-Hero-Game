package com.example.demo;

public abstract class BaseController {
    private Start mainApplication;

    public void setMainApplication(Start mainApplication) {
        this.mainApplication = mainApplication;
    }

    public Start getMainApplication() {
        return mainApplication;
    }
}

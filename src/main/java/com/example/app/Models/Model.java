package com.example.app.Models;

import com.example.app.Views.ViewAdminFactory;
import com.example.app.Views.ViewClientFactory;
import com.example.app.Views.ViewFactory;

public class Model {
    private static Model model;
    private final ViewFactory viewFactory;
    private final ViewAdminFactory viewAdminFactory;

    private final ViewClientFactory viewClientFactory;

    private Model() {
        this.viewFactory = new ViewFactory();
        this.viewAdminFactory = new ViewAdminFactory();
        this.viewClientFactory = new ViewClientFactory();
    }



    public static synchronized Model getInstance() {
        if(model == null) {
            model = new Model();
        }
        return model;
    }


    public ViewFactory getViewFactory() {
        return viewFactory;
    }

    public ViewAdminFactory getViewAdminFactory() {
        return viewAdminFactory;
    }

    public ViewClientFactory getViewClientFactory() {
        return viewClientFactory;
    }
}

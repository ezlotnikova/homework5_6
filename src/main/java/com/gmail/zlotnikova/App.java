package com.gmail.zlotnikova;

import com.gmail.zlotnikova.controller.HomeWorkController;
import com.gmail.zlotnikova.controller.impl.HomeWorkControllerImpl;

public class App {

    public static void main(String[] args) {

        HomeWorkController homeWorkController = new HomeWorkControllerImpl();

        homeWorkController.runTaskOne();
    }

}
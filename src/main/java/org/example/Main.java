package org.example;

import org.example.services.UserInputService;
import org.example.services.UserInputServiceInterface;
import org.example.services.UserManager;

public class Main {
  public static void main(String[] args) {
    try {
      UserInputServiceInterface userInputService = new UserInputService();
      UserManager userManager = new UserManager(userInputService);
      userManager.startManager();
    } catch (Exception e) {
      System.out.println("Error : " + e.getMessage());
    }
  }
}
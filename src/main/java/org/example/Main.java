package org.example;
import org.example.services.UserInputService;
import org.example.services.UserManager;

public class Main {
  public static void main(String[] args) {
    UserInputService userInputService = new UserInputService();
    UserManager userManager = new UserManager(userInputService);
    userManager.startManager();
  }
}
package org.example;

import java.util.Scanner;
import org.example.services.ConsoleInputService;
import org.example.services.ConsoleInputServiceInterface;
import org.example.services.UserInputService;
import org.example.services.UserInputServiceInterface;
import org.example.services.UserManager;
import org.example.validators.CyclicDependencyValidator;

public class Main {
  public static void main(String[] args) {
    try (Scanner scanner = new Scanner(System.in)) { // Ensure Scanner is closed properly
      ConsoleInputServiceInterface consoleInputService = new ConsoleInputService(scanner);
      CyclicDependencyValidator cyclicDependencyValidator = new CyclicDependencyValidator();
      UserInputServiceInterface userInputService = new UserInputService(consoleInputService);
      UserManager userManager = new UserManager(userInputService, cyclicDependencyValidator,
          consoleInputService);
      userManager.startManager();
    } catch (Exception e) {
      System.out.println("Error: " + e.getMessage());
    }
  }
}

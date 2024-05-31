package com.ulanzhasssanov.CrudAppWithJUnit.view;

import com.ulanzhasssanov.CrudAppWithJUnit.controller.LabelController;
import com.ulanzhasssanov.CrudAppWithJUnit.enums.Status;
import com.ulanzhasssanov.CrudAppWithJUnit.model.Label;

import java.util.Scanner;

public class LabelView {
    LabelController labelController = new LabelController();
    private final Scanner scanner = new Scanner(System.in);

    public void labelOperations(){

        while (true) {
            System.out.println("1. Create Label");
            System.out.println("2. Display All Labels");
            System.out.println("3. Display Label by ID");
            System.out.println("4. Update Label");
            System.out.println("5. Delete Label");
            System.out.println("0. Exit from Label operations");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character
            System.out.println();

            switch (choice) {
                case 1:
                    System.out.print("Enter name: ");
                    String name = scanner.nextLine();
                    Label label = new Label(name, Status.ACTIVE);
                    System.out.println(labelController.saveLabel(label));
                    System.out.println("Label saved to db");
                    System.out.println();
                    break;
                case 2:
                    System.out.println("All labels:");
                    System.out.println(labelController.getAllLabels());
                    System.out.println();
                    break;
                case 3:
                    System.out.print("Enter id to find: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();

                    System.out.println("Label with id " + id + ":");
                    System.out.println(labelController.getLabelById(id));
                    System.out.println();
                    break;
                case 4:
                    System.out.print("Enter id to update: ");
                    int labelUpdateId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println(labelController.getLabelById(labelUpdateId));

                    System.out.print("Enter new name: ");
                    String labelUpdateName = scanner.nextLine();
                    Label labelUpdate = new Label(labelUpdateId, labelUpdateName, Status.ACTIVE);

                    System.out.println("Updated label " + labelUpdateId + ":");
                    System.out.println(labelController.updateLabel(labelUpdate));
                    System.out.println();
                    break;
                case 5:
                    System.out.print("Enter id to delete: ");
                    int labelDeleteId = scanner.nextInt();
                    scanner.nextLine();
                    labelController.deleteLabel(labelDeleteId);
                    System.out.println("Label deleted");
                    System.out.println();
                    break;
                case 0:
                    System.out.println("Exiting from LabelView");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    System.out.println();
            }
        }
    }

}

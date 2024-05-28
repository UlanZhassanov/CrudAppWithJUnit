package com.ulanzhasssanov.CrudAppWithJUnit.view;

import com.ulanzhasssanov.CrudAppWithJUnit.controller.LabelController;
import com.ulanzhasssanov.CrudAppWithJUnit.controller.PostController;
import com.ulanzhasssanov.CrudAppWithJUnit.enums.PostStatus;
import com.ulanzhasssanov.CrudAppWithJUnit.model.Label;
import com.ulanzhasssanov.CrudAppWithJUnit.model.Post;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class PostView {

    public void postOperations(){
        LabelController labelController = new LabelController();
        PostController postController = new PostController();
        Scanner scanner = new Scanner(System.in);
        LocalDateTime dateTimeNow = LocalDateTime.now();

        while (true) {
            System.out.println("1. Create Post");
            System.out.println("2. Display All Posts");
            System.out.println("3. Display Post by ID");
            System.out.println("4. Update Post");
            System.out.println("5. Delete Post");
            System.out.println("0. Exit from Post operations");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character
            System.out.println();


            List<Label> allLabels = labelController.getAllLabels();

            switch (choice) {
                case 1:
                    System.out.print("Enter content: ");
                    String content = scanner.nextLine();

                    System.out.println("Available labels:");
                    System.out.println(labelController.getAllLabels());
                    System.out.print("Enter labels (separate by comma): ");
                    String labelIds = scanner.nextLine();
                    String[] labelIdsArray = labelIds.split(",");
                    List<Label> selectedLabels = new ArrayList<>();
                    for (String labelIdStr : labelIdsArray) {
                        int labelId = Integer.parseInt(labelIdStr.trim());
                        for (Label label : allLabels) {
                            if (label.getId() == labelId) {
                                selectedLabels.add(label);
                                break;
                            }
                        }
                    }

                    System.out.print("Enter writerId: ");
                    Integer writerId = scanner.nextInt();

                    Post post = new Post(content, selectedLabels, writerId);
                    System.out.println(postController.savePost(post));
                    System.out.println("Post saved to db");
                    System.out.println();
                    break;
                case 2:
                    System.out.println("All posts:");
                    System.out.println(postController.getAllPosts());
                    System.out.println();
                    break;
                case 3:
                    System.out.print("Enter id to find: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();

                    System.out.println("Post with id " + id + ":");
                    System.out.println(postController.getPostById(id));
                    System.out.println();
                    break;
                case 4:
                    System.out.print("Enter id to update: ");
                    int postUpdateId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println(postController.getPostById(postUpdateId));

                    System.out.print("Enter new content: ");
                    String postUpdateContent = scanner.nextLine();

                    System.out.println("Available labels:");
                    System.out.println(labelController.getAllLabels());
                    System.out.print("Enter labels (separate by comma): ");
                    String labelIdsForUpdate = scanner.nextLine();
                    String[] labelIdsForUpdateArray = labelIdsForUpdate.split(",");
                    List<Label> selectedLabelsForUpdate = new ArrayList<>();
                    for (String labelIdStr : labelIdsForUpdateArray) {
                        int labelId = Integer.parseInt(labelIdStr.trim());
                        for (Label label : allLabels) {
                            if (label.getId() == labelId) {
                                selectedLabelsForUpdate.add(label);
                                break;
                            }
                        }
                    }

                    Post postUpdate = postController.getPostById(postUpdateId);

                    postUpdate.setContent(postUpdateContent);
                    postUpdate.setLabels(selectedLabelsForUpdate);

                    System.out.println("Updated post " + postUpdateId + ":");
                    System.out.println(postController.updatePost(postUpdate));
                    System.out.println();
                    break;
                case 5:
                    System.out.print("Enter id to delete: ");
                    int postDeleteId = scanner.nextInt();
                    scanner.nextLine();

                    postController.deletePost(postDeleteId);
                    System.out.println("Post deleted");
                    System.out.println();
                    break;
                case 0:
                    System.out.println("Exiting from PostView");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    System.out.println();
            }
        }
    }

}

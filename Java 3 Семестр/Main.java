package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static List<Group> groups = new ArrayList<>();
    private static Connection connection;

    public static void main(String[] args) {
        connectToDatabase();

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("Выберите действие:");
            System.out.println("1. Создать группу");
            System.out.println("2. Удалить группу");
            System.out.println("3. Просмотреть группы");
            System.out.println("4. Создать студента");
            System.out.println("5. Удалить студента");
            System.out.println("6. Просмотреть студентов в группе");
            System.out.println("0. Выход");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Очистка буфера чтения

            switch (choice) {
                case 1:
                    createGroup(scanner);
                    break;
                case 2:
                    deleteGroup(scanner);
                    break;
                case 3:
                    viewGroups();
                    break;
                case 4:
                    createStudent(scanner);
                    break;
                case 5:
                    deleteStudent(scanner);
                    break;
                case 6:
                    viewStudentsInGroup(scanner);
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Некорректный выбор. Попробуйте еще раз.");
                    break;
            }

            System.out.println();
        }

        scanner.close();
        disconnectFromDatabase();
        System.out.println("Работа программы завершена.");
    }

    public static void createGroup(Scanner scanner) {
        System.out.println("Введите название группы:");
        String groupName = scanner.nextLine();

        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO group_table (name) VALUES (?)");
            statement.setString(1, groupName);
            statement.executeUpdate();
            statement.close();
            System.out.println("Группа успешно создана.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteGroup(Scanner scanner) {
        System.out.println("Введите название группы для удаления:");
        String groupName = scanner.nextLine();

        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM group_table WHERE name = ?");
            statement.setString(1, groupName);
            int rowsAffected = statement.executeUpdate();
            statement.close();

            if (rowsAffected > 0) {
                System.out.println("Группа успешно удалена.");
            } else {
                System.out.println("Группа не найдена.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void viewGroups() {
        System.out.println("Список групп:");

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM group_table");

            if (!resultSet.isBeforeFirst()) {
                System.out.println("Нет доступных групп.");
            }

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                System.out.println("Группа: " + name);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void createStudent(Scanner scanner) {
        System.out.println("Введите название группы, в которую добавить студента:");
        String groupName = scanner.nextLine();

        List<String> groupNames = getGroupNames();

        if (groupNames.contains(groupName)) {
            System.out.println("Введите имя студента:");
            String studentName = scanner.nextLine();

            try {
                PreparedStatement statement = connection.prepareStatement("INSERT INTO student_table (name, group_name) VALUES (?, ?)");
                statement.setString(1, studentName);
                statement.setString(2, groupName);
                statement.executeUpdate();
                statement.close();
                System.out.println("Студент успешно добавлен в группу.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Группа не найдена.");
        }
    }

    public static void deleteStudent(Scanner scanner) {
        System.out.println("Введите имя студента для удаления:");
        String studentName = scanner.nextLine();

        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM student_table WHERE name = ?");
            statement.setString(1, studentName);
            int rowsAffected = statement.executeUpdate();
            statement.close();
            if (rowsAffected > 0) {
                System.out.println("Студент успешно удален.");
            } else {
                System.out.println("Студент не найден.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void viewStudentsInGroup(Scanner scanner) {
        System.out.println("Введите название группы для просмотра студентов:");
        String groupName = scanner.nextLine();

        List<String> studentNames = getStudentsInGroup(groupName);

        if (!studentNames.isEmpty()) {
            System.out.println("Студенты в группе " + groupName + ":");
            for (String studentName : studentNames) {
                System.out.println(studentName);
            }
        } else {
            System.out.println("Нет студентов в указанной группе.");
        }
    }

    public static List<String> getStudentsInGroup(String groupName) {
        List<String> studentNames = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT name FROM student_table WHERE group_name = ?");
            statement.setString(1, groupName);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                studentNames.add(name);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return studentNames;
    }

    public static List<String> getGroupNames() {
        List<String> groupNames = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT name FROM group_table");
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                groupNames.add(name);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return groupNames;
    }

    public static void connectToDatabase() {
        String url = "jdbc:mysql://localhost:3306/zadan";
        String username = "Nox1";
        String password = "Azza2005.";

        try {
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Успешное подключение к базе данных.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void disconnectFromDatabase() {
        try {
            if (connection != null) {
                connection.close();
                System.out.println("Отключение от базы данных.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Group findGroupByName(String groupName) {
        for (Group group : groups) {
            if (group.getName().equals(groupName)) {
                return group;
            }
        }
        return null;
    }
}
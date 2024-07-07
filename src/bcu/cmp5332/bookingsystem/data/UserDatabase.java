package bcu.cmp5332.bookingsystem.data;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * The UserDatabase class manages user authentication and persistence of user credentials.
 * 
 * @author Sugam Adhikari
 * @author Prasansha Tamang
 * 
 */
public class UserDatabase {
    private static final String FILE_PATH = "./resources/data/users.txt";
    private Map<String, String> users;

    /**
     * Constructs a UserDatabase object and loads users from a file.
     */
    public UserDatabase() {
        users = new HashMap<>();
        loadUsers();
    }

    /**
     * Loads user credentials from a file into the users map.
     */
    private void loadUsers() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    users.put(parts[0], parts[1]);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading user database: " + e.getMessage());
        }
    }

    /**
     * Authenticates a user based on provided username and password.
     *
     * @param username the username of the user
     * @param password the password of the user
     * @return true if authentication succeeds, false otherwise
     */
    public boolean authenticate(String username, String password) {
        return users.containsKey(username) && users.get(username).equals(password);
    }

    /**
     * Adds a new user to the database.
     *
     * @param username the username of the new user
     * @param password the password of the new user
     * @return true if the user was successfully added, false if the username already exists
     */
    public boolean addUser(String username, String password) {
        if (users.containsKey(username)) {
            return false; // Username already exists
        }

        users.put(username, password);
        saveUsers();
        return true;
    }

    /**
     * Saves the current state of the users map to the file.
     */
    private void saveUsers() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Map.Entry<String, String> entry : users.entrySet()) {
                writer.write(entry.getKey() + "," + entry.getValue());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving user database: " + e.getMessage());
        }
    }
}

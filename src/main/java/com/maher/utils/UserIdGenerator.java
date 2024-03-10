package com.maher.utils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class UserIdGenerator {
    private static long userCount = 0;

    static {
        try {
            File file = new File("userCount.txt");
            if (file.exists()) {
                String content = new String(Files.readAllBytes(Paths.get("userCount.txt")));
                userCount = Long.parseLong(content);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static synchronized String generateUserId() {
        userCount++;
        try (FileWriter writer = new FileWriter("userCount.txt")) {
            writer.write(String.valueOf(userCount));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "user" + userCount;
    }
}
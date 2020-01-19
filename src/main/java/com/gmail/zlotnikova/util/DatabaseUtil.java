package com.gmail.zlotnikova.util;

import java.io.IOException;
import java.io.InputStream;
import java.lang.invoke.MethodHandles;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DatabaseUtil {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public static String getInitData(String fileName) {
        String initData = null;
        try (InputStream inputStream = DatabaseUtil.class.getClassLoader().getResourceAsStream(fileName)) {
            if (inputStream != null) {
                try (Scanner scanner = new Scanner(inputStream, StandardCharsets.UTF_8.name())) {
                    StringBuilder stringBuilder = new StringBuilder();
                    while (scanner.hasNextLine()) {
                        stringBuilder.append(scanner.nextLine());
                    }
                    initData = stringBuilder.toString();
                }
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return initData;
    }

}
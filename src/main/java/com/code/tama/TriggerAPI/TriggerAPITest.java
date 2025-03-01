package com.code.tama.TriggerAPI;

public class TriggerAPITest {
    public static void main(String[] args) {
        TriggerAPI triggerAPI = new TriggerAPI();

        FileHelper.createStoredFile("test_file", "I Am a Test");

        String file = FileHelper.getStoredFile("test_file");

        Logger.info("File content - %s", file);
    }
}

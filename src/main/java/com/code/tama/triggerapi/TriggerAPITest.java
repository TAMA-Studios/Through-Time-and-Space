/* (C) TAMA Studios 2025 */
package com.code.tama.triggerapi;

import com.code.tama.triggerapi.helpers.FileHelper;

public class TriggerAPITest {
	public static void main(String[] args) {
		TriggerAPI triggerAPI = new TriggerAPI("test");

		FileHelper.createStoredFile("test_file", "I Am a Test");

		String file = FileHelper.getStoredFile("test_file");

		Logger.info("File content - %s", file);
	}
}

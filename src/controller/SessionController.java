package controller;

import model.User;

public class SessionController {
	private static User currUser = null;
	
	public static void setCurrentUser(User user) {
        currUser = user;
    }
	
	public static User getCurrentUser() {
        return currUser;
    }
	
	public static void logout() {
        currUser = null;
    }
}

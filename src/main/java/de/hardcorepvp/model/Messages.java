package de.hardcorepvp.model;

public class Messages {

    public static final String NO_PERMISSIONS = " ";
    public static final String TOO_MANY_ARGUMENTS = " ";
	public static final String TOO_LESS_ARGUMENTS = " ";
    public static final String PLAYER_NOT_FOUND = " ";
    public static final String PREFIX = " ";
    public static final String[] HELP = {"", "", ""};

    public static String formatMessage(String message) {

        return PREFIX + message;

    }

    public static String formatMessage(String[] message) {

        return PREFIX + message;

    }
}

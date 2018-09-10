package de.hardcorepvp.utils;

import java.util.Arrays;

public class Messages {

	public static final String NO_PERMISSIONS = "NO_PERMISSIONS";
	public static final String TOO_MANY_ARGUMENTS = "TOO_MANY_ARGUMENTS";
	public static final String TOO_LESS_ARGUMENTS = "TOO_LESS_ARGUMENTS";
	public static final String WRONG_ARGUMENTS = "WRONG_ARGUMENTS";
	public static final String PLAYER_NOT_FOUND = "PLAYER_NOT_FOUND";
	public static final String ERROR_OCCURRED = "§cEs ist ein Fehler aufgetreten! Melde dich umgehend im Teamspeak mit dieser Nachricht! §6§l%error%";
	public static final String PREFIX = "PREFIX";
	public static final String[] HELP = {"ex1", "ex2", "ex3"};
	public static final String[] SPAWNERTYPES = {"Creeper", "Pigman", "ex3"};

	public static String formatMessage(String message) {
		return PREFIX + message;
	}

	public static String formatMessage(String[] message) {
		return PREFIX + Arrays.toString(message);
	}
}

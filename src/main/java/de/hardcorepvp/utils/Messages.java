package de.hardcorepvp.utils;

import java.util.Arrays;

public class Messages {

	public static final String NO_PERMISSIONS = "NO_PERMISSIONS";
	public static final String TOO_MANY_ARGUMENTS = "TOO_MANY_ARGUMENTS";
	public static final String TOO_LESS_ARGUMENTS = "TOO_LESS_ARGUMENTS";
	public static final String WRONG_ARGUMENTS = "WRONG_ARGUMENTS";
	public static final String PLAYER_NOT_FOUND = "PLAYER_NOT_FOUND";
	public static final String SYNTAX_ERROR = "%error%";
	public static final String ERROR_OCCURRED = "§cEs ist ein Fehler aufgetreten! Melde dich umgehend im Teamspeak mit dieser Nachricht! §6§l%error%";
	public static final String RANKING_HEADER = "§b<------ §6Ranking §b------>";
	public static final String RANKING_STRUCTURE = "§aName §7| §a%ranking%";
	public static final String PREFIX = "PREFIX";
	public static final String[] HELP = {"ex1", "ex2", "ex3"};
	public static final String CMDITEMPREFIX = "§5[CMDITEM] ";
	public static final String RANKUPBOOK = "RANGUPGRADE ";
	public static final String RANKUPCMD = "rankup %p%";
	public static final String TEAMMEMBERJOINED = "+%p%";
	public static final String TEAMMEMBERLEFT = "-%p%";


	public static String formatMessage(String message) {
		return PREFIX + message;
	}

	public static String formatMessage(String[] message) {
		return PREFIX + Arrays.toString(message);
	}
}

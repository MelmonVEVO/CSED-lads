package group.csed.frontend.notifications;

public class OSChecker{

    private static String OSType = System.getProperty("os.name").toLowerCase();

    public static boolean isWindows() {

		return (OSType.indexOf("win") >= 0);

	}

	public static boolean isMac() {

		return (OSType.indexOf("mac") >= 0);

	}

	public static boolean isUnix() {

		return (OSType.indexOf("nix") >= 0 || OSType.indexOf("nux") >= 0 || OSType.indexOf("aix") > 0 );
		
	}
}
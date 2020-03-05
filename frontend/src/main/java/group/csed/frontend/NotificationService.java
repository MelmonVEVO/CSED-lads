package group.csed.frontend;

import group.csed.frontend.http.ApiRequest;
import java.awt.SystemTray;
import java.awt.AWTException;
import java.awt.Image;
import java.awt.TrayIcon;
import java.awt.Toolkit;
import java.awt.TrayIcon.MessageType;

public class NotificationService extends Thread {

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                if (isInterrupted()) {
                    return;
                }
            }
            if (ApiRequest.periodDue()) {
                if (OSChecker.isWindows()) {
                    SystemTray tray = SystemTray.getSystemTray();
                    Image image = Toolkit.getDefaultToolkit().createImage("icon.png");
                    TrayIcon trayIcon = new TrayIcon(image, "DueNoti");
                    try {
                        tray.add(trayIcon);
                    } catch (AWTException e) {
                        System.out.println("System Tray not available");
                    }

                    trayIcon.displayMessage("OH NO!!", "Period due", MessageType.INFO);
                }
                else if(OSChecker.isMac()){

                }
                else if(OSChecker.isUnix()){

                }
                else{
                    System.out.println("Notifications not supported for this OS");
                }
            }

        }
    }
}
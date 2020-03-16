package group.csed.frontend.notifications;

import group.csed.frontend.http.ApiRequest;
import java.awt.SystemTray;
import java.awt.AWTException;
import java.awt.Image;
import java.awt.TrayIcon;
import java.awt.Toolkit;
import java.awt.TrayIcon.MessageType;
import java.io.IOException;

public class NotificationService extends Thread {

    private boolean thisPeriodNotified = false;

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
            if (ApiRequest.periodDue() && !thisPeriodNotified) {
                if (OSChecker.isWindows()) {
                    SystemTray tray = SystemTray.getSystemTray();
                    Image image = Toolkit.getDefaultToolkit().createImage("icon.png");
                    TrayIcon trayIcon = new TrayIcon(image, "DueNoti");
                    try {
                        tray.add(trayIcon);
                    } catch (AWTException e) {
                        System.out.println("System Tray not available");
                    }

                    trayIcon.displayMessage("Tsunami", "Your period is due", MessageType.INFO);
                }
                else if(OSChecker.isUnix()){
                    ProcessBuilder pb = new ProcessBuilder("notify send 'Tsunami' 'Your period is due'");
                    pb.inheritIO();
                    try{
                        pb.start();
                    } catch (IOException e){
                        System.out.println("Desktop notification not possible");
                    }
                }
                else{
                    System.out.println("Notifications not supported for this OS");
                }
                thisPeriodNotified = true;
            }

        }
    }
}
package gomoku.view.console;

import gomoku.client.*;

import java.util.Scanner;
import java.io.IOException;


public class LeaveWaitingBreaker extends Thread {

    private Scanner scan;
    private Client client;
    private boolean disable = false;

    public LeaveWaitingBreaker(Scanner scan, Client client) {
        this.scan = scan;
        this.client = client;
    }

    @Override
    public void run() {
        while(!this.disable) {
            try {
                if(System.in.available() > 0) {
                    String input = scan.nextLine();
                    if(input.equals("leave")){
                        client.sendLeaveBoth();
                        this.disable();
                    }
                }
                Thread.sleep(1000);
            }
            catch (InterruptedException ie) { }
            catch (IOException ioe) { }
        }
    }

    public void disable() {
        this.disable = true;
    }
}
package gomoku.view.console;

import gomoku.client.*;

import java.util.Scanner;
import java.io.IOException;


public class CreateWaitingBreaker extends Thread {

    private Scanner scan;
    private Client client;
    private boolean disable = false;

    public CreateWaitingBreaker(Scanner scan, Client client) {
        this.scan = scan;
        this.client = client;
    }

    @Override
    public void run() {
        while(!this.disable) {
            try {
                if(System.in.available() > 0) {
                    scan.nextLine();
                    client.sendWaitingStop();
                    this.disable();
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
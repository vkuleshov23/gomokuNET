// package net.exe;

// import net.client.*;
// import java.io.IOException;
// import java.net.UnknownHostException;

// public class ClientExe{
// 	public static void main(String args[]) {
// 		Client client = new Client();
// 		try {
// 			System.out.println(client.sendName("1--1"));
// 			Thread.sleep(2000);
// 			client.sendCreate();
// 			Thread.sleep(2000);
// 			client.sendExit();
// 			Thread.sleep(2000);
// 			System.out.println(client.getCreateList());
// 			Thread.sleep(2000);
// 			client.sendQuit();
// 		} catch (IOException ioe) {
// 			System.out.println(ioe.getMessage());
// 		} catch (InterruptedException ie) {
// 			System.out.println(ie.getMessage());
// 		} catch (UnknownHostException uhe) {
// 			System.out.println(uhe.getMessage());
// 		}
// 	}
// }
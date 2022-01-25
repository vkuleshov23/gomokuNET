package gomoku.net.server;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashSet;

import gomoku.net.utils.NETparam;

public class Sender {
	
	private OutputStream outStream;

	public Sender(OutputStream outStream) {
		this.outStream = outStream;
	}

	public OutputStream getStream() {
		return outStream;
	}

	public void sendToMe(String data) throws IOException {
		this.trueSend(data, outStream);
	}

	public void trueSend(String data, OutputStream out) throws IOException {
		byte[] sendData = new byte[NETparam.maxPacketLength + NETparam.maxNameLength];
		sendData = data.getBytes();
		out.write(sendData);
		out.flush();
	}

	public void send(String data, Sender out) throws IOException {
		trueSend(data, out.getStream());
	}

	public void closeOutput() throws IOException {
		outStream.close();
	}

	@Override
	public boolean equals(Object o) {
		if(this == o){
			return true;
		} else if(getClass() != o.getClass()) {
			return false;
		} else {
			Sender sender = (Sender)o;
			return this.getStream() == sender.getStream();
		}
	}

	@Override
	public int hashCode() {
		return outStream.hashCode();
	}
}

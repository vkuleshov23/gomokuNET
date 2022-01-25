package gomoku.net.server;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.io.IOException;
// import java.util.HashSet;

import gomoku.logs.Log;

import gomoku.net.utils.NETparam;

public class Reciever {

	private InputStream inStream;
	private Log clSreamLog;

	public Reciever(Log clSreamLog, InputStream inStream) {
		this.inStream = inStream;
		this.clSreamLog = clSreamLog;
	}

	public void closeInput() throws IOException {
		inStream.close();
	}

	public String getData() {
		byte[] receiveData = new byte[NETparam.maxPacketLength + NETparam.maxNameLength];
		try	{
			inStream.read(receiveData);
		} catch (IOException e) {
			this.clSreamLog.severe("Can't read data\n");
			return "@quit .";
		}
		return (new String(receiveData, StandardCharsets.UTF_8)).trim();
	}
}
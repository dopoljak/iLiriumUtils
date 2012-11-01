package com.iLirium.utils.commons;

import java.io.IOException;
import java.net.Socket;

public class IO
{
	public static void close(Socket socket)
	{
		try {
			if (socket != null && !socket.isClosed())
				socket.close();
		}
		catch (IOException ioe) {
			System.out.println("Error closing socket : " + ioe);
		}
	}
}

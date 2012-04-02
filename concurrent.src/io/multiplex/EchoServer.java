package io.multiplex;

import java.io.IOException;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.SelectionKey;
import java.nio.ByteBuffer;
import java.util.Iterator;

public class EchoServer extends Thread {
	private Selector selector;

	public EchoServer() {
		
	}
	
	public void run() {
		try {
			ServerSocketChannel ssChannel = ServerSocketChannel.open();
			ssChannel.configureBlocking(false);
			ssChannel.register(selector, SelectionKey.OP_ACCEPT);
			//ss = ssChannel.socket();
		} catch(IOException ioe) {
			ioe.printStackTrace();
			System.exit(1);
		}
		
		while (true) {
			Iterator<SelectionKey> keys = selector.keys().iterator();
			SelectionKey key = null;
			if (keys.hasNext()) {
				key = keys.next();
				keys.remove();
			}
		}
		
		
	}
	
	private void handleKey(SelectionKey key) {
		if (key != null) {
			handleAccept(key);
		} else if (key.isReadable()) {
			handleRead(key);
		}
	}
	
	public void handleAccept(SelectionKey key) {
		try {
			
		} catch (Exception ioe) {
			ioe.printStackTrace();
		}
	}
	
	public void handleRead (SelectionKey key) {
		ByteBuffer buffer = ByteBuffer.allocateDirect(2048);
		SocketChannel channel = (SocketChannel)key.channel();
		try {
			while (channel.read(buffer) > 0) {
				
			};
			buffer.flip();
//			byte[] content = new byte[buffer.limit()];
//			buffer.get(content);
//			System.out.println(new String(content));
			channel.write(buffer);
			
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		EchoServer es = new EchoServer();
		es.start();
	}
}

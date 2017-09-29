package Chat_Java_Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * 

* @ClassName: SimpleChatClient 

* @Description: TODO(聊天客户端) 

* @author xieydd xieydd@gmail.com  

* @date 2017-9-29 下午4:46:53 

*
 */
public class SimpleChatClient {
	public static void main(String[] args) throws Exception {
		new SimpleChatClient("localhost", 8080).run();
	}

	private final String host;
	private final int port;

	public SimpleChatClient(String host, int port) {
		this.host = host;
		this.port = port;
	}

	public void run() throws Exception {
		EventLoopGroup workGroup = new NioEventLoopGroup();

		try {
			Bootstrap b = new Bootstrap();
			b.group(workGroup).channel(NioSocketChannel.class)
					.handler(new SimpleChatClientInitializer());

			Channel channel = b.connect(host, port).sync().channel();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					System.in));
			while (true) {
				channel.writeAndFlush(in.readLine() + "\r\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			workGroup.shutdownGracefully();
		}
	}
}

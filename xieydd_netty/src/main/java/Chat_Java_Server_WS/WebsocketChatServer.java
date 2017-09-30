package Chat_Java_Server_WS;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 

* @ClassName: WebsocketChatServer 

* @Description: TODO(�����) 

* @author xieydd xieydd@gmail.com  

* @date 2017-9-30 ����5:03:57 

*
 */

public class WebsocketChatServer {

	private int port;

	public WebsocketChatServer(int port) {
		this.port = port;
	}

	public void run() throws Exception {

		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workGroup = new NioEventLoopGroup();

		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workGroup).channel(NioServerSocketChannel.class)
					.childHandler(new WebsocketChatServerInitializer())
					.option(ChannelOption.SO_BACKLOG, 128)
					.childOption(ChannelOption.SO_KEEPALIVE, true);

			System.out.println("WebServerChatServer������");

			// �󶨶˿�
			ChannelFuture f = b.bind(port).sync();

			// �ȴ�������socket�ر�
			f.channel().closeFuture().sync();
		} finally {
			workGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
			System.out.println("WebsocketChatServer�ر���");
		}
	}

	public static void main(String[] args) throws Exception {
		int port;
		if (args.length > 0) {
			port = Integer.parseInt(args[0]);
		} else {
			port = 8080;
		}
		
		new WebsocketChatServer(port).run();
	}
}

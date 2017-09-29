package Chat_Java_Server;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 

* @ClassName: SimpleChatServer 

* @Description: TODO(聊天服务端) 

* @author xieydd xieydd@gmail.com  

* @date 2017-9-29 下午4:50:07 

*
 */
public class SimpleChatServer {
	private int port;
	
	public SimpleChatServer(int port) {
		this.port = port;
	}
	
	public void run() throws Exception {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workGroup = new NioEventLoopGroup();
		
		try{
			//我们正在写一个TCP/IP 的服务端，因此我们被允许设置 socket的参数选项比如tcpNoDelay 和 keepAlive
			//option() 是提供给NioServerSocketChannel用来接收进来的连接。childOption() 是提供给由父管道ServerChannel接收到的连接，在这个例子中也是 NioServerSocketChannel
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup,workGroup).channel(NioServerSocketChannel.class).childHandler(new SimpleChatServerInitializer()).
			option(ChannelOption.SO_BACKLOG,128).childOption(ChannelOption.SO_KEEPALIVE,true);
			
			System.out.println("服务端启动了");
			
			//绑定端口，接受连接进来
			ChannelFuture f = b.bind(port).sync(); 
			
			//等待服务器，Socket关闭
			f.channel().closeFuture().sync();
		}finally{
			workGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
			
			System.out.println("SimpleChatServer关闭了");
		}
	}
	
	public static void main(String[] args) throws Exception {
		int port;
		if(args.length > 0) {
			port = Integer.parseInt(args[0]);
		}else {
			port = 8080;
		}
		
		new SimpleChatServer(port).run();
	}
}

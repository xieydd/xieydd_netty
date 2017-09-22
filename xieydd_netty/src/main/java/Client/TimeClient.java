package Client;

import Handler.TimeClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * 

* @ClassName: TimeClient 

* @Description: TODO(时间客户端) ,不想echo和Discard服务端，对于TIME协议需要一个客户端，因为人们不能把一个32位进制的数据翻译成日期

* @author xieydd xieydd@gmail.com  

* @date 2017-9-22 下午2:15:18 

*
 */

public class TimeClient {
	public static void main(String[] args) throws Exception{
		String host = "127.0.0.1";
		int port = 8080;
				//Integer.parseInt("8080");
		
		//这里即作为boss group也作为work group
		EventLoopGroup workGroup = new NioEventLoopGroup();
		try{
			//对于非服务端的Channel例如客户端和无连接传输的Channel
			Bootstrap b = new Bootstrap();
			b.group(workGroup);
			b.channel(NioSocketChannel.class);
			//这里不需要b.childOption(..)因为NioSocketChannel没有父亲但是NioServerSocketChannel有
			b.option(ChannelOption.SO_KEEPALIVE, true);
			b.handler(new ChannelInitializer<SocketChannel>() {

				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					// TODO Auto-generated method stub
					ch.pipeline().addLast(new TimeClientHandler());
				}
			});
			
			//启动客户端 用connect代替server中的bind
			ChannelFuture f = b.connect(host, port).sync();
			
			//等待连接关闭
			f.channel().closeFuture().sync();
		}finally{
			workGroup.shutdownGracefully();
		}
	}
}

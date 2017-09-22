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

* @Description: TODO(ʱ��ͻ���) ,����echo��Discard����ˣ�����TIMEЭ����Ҫһ���ͻ��ˣ���Ϊ���ǲ��ܰ�һ��32λ���Ƶ����ݷ��������

* @author xieydd xieydd@gmail.com  

* @date 2017-9-22 ����2:15:18 

*
 */

public class TimeClient {
	public static void main(String[] args) throws Exception{
		String host = "127.0.0.1";
		int port = 8080;
				//Integer.parseInt("8080");
		
		//���Ｔ��Ϊboss groupҲ��Ϊwork group
		EventLoopGroup workGroup = new NioEventLoopGroup();
		try{
			//���ڷǷ���˵�Channel����ͻ��˺������Ӵ����Channel
			Bootstrap b = new Bootstrap();
			b.group(workGroup);
			b.channel(NioSocketChannel.class);
			//���ﲻ��Ҫb.childOption(..)��ΪNioSocketChannelû�и��׵���NioServerSocketChannel��
			b.option(ChannelOption.SO_KEEPALIVE, true);
			b.handler(new ChannelInitializer<SocketChannel>() {

				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					// TODO Auto-generated method stub
					ch.pipeline().addLast(new TimeClientHandler());
				}
			});
			
			//�����ͻ��� ��connect����server�е�bind
			ChannelFuture f = b.connect(host, port).sync();
			
			//�ȴ����ӹر�
			f.channel().closeFuture().sync();
		}finally{
			workGroup.shutdownGracefully();
		}
	}
}

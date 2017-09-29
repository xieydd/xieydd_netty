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

* @Description: TODO(��������) 

* @author xieydd xieydd@gmail.com  

* @date 2017-9-29 ����4:50:07 

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
			//��������дһ��TCP/IP �ķ���ˣ�������Ǳ��������� socket�Ĳ���ѡ�����tcpNoDelay �� keepAlive
			//option() ���ṩ��NioServerSocketChannel�������ս��������ӡ�childOption() ���ṩ���ɸ��ܵ�ServerChannel���յ������ӣ������������Ҳ�� NioServerSocketChannel
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup,workGroup).channel(NioServerSocketChannel.class).childHandler(new SimpleChatServerInitializer()).
			option(ChannelOption.SO_BACKLOG,128).childOption(ChannelOption.SO_KEEPALIVE,true);
			
			System.out.println("�����������");
			
			//�󶨶˿ڣ��������ӽ���
			ChannelFuture f = b.bind(port).sync(); 
			
			//�ȴ���������Socket�ر�
			f.channel().closeFuture().sync();
		}finally{
			workGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
			
			System.out.println("SimpleChatServer�ر���");
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

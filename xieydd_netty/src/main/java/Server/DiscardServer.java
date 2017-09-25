package Server;

import Decoder.TimeDecoder;
import Encoder.TimeEncoder;
import Handler.DiscardServerHandler;
import Handler.TimeServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 

* @ClassName: DiscardServer 

* @Description: TODO(����DiscardServerHandler) 

* @author xieydd xieydd@gmail.com  

* @date 2017-9-20 ����1:30:59 

*
 */
public class DiscardServer {
	
	private int port;
	
	public DiscardServer(int port) {
		this.port = port;
	}
	
	public void run() throws Exception {
		/**
		 * NioEventLoopGroup���Ǵ���IO�����Ķ��߳�ѭ����
		 * bossGroup:�������ս���������,һ�����յ����ݾ�ע�ᵽworkGroup��
		 * workGroup:�������������
		 */
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workGroup = new NioEventLoopGroup();
		try{
			/**
			 * ServerBootstrap��Ϊ������NIO���񴴽��ĸ����࣬����������п���ֱ��ʹ��Channel(Ϊ�˽�������̼�)
			 */
			ServerBootstrap b = new ServerBootstrap();
			/**
			 * ָ��ʹ��NioServerSocketChannel��˵���µ�Channel������ӽ���
			 * ChannelInitializer����ʹ��������һ���µ�Channel������������������һ��������DiscardServerHandler(����ӵ�SocketChannel.pipline�õ���ChannelPipeline��)
			 * .option��childOption������Channelʵ�ֵĲ���
			 *  public static final ChannelOption<Integer> SO_BACKLOG = valueOf("SO_BACKLOG"); return new ChannelOption<Object>(id, name);
			 *  
			 */
			b.group(bossGroup, workGroup).channel(NioServerSocketChannel.class).childHandler(new ChannelInitializer<SocketChannel>() {

				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					// TODO Auto-generated method stub
					//��DiscardServerHandler����TimeServerHandler�ͱ��ʱ��������ķ���ˣ����TimeClientʹ��
					ch.pipeline().addLast(new TimeEncoder(),new  TimeServerHandler());
				}
				
			})
			.option(ChannelOption.SO_BACKLOG, 128)
			.childOption(ChannelOption.SO_KEEPALIVE,true);
			
			//�󶨶˿�,������԰󶨲�ͬ��ַ
			ChannelFuture f = b.bind(port).sync();
			f.channel().closeFuture().sync();
		}finally{
			//��EventLoopGroup ����ȫ����ֹ,���Ҷ�Ӧ������ channel ���Ѿ����ر�ʱ��Netty �᷵��һ��Future������֪ͨ��
			workGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}
	}
	
	public static void main(String[] args) throws Exception {
		int port;
		if(args.length > 0) {
			port = Integer.parseInt(args[0]);
		}else{
			port = 8080;
		}
		new DiscardServer(port).run();
	}
}

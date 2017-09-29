package Chat_Java_Server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * 

* @ClassName: SimpleChatServerInitializer 

* @Description: TODO(�������Ӷ���Ĵ����ൽ ChannelPipeline �ϣ��������롢���롢SimpleChatServerHandler��) 
������¼������ྭ���ᱻ��������һ��������Ѿ����յ� Channel��SimpleChatServerInitializer �̳���
ChannelInitializer��һ������Ĵ����࣬����Ŀ���ǰ���ʹ��������һ���µ� Channel��Ҳ������ͨ������һЩ
��������� SimpleChatServerHandler ������һ���µ� Channel �������Ӧ��ChannelPipeline��ʵ�����
������򡣵���ĳ����ĸ���ʱ������������Ӹ���Ĵ����ൽ pipline �ϣ�Ȼ����ȡ��Щ�����ൽ������
��
* @author xieydd xieydd@gmail.com  

* @date 2017-9-25 ����5:11:44 

*
 */
public class SimpleChatServerInitializer extends ChannelInitializer<SocketChannel>{

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		// TODO Auto-generated method stub
		ChannelPipeline pipeline = ch.pipeline();
		
		pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
		//����
		pipeline.addLast("decoder", new StringDecoder());
		//����
		pipeline.addLast("encoder", new StringEncoder());
		
		pipeline.addLast("handler", new SimpleChatServerHandler());
	}
	
}

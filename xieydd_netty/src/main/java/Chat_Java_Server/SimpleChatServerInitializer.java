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

* @Description: TODO(用来增加多个的处理类到 ChannelPipeline 上，包括编码、解码、SimpleChatServerHandler等) 
这里的事件处理类经常会被用来处理一个最近的已经接收的 Channel。SimpleChatServerInitializer 继承自
ChannelInitializer是一个特殊的处理类，他的目的是帮助使用者配置一个新的 Channel。也许你想通过增加一些
处理类比如 SimpleChatServerHandler 来配置一个新的 Channel 或者其对应的ChannelPipeline来实现你的
网络程序。当你的程序变的复杂时，可能你会增加更多的处理类到 pipline 上，然后提取这些匿名类到最顶层的类
上
* @author xieydd xieydd@gmail.com  

* @date 2017-9-25 下午5:11:44 

*
 */
public class SimpleChatServerInitializer extends ChannelInitializer<SocketChannel>{

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		// TODO Auto-generated method stub
		ChannelPipeline pipeline = ch.pipeline();
		
		pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
		//编码
		pipeline.addLast("decoder", new StringDecoder());
		//解码
		pipeline.addLast("encoder", new StringEncoder());
		
		pipeline.addLast("handler", new SimpleChatServerHandler());
	}
	
}

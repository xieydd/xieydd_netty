package Decoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import Pojo.UnixTime;

/**
 * 

* @ClassName: TimeDecoder 

* @Description: TODO(时间解码器放在TimeClient的ch.pipeline().addLast(new TimeDecoder(),new TimeClientHandler());即将TimeClientHandlerc拆分) 
当有可变长度的字段组成更加复杂的协议，第一种方法将会十分麻烦
	 * 将整个channelHandler拆分为TimeDecoder处理数据拆分 问题 ;TimeClientHandler原始版本实现
	 * Netty提供可扩展类对于TimeDecoder
	 * ByteToMessageDecoder 是 ChannelInboundHandler 的一个实现类，他可以在处理数据拆分的问题上变得很简单
	 * 当新数据接受的时候会调用decode方法对于数据进行处理内部的累计缓存;当累计缓存李没有足够的数据的时候可以在out对象中放任意的数据，当有更多的数据被接受的时候会继续调用这个方法进行decode
	 * 如果在decode中添加了out对象，意味着解码器解码成功，将会求其在累计缓存中读取过得数据
* @author xieydd xieydd@gmail.com  

* @date 2017-9-22 下午5:24:37 

*
 */
public class TimeDecoder extends ByteToMessageDecoder {
	

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in,
			List<Object> out) throws Exception {
		// TODO Auto-generated method stub
		if (in.readableBytes() < 4) {
			return;
		}
		out.add(new UnixTime(in.readUnsignedInt()));
	}
}

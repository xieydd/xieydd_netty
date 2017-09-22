package Handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 

* @ClassName: TimeClientHandler 

* @Description: TODO(TimeClient的处理类，将32位时间消息转换成时间格式) 

* @author xieydd xieydd@gmail.com  

* @date 2017-9-22 下午3:17:30 

*
 */
public class TimeClientHandler extends ChannelInboundHandlerAdapter{
	
	/*@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		// TODO Auto-generated method stub
		ByteBuf b = (ByteBuf)msg;
		try{
			Long time =  (b.readUnsignedInt()-2208988800L)*1000L;
			System.out.print(new Date(time));
			ctx.close();
		}finally{
			b.release();
		}
	}*/
	//第一次修改
	/**
	 * 原因:由于在TCP/TP中接受的书记是在Socket的BUf流中的，基于流传输并不是包队列而是字节队列，可能出现这样的情况 abc def igh是输入但是在基于字节队列中将其输出成 abcd ef igh这样
	 * 这样传入的32位数据可能会被拆分
	 */
	//解决方法1:构建一个内部可缓冲区，等到4个字节的数据完全传入
	/*private ByteBuf buf;
	
	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		buf.release();
		buf = null;
	}
	
	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		buf = ctx.alloc().buffer(4);
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		// TODO Auto-generated method stub
		ByteBuf b = (ByteBuf)msg;
		buf.writeBytes(b);
		b.release();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		if(buf.readableBytes()>=4) {
			long time = (buf.readUnsignedInt()- 2208988800L)*1000L;
			
			System.out.println(sdf.format(new Date(time)));
			ctx.close();
		}
	}*/
	
	//方法2
	/**
	 * 当有可变长度的字段组成更加复杂的协议，第一种方法将会十分麻烦
	 * 将整个channelHandler拆分为TimeDecoder处理数据拆分 问题 ;TimeClientHandler原始版本实现
	 * Netty提供可扩展类对于TimeDecoder
	 * ByteToMessageDecoder 是 ChannelInboundHandler 的一个实现类，他可以在处理数据拆分的问题上变得很简单
	 * 当新数据接受的时候会调用decode方法对于数据进行处理内部的累计缓存;当累计缓存李没有足够的数据的时候可以在out对象中放任意的数据，当有更多的数据被接受的时候会继续调用这个方法进行decode
	 * public class TimeDecoder extends ByteToMessageDecoder { // (1)
			@Override
			protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) { // (2)
				if (in.readableBytes() < 4) {
					return;
				}
				out.add(in.readBytes(4)); 
			}
		}
		
		
	 */
	
	
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		// TODO Auto-generated method stub
		cause.printStackTrace();
		ctx.close();
	}
	
}

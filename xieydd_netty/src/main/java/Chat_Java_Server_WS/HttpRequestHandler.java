package Chat_Java_Server_WS;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.DefaultFileRegion;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.DefaultHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.stream.ChunkedNioFile;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.URISyntaxException;
import java.net.URL;
/**
 * 

* @ClassName: HttpRequestHandler 

* @Description: TODO(请求处理类) 

* @author xieydd xieydd@gmail.com  

* @date 2017-9-30 下午5:01:54 

*
 */
public class HttpRequestHandler extends
		SimpleChannelInboundHandler<FullHttpRequest> {

	private final String wsUri;
	private static final File INDEX;

	static {
		URL location = HttpRequestHandler.class.getProtectionDomain()
				.getCodeSource().getLocation();
		try {
			String path = location.toURI() + "WebsocketChatClient.html";
			path = !path.contains("file:") ? path : path.substring(5);
			INDEX = new File(path);
		} catch (URISyntaxException e) {
			throw new IllegalStateException(
					"Unable to location WebsocketChatClient.html", e);
		}
	}

	public HttpRequestHandler(String wsUri) {
		this.wsUri = wsUri;
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx,
			FullHttpRequest request) throws Exception {
		// TODO Auto-generated method stub
		if (wsUri.equalsIgnoreCase(request.getUri())) {
			// 如果请求是 WebSocket 升级，递增引用计数器（保留）并且将它传递给在 ChannelPipeline 中的下个
			// 如果该 HTTP 请求被发送到URI “/ws”，调用 FullHttpRequest 上的 retain()，并通过调用 fireChannelRead(msg) 转发到下一个 ChannelInboundHandler。retain() 是必要的，因为 channelRead() 完成后，它会调用 FullHttpRequest 上的 release() 来释放其资源
			ctx.fireChannelRead(request.retain());
		} else {
			if (HttpHeaders.is100ContinueExpected(request)) {
				// 处理符合 HTTP 1.1的 "100 Continue" 请求
				send100Continue(ctx);
			}
		}

		// 读取默认的 WebsocketChatClient.html 页面
		RandomAccessFile file = new RandomAccessFile(INDEX, "r");

		HttpResponse response = new DefaultHttpResponse(
				request.getProtocolVersion(), HttpResponseStatus.OK);
		response.headers().set(HttpHeaders.Names.CONTENT_TYPE,
				"text/html;charset=UTF-8");
		boolean keepAlive = HttpHeaders.isKeepAlive(request);

		//判断 keepalive 是否在请求头里面
		if (keepAlive) {
			response.headers().set(HttpHeaders.Names.CONTENT_LENGTH,
					file.length());
			response.headers().set(HttpHeaders.Names.CONNECTION,
					HttpHeaders.Values.KEEP_ALIVE);
		}
		//写 HttpResponse 到客户端
		ctx.write(response);
		
		//写 index.html 到客户端，判断 SslHandler 是否在 ChannelPipeline 来决定是使用 DefaultFileRegion 还是ChunkedNioFile
		if (ctx.pipeline().get(SslHandler.class) == null) {
			ctx.write(new DefaultFileRegion(file.getChannel(), 0, file.length()));
		} else {
			ctx.write(new ChunkedNioFile(file.getChannel()));
		}
		
		//写并刷新 LastHttpContent 到客户端，标记响应完成,写 LastHttpContent 来标记响应的结束，并终止它
		ChannelFuture future = ctx
				.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);
		
		//如果不要求 keepalive ，添加 ChannelFutureListener 到 ChannelFuture 对象的最后写入，并关闭连接。注意，这里我们调用 writeAndFlush() 来刷新所有以前写的信息
		if (!keepAlive) {
			future.addListener(ChannelFutureListener.CLOSE);
		}
		//如果 keepalive 没有要求，当写完成时，关闭 Channel
		file.close();
	}

	private void send100Continue(ChannelHandlerContext ctx) {
		// TODO Auto-generated method stub
		//在 头被设置后，写一个 HttpResponse 返回给客户端。注意，这是不是 FullHttpResponse，唯一的反应的第一部分。此外，我们不使用 writeAndFlush() 在这里 - 这个是在最后完成
		FullHttpResponse response = new DefaultFullHttpResponse(
				HttpVersion.HTTP_1_1, HttpResponseStatus.CONTINUE);
		ctx.writeAndFlush(response);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		// TODO Auto-generated method stub
		Channel incoming = ctx.channel();
		System.out.println("Client:" + incoming.remoteAddress() + "异常");
		cause.printStackTrace();
		ctx.close();
	}
}

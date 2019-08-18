package test.proxy.three;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface Service
{
    public void serve(InputStream in, OutputStream out) throws IOException;
    // 一个简单的service，它向客户端显示当前sever上的时间并负责关闭客户端连接
}


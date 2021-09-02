# notice-agent
```java


运行异常通知:
项目:lead-admin
信息:connect timed out
 
 java.net.SocketTimeoutException: connect timed out
    at java.net.PlainSocketImpl.socketConnect(Native Method)
    at java.net.AbstractPlainSocketImpl.doConnect(AbstractPlainSocketImpl.java:350)
    at java.net.AbstractPlainSocketImpl.connectToAddress(AbstractPlainSocketImpl.java:206)
    at java.net.AbstractPlainSocketImpl.connect(AbstractPlainSocketImpl.java:188)
    at java.net.SocksSocketImpl.connect(SocksSocketImpl.java:392)
    at java.net.Socket.connect(Socket.java:607)
    at okhttp3.internal.platform.Platform.connectSocket(Platform.java:130)
    at okhttp3.internal.connection.RealConnection.connectSocket(RealConnection.java:263)
    at okhttp3.internal.connection.RealConnection.connect(RealConnection.java:183)
    at okhttp3.internal.connection.ExchangeFinder.findConnection(ExchangeFinder.java:224)
    at okhttp3.internal.connection.ExchangeFinder.findHealthyConnection(ExchangeFinder.java:107)
    at okhttp3.internal.connection.ExchangeFinder.find(ExchangeFinder.java:87)

```

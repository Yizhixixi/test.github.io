---
layout: post
category: life
title: 老板急坏了，公司网站的 HTTPS 过期了
tagline: by 沉默王二
tags: 
  - 程序员
---

端午出去玩的时候，老板打电话说公司网站的 HTTPS 过期了，访问不了（见下图），要我立马升级一下。可惜我当时没带电脑，无能为力，可把老板急坏了。

<!--more-->



![](http://www.itwanger.com/assets/images/2020/06/java-https-01.png)

没办法，急就先急着，只能等我有电脑了才能搞。点击高级，可以看到以下信息：

>z.xxxx.cn 通常会使用加密技术来保护您的信息。Google Chrome 此次尝试连接到 z.xxxx.cn 时，此网站发回了异常的错误凭据。这可能是因为有攻击者在试图冒充 z.xxxx.cn，或 Wi-Fi 登录屏幕中断了此次连接。请放心，您的信息仍然是安全的，因为 Google Chrome 尚未进行任何数据交换便停止了连接。
>
>您目前无法访问 z.xxxx.cn，因为此网站使用了 HSTS。网络错误和攻击通常是暂时的，因此，此网页稍后可能会恢复正常。

我使用的是 FreeSSL 证书，原因很简单，老板不舍得掏钱，这个证书每次申请只能免费使用一年。

[FreeSSL](https://freessl.cn/) 是一个免费提供 HTTPS 证书申请、HTTPS 证书管理和 HTTPS 证书到期提醒服务的网站，旨在推进 HTTPS 证书的普及与应用，简化证书申请的流程。

![](http://www.itwanger.com/assets/images/2020/06/java-https-02.png)

由于我之前已经注册过了，所以从「控制台」的证书列表里就可以看到过期信息。

![](http://www.itwanger.com/assets/images/2020/06/java-https-03.png)

不过很遗憾，没有直接重新申请的选项。只能在首页重新填写域名，点击「创建免费的 SSL 证书」。

![](http://www.itwanger.com/assets/images/2020/06/java-https-04.png)

品牌证书选择「TRUSTAsia」就行了，可以免费使用一年，到期了重新再申请一下就可以了。虽然麻烦点，但能给老板省点钱，看我这良心员工啊。

完事后会跳转到下图这个页面，注意填写一下邮箱。

![](http://www.itwanger.com/assets/images/2020/06/java-https-05.png)

有些同学可能对选项不太了解，我这里统一解释下：

**1）证书类型**

我选择的是 RSA，那 ECC 又是什么，两者有什么区别？

HTTPS 通过 TLS 层和证书机制提供了内容加密、身份认证和数据完整性三大功能，可以有效防止数据被监听或篡改，还能抵御 MITM（中间人）攻击。TLS 在实施加密过程中，需要用到非对称密钥交换和对称内容加密两大算法。

对称内容加密强度非常高，加解密速度也很快，只是无法安全地生成和保管密钥。在 TLS 协议中，应用数据都是经过对称加密后传输的，传输中所使用的对称密钥，则是在握手阶段通过非对称密钥交换而来。常见的 AES-GCM、ChaCha20-Poly1305，都是对称加密算法。

非对称密钥交换能在不安全的数据通道中，产生只有通信双方才知道的对称加密密钥。目前最常用的密钥交换算法有 RSA 和 ECDHE：RSA 历史悠久，支持度好，但不支持 PFS（Perfect Forward Secrecy）；而 ECDHE 是使用了 ECC（椭圆曲线）的 DH（Diffie-Hellman）算法，计算速度快，支持 PFS。

是不是一下子就点醒了你？

**2）验证类型**

我选择的是文件验证，那 DNS 验证又是什么，两者有什么区别？

首先，我们需要明白一点，CA（Certificate Authority，证书颁发机构） 需要验证我们是否拥有该域名，这样才给我们颁发证书。

文件验证（HTTP）：CA 将通过访问特定 URL 地址来验证我们是否拥有域名的所有权。因此，我们需要下载给定的验证文件，并上传到您的服务器。

DNS 验证：CA 将通过查询 DNS 的 TXT 记录来确定我们对该域名的所有权。我们只需要在域名管理平台将生成的 TXT 记录名与记录值添加到该域名下，等待大约 1 分钟即可验证成功。

所以，如果对服务器操作方便的话，可以选择文件验证；如果对域名的服务器操作比较方便的话，可以选择 DNS 验证。如果两个都方便的话，请随意选啦。

**3）CSR 生成**

我选择的是离线生成，这也是 FreeSSL 现在推荐的方式，那到底三个选项之间有什么区别呢？

离线生成：私钥在本地加密存储，更安全；公钥自动合成，支持常见证书格式转换，方便部署；支持部分 WebServer 的一键部署，非常便捷。

离线生成的时候，需要先安装 KeyManager，可以提供安全便捷的 SSL 证书申请和管理。下载地址如下：

>https://keymanager.org/


浏览器生成：在浏览器支持 Web Cryptography 的情况下，会使用浏览器根据用户的信息生成 CSR 文件。

>Web Cryptography，网络密码学，用于在 Web 应用程序中执行基本加密操作的 JavaScript API。很多浏览器并不支持

我有 CSR：可以粘贴自己的 CSR，然后创建。

明白区别之后，选择「点击创建」，如果没有安装 KeyManager 的话，会弹出提示对话框，让你安装。

![](http://www.itwanger.com/assets/images/2020/06/java-https-06.png)

直接点击「安装 KeyManager」进行下载。

![](http://www.itwanger.com/assets/images/2020/06/java-https-07.png)

双击运行安装，成功后打开 KeyManager。

![](http://www.itwanger.com/assets/images/2020/06/java-https-08.png)

填写密码后点击「开始」，稍等片刻，出现如下界面。

![](http://www.itwanger.com/assets/images/2020/06/java-https-09.png)

回到 FreeSSL 首页，点击下图中红色框中的链接「再次尝试启动 KeyManager」。

![](http://www.itwanger.com/assets/images/2020/06/java-https-10.png)

注意 KeyManager 界面的变化，会出现以下界面中的信息。

![](http://www.itwanger.com/assets/images/2020/06/java-https-11.png)

可以回到浏览器页面，点击「继续」按钮：

![](http://www.itwanger.com/assets/images/2020/06/java-https-12.png)

会跳出文件验证的提示信息：

![](http://www.itwanger.com/assets/images/2020/06/java-https-13.png)

点击右下角的「下载文件」。

![](http://www.itwanger.com/assets/images/2020/06/java-https-14.png)

好了，现在链接服务器，将下载好的文件上传到「文件路径」处指出的路径下，一定要路径匹配上，否则无法完成验证。

![](http://www.itwanger.com/assets/images/2020/06/java-https-15.png)

文件上传成功后，就可以「点击验证」，稍等片刻后，就会出现以下提示信息：

![](http://www.itwanger.com/assets/images/2020/06/java-https-16.png)

点击「保存到 KeyManager」，可以看到证书的有效期延长了。

![](http://www.itwanger.com/assets/images/2020/06/java-https-17.png)

选择「导出证书」：

![](http://www.itwanger.com/assets/images/2020/06/java-https-18.png)

我的服务器软件使用的是 Tomcat，所以选择导出的格式是 jks。记住你的私钥加密密码，后面要用。

![](http://www.itwanger.com/assets/images/2020/06/java-https-19.png)

完事后点击「导出」按钮。

![](http://www.itwanger.com/assets/images/2020/06/java-https-20.png)


将生成好的证书，上传到服务器。

![](http://www.itwanger.com/assets/images/2020/06/java-https-21.png)


接下来，打开 Tomcat 的 server.xml 文件，配置一下 Connector 链接。

```
 <Connector port="81" protocol="HTTP/1.1"
                        maxThreads="250" maxHttpHeaderSize="8192" acceptCount="100" connectionTimeout="60000" keepAliveTimeout="200000"
                        redirectPort="8443"            
                        useBodyEncodingForURI="true" URIEncoding="UTF-8"  
                        compression="on" compressionMinSize="2048" noCompressionUserAgents="gozilla, traviata"   
            compressableMimeType="text/html,text/xml,application/xml,application/json,text/javascript,application/javascript,text/css,text/plain,text/json,image/png,image/gif"/>

<Connector
  protocol="org.apache.coyote.http11.Http11NioProtocol"
  port="443" maxThreads="200"
  scheme="https" secure="true" SSLEnabled="true"
  keystoreFile="/home/backup/xxx.cn.jks" keystorePass="Chenmo"
  clientAuth="false" sslProtocol="TLS"
useBodyEncodingForURI="true" URIEncoding="UTF-8"  
                        compression="on" compressionMinSize="2048" noCompressionUserAgents="gozilla, traviata"   
            compressableMimeType="text/html,text/xml,application/xml,application/json,text/javascript,application/javascript,text/css,text/plain,text/json,image/png,image/gif"
/>
```

其中 keystorePass 为导出证书时私钥的加密密码。

重启 Tomcat 后，重新访问一下网站，发现网站恢复正常了。

![](http://www.itwanger.com/assets/images/2020/06/java-https-22.png)

好了，HTTPS 它回来了，赶紧给老板吱一声，网站几天不能用，少卖不少酒，少挣不少钱啊，嘿嘿。

同学们，学到了吧？网站想从 HTTP 升级到 HTTPS 并不难，按照我给出的这份攻略，五分钟就能轻松搞定，关键是还免费，真香警告！

我是沉默王二，一枚有趣的程序员。如果觉得文章对你有点帮助，请微信搜索「 **沉默王二** 」第一时间阅读，回复【**666**】更有我为你精心准备的 500G 高清教学视频（已分门别类）。

>本文 [GitHub](https://github.com/qinggee/itwanger.github.io) 已经收录，有大厂面试完整考点，欢迎 Star。

**原创不易，莫要白票，请你为本文点个赞吧**，这将是我写作更多优质文章的最强动力。
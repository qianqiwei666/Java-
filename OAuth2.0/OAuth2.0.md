# 一、4种授权模式概述

## 1、授权码模式

> 第三方Web服务器端应用与第三方原生App

## 2、简化模式

> 第三方单页面应用

## 3、密码模式

> 客户端携带用户名与密码从认证服务器授权得到token

![密码模式](密码模式.svg)

## 4、客户端模式

> 没有用户参与的,完全信任的服务器端服务

![客户端模式](客户端模式.svg)

# 一、OAuth2.0使用详情

## 一、ClientDetails详解

```java
public interface ClientDetails extends Serializable {

  
   //客户端id 
   String getClientId();

   //此客户端可以访问的资源。如果为空，调用者可以忽略。
   Set<String> getResourceIds();

   //服务端是否需要验证密文
   boolean isSecretRequired();

   //获取客户端的密文   
   String getClientSecret();

   //是否需要获取客户端获取数据
   boolean isScoped();

   //获取客户端可访问的作用域 
   Set<String> getScope();

   //客户端被授权的授权类型
   Set<String> getAuthorizedGrantTypes();

   //获取客户端重定向uri
   Set<String> getRegisteredRedirectUri();

   //获取用户访问权限
   Collection<GrantedAuthority> getAuthorities();

   //设置token过期时间
   Integer getAccessTokenValiditySeconds();
   
   //刷新token令牌有效期
   Integer getRefreshTokenValiditySeconds();
   
   //测试客户是否需要用户批准特定范围。
   boolean isAutoApprove(String scope);

   //额外追加信息。
   Map<String, Object> getAdditionalInformation();

}
```

> 1. ClientId:用来标识客户端的Id
> 2. Secret:请求加密密文。
> 3. Scope:客户端请求数据的范围(哪部分请求可以获取)
> 4. AuthorizedGrantType:授权类型,一般为空。
> 5. Authorities:客户端的权限。
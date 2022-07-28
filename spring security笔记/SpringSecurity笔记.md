# 一、前置

## 一、SSO

> 单点登录

## 二、Session共享问题

> 我们通常单独开一台服务器,将Session存储到Redis,数据库,以及文件中(不推荐),每台服务从单独的服务器中拿去Session

## 三、CSRF攻击

> 当我们登录网站之后,我们的浏览器会存储Cookie,当我们访问其他网站时,有些网站可能内置上一个页面访问的脚本,通过cookie非法访问我们上一个网站,这就是CSRF攻击

## 四、XSS攻击

> A网站内嵌B网站链接,当我们访问A网站的同时,也会访问B网站,就造成了XSS攻击。

> 备注:代码示例:[代码演示](./演示.7z)

## 五、SQL实例

```sql


drop database user;
create database user;
use user;

create table role
(
    role_id   int auto_increment
        primary key,
    role_name varchar(20) not null
);

INSERT INTO user.role (role_id, role_name) VALUES (1, 'ROLE_admin');
INSERT INTO user.role (role_id, role_name) VALUES (2, 'ROLE_root');

create table user_message
(
    id       int auto_increment
        primary key,
    username varchar(20)  unique key ,
    password varchar(20) not null
);

INSERT INTO user.user_message (id, username, password) VALUES (1, 'qianqiwei', 'qianqiwei');
INSERT INTO user.user_message (id, username, password) VALUES (2, 'liudehua', 'liudehua');



create table user_role
(
    user_id int not null,
    role_id int not null
);

INSERT INTO user.user_role (user_id, role_id) VALUES (1, 1);
INSERT INTO user.user_role (user_id, role_id) VALUES (1, 2);
INSERT INTO user.user_role (user_id, role_id) VALUES (2, 1);


create table user_status
(
    user_id               int                  not null
        primary key,
    AccountNonExpired     tinyint(1) default 1 not null,
    AccountNonLocked      tinyint(1) default 1 not null,
    CredentialsNonExpired tinyint(1) default 1 not null,
    Enabled               tinyint(1) default 1 not null
);

INSERT INTO user.user_status (user_id, AccountNonExpired, AccountNonLocked, CredentialsNonExpired, Enabled) VALUES (1, 1, 1, 1, 1);
INSERT INTO user.user_status (user_id, AccountNonExpired, AccountNonLocked, CredentialsNonExpired, Enabled) VALUES (2, 1, 1, 1, 1);
```

# 二、Spring Security配置选项

## 一、自定义登录

``` java
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //配置拦截逻辑
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //设置哪些请求需要被验证
        http.authorizeRequests()
                .antMatchers("/success","/")//设置需要校验的路径 如果是any的话,之后不能做放行
                .authenticated()
                .antMatchers("/fail")//放行路径
                .permitAll()
                .and()
                .formLogin()//自定义登录配置
                .loginPage("/loginPage")//自定义登录界面,第二个参数为true时候登陆成功后跳到自定义登录成功页面,false跳转到默认路径
                .loginProcessingUrl("/test/login")//自定义认证路径
                .permitAll()//放行所有的请求
                .passwordParameter("username")
                .passwordParameter("password")
                .successHandler(new AuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
                        httpServletResponse.sendRedirect("/success");
                    }
                })
                .failureHandler(new AuthenticationFailureHandler() {
                    @Override
                    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
                        httpServletResponse.sendRedirect("/fail");
                    }
                })
                .and()
                .csrf()
                .disable();//关闭csrf 如果开启就会下发hash值去进行验证
    }
    //配置验证逻辑
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //手动设置用户名与密码(创建临时的用户名与密码)
        auth.inMemoryAuthentication()
                //配置多个用户名与密码(必须加密)
                .withUser("liudehua")
                .password(new BCryptPasswordEncoder().encode("liudehua"))
                .roles("admin")
                .and()
                .withUser("qianqiwei")
                .password(new BCryptPasswordEncoder().encode("qianqiwei"))
                .roles("root");
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


}


```

> 注意:一般在anyRequest()之前进行放行操作,不能够颠倒,最好放行在不放行之前
>
> ```java
> http.authorizeRequests()
>         .antMatchers("/img/**") 
>         .permitAll()
>         .anyRequest()
>         .authenticated()
> ```

## 二、自定义登出

```java
.logout()
.logoutUrl("/application/logout") //通过哪个路径进行登出
.addLogoutHandler(new LogoutHandler() { //登出的操作逻辑
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        System.out.println("登出成功");
        System.out.println("跳转中");
        try {
            response.sendRedirect("/public");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
})
```

## 三、自定义登录失败(处理器)

```java
//设置自定义登陆失败页面
.failureUrl("/fail")
.failureHandler(((request, response, exception) -> {
    //这里可以捕获到异常
    System.out.println(exception.getMessage());
    //重定向
    response.sendRedirect("/public");
}))
```

## 四、自定义登录成功(处理器)

```java
.loginPage("/login")
.permitAll()
.loginProcessingUrl("/login/check")
.successHandler(new AuthenticationSuccessHandler() {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        System.out.println(authentication.getPrincipal());
        //这里SpringSecurity将我们的密码进行隐藏
        System.out.println(authentication.getCredentials());
        //成功之后跳转到指定的页面
        httpServletResponse.sendRedirect("/vip");
    }
})
```

## 五、配置时 HttpSecurity与WebSecurity有什么区别?

### 一、区别

> 1. `WebSecurity`构建目标是整个`Spring Security`安全过滤器`FilterChainProxy`,
> 2. 而`HttpSecurity`的构建目标仅仅是`FilterChainProxy`中的一个`SecurityFilterChain`。

### 二、运用

```java
@Override
public void configure(WebSecurity web) throws Exception {
    //通常忽略静态请求
    web.ignoring().antMatchers("/img/**");
}
```

## 六、记住我选项

> 自动设置过期时间,当浏览器从关闭时,下次打开浏览器访问就可以直接访问。

```java
@Override
protected void configure(HttpSecurity http) throws Exception {
    http.rememberMe();
}
```

## 七、实现单点登录

> http.sessionManagement().maximumSessions(1);  加入这个配置,SpringSecurity自动实现单点登录



## 八、怎么关闭session(一般由token代替)

```java
.sessionManagement()
.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
```

## 九、怎么在UsernamePasswordFilter进行执行替换

```java
.and()
.addFilterBefore(自定义前置拦截器,UsernamePasswordAuthenticationFilter.class)
.addFilterAfter(自定义后置拦截器,UsernamePasswordAuthenticationFilter.class)
.addFilterAt(自定义实现AbstractAuthenticationProcessingFilter的自定义类,UsernamePasswordAuthenticationFilter.class);
```

备注:

> 我们的拦截器最好实现OncePerRequestFilter和GenericFilterBean

## 十、未登录进行处理

> 当SpringSecurity上下文中没有UsernamePasswordToken的时候,就进行未登录处理

```java
.exceptionHandling()
.authenticationEntryPoint(new AuthenticationEntryPoint() {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        System.out.println("您还没有登录");
        response.sendRedirect("/login");
    }
})
//我们设置了 http.authorizeRequests()
//                .anyRequest()
//                .authenticated()
//当Spring上下文容器里没有token的时候就进行
```

## 十一、角色权限不够处理

> 当我们的角色权限不够的时候就进行处理

```
.accessDeniedHandler(new AccessDeniedHandler() {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        System.out.println("您的权限不够");
    }
})
```



# 三、SpringSecurity重要对象以及作用

### 一、User

> 这里的User实现了UserDetails接口,包装SpringSecurity验证信息对象。

``` java

public class User implements UserDetails, CredentialsContainer {
    private static final long serialVersionUID = 530L;
    private static final Log logger = LogFactory.getLog(User.class);
    private String password;
    private final String username;
    private final Set<GrantedAuthority> authorities;
    private final boolean accountNonExpired;
    private final boolean accountNonLocked;
    private final boolean credentialsNonExpired;
    private final boolean enabled;

    public User(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        this(username, password, true, true, true, true, authorities);
    }

    public User(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        if (username != null && !"".equals(username) && password != null) {
            this.username = username;
            this.password = password;
            this.enabled = enabled;
            this.accountNonExpired = accountNonExpired;
            this.credentialsNonExpired = credentialsNonExpired;
            this.accountNonLocked = accountNonLocked;
            this.authorities = Collections.unmodifiableSet(sortAuthorities(authorities));
        } else {
            throw new IllegalArgumentException("Cannot pass null or empty values to constructor");
        }
    }

    public Collection<GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    public String getPassword() {
        return this.password;
    }

    public String getUsername() {
        return this.username;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    public void eraseCredentials() {
        this.password = null;
    }

    private static SortedSet<GrantedAuthority> sortAuthorities(Collection<? extends GrantedAuthority> authorities) {
        Assert.notNull(authorities, "Cannot pass a null GrantedAuthority collection");
        SortedSet<GrantedAuthority> sortedAuthorities = new TreeSet(new User.AuthorityComparator());
        Iterator var2 = authorities.iterator();

        while(var2.hasNext()) {
            GrantedAuthority grantedAuthority = (GrantedAuthority)var2.next();
            Assert.notNull(grantedAuthority, "GrantedAuthority list cannot contain any null elements");
            sortedAuthorities.add(grantedAuthority);
        }

        return sortedAuthorities;
    }

    public boolean equals(Object rhs) {
        return rhs instanceof User ? this.username.equals(((User)rhs).username) : false;
    }

    public int hashCode() {
        return this.username.hashCode();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString()).append(": ");
        sb.append("Username: ").append(this.username).append("; ");
        sb.append("Password: [PROTECTED]; ");
        sb.append("Enabled: ").append(this.enabled).append("; ");
        sb.append("AccountNonExpired: ").append(this.accountNonExpired).append("; ");
        sb.append("credentialsNonExpired: ").append(this.credentialsNonExpired).append("; ");
        sb.append("AccountNonLocked: ").append(this.accountNonLocked).append("; ");
        if (!this.authorities.isEmpty()) {
            sb.append("Granted Authorities: ");
            boolean first = true;
            Iterator var3 = this.authorities.iterator();

            while(var3.hasNext()) {
                GrantedAuthority auth = (GrantedAuthority)var3.next();
                if (!first) {
                    sb.append(",");
                }

                first = false;
                sb.append(auth);
            }
        } else {
            sb.append("Not granted any authorities");
        }

        return sb.toString();
    }

    public static User.UserBuilder withUsername(String username) {
        return builder().username(username);
    }

    public static User.UserBuilder builder() {
        return new User.UserBuilder();
    }

    /** @deprecated */
    @Deprecated
    public static User.UserBuilder withDefaultPasswordEncoder() {
        logger.warn("User.withDefaultPasswordEncoder() is considered unsafe for production and is only intended for sample applications.");
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        User.UserBuilder var10000 = builder();
        encoder.getClass();
        return var10000.passwordEncoder(encoder::encode);
    }

    public static User.UserBuilder withUserDetails(UserDetails userDetails) {
        return withUsername(userDetails.getUsername()).password(userDetails.getPassword()).accountExpired(!userDetails.isAccountNonExpired()).accountLocked(!userDetails.isAccountNonLocked()).authorities(userDetails.getAuthorities()).credentialsExpired(!userDetails.isCredentialsNonExpired()).disabled(!userDetails.isEnabled());
    }

    public static class UserBuilder {
        private String username;
        private String password;
        private List<GrantedAuthority> authorities;
        private boolean accountExpired;
        private boolean accountLocked;
        private boolean credentialsExpired;
        private boolean disabled;
        private Function<String, String> passwordEncoder;

        private UserBuilder() {
            this.passwordEncoder = (password) -> {
                return password;
            };
        }

        public User.UserBuilder username(String username) {
            Assert.notNull(username, "username cannot be null");
            this.username = username;
            return this;
        }

        public User.UserBuilder password(String password) {
            Assert.notNull(password, "password cannot be null");
            this.password = password;
            return this;
        }

        public User.UserBuilder passwordEncoder(Function<String, String> encoder) {
            Assert.notNull(encoder, "encoder cannot be null");
            this.passwordEncoder = encoder;
            return this;
        }

        public User.UserBuilder roles(String... roles) {
            List<GrantedAuthority> authorities = new ArrayList(roles.length);
            String[] var3 = roles;
            int var4 = roles.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                String role = var3[var5];
                Assert.isTrue(!role.startsWith("ROLE_"), () -> {
                    return role + " cannot start with ROLE_ (it is automatically added)";
                });
                authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
            }

            return this.authorities((Collection)authorities);
        }

        public User.UserBuilder authorities(GrantedAuthority... authorities) {
            return this.authorities((Collection)Arrays.asList(authorities));
        }

        public User.UserBuilder authorities(Collection<? extends GrantedAuthority> authorities) {
            this.authorities = new ArrayList(authorities);
            return this;
        }

        public User.UserBuilder authorities(String... authorities) {
            return this.authorities((Collection)AuthorityUtils.createAuthorityList(authorities));
        }

        public User.UserBuilder accountExpired(boolean accountExpired) {
            this.accountExpired = accountExpired;
            return this;
        }

        public User.UserBuilder accountLocked(boolean accountLocked) {
            this.accountLocked = accountLocked;
            return this;
        }

        public User.UserBuilder credentialsExpired(boolean credentialsExpired) {
            this.credentialsExpired = credentialsExpired;
            return this;
        }

        public User.UserBuilder disabled(boolean disabled) {
            this.disabled = disabled;
            return this;
        }

        public UserDetails build() {
            String encodedPassword = (String)this.passwordEncoder.apply(this.password);
            return new User(this.username, encodedPassword, !this.disabled, !this.accountExpired, !this.credentialsExpired, !this.accountLocked, this.authorities);
        }
    }

    private static class AuthorityComparator implements Comparator<GrantedAuthority>, Serializable {
        private static final long serialVersionUID = 530L;

        private AuthorityComparator() {
        }

        public int compare(GrantedAuthority g1, GrantedAuthority g2) {
            if (g2.getAuthority() == null) {
                return -1;
            } else {
                return g1.getAuthority() == null ? 1 : g1.getAuthority().compareTo(g2.getAuthority());
            }
        }
    }
}

```

### 二、UserDetails

#### 一、作用

> UserDetails实现了一套信息规范,一般开发中需要我们创建一个类来实现这个方法。

``` java

public interface UserDetails extends Serializable {
    Collection<? extends GrantedAuthority> getAuthorities();

    String getPassword();  //获取密码

    String getUsername();  //获取用户

    boolean isAccountNonExpired(); //账户是否过期,过期无法验证

    boolean isAccountNonLocked(); //指定用户是否被锁定或者解锁,锁定的用户无法进行身份验证

    boolean isCredentialsNonExpired();//指示是否已过期的用户的凭据(密码),过期的凭据防止认证

    boolean isEnabled(); //是否被禁用,禁用的用户不能身份验证
}

```

#### 二、自定义实现类的实现

```java
public class CustomizeUserDetailImpl implements UserDetails {
    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> collection;
    private boolean isAccountNonExpired;
    private boolean isAccountNonLocked;
    private boolean isCredentialsNonExpired;
    private boolean isEnabled;


    public CustomizeUserDetailImpl(String username, String password, Collection<? extends GrantedAuthority> collection, boolean isAccountNonExpired, boolean isAccountNonLocked, boolean isCredentialsNonExpired, boolean isEnabled) {
        this.username = username;
        this.password = password;
        this.collection = collection;
        this.isAccountNonExpired = isAccountNonExpired;
        this.isAccountNonLocked = isAccountNonLocked;
        this.isCredentialsNonExpired = isCredentialsNonExpired;
        this.isEnabled = isEnabled;
    }

    public CustomizeUserDetailImpl() {
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.collection;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.isEnabled;
    }

    @Override
    public String toString() {
        return "CustomizeUserDetailImpl{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", collection=" + collection +
                ", isAccountNonExpired=" + isAccountNonExpired +
                ", isAccountNonLocked=" + isAccountNonLocked +
                ", isCredentialsNonExpired=" + isCredentialsNonExpired +
                ", isEnabled=" + isEnabled +
                '}';
    }
}
```



### 三、UserDetailsService

#### 一、作用

> 根据用户名查找来返回一个UserDetails,实现这个方法的类主要为了从数据库拿取数据返回userDetails

```java
public interface UserDetailsService {
   // ~ Methods
   // ========================================================================================================

   /**
    * Locates the user based on the username. In the actual implementation, the search
    * may possibly be case sensitive, or case insensitive depending on how the
    * implementation instance is configured. In this case, the <code>UserDetails</code>
    * object that comes back may have a username that is of a different case than what
    * was actually requested..
    *
    * @param username the username identifying the user whose data is required.
    *
    * @return a fully populated user record (never <code>null</code>)
    *
    * @throws UsernameNotFoundException if the user could not be found or the user has no
    * GrantedAuthority
    */
    //传入username,通过username查询到用户所有的信息以UserDetails的方式返回(在做权限校验的时候进行拿取)
   UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
```

#### 二、自定义UserDetailService实现

```java

@Component
public class CustomizeUserDetailsManager implements UserDetailsService {

    //获取用户信息
    @Autowired
    private UserMessageService userMessageServiceImpl;

    //获取用户权限
    @Autowired
    private RoleService roleServiceImpl;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //将前台传入的值传到数据库里,查询到用户的所有信息以UserDetails的方式返回
        UserMessage userMessages = userMessageServiceImpl.queryWithStatus(username);
        if (userMessages==null)throw new UsernameNotFoundException("查无此人");
        List<Role> roles = roleServiceImpl.queryRoleByID(userMessages.getId());
        //获取权限
        Collection<SimpleGrantedAuthority> collection=new ArrayList<>();
        roles.stream().forEach(item->{
            System.out.println(item);
            collection.add(new SimpleGrantedAuthority(item.getRoleName()));
        });
        CustomizeUserDetailImpl customizeUserDetail=new CustomizeUserDetailImpl(
                userMessages.getUsername(),
                userMessages.getPassword(),
                collection,
                userMessages.getUserStatus().getAccountnonexpired()==1?true:false,
                userMessages.getUserStatus().getAccountnonlocked()==1?true:false,
                userMessages.getUserStatus().getCredentialsnonexpired()==1?true:false,
                userMessages.getUserStatus().getEnabled()==1?true:false
        );
        return customizeUserDetail;
    }
}

```

### 四、UserDetailsManager

> 定制了用户的操作,以及获取UserDetails(继承了UserDetailsService)的接口规范

```java
public interface UserDetailsManager extends UserDetailsService {

   /**
    * Create a new user with the supplied details.
    */
   void createUser(UserDetails user);

   /**
    * Update the specified user.
    */
   void updateUser(UserDetails user);

   /**
    * Remove the user with the given login name from the system.
    */
   void deleteUser(String username);

   /**
    * Modify the current user's password. This should change the user's password in the
    * persistent user repository (datbase, LDAP etc).
    *
    * @param oldPassword current password (for re-authentication if required)
    * @param newPassword the password to change to
    */
   void changePassword(String oldPassword, String newPassword);

   /**
    * Check if a user with the supplied login name exists in the system.
    */
   boolean userExists(String username);

}
```

>一、实现UserDetailsManager的类
>
>1. InMemoryUserDetailsManager 在内存中对用户进行操作,创建用户。
>2. JdbcUserDetailsManager 实现了JDBC的一套规范,可用JDBC对用户进行操作或者创建用户。



### 五、AuthenticationProvider

#### 一、作用

> 我们一般去实现AuthenticationProvider接口来做权限验证

> 主要做信息比对,从UserDetails拿取用户的信息与前台输入的信息进行一个比对,验证

> UsernamePasswordAuthenticationToken实现了Authentication接口,一般返回UsernamePasswordAuthenticationToken，一般包含了用户的真实密码以及信息

#### 二、代码展示(流程)

```java
@Component
public class CustomizeAuthentication implements AuthenticationProvider {

    @Autowired
    private UserDetailsService customizeUserDetailsManager;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        //这里的Authentication包含了前台输入的用户名与密码
        //传入用户
        String username =authentication.getPrincipal().toString();
        //传入密码
        String password=authentication.getCredentials().toString();
        //这里我们传入一个UserDetailsService的实现类,来获取通过用户名获取的UserDetails信息
        UserDetails userDetails = customizeUserDetailsManager.loadUserByUsername(username);
        System.out.println(userDetails);
        //这里的密码需要进行加密验证,为了方便就不进行验证,比对流程 new BCryptPasswordEncoder().matches(没有加密的密码,加密过的密码);
        if (userDetails.getPassword().equals(password)){
            //UsernamePasswordAuthenticationToken实现了Authentication接口。
            return new UsernamePasswordAuthenticationToken(userDetails.getUsername(),userDetails.getPassword(), userDetails.getAuthorities());
        }
        throw new BadCredentialsException("密码错误");
    }

    //是否支持自定义配置,一定要开启不然进不了自定义验证
    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
```



### 六、SpringSecurity中的上下文

> SecurityContextHolder.getContext().setAuthentication(UsernamePasswordAuthenticationToken);
>
> 设置SpringSecurity上下文,一般用于登录成功

> SecurityContextHolder.clearContext();
>
> 清除SpringSecurity上下文,一般用于登录失败



### 六、继承关系(父->子)

> UserDetailsService->UserDetailsManager
>
> UserDetails->User
>
> UserDetailsManager->InMemoryUserDetailsManager 
>
> UserDetailsManager ->JdbcUserDetailsManager
>
> AuthenticationProvider ->UsernamePasswordAuthenticationToken

# 四、SpringSecurity的UserDetailsManager配置用户

### 一、InMemoryUserDetailsManager配置用户

> 基于内存配置用户

```java
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/vip/**").authenticated()
                .and()
                .formLogin()
                .loginPage("/login").permitAll()
                .loginProcessingUrl("/login/check")
                .defaultSuccessUrl("/success")//第二个参数为true时候登陆成功后跳到自定义登录成功页面,false跳转到默认路径
                .failureUrl("/fail")
                .and()
                .csrf()
                .disable();
    }
    @Bean
    public UserDetailsService userDetailsService(){
        //在内存中添加用户
        InMemoryUserDetailsManager inMemoryUserDetailsManager=new InMemoryUserDetailsManager();
        //设置角色
        Set<GrantedAuthority> authorities=new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("admin"));
        //临时存储用户,将我们的用户放到InMemoryUserDetailsManager的Map里
        inMemoryUserDetailsManager.createUser(new User("qianqiwei",new BCryptPasswordEncoder().encode("qianqiwei"),true,true,true,true,authorities));
        inMemoryUserDetailsManager.createUser(User.withUsername("qianqiwei").password(new BCryptPasswordEncoder().encode("qianqiwei")).roles("admin").build());
        return inMemoryUserDetailsManager;
    }


    //设置加密算法
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //这里放入执行流程
        auth.userDetailsService(userDetailsService());
    }
}
```



# 五、Spring Security异常整理

###  一、BadCredentialsException

> 用户名与密码错误时，抛出这个异常

### 二、SessionAuthenticationException

> 当前会话失效。

### 三、UsernameNotFoundException

> 用户名没有找到异常,当用户输入用户名时,数据库中没有当前用户,抛出此异常

### 四、AuthenticationServiceException

> 系统自身无法校验(系统问题),则会抛出这个异常

### 五、NonceExpiredException

> 身份过期才会抛出此异常

### 六、LockedException

> 当前账户被锁定

### 七、DisabledException

> 账户被禁用

### 八、CredentialsExpiredException

> 密码过期

### 九、AccountExpiredException

> 账户过期



# 六、角色管理

### 一、Role之间的区别

> 1. hasAnyRole 包含其中任意一种权限
> 2. hasRole 必须包含对应的权限
> 3. 数据库的权限名必须以ROLE_为前缀。

 注意:数据库中的权限名必须以ROLE_为前缀

```java
http.authorizeRequests()
        .antMatchers("/public") 
        .hasAnyRole("admin","root") //要访问public路径,必须要有admin或者root权限
        .antMatchers("/vip")
        .hasRole("root") //要访问vip路径,必须要有root权限
        .antMatchers("/**")
        .authenticated()
```

### 二、注解

> 1. 要用权限注解必须使用 @EnableGlobalMethodSecurity(securedEnabled = true,prePostEnabled = true)
> 2. @Secured({"ROLE_root","ROLE_admin"}) 这里的权限是或的关系,任意一个都行,权限名必须要有前缀。
> 3. @PreAuthorize("hasAnyRole('admin','root')") 这里的权限也是或的关系,任意一个都行,权限名必须不能有前缀。
> 4. @PreAuthorize("hasRole('admin') and hasRole('root')")  这两个权限必须都有

# 七、JWT

### 一、JWT的结构

> - Header(头)
> - Payload(载荷)
> - Signature(签名)

#### 一、Header结构

> {  
>
> "alg": "HS256",    (加密算法)
>
> "typ": "JWT"         (令牌类型,默认是JWT)
>
>  }

注意:

> 在实际编码中通常Base64进行加密

#### 二、Payload(载荷)

> 主要装载自定义信息,一般通常是用户。请注意，对于已签名的令牌，此信息虽然受到保护以防止篡改，但任何人都可以读取。不要将机密信息放在 JWT 的有效负载或标头元素中，除非它已加密。

例如

> {  
>
> "sub": "1234567890",
>
>  "name": "John Doe", 
>
>  "admin": true 
>
> }

注意:

> 在实际编码中通常Base64进行加密

#### 三、Signature(签名)

> 加密过程:
>
> HMACSHA256( base64UrlEncode(header) + "." +  base64UrlEncode(payload),  secret(盐) )

###  二、自定义JWT生成工具类

#### 一、工具类

```java
public class JWTUtils {
    public String create(Map<String,Object> header, Map<String,Object> payload, SignatureAlgorithm signatureAlgorithm, String salt,Date expireTime){
        DefaultJwtBuilder defaultJwtBuilder=new DefaultJwtBuilder();
        //生成token
        JwtBuilder jwtBuilder = defaultJwtBuilder
                .setHeader(header)  //设置header
                .setClaims(payload) //设置payload
                .signWith(SignatureAlgorithm.HS256, salt)//设置加密算法以及Secret
                .setExpiration(expireTime); //设置过期时间
        //获取token
        String compact = jwtBuilder.compact();
        return compact;
    }

    public Jwt ParseToken(String token,String salt){
        DefaultJwtParser defaultJwtParser=new DefaultJwtParser();
        //获取到盐
        defaultJwtParser.setSigningKey(salt);
        //解析token
        Jwt parse = defaultJwtParser.parse(token);
        return parse;
    }




}

```

#### 二、测试

```java
 DefaultJwtBuilder defaultJwtBuilder=new DefaultJwtBuilder();
    //创建Header
    Map<String,Object> header=new HashMap<>();
    header.put("alg","HS256");
    header.put("typ","JWT");
    //创建PayLoad
    Map<String,Object> payload=new HashMap<>();
    payload.put("role","[admin,root]");
    payload.put("username","qianqiwei");
    //设置token的过期时间
    LocalDateTime date = LocalDateTime.now(); //获取到现在的时间
    LocalDateTime oneMonthLater = date.plusSeconds(7); //设置过期时间
    Date expireDate = Date.from(oneMonthLater.atZone(ZoneId.systemDefault()).toInstant()); // LocalDateTime 转换
    //生成token
    String token = jwtCreateUtils.create(header, payload, SignatureAlgorithm.HS256, salt, expireDate);
    try {
        System.out.println(token);
        TimeUnit.SECONDS.sleep(8);
        try {
            System.out.println(jwtCreateUtils.ParseToken(token,salt));
        }catch (ExpiredJwtException e){
            System.out.println("token过期");
        }

    } catch (InterruptedException e) {
        e.printStackTrace();
    }


}
```





# 八、SpringSecurity源码解析

## 一、AbstractAuthenticationProcessingFilter源码解析

> 我们的UsernamePasswordAuthenticationFilter继承AbstractAuthenticationProcessingFilter抽象类,主要作验证用户名与密码,其中我们可以添加验证码校验,以及JWT校验

### 一、doFilter方法解析

```java
public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
      throws IOException, ServletException {

    //获取用户名与密码
   HttpServletRequest request = (HttpServletRequest) req;
   HttpServletResponse response = (HttpServletResponse) res;

   //是否需要进行登录验证(如果不需要登陆,直接进入下一个过滤器)
   if (!requiresAuthentication(request, response)) {
      //进入下一个过滤器
      chain.doFilter(request, response);

      return;
   }

   if (logger.isDebugEnabled()) {
      logger.debug("Request is to process authentication");
   }

    
   //获取Authentication对象
   Authentication authResult;
   try {
      //这里调用attemptAuthentication主要用于对用户名与密码进行校验,校验成功返回Authentication,如果其中有异常在try catch中进行处理
      authResult = attemptAuthentication(request, response);
      if (authResult == null) {
        
         return;
      }
     
      sessionStrategy.onAuthentication(authResult, request, response);
   }
   catch (InternalAuthenticationServiceException failed) {
      logger.error(
            "An internal error occurred while trying to authenticate the user.",
            failed);
       //当没有session时将错误交给unsuccessfulAuthentication
      unsuccessfulAuthentication(request, response, failed);

      return;
   }
   catch (AuthenticationException failed) {
      //如果发生了异常就进入unsuccessfulAuthentication进一步处理
      unsuccessfulAuthentication(request, response, failed);
      return;
   }

   // Authentication success
   if (continueChainBeforeSuccessfulAuthentication) {
      chain.doFilter(request, response);
   }
    //如果中途没有出错就将我们的Authentication放入SpringSecurity上下文中。
    //底层用到了事件监听
   successfulAuthentication(request, response, chain, authResult);
}
```

### 二、unsuccessfulAuthentication方法解析

```java
protected void unsuccessfulAuthentication(HttpServletRequest request,
      HttpServletResponse response, AuthenticationException failed)
      throws IOException, ServletException {
   SecurityContextHolder.clearContext();

   if (logger.isDebugEnabled()) {
      logger.debug("Authentication request failed: " + failed.toString(), failed);
      logger.debug("Updated SecurityContextHolder to contain null Authentication");
      logger.debug("Delegating to authentication failure handler " + failureHandler);
   }
   rememberMeServices.loginFail(request, response);
   //调用登录失败逻辑
   failureHandler.onAuthenticationFailure(request, response, failed);
}
```

### 三、successfulAuthentication方法解析

```java
protected void successfulAuthentication(HttpServletRequest request,
      HttpServletResponse response, FilterChain chain, Authentication authResult)
      throws IOException, ServletException {

   if (logger.isDebugEnabled()) {
      logger.debug("Authentication success. Updating SecurityContextHolder to contain: "
            + authResult);
   }

   //将成功的Authentication设置到SpringSecurity上下文中。
   SecurityContextHolder.getContext().setAuthentication(authResult);

   //成功设置记住我。
   rememberMeServices.loginSuccess(request, response, authResult);

   // Fire event
   if (this.eventPublisher != null) {
      eventPublisher.publishEvent(new InteractiveAuthenticationSuccessEvent(
            authResult, this.getClass()));
   }
    
   //调用成功处理器
   successHandler.onAuthenticationSuccess(request, response, authResult);
}
```

# 九、深度定制SpringSecurity

## 一、前置

### 一、概述

> Spring Security是基于Session会话,这样就会造成服务端存储大量session,导致服务端压力变大,于是将SpringSecurity中的Session替换成JWT,来进行无状态会话。

### 二、设计流程图

![SpringSecurity执行流程](D:\SpringSecurity笔记\SpringSecurity执行流程.png)

## 二、自定义UserDetailsService

> 用于从数据库查询到相应用户的信息

```java
@Slf4j
@Component
public class UserDetailsManager implements UserDetailsService {
    @Autowired
    private UserMessageService userMessageServiceImpl;
    @Autowired
    private RoleService roleServiceImpl;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("数据库中获取用户信息......");
        //获取用户信息
        UserMessage userMessage = userMessageServiceImpl.selectMessageByUsername(username);
        if (userMessage==null) throw new UsernameNotFoundException("用户未找到");
        //查询用户权限
        List<Role> roleList=roleServiceImpl.selectByUserID(userMessage.getId());
        //设置权限
        Set<SimpleGrantedAuthority> authorities=new HashSet<>();
        roleList.stream().forEach(item->{
            authorities.add(new SimpleGrantedAuthority(item.getRoleName()));
        });
        //将用户信息整理好后放入UserDetails
        UserDetails userDetails=new UserDetailsMessageBean(
                userMessage.getUsername(),
                userMessage.getPassword(),
                authorities,
                userMessage.getUserStatus().getAccountnonexpired()==1?true:false,
                userMessage.getUserStatus().getAccountnonlocked()==1?true:false,
                userMessage.getUserStatus().getCredentialsnonexpired()==1?true:false,
                userMessage.getUserStatus().getEnabled()==1?true:false
        );
        return userDetails;
    }
}
```

## 三、 自定义验证

> 通过从UserDetailsService中获取到完整的用户信息,与前台数据进行比对,对验证不通过的部分抛出异常。

```java
//进行权限认证
@Slf4j
@Component
public class AuthencationUser implements AuthenticationProvider {

    @Autowired
    private UserDetailsService userDetailsManager;

    //进行权限校验
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        log.info("数据校验中.....");
        //获取用户名与密码
        String username=authentication.getPrincipal().toString();
        String password=authentication.getCredentials().toString();
        //从userDetailsManager拿取从数据库查询的数据
        UserDetails userDetails = userDetailsManager.loadUserByUsername(username);
        log.info("检查用户状态中.....");
        //获取用户的状态进行限制
        if (!userDetails.isAccountNonExpired()) throw new AccountExpiredException("账户过期");
        if (!userDetails.isAccountNonLocked()) throw new  LockedException("当前账户被锁定");
        if (!userDetails.isCredentialsNonExpired()) throw new CredentialsExpiredException("密码过期");
        if (!userDetails.isEnabled()) throw new LockedException("当前用户被锁定");
        log.info("用户状态正常");
        //比对密码
        log.info("进行密码校验中.....");
        if (userDetails.getPassword().equals(password)){
            log.info("密码比对成功,准备返回UsernamePasswordAuthenticationToken");
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(username,password,userDetails.getAuthorities());
            return usernamePasswordAuthenticationToken;
        }
        log.info("用户密码错误");
        throw new BadCredentialsException("密码出错了");
    }


    //是否使用自定义的验证(如果要自定义必须开启)
    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
```

## 四、自定义登录验证

> 1. 通过用户传进来的用户名,密码,验证码(不是必须),生成UsernamePasswordAuthenticationToken
> 2. 将生成好的UsernamePasswordAuthenticationToken交给AuthenticationManager与数据库进行比对信息。
> 3. 比对的过程中可能产生错误我们要对其进行捕捉
> 4. 如果比对成功返回Authentication,其中包含了用户名,密码,账户状态,权限。
> 5. 对用户名与权限生成token
> 6. 返回token
>
> 通过AuthenticationManager交给UserDetailsService以及AuthenticationProvider对

```java
//继承主要是为了获取验证管理器
@Slf4j
public class LoginHandler  {

    //JWT工具
    @Autowired
    private JWTUtils jwtUtils;

    //验证码
    @Autowired
    private ShearCaptcha shearCaptcha;

    //设置JWT参数
    //设置加密类型
    private String alg;
    //设置加密算法
    private SignatureAlgorithm signatureAlgorithm;
    //设置盐
    private String sale;
    //设置过期时间
    private int expireTime;


    private AuthenticationManager authenticationManager;

    public void setAuthenticationManager(AuthenticationManager authenticationManager){
        this.authenticationManager=authenticationManager;
    }

    public ResponseEntity login( String username, String password, String code) {
        log.info("正在进行用户登录");
        log.info("验证码校验");
        //校验验证码是否正确
        if (!shearCaptcha.verify(code)) {
            log.info("验证码不正确");
            return  ResponseEntity.status(HttpStatusForProject.RequestReject).body("验证码出错了");
        }
        log.info("验证码正确");
        //通过用户名与密码创建UsernamePasswordToken
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authenticate = null;
        try {
            //进行用户名与密码校验
            authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
            //校验成功
            log.info("校验成功");
            //获取用户名
            String token_username=(String)authenticate.getPrincipal();
            log.info("生成token中");
            //获取角色
            Collection<? extends GrantedAuthority> authorities = authenticate.getAuthorities();
            //生成token
            String token = createToken(token_username, authorities);
            //将token进行返回
            log.info("token创建完成返回token到客户端");
            return ResponseEntity.ok(token);
        } catch (AuthenticationException e) {
            //登录失败进行处理
            log.info("登录失败");
            log.info("返回错误信息");
            return ResponseEntity.status(HttpStatusForProject.UsernamePasswordFail).body(e.getMessage());
        }
    }

    private String createToken(String username,Collection<? extends GrantedAuthority> authorities){
        StringBuffer temp_roles=new StringBuffer();
        authorities.stream().forEach(item->{
            temp_roles.append(item.getAuthority());
            temp_roles.append(",");
        });
        //替换最后的逗号(生成token角色排版)
        String roles = temp_roles.substring(0, temp_roles.length() - 1);
        //设置token的过期时间
        LocalDateTime date = LocalDateTime.now(); //获取到现在的时间
        LocalDateTime time = date.plusSeconds(expireTime);//设置过期时间(秒)
        Date expireDate = Date.from(time.atZone(ZoneId.systemDefault()).toInstant());
        //设置Token
        Map<String,Object> header=new HashMap<>();
        header.put("alg","HS256");
        header.put("typ","JWT");
        //创建载荷
        Map<String,Object> payload=new HashMap<>();
        payload.put("username",username);
        payload.put("roles",roles);
        //创建好token
        String s = jwtUtils.create(header, payload, signatureAlgorithm, sale, expireDate);
        return s;
    }


    public void setAlg(String alg) {
        this.alg = alg;
    }

    public void setSignatureAlgorithm(SignatureAlgorithm signatureAlgorithm) {
        this.signatureAlgorithm = signatureAlgorithm;
    }

    public void setSale(String sale) {
        this.sale = sale;
    }

    public void setExpireTime(int expireTime) {
        this.expireTime = expireTime;
    }
}
```

## 五、自定义注册

> 1. 首先获取要注册的用户名与密码
> 2. 查询数据库中是否有该账户,如果有就返回错误码,没有就进行插入
> 3. 如果插入成功,就通过用户信息生成Token返回给客户端

```java
@Slf4j
public class RegisterHandler {

    @Autowired
    private UserMessageService userMessageServiceImpl;

    @Autowired
    private UserStatusService userStatusServiceImpl;

    @Autowired
    private UserRoleService userRoleServiceImpl;

    @Autowired
    private RoleService roleServiceImpl;

    @Autowired
    private ShearCaptcha shearCaptcha;

    //JWT工具
    @Autowired
    private JWTUtils jwtUtils;

    //设置加密类型
    private String alg;
    //设置加密算法
    private SignatureAlgorithm signatureAlgorithm;
    //设置盐
    private String sale;
    //设置过期时间
    private int expireTime;


    public ResponseEntity register(String username, String password,String code) {
        if (hasUsername(username)) {
            log.info("数据库中已有该用户");
            log.info("正在返回状态码");
            return ResponseEntity.status(HttpStatusForProject.UserAlreadyExists).body("已有当前用户");
        }
        if (!shearCaptcha.verify(code)) return ResponseEntity.status(HttpStatusForProject.RequestReject).body("验证码错误");
        try {
            insertBath(username,password);
            log.info("添加用户成功");
        }catch (Exception e){
            log.info("存储用户异常,正在进行回滚中.....");
            log.info("回滚成功!");
            log.info("返回错误状态码");
            return  ResponseEntity.status(HttpStatusForProject.AuthenticationFail).body("注册失败请重试");
        }
        log.info("生成token中");
        UserMessage userMessage = userMessageServiceImpl.selectMessageByUsername(username);
        String register_username=userMessage.getUsername();
        List<Role> roleList = roleServiceImpl.selectByUserID(userMessage.getId());
        String token=createToken(register_username,roleList);
        log.info("token生成成功返回到客户端.....");
        return ResponseEntity.ok().body(token);
    }

    public String createToken(String username,List<Role> roleList){
        StringBuffer temp_roles=new StringBuffer();
        roleList.stream().forEach(item->{
            temp_roles.append(item.getRoleName());
            temp_roles.append(",");
        });
        String roles = temp_roles.substring(0, temp_roles.length() - 1);
        //创建token头
        Map<String,Object> header=new HashMap<>();
        header.put("alg",alg);
        header.put("typ","JWT");
        //创建载荷
        Map<String,Object> payload=new HashMap<>();
        payload.put("username",username);
        payload.put("roles",roles);
        //设置token的过期时间
        LocalDateTime date = LocalDateTime.now(); //获取到现在的时间
        LocalDateTime time = date.plusSeconds(expireTime);//设置过期时间(秒)
        Date expireDate = Date.from(time.atZone(ZoneId.systemDefault()).toInstant());
        //创建好token
        String s = jwtUtils.create(header, payload, signatureAlgorithm, sale, expireDate);
        return s;
    }


    public boolean hasUsername(String username) {
        log.info("正在检测用户是否存在");
        QueryWrapper<UserMessage> messageQueryWrapper=new QueryWrapper<>();
        messageQueryWrapper.eq("username",username);
        UserMessage one = userMessageServiceImpl.getOne(messageQueryWrapper);
        return one!=null?true:false;
    }


    @Transactional
    public void insertBath(String username, String password) {
        log.info("正在存入用户");
        //插入当前用户
        userMessageServiceImpl.save(new UserMessage(null, username, password));
        QueryWrapper<UserMessage> userMessageQueryWrapper = new QueryWrapper<>();
        userMessageQueryWrapper.eq("username", username);
        UserMessage one = userMessageServiceImpl.getOne(userMessageQueryWrapper);
        int user_id = one.getId();
        userStatusServiceImpl.save(new UserStatus(user_id, null, null, null, null));
        userRoleServiceImpl.save(new UserRole(user_id, 1));
    }




    public void setAlg(String alg) {
        this.alg = alg;
    }

    public void setSignatureAlgorithm(SignatureAlgorithm signatureAlgorithm) {
        this.signatureAlgorithm = signatureAlgorithm;
    }

    public void setSale(String sale) {
        this.sale = sale;
    }

    public void setExpireTime(int expireTime) {
        this.expireTime = expireTime;
    }
}
```

## 六、自定义token拦截器

> 通过拦截所有的请求对token进行验证,验证成功将token放入SpringSecurity上下文中。

```java
@Slf4j
public class TokenFilter extends AbstractAuthenticationProcessingFilter {

    public TokenFilter() {
        super("/**");
    }

    //JWT工具
    private JWTUtils jwtUtils = new JWTUtils();
    //Token失效处理器
    private TokenAuthencationFail authencationFail = new TokenAuthencationFail();

    //这里获取过滤器执行路径,以及认证管理器
    protected TokenFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
    }

    //重写doFilter方法验证token的合法性
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        log.info("正在进行token校验");
        //获取HttpServlet
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        //从请求头获取token
        String token = request.getHeader("Authentication");
        System.out.println(token);
        log.info("检查token的合法性");
        if (token == null) {
            log.info("token为空,返回错误状态码");
            unsuccessfulAuthentication(request, response, "会话无效");
            return;
        }
        //获取盐
        Keys keys = new Keys();
        //解析token
        Jwt jwt = null;
        try {
            jwt = jwtUtils.ParseToken(token, keys.getSale());
        } catch (JwtException e) {
            //token过期
            if (e instanceof ExpiredJwtException) {
                log.info("token过期");
                unsuccessfulAuthentication(request, response, "token过期");
                return;
            } else if (e instanceof MalformedJwtException) { //token无效
                log.info("token无效");
                unsuccessfulAuthentication(request, response, "token无效");
                return;
            }
        }
        log.info("解析token中......");
        //获取token的用户名以及权限
        Map<String, Object> bodyMap = (Map<String, Object>) jwt.getBody();
        //获取用户名
        String username = (String) bodyMap.get("username");
        //获取权限
        String roles = (String) bodyMap.get("roles");
        String role[] = roles.split(",");
        //装载权限
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        for (String item : role) {
            authorities.add(new SimpleGrantedAuthority(item));
        }
        log.info("token解析完成");
        log.info("用户名:" + username + "权限:" + authorities);
        //创建UsernamePasswordToken
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
        //成功
        successfulAuthentication(request, response, chain, usernamePasswordAuthenticationToken);

        chain.doFilter(req,res);
    }


    //这里主要作为token验证,这个方法暂时用不着
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        return null;
    }


    //验证成功流程,如果成功就将请求放入SpringSecurity上下文中,直接进入下一个过滤器。
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        //设置上下文
        SecurityContextHolder.getContext().setAuthentication(authResult);
        log.info("信息交给上下文");
        //直接进入下一个过滤器
        log.info("通过");
        chain.doFilter(request, response);
    }

    //验证失败流程,如果失败进入TokenAuthencationFail中,向前台抛出状态码。
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, String message) throws IOException, ServletException {
        authencationFail.onAuthenticationFailure(request, response, message);
    }
}
```

## 七、token验证失败处理

> 如果验证失败返回错误码交给客户端

```java
@Slf4j
public class TokenAuthencationFail{

    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, String message) throws IOException {
        log.info("返回错误码到客户端.....");
        response.sendError(HttpStatusForProject.AuthenticationFail,message);
    }
}
```

## 八、SpringSecurity配置文件

```java
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true) //开启权限注解
@EnableWebSecurity //开启SpringSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    //获取自定义用户管理器
    @Autowired
    private UserDetailsService userDetailsManager;
    //获取用户校验处理器
    @Autowired
    private AuthenticationProvider authencationUser;
    //通常这里一般配置忽略请求
    @Override
    public void configure(WebSecurity web) throws Exception {
        //放行资源,这里不走AbstractAuthenticationProcessingFilter
        web.ignoring().antMatchers("/app/login/**","/app/checkCode/**","/getCode/**","/app/register/**");
    }

    //配置请求
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //去掉的配置:
        //路径拦截去除原因:每次请求都要带token,来进行验证。不需要验证的直接使用忽略请求
        //登录去除原因:自定义Controller已经帮我们进行登录验证,以及成功处理错误处理。并且我重写了AbstractAuthenticationProcessingFilter中的doFilter方法。自带的处理器已经失效
        //去掉SpringSecurity的Session原因: 我们的验证由token来进行的。
        //不需要authenticationEntryPoint,我已经对没有登录的用户进行了处理,token校验的时候
        http
                .exceptionHandling()
                .accessDeniedHandler(new LessPermissionsHandler())//配置角色权限处理器
                .and()
                .addFilterBefore(corsFilter(),UsernamePasswordAuthenticationFilter.class) //一定要加过滤跨域,不然token访问不到
                .addFilterAt(new TokenFilter(), UsernamePasswordAuthenticationFilter.class) //设置token过滤器
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)//关闭Spring自带的session
                .and()
                .csrf()
                .disable();//关闭CSRF后续自己配置CSRF校验过滤器
    }

    //由于需要用到SpringSecurity的AuthenticationManager必须在SpringSecurity配置类中进行注入
    //配置额外的参数
    @Bean
    public LoginHandler loginHandler(){
        //准备过期时间
        int expireTime=30;
        //准备盐
        Keys keys=new Keys();
        LoginHandler loginHandler=new LoginHandler();
        //设置加密算法
        loginHandler.setSignatureAlgorithm(SignatureAlgorithm.HS256);
        //设置时间
        loginHandler.setExpireTime(expireTime);
        //设置加密类型
        loginHandler.setAlg("HS256");
        //设置盐
        loginHandler.setSale(keys.getSale());
        try {
            loginHandler.setAuthenticationManager(authenticationManager());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return loginHandler;
    }

    @Bean
    public RegisterHandler registerHandler(){

        RegisterHandler registerHandler=new RegisterHandler();
        //准备过期时间
        int expireTime=30;
        //准备盐
        Keys keys=new Keys();
        //设置盐
        registerHandler.setSale(keys.getSale());
        //设置过期时间
        registerHandler.setExpireTime(expireTime);
        //设置加密类型
        registerHandler.setAlg("HS256");
        //设置加密算法
        registerHandler.setSignatureAlgorithm(SignatureAlgorithm.HS256);
        return registerHandler;
    }

    //配置权限管理器
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //设置UserDetailsService,authenticationProvider,进行用户信息的认证,查看是否合法。
        auth.userDetailsService(userDetailsManager).and().authenticationProvider(authencationUser);
    }

    //配置密钥
    @Bean
    public PasswordEncoder passwordEncoder(){
        return  new BCryptPasswordEncoder();
    }


    @Bean
    public ShearCaptcha shearCaptcha(){
        return CaptchaUtil.createShearCaptcha(300, 120, 4, 4);
    }

    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true); // 允许cookies跨域
        config.addAllowedOrigin("*");// 允许向该服务器提交请求的URI，*表示全部允许。。这里尽量限制来源域，比如http://xxxx:8080 ,以降低安全风险。。
        config.addAllowedHeader("*");// 允许访问的头信息,*表示全部
        config.setMaxAge(18000L);// 预检请求的缓存时间（秒），即在这个时间段里，对于相同的跨域请求不会再预检了
        config.addAllowedMethod("*");// 允许提交请求的方法，*表示全部允许，也可以单独设置GET、PUT等
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

}
```

## 九、具体看源码


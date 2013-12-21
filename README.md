<h1>howsun-javaee-framework<br />
</h1>
<p>Java应用层框架</p>
<p>版本：1.8</p>
<h2>1、项目介绍</h2>
<p>  这是一款居于Spring容器之上特别适用于中小企业应用的JavaEE快速开发框架，具有如下特性：</p>
<p>1、跨服务调用（跨Spring容器，也可以使用类似Netty的通信中间件来实现）</p>
<p>2、封装DAO操作，大大简化了数据库操纵业务，统一的查询参数接口，统一的分页对象，可创建单机可集群环境的数据唯一ID。支持Hibernate，JPA和MongoDB操纵</p>
<p>3、统一配置管理，配置文件不随工程一起发布，可以有效地避免线上线下配置文件混乱问</p>
<p>4、统一日志管理，不需要做太多的日志配置，自动生成日志配置模板，并将日志记录到指定的文件夹中</p>
<p>5、封装了Redis客户端，取代Memcache</p>
<p>6、简化了Json操作</p>
<p>7、提供了分页、工具类封装的JSP标签库</p>
<p>8、大量工具包：如安全、Web、断言、编码等40多种</p>
<p>&nbsp;</p>
<h2>2、引入框架构件  </h2>
<p>Maven引入 </p>
<div align="center">
  <table border="1" cellspacing="0" cellpadding="0">
    <tr>
      <td width="572" valign="top"><br />
        &lt;dependency&gt;<br />
        &lt;groupId&gt;org.howsun&lt;/groupId&gt;<br />
        &lt;artifactId&gt;howsun-jee-framework&lt;/artifactId&gt;<br />
        &lt;version&gt;1.0.8&lt;/version&gt;<br />
        &lt;/dependency&gt; </td>
    </tr>
  </table>
</div>
<p>需要注意，必须保证内部仓库中已经存在构件。 <br />
非Maven工程，请在build目录下载howsun-jee-framework-1.0.8.jar，并手工将其和依赖构件全部加入工程的ClassPath</p>
<p>&nbsp;</p>
<h2>3、定制统一配置的名称空间</a></h2>
<p>步骤： </p>
<ul>
  <li>在工程Source下创建META-INF目录 </li>
  <li>目录中创建howsun-config.properties配置文件 </li>
  <li>文件中只需一行：namespace=空间名 </li>
</ul>
<p>统一这种配置后，框架会在工程所在的磁盘根目录下创建opt/hjf/{namespace}子目录，用于存放配置文件、日志文件。 </p>
<p>&nbsp;</p>
<h2>4、统一日志管理</h2>
<ul>
  <li>框架运行时，第一次会在项目同分区中创建/opt/hjf/${空间名}目录，并自动写一份log4j.properties的配置文件 </li>
  <li>日志级别默认为info</li>
  <li>日志分别往控制台和文件中传送 </li>
  <li>日志内容文件在/opt/hjf/logs/${空间名}.log</li>
  <li>按天切分存储 </li>
</ul>
<p>&nbsp;</p>
<h2>5、统一服务管理</h2>
<p>以实际例子说明应用，假如某项目含有如下服务：</p>
<p>WWW：主站服务，既是个Web工程</p>
<p>CMS：资源管理服务，重点是数据存储和操纵。普通Java工程</p>
<p>SNS：用户管理服务，并含有复杂社交关系应用。普通Java工程</p>
<p>如果WWW要调用CMS服务中接口，就涉及到跨服务，目前1.8版是跨Spring容器来实现的，具体使用如下。</p>
<p>&nbsp;</p>
<h3>(1)、配置</h3>
<p>步骤： </p>
<ol>
  <li>新建一普通Java工程，例如howsun-sns；</li>
  <li>设定howsun-config.properties中的namespace=howsun_sns</li>
  <li>在/opt/hjf/${空间名}目录下手工创建一份Spring配置文件，文件名为${空间名}-sc.xml；即/opt/hjf/howsun_sns/sns-sc.xml</li>
  <li>要将cms服务中接口纳入到框架统一服务中管理，需要在接口和实现类上标注：@ContractService，其中实现类上的注解需要指定bean的id</li>
</ol>
<p>示例：</p>
<pre>
<p>@ContractService<br />
public interface PersonService{</p>
<p>}</p>
<p>@ContractService(&quot;personService&quot;)<br />
public class PersonServiceImpl implements PersonService{</p>
<p>}</p>
</pre>
<h3>(2)、跨服务调用</h3>
<p>现在www服务需要调用sns服务的PersonService，步骤如下：</p>
<ol>
  <li>新建一Java Web工程，例如howsun-www；</li>
  <li>设定howsun-config.properties中的namespace=howsun_www</li>
  <li>在/opt/hjf/${空间名}目录下手工创建一份Spring配置文件，文件名为${空间名}-sc.xml；即/opt/hjf/howsun_www/www-sc.xml</li>
  <li>在配置文件中装配ContractServices Bean并注入modules属性，如：&lt;entry key=&quot;sns&quot;  value=&quot;howsun_sns&quot;/&gt;</li>
  <li>在Controller类中注入ContractServices，ContractServices来调用PersonService接口</li>
</ol>
<p>示例：</p>

<pre>
//配置文件
&lt;bean id=&quot;contractServices&quot; class=&quot;org.howsun.core.ContractServices&quot; &gt;<br />	&lt;property name=&quot;modules&quot;&gt;<br />		&lt;map key-type=&quot;java.lang.String&quot; value-type=&quot;java.lang.String&quot;&gt;<br />			&lt;entry key=&quot;sns&quot;  value=&quot;howsun_sns&quot;/&gt;<br />		&lt;/map&gt;<br />	&lt;/property&gt;<br />&lt;/bean&gt;


//Java文件<p>@Controller<br />
public class HomeController{</p>
<p>&nbsp;&nbsp;	&nbsp;&nbsp; @Resource<br />
&nbsp;&nbsp; &nbsp;&nbsp; private ContractServices contractServices;</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; @RequestMapping(&quot;/{nickname}/index&quot;)<br />
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; public String index(@PathVariable String nickname, Model model){</p><p>&nbsp;&nbsp;	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;PersonService personService = contractServices.getContractService(PersonService.class);</p><p>&nbsp;&nbsp;	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;model.addAttribute(&quot;person&quot;,personService.getPerson(nickname));<br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; }
<br/>}</p>
</pre>
<p>&nbsp;</p>
<h2>6、统一数据库操纵</a></h2>
<ul>
  <li>框架中封装了三种持久层框架，分别是Hibernate、JPA、Mongo4J。其中前两者是针对RDBMS，后者是针对NoSQL MongoDB</li>
  <li>使用时只需在配置中增加id为genericDao的bean，实现类可以为上述三种的任意一种 </li>
  <li>实现类为Hibernate、JPA时，需要注入相应的Session工厂和数据源；为Mongo4j时，需要MongoDB连接池。这些都可以在各自的软件厂商找到使用说明 </li>
</ul>
<h3>6.1、重点数据操纵方法</h3>
<h3>6.1.1、事务方法</h3>
<p>■ 保存实体 <br />
  void save(Object object) </p>
<p>■ 更新对象 <br />
  void update(Object object)</p>
<p>■ 根据条件更新记录 <br />
  public &lt;T&gt; int update(Class&lt;T&gt; entityName, String[] fields,  Object[] values, Serializable id)<br />
  返回更新的结果数 </p>
<p>■ 批量更新记录 <br />
  public &lt;T&gt; int updateByBatch(Class&lt;T&gt; entityName, String  fields, String condition, Object[] values)<br />
  返回批量更新的记录个数 </p>
<p>■ 根据主键值删除记录 <br />
  public &lt;T&gt; int delete(Class&lt;T&gt; entityClass, Serializable...  entityids)<br />
  返回删除的记录个数 </p>
<p>■ 删除一个已知的记录 <br />
  public int delete(Object object)<br />
  返回删除的记录个数 </p>
<p>■ 根据条件和参数删除记录 <br />
  public &lt;T&gt; int delete(Class&lt;T&gt; entityClass, String  condition, Object[] params)<br />
  返回删除的记录个数 </p>
<p>注意： </p>
<ul>
  <li>这些方法都需要事务，Service层标上@Transactional</li>
  <li>MongoDB目前还不支持事务 </li>
</ul>
<p>&nbsp;</p>
<h3>6.1.2、非事务方法</h3>
<p>■ 根据主键值查询一条记录 <br />
  public &lt;T&gt; T find(Class&lt;T&gt; entityClass, Serializable  entityid)<br />
返回与实体名一致的单个实体对象 </p>
<p>■ 根据条件查询多条记录，可以设分页、排序 <br />
  public &lt;T&gt; List&lt;T&gt; finds(Class&lt;T&gt; entityClass, String  fields, Page page,String condition, Object[] params, OrderBean order)<br />
  返回与实体名一致的多个实体集合对象 </p>
<p>■ 统计某表中的总记录数 <br />
  &lt;T&gt; long getCount(Class&lt;T&gt; entityClass);<br />
  返回总记录数 </p>
<p>■ 根据条件统计某表中的总记录数 <br />
  &lt;T&gt; long getCount(Class&lt;T&gt; entityClass, String condition, Object[]  params);<br />
  返回记录数 </p>
<p>■ 模拟自增长主键，不支持同步锁，适合数据频繁低的应用中 <br />
  Long nextId(Class&lt;?&gt; entityClass);<br />
  返回下一个主键值 </p>
<p>注意： <br />
  这些方法都不需要事务，Service层标上@Transactional，还需要调用方法上加上@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)以提高性能 </p>
<p>&nbsp;</p>
<h3>6.2、数据分页</h3>
<ul>
  <li>构建一个Page对象：new Page(当前页码，每页大小，页码上的URL地址)</li>
  <li>将对象送到分页查询方法中去 </li>
  <li>方法执行完后，将对象放在HttpServletRequest作用域中，Key为Page. SCOPE_NAME</li>
  <li>JSP页面上引入框架的标签库： </li>
</ul>
<div align="center">
  <table border="1" cellspacing="0" cellpadding="0">
    <tr>
      <td width="562" valign="top"><p>&lt;%@taglib    uri=&quot;http://www.howsun.org/tags/page&quot;    prefix=&quot;howsun&quot;%&gt; </p></td>
    </tr>
  </table>
</div>
<ul>
  <li>JSP页面上利用分页标签显示分页结果： </li>
</ul>
<div align="center">
  <table border="1" cellspacing="0" cellpadding="0">
    <tr>
      <td width="563" valign="top"><p>    &lt;howsun:pagination/&gt; </p></td>
    </tr>
  </table>
</div>
<ul>
  <li>效果示例： </li>
</ul>
<p> <br />
</p>
<h3>6.3、创建主键</h3>
<ul>
  <li>支持大规模数据库集群环境中无重复主键 </li>
  <li>主键类型：java.lang.Long，因此表中预先要设计为bigint型主键类型 </li>
</ul>
<div align="center">
  <table border="1" cellspacing="0" cellpadding="0">
    <tr>
      <td width="563" valign="top"><p>org.howsun.dao.IDGenerator    .getUniqueID () </p></td>
    </tr>
  </table>
</div>
<p>&nbsp;</p>
<h2>7、Redis缓存客户端</h2>
<p>配置文件：</p>
<pre>
<p>
&lt;bean id=&quot;server1&quot; class=&quot;org.howsun.redis.ShardedServer&quot; &gt;<br />
	&lt;constructor-arg index=&quot;0&quot; value=&quot;IP地址&quot; /&gt;<br />
	&lt;constructor-arg index=&quot;1&quot; value=&quot;6379&quot;/&gt;<br />
	&lt;constructor-arg index=&quot;2&quot; value=&quot;local&quot;/&gt;<br />
	&lt;property name=&quot;password&quot; value=&quot;密码&quot;/&gt;<br />
	&lt;property name=&quot;timeout&quot; value=&quot;1000&quot;/&gt;&lt;!-- 小于connectionFactoryConfig.maxWait --&gt;<br />
&lt;/bean&gt;<br />
&lt;bean id=&quot;cacheFactoryConfig&quot; class=&quot;org.howsun.redis.CacheFactoryConfig&quot; &gt;<br />
	&lt;property name=&quot;maxActive&quot; value=&quot;5&quot; /&gt;<br />
	&lt;property name=&quot;maxIdle&quot; value=&quot;10&quot; /&gt;<br />
	&lt;property name=&quot;minIdle&quot; value=&quot;3&quot; /&gt;<br />
	&lt;property name=&quot;maxWait&quot; value=&quot;1200&quot; /&gt;&lt;!--1.2秒之内必须返回--&gt;<br />
	&lt;property name=&quot;whenExhaustedAction&quot; value=&quot;1&quot; /&gt;<br />
	&lt;property name=&quot;testOnBorrow&quot; value=&quot;false&quot; /&gt;<br />
	&lt;property name=&quot;testOnReturn&quot; value=&quot;false&quot; /&gt;<br />
	&lt;property name=&quot;testWhileIdle&quot; value=&quot;true&quot; /&gt;<br />
	&lt;property name=&quot;timeBetweenEvictionRunsMillis&quot; value=&quot;30000&quot; /&gt;<br />
	&lt;property name=&quot;numTestsPerEvictionRun&quot; value=&quot;-1&quot; /&gt;<br />
	&lt;property name=&quot;minEvictableIdleTimeMillis&quot; value=&quot;60000&quot; /&gt;<br />
	&lt;property name=&quot;softMinEvictableIdleTimeMillis&quot; value=&quot;-1&quot; /&gt;<br />
	&lt;property name=&quot;servers&quot;&gt;<br />
		&lt;list&gt;<br />
			&lt;ref bean=&quot;server1&quot; /&gt;<br />
		&lt;/list&gt;<br />
	&lt;/property&gt;<br />
&lt;/bean&gt;<br />
&lt;bean id=&quot;cacheFactory&quot; class=&quot;org.howsun.redis.ShardedCacheFactory&quot; &gt;<br />
	&lt;constructor-arg index=&quot;0&quot; ref=&quot;cacheFactoryConfig&quot; /&gt;<br />
&lt;/bean&gt;<br />
&lt;bean id=&quot;cacheService&quot; class=&quot;org.howsun.redis.JedisCacheService&quot; destroy-method=&quot;destroy&quot;&gt;<br />
	&lt;property name=&quot;cacheFactory&quot; ref=&quot;cacheFactory&quot; /&gt;<br />
	&lt;property name=&quot;serializer&quot; &gt;<br />
		&lt;bean class=&quot;org.springframework.data.redis.serializer.JdkSerializationRedisSerializer&quot;/&gt;<br />
	&lt;/property&gt;<br />
	&lt;property name=&quot;report&quot; value=&quot;false&quot; /&gt;<br />
	&lt;property name=&quot;callback&quot;&gt;<br />
		&lt;bean class=&quot;com.chinaot.core.cache.CacheHelper&quot;/&gt;<br />
	&lt;/property&gt;<br />
&lt;/bean&gt;<br />//注意这里做了个CacheHelper静态工具类，以所有要访问缓存的地方直接CacheHelper.get(xxx)方法即可</p></pre>
<p>&nbsp;</p>
<h2>8、工具包</h2>
<p>■安全工具 <br />
  org.howsun.util.security.Codings<br />
org.howsun.util.security.AES</p>
<p>■字符串工具 <br />
  org.howsun.util.Strings</p>
<p>■日期工具 <br />
  org.howsun.util.Dates</p>
<p>■断言工具 <br />
  org.howsun.util. Asserts</p>
<p>■JavaBean工具 <br />
  org.howsun.util. Beans</p>
<p>■中文拼音工具 <br />
  org.howsun.util.ChinesePinyin</p>
<p>■文件工具 <br />
  org.howsun.util. Files</p>
<p>■文件流工具 <br />
  org.howsun.util.Streams</p>
<p>■IO工具 <br />
  org.howsun.util.IOs</p>
<p>■数值工具 <br />
  org.howsun.util.Numbers</p>
<p>■随机数工具 <br />
  org.howsun.util.Randoms</p>
<p>■IP工具 <br />
  org.howsun.util.Ips</p>
<p>■Web工具 <br />
  org.howsun.util.Webs</p>
<p>其它未列的请查看JavaDoc说明 </p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>作者：张纪豪(zhangjihao@sohu.com)</p>
# EXA 基础项目

## 模块

- 基础包（common)

  > 包含基础工具类、通用类型等

- 数据库模型包（domain）

  > 包含数据库对应的表模型类型（Bean）和数据查询结果类型（Dto）

- 数据库操作包（dao）

  > 包含数据库操作类（Dao）和MyBatis的Mapper文件以及查询对象（QO）

- 业务操作包（service）

  > 包含业务操作类型（Service），处理业务逻辑过程，连接数据库和输出层

- 视图交互包（可多项，如管理端、用户端、移动端等）（controller）

  > 处理数据的输入和输出，与用户直接进行数据交互

## 技术栈

- SpringBoot：[首页](https://spring.io/projects/spring-boot) [文档](https://docs.spring.io/spring-boot/docs/2.1.3.RELEASE/reference/htmlsingle/) [API](https://docs.spring.io/spring-boot/docs/2.1.3.RELEASE/api/)
- SpringFramework：[首页](https://spring.io/projects/spring-framework) [文档](https://docs.spring.io/spring/docs/5.1.5.RELEASE/spring-framework-reference/) [API](https://docs.spring.io/spring/docs/5.1.5.RELEASE/javadoc-api/)
- SpringSecurity：[首页](https://spring.io/projects/spring-security) [文档](https://docs.spring.io/spring-security/site/docs/5.1.4.RELEASE/reference/htmlsingle/) [API](https://docs.spring.io/spring-security/site/docs/5.1.4.RELEASE/api/)
- SpringSession：[首页](https://spring.io/projects/spring-session) [文档](https://docs.spring.io/spring-session/docs/2.1.2.RELEASE/reference/html5/) [API](https://docs.spring.io/spring-session/docs/2.1.2.RELEASE/api/)
- MyBatis：[文档](http://www.mybatis.org/mybatis-3/zh/index.html)
- Redis：[官网](https://redis.io) [命令](https://redis.io/commands) [Windows版本](https://github.com/MicrosoftArchive/redis)
- MySQL：[官网](https://www.mysql.com) [文档](https://dev.mysql.com/doc/)
- Maven：[官网](http://maven.apache.org) [仓库](https://search.maven.org/#browse)

- [Jackson](https://github.com/FasterXML/jackson)
- [Fastjson](https://github.com/alibaba/fastjson)：A fast JSON parser/generator for Java. 
- [Orika](http://orika-mapper.github.io/orika-docs/intro.html)：一个简单、快速的JavaBean拷贝框架，它能够递归地将数据从一个JavaBean复制到另一个JavaBean

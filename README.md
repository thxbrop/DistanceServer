## Distance 后端程序（Server）

该项目的是《初学者入门后的进阶》系列的一部分内容
> Distance系列项目立志于快捷搭建私有IM系统

### 项目技术栈
  - Kotlin Coroutine
  - Servlet
  - Ktorm
  - WebSocket-Server
### 初学者应该预先了解的知识
  - Java 8
  - Kotlin
  - Json
  - Java Web 基础

### Features
- * [x] 注册登录
- * [ ] 基础会话
- * [ ] 创建会话
- * [ ] 账户头像
- * [x] 账户更新

### 项目结构
- main
  - kotlin.com.unltm.distancetomcat
    - db
      - base
        - ~~*BaseStorage.kt*~~
        - Dao.kt
      - ~~*UserStorage.kt*~~
      - ~~*UserRichStorage.kt*~~
    - entity
      - *User.kt*
      - *UserRich.kt*
      - *Conversation.kt*
      - *Message.kt*
    - ktorm
      - table
        - *UserTable.kt*
        - *UserRichTable.kt*
      - *KtDatabase.kt*
      - *UserDatabase.kt*
      - *UserRichDatabase.kt*
    - repository
      - *BaseRepository.kt*
      - *UserRepository.kt*
      - *UserRichRepository.kt*
      - *ConversationRepository.kt*
    - servlet
      - account
        - *InfoServlet.kt*
        - *UpdateServlet.kt*
      - auth
        - *LoginServlet.kt*
        - *SignServlet.kt*
      - conversation
        - *ConversationServlet.kt*
        - *CreateConversationServlet.kt*
      - upload
        - *ImageServlet.kt*
      - *Contracts.kt*
      - *RequestResult.kt*
    - *Configure.kt*
    - *Contracts.kt*
    - *HelloServlet.kt*
    - *ServletException.kt*
  - resources
  - webapp
    - avatars
    - file
    - WEB-INF
    - *index.jsp*
> storage已被废弃，使用ktorm替换之，并且他可能会在后续项目中版本移除

点击[这里](https://github.com/thxbrop/DistanceTomcat)获取该项目最新源代码

点击[这里](https://github.com/thxbrop/Distance)获取此项目对应的原生Android开源项目

点击[这里]()获取此项目对应的MySQL数据库开源项目

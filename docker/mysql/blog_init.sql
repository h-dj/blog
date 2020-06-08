--
-- Table structure for table `t_article`
--

DROP TABLE IF EXISTS `t_article`;
CREATE TABLE `t_article` (
  `id` bigint NOT NULL COMMENT '文章ID',
  `title` varchar(50)  NOT NULL COMMENT '文章标题',
  `description` varchar(512)  DEFAULT NULL COMMENT '概要',
  `content` text  NOT NULL COMMENT '正文（markdown）',
  `tags` varchar(255)  DEFAULT NULL COMMENT '标签',
  `author_id` bigint DEFAULT NULL COMMENT '作者',
  `allow_comment` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否允许评论：0：不允许，1允许',
  `author_name` varchar(50)  DEFAULT NULL COMMENT '作者名称',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `status` int NOT NULL DEFAULT '0' COMMENT '状态，0：草稿，1：发布，2：删除',
  `type` int NOT NULL DEFAULT '0' COMMENT '0:普通文章，1：简历，2关于页',
  `publish_time` datetime DEFAULT NULL COMMENT '发布时间',
  `like_num` int NOT NULL DEFAULT '0' COMMENT '点赞数',
  `comment_num` int DEFAULT '0' COMMENT '评论数',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `read_num` int NOT NULL DEFAULT '0' COMMENT '阅读量',
  `recommend` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否推荐',
  `allow_feed` tinyint(1) DEFAULT '1' COMMENT '是否允许订阅：0不允许，不允许',
  `category_id` bigint NOT NULL DEFAULT '0' COMMENT '分类id',
  `top` tinyint(1) NOT NULL DEFAULT '0' COMMENT '置顶',
  `cover` varchar(128)  DEFAULT NULL COMMENT '封面',
  `slug` varchar(19)  NOT NULL COMMENT '文章自定义路径',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `t_article_un_slug` (`slug`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='文章信息表';


--
-- Table structure for table `t_attachment`
--

DROP TABLE IF EXISTS `t_attachment`;

CREATE TABLE `t_attachment` (
  `id` bigint NOT NULL,
  `file_name` varchar(255)  NOT NULL COMMENT '文件名称',
  `file_suffix` varchar(255)  NOT NULL COMMENT '文件后缀',
  `type` int NOT NULL COMMENT '文件存放类型(0:本地，1:七牛云等oss)',
  `file_size` varchar(100)  NOT NULL,
  `create_by` bigint NOT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `path` varchar(512)  DEFAULT NULL COMMENT '文件访问路径',
  `url` varchar(512)  NOT NULL COMMENT '附件链接',
  `width` bigint DEFAULT NULL COMMENT '宽',
  `height` bigint DEFAULT NULL COMMENT '高',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='附件表';


--
-- Table structure for table `t_category`
--

DROP TABLE IF EXISTS `t_category`;

CREATE TABLE `t_category` (
  `id` bigint NOT NULL COMMENT 'id',
  `category_name` varchar(25)  NOT NULL COMMENT '分类名称',
  `create_time` datetime DEFAULT NULL COMMENT '创建日期',
  `description` varchar(512)  DEFAULT NULL COMMENT '描述',
  `update_time` datetime DEFAULT NULL COMMENT '修改日期',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='分类表';


--
-- Table structure for table `t_comment`
--

DROP TABLE IF EXISTS `t_comment`;

CREATE TABLE `t_comment` (
  `id` bigint NOT NULL,
  `article_id` bigint NOT NULL COMMENT '文章id',
  `parent_id` bigint DEFAULT NULL COMMENT '父评论',
  `content` varchar(1023)  NOT NULL COMMENT '评论内容',
  `author` varchar(255)  NOT NULL COMMENT '评论人的昵称',
  `email` varchar(255)  NOT NULL COMMENT '评论人的邮箱(不公开)',
  `author_url` varchar(255)  DEFAULT NULL COMMENT '评论人的网站',
  `like_count` bigint NOT NULL DEFAULT '0' COMMENT '点赞数',
  `user_agent` varchar(512)  DEFAULT NULL,
  `ip_address` varchar(127)  DEFAULT NULL COMMENT 'ip 地址',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='评论表';


--
-- Table structure for table `t_friend_link`
--

DROP TABLE IF EXISTS `t_friend_link`;

CREATE TABLE `t_friend_link` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  `status` int NOT NULL DEFAULT '0' COMMENT ':012',
  `remark` varchar(100) DEFAULT NULL,
  `url` varchar(256) NOT NULL,
  `email` varchar(64) NOT NULL,
  `title` varchar(100) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;


--
-- Table structure for table `t_log`
--

DROP TABLE IF EXISTS `t_log`;

CREATE TABLE `t_log` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `title` varchar(120)  DEFAULT NULL COMMENT '操作标题',
  `method` varchar(6)  DEFAULT NULL COMMENT '操作方法',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `time` bigint DEFAULT NULL COMMENT '执行时间',
  `params` text  COMMENT '请求参数',
  `ip` varchar(16)  DEFAULT NULL COMMENT 'ip',
  `url` varchar(100)  DEFAULT NULL COMMENT '请求url',
  `ip_address` varchar(25)  DEFAULT NULL COMMENT 'ip地址',
  `create_time` datetime DEFAULT NULL COMMENT '操作时间',
  `exception` text  COMMENT '异常',
  `user_agent` varchar(255)  DEFAULT NULL COMMENT '用户代理',
  `level` varchar(100)  DEFAULT NULL COMMENT '等级',
  `browser` varchar(20)  DEFAULT NULL COMMENT '浏览器',
  `os` varchar(20)  DEFAULT NULL COMMENT '操作系统',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1215270143660378778 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='系统日志';


--
-- Table structure for table `t_menu`
--

DROP TABLE IF EXISTS `t_menu`;

CREATE TABLE `t_menu` (
  `id` bigint NOT NULL COMMENT '资源ID',
  `menu_name` varchar(20)  NOT NULL COMMENT '资源名称',
  `type` int NOT NULL DEFAULT '0' COMMENT '菜单类型：0目录，1菜单，2按钮',
  `parent_id` bigint NOT NULL DEFAULT '0' COMMENT 'parentId',
  `parent_name` varchar(50)  DEFAULT NULL COMMENT '父菜单名称',
  `sort` tinyint DEFAULT '0' COMMENT '排序',
  `remark` varchar(255)  DEFAULT NULL COMMENT '备注',
  `permission` varchar(255)  DEFAULT NULL COMMENT '权限标识',
  `url` varchar(100)  DEFAULT NULL COMMENT '链接',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '是否删除',
  `icon` varchar(20)  DEFAULT NULL COMMENT '菜单图标',
  `component` varchar(100)  NOT NULL COMMENT '路由组件',
  `hidden` tinyint NOT NULL DEFAULT '0' COMMENT '是否隐藏',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='系统菜单';


--
-- Table structure for table `t_role`
--

DROP TABLE IF EXISTS `t_role`;

CREATE TABLE `t_role` (
  `id` bigint NOT NULL COMMENT 'ID',
  `role_code` varchar(10)  NOT NULL COMMENT '角色名称',
  `role_name` varchar(50)  DEFAULT NULL COMMENT '描述',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `create_by` bigint DEFAULT NULL COMMENT '创建人',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除',
  `remark` varchar(100)  DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='系统角色';


--
-- Table structure for table `t_role_menu`
--

DROP TABLE IF EXISTS `t_role_menu`;

CREATE TABLE `t_role_menu` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `role_id` bigint NOT NULL COMMENT '角色ID',
  `menu_id` bigint NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=68 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='角色菜单表';


--
-- Table structure for table `t_tag`
--

DROP TABLE IF EXISTS `t_tag`;

CREATE TABLE `t_tag` (
  `id` bigint NOT NULL,
  `tag_name` varchar(255)  NOT NULL COMMENT '标签名称',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='标签表';


--
-- Table structure for table `t_tag_article`
--

DROP TABLE IF EXISTS `t_tag_article`;

CREATE TABLE `t_tag_article` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `tag_id` bigint NOT NULL COMMENT '标签id',
  `article_id` bigint NOT NULL COMMENT '文章id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=76 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='文章标签表';


--
-- Table structure for table `t_user`
--

DROP TABLE IF EXISTS `t_user`;

CREATE TABLE `t_user` (
  `id` bigint NOT NULL COMMENT '用户ID',
  `user_name` varchar(100)  NOT NULL COMMENT '用户名称',
  `email` varchar(100)  NOT NULL COMMENT '邮箱',
  `password` varchar(100)  NOT NULL COMMENT '密码',
  `salt` varchar(64)  NOT NULL COMMENT '加密盐',
  `avatar` varchar(255)  DEFAULT NULL COMMENT '头像',
  `enable` tinyint(1) NOT NULL DEFAULT '1' COMMENT '状态：1：正常，0：禁用',
  `remark` varchar(255)  DEFAULT NULL COMMENT '备注',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '是否删除',
  `is_locked` tinyint(1) DEFAULT '0' COMMENT '是否锁定',
  `login_time` datetime DEFAULT NULL COMMENT '登陆时间',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='系统用户';


--
-- Table structure for table `t_user_role`
--

DROP TABLE IF EXISTS `t_user_role`;
CREATE TABLE `t_user_role` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `role_id` bigint NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='用户角色';




-- 初始化数据

INSERT INTO `t_role` VALUES (1256542256089796610,'Admin','管理员','2020-05-02 19:13:14','2020-05-02 19:13:14',0,0,''),(1256542445198381058,'Guest','游客','2020-05-02 19:13:59','2020-05-02 20:57:13',0,0,'');

INSERT INTO `t_category` VALUES (0,'其他','2020-01-09 21:36:53',NULL,'2020-01-09 21:36:53'),(1215262901141749762,'后台','2020-01-09 21:23:49','','2020-01-09 21:23:49'),(1215262935740563457,'前端','2020-01-09 21:23:57','','2020-01-09 21:23:57'),(1215262961250320386,'Linux','2020-01-09 21:24:03','','2020-01-09 21:24:03'),(1215263075968729089,'框架','2020-01-09 21:24:30','','2020-01-09 21:24:30'),(1215266189052461057,'关于本站','2020-01-09 21:36:53','','2020-01-09 21:36:53');

INSERT INTO `t_tag` VALUES (1215290346028900353,'关于本站','2020-01-09 23:12:52','2020-01-09 23:12:52'),(1215290346041483265,'关于博主','2020-01-09 23:12:52','2020-01-09 23:12:52');

INSERT INTO `t_tag_article` VALUES (50,1215290346028900353,1215290345965985793),(51,1215290346041483265,1215290345965985793),(54,1215290346041483265,1257195405330874368),(75,1215290346028900353,1262989998605467648);

INSERT INTO `t_menu` VALUES (1190952764063309825,'系统管理',0,0,'根目录',1,'','','','2019-11-03 19:24:00','2020-01-05 21:09:02',0,'sys','layout/index',0),(1190953725242597378,'文章管理',0,0,'根目录',0,'','','','2019-11-03 19:27:50','2020-06-06 21:43:46',0,'article','layout/index',0),(1190953983393619969,'系统监控',0,0,'根目录',2,'','','','2019-11-03 19:28:51','2020-01-05 21:10:33',0,'Monitor','layout/index',0),(1190954172489621505,'系统配置',0,0,'根目录',3,'','','','2019-11-03 19:29:36','2019-11-03 19:29:36',1,'example','layout/index',0),(1190954238612824065,'系统工具',0,0,'根目录',99,'','','','2019-11-03 19:29:52','2020-01-05 21:11:01',1,'tool','layout/index',0),(1190955569503232002,'菜单管理',1,1190952764063309825,'根目录',1,'','sys:menu','/sys/menu','2019-11-03 19:35:09','2020-06-06 21:36:47',0,'menu','views/sys/menu/index',0),(1190956260263157762,'角色管理',1,1190952764063309825,'根目录',2,'','sys:role','/sys/role','2019-11-03 19:37:54','2020-06-06 21:37:04',0,'role','views/sys/role/index',0),(1190956520771379201,'用户管理',1,1190952764063309825,'根目录',0,'','sys:user','/sys/user','2019-11-03 19:38:56','2020-06-06 21:38:37',0,'user','views/sys/user/index',0),(1190956782248484866,'个人中心',1,1190952764063309825,'根目录',99,'','sys:user:profile','/profile','2019-11-03 19:39:58','2020-06-06 21:39:16',0,'user','views/sys/profile/index',0),(1190961402907701250,'菜单搜索',2,1190955569503232002,'根目录',99,'','sys:menu:search',NULL,'2019-11-03 19:58:20','2020-01-10 21:24:51',0,'search','layout/index',1),(1190963023809081346,'添加菜单',2,1190955569503232002,'根目录',99,'','sys:menu:add',NULL,'2019-11-03 20:04:47','2020-01-10 21:24:58',0,'add','layout/index',1),(1190963114494128130,'编辑菜单',2,1190955569503232002,'根目录',99,'','sys:menu:edit',NULL,'2019-11-03 20:05:08','2020-01-10 21:25:13',0,'edit','layout/index',1),(1190963313421578241,'菜单详情',2,1190955569503232002,'根目录',99,'','sys:menu:info',NULL,'2019-11-03 20:05:56','2020-01-10 21:25:23',0,'menu','layout/index',1),(1190963700576808962,'文章列表',1,1190953725242597378,'根目录',99,'','article','/articles','2019-11-03 20:07:28','2020-06-06 21:44:32',0,'article','views/article/article-list',0),(1190964240761221122,'创建文章',1,1190953725242597378,'根目录',99,'','admin:article:add','/article/create','2019-11-03 20:09:37','2020-01-06 20:52:40',0,'create','views/article/article-create',0),(1190964656332861442,'文章编辑',2,1190963700576808962,'根目录',99,'','article:edit','','2019-11-03 20:11:16','2020-06-06 21:57:51',0,'editor','',1),(1190965214737330177,'文章分类',1,1190953725242597378,'根目录',99,'','admin:article:category','/article/category','2019-11-03 20:13:29','2020-01-09 21:13:33',0,'category','views/article/category',0),(1190965536222343170,'文章标签',1,1190953725242597378,'根目录',99,'','admin:article:tag','/article/tag','2019-11-03 20:14:46','2020-01-09 21:13:42',0,'tag','views/article/tags',0),(1190966241939156993,'文章删除',2,1190963700576808962,'根目录',99,'','article:delete',NULL,'2019-11-03 20:17:34','2020-06-06 21:42:26',0,'delete','layout/index',1),(1190966783444774913,'文章详情',2,1190963700576808962,'根目录',99,'','article:info',NULL,'2019-11-03 20:19:43','2020-01-10 21:27:13',0,'article','layout/index',1),(1190967812462735362,'角色搜索',2,1190956260263157762,'根目录',99,'','sys:role:search',NULL,'2019-11-03 20:23:48','2020-01-10 21:24:07',0,'search','layout/index',1),(1190967915520978946,'添加角色',2,1190956260263157762,'根目录',99,'','sys:role:add',NULL,'2019-11-03 20:24:13','2020-01-10 21:24:14',0,'add','layout/index',1),(1190968025067810817,'角色编辑',2,1190956260263157762,'根目录',99,'','sys:role:edit',NULL,'2019-11-03 20:24:39','2020-01-10 21:24:28',0,'edit','layout/index',1),(1190968129396928514,'角色详情',2,1190956260263157762,'根目录',99,'','sys:role:info',NULL,'2019-11-03 20:25:04','2020-01-10 21:24:37',0,'role','layout/index',1),(1190968410293661697,'用户添加',2,1190956520771379201,'根目录',99,'','sys:user:add',NULL,'2019-11-03 20:26:11','2020-06-06 21:38:49',0,'add','layout/index',1),(1190968508327129090,'用户编辑',2,1190956520771379201,'根目录',99,'','sys:user:edit',NULL,'2019-11-03 20:26:34','2020-06-06 21:38:55',0,'edit','layout/index',1),(1190968587712720897,'用户详情',2,1190956520771379201,'根目录',99,'','sys:user:info',NULL,'2019-11-03 20:26:53','2020-06-06 21:39:02',0,'user','layout/index',1),(1190970158727032834,'SQL监控',1,1190953983393619969,'根目录',99,'','','/druid','2019-11-03 20:33:08','2020-01-05 21:10:53',1,'sql','views/monitor/sql/index',0),(1190970848706818049,'系统日志',1,1190953983393619969,'根目录',99,'','admin:monitor:log','/monitor/log','2019-11-03 20:35:52','2020-01-05 21:10:45',0,'log','views/monitor/log/index',0),(1190971465814765570,'API',1,1190953983393619969,'根目录',99,'','','/swagger-ui.html','2019-11-03 20:38:19','2020-01-05 21:10:40',1,'swagger','views/monitor/api/index',0),(1213806193341001729,'图库',1,1190954238612824065,'根目录',99,'','sys-tool:picture:*','/picture','2020-01-05 20:55:23','2020-01-05 20:56:22',1,'picture','views/sys-tool/picture/index',0),(1215624999269015554,'用户搜索',2,1190956520771379201,'根目录',99,'','sys:user:search',NULL,'2020-01-10 21:22:40','2020-06-06 21:39:09',0,'search','layout/index',1),(1215626296789540865,'文章搜索',2,1190963700576808962,'根目录',99,'','article:search',NULL,'2020-01-10 21:27:49','2020-01-10 21:27:49',0,'search','layout/index',1),(1215626547663446017,'分类添加',2,1190965214737330177,'根目录',99,'','article:category:add',NULL,'2020-01-10 21:28:49','2020-01-10 21:28:49',0,'add','layout/index',1),(1215626696632541186,'分类删除',2,1190965214737330177,'根目录',99,'','article:category:delete',NULL,'2020-01-10 21:29:24','2020-06-06 21:58:18',0,'delete','layout/index',1),(1215626845844905985,'分类编辑',2,1190965214737330177,'根目录',99,'','article:category:edit',NULL,'2020-01-10 21:30:00','2020-06-06 21:58:25',0,'edit','layout/index',1),(1215627034445979649,'分类搜索',2,1190965214737330177,'根目录',99,'','article:category:search',NULL,'2020-01-10 21:30:45','2020-01-10 21:30:45',0,'search','layout/index',1),(1215627152536608769,'标签添加',2,1190965536222343170,'根目录',99,'','article:tag:add',NULL,'2020-01-10 21:31:13','2020-01-10 21:31:13',0,'add','layout/index',1),(1215627251610263554,'标签删除',2,1190965536222343170,'根目录',99,'','article:tag:delete',NULL,'2020-01-10 21:31:37','2020-01-10 21:31:37',0,'delete','layout/index',1),(1215627366181871617,'标签编辑',2,1190965536222343170,'根目录',99,'','article:tag:edit',NULL,'2020-01-10 21:32:04','2020-01-10 21:32:04',0,'edit','layout/index',1),(1215627489569906689,'标签搜索',2,1190965536222343170,'根目录',99,'','article:tag:search',NULL,'2020-01-10 21:32:33','2020-01-10 21:32:33',0,'search','layout/index',1),(1269261854996172801,'菜单删除',2,1190955569503232002,'根目录',99,'','sys:menu:delete',NULL,'2020-06-06 21:36:23','2020-06-06 21:36:23',0,'delete','layout/index',1),(1269262138686312450,'角色删除',2,1190956260263157762,'根目录',99,'','sys:role:delete',NULL,'2020-06-06 21:37:30','2020-06-06 21:37:30',0,'delete','layout/index',1),(1269262362750226433,'用户删除',2,1190956520771379201,'根目录',99,'','sys:user:delete',NULL,'2020-06-06 21:38:24','2020-06-06 21:38:24',0,'delete','layout/index',1),(1269267622323556354,'分类详情',2,1190965214737330177,'根目录',99,'','article:category:info',NULL,'2020-06-06 21:59:18','2020-06-06 21:59:18',0,'form','layout/index',1),(1269267783800066049,'标签详情',2,1190965536222343170,'根目录',99,'','article:tag:info',NULL,'2020-06-06 21:59:56','2020-06-06 21:59:56',0,'form','layout/index',1);

INSERT INTO `t_user` VALUES (0,'admin','admin@qq.com','72383e16826d817cdd8edeabf09a40dca55b45751abde6eb1f09e09c06771ea6','159c424b871b94c2ea2ff206001dcba5','http://192.168.43.122:8181/upload/2/4/e6fea537-facd-4117-ad69-229b454016b3_2020-01-07.jpg',1,'',0,0,'2020-06-06 21:34:50','2019-07-10 10:48:04','2020-06-06 21:34:27');

INSERT INTO `t_article` VALUES (1215290345965985793,'关于本站',' 关于本站  简介这个博客系统是为了将学习的技术整合在一起，巩固基础的练习项目当然也作为本人的个人博客用于记录工作中的问题和学习新技术的笔记  項目的技术选型 SpringBoot MyBatis and MyBatisPlus SpringMVC MySQL.8.0 Shiro 安全框架 Redis 缓存 API 文档 Swagger lombok 消息队列 RabbitMQ 搜索引擎 Elas','### 关于本站\n\n> ? 简介\n\n这个博客系统是为了将学习的技术整合在一起，巩固基础的练习项目。当然也作为本人的个人博客用于记录工作中的问题和学习新技术的笔记。\n\n> ? 項目的技术选型\n\n- Spring-Boot\n- MyBatis and MyBatis-Plus\n- SpringMVC\n- MySQL.8.0\n- Shiro 安全框架\n- Redis 缓存\n- API 文档 Swagger\n- lombok\n- 消息队列 RabbitMQ\n- 搜索引擎 Elasticsearch\n- Docker 化部署\n- 持续集成 Jenkins\n- Markdown 使用Vditor \n\n> TODOList\n\n- [X] 完成框架的搭建\n- [X] Shiro 完成整合\n- [X] Redis 完成整合\n- [X] SwaggerUI 完成整合\n- [X] 配置打包部署\n- [X] 用户管理\n- [X] 角色管理\n- [X] 菜单管理\n- [X] 文章管理\n- [X] 标签管理\n- [X] 分类管理\n- [X] 加入文件上传功能(只实现上传到本地)\n- [ ] ES 搜索文章\n- [ ] 使用 Redis 缓存数据\n- [ ] 文章备份\n- [ ] 安全问题防范\n- [ ] 整理整个搭建文档\n\n> ? 感谢以下项目\n\n[vue-element-admin](https://github.com/PanJiaChen/vue-element-admin)\n[Vditor Markdown](https://hacpai.com/article/1549638745630)\n[Flog 博客模板](https://gitee.com/fengziy/Fblog)\n\n\n> ❤️ 联系\n-  Email: 1432517356@qq.com\n-  Github: \n-  Gitee:\n-  掘金:\n- 语雀:\n','关于本站,关于博主',0,1,'hdj@admin.cn','2020-01-09 23:12:52',1,0,'2020-01-09 23:13:21',1,0,'2020-04-20 23:42:44',34,0,1,1215266189052461057,0,'http://192.168.43.122:8181/upload/1/13/019547be-b4aa-44fa-bb95-df995a6b3951_2020-01-09.jpg','about'),(1255509468804808704,'并发编程之入门',' 并发编程之入门alignleftdisplayinlineheight784nameimage.pngoriginHeight784originWidth1918size153743statusdonestylenonewidth1918)a name\"SHKMP\"a 一、Java 并发的发展进程a name\"ziHA2\"a 1. 在JDK 1.x release的早期版本，就确立了 Java ','# 并发编程之入门\n\n\n![image.png](https://cdn.nlark.com/yuque/0/2019/png/438760/1574673418930-403c4991-e027-42de-8665-e35f5527a428.png#align=left&display=inline&height=784&name=image.png&originHeight=784&originWidth=1918&size=153743&status=done&style=none&width=1918)\n<a name=\"SHKMP\"></a>\n### 一、Java 并发的发展进程\n<a name=\"ziHA2\"></a>\n#### 1. 在JDK 1.x release的早期版本，就确立了 Java 最基础的线程模型\n> 具有代表性的类和接口有：\n\n- java.lang.Thread\n- java.lang.ThreadGroup\n- java.lang.Runnable\n- java.lang.Process\n- java.lang.ThreadDeath\n> 异常类\n\n- java.lang.IllegalMonitorStateException\n- java.lang.IllegalStateException\n- java.lang.IllegalThreadStateException.\n<a name=\"erhQt\"></a>\n#### 2. JDK5发布了许多特性功能: 泛型 Generic、枚举类型 Enumeration、可变参数varargs、注解 Annotations等等\n\n- 在并发方面：提供了java.util.concurrent(并发包)\n  - 原子(Atomic)类型\n  - 显式锁(Lock)接口\n  - 计数器(CountDownLatch)\n  - 回环栅栏(CyclicBarrier)\n  - 信号量(Semaphore)\n  - 并发集合(concurrent collections)\n  - Callable和Future接口\n  - 执行器(Executor接口)\n\n\n\n<a name=\"naKCN\"></a>\n#### 3. JDK7 \n\n- 添加ForkJoinPool(工作窃取技术)框架的支持\n- 添加Phaser类，是可重用的同步屏障，类似于CountDownLatch和CyclicBarrier\n- TransferQueue　队列\n<a name=\"rj2Ol\"></a>\n#### 4. JDK8 \n\n- 加法器(Adder)和累加器(Accumulator)：原子类型的扩充与优化，主要有：LongAdder、LongAccumulator、DoubleAdder和DoubleAccumulator，比AtomicLong和AtomicDouble性能更优。\n- CompletableFuture：JDK5中Future的增强版。\n- StampedLock：JDK5中ReadWriteLock的改进版。\n\n<a name=\"GbP6N\"></a>\n### 二、了解相关概念\n<a name=\"lSZNR\"></a>\n#### 1. 线程与进程的区别?\n\n- 进程（process）和线程（thread）是操作系统的基本概念\n- 现代操作系统调度的最小单元是线程，也叫轻量级进程（Light　Weight Process）\n- 线程都拥有各自的计数器、堆栈和局部变量等属性，并且能够访问共享的内存变量\n- 一个进程可以包含多个线程\n\n<a name=\"ePvBY\"></a>\n#### 2. 并发(concurrency)和并行(parallelism)的区别？\n![image.png](https://cdn.nlark.com/yuque/0/2019/png/438760/1574667043211-359c5014-eee3-47cf-908d-adc65ee34758.png#align=left&display=inline&height=365&name=image.png&originHeight=365&originWidth=409&size=14247&status=done&style=none&width=409)\n\n- 并发是两个任务在重叠的时间(交替)内开始，运行和完成；　而并行是两个任务能在多核CPU 下，同一时间开始，运行和完成\n- 并发是马上处理许多任务，而并行是马上开始执行多个任务\n\n\n<a name=\"3RDb6\"></a>\n### 三、了解CAS算法\n<a name=\"BnwdW\"></a>\n#### 1. CAS 涉及的三个操作数\n\n- 需要读写的内存位置V\n- 需要进行比较的预期值A\n- 需要写入的新值U\n<a name=\"NzNsL\"></a>\n#### 2. 图示\n![image.png](https://cdn.nlark.com/yuque/0/2019/png/438760/1574667803094-76e87687-f426-4857-b780-9018d9136c4c.png#align=left&display=inline&height=222&name=image.png&originHeight=404&originWidth=770&size=56946&status=done&style=none&width=423)\n\n<a name=\"wiRbE\"></a>\n#### 3. CAS引发的问题\n\n- **ABA问题**，一个线程将内存值从A改为B，另一个线程又从B改回到A\n  - 图示\n\n![image.png](https://cdn.nlark.com/yuque/0/2019/png/438760/1574668243392-a7e8a667-ca43-4623-979e-5c9f77bd4166.png#align=left&display=inline&height=321&name=image.png&originHeight=958&originWidth=1344&size=72265&status=done&style=none&width=450)\n\n  - 解决方法：在变量前面添加**版本号**，每次变量更新的时候都将版本号加1，比如juc的原子包中的AtomicStampedReference类。\n\n- **循环时间长开销大**：CAS算法需要不断地自旋来读取最新的内存值，长时间读取不到就会造成不必要的CPU开销。\n- **只能保证一个共享变量的原子操作**（jdk1.5的AtomicReference来保证应用对象之间的原子性，可以把多个变量放在一个对象里来进行CAS操作，解决了这一问题）。\n\n \n<a name=\"AkT6i\"></a>\n### 四、了解Java的中线程\n<a name=\"iYDJY\"></a>\n#### 1. 简单线程的创建和启动\n\n```java\n//继承Thread类\npublic class MyThread extends Thread{\n    @Override\n    public void run() {\n        System.out.println(\"线程运行\");\n    }\n    public static void main(String[] args) {\n        MyThread myThread=new MyThread();\n        myThread.start();\n    }\n}\n```\n\n```java\n//实现Runable接口\npublic class MyRunable implements Runnable{\n    @Override\n    public void run() {\n        System.out.println(\"线程运行\");\n    }\n\n    public static void main(String[] args) {http://moguhu.com/article/detail?articleId=39\n        Thread t=new Thread(new MyRunable());\n        t.start();\n    }\n}\n```\n\n<a name=\"1OfIg\"></a>\n#### 2. 了解线程的生命周期\n![image.png](https://cdn.nlark.com/yuque/0/2019/png/438760/1574674289075-5e062380-5106-4e16-8500-188be956c5cc.png#align=left&display=inline&height=471&name=image.png&originHeight=471&originWidth=660&size=136767&status=done&style=none&width=660)<br />对上图进行对线程的生命周期的分析：\n\n- （1）新建状态(NEW)：线程被创建出来，还没有调用start方法；\n- （2）可运行状态(RUNABLE)：调用线程的start方法，线程等待CPU时间\n- （3）运行状态(RUNNING)：线程获取到CPU时间，开始执行线程任务\n- （4）等待状态(WAITING)：阻塞状态是指线程因为某种原因放弃了 cpu 使用权，也即让出了 cpu timeslice ，暂时停止运行。直到线程进入可运行状态，才有机会再次获得 cpu timeslice 转到运行状态。　\n-  (5) 等待超时状态(TIME_WAITING): 超时等待状态，与WAITING不同，它可以在指定时间自行退出，释放锁\n- （6） 死亡(TERMINATED)：线程 run ()、 main () 方法执行结束，或者因异常退出了 run ()方法，则该线程结束生命周期。死亡的线程不可再次复生。\n- 等待阻塞(BLOCKED)的情况分三种：\n  - A：等待阻塞：线程调用wait方法，释放线程占有的锁，JVM把线程加入等待队列\n  - B：同步阻塞：线程尝试获取对象锁的时候，但是该锁被别的线程占有，那么JVM把该线程加入锁池\n  - C：其它阻塞：线程调用sleep，join方法(不会释放锁)，请求I/O时，JVM把线程设为阻塞状态，当休眠时间到了，join等待的线程超时或者完成任务，I/O处理完毕，线程重新进入运行状态，等待CPU时间\n\n<a name=\"kNRek\"></a>\n#### 3. 线程的优先级\n\n- 在Thread 类中定义了属性 priority 用来控制线程的优先级\n- 优先级的范围从1~10，在线程构建的时候可以通过setPriority(int)方法来修改优先级，默认优先级是5，优先级高的线程分配时间片的数量要多于优先级低的线程。\n\n**注意**：线程优先级不能作为程序正确性的依赖，因为操作系统可以完全不用理会Java线程对于优先级的设定\n\n<a name=\"2YkfJ\"></a>\n#### 4. 守护线程\n\n```java\npublic class DaemonThread {\n    public static void main(String[] args) {\n        Thread thread = new Thread(new DaemonRunner(), \"DaemonRunner\");\n        thread.setDaemon(true); //开启守护线程\n        thread.start();\n    }\n    static class DaemonRunner implements Runnable {\n        @Override\n        public void run() {\n            try {\n                TimeUnit.SECONDS.sleep(100);\n            } catch (InterruptedException e) {\n                e.printStackTrace();\n            } finally {\n                //这里不会输出\n                System.out.println(\"DaemonThread finally run.\");\n            }\n        }\n    }\n}\n```\n**注意：　**\n\n- Daemon属性需要在启动线程之前设置，不能在启动线程之后设置。不然会抛出IllegalThreadStateException　异常\n- 当一个Java虚拟机中不存在非Daemon线程的时候，Java虚拟机将会退出，意味着Daemon 线程会被中断\n- 所以，在构建Daemon线程时，不能依靠finally块中的内容来确保执行关闭或清理资源的逻辑\n\n\n到此，　Java 多线程已经入门，接下来要先了解线程的抽象内存模型。\n\n<a name=\"tikZJ\"></a>\n### 参考\n\n- [https://blog.csdn.net/hanchao5272/article/details/79521731](https://blog.csdn.net/hanchao5272/article/details/79521731)\n- [https://howtodoinjava.com/java/multi-threading/java-multi-threading-evolution-and-topics/](https://howtodoinjava.com/java/multi-threading/java-multi-threading-evolution-and-topics/)\n- [https://docs.oracle.com/javase/8/docs/technotes/guides/concurrency/changes8.html](https://docs.oracle.com/javase/8/docs/technotes/guides/concurrency/changes8.html)\n- [https://www.itcodemonkey.com/article/1830.html](https://www.itcodemonkey.com/article/1830.html)\n- [https://mp.weixin.qq.com/s/-gvhklcWGO5aPiFaBLpp3g](https://mp.weixin.qq.com/s/-gvhklcWGO5aPiFaBLpp3g)\n\n','',0,1,'hdj@admin.cn','2020-04-29 22:49:18',1,0,'2020-04-29 23:09:00',0,0,'2020-04-29 23:09:00',5,0,1,0,0,'','1255509468804808704'),(1255514529761067008,'并发编程之JMM（Java Memory Model）',' 并发编程之JMMJava Memory Modela name\"a9SE9\"a 一、JMM基础a name\"oZfku\"a 1.  线程之间的通信机制在并发编程中，需要处理两个关键问题线程之间如何通信及线程之间如何同步这里的线程是指并发执行的活动实体通信是指线程之间以何种机制来交换信息在命令式编程中，线程之间的通信机制有两种共享内存和消息传递br 同步是指程序中用于控制不同线程间操作发生相对顺序','# 并发编程之JMM（Java Memory Model）\n\n\n<a name=\"a9SE9\"></a>\n### 一、JMM基础\n<a name=\"oZfku\"></a>\n#### 1.  线程之间的通信机制\n在并发编程中，需要处理两个关键问题：线程之间如何通信及线程之间如何同步（这里的线程是指并发执行的活动实体）。通信是指线程之间以何种机制来交换信息。在命令式编程中，线程之间的通信机制有两种：**共享内存和消息传递。**<br />**同步**是指程序中用于控制不同线程间操作发生相对顺序的机制。**在共享内存并发模里，同步是显式进行的**。程序员必须显式指定某个方法或某段代码需要在线程之间互斥执行。**在消息传递的并发模型里，由于消息的发送必须在消息的接收之前，因此同步是隐式进行的。<br />Java的并发采用的是共享内存模型**，Java线程之间的通信总是隐式进行，整个通信过程对程序员完全透明。\n\n后续，会说明有哪几种通信方法。\n\n<a name=\"ihiIM\"></a>\n#### 2. JMM 抽象结构\n![image.png](https://cdn.nlark.com/yuque/0/2019/png/438760/1574733118769-34318fab-95f0-4973-879a-47c21b3acbd7.png#align=left&display=inline&height=420&name=image.png&originHeight=582&originWidth=675&size=57085&status=done&style=none&width=487)\n\n在Java中，所有成员变量、静态变量和数组元素都存储在堆内存中，堆内存在线程之间共享，所以它们通常也称为共享变量。JMM定义了线程和主内存之间的抽象关系：线程之间的共享变量存储在主内存（Main Memory）中，每个线程都有一个私有的本地内存（Local Memory，或者也可以称为工作内存 Work Memory），本地内存中存储了该线程以读/写共享变量的副本。本地内存是JMM的一个抽象概念，并不真实存在。它涵盖了缓存、写缓冲区、寄存器以及其他的硬件和编译器优化。\n\n多个线程同时对同一个共享变量进行读写的时候会产生线程安全问题。那为什么CPU不直接操作内存，而要在CPU和内存间加上各种缓存和寄存器等缓冲区呢？因为CPU的运算速度要比内存的读写速度快得多，如果CPU直接操作内存的话势必会花费很长时间等待数据到来，所以缓存的出现主要是为了解决CPU运算速度与内存读写速度不匹配的矛盾。\n\n<a name=\"WNvQT\"></a>\n#### 3. Java内存间的交互操作\n![image.png](https://cdn.nlark.com/yuque/0/2019/png/438760/1574734116089-cd405592-ec33-43c1-b0df-b854f528af2a.png#align=left&display=inline&height=268&name=image.png&originHeight=776&originWidth=1842&size=206954&status=done&style=none&width=635)\n\n![image.png](https://cdn.nlark.com/yuque/0/2019/png/438760/1574734169101-d985d044-a9be-44a6-a002-9c396ae0e459.png#align=left&display=inline&height=378&name=image.png&originHeight=378&originWidth=474&size=89319&status=done&style=none&width=474)\n\n- read：把一个变量的值从主内存传输到工作内存中\n- load：在 read 之后执行，把 read 得到的值放入工作内存的变量副本中\n- use：把工作内存中一个变量的值传递给执行引擎\n- assign：把一个从执行引擎接收到的值赋给工作内存的变量\n- store：把工作内存的一个变量的值传送到主内存中\n- write：在 store 之后执行，把 store 得到的值放入主内存的变量中\n- lock：作用于主内存的变量，把一个变量标识为一条线程独占状态\n- unlock：作用于主内存变量，把一个处于锁定状态的变量释放出来，释放后的变量才可以被其他线程锁定\n\n对应以上内存交互操作的相关规定：\n\n1. **不允许read和load、store和write操作之一单独出现**，即不允许出现从主内存读取了而工作内存不接受，或者从工作内存回写了但主内存不接受的情况出现；\n1. 不允许一个线程丢弃它最近的assign操作，即变量在工作内存变化了必须把该变化同步回主内存；\n1. 不允许一个线程无原因地（即未发生过assign操作）把一个变量从工作内存同步回主内存；\n1. 一个新的变量必须在主内存中诞生，不允许工作内存中直接使用一个未被初始化（load或assign）过的变量，换句话说就是对一个变量的use和store操作之前必须执行过load和assign操作；\n1. 一个变量同一时刻只允许一条线程对其进行lock操作，但lock操作可以被同一个线程执行多次，多次执行lock后，只有执行相同次数的unlock操作，变量才能被解锁。\n1. 如果对一个变量执行lock操作，将会清空工作内存中此变量的值，在执行引擎使用这个变量前，需要重新执行load或assign操作初始化变量的值；\n1. 如果一个变量没有被lock操作锁定，则不允许对其执行unlock操作，也不允许unlock一个其它线程锁定的变量；\n1. 对一个变量执行unlock操作之前，必须先把此变量同步回主内存中，即执行store和write操作；\n\n**注意:**这里的lock和unlock是实现synchronized的基础，Java并没有把lock和unlock操作直接开放给用户使用，但是却提供了两个更高层次的指令来隐式地使用这两个操作，即monitorenter和monitorexit。\n\n<a name=\"56m5h\"></a>\n### 二、指令重排\n在执行程序时，为了提高性能，编译器和处理器常常会对指令做重排序。\n\n<a name=\"uwJ7E\"></a>\n#### 1. 指令重排种类\n\n- **编译器优化的重排序。**编译器在不改变单线程程序语义的前提下，可以重新安排语句的执行顺序。\n- **指令级并行的重排序**。现代处理器采用了指令级并行技术（Instruction-LevelParallelism，ILP）来将多条指令重叠执行。如果不存在数据依赖性，处理器可以改变语句对应机器指令的执行顺序。\n- **内存系统的重排序**。由于处理器使用缓存和读/写缓冲区，这使得加载和存储操作看上去可能是在乱序执行\n\n<a name=\"9BvYj\"></a>\n#### 2. 图示\n![image.png](https://cdn.nlark.com/yuque/0/2019/png/438760/1574735612188-22ad2eb0-dbb8-4d03-9f74-ce9c720bc1fc.png#align=left&display=inline&height=113&name=image.png&originHeight=113&originWidth=634&size=37877&status=done&style=none&width=634)\n\n**注意**：如果两个操作访问同一个变量，其中一个为写操作，此时这两个操作之间存在数据依赖性。 编译器和处理器不会改变存在数据依赖性关系的两个操作的执行顺序，即不会重排序。不管怎么重排序，单线程下的执行结果不能被改变，编译器、runtime和处理器都必须遵守as-if-serial语义。\n\n名称说明：<br />as-if-serial 语义: 不管怎么重排序（编译器和处理器为了提高并行度），（单线程）程序的执行结果不能被改变\n\n<a name=\"19o9e\"></a>\n### 三、内存屏障\n\n| 屏障类型 | 示例 | 描述 |\n| :--- | :--- | :--- |\n| LoadLoad Barriers | Load1-LoadLoad-Load2 | Load1数据装载过程要先于Load2及所有后续的数据装载过程 |\n| StoreStore Barriers | Store1-StoreStore-Store2 | Store1刷新数据到内存的过程要先于Strore2及后续所有刷新数据到内存的过程 |\n| LoadStore Barriers | Load1-LoadStore-Store2 | Load1数据装载要先于Strore2及后续所有刷新数据到内存的过程 |\n| StoreLoad Barriers | Store1-StoreLoad-Load2 | Store1刷新数据到内存的过程要先于Load2及所有后续的数据装载过程 |\n\n\n**注意**：Java中volatile关键字的实现就是通过内存屏障来完成的。\n\n<a name=\"OSY1p\"></a>\n### 四、Java内存模型的三大特性\n<a name=\"gsFIv\"></a>\n#### 1. 介绍\nJava内存模型就是为了解决多线程环境下共享变量的一致性问题；一致性主要包含三大特性：原子性、可见性、有序性\n<a name=\"uPoZv\"></a>\n#### 2. 原子性：\n\n- 原子性是指一段操作一旦开始就会一直运行到底，中间不会被其它线程打断，这段操作可以是一个操作，也可以是多个操作。\n<a name=\"IrR3M\"></a>\n#### 3. 可见性:\n\n- 可见性是指当一个线程修改了共享变量的值，其它线程能立即感知到这种变化。\n<a name=\"FdJp3\"></a>\n#### 4. 有序性\n\n- 如果在本线程中观察，所有的操作都是有序的；如果在另一个线程中观察，所有的操作都是无序的。\n- 前半句是指线程内表现为串行的语义，后半句是指“指令重排序”现象和“工作内存和主内存同步延迟”现象。\n\n<a name=\"J4w1O\"></a>\n### 五、happens-before\n<a name=\"oJfVX\"></a>\n#### 1. 介绍\n从JDK5开始，Java使用新的JSR-133内存模型，基于happens-before的概念来阐述操作之间的内存可见性。在JMM中，如果一个操作的执行结果需要对另一个操作可见，那么这两个操作之间必须要存在happens-before关系，这个的两个操作既可以在同一个线程，也可以在不同的两个线程中。\n\n<a name=\"OLk3b\"></a>\n#### 2. happens-before规则：\n\n- 程序次序原则：\n  - 在一个线程内，按照程序书写的顺序执行，**书写在前面的操作先行发生于书写在后面的操作**，准确地讲是控制流顺序而不是代码顺序，因为要考虑分支、循环等情况。\n- 监视器锁定原则：\n  - 一个unlock操作先行发生于后面对同一个锁的lock操作。\n- volatile域规则：\n  - 对一个volatile变量的**写操作**先行发生于后面对该变量的**读操作**。\n- 传递性规则：\n  - 如果 A happens-before B，且 B happens-before C，那么A happens-before C。\n- 线程启动原则\n  - 对线程的start()操作先行发生于线程内的任何操作。\n- 线程终止原则\n  - 线程中的所有操作先行发生于检测到线程终止，可以通过Thread.join()、Thread.isAlive()的返回值检测线程是否已经终止。\n- 线程中断原则\n  - 对线程的interrupt()的调用先行发生于线程的代码中检测到中断事件的发生，可以通过Thread.interrupted()方法检测是否发生中断。\n- 对象终结原则\n  - 一个对象的初始化完成（构造方法执行结束）先行发生于它的finalize()方法的开始。\n\n**注意:**两个操作之间具有happens-before关系，并不意味着前一个操作必须要在后一个操作之前执行！happens-before仅仅要求前一个操作（**执行的结果**）对后一个操作可见，且前一个操作按顺序排在第二个操作之前（the first is visible to and ordered before the second）。\n\n<a name=\"bLdqc\"></a>\n#### 3. 图示\n![image.png](https://cdn.nlark.com/yuque/0/2019/png/438760/1574738904582-80fd9254-e428-445b-89e4-b621b9d294fd.png#align=left&display=inline&height=599&name=image.png&originHeight=599&originWidth=572&size=123226&status=done&style=none&width=572)\n\n**解析：**\n\n- **1 happens-before 2和3 happens-before 4**由程序顺序规则产生。由于编译器和处理器都要遵守as-if-serial语义，也就是说，as-if-serial语义保证了程序顺序规则。因此，可以把程序顺序规则看成是对as-if-serial语义的“封装”。\n- **2 happens-before 3**是由volatile规则产生。对一个volatile变量的读，总是能看到（任意线程）之前对这个volatile变量最后的写入。因此，volatile的这个特性可以保证实现volatile规则。\n- **1 happens-before 4**是由传递性规则产生的。这里的传递性是由volatile的内存屏障插入策略和volatile的编译器重排序规则共同来保证的。\n\n<a name=\"o7DOo\"></a>\n### 参考\n\n- [https://mrbird.cc/Java-Memory-model.html](https://mrbird.cc/Java-Memory-model.html)\n- [死磕 java同步系列之JMM（Java Memory Model)](https://mp.weixin.qq.com/s?__biz=Mzg2ODA0ODM0Nw==&mid=2247483909&idx=1&sn=778c86bc63a350e9d8397e1a727aabf5&scene=21#wechat_redirect)\n- 《Java并发编程的艺术》\n\n','',0,1,'hdj@admin.cn','2020-04-29 23:09:25',1,0,'2020-04-29 23:10:21',0,0,'2020-04-29 23:10:21',16,0,1,0,0,'','1255514529761067008'),(1255514634941628416,'并发编程之线程间的通信',' 并发编程之线程间的通信Java支持多个线程同时访问一个对象或者对象的成员变量，由于每个线程可以拥有这个变量的拷贝虽然对象以及成员变量分配的内存是在共享内存中的，但是每个执行的线程还是可以拥有一份拷贝，这样做的目的是加速程序的执行，这是现代多核处理器的一个显著特性，所以程序在执行过程中，一个线程看到的变量并不一定是最新的a name\"9ElWg\"a 一、了解volatile 和 synchroni','# 并发编程之线程间的通信\n\nJava支持多个线程同时访问一个对象或者对象的成员变量，由于每个线程可以拥有这个变量的拷贝（虽然对象以及成员变量分配的内存是在共享内存中的，但是每个执行的线程还是可以拥有一份拷贝，这样做的目的是加速程序的执行，这是现代多核处理器的一个显著特性），所以程序在执行过程中，一个线程看到的变量并不一定是最新的。\n\n<a name=\"9ElWg\"></a>\n### 一、了解volatile 和 synchronized关键字\n<a name=\"8MbOP\"></a>\n#### 1.1. volatile关键字\n关键字volatile可以用来修饰字段（成员变量），就是告知程序任何对该变量的访问均需要从共享内存（主内存）中获取，忽视(设置无效)工作内存中的变量，而对它的改变必须同步刷新回共享内存，它能保证所有线程对变量访问的**可见性。**<br />**\n\n<a name=\"XDMcF\"></a>\n#### 1.2. synchronized 关键字\nsynchronized  是提供同步锁 关键字，可以修饰方法 或 以同步块的方式使用，synchronized  实现的锁是排他锁，非公平锁。主要采用monitorenter和monitorexit指令 来保持线程同步， synchronized 可以保证数据的一致性：**即原子性、可见性和有序性**\n\n关键字volatile 和 synchronized简单了解一下，后面着重深入了解。\n\n<a name=\"nkFWx\"></a>\n### 二、**等待通知机制**\n<a name=\"kHZAt\"></a>\n#### 2.1 Object 类中等待通知的方法\n\n- notify() :  用于唤醒正在等待对象监视器的单一线程，使其从wait()方法中返回，返回的前提是该线程必须获取了对象锁\n- notifyAll()：　唤醒等待对象监视器的所有线程\n- wait() ：　调用该方法线程进入等待状态WAITING，只用等其他线程唤醒或者中断操作才能返回，注意在调用wait()　方法后，线程会释放锁\n- wait(long timeout) : 超时等待返回，超时参数为毫秒，所以说在等待n毫秒后，没有被唤醒，则超时返回\n- wait(long timeout, int nanos) ：　用于控制超时参数的单位，可以达到纳秒级\n<a name=\"j89qs\"></a>\n#### 2.2 实现交替打印１，２\n\n```java\n//交替打印1 2 1 2 \npublic class WaitNotifyThread {\n    //定义对象锁\n    private static Object lock = new Object();\n    private static boolean flag = false;\n\n    public static void main(String[] args) {\n        WaitThread waitThread = new WaitThread();\n        NotifyThread notifyThread = new NotifyThread();\n\n        waitThread.setName(\"WaitThread\");\n        notifyThread.setName(\"notifyThread\");\n        waitThread.start();\n        notifyThread.start();\n\n    }\n\n    static class WaitThread extends Thread {\n\n        @Override\n        public void run() {\n            //synchronized代码块，锁住lock\n            synchronized (lock) {\n                while (true) {\n                    //如果flag == true ，则挂起线程等待，释放锁\n                    while (flag) {\n                        try {\n                            lock.wait();\n                        } catch (InterruptedException e) {\n                            e.printStackTrace();\n                        }\n                    }\n                    System.out.println(Thread.currentThread().getName() + \" 1\");\n                    try {\n                        TimeUnit.SECONDS.sleep(1);\n                    } catch (InterruptedException e) {\n                        e.printStackTrace();\n                    }\n                    //设置flag = true ，挂起线程\n                    flag = true;\n                    //通知其他线程执行\n                    lock.notifyAll();\n                }\n            }\n        }\n    }\n\n    static class NotifyThread extends Thread {\n        @Override\n        public void run() {\n            synchronized (lock) {\n                while (true) {\n                    //如果flag == false ，则挂起线程等待，释放锁\n                    while (!flag) {\n                        try {\n                            lock.wait();\n                        } catch (InterruptedException e) {\n                            e.printStackTrace();\n                        }\n                    }\n                    System.out.println(Thread.currentThread().getName() + \" 2\");\n                    try {\n                        TimeUnit.SECONDS.sleep(1);\n                    } catch (InterruptedException e) {\n                        e.printStackTrace();\n                    }\n                    //设置flag = false ，挂起线程\n                    flag = false;\n                    //通知其他线程执行\n                    lock.notifyAll();\n                }\n            }\n        }\n    }\n}\n```\n\n**注意：**\n\n- 1）使用wait()、notify()和notifyAll()时需要先对调用对象加锁。\n- 2）调用wait()方法后，线程状态由RUNNING变为WAITING，并将当前线程放置到对象的等待队列，释放对象锁。\n- 3）notify()或notifyAll()方法调用后，等待线程依旧不会从wait()返回，需要调用notify()或notifAll()的线程释放锁之后，等待线程才有机会从wait()返回。\n- 4）notify()方法将等待队列中的任意一个等待线程从等待队列中移到同步队列中，而notifyAll()方法则是将等待队列中所有的线程全部移到同步队列，被移动的线程状态由WAITING变为BLOCKED\n\n<a name=\"yw7kJ\"></a>\n#### 2.3 等待通知图示\n![image.png](https://cdn.nlark.com/yuque/0/2019/png/438760/1574751785129-1e66cb49-ea3c-42af-9619-b0543d5def2c.png#align=left&display=inline&height=437&name=image.png&originHeight=447&originWidth=647&size=101424&status=done&style=none&width=632)\n\n<a name=\"T0slm\"></a>\n### 三、管道输入/输出流\n<a name=\"mRooA\"></a>\n#### 3.1 定义\n管道流用于输送一个线程的输出流到另一个线程的输入流，即可用于线程间的通信\n\n<a name=\"mpWUH\"></a>\n#### 3.2 管道流相关类\n\n- [PipedReader](http://java.sun.com/j2se/1.5.0/docs/api/java/io/PipedReader.html)\n- [PipedWriter](http://java.sun.com/j2se/1.5.0/docs/api/java/io/PipedWriter.html)\n- [PipedInputStream](http://java.sun.com/j2se/1.5.0/docs/api/java/io/PipedInputStream.html)\n- [PipedOutputStream](http://java.sun.com/j2se/1.5.0/docs/api/java/io/PipedOutputStream.html)\n\n<a name=\"KJ9il\"></a>\n#### 3.3 例子\n\n```java\npublic class SendAndReceivePips {\n\n    PipedReader reader = new PipedReader();\n    PipedWriter writer = new PipedWriter();\n\n    public SendAndReceivePips() {\n        try {\n            //管道连接\n            reader.connect(writer);\n        } catch (IOException e) {\n            e.printStackTrace();\n        }\n    }\n\n    public void sender() {\n        new Thread(new Runnable() {\n            @Override\n            public void run() {\n                try  {\n                    int a = 10;\n                    while (a > 0) {\n                        a = a - 1;\n                        Thread.sleep(1000);\n                        int num = (int) (Math.random() * 255);\n                        System.out.println(Thread.currentThread().getName() + \" 生产者生产了一个数字，该数字为： \" + num);\n                       	\n                        writer.write(num);\n                        writer.flush();\n                    }\n                }  catch (Exception e) {\n                    e.printStackTrace();\n                }finally {\n                    try {\n                        writer.close();\n                    } catch (IOException e) {\n                        e.printStackTrace();\n                    }\n                }\n            }\n        }).start();\n    }\n\n    public void receiver() {\n        new Thread(new Runnable() {\n            @Override\n            public void run() {\n                try {\n                    int a = 10;\n                    while (a > 0) {\n                        a = a - 1;\n                       Thread.sleep(2000);\n                        int num = reader.read();\n                        System.out.println(Thread.currentThread().getName() + \" 消费者消费了一个数字，该数字为：\" + num);\n                    }\n                } catch (Exception e) {\n                    e.printStackTrace();\n                }finally {\n                    try {\n                        reader.close();\n                    } catch (IOException e) {\n                        e.printStackTrace();\n                    }\n                }\n            }\n        }).start();\n    }\n\n    public static void main(String[] args) {\n        SendAndReceivePips pips = new SendAndReceivePips();\n        pips.sender();\n        pips.receiver();\n    }\n}\n```\n\n```bash\nThread-0 生产者生产了一个数字，该数字为： 150\nThread-0 生产者生产了一个数字，该数字为： 118\nThread-1 消费者消费了一个数字，该数字为：150\nThread-0 生产者生产了一个数字，该数字为： 232\nThread-0 生产者生产了一个数字，该数字为： 250\nThread-1 消费者消费了一个数字，该数字为：118\nThread-0 生产者生产了一个数字，该数字为： 141\nThread-0 生产者生产了一个数字，该数字为： 103\nThread-1 消费者消费了一个数字，该数字为：232\nThread-0 生产者生产了一个数字，该数字为： 24\nThread-0 生产者生产了一个数字，该数字为： 111\nThread-1 消费者消费了一个数字，该数字为：250\nThread-0 生产者生产了一个数字，该数字为： 142\nThread-0 生产者生产了一个数字，该数字为： 12\nThread-1 消费者消费了一个数字，该数字为：141\nThread-1 消费者消费了一个数字，该数字为：103\nThread-1 消费者消费了一个数字，该数字为：24\nThread-1 消费者消费了一个数字，该数字为：111\nThread-1 消费者消费了一个数字，该数字为：142\nThread-1 消费者消费了一个数字，该数字为：12\n```\n\n<a name=\"obvLV\"></a>\n### 四、 Thread.join 的使用\n<a name=\"aOQ4k\"></a>\n#### 4.1 介绍\n如果一个线程A执行了thread.join()语句，其含义是：当前线程A等待thread线程终止之后才从thread.join()返回。线程Thread除了提供join()方法之外，还提供了join(long millis)和join(longmillis,int nanos)两个具备超时特性的方法。这两个超时方法表示，如果线程thread在给定的超时时间里没有终止，那么将会从该超时方法中返回\n<a name=\"8jGxe\"></a>\n#### 4.2 例子\n\n```java\npublic class Join {\n    public static void main(String[] args) throws Exception {\n        Thread previous = Thread.currentThread();\n        for (int i = 0; i < 10; i++) {\n            // 每个线程拥有前一个线程的引用，需要等待前一个线程终止，才能从等待中返回\n            Thread thread = new Thread(new Domino(previous), String.valueOf(i));\n            thread.start();\n            previous = thread;\n        }\n        TimeUnit.SECONDS.sleep(5);\n        System.out.println(Thread.currentThread().getName() + \" terminate.\");\n    }\n\n    static class Domino implements Runnable {\n        private Thread thread;\n\n        public Domino(Thread thread) {\n            this.thread = thread;\n        }\n\n        public void run() {\n            try {\n                //当前线程挂起，进入阻塞状态\n                thread.join();\n            } catch (InterruptedException e) {\n            }\n            System.out.println(Thread.currentThread().getName() + \" terminate.\");\n        }\n    }\n}\n```\n\n输出\n```bash\nmain terminate.\n0 terminate.\n1 terminate.\n2 terminate.\n3 terminate.\n4 terminate.\n5 terminate.\n6 terminate.\n7 terminate.\n8 terminate.\n9 terminate.\n```\n<a name=\"X7O1B\"></a>\n#### 4.3 join 源码\n```java\npublic final synchronized void join(long millis)\nthrows InterruptedException {\n    long base = System.currentTimeMillis();\n    long now = 0;\n\n    if (millis < 0) {\n        throw new IllegalArgumentException(\"timeout value is negative\");\n    }\n\n    if (millis == 0) {\n        // 条件不满足，继续等待\n        while (isAlive()) {\n            wait(0);\n        }\n    } else {\n        // 条件不满足，继续等待\n        while (isAlive()) {\n            long delay = millis - now;\n            if (delay <= 0) {\n                break;\n            }\n            wait(delay);\n            now = System.currentTimeMillis() - base;\n        }\n    }\n    // 条件满足，方法返回\n}\n```\n\n\n<a name=\"Xo1ir\"></a>\n### 五、**ThreadLocal**\n<a name=\"wheph\"></a>\n#### 5.1 介绍\n ThreadLocal，即线程本地变量，是一个以ThreadLocal对象为键、任意对象为值的存储结构。\n<a name=\"B8yOS\"></a>\n#### 5.2 ThreadLocal 、Thread和ThreadLocalMap 关系 图示(来自google搜索)\n![image.png](https://cdn.nlark.com/yuque/0/2020/png/438760/1580305007220-31d7c880-038f-459a-90e7-a30642ddeb8c.png#align=left&display=inline&height=397&name=image.png&originHeight=794&originWidth=1072&size=362143&status=done&style=none&width=536)\n\n<a name=\"H610w\"></a>\n#### 5.3 ThreadLocal　使用总结\n\n- ThreadLocal 并不解决线程间共享数据的问题\n- ThreadLocal 通过隐式的在不同线程内创建独立实例副本避免了实例线程安全的问题\n- 个线程持有一个 Map 并维护了 ThreadLocal 对象与具体实例的映射，该 Map 由于只被持有它的线程访问，故不存在线程安全以及锁的问题\n- ThreadLocalMap 的 Entry 对 ThreadLocal 的引用为**弱引用**，避免了 ThreadLocal 对象无法被回收的问题\n- ThreadLocal 适用于变量在线程间隔离且在方法间共享的场景\n<a name=\"QAfdb\"></a>\n### 六、并发工具类\n\n- CountDownLatch 并发工具\n- CyclicBarrier 并发工具\n\n线程之间的通信可按照实际场景，选择合适的方法。\n\n<a name=\"W4g6G\"></a>\n### 参考\n\n-  《Java并发编程的艺术》\n\n','',0,1,'hdj@admin.cn','2020-04-29 23:09:50',1,0,'2020-04-29 23:10:32',0,0,'2020-04-29 23:10:32',5,0,1,0,0,'','1255514634941628416'),(1257195405330874368,'个人简历','姓名 黄家健电话 13660846589邮箱  13660846589163.com工作经验 2 年求职意向 Java 后端开发工程师专业 软件技术学历 : 大专个人博客 专业技能 掌握  Java 基础知识，熟练使用 Java 集合，Java JUC 并发包 熟悉Servlet及其工作原理和生命周期 能熟练使用 SpringBoot、 Spring、 SpringMVC、 MyBatis等 Ja','姓名： 黄家健\n电话： 1366-0846-589\n邮箱：  13660846589@163.com\n\n工作经验： **2 年**\n求职意向： **Java 后端开发工程师**\n\n专业： 软件技术\n学历 : 大专\n个人博客：\n\n## 专业技能\n\n- 掌握  **Java 基础知识**，熟练使用 **Java 集合**，**Java JUC 并发包**\n- 熟悉**Servlet**及其工作原理和生命周期\n- 能熟练使用 **SpringBoot**、 **Spring**、 **SpringMVC**、 **MyBatis**等 Java 框架进行后台开发，了解其执行原理\n- 熟悉关系型数据库**MySQL**，能熟练编写 SQL 及简单优化;\n- 熟悉使用 **Redis**、**Rabbitmq**、**Elasticsearch**等中间件；以及与 Spring 的整合使用\n- 熟练使用 Java 开发环境 Eclipse 和 IntelliJIDEA、版本控制工具 Git、项目构建和管理工具 Maven。\n- 熟悉使用 Nginx， 采用 Nginx 作为静态服务器，限流，反向代理，防盗链，SSL 配置等操作\n- 熟悉使用 HTML、CSS、JavaScript 和 Vue.js 进行 Web 开发\n- 熟悉使用 SpringCloud 构建微服务\n- 了解 Linux 常用命令\n- 了解 Docker，并有使用 docker-compose 编排项目\n\n## 证书\n\nCTE4 , 全国计算机等级考试二级证书(Java)\n\n## 工作经历\n\n2019.06-至今　　|　　佛山市深简软件科技有限公司　　|　　Java 后端开发工程师\n\n2018.04-2019.02　　|  奇立智云科技有限公司　　|　　Java 后端开发工程师\n\n## 项目经验\n\n#### 2019.06 -至今　　|　　DBSTOOL 亚马逊卖家分析系统　　|　　佛山市深简软件科技有限公司\n\n项目简介：公司自主研发，针对亚马逊卖家运营过程中的需求和痛点，运用人工智能 AI、云计算、大数据等前沿技术，能给出精准实时的决策数据分析，帮助卖家降低成本，提升工作效率，提高销售业绩的系统。\n\n主要负责如下工作：\n\n* 负责用户权限模块，基于 RBAC 模型 采用 Shiro + JWT(JSON Web Token)   实现授权和鉴权\n* 负责系统消息通知功能，包括短信、邮件和站内信； 采用 AOP + 注解 + Rabbitmq 实现无侵入业务代码，异步发送消息。\n* 在后端 leader 的指导下，了解业务和整合亚马逊 API 实现卖家店铺授权和广告授权模块。\n* 在后端 leader 的指导下，针对评论分析数据复杂，API 接口响应慢问题。一、对原有的表结构进行调整，先在后端统计计算保存。 二、对 API 基于单一原则拆分返回数据。\n\n项目主要使用 **SpringBoot**、 **SpringMVC**、**Mybatis-Plus** 框架基于 Java 语言开发，前端使用基于 **Vue.js** 的 UI 框架 Element UI ，采用了前后端分离开发部署\n\n项目地址： 　　[https://www.dbstool.com/](https://www.dbstool.com/)\n\n#### 2019.01 - 2019.03　　|　　优车优盟　　|　　奇立智云科技有限公司\n\n项目一：二手车拍卖平台，类似瓜子二手车\n\n对公司老旧项目进行二次迭代，技术栈：前端采用 JQuery+DoT.js+LayUi；后端采用 Spring, SpringMVC, MyBatis ，SpringSecurity\n\n主要负责如下工作：\n\n- 负责询价模块和拍卖模块重构优化\n- 负责用户权限模块的调整\n\n项目二、Gbox 系统\n\n项目介绍：该系统对 4s 店买车的客户流失分析、客户行为分析，以供广汽商贸集团及时制定对应的方案\n\n主要负责如下工作\n\n- 了解客户需求与客户沟通，对 4s 店的销售系统和售后服务系统的数据进行抓取、清洗，协助产品经理编写数据统计文档。\n- 使用 Java 编写爬虫，抓取销售数据，为 GBox 系统提供分析数据。\n\n#### 个人项目\n\n##### 一、个人博客项目\n\n项目介绍：\n该项目主要是为了磨合自己的技术，方便自己总结日常技术和生活的琐事而创建的。\n\n项目模块：\n\n- 用户角色菜单管理模块\n- 文章创建发布模块\n- 图库模块\n- 评论模块\n- 操作日志模块\n- 文章搜索模块\n\n项目技术：\n\n- 前端使用基于 **Vue.js** 的 UI 框架 Element UI\n- 后台采用 SpringBoot，SpringMVC ， Mybatis-Plus ，Redis ，Rabbitmq 和 Elasticsearch\n- 部署使用 Docker, docker-compose 编排\n','关于博主',0,1,'h-dj','2020-05-04 14:28:37',0,0,NULL,0,0,'2020-05-12 21:33:06',0,0,1,0,0,'','1257195405330874368'),(1262989998001487872,'博客备份到Github　私有仓库',' 一、配置SSH 秘钥 1. 创建ssh 秘钥检查是否存在ssh ls al .ssh 不存在，则生成key sshkeygen t rsa b 4096 C \"youremailexample.com\" 添加ssh key 到sshagent eval \"(sshagent s)\" 拷贝到Github 中 cat .sshidrsa.pub 2. 配置ssh 密钥到 Git 仓库 (Github','### 一、配置SSH 秘钥\n#### 1. 创建ssh 秘钥\n```\n#检查是否存在ssh \nls -al ~/.ssh \n#不存在，则生成key \nssh-keygen -t rsa -b 4096 -C \"your_email@example.com\" \n#添加ssh key 到ssh-agent \neval \"$(ssh-agent -s)\" \n#拷贝到Github 中 \ncat ~/.ssh/id_rsa.pub\n```\n#### 2. 配置ssh 密钥到 Git 仓库 (Github 或 Gitee )\n-  [配置到Github](https://help.github.com/en/github/authenticating-to-github/generating-a-new-ssh-key-and-adding-it-to-the-ssh-agent)\n\n','关于本站',0,1,'hdj@admin.cn','2020-05-20 14:14:15',2,0,'2020-05-20 14:14:15',0,0,'2020-05-20 14:14:15',0,0,1,1215266189052461057,0,'','1262989998001487872'),(1262989998605467648,'Docker mysqldump备份及推送到Github私有仓库',' 创建 GitHub 私有仓库，配置服务器 SSH 秘钥到 GitHub 定时备份数据库，并推送到 GitHub 仓库中 1. 创建 SSH 秘钥检查是否存在ssh ls al .ssh 不存在，则生成key sshkeygen t rsa b 4096 C \"youremailexample.com\" 添加ssh key 到sshagent eval \"(sshagent s)\" 拷贝到Gith','１ ．创建 GitHub 私有仓库，配置服务器 SSH 秘钥到 GitHub\n２ ．定时备份数据库，并推送到 GitHub 仓库中\n\n#### 1. 创建 SSH 秘钥\n\n```\n#检查是否存在ssh \nls -al ~/.ssh \n#不存在，则生成key \nssh-keygen -t rsa -b 4096 -C \"your_email@example.com\" \n#添加ssh key 到ssh-agent \neval \"$(ssh-agent -s)\" \n#拷贝到Github 中 \ncat ~/.ssh/id_rsa.pub\n```\n\n#### 2. 配置 SSH 密钥到 Git 仓库 (Github 或 Gitee )\n\n- [配置到Github](https://help.github.com/en/github/authenticating-to-github/generating-a-new-ssh-key-and-adding-it-to-the-ssh-agent)\n- [配置到 Gitee]()\n\n#### 3. docker mysqldump 备份数据库命令\n\n```\n#备份 \ndocker exec blog-mysql sh -c \'exec mysqldump --all-databases -uroot -p\"$MYSQL_ROOT_PASSWORD\"\' > ./all-databases.sql \n#恢复 \ndocker exec -i some-mysql sh -c \'exec mysql -uroot -p\"$MYSQL_ROOT_PASSWORD\"\' < /some/path/on/your/host/all-databases.sql \n# 备份数据库表结构 \ndocker exec blog-mysql sh -c \'exec mysqldump -uroot -p\"$MYSQL_ROOT_PASSWORD\" -d blog\' > ./blog-schema.sql \n#备份数据库数据 \ndocker exec blog-mysql sh -c \'exec mysqldump -uroot -p\"$MYSQL_ROOT_PASSWORD\" -t blog\' > ./blog-data-`date +%Y%m%d`.sql\n```\n\n#### 4. 备份脚本编写\n\n```\n#!/bin/bash\n#删除日志函数\ncleanLog () {\n  #3天前的日期\n  three_day_ago=$(date +%Y%m%d --date=\"-3 day\")\n  #遍历这个数组\n  for file in $files\n  do\n  echo $file\n  #获取该文件的创建时间\n  echo $(echo  $file | sed \'s/[^0-9]*//g\')\n  datatime=$(echo  $file | sed \'s/[^0-9]*//g\')\n  echo \"文件：　$datatime\"\n  #如果这个文件的时间小于三天前的时间则删除这个文件\n          if [ $datatime -lt $three_day_ago ]; then\n                  rm -rf ./$file\n                  git rm ./$file\n          fi\n  done\n}\n\nfile_back_path=/opt/blog/backup/blog_back\n\ncd $file_back_path\n\ntody = $(date \"+%Y-%m-%d   %H:%M:%S\")\necho  \"备份日期 开始====> ：$tody　\"\npwd\n\n# 先清除过期文件\nfiles=blog-data-*.sql\ncleanLog $files\nfiles=blog-schema-*.sql\ncleanLog $files\n\n# 备份数据\n# 备份数据库表结构\ndocker exec blog-mysql sh -c \'exec mysqldump -uroot -p\"$MYSQL_ROOT_PASSWORD\" -d blog\' > /blog-schema-`date +%Y%m%d`.sql\n##备份数据库数据\ndocker exec blog-mysql sh -c \'exec mysqldump -uroot -p\"$MYSQL_ROOT_PASSWORD\" -t blog\' > /blog-data-`date +%Y%m%d`.sql\necho  \"备份结束 ===> \"\n\necho  \"push 到Github\"\ngit pull\ngit add .\ngit commit -m \"backup blog at $tody\"\ngit push\necho  \"push 到Github 结束\"\n```\n\n#### 5、配置定时执行\n\n- 脚步执行权限\n\n```\nchmod u+x BlogBack.sh\n```\n\n- 添加任务\n\n```\ncrontab -e  \n#在文件尾部添加  \n#语法: m h  dom mon dow   command   \n#分钟　小时　天数　月　周　命令  \n#例子: 每周上午５点　:  0 5 * * 1 tar -zcf /var/backups/home.tgz /home/  \n0 0 * * * sh /opt/blog/backup/BlogBack.sh >/dev/null 2>&1\n```\n\n- 重启\n\n```\n/etc/init.d/cron restart\n```\n\n- 开启 crontab 日志(非必须)\n\n```\nsudo vim /etc/rsyslog.d/50-default.conf\n#找到cron.log相关行，将前面注释符#去掉，保存退出，重启rsyslog：\nsudo  service rsyslog  restart\n```\n','关于本站',0,1,'hdj@admin.cn','2020-05-20 14:14:16',1,0,'2020-05-20 14:14:16',1,0,'2020-05-20 16:41:30',15,0,1,1215266189052461057,0,'','1262989998605467648'),(1267078518487908352,'Linux服务器配置笔记','最近想购买服务器搭建属于自己的个人博客，需要对服务器进行一下配置，防止被人攻击购买服务器一般都有安全组配置，注意不要开放一些常用的端口 一、 参考 http:www.ruanyifeng.comblog201403serversetup.html','\n最近想购买服务器搭建属于自己的个人博客，需要对服务器进行一下配置，防止被人攻击\n\n购买服务器一般都有安全组配置，注意不要开放一些常用的端口\n\n### 一、\n\n### 参考\n- http://www.ruanyifeng.com/blog/2014/03/server_setup.html\n','',0,1,'hdj@admin.cn','2020-05-31 21:00:35',0,0,NULL,0,0,'2020-05-31 21:00:35',0,0,1,0,0,'','1267078518487908352');
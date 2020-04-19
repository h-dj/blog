drop database if exists `blog`;
create database `blog` default character set utf8mb4 collate utf8mb4_0900_ai_ci;

 use `blog`;


-- 表结构

 -- ----------------------------
 -- Table structure for t_article
 -- ----------------------------
 DROP TABLE IF EXISTS `t_article`;
 CREATE TABLE `t_article`  (
   `id` bigint(19) NOT NULL COMMENT '文章ID',
   `title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '文章标题',
   `description` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '概要',
   `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '正文（markdown）',
   `tags` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '标签',
   `author_id` bigint(11) NULL DEFAULT NULL COMMENT '作者',
   `allow_comment` tinyint(1) NULL DEFAULT 1 COMMENT '是否允许评论：0：不允许，1允许',
   `author_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '作者名称',
   `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
   `status` int(1) NULL DEFAULT 0 COMMENT '状态，0：草稿，1：发布，2：删除',
   `type` int(1) NOT NULL DEFAULT 0 COMMENT '0:普通文章，1：简历，2关于页',
   `publish_time` datetime(0) NULL DEFAULT NULL COMMENT '发布时间',
   `like_num` int(11) NULL DEFAULT NULL COMMENT '点赞数',
   `comment_num` int(11) NULL DEFAULT 0 COMMENT '评论数',
   `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
   `read_num` int(11) NULL DEFAULT 0 COMMENT '阅读量',
   `recommend` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否推荐',
   `allow_feed` tinyint(1) NULL DEFAULT 1 COMMENT '是否允许订阅：0不允许，不允许',
   `category_id` bigint(11) NULL DEFAULT NULL COMMENT '分类id',
   `top` tinyint(1) NULL DEFAULT 0 COMMENT '置顶',
   `cover` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '封面',
   `slug` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '文章自定义路径',
   PRIMARY KEY (`id`) USING BTREE
 ) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '文章信息表' ROW_FORMAT = Dynamic;

 -- ----------------------------
 -- Table structure for t_attachment
 -- ----------------------------
 DROP TABLE IF EXISTS `t_attachment`;
 CREATE TABLE `t_attachment`  (
   `id` bigint(19) NOT NULL,
   `file_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '文件名称',
   `file_suffix` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '文件后缀',
   `type` int(1) NOT NULL COMMENT '文件存放类型(0:本地，1:七牛云等oss)',
   `file_size` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
   `create_by` bigint(11) NOT NULL COMMENT '创建人',
   `create_time` datetime(0) NULL DEFAULT NULL,
   `update_time` datetime(0) NULL DEFAULT NULL,
   `path` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '文件访问路径',
   `url` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '附件链接',
   `width` bigint(20) NULL DEFAULT NULL COMMENT '宽',
   `height` bigint(20) NULL DEFAULT NULL COMMENT '高',
   PRIMARY KEY (`id`) USING BTREE
 ) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '附件表' ROW_FORMAT = Dynamic;

 -- ----------------------------
 -- Table structure for t_category
 -- ----------------------------
 DROP TABLE IF EXISTS `t_category`;
 CREATE TABLE `t_category`  (
   `id` bigint(19) NOT NULL COMMENT 'id',
   `category_name` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '分类名称',
   `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建日期',
   `description` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '描述',
   `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改日期',
   PRIMARY KEY (`id`) USING BTREE
 ) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '分类表' ROW_FORMAT = Dynamic;

 -- ----------------------------
 -- Table structure for t_comment
 -- ----------------------------
 DROP TABLE IF EXISTS `t_comment`;
 CREATE TABLE `t_comment`  (
   `id` bigint(19) NOT NULL,
   `article_id` bigint(11) NOT NULL COMMENT '文章id',
   `parent_id` bigint(11) NULL DEFAULT NULL COMMENT '父评论',
   `content` varchar(1023) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '评论内容',
   `author` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '评论人的昵称',
   `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '评论人的邮箱(不公开)',
   `author_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '评论人的网站',
   `like_count` bigint(20) NOT NULL DEFAULT 0 COMMENT '点赞数',
   `user_agent` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
   `ip_address` varchar(127) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'ip 地址',
   `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
   `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
   PRIMARY KEY (`id`) USING BTREE
 ) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '评论表' ROW_FORMAT = Dynamic;

 -- ----------------------------
 -- Table structure for t_friend_link
 -- ----------------------------
 DROP TABLE IF EXISTS `t_friend_link`;
 CREATE TABLE `t_friend_link`  (
   `id` bigint(19) NOT NULL AUTO_INCREMENT COMMENT 'ID',
   `create_time` datetime(0) NOT NULL ON UPDATE CURRENT_TIMESTAMP(0),
   `update_time` datetime(0) NOT NULL ON UPDATE CURRENT_TIMESTAMP(0),
   `status` int(1) NOT NULL DEFAULT 0 COMMENT '状态:0新建，1审核通过，2不通过',
   `describe` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '描述',
   `url` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '友链',
   `email` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '通知邮箱',
   PRIMARY KEY (`id`) USING BTREE
 ) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '友链' ROW_FORMAT = Dynamic;

 -- ----------------------------
 -- Table structure for t_log
 -- ----------------------------
 DROP TABLE IF EXISTS `t_log`;
 CREATE TABLE `t_log`  (
   `id` bigint(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
   `title` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '操作标题',
   `method` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '操作方法',
   `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
   `time` bigint(11) NULL DEFAULT NULL COMMENT '执行时间',
   `params` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '请求参数',
   `ip` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'ip',
   `url` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '请求url',
   `ip_address` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'ip地址',
   `create_time` datetime(0) NULL DEFAULT NULL COMMENT '操作时间',
   `exception` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '异常',
   `user_agent` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户代理',
   `level` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '等级',
   `browser` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '浏览器',
   `os` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '操作系统',
   PRIMARY KEY (`id`) USING BTREE
 ) ENGINE = InnoDB AUTO_INCREMENT = 1215270143660377758 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '系统日志' ROW_FORMAT = Dynamic;

 -- ----------------------------
 -- Table structure for t_menu
 -- ----------------------------
 DROP TABLE IF EXISTS `t_menu`;
 CREATE TABLE `t_menu`  (
   `id` bigint(19) NOT NULL COMMENT '资源ID',
   `menu_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '资源名称',
   `type` int(1) NOT NULL DEFAULT 0 COMMENT '菜单类型：0目录，1菜单，2按钮',
   `parent_id` bigint(20) NOT NULL DEFAULT 0 COMMENT 'parentId',
   `parent_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '父菜单名称',
   `sort` tinyint(4) NULL DEFAULT 0 COMMENT '排序',
   `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
   `permission` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '权限标识',
   `url` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '链接',
   `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
   `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
   `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '是否删除',
   `icon` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '菜单图标',
   `component` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '路由组件',
   `hidden` tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否隐藏',
   PRIMARY KEY (`id`) USING BTREE
 ) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '系统菜单' ROW_FORMAT = Dynamic;

 -- ----------------------------
 -- Table structure for t_role
 -- ----------------------------
 DROP TABLE IF EXISTS `t_role`;
 CREATE TABLE `t_role`  (
   `id` bigint(19) NOT NULL COMMENT 'ID',
   `role_code` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色名称',
   `role_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '描述',
   `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
   `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
   `create_by` bigint(19) NULL DEFAULT NULL COMMENT '创建人',
   `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '是否删除',
   `remark` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
   PRIMARY KEY (`id`) USING BTREE
 ) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '系统角色' ROW_FORMAT = Dynamic;

 -- ----------------------------
 -- Table structure for t_role_menu
 -- ----------------------------
 DROP TABLE IF EXISTS `t_role_menu`;
 CREATE TABLE `t_role_menu`  (
   `id` int(19) NOT NULL AUTO_INCREMENT COMMENT 'ID',
   `role_id` bigint(19) NOT NULL COMMENT '角色ID',
   `menu_id` bigint(19) NOT NULL COMMENT '菜单ID',
   PRIMARY KEY (`id`) USING BTREE
 ) ENGINE = InnoDB AUTO_INCREMENT = 30 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色菜单表' ROW_FORMAT = Dynamic;

 -- ----------------------------
 -- Table structure for t_tag
 -- ----------------------------
 DROP TABLE IF EXISTS `t_tag`;
 CREATE TABLE `t_tag`  (
   `id` bigint(19) NOT NULL,
   `tag_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '标签名称',
   `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
   `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
   PRIMARY KEY (`id`) USING BTREE
 ) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '标签表' ROW_FORMAT = Dynamic;

 -- ----------------------------
 -- Table structure for t_tag_article
 -- ----------------------------
 DROP TABLE IF EXISTS `t_tag_article`;
 CREATE TABLE `t_tag_article`  (
   `id` bigint(19) NOT NULL AUTO_INCREMENT COMMENT 'id',
   `tag_id` bigint(19) NOT NULL COMMENT '标签id',
   `article_id` bigint(19) NOT NULL COMMENT '文章id',
   PRIMARY KEY (`id`) USING BTREE
 ) ENGINE = InnoDB AUTO_INCREMENT = 50 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '文章标签表' ROW_FORMAT = Dynamic;

 -- ----------------------------
 -- Table structure for t_user
 -- ----------------------------
 DROP TABLE IF EXISTS `t_user`;
 CREATE TABLE `t_user`  (
   `id` bigint(19) NOT NULL COMMENT '用户ID',
   `user_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名称',
   `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '邮箱',
   `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码',
   `salt` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '加密盐',
   `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '头像',
   `enable` tinyint(1) NOT NULL DEFAULT 1 COMMENT '状态：1：正常，0：禁用',
   `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
   `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '是否删除',
   `is_locked` tinyint(1) NULL DEFAULT 0 COMMENT '是否锁定',
   `login_time` datetime(0) NULL DEFAULT NULL COMMENT '登陆时间',
   `create_time` datetime(0) NOT NULL COMMENT '创建时间',
   `update_time` datetime(0) NOT NULL COMMENT '修改时间',
   PRIMARY KEY (`id`) USING BTREE
 ) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '系统用户' ROW_FORMAT = Dynamic;

 -- ----------------------------
 -- Table structure for t_user_role
 -- ----------------------------
 DROP TABLE IF EXISTS `t_user_role`;
 CREATE TABLE `t_user_role`  (
   `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
   `user_id` bigint(19) NOT NULL COMMENT '用户ID',
   `role_id` bigint(19) NOT NULL COMMENT '角色ID',
   PRIMARY KEY (`id`) USING BTREE
 ) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户角色' ROW_FORMAT = Dynamic;

 -- 初始化数据

-- 文章
 INSERT INTO `t_article` VALUES (1215290345965985793, '关于本站', ' 关于本站  简介这个博客系统是为了将学习的技术整合在一起，巩固基础的练习项目当然也作为本人的个人博客用于记录工作中的问题和学习新技术的笔记  項目的技术选型 SpringBoot MyBatis and MyBatisPlus SpringMVC MySQL.8.0 Shiro 安全框架 Redis 缓存 API 文档 Swagger lombok 消息队列 RabbitMQ 搜索引擎 Elas', '### 关于本站\n\n> 😰 简介\n\n这个博客系统是为了将学习的技术整合在一起，巩固基础的练习项目。当然也作为本人的个人博客用于记录工作中的问题和学习新技术的笔记。\n\n> 👍 項目的技术选型\n\n- Spring-Boot\n- MyBatis and MyBatis-Plus\n- SpringMVC\n- MySQL.8.0\n- Shiro 安全框架\n- Redis 缓存\n- API 文档 Swagger\n- lombok\n- 消息队列 RabbitMQ\n- 搜索引擎 Elasticsearch\n- Docker 化部署\n- 持续集成 Jenkins\n- Markdown 使用Vditor \n\n> TODOList\n\n- [X] 完成框架的搭建\n- [X] Shiro 完成整合\n- [X] Redis 完成整合\n- [X] SwaggerUI 完成整合\n- [X] 配置打包部署\n- [X] 用户管理\n- [X] 角色管理\n- [X] 菜单管理\n- [X] 文章管理\n- [X] 标签管理\n- [X] 分类管理\n- [X] 加入文件上传功能(只实现上传到本地)\n- [ ] ES 搜索文章\n- [ ] 使用 Redis 缓存数据\n- [ ] 文章备份\n- [ ] 安全问题防范\n- [ ] 整理整个搭建文档\n\n> 👍 感谢以下项目\n\n[vue-element-admin](https://github.com/PanJiaChen/vue-element-admin)\n[Vditor Markdown](https://hacpai.com/article/1549638745630)\n[Flog 博客模板](https://gitee.com/fengziy/Fblog)\n\n\n> ❤️ 联系\n-  Email: 1432517356@qq.com\n-  Github: \n-  Gitee:\n-  掘金:\n- 语雀:\n', '关于本站,关于博主', 0, 1, 'hdj@admin.cn', '2020-01-09 23:12:52', 1, 0, '2020-01-09 23:13:21', NULL, 0, '2020-01-10 00:07:41', 0, 0, 1, 1215266189052461057, 0, 'http://192.168.43.122:8181/upload/1/13/019547be-b4aa-44fa-bb95-df995a6b3951_2020-01-09.jpg', '');


-- 分离

INSERT INTO `t_category` VALUES (1215262901141749762, '后台', '2020-01-09 21:23:49', '', '2020-01-09 21:23:49');
INSERT INTO `t_category` VALUES (1215262935740563457, '前端', '2020-01-09 21:23:57', '', '2020-01-09 21:23:57');
INSERT INTO `t_category` VALUES (1215262961250320386, 'Linux', '2020-01-09 21:24:03', '', '2020-01-09 21:24:03');
INSERT INTO `t_category` VALUES (1215263075968729089, '框架', '2020-01-09 21:24:30', '', '2020-01-09 21:24:30');
INSERT INTO `t_category` VALUES (1215266189052461057, '关于本站', '2020-01-09 21:36:53', '', '2020-01-09 21:36:53');

-- 菜单
INSERT INTO `t_menu` VALUES (1190952764063309825, '系统管理', 0, 0, '根目录', 1, '', '', '', '2019-11-03 19:24:00', '2020-01-05 21:09:02', 0, 'sys', 'layout/index', 0);
INSERT INTO `t_menu` VALUES (1190953725242597378, '文章管理', 0, 0, '根目录', 0, '', '', '', '2019-11-03 19:27:50', '2020-01-05 21:10:03', 0, 'article', 'layout/index', 0);
INSERT INTO `t_menu` VALUES (1190953983393619969, '系统监控', 0, 0, '根目录', 2, '', '', '', '2019-11-03 19:28:51', '2020-01-05 21:10:33', 0, 'Monitor', 'layout/index', 0);
INSERT INTO `t_menu` VALUES (1190954172489621505, '系统配置', 0, 0, '根目录', 3, '', '', '', '2019-11-03 19:29:36', '2019-11-03 19:29:36', 1, 'example', 'layout/index', 0);
INSERT INTO `t_menu` VALUES (1190954238612824065, '系统工具', 0, 0, '根目录', 99, '', '', '', '2019-11-03 19:29:52', '2020-01-05 21:11:01', 0, 'tool', 'layout/index', 0);
INSERT INTO `t_menu` VALUES (1190955569503232002, '菜单管理', 1, 1190952764063309825, '根目录', 1, '', 'admin:sys:menu', '/sys/menu', '2019-11-03 19:35:09', '2020-01-05 21:09:11', 0, 'menu', 'views/sys/menu/index', 0);
INSERT INTO `t_menu` VALUES (1190956260263157762, '角色管理', 1, 1190952764063309825, '根目录', 2, '', 'admin:sys:role', '/sys/role', '2019-11-03 19:37:54', '2020-01-05 21:09:37', 0, 'role', 'views/sys/role/index', 0);
INSERT INTO `t_menu` VALUES (1190956520771379201, '管理员列表', 1, 1190952764063309825, '根目录', 0, '', 'admin:sys:user', '/sys/user', '2019-11-03 19:38:56', '2020-01-05 21:09:44', 0, 'user', 'views/sys/user/index', 0);
INSERT INTO `t_menu` VALUES (1190956782248484866, '个人中心', 1, 1190952764063309825, '根目录', 99, '', 'sys:user:profile', '/profile', '2019-11-03 19:39:58', '2020-01-10 21:25:47', 0, 'user', 'views/sys/profile/index', 1);
INSERT INTO `t_menu` VALUES (1190961402907701250, '菜单搜索', 2, 1190955569503232002, '根目录', 99, '', 'sys:menu:search', NULL, '2019-11-03 19:58:20', '2020-01-10 21:24:51', 0, 'search', 'layout/index', 1);
INSERT INTO `t_menu` VALUES (1190963023809081346, '添加菜单', 2, 1190955569503232002, '根目录', 99, '', 'sys:menu:add', NULL, '2019-11-03 20:04:47', '2020-01-10 21:24:58', 0, 'add', 'layout/index', 1);
INSERT INTO `t_menu` VALUES (1190963114494128130, '编辑菜单', 2, 1190955569503232002, '根目录', 99, '', 'sys:menu:edit', NULL, '2019-11-03 20:05:08', '2020-01-10 21:25:13', 0, 'edit', 'layout/index', 1);
INSERT INTO `t_menu` VALUES (1190963313421578241, '菜单详情', 2, 1190955569503232002, '根目录', 99, '', 'sys:menu:info', NULL, '2019-11-03 20:05:56', '2020-01-10 21:25:23', 0, 'menu', 'layout/index', 1);
INSERT INTO `t_menu` VALUES (1190963700576808962, '文章列表', 1, 1190953725242597378, '根目录', 99, '', 'admin:article:list', '/articles', '2019-11-03 20:07:28', '2020-01-06 20:53:35', 0, 'article', 'views/article/article-list', 0);
INSERT INTO `t_menu` VALUES (1190964240761221122, '创建文章', 1, 1190953725242597378, '根目录', 99, '', 'admin:article:add', '/article/create', '2019-11-03 20:09:37', '2020-01-06 20:52:40', 0, 'create', 'views/article/article-create', 0);
INSERT INTO `t_menu` VALUES (1190964656332861442, '文章编辑', 1, 1190953725242597378, '根目录', 99, '', 'admin:article:edit', '/article/edit/:id', '2019-11-03 20:11:16', '2020-01-06 20:52:50', 0, 'editor', 'views/article/article-edit', 1);
INSERT INTO `t_menu` VALUES (1190965214737330177, '文章分类', 1, 1190953725242597378, '根目录', 99, '', 'admin:article:category', '/article/category', '2019-11-03 20:13:29', '2020-01-09 21:13:33', 0, 'category', 'views/article/category', 0);
INSERT INTO `t_menu` VALUES (1190965536222343170, '文章标签', 1, 1190953725242597378, '根目录', 99, '', 'admin:article:tag', '/article/tag', '2019-11-03 20:14:46', '2020-01-09 21:13:42', 0, 'tag', 'views/article/tags', 0);
INSERT INTO `t_menu` VALUES (1190966241939156993, '文章删除', 2, 1190963700576808962, '根目录', 99, '', 'article:delete', NULL, '2019-11-03 20:17:34', '2020-01-10 21:27:00', 0, 'delete', 'layout/index', 1);
INSERT INTO `t_menu` VALUES (1190966783444774913, '文章详情', 2, 1190963700576808962, '根目录', 99, '', 'article:info', NULL, '2019-11-03 20:19:43', '2020-01-10 21:27:13', 0, 'article', 'layout/index', 1);
INSERT INTO `t_menu` VALUES (1190967812462735362, '角色搜索', 2, 1190956260263157762, '根目录', 99, '', 'sys:role:search', NULL, '2019-11-03 20:23:48', '2020-01-10 21:24:07', 0, 'search', 'layout/index', 1);
INSERT INTO `t_menu` VALUES (1190967915520978946, '添加角色', 2, 1190956260263157762, '根目录', 99, '', 'sys:role:add', NULL, '2019-11-03 20:24:13', '2020-01-10 21:24:14', 0, 'add', 'layout/index', 1);
INSERT INTO `t_menu` VALUES (1190968025067810817, '角色编辑', 2, 1190956260263157762, '根目录', 99, '', 'sys:role:edit', NULL, '2019-11-03 20:24:39', '2020-01-10 21:24:28', 0, 'edit', 'layout/index', 1);
INSERT INTO `t_menu` VALUES (1190968129396928514, '角色详情', 2, 1190956260263157762, '根目录', 99, '', 'sys:role:info', NULL, '2019-11-03 20:25:04', '2020-01-10 21:24:37', 0, 'role', 'layout/index', 1);
INSERT INTO `t_menu` VALUES (1190968410293661697, '添加', 2, 1190956520771379201, '根目录', 99, '', 'sys:user:add', NULL, '2019-11-03 20:26:11', '2020-01-10 21:23:51', 0, 'add', 'layout/index', 1);
INSERT INTO `t_menu` VALUES (1190968508327129090, '编辑', 2, 1190956520771379201, '根目录', 99, '', 'sys:user:edit', NULL, '2019-11-03 20:26:34', '2020-01-10 21:23:40', 0, 'edit', 'layout/index', 1);
INSERT INTO `t_menu` VALUES (1190968587712720897, '详情', 2, 1190956520771379201, '根目录', 99, '', 'sys:user:info', NULL, '2019-11-03 20:26:53', '2020-01-10 21:23:28', 0, 'user', 'layout/index', 1);
INSERT INTO `t_menu` VALUES (1190970158727032834, 'SQL监控', 1, 1190953983393619969, '根目录', 99, '', '', '/druid', '2019-11-03 20:33:08', '2020-01-05 21:10:53', 0, 'sql', 'views/monitor/sql/index', 0);
INSERT INTO `t_menu` VALUES (1190970848706818049, '系统日志', 1, 1190953983393619969, '根目录', 99, '', 'admin:monitor:log', '/monitor/log', '2019-11-03 20:35:52', '2020-01-05 21:10:45', 0, 'log', 'views/monitor/log/index', 0);
INSERT INTO `t_menu` VALUES (1190971465814765570, 'API', 1, 1190953983393619969, '根目录', 99, '', '', '/swagger-ui.html', '2019-11-03 20:38:19', '2020-01-05 21:10:40', 0, 'swagger', 'views/monitor/api/index', 0);
INSERT INTO `t_menu` VALUES (1213806193341001729, '图库', 1, 1190954238612824065, '根目录', 99, '', 'sys-tool:picture:*', '/picture', '2020-01-05 20:55:23', '2020-01-05 20:56:22', 0, 'picture', 'views/sys-tool/picture/index', 0);
INSERT INTO `t_menu` VALUES (1215624999269015554, '搜索', 2, 1190956520771379201, '根目录', 99, '', 'sys:user:search', NULL, '2020-01-10 21:22:40', '2020-01-10 21:22:40', 0, 'search', 'layout/index', 1);
INSERT INTO `t_menu` VALUES (1215626296789540865, '文章搜索', 2, 1190963700576808962, '根目录', 99, '', 'article:search', NULL, '2020-01-10 21:27:49', '2020-01-10 21:27:49', 0, 'search', 'layout/index', 1);
INSERT INTO `t_menu` VALUES (1215626547663446017, '分类添加', 2, 1190965214737330177, '根目录', 99, '', 'article:category:add', NULL, '2020-01-10 21:28:49', '2020-01-10 21:28:49', 0, 'add', 'layout/index', 1);
INSERT INTO `t_menu` VALUES (1215626696632541186, '文章分类删除', 2, 1190965214737330177, '根目录', 99, '', 'article:category:delete', NULL, '2020-01-10 21:29:24', '2020-01-10 21:29:24', 0, 'delete', 'layout/index', 1);
INSERT INTO `t_menu` VALUES (1215626845844905985, '文章分类编辑', 2, 1190965214737330177, '根目录', 99, '', 'article:category:edit', NULL, '2020-01-10 21:30:00', '2020-01-10 21:30:00', 0, 'edit', 'layout/index', 1);
INSERT INTO `t_menu` VALUES (1215627034445979649, '分类搜索', 2, 1190965214737330177, '根目录', 99, '', 'article:category:search', NULL, '2020-01-10 21:30:45', '2020-01-10 21:30:45', 0, 'search', 'layout/index', 1);
INSERT INTO `t_menu` VALUES (1215627152536608769, '标签添加', 2, 1190965536222343170, '根目录', 99, '', 'article:tag:add', NULL, '2020-01-10 21:31:13', '2020-01-10 21:31:13', 0, 'add', 'layout/index', 1);
INSERT INTO `t_menu` VALUES (1215627251610263554, '标签删除', 2, 1190965536222343170, '根目录', 99, '', 'article:tag:delete', NULL, '2020-01-10 21:31:37', '2020-01-10 21:31:37', 0, 'delete', 'layout/index', 1);
INSERT INTO `t_menu` VALUES (1215627366181871617, '标签编辑', 2, 1190965536222343170, '根目录', 99, '', 'article:tag:edit', NULL, '2020-01-10 21:32:04', '2020-01-10 21:32:04', 0, 'edit', 'layout/index', 1);
INSERT INTO `t_menu` VALUES (1215627489569906689, '标签搜索', 2, 1190965536222343170, '根目录', 99, '', 'article:tag:search', NULL, '2020-01-10 21:32:33', '2020-01-10 21:32:33', 0, 'search', 'layout/index', 1);


-- 标签
INSERT INTO `t_tag` VALUES (1215290346028900353, '关于本站', '2020-01-09 23:12:52', '2020-01-09 23:12:52');
INSERT INTO `t_tag` VALUES (1215290346041483265, '关于博主', '2020-01-09 23:12:52', '2020-01-09 23:12:52');

-- 标签文章
INSERT INTO `t_tag_article` VALUES (32, 1215290346028900353, 1215290345965985793);
INSERT INTO `t_tag_article` VALUES (33, 1215290346041483265, 1215290345965985793);

-- 用户
INSERT INTO `t_user` VALUES (0, 'hdj', 'hdj@admin.cn', '2fb59eba241926beeb7ce3096ba3b5442d3da1f05fe0b3146dacedb472002393', '9af1d92077b5d384fc9401fea35e0c2c', 'http://192.168.43.122:8181/upload/2/4/e6fea537-facd-4117-ad69-229b454016b3_2020-01-07.jpg', 1, '1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111', 0, 0, '2020-01-10 21:38:28', '2019-07-10 10:48:04', '2020-01-07 23:21:53');

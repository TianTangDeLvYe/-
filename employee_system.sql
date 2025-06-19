# Host: localhost  (Version 5.7.30-log)
# Date: 2025-06-18 09:30:09
# Generator: MySQL-Front 6.1  (Build 1.26)


#
# Structure for table "t_attendance"
#

DROP TABLE IF EXISTS `t_attendance`;
CREATE TABLE `t_attendance` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '考勤记录ID',
  `emp_id` int(11) NOT NULL COMMENT '员工ID(外键: 关联t_employee.emp_id)',
  `clock_time` datetime NOT NULL COMMENT '打卡时间',
  `clock_type` char(1) NOT NULL COMMENT '1-上班，2-下班',
  `location` varchar(100) DEFAULT NULL COMMENT '打卡位置(模拟)',
  `status` char(1) NOT NULL COMMENT '0-正常，1-迟到，2-早退',
  PRIMARY KEY (`id`),
  KEY `idx_attendance_emp_time` (`emp_id`,`clock_time`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='考勤记录表';

#
# Data for table "t_attendance"
#

INSERT INTO `t_attendance` VALUES (1,1,'2025-06-01 08:30:00','1','总部大楼','0'),(2,1,'2025-06-01 18:00:00','2','总部大楼','0'),(3,2,'2025-06-01 09:10:00','1','研发部','1'),(4,2,'2025-06-01 18:30:00','2','研发部','0'),(5,3,'2025-06-02 08:45:00','1','开发组','0'),(6,3,'2025-06-02 17:45:00','2','开发组','2'),(7,4,'2025-06-03 09:05:00','1','市场部','1'),(8,4,'2025-06-03 18:10:00','2','市场部','0');

#
# Structure for table "t_department"
#

DROP TABLE IF EXISTS `t_department`;
CREATE TABLE `t_department` (
  `dept_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '部门唯一标识',
  `dept_name` varchar(50) NOT NULL COMMENT '部门名称',
  `parent_id` int(11) NOT NULL DEFAULT '0' COMMENT '父部门ID(外键: 关联自身dept_id，顶级部门为0)',
  `order_num` int(4) NOT NULL DEFAULT '0' COMMENT '排序号(升序展示)',
  `status` char(1) NOT NULL DEFAULT '1' COMMENT '状态(1-正常，0-停用)',
  `level_depth` int(4) NOT NULL DEFAULT '1' COMMENT '层级深度(父部门level_depth+1)',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`dept_id`),
  UNIQUE KEY `uk_dept_name_parent` (`dept_name`,`parent_id`),
  KEY `idx_dept_parent_status` (`parent_id`,`status`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='部门架构表';

#
# Data for table "t_department"
#

INSERT INTO `t_department` VALUES (1,'总公司',0,1,'1',1,'2025-06-16 16:43:03'),(2,'研发部',1,1,'1',2,'2025-06-16 16:43:03'),(3,'开发组',2,1,'1',3,'2025-06-16 16:43:03'),(4,'市场部',1,2,'1',2,'2025-06-16 16:43:03');

#
# Structure for table "t_employee"
#

DROP TABLE IF EXISTS `t_employee`;
CREATE TABLE `t_employee` (
  `emp_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '员工唯一标识(外键: 无约束，关联部门表和岗位表)',
  `emp_name` varchar(50) NOT NULL COMMENT '员工真实姓名',
  `dept_id` int(11) NOT NULL COMMENT '所属部门ID(外键: 关联t_department.dept_id)',
  `post_id` int(11) NOT NULL COMMENT '岗位ID(外键: 关联t_post.post_id)',
  `login_name` varchar(30) NOT NULL COMMENT '登录账号',
  `password` varchar(100) NOT NULL COMMENT 'MD5+盐值加密密码',
  `phone` varchar(11) NOT NULL COMMENT '手机号码',
  `create_time` datetime NOT NULL COMMENT '入职时间',
  `salt` varchar(32) NOT NULL COMMENT '盐值加密',
  PRIMARY KEY (`emp_id`),
  UNIQUE KEY `login_name` (`login_name`),
  UNIQUE KEY `phone` (`phone`),
  KEY `idx_employee_dept` (`dept_id`),
  KEY `idx_employee_post` (`post_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='员工信息表';

#
# Data for table "t_employee"
#

INSERT INTO `t_employee` VALUES (1,'张三',1,1,'zhangsan','e10adc3949ba59abbe56e057f20f883e','13800138001','2023-01-10 00:00:00','salt123'),(2,'李四',2,3,'lisi','e10adc3949ba59abbe56e057f20f883e','13800138002','2023-02-15 00:00:00','salt456'),(3,'王五',3,4,'wangwu','e10adc3949ba59abbe56e057f20f883e','13800138003','2023-03-20 00:00:00','salt789'),(4,'赵六',4,5,'zhaoliu','e10adc3949ba59abbe56e057f20f883e','13800138004','2023-04-10 00:00:00','salt012');

#
# Structure for table "t_leave_apply"
#

DROP TABLE IF EXISTS `t_leave_apply`;
CREATE TABLE `t_leave_apply` (
  `apply_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '请假申请ID',
  `emp_id` int(11) NOT NULL COMMENT '申请人ID(外键: 关联t_employee.emp_id)',
  `dept_id` int(11) NOT NULL COMMENT '申请人部门ID(外键: 关联t_department.dept_id)',
  `manager_id` int(11) DEFAULT NULL COMMENT '审批人ID(部门经理/人事)',
  `leave_type` varchar(20) NOT NULL COMMENT '请假类型(事假/病假等)',
  `start_time` datetime NOT NULL COMMENT '开始时间',
  `end_time` datetime NOT NULL COMMENT '结束时间',
  `days` decimal(3,1) NOT NULL COMMENT '请假天数',
  `reason` varchar(200) NOT NULL COMMENT '请假事由',
  `status` char(1) NOT NULL COMMENT '状态(0-待审批，1-通过，2-拒绝，3-已撤销)',
  `approve_time` datetime DEFAULT NULL COMMENT '审批时间',
  PRIMARY KEY (`apply_id`),
  KEY `idx_leave_emp_status` (`emp_id`,`status`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='请假申请表';

#
# Data for table "t_leave_apply"
#

INSERT INTO `t_leave_apply` VALUES (1,3,3,2,'事假','2025-06-05 00:00:00','2025-06-05 23:59:59',1.0,'家中有事','1',NULL),(2,4,4,1,'病假','2025-06-07 00:00:00','2025-06-08 23:59:59',2.0,'身体不适','0',NULL);

#
# Structure for table "t_post"
#

DROP TABLE IF EXISTS `t_post`;
CREATE TABLE `t_post` (
  `post_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '岗位唯一标识',
  `post_code` varchar(20) NOT NULL COMMENT '岗位编码(如"CEO")',
  `post_name` varchar(50) NOT NULL COMMENT '岗位名称',
  `post_level` varchar(20) NOT NULL COMMENT '岗位级别(如"高级")',
  `salary_range` varchar(50) DEFAULT NULL COMMENT '薪资范围(如"8k-15k")',
  `description` varchar(200) DEFAULT NULL COMMENT '岗位描述',
  `status` char(1) NOT NULL DEFAULT '1' COMMENT '状态(1-正常，0-停用)',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `dept_id` int(11) DEFAULT NULL COMMENT '所属部门ID(外键: 关联t_department.dept_id)',
  PRIMARY KEY (`post_id`),
  UNIQUE KEY `post_code` (`post_code`),
  KEY `idx_post_dept` (`dept_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='岗位信息表';

#
# Data for table "t_post"
#

INSERT INTO `t_post` VALUES (1,'CEO','董事长','管理岗','50k-100k','公司最高管理者','1','2025-06-16 16:43:03',1),(2,'HR001','人事经理','管理岗','15k-25k','负责人事管理','1','2025-06-16 16:43:03',1),(3,'DEV001','开发经理','管理岗','20k-30k','研发部负责人','1','2025-06-16 16:43:03',2),(4,'DEV002','高级开发','技术岗','15k-25k','核心代码开发','1','2025-06-16 16:43:03',3),(5,'MKT001','市场专员','业务岗','8k-15k','市场推广','1','2025-06-16 16:43:03',4);

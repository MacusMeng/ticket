CREATE TABLE `tickets` (
  `id`          VARCHAR(36)  NOT NULL
  COMMENT '标识符',
  `number`      MEDIUMINT    NOT NULL AUTO_INCREMENT
  COMMENT '工单号',
  `title`       VARCHAR(255) NOT NULL
  COMMENT '标题',
  `content`     TEXT         NOT NULL
  COMMENT '内容',
  `module`      VARCHAR(100) NOT NULL
  COMMENT '模块',
  `status`      VARCHAR(50)  NOT NULL
  COMMENT '状态',
  `user_id`     VARCHAR(100) NOT NULL
  COMMENT '提交人标识符',
  `username`    VARCHAR(255) NOT NULL
  COMMENT '提交人用户名',
  `user_email`  VARCHAR(255) NOT NULL
  COMMENT '提交人邮箱',
  `user_mobile` VARCHAR(100)
  COMMENT '提交人手机号',
  `admin_id`    VARCHAR(36)
  COMMENT '认领人标识符',
  `version`     INT          NOT NULL
  COMMENT '版本',
  `created_at`  DATETIME     NOT NULL
  COMMENT '创建时间',
  `updated_at`  DATETIME     NOT NULL
  COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `index_number` (`number`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  DEFAULT COLLATE = utf8_general_ci
  COMMENT = '工单表';
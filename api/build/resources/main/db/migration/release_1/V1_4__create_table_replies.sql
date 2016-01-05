CREATE TABLE `replies` (
  `id`         VARCHAR(36)  NOT NULL
  COMMENT '标识符',
  `content`    TEXT         NOT NULL
  COMMENT '内容',
  `ticket_id`  VARCHAR(36)  NOT NULL
  COMMENT '工单标识符',
  `user_id`    VARCHAR(100)
  COMMENT '用户标识符',
  `username`   VARCHAR(255)
  COMMENT '用户名',
  `admin_id`   VARCHAR(36)
  COMMENT '管理员标识符',
  `created_at` DATETIME     NOT NULL
  COMMENT '创建时间',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  DEFAULT COLLATE = utf8_general_ci
  COMMENT = '回复表';
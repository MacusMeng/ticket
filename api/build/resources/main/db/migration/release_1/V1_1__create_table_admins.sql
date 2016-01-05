CREATE TABLE `admins` (
  `id`         VARCHAR(36)  NOT NULL
  COMMENT '标识符',
  `username`   VARCHAR(100) NOT NULL
  COMMENT '用户名',
  `password`   VARCHAR(100) NOT NULL
  COMMENT '密码',
  `email`      VARCHAR(100) NOT NULL
  COMMENT '邮箱',
  `mobile`     VARCHAR(100)
  COMMENT '手机号',
  `version`    INT          NOT NULL
  COMMENT '版本',
  `created_at` DATETIME     NOT NULL
  COMMENT '创建时间',
  `updated_at` DATETIME     NOT NULL
  COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE (username),
  UNIQUE (email),
  UNIQUE (mobile)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  DEFAULT COLLATE = utf8_general_ci
  COMMENT = '管理员';
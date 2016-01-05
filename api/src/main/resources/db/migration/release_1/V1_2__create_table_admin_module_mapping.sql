CREATE TABLE `admin_module_mapping` (
  `id`       VARCHAR(36)  NOT NULL
  COMMENT '标识符',
  `admin_id` VARCHAR(36)  NOT NULL
  COMMENT '管理员标识符',
  `module`   VARCHAR(100) NOT NULL
  COMMENT '模块',
  CONSTRAINT un_mapping UNIQUE (admin_id, module),
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  DEFAULT COLLATE = utf8_general_ci
  COMMENT = '管理员处理模块映射';
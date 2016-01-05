CREATE TABLE `notifications` (
  `id`         VARCHAR(36)  NOT NULL
  COMMENT '标识符',
  `ticket_id`  VARCHAR(36)  NOT NULL
  COMMENT '工单标识符',
  `category`   VARCHAR(100) NOT NULL
  COMMENT '通知种类',
  `type`       VARCHAR(100) NOT NULL
  COMMENT '通知类型',
  `status`     VARCHAR(50)  NOT NULL
  COMMENT '状态',
  `created_at` DATETIME     NOT NULL
  COMMENT '创建时间',
  `updated_at` DATETIME     NOT NULL
  COMMENT '更新时间',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  DEFAULT COLLATE = utf8_general_ci
  COMMENT = '通知表';
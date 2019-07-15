CREATE SCHEMA IF NOT EXISTS task_manager;
USE task_manager;

CREATE TABLE `projects` (
  `id` varchar(36) NOT NULL,
  `name` varchar(36) DEFAULT NULL,
  `description` varchar(250) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `tasks` (
  `id` varchar(36) NOT NULL,
  `name` varchar(36) DEFAULT NULL,
  `description` varchar(250) DEFAULT NULL,
  `priority` INT DEFAULT NULL,
  `status` INT DEFAULT NULL,
  `project_id` varchar(36) NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`project_id`)
        REFERENCES projects(`id`)
        ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
-- ---------- CREATE TABLES ----------
CREATE TABLE IF NOT EXISTS `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `experience` bigint DEFAULT NULL,
  `gold` bigint DEFAULT NULL,
  `party_level` int DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `profile_picture_url` varchar(255) DEFAULT NULL,
  `username` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY (`email`),
  UNIQUE KEY (`username`)
);

CREATE TABLE IF NOT EXISTS `roles` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY (`name`)
);

CREATE TABLE IF NOT EXISTS `users_roles` (
  `user_id` bigint NOT NULL,
  `roles_id` bigint NOT NULL,
  PRIMARY KEY (`user_id`,`roles_id`),
  FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  FOREIGN KEY (`roles_id`) REFERENCES `roles` (`id`)
);

-- ---------- POPULATE WITH ADMIN ----------
-- username=admin; password="ironhack-admin";
INSERT IGNORE INTO `users`(`id`, `email`, `experience`, `gold`, `party_level`, `password`, `profile_picture_url`, `username`)
VALUES
       ('1', 'admin@admin', '0', '0', '0', '$2a$10$x.M9SLA4jm.bUNxgpahDl.54B8PToW2AB/RnqsLLBUrlghBl9053S', '', 'admin');

INSERT IGNORE INTO `roles`(`id`, `name`)
VALUES
       ('1', 'ADMIN'),
       ('2', 'USER');

INSERT IGNORE INTO `users_roles`(`user_id`, `roles_id`)
VALUES
       ('1', '1');

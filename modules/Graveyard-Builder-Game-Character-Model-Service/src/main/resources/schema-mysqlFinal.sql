-- ---------- CREATE TABLES ----------
CREATE TABLE IF NOT EXISTS `characters` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `accuracy` int DEFAULT NULL,
    `current_health` int DEFAULT NULL,
    `current_mana` int DEFAULT NULL,
    `current_stamina` int DEFAULT NULL,
    `death_time` datetime(6) DEFAULT NULL,
    `experience` bigint DEFAULT NULL,
    `intelligence` int DEFAULT NULL,
    `is_alive` bit(1) DEFAULT NULL,
    `level` int DEFAULT NULL,
    `max_health` int DEFAULT NULL,
    `max_mana` int DEFAULT NULL,
    `max_stamina` int DEFAULT NULL,
    `name` varchar(255) DEFAULT NULL,
    `passive_chance` double DEFAULT NULL,
    `picture_url` varchar(255) DEFAULT NULL,
    `strength` int DEFAULT NULL,
    `type` int DEFAULT NULL,
    `user_id` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
);
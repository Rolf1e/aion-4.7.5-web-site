CREATE TABLE `users` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `username` varchar(255) COLLATE armscii8_bin NOT NULL,
    `password` varchar(45) COLLATE armscii8_bin NOT NULL,
    `token` varchar(45) COLLATE armscii8_bin DEFAULT NULL,
    `amount_web_money` int(11) DEFAULT NULL,
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=armscii8 COLLATE=armscii8_bin

CREATE TABLE `player_shop` (
    `playerid` int(11) NOT NULL,
    `itemid` int(11) NOT NULL,
    `itemcount` int(11) DEFAULT NULL
    ) ENGINE=InnoDB DEFAULT CHARSET=armscii8 COLLATE=armscii8_bin

CREATE TABLE `item` (
    `id` int(11) NOT NULL,
    `name` varchar(255) COLLATE armscii8_bin NOT NULL,
    `category` varchar(255) COLLATE armscii8_bin DEFAULT NULL,
    `description` varchar(255) COLLATE armscii8_bin DEFAULT NULL,
    `price` int(11) NOT NULL,
    `pathtoimage` varchar(255) COLLATE armscii8_bin NOT NULL,
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=armscii8 COLLATE=armscii8_bin

CREATE TABLE `Product` (
  `id` int(11) NOT NULL,
  `productId` int(11) NOT NULL,
     `name` varchar(20) NOT NULL,
  `type` varchar(1000) DEFAULT NULL,
  `cost` varchar(50) NOT NULL,
  `country`varchar(50) NOT NULL,
 
  `lastUpdatedTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
)

ALTER TABLE `Product` ADD PRIMARY KEY( `id`);

ALTER TABLE `Product` CHANGE `productId` `productId` VARCHAR(11) NOT NULL;
ALTER TABLE `Product` ADD `set` VARCHAR(10) NULL DEFAULT NULL AFTER `country`;


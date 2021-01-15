
CREATE TABLE `product` (
  `id` int(11) NOT NULL,
  `product` varchar(200) NOT NULL,
  `category` varchar(1000) DEFAULT NULL,
  `price` float NOT NULL,
  `currency` varchar(20) NOT NULL,
  `rating` float DEFAULT NULL,
  `origin` varchar(50) DEFAULT NULL,
  `inventory` int(5) NOT NULL,
  `arrival` varchar(100) DEFAULT NULL,
  `lastUpdatedTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

ALTER TABLE `product`
  ADD PRIMARY KEY (`id`);

ALTER TABLE `product`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;


CREATE TABLE `ingredienti` (
  `ingrediente_id` int(11) NOT NULL AUTO_INCREMENT,
  `nome_ingrediente` varchar(50) NOT NULL,
  PRIMARY KEY (`ingrediente_id`)) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;


CREATE TABLE `ricette` (
  `ricetta_id` int(11) NOT NULL AUTO_INCREMENT,
  `titolo` varchar(50)  NOT NULL,
  `tipo_piatto` int(11),
  `ingrediente_princ` int(11),
  `nr_persone` int(11),
  `note` VARCHAR(240),
  `preparazione`TEXT  NOT NULL,
  PRIMARY KEY (`ricetta_id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;


CREATE TABLE `ingredienti_ricette` (
  `ingrediente_ricette_id` int(11) NOT NULL AUTO_INCREMENT,
  `ingrediente_id` int(11) NOT NULL,
  `ricetta_id` int(11) NOT NULL,
  `quantita` int(10),
  `unita_misura` varchar(24),
  PRIMARY KEY (`ingrediente_ricette_id`),
  KEY `ricetta_id` (`ricetta_id`),
  CONSTRAINT `ingrediente_id_1` 
   FOREIGN KEY (`ingrediente_id`) REFERENCES `ingredienti` (`ingrediente_id`),
  CONSTRAINT `ingrediente_id_2` 
   FOREIGN KEY (`ricetta_id`) REFERENCES `ricette` (`ricetta_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE ricette ADD CONSTRAINT `ricette_1`
  FOREIGN KEY(`ingrediente_princ`) REFERENCES 
  `ingredienti` (`ingrediente_id`);
  
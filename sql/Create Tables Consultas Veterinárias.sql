CREATE TABLE `animal` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(100) DEFAULT NULL,
  `especie` varchar(50) DEFAULT NULL,
  `raca` varchar(50) DEFAULT NULL,
  `idade` int(11) DEFAULT NULL,
  `tutor` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


CREATE TABLE `consulta` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `data_consulta` date NOT NULL,
  `horario` time NOT NULL,
  `motivo` text DEFAULT NULL,
  `urgencia` varchar(20) DEFAULT NULL,
  `id_animal` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id_animal` (`id_animal`),
  CONSTRAINT `consulta_ibfk_1` FOREIGN KEY (`id_animal`) REFERENCES `animal` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


INSERT INTO animal (nome, especie, raca, idade, tutor) VALUES
('Thor', 'Cachorro', 'Golden Retriever', 5, 'Carlos Silva'),
('Mimi', 'Gato', 'Siamês', 3, 'Ana Souza'),
('Luna', 'Cachorro', 'Poodle', 2, 'Fernanda Lima'),
('Rex', 'Cachorro', 'Pastor Alemão', 4, 'João Pedro'),
('Bidu', 'Cachorro', 'Vira-lata', 6, 'Mariana Costa'),
('Mel', 'Gato', 'Persa', 1, 'Lucas Rocha'),
('Paco', 'Pássaro', 'Calopsita', 2, 'Bruna Dias'),
('Nina', 'Coelho', 'Mini Lop', 1, 'Eduardo Ramos'),
('Spike', 'Hamster', 'Sírio', 1, 'Patrícia Gomes'),
('Tuga', 'Tartaruga', 'Tartaruga Tigre-d\'água', 8, 'Roberto Lima'),
('Bento', 'Cavalo', 'Mangalarga', 7, 'Ricardo Alves'),
('Lola', 'Porquinho-da-índia', 'Abissínio', 2, 'Juliana Martins');
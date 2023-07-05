USE `mspr`;

INSERT INTO `role` (`libelle`) VALUE ("admin");
INSERT INTO `utilisateur` (`email`, `password`, `code_role`) VALUES ("fredoudjoudi@gmail.com", "foo", LAST_INSERT_ID());


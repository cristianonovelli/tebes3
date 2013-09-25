-- User, Role e UserGroup
INSERT INTO `Role`(`id`, `name`, `description`, `level`) VALUES (1,'super_user','super_user', 2);
INSERT INTO `Role`(`id`, `name`, `description`, `level`) VALUES (2,'admin_user','admin_user', 1);
INSERT INTO `Role`(`id`, `name`, `description`, `level`) VALUES (3,'standard_user','standard_user', 4);
INSERT INTO `Role`(`id`, `name`, `description`, `level`) VALUES (4,'advanced_user','advanced_user', 3);

INSERT INTO `UserGroup`(`id`, `name`, `description`) VALUES (1,'x-lab','x-lab');
INSERT INTO `UserGroup`(`id`, `name`, `description`) VALUES (2,'deis','deis');
INSERT INTO `UserGroup`(`id`, `name`, `description`) VALUES (3,'epoca','epoca');

INSERT INTO `User`(`id`, `name`, `surname`, `eMail`, `password`, `role_id`, `group_id`) VALUES (3, 'Cristiano', 'Novelli', 'cristiano.novelli@gmail.com', 'xcristiano', 1, 1);
INSERT INTO `User`(`id`, `name`, `surname`, `eMail`, `password`, `role_id`, `group_id`) VALUES (5, 'Mattia', 'Generali', 'mattigene@gmail.com', 'password', 2, 3);








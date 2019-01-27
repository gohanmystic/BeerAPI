insert into Users(User_Id,Username,Password,Enabled,Full_Name,Gender,Date_Of_Birth,Phone,Email) values(1,'user','$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra',1,'User',0,CURDATE(),'0124798123','user@fsoft.com.vn');
insert into Users(User_Id,Username,Password,Enabled,Full_Name,Gender,Date_Of_Birth,Phone,Email) values(2,'admin','$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra',1,'Administration',0,CURDATE(),'0124798155','admin@fsoft.com.vn');

insert into Authority(id,name) values(1,'ROLE_USER');
insert into Authority(id,name) values(2,'ROLE_ADMIN');

insert into User_Authority(User_Id,Authority_Id) values(1, 1);
insert into User_Authority(User_Id,Authority_Id) values(2, 1);
insert into User_Authority(User_Id,Authority_Id) values(2, 2);

Insert into Category(Category_Id,Category_Name) values(1, 'Ambers & Red Ales');
Insert into Category(Category_Id,Category_Name) values(2, 'White Ales');
Insert into Category(Category_Id,Category_Name) values(3, 'Wheat Ales');

Insert into Beer(Beer_Id,Beer_Name,Price,Description,Category_Id) values (1,'Ambers Beer',15000,'Ambers Beer',1);
Insert into Beer(Beer_Id,Beer_Name,Price,Description,Category_Id) values (2,'White Beer',16000,'White Beer Des',2);
Insert into Beer(Beer_Id,Beer_Name,Price,Description,Category_Id) values (3,'Wheat Beer',16000,'Wheat Beer Des',3);
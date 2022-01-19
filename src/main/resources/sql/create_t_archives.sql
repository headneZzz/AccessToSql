drop table if exists Т_Архивы;
create table Т_Архивы
(
    Код_архива int identity,
    Название   nvarchar(50),
    Место      nvarchar(50)
);
SET IDENTITY_INSERT Т_Архивы ON

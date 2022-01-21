drop table if exists Т_Дело;
create table Т_Дело
(
    Код_Дела          int identity,
    Номер_Дела        nvarchar(20),
    Код_Описи         int,
    Код_фонда         int,
    Наименование_дела ntext,
    [Кол-во_листов]   smallint,
    Физич_состояние   nvarchar(50),
    Отметка           nvarchar(50),
    Содержание        ntext,
    Год_начала        smallint,
    Год_конца         smallint,
    Заголовок_Изменен bit,
    Фото              ntext,
    Исполнитель       int,
    Дата              datetime,
    Точная_дата       nvarchar(50)
);
SET IDENTITY_INSERT Т_Дело ON;
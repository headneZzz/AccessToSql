IF OBJECT_ID(N'Т_Описи', N'U') IS NOT NULL
    DROP TABLE [Т_Описи];

create table Т_Описи
(
    Код_Описи      int identity,
    Номер_Описи    nvarchar(20),
    Название_описи nvarchar(255),
    Год_начала     smallint,
    Год_конца      smallint,
    Код_фонда      int,
    Кол_дел        smallint,
    Аннотация      ntext,
    Кол_экземп     int,
    Полка          nvarchar(50),
    Кол_связок     nvarchar(50),
    Кол_коробок    nvarchar(50),
    Фото           ntext
)


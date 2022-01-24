create table Т_Фонд
(
    Код_фонда          int identity,
    Номер_фонда        nvarchar(20),
    Имя_Т_Дела         nvarchar(20),
    Наименование_фонда ntext,
    Число_дел          smallint,
    Год_начала         smallint,
    Год_конца          smallint,
    Аннотация          ntext,
    ДСП                bit,
    Код_Архива         int
);



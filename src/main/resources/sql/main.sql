SELECT 0                                  AS ISN_UNIT,
       Null                               AS ISN_HIGH_UNIT,
       З_СоответствиеОписей.ISN_INVENTORY,
       1                                  AS ISN_DOC_TYPE,
       Null                               AS ISN_LOCATION,
       1                                  AS ISN_SECURLEVEL,
       "o"                                AS SECURITY_CHAR,
       Null                               AS SECURITY_REASON,
       З_СоответствиеОписей.ISN_INVENTORY AS ISN_INVENTORY_CLS,
       Null                               AS ISN_STORAGE_MEDIUM,
       Null                               AS ISN_DOC_KIND,
       703                                AS UNIT_KIND,
       Т_Дело ! Номер_Дела                AS UNIT_NUM_1,
       Null                               AS UNIT_NUM_2,
       Null                               AS VOL_NUM,
       Т_Дело ! Наименование_дела         AS NAME,
       Null                               AS ANNOTATE,
       Null                               AS DELO_INDEXE,
       Null                               AS PRODUCTION_NUME,
       Null                               AS UNIT_CATEGORYE,
       Null                               AS NOTEE,
       "N"                                AS IS_IN_SEARCH,
       "N"                                AS IS_LOST,
       "N"                                AS HAS_SF,
       "N"                                AS HAS_FP,
       "N"                                AS HAS_DEFECTS,
       Null                               AS ARCHIVE_CODE,
       "N"                                AS CATALOGUED,
       0                                  AS WEIGHT,
       Null                               AS UNIT_CNT,
       Т_Дело ! Год_начала                AS START_YEAR,
       "N"                                AS START_YEAR_INEXACT,
       Т_Дело ! Год_конца                 AS END_YEAR,
       "N"                                AS END_YEAR_INEXACT,
       "T"                                AS MEDIUM_TYPE,
       Null                               AS BACKUP_COPY_CNT,
       "N"                                AS HAS_TREASURES,
       "N"                                AS IS_MUSEUM_ITEM,
       Т_Дело![Кол - во_листов]           AS PAGE_COUNT,
       "N"                                AS CARDBOARDED,
       Null                               AS ADDITIONAL_CLS,
       Т_Дело ! Точная_дата               AS ALL_DATE,
       Null                               AS ISN_SECURITY_REASON
INTO Т_Буфер
FROM Т_Дело
         INNER JOIN (SELECT dbo_INVENTORY.ISN_INVENTORY,
                            З_СоответствиеФондов.United_FUND_NUM,
                            dbo_INVENTORY.INVENTORY_NUM_1,
                            Т_Описи.Номер_Описи,
                            Т_Описи.Код_Описи
                     FROM (Т_Описи INNER JOIN dbo_INVENTORY ON Т_Описи.Номер_Описи = dbo_INVENTORY.INVENTORY_NUM_1)
                              INNER JOIN (SELECT З_United_FUND_NUM.ISN_FUND,
                                                 З_United_FUND_NUM.United_FUND_NUM,
                                                 Т_Фонд.Номер_фонда,
                                                 Т_Фонд.Код_фонда
                                          FROM (SELECT dbo_FUND.ISN_FUND,
                                                       dbo_FUND.FUND_NUM_1,
                                                       dbo_FUND.FUND_NUM_2,
                                                       dbo_FUND.FUND_NUM_3,
                                                       IIf(IsNull(dbo_FUND!FUND_NUM_1),Null, dbo_FUND ! FUND_NUM_1 & "-") &
                                                       dbo_FUND ! FUND_NUM_2 & dbo_FUND ! FUND_NUM_3 AS United_FUND_NUM
                                                FROM dbo_FUND
                                               )
                                                   INNER JOIN Т_Фонд ON З_United_FUND_NUM.United_FUND_NUM = Т_Фонд.Номер_фонда
                     )
                                         ON (dbo_INVENTORY.ISN_FUND = З_СоответствиеФондов.ISN_FUND) AND
                                            (Т_Описи.Код_фонда = З_СоответствиеФондов.Код_фонда)
) З_СоответствиеОписей ON Т_Дело.Код_Описи = З_СоответствиеОписей.Код_Описи
WHERE (((З_СоответствиеОписей.United_FUND_NUM) = [Фонд №]) AND ((З_СоответствиеОписей.Номер_Описи)=[Опись №]));

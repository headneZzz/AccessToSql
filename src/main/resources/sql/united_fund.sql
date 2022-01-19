SELECT dbo_FUND.ISN_FUND,
       dbo_FUND.FUND_NUM_1,
       dbo_FUND.FUND_NUM_2,
       dbo_FUND.FUND_NUM_3,
    IsNull(NULLIF(dbo_FUND.FUND_NUM_1, '') + '-', '')
          + dbo_FUND.FUND_NUM_2 +
       IsNull(dbo_FUND.FUND_NUM_3, '') AS United_FUND_NUM
FROM tblFUND as dbo_FUND;

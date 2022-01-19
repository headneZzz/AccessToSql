package ru.headnezzz.accesstosql.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import ru.headnezzz.accesstosql.model.UnitedFund

interface UnitedFundDao : JpaRepository<UnitedFund, String> {
    @Query(
        value = """
        SELECT dbo_FUND.ISN_FUND as isn_fund,
       IsNull(NULLIF(dbo_FUND.FUND_NUM_1, '') + '-', '')
           + dbo_FUND.FUND_NUM_2 +
       IsNull(dbo_FUND.FUND_NUM_3, '') as united_fund_num
FROM tblFUND as dbo_FUND;
    """,
        nativeQuery = true
    )
    fun unitedFund(): List<UnitedFund>
}

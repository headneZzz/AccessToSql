package ru.headnezzz.accesstosql.model

import javax.persistence.Entity
import javax.persistence.Id


@Entity
class UnitedFund {
    @Id
    val isnFund: Int? = null
    val unitedFundNum: String? = null
}

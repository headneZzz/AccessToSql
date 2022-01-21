package ru.headnezzz.accesstosql

import java.sql.Connection
import java.sql.DriverManager
import java.sql.Statement

fun test() {
    val conn: Connection =
        DriverManager.getConnection("jdbc:ucanaccess://D:\\Users\\headneZzz\\Downloads\\af\\test.mdb;openExclusive=true;ignoreCase=true")
    val st: Statement = conn.createStatement()
    val start = System.currentTimeMillis()
    val res = st.executeQuery("select * from Т_Дело where Код_Дела=1")
    while (res.next()) {
        println(res.getInt("Код_Дела"))
    }
    println(System.currentTimeMillis() - start)
    conn.close()
}

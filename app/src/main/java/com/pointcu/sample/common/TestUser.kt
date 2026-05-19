package com.pointcu.sample.common

enum class TestUser(val code: String, val birth: String, val age: String, val gender: String) {
    USER_01("uid_01_age_15_f", "2010.06.03", "15", "0"),
    USER_02("uid_02_age_14_m", "2011.06.03", "14", "1"),
    USER_03("uid_03_age_25_f", "2000.06.03", "25", "0"),
    USER_04("uid_04_age_24_m", "2001.06.03", "24", "1"),
    USER_05("uid_05_age_35_f", "1990.06.03", "35", "0"),
    USER_06("uid_06_age_34_m", "1991.06.03", "34", "1"),
    USER_07("uid_07_age_45_f", "1980.06.03", "45", "0"),
    USER_08("uid_08_age_44_m", "1981.06.03", "44", "1"),
    USER_09("uid_09_age_55_f", "1970.06.03", "55", "0"),
    USER_10("uid_10_age_54_m", "1971.06.03", "54", "1"),
    USER_11("uid_11_age_65_f", "1960.06.03", "65", "0"),
    USER_12("uid_12_age_64_m", "1961.06.03", "64", "1"),
    USER_13("uid_13_age_75_f", "1950.06.03", "75", "0"),
    USER_14("uid_14_age_74_m", "1951.06.03", "74", "1"),
    ;

    companion object {
        fun parse(code: String): TestUser {
            return entries.firstOrNull {
                code == it.code || code.contains(it.code)
            } ?: USER_04
        }
    }
}
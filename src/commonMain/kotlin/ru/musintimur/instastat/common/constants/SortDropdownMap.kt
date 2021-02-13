package ru.musintimur.instastat.common.constants

const val DROPDOWN_SORT_AZ = "Аккаунты по алфавиту"

const val DROPDOWN_SORT_POSTS_MAX = "Все посты, убывание"
const val DROPDOWN_SORT_POSTS_MIN = "Все посты, возрастание"
const val DROPDOWN_SORT_POSTS_MAX_NEW = "Новые посты, убывание"
const val DROPDOWN_SORT_POSTS_MIN_NEW = "Новые посты, возрастание"

const val DROPDOWN_SORT_FOLLOWERS_MAX = "Все подписчики, убывание"
const val DROPDOWN_SORT_FOLLOWERS_MIN = "Все подписчики, возрастание"
const val DROPDOWN_SORT_FOLLOWERS_MAX_NEW = "Новые подписчики, убывание"
const val DROPDOWN_SORT_FOLLOWERS_MIN_NEW = "Новые подписчики, возрастание"

const val DROPDOWN_SORT_FOLLOWINGS_MAX = "Все подписки, убывание"
const val DROPDOWN_SORT_FOLLOWINGS_MIN = "Все подписки, возрастание"
const val DROPDOWN_SORT_FOLLOWINGS_MAX_NEW = "Новые подписки, убывание"
const val DROPDOWN_SORT_FOLLOWINGS_MIN_NEW = "Новые подписки, возрастание"

const val DROPDOWN_SORT_TYPE_AZ = "ddSortTypeAz"

const val DROPDOWN_SORT_TYPE_SPMAX = "ddSortTypeSpMax"
const val DROPDOWN_SORT_TYPE_SPMIN = "ddSortTypeSpMin"
const val DROPDOWN_SORT_TYPE_NSPMAX = "ddSortTypeNSpMax"
const val DROPDOWN_SORT_TYPE_MSPMIN = "ddSortTypeNspMin"

const val DROPDOWN_SORT_TYPE_SFLWRMAX = "ddSortTypeSflwrMax"
const val DROPDOWN_SORT_TYPE_SFFLWRMIN = "ddSortTypeSflwrMin"
const val DROPDOWN_SORT_TYPE_NSFLWRMAX = "ddSortTypeNSflwrMax"
const val DROPDOWN_SORT_TYPE_NSFLWRMIN = "ddSortTypeNSflwrMan"

const val DROPDOWN_SORT_TYPE_SFLWGMAX = "ddSortTypeSflwgMax"
const val DROPDOWN_SORT_TYPE_SFLWGMIN = "ddSortTypeSflwgMin"
const val DROPDOWN_SORT_TYPE_NSFLWGMAX = "ddSortTypeNsflwgMax"
const val DROPDOWN_SORT_TYPE_MSFLWGMIN = "ddSortTypeNsflwgMin"

const val DROPDOWN_SORT_TYPE_UNDEFINED = "ddSortTypeUd"

val sortDropdownMap: Map<String, String> =
    mapOf(
        DROPDOWN_SORT_TYPE_AZ to DROPDOWN_SORT_AZ,
        DROPDOWN_SORT_TYPE_SPMAX to DROPDOWN_SORT_POSTS_MAX,
        DROPDOWN_SORT_TYPE_SPMIN to DROPDOWN_SORT_POSTS_MIN,
        DROPDOWN_SORT_TYPE_NSPMAX to DROPDOWN_SORT_POSTS_MAX_NEW,
        DROPDOWN_SORT_TYPE_MSPMIN to DROPDOWN_SORT_POSTS_MIN_NEW,
        DROPDOWN_SORT_TYPE_SFLWRMAX to DROPDOWN_SORT_FOLLOWERS_MAX,
        DROPDOWN_SORT_TYPE_SFFLWRMIN to DROPDOWN_SORT_FOLLOWERS_MIN,
        DROPDOWN_SORT_TYPE_NSFLWRMAX to DROPDOWN_SORT_FOLLOWERS_MAX_NEW,
        DROPDOWN_SORT_TYPE_NSFLWRMIN to DROPDOWN_SORT_FOLLOWERS_MIN_NEW,
        DROPDOWN_SORT_TYPE_SFLWGMAX to DROPDOWN_SORT_FOLLOWINGS_MAX,
        DROPDOWN_SORT_TYPE_SFLWGMIN to DROPDOWN_SORT_FOLLOWINGS_MIN,
        DROPDOWN_SORT_TYPE_NSFLWGMAX to DROPDOWN_SORT_FOLLOWINGS_MAX_NEW,
        DROPDOWN_SORT_TYPE_MSFLWGMIN to DROPDOWN_SORT_FOLLOWINGS_MIN_NEW
    )
package ru.musintimur.instastat.common.constants

private const val API = "/api"
private const val V1 = "v1"
private const val CURRENT_VERSION = V1
private const val API_PATH = "$API/$CURRENT_VERSION"

const val API_DAY_REPORT = "$API_PATH/day-report"
const val API_GRAPH_BLOCK = "$API_PATH/graph-block"
const val API_START_PARSING = "$API_PATH/parse"
const val API_PARSE_PROGRESS = "$API_PATH/parser-progress"
const val API_PROFILE_HISTORY_POSTS = "$API_PATH/history/posts"
const val API_PROFILE_HISTORY_FOLLOWERS = "$API_PATH/history/followers"
const val API_PROFILE_HISTORY_FOLLOWINGS = "$API_PATH/history/followings"
const val API_PROFILE_ACTIVATE = "$API_PATH/profile-activate"
const val API_ADD_PROFILE = "$API_PATH/add-profile"
const val API_ADD_POST = "$API_PATH/add-post"
const val API_PARSE_POST = "$API_PATH/parse-post"
const val API_PRINT_COMMENTS = "$API_PATH/print-comments"

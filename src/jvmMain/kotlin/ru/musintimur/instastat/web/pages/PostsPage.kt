package ru.musintimur.instastat.web.pages

import io.ktor.application.*
import io.ktor.html.*
import io.ktor.routing.*
import kotlinx.html.*
import ru.musintimur.instastat.common.constants.*
import ru.musintimur.instastat.extensions.padZeros
import ru.musintimur.instastat.extensions.toSqlLiteText
import ru.musintimur.instastat.model.entities.Post
import ru.musintimur.instastat.repository.Repository
import ru.musintimur.instastat.web.auth.readUserGroupHash
import ru.musintimur.instastat.web.components.pageTemplate

const val POSTS_PAGE_ADD = "$POSTS_PAGE/add"

fun Route.postsPage(db: Repository) {
    get(POSTS_PAGE) {
        val posts = db.posts.getAllPosts()
        call.respondHtml {
            pageTemplate(POSTS_PAGE_NAME, readUserGroupHash(this@get)) {
                div {
                    classes = setOf(CLS_POSTS_SECTION)
                    div {
                        classes = setOf(CLS_ROW, CLS_ML_4)
                        buttonInput {
                            classes = setOf(CLS_INPUT, BUTTON_BOOTSTRAP_PRIMARY)
                            id = INPUT_ID_BUTTON_ADD_POST
                            value = "Добавить"
                        }
                    }
                    div {
                        classes = setOf(CLS_ROW, CLS_ML_2, CLS_MT_2, CLS_MR_2)
                        table {
                            attributes["border"] = "1"
                            classes = setOf(CLS_BOOTSTRAP_TABLE)
                            tr {
                                th { +"Post URL" }
                                th { +"Post Date" }
                                th { +"Post Text" }
                                th { +"Comments" }
                                th { }
                            }
                            posts.forEachIndexed { index, post ->
                                tr {
                                    td {
                                        +"${index.inc().padZeros(2)}. "
                                        a {
                                            href = post.postUrl
                                            target = "_blank"
                                            +post.postUrl
                                        }
                                    }
                                    td {
                                        post.postDate?.let {
                                            +it.toSqlLiteText()
                                        }
                                    }
                                    td {
                                        post.postText?.let {
                                            +it
                                        }
                                    }
                                    td {
                                        a {
                                            href = "$COMMENTS_PAGE/${post.postId}"
                                            target = "_blank"
                                            +post.commentsCount.toString()
                                        }
                                    }
                                    td {
                                        buttonInput {
                                            id = post.postUrl
                                            classes = setOf(CLS_INPUT, CLS_INPUT_POST_ENTRY)
                                            value = "Выгрузка"
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    get(POSTS_PAGE_ADD) {
        call.respondHtml {
            pageTemplate(POSTS_PAGE_NAME, readUserGroupHash(this@get)) {
                div {
                    classes = setOf(CLS_POSTS_SECTION)
                    form {
                        classes = setOf(CLS_ROW, CLS_ML_4)
                        id = FORM_ID_ADD_POST
                        input {
                            name = Post::postUrl.name
                        }
                        submitInput {
                            classes = setOf(CLS_ML_2, BUTTON_BOOTSTRAP_PRIMARY)
                            value = "Сохранить"
                        }
                    }
                    div {
                        classes = setOf(CLS_ROW, CLS_ML_4)
                        id = ADD_POST_MESSAGE
                    }
                }
            }
        }
    }
}
package ru.musintimur.instastat.web.pages

import io.ktor.application.*
import io.ktor.html.*
import io.ktor.locations.*
import io.ktor.routing.*
import kotlinx.html.*
import kotlinx.html.attributes.StringAttribute
import ru.musintimur.instastat.common.constants.*
import ru.musintimur.instastat.extensions.padZeros
import ru.musintimur.instastat.parsers.getFullProfileUrl
import ru.musintimur.instastat.repository.Repository
import ru.musintimur.instastat.web.auth.readUserGroupHash
import ru.musintimur.instastat.web.components.pageTemplate

const val COMMENTS_PAGE = "comments"
const val COMMENTS_PAGE_NAME = "Комментарии"
const val COMMENTS_PAGE_PARAMETER = "$COMMENTS_PAGE/{postId}"
const val COMMENTS_PAGE_PRINT = "$COMMENTS_PAGE_PARAMETER/print"

@KtorExperimentalLocationsAPI
@Location(COMMENTS_PAGE_PARAMETER)
data class CommentsPage(val postId: Int)

@KtorExperimentalLocationsAPI
@Location(COMMENTS_PAGE_PRINT)
data class CommentsPrint(val postId: Int)

@KtorExperimentalLocationsAPI
fun Route.comments(db: Repository) {
    get<CommentsPage> { page ->
        val post = db.posts.getPostById(page.postId)
        val comments = post?.let { db.comments.getPostComments(it) } ?: emptyList()
        call.respondHtml {
            pageTemplate(COMMENTS_PAGE_NAME, readUserGroupHash(this@get)) {
                div {
                    classes = setOf(CLS_COMMENTS_SECTION)
                    div {
                        classes = setOf(CLS_ROW, CLS_ML_2, CLS_MT_2, CLS_MR_2)
                        buttonInput {
                            id = INPUT_ID_BUTTON_PRINT_COMMENTS
                            classes = setOf(CLS_INPUT, BUTTON_BOOTSTRAP_PRIMARY)
                            value = "Напечатать"
                            StringAttribute()[this, "post_id"] = post?.postId.toString()
                        }
                    }
                    div {
                        classes = setOf(CLS_ROW, CLS_ML_2, CLS_MT_2, CLS_MR_2)
                        table {
                            attributes["border"] = "1"
                            classes = setOf(CLS_BOOTSTRAP_TABLE)
                            tr {
                                th { +"Author" }
                                th { +"Comment" }
                            }
                            comments.forEachIndexed { index, comment ->
                                tr {
                                    td {
                                        +"${index.inc().padZeros(2)}. "
                                        a {
                                            href = getFullProfileUrl(comment.author)
                                            target = "_blank"
                                            +comment.author
                                        }
                                    }
                                    td {
                                        +comment.text
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
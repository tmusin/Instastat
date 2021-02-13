package ru.musintimur.instastat.web.javascript.links

import kotlinx.browser.document
import org.w3c.dom.get
import ru.musintimur.instastat.common.constants.LINKS_JS_KOTLIN
import ru.musintimur.instastat.common.constants.LINK_SORT_MAIN_PAGE
import ru.musintimur.instastat.web.javascript.extensions.asHTMLAnchorElement

fun setupLinks() {
    val refs = document.getElementsByClassName(LINKS_JS_KOTLIN)
    for (i in 0 until refs.length) {
        refs[i]?.asHTMLAnchorElement()?.let { anchor ->
            when {
                anchor.classList.contains(LINK_SORT_MAIN_PAGE) -> {
                    anchor.onclick = { event ->
                        onClickSortReport(anchor)
                        event.preventDefault()
                    }
                }
            }
        }
    }
}
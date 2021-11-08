package ru.musintimur.instastat.web.components

import kotlinx.html.*
import ru.musintimur.instastat.common.constants.*
import ru.musintimur.instastat.extensions.ariaControls
import ru.musintimur.instastat.extensions.ariaLabelLedBy
import ru.musintimur.instastat.extensions.dataToggle
import ru.musintimur.instastat.extensions.dropdownStyle

fun DIV.customDropdown(sortMap: Map<String, String>, sortName: String) {
    div {
        classes = setOf(CLS_BOOTSTRAP_DROPDOWN, CLS_ML_2)
        button {
            classes = setOf(BUTTON_BOOTSTRAP, BUTTON_BOOTSTRAP_SECONDARY, CLS_BOOTSTRAP_DROPDOWN_TOGGLE, CLS_BOOTSTRAP_JUSTIFY_CONTENT_LEFT)
            type = ButtonType.button
            dropdownStyle()
            +sortName
        }
        div {
            classes = setOf(CLS_BOOTSTRAP_DROPDOWN_MENU)
            ariaLabelLedBy("dropdownMenuButton")
            sortMap.forEach { item ->
                a {
                    classes = setOf("dropdown-item", LINKS_JS_KOTLIN, LINK_SORT_MAIN_PAGE)
                    href = FORM_ACTION_VOID
                    dataToggle("list")
                    ariaControls("home")
                    +item.value
                    hiddenInput {
                        name = "sortOrder"
                        value = item.key
                    }
                }
            }
        }
    }
}
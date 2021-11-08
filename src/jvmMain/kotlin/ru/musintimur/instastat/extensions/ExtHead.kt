package ru.musintimur.instastat.extensions

import kotlinx.html.HEAD
import kotlinx.html.link
import kotlinx.html.meta
import ru.musintimur.instastat.common.constants.STYLES_COMMON

fun HEAD.bootstrapCss() {
    link {
        rel = "stylesheet"
        href = "https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css"
        integrity = "sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2"
        crossOrigin("anonymous")
    }
    link {
        rel = "stylesheet"
        href = "https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"
    }
    link {
        rel = "stylesheet"
        href = "https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.7.1/css/bootstrap-datepicker.min.css"
    }
}

fun HEAD.defMeta() {
    meta {
        httpEquiv="content-type"
        content="text/html; charset=utf-8"
    }
    meta {
        content="utf-8"
        httpEquiv="encoding"
    }
    meta {
        name="viewport"
        content="width=device-width, initial-scale=1, shrink-to-fit=no"
    }
}

fun HEAD.googleFonts() {
    link {
        rel="stylesheet"
        href="https://fonts.googleapis.com/css?family=Open+Sans|PT+Serif|Roboto|Charm|Quantico&display=swap"
    }
}

fun HEAD.customCss() {
    link {
        rel = "stylesheet"
        href = STYLES_COMMON
    }
}
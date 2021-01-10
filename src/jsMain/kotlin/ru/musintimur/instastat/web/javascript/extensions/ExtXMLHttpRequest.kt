package ru.musintimur.instastat.web.javascript.extensions

import org.w3c.dom.events.Event
import org.w3c.xhr.XMLHttpRequest

fun XMLHttpRequest.setupPost(url: String, onLoadEnd: (Event) -> Unit): XMLHttpRequest {
    this.onloadend = onLoadEnd
    this.open("POST", url, true)
    this.setRequestHeader("Content-type", "application/x-www-form-urlencoded; charset=utf-8")
    return this
}

fun XMLHttpRequest.setupGet(url: String, onLoadEnd: (Event) -> Unit): XMLHttpRequest {
    this.onloadend = onLoadEnd
    this.open("GET", url, true)
    return this
}
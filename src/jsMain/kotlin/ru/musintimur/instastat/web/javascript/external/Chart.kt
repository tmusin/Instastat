package ru.musintimur.instastat.web.javascript.external

import kotlinext.js.asJsObject

@JsModule("chart.js")
@JsNonModule
external class Chart(ctx: dynamic, config: dynamic)

data class Dataset(
    val label: String,
    val data: Array<Long>,
    val borderColor: String = "blue"
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class.js != other::class.js) return false

        other as Dataset

        if (label != other.label) return false
        if (!data.contentEquals(other.data)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = label.hashCode()
        result = 31 * result + data.contentHashCode()
        return result
    }
}

fun getChartConfig(
    labels: Array<String>,
    dataset: Array<Long>,
    name: String
): dynamic = object {
    val type = "line"
    val data = object {
        val labels = labels
        val datasets = arrayOf(Dataset(name, dataset))
    }
    val options = object {
        val scales = object {
            val xAxes = arrayOf(
                object {
                    val ticks = object {
                        val callback = { value: String, _: Int, _: Array<dynamic> ->
                            value.substring(0,10)
                        }
                    }
                }
            )
        }
    }
}.asJsObject()
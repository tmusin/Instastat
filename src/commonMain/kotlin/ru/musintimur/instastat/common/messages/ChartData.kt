package ru.musintimur.instastat.common.messages

data class Data (val data: Array<Int>) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as Data

        if (!data.contentEquals(other.data)) return false

        return true
    }

    override fun hashCode(): Int {
        return data.contentHashCode()
    }
}

data class ChartData(
    val labels: Array<String>,
    val datasets: Array<Data>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as ChartData

        if (!labels.contentEquals(other.labels)) return false
        if (!datasets.contentEquals(other.datasets)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = labels.contentHashCode()
        result = 31 * result + datasets.contentHashCode()
        return result
    }
}
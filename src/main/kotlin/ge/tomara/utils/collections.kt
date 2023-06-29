package ge.tomara.utils

fun <T> mergeSortedLists(
    firstList: List<T>, secondList: List<T>, nLimitArg: Int?, comparator: Comparator<in T>,
): List<T> {
    val bothListSize = firstList.size + secondList.size
    val nLimit = if(nLimitArg == null || nLimitArg < 0 || nLimitArg > bothListSize) {
        bothListSize
    } else nLimitArg
    val merged = ArrayList<T>(nLimit)
    var firstIndex = 0
    var secondIndex = 0
    while(firstIndex < firstList.size && secondIndex < secondList.size && merged.size < nLimit) {
        val firstElem = firstList[firstIndex]
        val secondElem = secondList[secondIndex]
        if(comparator.compare(firstElem, secondElem) <= 0) {
            merged.add(firstElem)
            firstIndex++
        } else {
            merged.add(secondElem)
            secondIndex++
        }
    }
    while(firstIndex < firstList.size && merged.size < nLimit) {
        merged.add(firstList[firstIndex++])
    }
    while(secondIndex < secondList.size && merged.size < nLimit) {
        merged.add(secondList[secondIndex++])
    }
    return merged
}

fun <T> mergeSortedLists(firstList: List<T>, secondList: List<T>, comparator: Comparator<in T>): List<T> {
    return mergeSortedLists(firstList, secondList, null, comparator)
}

fun <K, V> zipLists(aList: List<K>, bList: List<V>): List<Pair<K, V>> {
    val capacity = aList.size.coerceAtMost(bList.size)
    return ArrayList<Pair<K, V>>(capacity).apply {
        for(i in 0 until capacity) {
            add(aList[i] to bList[i])
        }
    }
}

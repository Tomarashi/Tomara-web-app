package ge.tomara.Tomarawebapp.utils

import ge.tomara.response.words.WordFullResponse
import ge.tomara.utils.mergeSortedLists
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.util.Random

class TestCollections {

    private val intComparator = Comparator<Int> { a, b ->
        a - b
    }
    private val stringComparator = Comparator<String> { a, b ->
        a.compareTo(b)
    }

    @Test
    fun testMergeSortedListsInt() {
        val firstList = listOf(1, 2, 3, 4, 5)
        val secondList = listOf(6, 7, 8, 9)
        val expectedList = firstList + secondList

        val mergedList = mergeSortedLists(firstList, secondList, intComparator)
        Assertions.assertEquals(expectedList, mergedList)
    }

    @Test
    fun testMergeSortedListsInt2() {
        val random = Random()
        val expectedList = (1..10_000).toList()
        val firstList = mutableListOf<Int>()
        val secondList = mutableListOf<Int>()

        for(a in expectedList) {
            if(random.nextBoolean()) {
                firstList.add(a)
            } else {
                secondList.add(a)
            }
        }

        val mergedList = mergeSortedLists(firstList, secondList, intComparator)
        Assertions.assertEquals(expectedList, mergedList)

        val nLimit = 50
        val mergedListNLimit = mergeSortedLists(firstList, secondList, nLimit, intComparator)
        Assertions.assertEquals((1..nLimit).toList(), mergedListNLimit)

        val mergedListNLimitAll = mergeSortedLists(firstList, secondList, expectedList.size + 1, intComparator)
        Assertions.assertEquals(expectedList, mergedListNLimitAll)
    }

    @Test
    fun testMergeSortedListsStrings() {
        val list = listOf("a", "b", "c", "d", "e", "f")

        var mergedList: List<String> = mergeSortedLists(emptyList(), emptyList(), stringComparator)
        Assertions.assertEquals(emptyList<String>(), mergedList)

        mergedList = mergeSortedLists(list, emptyList(), stringComparator)
        Assertions.assertEquals(list, mergedList)

        mergedList = mergeSortedLists(emptyList(), list, stringComparator)
        Assertions.assertEquals(list, mergedList)
    }

    @Test
    fun testMergeSortedListsWordResponses() {
        fun wordResponse(wordEng: String): WordFullResponse {
            return WordFullResponse.from(1, "", wordEng, 1)
        }

        val firstList = listOf(
            wordResponse("A"), wordResponse("D"), wordResponse("F"))
        val secondList = listOf(
            wordResponse("B"), wordResponse("C"), wordResponse("E"))

        val mergedList = mergeSortedLists(firstList, secondList) { a, b ->
            a.wordEng.compareTo(b.wordEng)
        }
        val mergedListWordEngs = mergedList.map { it.wordEng }

        Assertions.assertEquals(listOf("A", "B", "C", "D", "E", "F"), mergedListWordEngs)
    }

}

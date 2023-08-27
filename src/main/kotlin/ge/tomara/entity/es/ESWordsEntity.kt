package ge.tomara.entity.es

import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.Document

@Document(indexName = "words")
data class ESWordsEntity(
    @Id
    var id: String
)

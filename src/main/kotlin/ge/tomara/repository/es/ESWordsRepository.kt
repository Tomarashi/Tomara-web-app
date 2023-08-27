package ge.tomara.repository.es

import ge.tomara.entity.es.ESWordsEntity
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository

interface ESWordsRepository: ElasticsearchRepository<ESWordsEntity, String>

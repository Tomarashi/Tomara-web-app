package ge.tomara.repository

import ge.tomara.repository.entity.WordsEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface WordsRepository: CrudRepository<WordsEntity, Int>

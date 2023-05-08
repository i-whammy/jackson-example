import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class JacksonTest {

    private val objectMapper = jacksonObjectMapper()

    @Test
    fun testIfProperlyDeserialized() {
        data class User (val id: Int, val name: String)
        val sut = "{\"id\": 1, \"name\": \"John Doe\"}"
        val actual = objectMapper.readValue(sut, User::class.java)
        assertEquals(1, actual.id)
        assertEquals("John Doe", actual.name)
    }

    @Test
    fun testIfMissingNullablePropertyDeserialized() {
        data class User2 (val id: Int, val name: String?)
        val sut = "{\"id\": 1}"
        val actual = objectMapper.readValue(sut, User2::class.java)
        assertEquals(1, actual.id)
        assertNull(actual.name)
    }

    @Test
    fun testIfUnnecessaryPropertyDeserialized() {
        @JsonIgnoreProperties(ignoreUnknown = true)
        data class User3 (val id: Int, val name: String?)
        val sut = "{\"id\": 1, \"dummy\": false}"
        val actual = objectMapper.readValue(sut, User3::class.java)
        assertEquals(1, actual.id)
        assertNull(actual.name)
    }
}

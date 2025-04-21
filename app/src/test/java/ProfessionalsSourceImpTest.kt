import com.example.nutriumdemo.data.dto.ProfessionalDetails
import com.example.nutriumdemo.data.remote.ProfessionalsSourceImp
import io.ktor.client.*
import io.ktor.client.engine.mock.MockEngine  // Importação correta
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.test.resetMain
import kotlinx.serialization.json.Json
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*
import io.mockk.*

@OptIn(ExperimentalCoroutinesApi::class)
class ProfessionalsSourceImpTest {

    private lateinit var professionalsSourceImp: ProfessionalsSourceImp
    private lateinit var mockEngine: MockEngine  // Agora reconhecido
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        // MockK - mockando a dependência do engine HTTP
        mockEngine = MockEngine { request ->
            respond(
                content = "",
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }

        professionalsSourceImp = ProfessionalsSourceImp(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getProfessionalDetails returns ProfessionalDetails on success`() =
        runTest(testDispatcher) {
            // Arrange
            val professionalId = "5"
            val expectedProfessionalDetails =
                ProfessionalDetails(id = 5, name = "Sophia Davis", about_me = "Dr. Sophia Davis is passionate", expertise = listOf("Sports Nutrition", "Weight Loss"), languages = listOf("German", "Italian"), profile_picture_url ="https://nutrisearch.vercel.app/static/image-5.jpg", rating = 3, rating_count = 153)

            // Act
            val result = professionalsSourceImp.getProfessionalDetails(professionalId)

            // Assert
            assertEquals(expectedProfessionalDetails.name, result?.name)
            assertEquals(expectedProfessionalDetails.expertise, result?.expertise)
            assertEquals(expectedProfessionalDetails.languages, result?.languages)
        }
}

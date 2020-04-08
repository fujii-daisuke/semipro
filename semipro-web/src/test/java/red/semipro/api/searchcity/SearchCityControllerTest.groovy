package red.semipro.api.searchcity

import org.mockito.InjectMocks
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification
import spock.lang.Unroll

@SpringBootTest
@AutoConfigureMockMvc
@Unroll
class SearchCityControllerTest extends Specification {

    @InjectMocks
    SearchCityController searchCityController

    @Autowired
    MockMvc mockMvc

    def "when context is loaded then all expected beans are created"() {
        expect: "the WebController is created"
        searchCityController
    }
}

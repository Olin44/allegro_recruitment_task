package pl.olin44.allegro.languages;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import pl.olin44.allegro.externalsource.ExternalServerException;
import pl.olin44.allegro.externalsource.ExternalSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {ExternalSource.class})
public class ExternalSourceTest {

    public static final String TEST_DATA = "test data";

    public static final String TEST_USER_NAME = "userName";

    @MockBean
    RestTemplate restTemplate;

    ExternalSource<String> externalSource;

    @BeforeEach
    void beforeEach() {
        externalSource = new ExternalSource<>(restTemplate);
    }

    @Test
    void whenExternalSourceIsAvailableShouldReturnData() {
        //      arrange
        when(restTemplate.getForEntity(ExternalSource.REPOS_URL_TEMPLATE.formatted(TEST_USER_NAME), String[].class))
                .thenReturn(ResponseEntity.ok(new String[]{TEST_DATA}));
        //      act
        List<String> providedData = externalSource.getAll(String[].class, TEST_USER_NAME);
        //      assert
        assertEquals(providedData, List.of(TEST_DATA));
    }

    @Test
    void whenExternalSourceIsNotAvailableShouldThrownException() {
//      arrange
        ExternalServerException expectedExternalServerException = new ExternalServerException("External error occurred 400 BAD_REQUEST");
        when(restTemplate.getForEntity(ExternalSource.REPOS_URL_TEMPLATE.formatted(TEST_USER_NAME), String[].class))
                .thenReturn(ResponseEntity.badRequest().build());
//      act
        ExternalServerException thrownException = assertThrows(expectedExternalServerException.getClass(),
                () -> externalSource.getAll(String[].class, TEST_USER_NAME));
//      assert
        assertEquals(expectedExternalServerException.getMessage(), thrownException.getMessage());
    }

    @Test
    void whenStatusCodeIsNot200ThenThrownException() {
        //      arrange
        when(restTemplate.getForEntity(ExternalSource.REPOS_URL_TEMPLATE.formatted(TEST_USER_NAME), String[].class))
                .thenReturn(getInternalServerErrorExceptionResponse());
        ExternalServerException expectedExternalServerException = new ExternalServerException("External error occurred 500 INTERNAL_SERVER_ERROR");
        //      act
        ExternalServerException thrownException = assertThrows(expectedExternalServerException.getClass(),
                () -> externalSource.getAll(String[].class, TEST_USER_NAME));
        //      assert
        assertEquals(thrownException.getMessage(), expectedExternalServerException.getMessage());
    }

    private ResponseEntity<String[]> getInternalServerErrorExceptionResponse() {
        return ResponseEntity.internalServerError().build();
    }
}

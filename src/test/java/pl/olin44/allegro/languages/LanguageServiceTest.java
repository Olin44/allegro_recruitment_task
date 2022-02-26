package pl.olin44.allegro.languages;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import pl.olin44.allegro.externalsource.ExternalSource;
import pl.olin44.allegro.externalsource.RestTemplateBeanConfiguration;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {LanguageService.class, RestTemplateBeanConfiguration.class, ExternalSource.class})
class LanguageServiceTest {

    public static final String TEST_USER_NAME = "userName";
    @Mock
    private RestTemplate restTemplate;

    @Test
    void getLanguageSortedByByteSize() {
        ExternalSource<RepositoryLanguageDetails> languageSource = new ExternalSource<>(restTemplate);
        when(restTemplate.getForEntity(ExternalSource.REPOS_URL_TEMPLATE.formatted(TEST_USER_NAME), RepositoryLanguageDetails[].class))
                .thenReturn(createResponseEntity());
        LanguageService languageService = new LanguageService(languageSource);
        List<LanguageCodeBaseSizeResponse> languageCodeBaseSizeResponses = languageService.getLanguageSortedByByteSize(TEST_USER_NAME);

        assertEquals(languageCodeBaseSizeResponses, createExpectedResponse());
    }

    private List<LanguageCodeBaseSizeResponse> createExpectedResponse() {
        return List.of(
                new LanguageCodeBaseSizeResponse("Java", 180),
                new LanguageCodeBaseSizeResponse("Python", 60),
                new LanguageCodeBaseSizeResponse("Scala", 3));
    }

    private ResponseEntity<RepositoryLanguageDetails[]> createResponseEntity() {
        RepositoryLanguageDetails[] repositoryLanguageDetails = {
                new RepositoryLanguageDetails("Java", 20),
                new RepositoryLanguageDetails("Java", 80),
                new RepositoryLanguageDetails("Java", 30),
                new RepositoryLanguageDetails("Java", 50),
                new RepositoryLanguageDetails("Python", 20),
                new RepositoryLanguageDetails("Python", 40),
                new RepositoryLanguageDetails("Scala", 1),
                new RepositoryLanguageDetails("Scala", 2),
        };
        return ResponseEntity.ok(repositoryLanguageDetails);
    }
}
package pl.olin44.allegro.langues;

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

    @Mock
    private RestTemplate restTemplate;

    @Test
    void getLanguageSortedByByteSize() {
        ExternalSource<RepositoryLanguageDetails> languageSource = new ExternalSource<>(restTemplate);
        when(restTemplate.getForEntity(ExternalSource.ALLEGRO_REPOS_URL, RepositoryLanguageDetails[].class))
                .thenReturn(createResponseEntity());
        LanguageService languageService = new LanguageService(languageSource);
        List<LanguageCodeBaseSize> languageCodeBaseSizes = languageService.getLanguageSortedByByteSize();

        assertEquals(languageCodeBaseSizes, createExpectedResponse());
    }

    private List<LanguageCodeBaseSize> createExpectedResponse() {
        return List.of(
                new LanguageCodeBaseSize("Java", 180),
                new LanguageCodeBaseSize("Python", 60),
                new LanguageCodeBaseSize("Scala", 3));
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
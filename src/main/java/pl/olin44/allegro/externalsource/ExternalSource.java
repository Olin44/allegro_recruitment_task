package pl.olin44.allegro.externalsource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class ExternalSource<T> {

    private static final String EXTERNAL_ERROR_MESSAGE_PATTERN = "External error occurred %s";

    public static final String ALLEGRO_REPOS_URL = "https://api.github.com/orgs/allegro/repos";

    private final Logger logger = LoggerFactory.getLogger(ExternalSource.class);

    private final RestTemplate restTemplate;

    public ExternalSource(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<T> getAll(Class<T[]> requestedEntityClass) {
        ResponseEntity<T[]> response = getResponse(requestedEntityClass);
        if (response.getStatusCode() != HttpStatus.OK) {
            String externalErrorMessage = getExternalErrorMessage(response.getStatusCode().toString());
            logger.error(externalErrorMessage);
            throw new ExternalServerException(externalErrorMessage);
        }
        return Optional.ofNullable(response.getBody())
                .map(Arrays::asList)
                .orElse(Collections.emptyList());
    }

    private String getExternalErrorMessage(String errorSource) {
        return EXTERNAL_ERROR_MESSAGE_PATTERN.formatted(errorSource);
    }

    private ResponseEntity<T[]> getResponse(Class<T[]> requestedEntityClass) {
        try {
            return restTemplate.getForEntity(ALLEGRO_REPOS_URL, requestedEntityClass);
        } catch (RestClientException restClientException) {
            String externalErrorMessage = getExternalErrorMessage(restClientException.getMessage());
            logger.error(externalErrorMessage);
            throw new ExternalServerException(externalErrorMessage,
                    restClientException);
        }
    }
}

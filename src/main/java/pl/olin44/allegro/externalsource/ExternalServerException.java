package pl.olin44.allegro.externalsource;

import org.springframework.web.client.RestClientException;

public class ExternalServerException extends RuntimeException {

    public ExternalServerException(String message) {
        super(message);
    }

    public ExternalServerException(String message, RestClientException restClientException) {
        super(message, restClientException);
    }
}

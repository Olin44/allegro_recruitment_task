package pl.olin44.allegro.languages;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.olin44.allegro.externalsource.ExternalSource;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class LanguageService {

    private final ExternalSource<RepositoryLanguageDetails> languageSource;

    public List<LanguageCodeBaseSizeResponse> getLanguageSortedByByteSize(String userName) {
        return languageSource.getAll(RepositoryLanguageDetails[].class, userName)
                .stream()
                .collect(Collectors.groupingBy(RepositoryLanguageDetails::getLanguage))
                .entrySet()
                .stream()
                .map(this::createResponse)
                .sorted(getResponseComparator())
                .toList();
    }

    private Comparator<LanguageCodeBaseSizeResponse> getResponseComparator() {
        return Comparator.comparing(LanguageCodeBaseSizeResponse::getByteSize).reversed();
    }

    private LanguageCodeBaseSizeResponse createResponse(Map.Entry<String, List<RepositoryLanguageDetails>> allegroLanguages) {
        return new LanguageCodeBaseSizeResponse(allegroLanguages.getKey(), getBytesSum(allegroLanguages.getValue()));
    }

    private int getBytesSum(List<RepositoryLanguageDetails> repositoryLanguageDetails) {
        return repositoryLanguageDetails.stream()
                .mapToInt(RepositoryLanguageDetails::getSize)
                .sum();
    }
}

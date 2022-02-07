package pl.olin44.allegro.langues;

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

    public List<LanguageCodeBaseSize> getLanguageSortedByByteSize() {
        return languageSource.getAll(RepositoryLanguageDetails[].class)
                .stream()
                .collect(Collectors.groupingBy(RepositoryLanguageDetails::getLanguage))
                .entrySet()
                .stream()
                .map(this::createResponse)
                .sorted(getResponseComparator())
                .toList();
    }

    private Comparator<LanguageCodeBaseSize> getResponseComparator() {
        return Comparator.comparing(LanguageCodeBaseSize::getByteSize).reversed();
    }

    private LanguageCodeBaseSize createResponse(Map.Entry<String, List<RepositoryLanguageDetails>> allegroLanguages) {
        return new LanguageCodeBaseSize(allegroLanguages.getKey(), getBytesSum(allegroLanguages.getValue()));
    }

    private int getBytesSum(List<RepositoryLanguageDetails> repositoryLanguageDetails) {
        return repositoryLanguageDetails.stream()
                .mapToInt(RepositoryLanguageDetails::getSize)
                .sum();
    }
}

package pl.olin44.allegro.langues;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@AllArgsConstructor
public class LanguageController {

    private final LanguageService languageSource;

    @GetMapping("{userName}/languages/popularity/bytecode")
    public ResponseEntity<List<LanguageCodeBaseSizeResponse>> getReposStars(@PathVariable String userName) {
        return ResponseEntity.ok(languageSource.getLanguageSortedByByteSize(userName));
    }
}

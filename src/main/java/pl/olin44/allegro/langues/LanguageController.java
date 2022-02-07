package pl.olin44.allegro.langues;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@AllArgsConstructor
public class LanguageController {

    private final LanguageService languageSource;

    @GetMapping("/languages/popularity/bytecode")
    public ResponseEntity<List<LanguageCodeBaseSize>> getReposStars() {
        return ResponseEntity.ok(languageSource.getLanguageSortedByByteSize());
    }
}

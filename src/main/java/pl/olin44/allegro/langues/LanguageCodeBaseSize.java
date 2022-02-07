package pl.olin44.allegro.langues;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LanguageCodeBaseSize {
    private String language;
    private int byteSize;
}

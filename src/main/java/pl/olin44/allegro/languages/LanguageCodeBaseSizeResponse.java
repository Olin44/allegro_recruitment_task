package pl.olin44.allegro.languages;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LanguageCodeBaseSizeResponse {
    private String language;
    private int byteSize;
}

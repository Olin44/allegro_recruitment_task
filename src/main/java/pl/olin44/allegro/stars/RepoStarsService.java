package pl.olin44.allegro.stars;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.olin44.allegro.externalsource.ExternalSource;

import java.util.List;

@Service
@AllArgsConstructor
public class RepoStarsService {

    private final ExternalSource<AllegroRepoStars> externalSource;

    public List<RepoStarsResponse> getReposStars() {
        return externalSource.getAll(AllegroRepoStars[].class).stream()
                .map(this::mapToRepoStars)
                .toList();
    }

    private RepoStarsResponse mapToRepoStars(AllegroRepoStars allegroRepoStars) {
        return new RepoStarsResponse(allegroRepoStars.getName(), allegroRepoStars.getStargazersCount());
    }

    public int getRepoStarsSum() {
        return externalSource.getAll(AllegroRepoStars[].class).stream()
                .mapToInt(AllegroRepoStars::getStargazersCount)
                .sum();
    }
}

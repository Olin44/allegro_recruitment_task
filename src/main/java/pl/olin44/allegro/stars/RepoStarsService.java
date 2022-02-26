package pl.olin44.allegro.stars;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.olin44.allegro.externalsource.ExternalSource;

import java.util.List;

@Service
@AllArgsConstructor
public class RepoStarsService {

    private final ExternalSource<AllegroRepoStars> externalSource;

    public List<RepoStarsResponse> getReposStars(String userName) {
        return externalSource.getAll(getRequestedEntityClass(), userName).stream()
                .map(this::mapToRepoStars)
                .toList();
    }

    private Class<AllegroRepoStars[]> getRequestedEntityClass() {
        return AllegroRepoStars[].class;
    }

    private RepoStarsResponse mapToRepoStars(AllegroRepoStars allegroRepoStars) {
        return new RepoStarsResponse(allegroRepoStars.getName(), allegroRepoStars.getStargazersCount());
    }

    public int getRepoStarsSum(String userName) {
        return externalSource.getAll(getRequestedEntityClass(), userName).stream()
                .mapToInt(AllegroRepoStars::getStargazersCount)
                .sum();
    }
}

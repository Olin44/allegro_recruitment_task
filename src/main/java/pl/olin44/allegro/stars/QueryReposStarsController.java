package pl.olin44.allegro.stars;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@AllArgsConstructor
public class QueryReposStarsController {

    private final RepoStarsService repoStarsService;

    @GetMapping("repos/stars")
    public ResponseEntity<List<RepoStarsResponse>> getReposStars() {
        return ResponseEntity.ok(repoStarsService.getReposStars());
    }

    @GetMapping("repos/stars/sum")
    public ResponseEntity<Integer> getStarsSum() {
        return ResponseEntity.ok(repoStarsService.getRepoStarsSum());
    }
}

package pl.olin44.allegro.stars;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@AllArgsConstructor
public class QueryReposStarsController {

    private final RepoStarsService repoStarsService;

    @GetMapping("{userName}/repos/stars")
    public ResponseEntity<List<RepoStarsResponse>> getReposStars(@PathVariable String userName) {
        return ResponseEntity.ok(repoStarsService.getReposStars(userName));
    }

    @GetMapping("{userName}/repos/stars/sum")
    public ResponseEntity<Integer> getStarsSum(@PathVariable String userName) {
        return ResponseEntity.ok(repoStarsService.getRepoStarsSum(userName));
    }
}

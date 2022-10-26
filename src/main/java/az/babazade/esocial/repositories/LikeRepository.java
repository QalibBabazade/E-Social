package az.babazade.esocial.repositories;

import az.babazade.esocial.entities.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {

    List<Like> findAllByUserIdAndPostIdAndActive(Long userId, Long postId, Integer active);

    List<Like> findAllByUserIdAndActive(Long userId, Integer active);

    List<Like> findAllByPostIdAndActive(Long postId, Integer active);

    List<Like> findAllByActive(Integer active);

    Optional<Like> findLikeByIdAndActive(Long id, Integer active);
}

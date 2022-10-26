package az.babazade.esocial.repositories;

import az.babazade.esocial.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {

    List<Comment> findAllByUserIdAndPostIdAndActive(Long userId, Long postId, Integer active);

    List<Comment> findAllByUserIdAndActive(Long userId, Integer active);

    List<Comment> findAllByPostIdAndActive(Long postId, Integer active);

    List<Comment> findAllByActive(Integer active);

    Optional<Comment> findCommentByIdAndActive(Long id, Integer active);
}

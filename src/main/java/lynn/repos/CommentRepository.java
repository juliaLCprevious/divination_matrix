package lynn.repos;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;

import lynn.models.Cell;
import lynn.models.Comment;

public interface CommentRepository extends GraphRepository<Comment> {
	
	@Query("MATCH (cell:Cell { name:{0} })-[:MESSAGES]->(comment:Comment) RETURN comment")
	Iterable<Cell> findCommentsByCellName(String name);

}

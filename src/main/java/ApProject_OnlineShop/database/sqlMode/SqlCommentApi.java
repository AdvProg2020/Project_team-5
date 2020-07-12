package ApProject_OnlineShop.database.sqlMode;

import ApProject_OnlineShop.database.sqlMode.entityUtils.EntityManagerProducer;
import ApProject_OnlineShop.model.persons.Person;
import ApProject_OnlineShop.model.productThings.Comment;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.stream.Collectors;

public class SqlCommentApi extends SqlAPIs<Comment> {
    public SqlCommentApi() {
        super(Comment.class);
    }

    public List<Comment> getCommentsByStatus(Comment.CommentStatus status) {
        EntityManager entityManager = EntityManagerProducer.getInstanceOfEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Comment> query = criteriaBuilder.createQuery(Comment.class);
        Root<Comment> root = query.from(Comment.class);

        query.select(root).where(criteriaBuilder.equal(root.get("CommentStatus"), status));
        query.distinct(true);
        TypedQuery<Comment> typedQuery = entityManager.createQuery(query);
        try {
            return typedQuery.getResultList();
        } catch (NoResultException e) {
            System.out.println(e.getMessage());
            return null;
        } finally {
            entityManager.close();
        }
    }

    public List<Comment> getCommentsByProductAndStatus(long goodId, Comment.CommentStatus status) {
        List<Comment> comments = getCommentsByStatus(status);
        return comments.stream().filter(comment -> comment.getGood().getGoodId() == goodId).collect(Collectors.toList());
    }
}

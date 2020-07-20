package ApProject_OnlineShop.model.productThings;

import ApProject_OnlineShop.model.Shop;
import ApProject_OnlineShop.model.persons.Customer;
import ApProject_OnlineShop.model.persons.Person;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Comment")
public class Comment implements Serializable {
    @Transient
    private static long commentIdCounter = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CommentID", nullable = false, unique = true)
    private long id;

    @ManyToOne
    @JoinColumn(name = "CustomerId", nullable = false)
    private Customer person;

    @ManyToOne
    @JoinColumn(name = "ProductID", nullable = false)
    private Good good;

    @Column(name = "Title", nullable = false)
    private String title;

    @Column(name = "Content")
    private String comment;

    @Enumerated(EnumType.STRING)
    @Column(name = "CommentStatus", nullable = false)
    private CommentStatus commentStatus;

    @Column(name = "isCommenterBuysThisProduct", nullable = false)
    private boolean didCommenterBoughtThisProduct;

    public enum CommentStatus {
        ACCEPTED,
        INPROGRESS,
    }

    public Comment(Person person, Good good, String title, String comment, boolean didCommenterBoughtThisProduct) {
        commentIdCounter++;
        this.person = (Customer) person;
        this.good = good;
        this.title = title;
        this.comment = comment;
        this.didCommenterBoughtThisProduct = didCommenterBoughtThisProduct;
        this.commentStatus = CommentStatus.INPROGRESS;
    }

    public Comment() {
    }

    public static long getCommentIdCounter() {
        return commentIdCounter;
    }

    public Person getPerson() {
        return this.person;
    }

    public static void setCommentIdCounter(long commentIdCounter) {
        Comment.commentIdCounter = commentIdCounter;
    }

    public long getId() {
        return id;
    }

    public Good getGood() {
        return this.good;
    }

    public CommentStatus getCommentStatus() {
        return commentStatus;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setPerson(Customer person) {
        this.person = person;
    }

    public void setGood(Good good) {
        this.good = good;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setDidCommenterBoughtThisProduct(boolean didCommenterBoughtThisProduct) {
        this.didCommenterBoughtThisProduct = didCommenterBoughtThisProduct;
    }

    public void setCommentStatus(CommentStatus commentStatus) {
        this.commentStatus = commentStatus;
    }

    public String getTitle() {
        return title;
    }

    public String getComment() {
        return comment;
    }

    public boolean isDidCommenterBoughtThisProduct() {
        return didCommenterBoughtThisProduct;
    }

    public String getPersonString(){
        return person.getUsername();
    }

    @Override
    public String toString() {
            return String.format("Commenter Username : %s\nProduct Id : %d\n" +
                        "Product Name : %s\nTitle : %s\nContent : %s\n", this.getPerson().getUsername(),
                this.getGood().getGoodId(), this.getGood().getName(), this.getTitle(), this.getComment());
    }
}

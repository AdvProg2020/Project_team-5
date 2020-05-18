package model.productThings;

import model.Shop;
import model.persons.Person;

public class Comment {
    private static long commentIdCounter = 1;
    private long id;
    private String person;
    private long good;
    private String title;
    private String comment;
    private CommentStatus commentStatus;
    private boolean didCommenterBoughtThisProduct;

    public enum CommentStatus {
        ACCEPTED,
        INPROGRESS,
        REJECTED
    }

    public Comment(Person person, Good good, String title, String comment, boolean didCommenterBoughtThisProduct) {
        this.id = commentIdCounter++;
        this.person = person.getUsername();
        this.good = good.getGoodId();
        this.title = title;
        this.comment = comment;
        this.didCommenterBoughtThisProduct = didCommenterBoughtThisProduct;
        this.commentStatus = CommentStatus.INPROGRESS;
    }

    public Person getPerson() {
        return Shop.getInstance().findUser(person);
    }

    public static void setCommentIdCounter(long commentIdCounter) {
        Comment.commentIdCounter = commentIdCounter;
    }

    public long getId() {
        return id;
    }

    public Good getGood() {
        return Shop.getInstance().findGoodById(this.good);
    }

    public String getTitle() {
        return title;
    }

    public String getComment() {
        return comment;
    }

    public CommentStatus getCommentStatus() {
        return commentStatus;
    }

    public boolean isDidCommenterBoughtThisProduct() {
        return didCommenterBoughtThisProduct;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setCommentStatus(CommentStatus commentStatus) {
        this.commentStatus = commentStatus;
    }

    @Override
    public String toString() {
        return String.format("Commenter Username : %s\nProduct Id : %d\n" +
                        "Product Name : %s\nTitle : %s\nContent : %s\n", this.person,
                this.good, this.getGood().getName(), this.title, this.comment);
    }
}

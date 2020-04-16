package model.productThings;

import model.persons.Person;

public class Comment {
    private Person person;
    private Good good;
    private String title;
    private String comment;
    private CommentStatus commentStatus;
    private boolean didCommenterBoughtThisProduct;

    enum CommentStatus {
        ACCEPTED,
        INPROGRESS,
        REJECTED
    }

    public Comment(Person person, Good good, String title, String comment, boolean didCommenterBoughtThisProduct) {
        this.person = person;
        this.good = good;
        this.title = title;
        this.comment = comment;
        this.didCommenterBoughtThisProduct = didCommenterBoughtThisProduct;
        this.commentStatus = CommentStatus.INPROGRESS;
    }

    public Person getPerson() {
        return person;
    }

    public Good getGood() {
        return good;
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
                        "Product Name : %s\nTitle : %s\nContent : %s\n", this.person.getUsername(),
                this.good.getGoodId(), this.good.getName(), this.title, this.comment);
    }
}

package model.productThings;

import model.persons.Person;

public class Comment {
    private Person person;
    private Good good;
    private String title;
    private String comment;
    private CommentStatus commentStatus;
    private boolean didCommenterBoughtThisProduct;

    public enum CommentStatus {
        ACCEPTED,
        INPROGRESS,
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

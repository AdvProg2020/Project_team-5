package model.requests;

import exception.FileCantBeSavedException;
import model.Shop;
import model.database.Database;
import model.persons.Person;
import model.productThings.Comment;
import model.productThings.Good;

import java.io.IOException;

public class AddingCommentRequest extends Request {
    private String username;
    private long goodId;
    private String title;
    private String comment;
    private boolean didCommenterBoughtThisProduct;

    public AddingCommentRequest(Person person, Good good, String title, String comment, boolean didCommenterBoughtThisProduct) {
        this.username = person.getUsername();
        this.goodId = good.getGoodId();
        this.title = title;
        this.comment = comment;
        this.didCommenterBoughtThisProduct = didCommenterBoughtThisProduct;
    }

    @Override
    public void acceptRequest() throws IOException, FileCantBeSavedException {
        Comment comment2=new Comment(Shop.getInstance().findUser(username),
                Shop.getInstance().findGoodById(goodId),this.title,this.comment,this.didCommenterBoughtThisProduct);
        comment2.getGood().addComment(comment2);
        comment2.setCommentStatus(Comment.CommentStatus.ACCEPTED);
        Shop.getInstance().addAComment(comment2);
       // Database.getInstance().saveItem(comment);
        //Database.getInstance().saveItem(comment.getGood().getSubCategory());
       // Database.getInstance().saveItem(comment.getGood().getSubCategory().getParentCategory());
    }
}

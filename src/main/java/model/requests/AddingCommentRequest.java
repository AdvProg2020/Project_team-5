package model.requests;

import exception.FileCantBeSavedException;
import model.Shop;
import model.database.Database;
import model.productThings.Comment;

import java.io.IOException;

public class AddingCommentRequest extends Request {
    private Comment comment;

    public AddingCommentRequest(Comment comment) {
        this.comment = comment;
    }

    @Override
    public void acceptRequest() throws IOException, FileCantBeSavedException {
        this.comment.getGood().addComment(comment);
        comment.setCommentStatus(Comment.CommentStatus.ACCEPTED);
        Shop.getInstance().addAComment(comment);
       // Database.getInstance().saveItem(comment);
        //Database.getInstance().saveItem(comment.getGood().getSubCategory());
       // Database.getInstance().saveItem(comment.getGood().getSubCategory().getParentCategory());
    }
}

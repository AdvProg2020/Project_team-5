package model.requests;

import exception.FileCantBeSavedException;
import model.Shop;
import model.database.Database;
import model.productThings.Comment;

import java.io.IOException;

public class AddingCommentRequest extends Request {
    private long comment;


    public AddingCommentRequest(Comment comment) {
        this.comment = comment.getId();
    }

    @Override
    public void acceptRequest() throws IOException, FileCantBeSavedException {
        Comment comment2=Shop.getInstance().getAllComments().get(this.comment);
        comment2.getGood().addComment(comment2);
        comment2.setCommentStatus(Comment.CommentStatus.ACCEPTED);
       // Database.getInstance().saveItem(comment);
        //Database.getInstance().saveItem(comment.getGood().getSubCategory());
       // Database.getInstance().saveItem(comment.getGood().getSubCategory().getParentCategory());
    }
}

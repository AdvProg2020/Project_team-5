package model.requests;

import model.productThings.Comment;

public class AddingCommentRequest extends Request {
    private Comment comment;

    public AddingCommentRequest(Comment comment) {
        this.comment = comment;
    }

    @Override
    public void acceptRequest() {
        this.comment.getGood().addComment(comment);
        comment.setCommentStatus(Comment.CommentStatus.ACCEPTED);
    }
}

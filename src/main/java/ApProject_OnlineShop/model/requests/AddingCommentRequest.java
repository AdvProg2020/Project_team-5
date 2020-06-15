package ApProject_OnlineShop.model.requests;

import ApProject_OnlineShop.database.Database;
import ApProject_OnlineShop.exception.FileCantBeSavedException;
import ApProject_OnlineShop.model.Shop;
import ApProject_OnlineShop.model.persons.Person;
import ApProject_OnlineShop.model.productThings.Comment;
import ApProject_OnlineShop.model.productThings.Good;

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
        Comment comment2 = new Comment(Shop.getInstance().findUser(username),
                Shop.getInstance().findGoodById(goodId),this.title,this.comment,this.didCommenterBoughtThisProduct);
        comment2.getGood().addComment(comment2);
        comment2.setCommentStatus(Comment.CommentStatus.ACCEPTED);
        Shop.getInstance().addAComment(comment2);
        Database.getInstance().saveItem(comment2);
        Database.getInstance().saveItem(comment2.getGood());
    }

    @Override
    public String toString() {
        return "Type: Adding Comment Request\n" + "Commenter: " + username + "\ngood id: " + goodId
                + "\ntitle: " + title + "\ncontent: " + comment;
    }
}

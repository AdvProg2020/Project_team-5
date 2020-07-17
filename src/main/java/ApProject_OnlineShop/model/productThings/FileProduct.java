package ApProject_OnlineShop.model.productThings;

import ApProject_OnlineShop.model.Shop;
import ApProject_OnlineShop.model.category.SubCategory;
import ApProject_OnlineShop.model.persons.Seller;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;

public class FileProduct {
    private long fileProductId;
    private String name;
    private String subCategory;
    private String seller;
    private long price;
    private String description;
    private int downloadNumber;
    private ArrayList<Long> comments;
    private LocalDate modificationDate;

    public FileProduct(String name, SubCategory subCategory, Seller seller, long price, String description) {
        fileProductId = Good.getGoodsCount() + 1;
        Good.setGoodsCount(fileProductId);
        this.name = name;
        this.subCategory = subCategory.getName();
        this.seller = seller.getUsername();
        this.price = price;
        this.description = description;
        this.downloadNumber = 0;
        this.comments = new ArrayList<>();
        this.modificationDate = LocalDate.now();
    }

    public File getFile() {
        return new File("Resources/fileProducts/" + this.name);
    }

    public FileProduct() {
    }

    public long getFileProductId() {
        return fileProductId;
    }

    public void setFileProductId(long fileProductId) {
        this.fileProductId = fileProductId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SubCategory getSubCategory() {
        return Shop.getInstance().findSubCategoryByName(subCategory);
    }

    public void setSubCategory(SubCategory subCategory) {
        this.subCategory = subCategory.getName();
    }

    public Seller getSeller() {
        return (Seller)Shop.getInstance().findUser(seller);
    }

    public void setSeller(Seller seller) {
        this.seller = seller.getUsername();
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDownloadNumber() {
        return downloadNumber;
    }

    public void increaseDownloadNumber() {
        this.downloadNumber++;
    }

    public ArrayList<Comment> getComments() {
        ArrayList<Comment> allComments = new ArrayList<>();
        for (Long commentId : comments) {
            allComments.add(Shop.getInstance().getAllComments().get(commentId));
        }
        return allComments;
    }

    public void addComment(Comment comment) {
        this.comments.add(comment.getId());
    }

    public LocalDate getModificationDate() {
        return modificationDate;
    }
}

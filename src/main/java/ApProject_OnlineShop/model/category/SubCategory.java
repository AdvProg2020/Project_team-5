package ApProject_OnlineShop.model.category;

import ApProject_OnlineShop.database.fileMode.Database;
import ApProject_OnlineShop.exception.FileCantBeSavedException;
import ApProject_OnlineShop.model.productThings.Good;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "SubCategory")
public class SubCategory implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SubCategoryId", nullable = false, unique = true)
    private int subCategoryId;

    @Column(name = "Name", nullable = false, unique = true)
    private String name;

    @ManyToOne
    @JoinColumn(name = "Category", referencedColumnName = "CategoryId")
    private Category parentCategory;

    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    @Cascade(value = org.hibernate.annotations.CascadeType.ALL)
    @CollectionTable(name = "DetailOfEachSubCategory", joinColumns = @JoinColumn(name = "SubCategory"))
    @Column(name = "Property")
    private List<String> details;

    @OneToMany(mappedBy = "subCategory", cascade = CascadeType.ALL)
    private List<Good> goods;

    public SubCategory(String name, ArrayList<String> details) {
        this.name = name;
        this.details = details;
        this.goods = new ArrayList<>();
    }

    public SubCategory() {
        this.goods = new ArrayList<>();
    }

    public void setParentCategory(Category parentCategory) {
        this.parentCategory= parentCategory;
    }

    public String getName() {
        return name;
    }

    public Category getParentCategory() {
        return this.parentCategory;
    }

    public List<String> getDetails() {
        return details;
    }

    public List<Good> getGoods() {
        return this.goods;
        /*ArrayList<Good> goods1=new ArrayList<>();
        for (Long id : goods) {
            goods1.add(Shop.getInstance().getAvailableGood(id));
        }
        return goods1;*/
    }

    public void addGood(Good good) {
        this.goods.add(good);
        //this.goods.add(good.getGoodId());
    }

    public int getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(int subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDetails(ArrayList<String> details) {
        this.details = details;
    }

    public void setGoods(ArrayList<Good> goods) {
        this.goods = goods;
    }

    public void deleteGood(Good good) throws IOException, FileCantBeSavedException {
        this.goods.remove(good);
        Database.getInstance().saveItem(this);
    }

    public Good findGoodById(long goodId) {
        for (Good good : getGoods()) {
            if (good.getGoodId() == goodId)
                return good;
        }
        return null;
    }

    @Override
    public String toString() {
        String subCategoryStr = getName() + " of " + parentCategory + " category";
        subCategoryStr += "\nproperties =";
        for (int i = 0; i < details.size() + getParentCategory().getDetails().size(); i++) {
            subCategoryStr += ("\n" + (i + 1) + "- ");
            if (i < getParentCategory().getDetails().size())
                subCategoryStr += getParentCategory().getDetails().get(i);
            else
                subCategoryStr += details.get(i - getParentCategory().getDetails().size());
        }
        subCategoryStr += "\nProducts =\n";
        for (Good good : getGoods()) {
            subCategoryStr += (good.toString());
        }
        return subCategoryStr;
    }
}

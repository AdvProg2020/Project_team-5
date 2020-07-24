package ApProject_OnlineShop.model.category;

import ApProject_OnlineShop.model.Shop;
import ApProject_OnlineShop.model.productThings.Good;
import com.google.gson.annotations.Expose;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "Category")
//@SecondaryTable(name = "DetailOfEachCategory", pkJoinColumns = @PrimaryKeyJoinColumn(name = "Category", referencedColumnName = "CategoryId"))
public class Category implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CategoryId", nullable = false, unique = true)
    @Expose
    private int categoryId;

    @Column(name = "Name", nullable = false, unique = true)
    @Expose
    private String name;

    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @Column(name = "Property")
    @CollectionTable(name = "DetailOfEachCategory", joinColumns = @JoinColumn(name = "Category", referencedColumnName = "CategoryId"))
    @Expose
    private List<String> details;

    @OneToMany(mappedBy = "parentCategory")
    private List<SubCategory> subCategories;

    public Category(String name, ArrayList<String> details) {
        this.name = name;
        this.details = details;
        this.subCategories = new ArrayList<>();
    }

    public Category() {
    }

    public String getName() {
        return name;
    }

    public List<String> getDetails() {
        return details;
    }

    public List<SubCategory> getSubCategories() {
        return subCategories;
        /*ArrayList<SubCategory> subCategories2=new ArrayList<>();
        for (String subCategory : this.subCategories) {
            subCategories2.add(Shop.getInstance().getSubCategory(subCategory));
        }
        return subCategories2;*/
    }

    public void addSubCategory(SubCategory subCategory) {
        this.subCategories.add(subCategory);
        //this.subCategories.add(subCategory.getName());
        subCategory.setParentCategory(this);
    }

    public void deleteSubCategory(SubCategory subCategory) {
        removeSubCategoryFromList(subCategory);
    }

    public void removeSubCategoryFromList(SubCategory subCategory) {
        this.subCategories.remove(subCategory);
    }

    public Good findGoodInSubCategories(long goodId) {
        for (SubCategory subCategory : this.getSubCategories()) {
            if (subCategory.findGoodById(goodId) != null)
                return subCategory.findGoodById(goodId);
        }
        return null;
    }

    public SubCategory findSubCategoryByName(String subcategoryName) {
        for (SubCategory subCategory : this.getSubCategories()) {
            if (subCategory.getName().equals(subcategoryName))
                return subCategory;
        }
        return null;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDetails(ArrayList<String> details) {
        this.details = details;
    }

    public void setSubCategories(ArrayList<SubCategory> subCategories) {
        this.subCategories = subCategories;
    }

    public ArrayList<String> getSubCategoriesString() {
        return this.subCategories.stream().map(SubCategory::getName).collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public String toString() {
        String categoryStr = "Name of Category = " + getName() + "\nSubcategories =";
        for (int i = 0; i < subCategories.size(); i++) {
            categoryStr += ("\n" + (i + 1) + "- " + subCategories.get(i));
        }
        categoryStr += "\nSpecial properties :";
        for (int i = 0; i < details.size(); i++) {
            categoryStr += ("\n" + (i + 1) + "- " + details.get(i));
        }
        return (categoryStr + "\n");
    }

}

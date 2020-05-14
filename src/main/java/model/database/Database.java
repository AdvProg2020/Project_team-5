package model.database;

public class Database {
    private DeletingData deletingData;
    private SavingData savingData;
    private LoadingData loadingData;

    public Database() {
        this.deletingData = new DeletingData();
        this.savingData = new SavingData();
        this.loadingData = new LoadingData();
    }

    public void initializeShop() {

    }

    public void deleteItem(Object item) {

    }

    public void saveItem(Object item) {

    }
}

package springJDBC.tx;

public interface Dao {
    public int findBookPriceByIsbn(String isbn);

    public void updateBookStock(String isbn);

    public void updateUserAccount(String username, int price);

}

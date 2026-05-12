/// Interface for product handling (Product.java)

public interface ProductHandling{

    @Override
    public String toString();
    @Override
    public boolean equals(Object obj);

    public int getQuantity();
    public String getColor();
    public String getSize();
    public String getName();
    public void increaseQuantity(int quantity);
    public void decreaseQuantity(int quantity);

    public void checkAttributes() throws InvalidProductAttribute;

}

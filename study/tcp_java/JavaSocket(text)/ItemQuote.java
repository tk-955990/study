public class ItemQuote {

  public long itemNumber;         // �����ֹ�
  public String itemDescription;  // ���ʤ�����
  public int quantity;            // ���Ѥ��ξ��ʤο���ɬ��1�ʾ��
  public int unitPrice;           // ���ʤ�ñ���ʥ����ñ�̡�
  public boolean discounted;      // ���ʤ��Ͱ�����ȿ�Ǥ���Ƥ��뤫�ɤ���
  public boolean inStock;         // ���ʤκ߸ˤ����뤫�ɤ���

  public ItemQuote(long itemNumber, String itemDescription,
      int quantity, int unitPrice, boolean discounted, boolean inStock) {
    this.itemNumber = itemNumber;
    this.itemDescription = itemDescription;
    this.quantity = quantity;
    this.unitPrice = unitPrice;
    this.discounted = discounted;
    this.inStock = inStock;
  }

  public String toString() {
    final String EOLN = java.lang.System.getProperty("line.separator");
    String value = "Item#=" + itemNumber + EOLN +
           "Description=" + itemDescription + EOLN +
           "Quantity=" + quantity + EOLN +
           "Price(each)=" + unitPrice + EOLN +
           "Total=" + (quantity * unitPrice);
    if (discounted)
      value += " (discounted)";
    if (inStock)
      value += EOLN + "In Stock" + EOLN;
    else
      value += EOLN + "Out of Stock" + EOLN;
    return value;
  }
}

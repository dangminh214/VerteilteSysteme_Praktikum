package borse;


public class Wertpapier {
  private CodeOfWertpapier codeOfWertpapier;
  private int quantity ;
  private int price;


  public CodeOfWertpapier getCode() {
    return codeOfWertpapier;
  }

  public void setCode(CodeOfWertpapier codeOfWertpapier) {
    this.codeOfWertpapier = codeOfWertpapier;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public int getPrice() {
    return price;
  }

  public void setPrice(int price) {
    this.price = price;
  }
  public Wertpapier(){
    this.price = RandomDataForWertpapierGenerator.getData(200,300);
    this.codeOfWertpapier = CodeOfWertpapier.values()[ RandomDataForWertpapierGenerator.getData(0, CodeOfWertpapier.values().length-1)];
    this.quantity = RandomDataForWertpapierGenerator.getData(-10,50);
  }
  public Wertpapier(CodeOfWertpapier codeOfWertpapier, int quantity, int price){
    this.codeOfWertpapier = codeOfWertpapier;
    this.price = price;
    this.quantity = quantity;
  }
  /*
  public int generateData(int min, int max){
    return random.nextInt(max - min + 1) + min;

  }*/

    public String toString(){
    return quantity+"," + codeOfWertpapier.toString()+ ","+ price;
  }


}

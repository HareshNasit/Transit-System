package user;

public class Card {
  private double balance;
  private boolean suspended;
  
  public Card() {
    balance = 19;
    suspended = false;
  }
  
  protected void suspend() {
    suspended = true;
  }
  
  public boolean isSuspended() {
    return suspended;
  }
  
  /* Returns true if the add balance action was successful and false otherwise.*/
  protected boolean addBalance(int amount) {
    if (amount == 10 || amount == 20 || amount == 50) {
      balance += amount;
      return true;
    }
    return false;
  }
  
  public double getBalance() {
    return balance;
  }
  
  // TODO: tap functionality
  // Return true if successful, false otherwise.
  public boolean tap() {
    return false;
  }
}

public class Account {

    private Integer accountNumber;
    private String pin;
    private String name;
    private Long balance;

    public Account(Integer accountNumber, String pin, String name, Long balance) {
        this.accountNumber = accountNumber;
        this.pin = pin;
        this.name = name;
        this.balance = balance;
    }

    public Integer getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Integer accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }
}

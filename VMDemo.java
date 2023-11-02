interface VMState {
    void insert(int amount);
    void select(String item);
    void dispense(String item);
}

class VendingContext {
    private VMState state;
    private int balance;

    public VendingContext() {
        state = new NoMoneyState(this);
    }

    public void setState(VMState state) {
        this.state = state;
    }

    public void insert(int amount) {
        state.insert(amount);
    }

    public void select(String item) {
        state.select(item);
    }

    public void dispense(String item) {
        state.dispense(item);
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getBalance() {
        return balance;
    }
}

class NoMoneyState implements VMState {
    private final VendingContext vm;

    public NoMoneyState(VendingContext vm) {
        this.vm = vm;
    }

    @Override
    public void insert(int amount) {
        vm.setBalance(amount);
        System.out.println(amount + " inserted. Total balance: " + vm.getBalance());
        vm.setState(new HasMoneyState(vm));
    }

    @Override
    public void select(String item) {
        System.out.println("Please insert money first.");
    }

    @Override
    public void dispense(String item) {
        System.out.println("Please insert money first.");
    }
}

class HasMoneyState implements VMState {
    private final VendingContext vm;

    public HasMoneyState(VendingContext vm) {
        this.vm = vm;
    }

    @Override
    public void insert(int amount) {
        vm.setBalance(vm.getBalance() + amount);
        System.out.println(amount + " more inserted. Total balance: " + vm.getBalance());
    }

    @Override
    public void select(String item) {
        if (vm.getBalance() >= 5) {
            vm.dispense(item);
            vm.setBalance(vm.getBalance() - 5);
        } else {
            System.out.println("Insufficient balance. Please insert more money.");
        }
    }

    @Override
    public void dispense(String item) {
        System.out.println("Dispensing " + item + ". Enjoy your " + item + "!");
        vm.setState(new NoMoney(vm));
    }
}

public class VMDemo {
    public static void main(String[] args) {
        VendingContext vm = new VendingContext();

        vm.select("Soda"); 
        vm.insert(10);     
        vm.select("Chips");  
    }
}

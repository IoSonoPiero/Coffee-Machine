package machine;

public class TheMachine {
    int water;
    int milk;
    int coffee;
    int disposableCups;
    int money;
    MachineState machineState;

    public TheMachine(int water, int milk, int coffee, int disposableCups, int money) {
        this.water = water;
        this.milk = milk;
        this.coffee = coffee;
        this.disposableCups = disposableCups;
        this.money = money;

        // Power ON machine
        setNormalState();
    }

    private void setNormalState() {
        machineState = MachineState.NORMAL;
        // print menu
        System.out.println("Write action (buy, fill, take, remaining, exit):");
    }

    public boolean isNormal() {
        if (machineState == MachineState.OFF) {
            return false;
        }
        return true;
    }

    public void doAction(String input) {
        switch (machineState) {
            case NORMAL:
                setMachineState(input);
                break;
            case OFF:
                break;
            case BUY:
                buyCoffee(input);
                setNormalState();
                break;
            case TAKE:
                takeMoney();
                setNormalState();
                break;
            case FILL_WATER:
                water += Integer.parseInt(input);
                System.out.println("Write how many ml of milk you want to add:");
                machineState = MachineState.FILL_MILK;
                break;
            case FILL_MILK:
                milk += Integer.parseInt(input);
                System.out.println("Write how many grams of coffee beans you want to add:");
                machineState = MachineState.FILL_COFFEE;
                break;
            case FILL_COFFEE:
                coffee += Integer.parseInt(input);
                System.out.println("Write how many disposable cups of coffee you want to add:");
                machineState = MachineState.FILL_CUPS;
                break;
            case FILL_CUPS:
                disposableCups += Integer.parseInt(input);
                setNormalState();
                break;
        }
    }

    private void setMachineState(String command) {
        switch (command) {
            case "remaining":
                printMachineState();
                setNormalState();
                break;
            case "buy":
                System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:");
                machineState = MachineState.BUY;
                break;
            case "fill":
                System.out.println("Write how many ml of water you want to add:");
                machineState = MachineState.FILL_WATER;
                break;
            case "take":
                takeMoney();
                setNormalState();
                break;
            case "exit":
                machineState = MachineState.OFF;
                break;
        }
    }

    private void takeMoney() {
        System.out.printf("I gave you $%d\n", this.money);
        this.money = 0;
    }

    private void printMachineState() {
        System.out.printf("The coffee machine has:\n%d ml of water\n%d ml of milk\n", water, milk);
        System.out.printf("%d g of coffee beans\n%d disposable cups\n$%d of money\n", coffee, disposableCups, money);
    }

    private void buyCoffee(String input) {
        CoffeeTypes coffeeType;

        switch (input) {
            case "back":
                machineState = MachineState.NORMAL;
                return;
            case "1":
                coffeeType = CoffeeTypes.ESPRESSO;
                break;
            case "2":
                coffeeType = CoffeeTypes.LATTE;
                break;
            case "3":
                coffeeType = CoffeeTypes.CAPPUCCINO;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + input);
        }

        if (makeCoffee(coffeeType)) {
            money += coffeeType.price;
        }
    }

    private boolean makeCoffee(CoffeeTypes coffeeType) {
        if (water < coffeeType.water) {
            System.out.println("Sorry, not enough water!");
            return false;
        }

        if (milk < coffeeType.milk) {
            System.out.println("Sorry, not enough milk!");
            return false;
        }

        if (coffee < coffeeType.coffee) {
            System.out.println("Sorry, not enough coffee beans!");
            return false;
        }

        if (disposableCups < 1) {
            System.out.println("Sorry, not enough disposable cups!");
            return false;
        }

        water -= coffeeType.water;
        milk -= coffeeType.milk;
        coffee -= coffeeType.coffee;
        disposableCups--;

        System.out.println("I have enough resources, making you a coffee!");
        return true;
    }
}
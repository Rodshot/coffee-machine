package machine;

public class Machine {
    private MachineState state;
    private int water;
    private int milk;
    private int beans;
    private int cups;
    private int money;

    Machine(int water, int milk, int beans, int cups, int money) {
        this.water = water;
        this.milk = milk;
        this.beans = beans;
        this.cups = cups;
        this.money = money;
        state = MachineState.MAIN;
        System.out.print("Write action (buy, fill, take, remaining, exit):\n> ");
    }
    public boolean isOn() {
        return state != MachineState.EXIT;
    }

    public void mainLoop(String input) {
        switch(state) {
            case MAIN:
                chooseAction(input);
                break;
            case BUYING:
                buyAction(input);
                setMainState();
                break;
            case FILLING_WATER:
                water += Integer.parseInt(input);
                System.out.print("Write how many ml of milk you want to add:\n> ");
                state = MachineState.FILLING_MILK;
                break;
            case FILLING_MILK:
                milk += Integer.parseInt(input);
                System.out.print("Write how many grams of coffee beans you want to add:\n> ");
                state = MachineState.FILLING_BEANS;
                break;
            case FILLING_BEANS:
                beans += Integer.parseInt(input);
                System.out.print("Write how many disposable cups of coffee you want to add:\n> ");
                state = MachineState.FILLING_CUPS;
                break;
            case FILLING_CUPS:
                cups += Integer.parseInt(input);
                setMainState();
                break;
            case EXIT:
                break;
            default:
                break;
        }
    }

    private void chooseAction(String action) {
        switch(action) {
            case "buy":
                System.out.print("\nWhat do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:\n> ");
                state = MachineState.BUYING;
                break;
            case "fill":
                System.out.print("\nWrite how many ml of water do you want to add:\n> ");
                state = MachineState.FILLING_WATER;
                break;
            case "take":
                takeAction();
                setMainState();
                break;
            case "remaining":
                remainingAction();
                setMainState();
                break;
            case "exit":
                state = MachineState.EXIT;
                return;
            default:
                System.out.println("Incorrect action!");
                setMainState();
        }
    }

    private void setMainState() {
        state = MachineState.MAIN;
        System.out.print("\nWrite action (buy, fill, take, remaining, exit):\n> ");
    }

    private void buyAction(String action) {
        CoffeeRecipe coffeeRecipe;
        switch (action) {
            case "1":
                coffeeRecipe = CoffeeRecipe.ESPRESSO;
                break;
            case "2":
                coffeeRecipe = CoffeeRecipe.LATTE;
                break;
            case "3":
                coffeeRecipe = CoffeeRecipe.CAPPUCCINO;
                break;
            case "back":
                state = MachineState.MAIN;
                return;
            default:
                System.out.println("Incorrect value!");
                return;
        }
        makeCoffee(coffeeRecipe);
    }

    private void makeCoffee(CoffeeRecipe coffeeRecipe) {
        if (water < coffeeRecipe.getWater()) {
            System.out.println("Sorry, not enough water!");
        } else if (beans < coffeeRecipe.getBeans()) {
            System.out.println("Sorry, not enough coffee beans!");
        } else if (milk < coffeeRecipe.getMilk()) {
            System.out.println("Sorry, not enough milk!");
        } else if (cups < 1) {
            System.out.println("Sorry, not enough disposable cups of coffee!");
        } else {
            System.out.println("I have enough resources, making you a coffee!");
            water -= coffeeRecipe.getWater();
            beans -= coffeeRecipe.getBeans();
            milk -= coffeeRecipe.getMilk();
            cups -= 1;
            money += coffeeRecipe.getPrice();
        }
    }

    private void takeAction() {
        System.out.printf("\nI gave you $%d%n", money);
        money = 0;
    }

    private void remainingAction() {
        System.out.println("\nThe coffee machine has:");
        System.out.printf("%d ml of water%n", water);
        System.out.printf("%d ml of milk%n", milk);
        System.out.printf("%d g of coffee beans%n", beans);
        System.out.printf("%d disposable cups%n", cups);
        System.out.printf("$%d of money%n", money);
    }

}

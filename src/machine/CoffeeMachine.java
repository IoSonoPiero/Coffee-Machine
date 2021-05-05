package machine;

import java.util.Scanner;

public class CoffeeMachine {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TheMachine theMachine = new TheMachine(400, 540, 120, 9, 550);

        do {
            theMachine.doAction(scanner.next());
        } while (theMachine.isNormal());


    }
}
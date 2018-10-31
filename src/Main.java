import java.util.Comparator;

public class Main {

    public static void main(String[] args) {
        ObligSBinTre<Character> tre = new ObligSBinTre<>(Comparator.naturalOrder());

        char[] verdier = "IATBHJCRSOFELKGDMPQN".toCharArray();

        for (char c : verdier) tre.leggInn(c);


        for (Character c : tre) {
            System.out.print(c + " "); // D G K N Q S
        }


    }
}

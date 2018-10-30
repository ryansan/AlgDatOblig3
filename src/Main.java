import java.util.Comparator;

public class Main {

    public static void main(String[] args) {
        ObligSBinTre<Character> tre = new ObligSBinTre<>(Comparator.naturalOrder());

        char[] verdier = "IATBHJCRSOFELKGDMPQN".toCharArray();

        for (char c : verdier) tre.leggInn(c);


        System.out.println(tre.lengstGren());
        // Utskrift: [I, T, J, R, S] [I, A, B, H, C, F, E, D]
    }
}

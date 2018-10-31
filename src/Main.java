import java.util.Comparator;

public class Main {

    public static void main(String[] args) {
        ObligSBinTre<Character> tre = new ObligSBinTre<>(Comparator.naturalOrder());

        char[] verdier = "IATBHJCRSOFELKGDMPQN".toCharArray();

        for (char c : verdier) tre.leggInn(c);

        System.out.println(tre.postString());
// [D, E, G, F, C, H, B, A, K, N, M, L, Q, P, O, S, R, J, T, I]

    }
}

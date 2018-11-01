import java.util.Comparator;

public class Main {

    public static void main(String[] args) {



        ObligSBinTre<Character> tre = new ObligSBinTre<>(Comparator.naturalOrder());




        char[] verdier = "IATBHJCRSOFELKGDMPQN".toCharArray();

        for (char c : verdier) tre.leggInn(c);

        while (!tre.tom()) {
            System.out.println(tre);
            tre.fjernHvis(x -> true);
        }
        int x = 2;

        /*
        [A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T]
        //BORT: D, G, K, N Q, S

        [A, B, C, E, F, H, I, J, L, M, O, P, R, T]
        //BORT: E, M, P

        [A, B, C, F, H, I, J, L, O, R, T]
        //BORT: F, L

        [A, B, C, H, I, J, O, R, T]
        //BORT: C, O

        [A, B, H, I, J, R, T]
        //BORT: H, R

        [A, B, I, J, T]
        //BORT: B, J

        [A, I, T]
        //BORT: A, T

        [I]
        */

    }
}

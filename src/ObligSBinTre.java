////////////////// ObligSBinTre /////////////////////////////////
/*

    S326149 Ryanjit Singh Sangha

 */
import java.util.*;
public class ObligSBinTre<T> implements Beholder<T>{
        // en indre nodeklasse
        // nodens verdi
        // venstre og høyre barn
        // forelder



        private static final class Node<T> {

            private T verdi;
            private Node<T> venstre, høyre;
            private Node<T> forelder;

            // konstruktør
            private Node(T verdi, Node<T> v, Node<T> h, Node<T> forelder) {
                this.verdi = verdi; venstre = v; høyre = h; this.forelder = forelder;
            }

            // konstruktør
            private Node(T verdi, Node<T> forelder) {
                this(verdi, null, null, forelder);
        }

        @Override
        public String toString(){
            return "" + verdi;
        }
}
// class Node

        private Node<T> rot; // peker til rotnoden
        private int antall; // antall noder
        private int endringer; // antall endringer
        private final Comparator<? super T> comp; // komparator
        public ObligSBinTre(Comparator<? super T> c) // konstruktør
        {
            rot = null; antall = 0; comp = c;
        }

        @Override
        public final boolean leggInn(T verdi)    // skal ligge i class SBinTre
        {
            Objects.requireNonNull(verdi, "Ulovlig med nullverdier!");

            Node<T> p = rot, q = null;               // p starter i roten
            int cmp = 0;                             // hjelpevariabel

            while (p != null)       // fortsetter til p er ute av treet
            {
                q = p;                                 // q er forelder til p
                cmp = comp.compare(verdi,p.verdi);     // bruker komparatoren
                p = cmp < 0 ? p.venstre : p.høyre;     // flytter p
            }

            // p er nå null, dvs. ute av treet, q er den siste vi passerte

            p = new Node<>(verdi,q);                   // oppretter en ny node

            if (q == null) rot = p;                  // p blir rotnode
            else if (cmp < 0) q.venstre = p;         // venstre barn til q
            else q.høyre = p;                        // høyre barn til q

            endringer++;                             // endringer øker med én verdi
            antall++;                                // én verdi mer i treet
            return true;                             // vellykket innlegging
        }

        @Override

        public boolean inneholder(T verdi) {
            if (verdi == null) return false; Node<T> p = rot;
            while (p != null) {
                int cmp = comp.compare(verdi, p.verdi);
                if (cmp < 0) p = p.venstre;
                else if (cmp > 0) p = p.høyre;
                else return true;
            }
            return false;
        }

        @Override
        public boolean fjern(T verdi) {

            if (verdi == null) {
                return false;  // treet har ingen nullverdier
            }

            Node<T> p = rot, forelderTilP = null;   // forelderTilP skal være forelder til p

            while (p != null){            // leter etter verdi

                int cmp = comp.compare(verdi,p.verdi);      // sammenligner

                if (cmp < 0) {      // går til venstre
                    forelderTilP = p;
                    p = p.venstre;
                }

                else if (cmp > 0) {  // går til høyre
                    forelderTilP = p;
                    p = p.høyre;
                }
                else {
                    break;    // den søkte verdien ligger i p
                }
            }


            if (p == null) {
                return false;   // finner ikke verdi
            }

            if (p.venstre == null || p.høyre == null)  // Tilfelle 1) og 2)
            {

                if(p.venstre == null && p.høyre == null){
                    if(p == rot){
                        rot = null;
                    }
                    else if(p == forelderTilP.venstre){
                        forelderTilP.venstre = null;
                    }else if(p == forelderTilP.høyre) {
                        forelderTilP.høyre = null;
                    }
                }else{
                    Node<T> barn = p.venstre != null ? p.venstre : p.høyre;  // b for barn

                    if (p == rot) {
                        rot = barn;
                    }
                    else if (p == forelderTilP.venstre) {
                        forelderTilP.venstre = barn;
                        barn.forelder = forelderTilP;
                    }
                    else {
                        forelderTilP.høyre = barn;
                        barn.forelder = forelderTilP;
                    }
                }


            }
            else  // Tilfelle 3)
            {
                Node<T> s = p, r = p.høyre;   // finner neste i inorden

                while (r.venstre != null) {
                    s = r;    // s er forelder til r
                    r = r.venstre;
                }

                p.verdi = r.verdi;   // kopierer verdien i r til p

                if (s != p) {
                    s.venstre = r.høyre;
                } else {
                    s.høyre = r.høyre;
                }
            }

            endringer++;
            antall--;   // det er nå én node mindre i treet
            return true;
        }


        public int fjernAlle(T verdi) {

            Node<T> p = rot;

            int antallF = 0;

            while(fjern(verdi)){
                antallF++;
            }

            return antallF;
        }


        @Override
        public int antall() {
            return antall;
        }

        public int antall(T verdi) {
            Node<T> p = rot;
            int antallAvVerdi = 0;

            while (p != null)
            {
                int cmp = comp.compare(verdi,p.verdi);
                if (cmp < 0) p = p.venstre;
                else
                {
                    if (cmp == 0) antallAvVerdi++;
                    p = p.høyre;
                }
            }
            return antallAvVerdi;
        }

        @Override

        public boolean tom() {

            return antall == 0;

        }


        @Override
        public void nullstill() {

            if(tom()){
                return;
            }

            Node<T> p = rot;

            while (p.venstre != null) {
                p = p.venstre;
            }

            while(!tom()){
                fjern(p.verdi);
                p = nesteInorden(p);
            }

            endringer++;
        }

        private static <T> Node<T> nesteInorden(Node<T> p) {

            if (p.høyre != null){  // p har høyre barn
                p = p.høyre;
                while (p.venstre != null) {
                    p = p.venstre;
                }
                return p;
            }
            else { // må gå oppover i treet
                while (p.forelder != null && p.forelder.høyre == p) {
                    p = p.forelder;
                }
                return p.forelder;
            }
        }


        @Override
        public String toString() {


            if(tom()){
                return "[]";
            }

            StringBuilder sb = new StringBuilder();

            sb.append("[");

            Node<T> p = rot;
            while (p.venstre != null) {
                p = p.venstre;
            }

            sb.append(p.verdi);
            p = nesteInorden(p);


            if(!tom()){
                while(p != null){
                    sb.append(", " + p.verdi);
                    p = nesteInorden(p);
                }
            }

            sb.append("]");
            return sb.toString();
        }


        public String omvendtString() {

            StringBuilder sb = new StringBuilder();
            sb.append("[");

            if (tom()) {
                return "[]";            // tomt tre
            }

            int teller = antall();

            if(teller == 1){
                return "[" + rot + "]";
            }

            Deque<Node<T>> stakk = new ArrayDeque<>();

            Node<T> p = rot;   // starter i roten og går til venstre

            for ( ; p.høyre != null; p = p.høyre) {
                stakk.push(p);
            }


            while (true)
            {
                //      // oppgaven utføres

                if(teller == 1){
                    sb.append(p.verdi + "]");
                }else{
                    sb.append(p.verdi + ", ");
                    teller--;
                }


                if (p.venstre != null){          // til venstre i høyre subtre

                    for (p = p.venstre; p.høyre != null; p = p.høyre){
                        stakk.push(p);
                    }
                }
                else if (!stakk.isEmpty()){
                    p = stakk.pop();   // p.høyre == null, henter fra stakken
                }
                else break;          // stakken er tom - vi er ferdig

            } // while

            return sb.toString();
        }



        public String høyreGren() {

            if (tom()) {
                return "[]";
            }

            if(antall == 1){
                return "[" + rot + "]";
            }

            StringBuilder sb = new StringBuilder();

            sb.append("[");

            Node<T> p = rot;

            sb.append(rot.verdi + ", ");


            while(p.venstre != null || p.høyre != null){

                if(p.høyre != null){
                    p = p.høyre;
                }
                else if(p.venstre != null){
                    p = p.venstre;
                }
                if(p.venstre == null && p.høyre == null){
                    sb.append(p.verdi + "]");
                }else{
                    sb.append(p.verdi + ", ");
                }

            }

            return sb.toString();
        }



        public String lengstGren() {
            StringBuilder sb = new StringBuilder();

            if(antall == 0){
                return "[]";
            }

            Node<T> p = rot;

            while (p.venstre != null) {
                p = p.venstre;
            }

            Deque<Node> bladNoder = new ArrayDeque();
            bladNoder.push(p);

            while(p != null){
                p = nesteInorden(p);
                if(p == null){
                    break;
                }
                if(p.venstre == null && p.høyre == null){
                    bladNoder.push(p);
                }
            }

            //for hver bladnode, sjekk antall linjer til rot

            int maxLength = 0;
            Node<T> finalNode = null;

            Node<T> node = null;

            while(!bladNoder.isEmpty()){
                int currentLength = 0;
                node = bladNoder.pop();
                Node<T> temp = node;

                while(node.forelder != null) {
                    currentLength++;
                    node = node.forelder;
                }

                //oppdater max og hvilken node som er lengst
                if(currentLength >= maxLength){
                    maxLength = currentLength;
                    finalNode = temp;
                }
            }

            Deque<Node> nodesToPrint = new ArrayDeque<>();

            while(finalNode.forelder != null){
                nodesToPrint.push(finalNode);
                finalNode = finalNode.forelder;
            }

            sb.append("[");
            nodesToPrint.push(node);

            while(!nodesToPrint.isEmpty()){
                sb.append(nodesToPrint.pop());
                if(nodesToPrint.size() == 0){
                    sb.append("]");
                }else{
                    sb.append(", ");
                }
            }
            return sb.toString();
        }

        public String[] grener() {


            if(antall == 0){
                return new String[0];
            }

            if(antall == 1){
                String s = "[" + rot.verdi + "]";
                String[] returnString = new String[1];
                returnString[0] = s;
                return returnString;
            }
            Node<T> p = rot;

            while (p.venstre != null) {
                p = p.venstre;
            }

            Deque<Node> bladNoder = new ArrayDeque();

            while(p != null){
                p = nesteInorden(p);
                if(p == null){
                    break;
                }
                if(p.venstre == null && p.høyre == null){
                    bladNoder.push(p);
                }
                else{

                }
            }

            //har nå alle bladnoder
            //forelder loop til topp og lagre veier

            String[] s = new String[bladNoder.size()];

            Node<T> node;
            int teller = 0;

            while(!bladNoder.isEmpty()){
                Deque<Node> hjelpeQue = new ArrayDeque<>();

                node = bladNoder.removeLast();
                s[teller] = "[";

                while(node != null){
                    hjelpeQue.push(node);
                    node = node.forelder;
                }

                //legg inn riktig vei
                while(!hjelpeQue.isEmpty()){
                    Node<T> temp = hjelpeQue.pop();

                    if(hjelpeQue.size() == 0){
                        s[teller] += temp.verdi + "]";
                    }else{
                        s[teller] += temp.verdi + ", ";
                    }
                }

                teller++;
            }


            return s;
        }


        public String bladnodeverdier() {

            if(antall == 0){
                return "[]";
            }

            Node<T> p = rot;
            StringBuilder s = new StringBuilder();


            s.append("[");
            rekursivInorden(p,s);

            s.replace(s.length()-2,s.length(),"]");

            return s.toString();
        }


        private static <T> void rekursivInorden(Node p, StringBuilder s){
            if (p.venstre != null){
                rekursivInorden(p.venstre,s);
            }

            if(p.venstre == null && p.høyre == null){
                s.append(p.verdi + ", ");
            }

            if (p.høyre != null){
                rekursivInorden(p.høyre,s);
            }
        }

        public String postString() {
            if(antall == 0){
                return "[]";
            }
            if(antall == 1){
                return "[" + rot.verdi + "]";
            }

            StringBuilder sb = new StringBuilder();



            Deque<Node> stack1 = new ArrayDeque();
            Deque<Node> stack2 = new ArrayDeque();

            Node<T> p;
            stack1.push(rot);


            while(!stack1.isEmpty()){

                p = stack1.pop();
                stack2.push(p);

                if(p.venstre != null){
                    stack1.push(p.venstre);
                }

                if(p.høyre != null){
                    stack1.push(p.høyre);
                }
            }

            sb.append("[");

            while(!stack2.isEmpty()){
                if(stack2.size() == 1){
                    sb.append(stack2.pop().verdi + "]");
                }else{
                    sb.append(stack2.pop().verdi + ", ");
                }

            }
            return sb.toString();
        }

        @Override
        public Iterator<T> iterator() {
            return new BladnodeIterator();
        }


        private class BladnodeIterator implements Iterator<T> {

            private Node<T> p = rot, q = null;
            private boolean removeOK = false;
            private int iteratorendringer = endringer;

            // konstruktør
            private BladnodeIterator() {

                if (p != null) {
                    while (p.venstre != null || p.høyre != null) {
                        if (p.venstre != null) p = p.venstre;
                        else p = p.høyre;
                    }
                }
            }

            @Override
            public boolean hasNext() {
                return p != null; // Denne skal ikke endres!
            }

            @Override
            public T next() {


                /*i

                removeOK = true;

                T verdi = null;


                while(p != null){

                    q = p;
                    p = nesteInorden(p);

                    if(q.venstre == null && q.høyre == null){
                        verdi = q.verdi;
                        break;
                    }

                }

                if(hasNext()) {
                    p = nesteInorden(p);
                }
                return verdi;*/

                if (iteratorendringer != endringer) {
                    throw new ConcurrentModificationException();
                }

                if(!hasNext()){
                    throw new NoSuchElementException("Ingen flere!");
                }

                if(antall == 0) {
                    throw new NoSuchElementException();
                }

                removeOK = true;

                q = p;
                T verdi = p.verdi;

                while(p != null) {
                    p = nesteInorden(p);

                    if (p == null) { //rot
                        return verdi;
                    }

                    if (p.venstre == null && p.høyre == null) { //bladnode
                        return verdi;
                    }
                }
                return verdi;
            }

            @Override
            public void remove() {

                if (endringer != iteratorendringer) {
                    throw new ConcurrentModificationException("Listen er endret!");
                }

                if(!removeOK){throw new IllegalStateException("Ulovlig tilstand!");}

                if (q == null) throw new IllegalStateException("Fjerning er ulovlig!");



                Node<T> forelder = q.forelder;


                if (forelder != null) {
                    if(q == forelder.høyre){
                        forelder.høyre = null;
                    }else{
                        forelder.venstre = null;
                    }
                }else{            //Forelder = null, altså q = rot
                    rot = null;
                }

                q.forelder = null;
                q.verdi = null;
                q = null;

                antall--;
                iteratorendringer++;
                endringer++;
                removeOK = false;
            }
        } // BladnodeIterator

} // ObligSBinTre
////////////////// ObligSBinTre /////////////////////////////////
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
        throw new UnsupportedOperationException("Ikke kodet ennå!"); }
        public int fjernAlle(T verdi) {
        throw new UnsupportedOperationException("Ikke kodet ennå!"); }


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
                Node<T> temp = p.forelder;
                return temp;
                //return p.forelder;
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
        throw new UnsupportedOperationException("Ikke kodet ennå!"); }
    public String høyreGren() {
        throw new UnsupportedOperationException("Ikke kodet ennå!"); }
    public String lengstGren() {
        throw new UnsupportedOperationException("Ikke kodet ennå!"); }
    public String[] grener() {
        throw new UnsupportedOperationException("Ikke kodet ennå!"); }
    public String bladnodeverdier() {
        throw new UnsupportedOperationException("Ikke kodet ennå!"); }
    public String postString() {
        throw new UnsupportedOperationException("Ikke kodet ennå!"); }

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
                throw new UnsupportedOperationException("Ikke kodet ennå!");
            }

            @Override
            public boolean hasNext() {
                return p != null; // Denne skal ikke endres!
            }

            @Override
            public T next() {
                throw new UnsupportedOperationException("Ikke kodet ennå!");
            }

            @Override

            public void remove() {
                throw new UnsupportedOperationException("Ikke kodet ennå!");
            }
        } // BladnodeIterator

} // ObligSBinTre
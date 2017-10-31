import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;
import java.util.Arrays;
import java.util.Comparator;

// An immutable data type that represents an autocomplete term: a query string 
// and an associated real-valued weight.
public class Term implements Comparable<Term> {
    public String query;
    public long weight;
    
    // Construct a term given the associated query string, having weight 0.
    public Term(String query) {
        this.weight = 0;
        this.query = query;
    }

    // Construct a term given the associated query string and weight.
    public Term(String query, long weight) {
    	if (query == null) {
            throw new java.lang.NullPointerException();
        }
        if (weight < 0) {
            throw new java.lang.IllegalArgumentException();
        }
        this.query = query;
        this.weight = weight;
    }


    // A reverse-weight comparator.
    public static Comparator<Term> byReverseWeightOrder() {
        return new ReverseWeightOrder();
    }



    // Helper reverse-weight comparator.
    private static class ReverseWeightOrder implements Comparator<Term> {
        public int compare(Term v, Term w) {
        	if (v.weight > w.weight) {
                return -1;
            } else if (v.weight == w.weight) {
                return 0;
            } else {
                return 1;
            }
        }
    }

    // A prefix-order comparator.
    public static Comparator<Term> byPrefixOrder(int r) {
    	return /*PrefixOrder stuff = */new PrefixOrder(r);
    	//return stuff;
    	
    	
    }

    // Helper prefix-order comparator.
    private static class PrefixOrder implements Comparator<Term> {
        private int r;

        PrefixOrder(int r) {
            this.r = r;
        }

        public int compare(Term v, Term w) {
            String s1 = v.query;
            String s2 = w.query;
            s1 = s1.substring(0, (Math.min(r, s1.length())));
            s2 = s2.substring(0, (Math.min(r, s2.length())));
            
            int minlength = s1.length() < s2.length() ? s1.length() : s2.length();
            //if (minlength >= x) {
              //  return s1.substring(0, x).compareTo(s2.substring(0, x));
            //} else if (s1.substring(0, minlength).compareTo(s2.substring(0, minlength)) == 0) {
              //  if (s1.length() == minlength) return -1;
              //  else return 1;
            return s1.compareTo(s2);
        }
    }

    // Compare this term to that in lexicographic order by query and 
    // return a negative, zero, or positive integer based on whether this 
    // term is smaller, equal to, or larger than that term.
    public int compareTo(Term that) {
    	String s1 = this.query;
        String s2 = that.query;
        return s1.compareTo(s2);
    }

    // A string representation of this term.
    public String toString() {
        return weight + "\t" + query;
    }

    // Test client. [DO NOT EDIT]
    public static void main(String[] args) {
        String filename = args[0];
        int k = Integer.parseInt(args[1]);
        In in = new In(filename);
        int N = in.readInt();
        Term[] terms = new Term[N];
        for (int i = 0; i < N; i++) {
            long weight = in.readLong(); 
            in.readChar(); 
            String query = in.readLine(); 
            terms[i] = new Term(query.trim(), weight); 
        }
        StdOut.printf("Top %d by lexicographic order:\n", k);
        Arrays.sort(terms);
        for (int i = 0; i < k; i++) {
            StdOut.println(terms[i]);
        }
        StdOut.printf("Top %d by reverse-weight order:\n", k);
        Arrays.sort(terms, Term.byReverseWeightOrder());
        for (int i = 0; i < k; i++) {
            StdOut.println(terms[i]);
        }
    }
}
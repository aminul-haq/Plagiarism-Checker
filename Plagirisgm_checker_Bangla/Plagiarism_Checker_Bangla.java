/**
 * Created by Aminul on 12/10/2018.
 */

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.*;

import static java.lang.Math.min;

public class Plagiarism_Checker_Bangla {
    static HashMap<String, String> wordRoot = new HashMap<>();
    static HashSet<String> dictionary = new HashSet<>();

    public static void main(String[] args)throws Exception {
        Scanner in = new Scanner(System.in);
        PrintWriter pw = new PrintWriter(new FileOutputStream("output.txt"));

        loadWords(dictionary, wordRoot);

        List<Node> list_1 = process_file("file_1.txt");
        List<Node> list_2 = process_file("file_2.txt");

        compareFiles(list_1, list_2);

        double sum = 0, count = 0;
        for(Node  node : list_2) {
            pw.println("[input Line:] " + node.original +
                    "\n[ordered matching:] "+ node.orderedMatch.original +" "+node.orderedPercentage +
                    "%\n[unordered matching:] "+node.unorderedMatch.original+" "+node.unorderedPercentage+"%\n");
            sum += Math.max(node.orderedPercentage, node.unorderedPercentage);
            count++;
        }
        double avg = sum / count;
        pw.println("Verdict: "+ avg +"% plagiarism detected");


        pw.close();
    }

    static void compareFiles(List<Node> list_1, List<Node> list_2) {
        for(Node node_2 : list_2) {
            for(Node node_1 : list_1) {
                int unorderedMatching = countMatchFromFrequencyMap(node_1.frequency, node_2.frequency);
                int orderedMatching = getLCS(node_1.splits, node_2.splits);
                if(node_2.orderedMatchedWords < orderedMatching) {
                    node_2.orderedMatchedWords = orderedMatching;
                    node_2.orderedMatch = node_1;
                    node_2.orderedPercentage = ((double)orderedMatching / node_2.splits.length) * 100;
                }
                if(node_2.unorderedMatchedWords < unorderedMatching) {
                    node_2.unorderedMatchedWords = unorderedMatching;
                    node_2.unorderedMatch = node_1;
                    node_2.unorderedPercentage = ((double)unorderedMatching / node_2.splits.length) * 100;
                }
            }
        }
    }

    static List<Node> process_file(String fileName) throws Exception {
        List<Node> g = new ArrayList<>();
        Scanner in = new Scanner(new FileInputStream(fileName));
        StringBuilder sb = new StringBuilder();
        while (in.hasNextLine()) {
            String text = in.nextLine();
            text = text.trim();
            sb.append(text);
        }
        StringTokenizer st = new StringTokenizer(sb.toString(), "।");
        while (st.hasMoreTokens()) {
            String text = st.nextToken();
            text = text.trim();
            g.add(new Node(text));
        }
        return g;
    }

    static final Node noMatch = new Node("no matching");
    static class Node {
        Node orderedMatch, unorderedMatch;
        double orderedPercentage = 0, unorderedPercentage = 0;
        int orderedMatchedWords, unorderedMatchedWords;
        LinkedHashMap<String, Integer> frequency;
        String original, modified, splits[];
        long hashValue;
        Node(String original) {
            this.original = original;
            frequency = new LinkedHashMap<>();
            orderedMatch = noMatch;
            unorderedMatch = noMatch;
            //hashValue = getHash(original.toCharArray());
            convertString();
        }
        void convertString() {
            modified = original.trim().toLowerCase();

            splits = modified.split(" ");
            for(int i = 0; i < splits.length; i++) {
                String s = splits[i];
                String base = getBaseWord(s);
                base = getBaseWord(base);
                splits[i] = base;
                frequency.put(base, frequency.getOrDefault(base, 0)+1);
            }
        }
    }

    static String getBaseWord(String s) {
        if(wordRoot.containsKey(s)) return wordRoot.get(s);
        if(dictionary.contains(s)) return s;
        for(int i = 0; i < s.length(); i++) {
            for(int j = s.length(); j >= i+1; j--) {
                String sub = s.substring(i, j);
                if(dictionary.contains(sub)) return sub;
            }
        }
        return s;
    }

    static int getLCS(String[] x, String[] y) {
        int m = x.length;
        int n = y.length;
        int[][] lcs = new int[m + 1][n + 1];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (x[i].equals(y[j])) {
                    lcs[i + 1][j + 1] = lcs[i][j] + 1;
                } else {
                    lcs[i + 1][j + 1] = Math.max(lcs[i + 1][j], lcs[i][j + 1]);
                }
            }
        }
        int cnt = lcs[m][n];
        return cnt;
    }

    static int countMatchFromFrequencyMap(HashMap<String, Integer> map1, HashMap<String, Integer> map2) {
        int matches = 0;
        for(String k : map2.keySet()) {
            matches += min(map1.getOrDefault(k, 0), map2.getOrDefault(k, 0));
        }
        return matches;
    }

    static void loadWords(HashSet<String> dictionary, HashMap<String, String> wordRoot) throws Exception {
        debug("Loading words...");
        //
        Scanner in = new Scanner(new FileInputStream("banglaWords.txt"));
        while (in.hasNextLine()) {
            String[] w = in.nextLine().split(" ");
            for (String s : w) {
                for (int i = 0; i < s.length(); i++) {
                    for (int j = i + 1; j <= s.length(); j++) {
                        dictionary.add(s.substring(i, j));
                    }
                }
            }
        }
        //
        in = new Scanner(new FileInputStream("bangla_synonyms.txt"));
        while (in.hasNextLine()) {
            String line = in.nextLine();
            if(line.length() == 0 || line == null) continue;
            String splits[] = line.split(" ");
            for(int i = 0; i < splits.length-1; i++) {
                wordRoot.put(splits[i], splits[splits.length-1]);
            }
        }

        //
        debug("word loading complete!");
    }

    static long getHash(char [] s){
        long mod1 = 1000000097L, mod2 = (long)1e9+9,  multiplier1 = 43, multiplier2 = 31;
        long hash1 = 0, hash2 = 0;
        int n = s.length;
        long p1 = 1, p2 = 1;
        for (int i = 0; i < n; i++) {
            hash1 = (hash1 + s[i] * p1) % mod1;
            hash2 = (hash2 + s[i] * p2) % mod2;
            p1 = (p1 * multiplier1) % mod1;
            p2 = (p2 * multiplier2) % mod2;
        }
        return (hash1 << 31) | hash2;
    }

    static void debug(Object...obj) {
        System.err.println(Arrays.deepToString(obj));
    }
}
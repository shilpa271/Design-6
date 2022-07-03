// Time Complexity :constant
// Space Complexity :O(l) where l is length of string currenctly created
// Did this code successfully run on Leetcode :Yes


class AutocompleteSystem {
    private HashMap<String, Integer> map;
    private StringBuilder str;

    class TrieNode {
        private TrieNode[] children;
        private List<String> pq;

        public TrieNode() {
            children = new TrieNode[256];
            pq = new ArrayList<>();
        }

        // inserting a word in trie such that each trienode only contains the result og
        // that particular string
        public void insert(String word) {
            TrieNode curr = root;
            for (int i = 0; i < word.length(); i++) {
                char c = word.charAt(i);
                if (curr.children[c - ' '] == null) {
                    curr.children[c - ' '] = new TrieNode();
                }
                curr = curr.children[c - ' '];
                if (!curr.pq.contains(word)) {
                    curr.pq.add(word);
                }
                // reorder the list
                Collections.sort(curr.pq, (a, b) -> {
                    int cnta = map.get(a);
                    int cntb = map.get(b);
                    if (cnta == cntb) {
                        return a.compareTo(b);
                    }
                    return cntb - cnta;
                });
                if (curr.pq.size() > 3) {
                    curr.pq.remove(curr.pq.size() - 1);
                }
            }

        }

        // to return list containing prefix word
        public List<String> search(String word) {
            TrieNode curr = root;
            for (int i = 0; i < word.length(); i++) {
                char c = word.charAt(i);
                if (curr.children[c - ' '] == null) {
                    return new ArrayList<>();
                }
                curr = curr.children[c - ' '];
            }
            return curr.pq;
        }
    }

    TrieNode root;

    public AutocompleteSystem(String[] sentences, int[] times) {
        map = new HashMap<>();
        root = new TrieNode();
        str = new StringBuilder();
        // add all sentences and freq in hashmap and trie
        for (int i = 0; i < sentences.length; i++) {
            String st = sentences[i];
            map.put(st, map.getOrDefault(st, 0) + times[i]);
            root.insert(sentences[i]);
        }

    }

    public List<String> input(char c) {
        if (c == '#') {// if #, insert in trie and map if it is not there already and make string
            // empty

            String inp = str.toString();
            map.put(inp, map.getOrDefault(inp, 0) + 1);
            root.insert(inp);
            str = new StringBuilder();
            return new ArrayList<>();
        }
        str.append(c);
        String srch = str.toString();
        return root.search(srch);
    }
}
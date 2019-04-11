package manual.LRUalgorithm;

import java.util.LinkedHashMap;
import java.util.Map;

public class LRUwithLinkHashMap<K, V> extends LinkedHashMap {

    private static final long serialVersionUID = 2431155117551680663L;

    private int cap;
    public LRUwithLinkHashMap(int cap, float loadFactor) {
        super((int) Math.ceil(cap / loadFactor) + 1, loadFactor, true);
        this.cap = cap;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry eldest) {
        return size() > cap;
    }
}
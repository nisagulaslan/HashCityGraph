
package com.mycompany.cityhashgraph;

import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Dell
 */
public class HashTable {
    private List<String> vertices;
    private int vertexCount;

    public HashTable() {
        vertices = new ArrayList<>();
        vertexCount = 0;
    }
    //353 linedan oluşan bir input aldığımız için mod 353
    public double hash(String word) {
        double hash = 0;
        for (int i = 0; i < word.length(); i++) {
            hash = (hash * 2 + word.charAt(i)) % 353;
        }
        return hash;
    }

    public int insert(String key) {
        if (!vertices.contains(key)) {
            vertices.add(key);
            vertexCount++;
        }
        return vertices.indexOf(key);
    }

    public String searchKey(int index) {
        if (index >= 0 && index < vertices.size()) {
            return vertices.get(index);
        }
        return null;
    }

    public int search(String key) {
        return vertices.indexOf(key);
    }

    public int getVertexCount() {
        return vertexCount;
    }

}

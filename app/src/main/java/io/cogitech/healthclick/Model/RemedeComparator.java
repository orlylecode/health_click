package io.cogitech.healthclick.Model;

import java.util.Comparator;

public class RemedeComparator implements Comparator<Remede> {

    @Override
    public int compare(Remede o1, Remede o2) {
        return o1.getName().compareTo(o2.getName());
    }
}

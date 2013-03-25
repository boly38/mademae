package net.mademocratie.gae.server.services.impl;

import net.mademocratie.gae.server.entities.Contribution;

import java.util.Comparator;
import java.util.Date;

public class ContributionDateComparator implements Comparator<Contribution> {
    public int compare(Contribution o1, Contribution o2) {
        Date age1 = o1.getDate();
        Date age2 = o2.getDate();
        return age1.compareTo(age2);
    }
}

package net.mademocratie.gae.server.services.impl;

import net.mademocratie.gae.server.entities.IContribution;

import java.util.Comparator;
import java.util.Date;

public class ContributionDateComparator implements Comparator<IContribution> {
    public int compare(IContribution o1, IContribution o2) {
        Date age1 = o1.getDate();
        Date age2 = o2.getDate();
        if (age1 == null) return -1;
        if (age2 == null) return 1;
        return age1.compareTo(age2);
    }
}

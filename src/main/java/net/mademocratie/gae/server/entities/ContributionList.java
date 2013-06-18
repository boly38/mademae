package net.mademocratie.gae.server.entities;

import com.googlecode.objectify.Key;
import net.mademocratie.gae.server.entities.v1.Citizen;
import net.mademocratie.gae.server.entities.v1.Contribution;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ContributionList <T extends Contribution> {
    protected List<T> contributions;

    public ContributionList() {
    }

    public ContributionList(List<T> contributions) {
        this.contributions = contributions;
    }
    public Set<Key<Citizen>> fetchAuthorsIds() {
        Set<Key<Citizen>> authorsIds = new HashSet<Key<Citizen>>();
        if (contributions == null) {
            return authorsIds;
        }
        for(Contribution contribution: contributions) {
            if (contribution != null
             && contribution.getAuthor() != null) {
                authorsIds.add(contribution.getAuthor());
            }
        }
        return authorsIds;
    }

    public int getCount() {
        return (contributions == null ? 0 : contributions.size());
    }

    public List<T> getObject() {
        return contributions;
    }
}

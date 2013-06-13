package net.mademocratie.gae.server.services;

import com.google.inject.ImplementedBy;
import com.googlecode.objectify.Key;
import net.mademocratie.gae.server.entities.v1.Contribution;
import net.mademocratie.gae.server.services.impl.ManageContributionsImpl;

import java.util.List;
import java.util.Map;
import java.util.Set;

@ImplementedBy(ManageContributionsImpl.class)
public interface IManageContributions {
    Map<Key<Contribution>,Contribution> getContributionsByIds(Set<Key<Contribution>> keys);
}

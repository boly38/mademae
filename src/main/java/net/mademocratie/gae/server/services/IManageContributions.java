package net.mademocratie.gae.server.services;

import com.google.inject.ImplementedBy;
import net.mademocratie.gae.server.entities.v1.Contribution;
import net.mademocratie.gae.server.services.impl.ManageContributionsImpl;

import java.util.List;

@ImplementedBy(ManageContributionsImpl.class)
public interface IManageContributions {
    List<Contribution> getLastContributions(int maxContributions);
}

package net.mademocratie.gae.server.services;

import com.google.inject.ImplementedBy;
import net.mademocratie.gae.server.entities.VoteContribution;

import java.util.List;

@ImplementedBy(ManageVoteContributionImpl.class)
public interface IManageVoteContributionImpl {
    List<VoteContribution> latest(int maxContributions);
}

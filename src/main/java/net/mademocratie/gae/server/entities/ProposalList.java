package net.mademocratie.gae.server.entities;

import net.mademocratie.gae.server.entities.v1.Proposal;

import java.util.List;

public class ProposalList extends ContributionList<Proposal> {
    public ProposalList(List<Proposal> contributions) {
        super(contributions);
    }
}

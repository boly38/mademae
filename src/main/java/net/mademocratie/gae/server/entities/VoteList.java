package net.mademocratie.gae.server.entities;

import com.googlecode.objectify.Key;
import net.mademocratie.gae.server.entities.dto.ProposalDTO;
import net.mademocratie.gae.server.entities.dto.ProposalVotesDTO;
import net.mademocratie.gae.server.entities.dto.VoteDTO;
import net.mademocratie.gae.server.entities.v1.Citizen;
import net.mademocratie.gae.server.entities.v1.Proposal;
import net.mademocratie.gae.server.entities.v1.Vote;

import java.util.*;

public class VoteList extends ContributionList<Vote> {
    public VoteList(List<Vote> contributions) {
        super(contributions);
    }

    @Override
    public Set<Key<Citizen>> fetchAuthorsIds() {
        return super.fetchAuthorsIds();
    }

    public Set<Key<Proposal>> fetchProposalsIds() {
        Set<Key<Proposal>> proposalsIds = new HashSet<Key<Proposal>>();
        if (contributions == null) {
            return proposalsIds;
        }
        for(Vote contribution: contributions) {
            if (contribution.getProposal() != null) {
                proposalsIds.add(contribution.getProposal());
            }
        }
        return proposalsIds;
    }

    public void asDTO(ProposalDTO proposal) {
    }

    public ProposalVotesDTO asProposalVotes(ProposalDTO proposal) {
        List<VoteDTO> votesDTos = new ArrayList<VoteDTO>(contributions.size());
        for(Vote v : contributions) {
            votesDTos.add(new VoteDTO(null, v)); // vote are anonymous any way
        }
        return new ProposalVotesDTO(votesDTos);
    }
}
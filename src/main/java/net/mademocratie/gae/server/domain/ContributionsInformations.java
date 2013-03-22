package net.mademocratie.gae.server.domain;

import net.mademocratie.gae.server.entities.IContribution;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class ContributionsInformations {
    ArrayList<IContribution> lastContributions;

    public ContributionsInformations() {
        lastContributions = new ArrayList<IContribution>();
    }

    public ContributionsInformations(List<IContribution> lastContributions) {
        this.lastContributions = new ArrayList<IContribution>(lastContributions);
    }
}

package net.mademocratie.gae.server.entities.v1;

/**
 * VoteKind
 * <p/>
 * Last update  : $LastChangedDate$
 * Last author  : $Author$
 *
 * @version : $Revision$
 */
public enum VoteKind {
    PRO(1), NEUTRAL(0), CON(1);

    private int weight;
    VoteKind(int weight) {
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }

    public static VoteKind getValue(int i)
    {
        for (VoteKind f : VoteKind.values())
        {
            if (f.getWeight() == i)
                return f;
        }
        throw new IllegalArgumentException("Invalid VoteKind weight: " + i);
    }
}

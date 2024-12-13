package lapr4.jobs4u.candidatemanagement.domain;

import eapli.framework.domain.model.ValueObject;

/**
 * The type Plugin main class name.
 */
public class PluginMainClassName implements ValueObject, Comparable<PluginMainClassName> {
    private String name;

    /**
     * Instantiates a new Plugin main class name.
     */
    protected PluginMainClassName() {}

    /**
     * Instantiates a new Plugin main class name.
     *
     * @param name the name
     */
    public PluginMainClassName(String name) {
        this.name = name;
    }

    @Override
    public int compareTo(PluginMainClassName o) {
        return 0;
    }

    /**
     * Name string.
     *
     * @return the string
     */
    public String name() {return name;}
}

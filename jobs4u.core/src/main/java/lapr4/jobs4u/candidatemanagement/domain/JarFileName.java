package lapr4.jobs4u.candidatemanagement.domain;

import eapli.framework.domain.model.ValueObject;

/**
 * The type Jar file name.
 */
public class JarFileName implements ValueObject, Comparable<JarFileName> {
    private String name;

    /**
     * Instantiates a new Jar file name.
     */
    protected JarFileName() {}

    /**
     * Instantiates a new Jar file name.
     *
     * @param name the name
     */
    public JarFileName(String name) {
        this.name = name;
    }

    @Override
    public int compareTo(JarFileName o) {
        return 0;
    }

    public String toString() {
        return name;
    }
}

package lapr4.jobs4u.candidatemanagement.domain;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.strings.StringMixin;

import java.io.Serializable;
import java.util.Objects;

/**
 * The type Plugin identifier.
 */
public class PluginIdentifier implements ValueObject, Comparable<PluginIdentifier>, Serializable, StringMixin {
    private String identifier;

    /**
     * Instantiates a new Plugin identifier.
     */
    public PluginIdentifier() {}

    /**
     * Instantiates a new Plugin identifier.
     *
     * @param identifier the identifier
     */
    public PluginIdentifier(String identifier) {
        if (!validateIdentifier(identifier)) throw new IllegalArgumentException();
        this.identifier = identifier;
    }

    /**
     * Validate identifier boolean.
     *
     * @param identifier the identifier
     * @return the boolean
     */
    public static boolean validateIdentifier(String identifier) {
        System.out.println(identifier);
        return identifier.matches("[A-Z]{3}_[0-9]+_[A-Za-z0-9]+");
    }

    /**
     * Generate plugin identifier plugin identifier.
     *
     * @param pluginType  the plugin type
     * @param jarFileName the jar file name
     * @param unixTime    the unix time
     * @return the plugin identifier
     */
    public static PluginIdentifier generatePluginIdentifier(PluginType pluginType, JarFileName jarFileName, Long unixTime) {
        if (unixTime == null) unixTime = System.currentTimeMillis() / 1000L;
        return new PluginIdentifier(pluginType.getShorthandName()+"_"+unixTime+"_"+jarFileName.toString().replace(".jar",""));
    }


    @Override
    public int compareTo(PluginIdentifier o) {
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PluginIdentifier that = (PluginIdentifier) o;
        return Objects.equals(identifier, that.identifier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier);
    }
}

package lapr4.jobs4u.candidatemanagement.domain;

import eapli.framework.domain.model.ValueObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The type Plugin type.
 */
public class PluginType implements ValueObject {
    /**
     * The constant REQUIREMENTS.
     */
    public static final PluginType REQUIREMENTS = new PluginType("Requirements Plugin", "REQ");
    /**
     * The constant INTERVIEW.
     */
    public static final PluginType INTERVIEW = new PluginType("Interview Model", "ITV");

    /**
     * Plugin type list list.
     *
     * @return the list
     */
    public static List<PluginType> pluginTypeList(){
        List<PluginType> pluginTypeList = new ArrayList<>();
        pluginTypeList.add(REQUIREMENTS);
        pluginTypeList.add(INTERVIEW);
        return pluginTypeList;
    }
    private String shorthand;
    private String pluginTypeName;

    /**
     * Instantiates a new Plugin type.
     */
    protected PluginType() {}

    /**
     * Instantiates a new Plugin type.
     *
     * @param name      the name
     * @param shorthand the shorthand
     */
    public PluginType(String name, String shorthand) {
        if (!isValid(shorthand) || name.isEmpty()) throw new IllegalArgumentException();
        this.pluginTypeName = name;
        this.shorthand = shorthand;
    }

    /**
     * Is valid boolean.
     *
     * @param shorthand the shorthand
     * @return the boolean
     */
    public static boolean isValid(String shorthand) {
        // Portuguese phone numbers typically start with 9 for mobile numbers or 2, 3, 4, 5 for landlines
        // They are usually 9 digits long for mobile and 9 or 10 digits long for landlines (including area code)
        return shorthand.matches("[A-Z]{3}");
    }


    /**
     * Gets shorthand name.
     *
     * @return the shorthand name
     */
    public String getShorthandName() {
        return shorthand;
    }

    /**
     * Gets plugin type name.
     *
     * @return the plugin type name
     */
    public String getPluginTypeName() {
        return pluginTypeName;
    }

    /**
     * Compare to int.
     *
     * @param o the o
     * @return the int
     */
    public int compareTo(PluginType o) {
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PluginType that = (PluginType) o;
        return Objects.equals(shorthand, that.shorthand) && Objects.equals(pluginTypeName, that.pluginTypeName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(shorthand, pluginTypeName);
    }
}

package lapr4.jobs4u.candidatemanagement.domain;

import eapli.framework.domain.model.AggregateRoot;
import jakarta.persistence.*;

import java.util.Objects;

/**
 * The type Plugin.
 */
@Entity
public class Plugin implements AggregateRoot<PluginIdentifier> {

    private static final long serialVersionUID = 1L;

    @Version
    private Long version;
    @EmbeddedId
    private PluginIdentifier pluginID;
    @Column
    private PluginType pluginType;
    @Column
    private Description description;
    @Column
    private PluginMainClassName pluginMainClassName;
    @Column
    private JarFileName jarFileName;

    /**
     * Instantiates a new Plugin.
     *
     * @param pluginType          the plugin type
     * @param description         the description
     * @param pluginMainClassName the plugin main class name
     * @param jarFileName         the jar file name
     */
    public Plugin(PluginType pluginType, Description description, PluginMainClassName pluginMainClassName, JarFileName jarFileName) {
        this.pluginID = PluginIdentifier.generatePluginIdentifier(pluginType,jarFileName, null);
        this.pluginType = pluginType;
        this.description = description;
        this.pluginMainClassName = pluginMainClassName;
        this.jarFileName = jarFileName;
    }

    /**
     * Instantiates a new Plugin.
     *
     * @param pluginIdentifier    the plugin identifier
     * @param pluginType          the plugin type
     * @param description         the description
     * @param pluginMainClassName the plugin main class name
     * @param jarFileName         the jar file name
     */
    public Plugin(PluginIdentifier pluginIdentifier,PluginType pluginType, Description description, PluginMainClassName pluginMainClassName, JarFileName jarFileName) {
        this.pluginID = pluginIdentifier;
        this.pluginType = pluginType;
        this.description = description;
        this.pluginMainClassName = pluginMainClassName;
        this.jarFileName = jarFileName;
    }

    /**
     * Instantiates a new Plugin.
     */
    protected Plugin() {

    }

    @Override
    public boolean sameAs(Object other) {
        return false;
    }

    @Override
    public PluginIdentifier identity() {
        return null;
    }

    /**
     * Plugin type plugin type.
     *
     * @return the plugin type
     */
    public PluginType pluginType() {
        return pluginType;
    }

    /**
     * Description description.
     *
     * @return the description
     */
    public Description description() { return description;}

    /**
     * Plugin main class name plugin main class name.
     *
     * @return the plugin main class name
     */
    public PluginMainClassName pluginMainClassName() { return pluginMainClassName; }

    /**
     * Jar file name jar file name.
     *
     * @return the jar file name
     */
    public JarFileName jarFileName() {return jarFileName;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Plugin plugin = (Plugin) o;
        return Objects.equals(version, plugin.version) && Objects.equals(pluginID, plugin.pluginID) && Objects.equals(pluginType, plugin.pluginType) && Objects.equals(description, plugin.description) && Objects.equals(pluginMainClassName, plugin.pluginMainClassName) && Objects.equals(jarFileName, plugin.jarFileName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(version, pluginID, pluginType, description, pluginMainClassName, jarFileName);
    }



}

package lapr4.jobs4u.persistence.impl.inmemory;

import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;
import lapr4.jobs4u.candidatemanagement.domain.Plugin;
import lapr4.jobs4u.candidatemanagement.domain.PluginIdentifier;
import lapr4.jobs4u.candidatemanagement.domain.PluginType;
import lapr4.jobs4u.candidatemanagement.repositories.PluginRepository;

import java.util.Optional;

public class InMemoryPluginRepository
        extends InMemoryDomainRepository<Plugin, PluginIdentifier>
        implements PluginRepository {

    static {
        InMemoryInitializer.init();
    }

    @Override
    public Iterable<Plugin> findPluginByType(final PluginType pluginType) {
        return match(e -> e.pluginType().getPluginTypeName().equals(pluginType));
    }

}

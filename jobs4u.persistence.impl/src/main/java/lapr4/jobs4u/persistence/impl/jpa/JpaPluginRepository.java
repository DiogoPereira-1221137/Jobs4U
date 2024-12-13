/*
 * Copyright (c) 2013-2024 the original author or authors.
 *
 * MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package lapr4.jobs4u.persistence.impl.jpa;

import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaAutoTxRepository;
import lapr4.jobs4u.Application;
import lapr4.jobs4u.candidatemanagement.domain.Plugin;
import lapr4.jobs4u.candidatemanagement.domain.PluginIdentifier;
import lapr4.jobs4u.candidatemanagement.domain.PluginType;
import lapr4.jobs4u.candidatemanagement.repositories.PluginRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 *
 * @author Jorge Santos ajs@isep.ipp.pt 02/04/2016
 */
class JpaPluginRepository
        extends JpaAutoTxRepository<Plugin, PluginIdentifier, PluginIdentifier>
        implements PluginRepository {

    public JpaPluginRepository(final TransactionalContext autoTx) {
        super(autoTx, "identifier");
    }

    public JpaPluginRepository(final String puname) {
        super(puname, Application.settings().getExtendedPersistenceProperties(),
                "identifier");
    }

    @Override
    public Iterable<Plugin> findPluginByType(PluginType pluginType) {
        final Map<String, Object> params = new HashMap<>();
        params.put("name", pluginType);
        return match("e.pluginType=:name", params);
    }


}

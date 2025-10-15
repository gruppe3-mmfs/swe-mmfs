package org.gruppe3.core.port;

import org.gruppe3.core.domain.Server;
import org.gruppe3.core.exception.FrontendRepositoryException;

public interface FrontendRepositoryPort {
  void createFrontend(Server server) throws FrontendRepositoryException;
}

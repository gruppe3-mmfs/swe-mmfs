package org.gruppe3.frontend.adapter;

import org.gruppe3.core.domain.Server;
import org.gruppe3.core.exception.FrontendRepositoryException;
import org.gruppe3.core.port.FrontendRepositoryPort;

public class FrontendRepositoryAdapter implements FrontendRepositoryPort {

  private Server server;

  public FrontendRepositoryAdapter(Server server) {
    this.server = server;
  }

  @Override
  public void createFrontend(Server server) throws FrontendRepositoryException {}
}

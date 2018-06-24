package controllers.di

import javax.inject.Inject

import usermanager.application.scenarios.session.SessionScenario
import usermanager.application.services.session.SessionService

class SessionScenarioImpl @Inject()(
  val sessionService: SessionService,
) extends SessionScenario

package br.com.s2.core.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;

public class VerticleDispatcher extends AbstractVerticle {

	@Override
	public void start() throws Exception {
		EventBus eventBus = vertx.eventBus();
		eventBus.publish("comunicacao_teste", "Mensagem de email enviada");
	}
}

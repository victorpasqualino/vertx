package br.com.s2.core.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;

public class VerticleConsumer extends AbstractVerticle {

	@Override
	public void start() throws Exception {
		EventBus eventBus = vertx.eventBus();
		eventBus.consumer("comunicacao_teste", r -> {
			System.out.println("-> Mensagem recebida: " + r.body());
		});
	}
}

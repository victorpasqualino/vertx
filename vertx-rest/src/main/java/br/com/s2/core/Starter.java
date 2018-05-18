package br.com.s2.core;

import br.com.s2.core.verticle.CustomHttpServer;
import br.com.s2.core.verticle.VerticleConsumer;
import br.com.s2.core.verticle.VerticleDispatcher;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;

public class Starter {
	

	public static void main(String[] args) {
		Vertx vertx = Vertx.vertx(new VertxOptions().setWorkerPoolSize(20).setEventLoopPoolSize(1));
		
		VerticleConsumer consumer = new VerticleConsumer();
		VerticleDispatcher dispatcher = new VerticleDispatcher();
		
		vertx.deployVerticle(consumer, s -> {
			if ( s.succeeded() ) {
				System.out.println("Deployed verticle Consumer");
				vertx.deployVerticle(dispatcher, d -> {
					if ( d.succeeded() ) {
						System.out.println("Deployed verticle Dispatcher");
					}
				});
			}
		});
		
		System.out.println("Finalizado Message");
		
		vertx.deployVerticle(new CustomHttpServer(), s -> {
			if ( s.succeeded() ) {
				System.out.println("Deployed verticle Http Server");
			}
		});
		
	}
}

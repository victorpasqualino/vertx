package br.com.s2.core.verticle;

import br.com.s2.core.jhandler.PessoaHandler;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.LoggerFormat;
import io.vertx.ext.web.handler.LoggerHandler;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.handler.TimeoutHandler;

public class CustomHttpServer extends AbstractVerticle {

	
	@Override
	public void start() throws Exception {
		HttpServer httpServer = vertx.createHttpServer();
		PessoaHandler pessoaHandler = new PessoaHandler();
		
		Router router = Router.router(vertx);
		
		router.route().handler(TimeoutHandler.create(10000, 555));
		
		router.route().handler(BodyHandler.create());
		
		router.route().handler(LoggerHandler.create(LoggerFormat.SHORT));
		
		router.route(HttpMethod.GET, "/pessoa/:cpf").handler(pessoaHandler::listar);
		
		router.route(HttpMethod.POST, "/pessoa").handler(pessoaHandler::criar);
		
		router.route(HttpMethod.GET, "/google").handler(pessoaHandler::callGoogle);
		router.route(HttpMethod.GET, "/google2").handler(pessoaHandler::callGoogleReactive);
		
		router.route("/static").handler(StaticHandler.create());
		
		Router router2 = Router.router(vertx);
		
		router2.route("/teste").handler(r -> {
			r.response().end("Teste");
		});
		
		router.mountSubRouter("/sub", router2);
		
		httpServer.requestHandler(router::accept).listen(8080);
	}
	
}

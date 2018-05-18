package br.com.s2.core.service;

import io.reactivex.Single;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;

public class ClientHttp {

	public void requestFAQ(Vertx vertx, Handler<AsyncResult<HttpResponse<Buffer>>> handler) {
		WebClient webClient = WebClient.create(vertx);

//		webClient.get(443, "faq.pagseguro.uol.com.br", "/").ssl(true).timeout(10000L).send(r -> {
//			if (r.succeeded()) {
//				HttpResponse<Buffer> result = r.result();
//				context.response().end(result.body());
//			} else {
//				r.cause().printStackTrace();
//				context.fail(500);
//			}
//		});
		
		webClient.get(443, "faq.pagseguro.uol.com.br", "/").ssl(true).timeout(10000L).send(handler);
	}
	
	public Single<io.vertx.reactivex.core.buffer.Buffer> requestFAQReactive(Vertx vertx) {
		
		io.vertx.reactivex.ext.web.client.WebClient webClient = io.vertx.reactivex.ext.web.client.WebClient.create(new io.vertx.reactivex.core.Vertx(vertx));

		return webClient.get(443, "faq.pagseguro.uol.com.br", "/")
				.ssl(true)
				.timeout(2000)
				.rxSend()
				.doOnError(t -> t.printStackTrace())
				.doOnSuccess(r -> System.out.println(r.statusCode()))
				.map(r -> r.body())
				.onErrorReturnItem(io.vertx.reactivex.core.buffer.Buffer.buffer());
//		webClient.get(443, "faq.pagseguro.uol.com.br", "/").ssl(true).timeout(10000L).send(r -> {
//			if (r.succeeded()) {
//				HttpResponse<Buffer> result = r.result();
//				context.response().end(result.body());
//			} else {
//				r.cause().printStackTrace();
//				context.fail(500);
//			}
//		});
		
	}

}

package br.com.s2.core.jhandler;

import java.awt.geom.Rectangle2D;

import br.com.s2.core.service.ClientHttp;
import io.reactivex.Single;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;

public class PessoaHandler {

	public void listar(RoutingContext context) {

		String param = context.request().getParam("cpf");

		context.response().end("CPF informado " + param);

	}

	public void criar(RoutingContext context) {

		context.response().putHeader(HttpHeaders.CONTENT_TYPE, "application/json");

		Buffer buffer = context.getBody();

		JsonObject jsonObject = new JsonObject(buffer);

		String nome = jsonObject.getString("nome");
		String cpf = jsonObject.getString("cpf");
		String dataNascimento = jsonObject.getString("dataNascimento");

		System.out.println("Nome: " + nome);
		System.out.println("CPF: " + cpf);
		System.out.println("Data Nascimento: " + dataNascimento);

		context.response().end(jsonObject.toBuffer());
	}

	public void callGoogle(RoutingContext context) {

		ClientHttp clientHttp = new ClientHttp();

		clientHttp.requestFAQ(context.vertx(), r -> {
			if (r.succeeded()) {
				HttpResponse<Buffer> result = r.result();
				context.response().end(result.body());
			} else {
				r.cause().printStackTrace();
				context.fail(500);
			}
		});

	}

	public void callGoogleReactive(RoutingContext context) {

		ClientHttp clientHttp = new ClientHttp();
		Single.just(new StringBuilder())
			  .flatMap(sb -> clientHttp.requestFAQReactive(context.vertx())
						.flatMap(r1 -> Single.just("teste") )
						.map(r -> sb.append(r))
						.flatMap(s -> Single.error(new RuntimeException("ERROR")))
						.flatMap(r2 -> Single.zip(Single.just("ano1"), Single.error(new RuntimeException("ERROR2")), Single.just("ano2"), (s1, s2, s3) -> sb.append(s1).append(s3)))
						.onErrorReturnItem(sb)
					  )
			  .doOnSubscribe(sb -> System.out.println("Iniciou"))
			  .doOnSuccess(s -> System.out.println("Terminou"))
			  .doOnError(t -> t.printStackTrace())
			  .subscribe(
					rr -> context.response().end(rr.toString()),
					e -> context.fail(500)
			  );

	}
}

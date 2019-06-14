package br.com.alura.loja;

import java.io.IOException;
import java.net.URI;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

public class Servidor {

	/**
	 * Classe utilizada para levantar o servlet conatainer do grizzly em memoria com
	 * jersey. Configura em qual pacote procurar classes JAX-RS para serem
	 * utilizadas como servico.
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		HttpServer server = inicializaServidor();

		System.out.println("Servidor rodando ...");
		System.in.read();
		server.stop();
		System.out.println("Servidor parado.");
	}

	static HttpServer inicializaServidor() {
		ResourceConfig config = new ResourceConfig().packages("br.com.alura.loja");
		URI uri = URI.create("http://localhost:8080/");
		HttpServer server = GrizzlyHttpServerFactory.createHttpServer(uri, config);
		return server;
	}

}

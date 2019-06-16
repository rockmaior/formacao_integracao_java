package br.com.alura.loja;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.thoughtworks.xstream.XStream;

import br.com.alura.loja.modelo.Projeto;

public class ProjetoClientTest {
	
	private HttpServer server;

	@Before
	public void startaServidor() {
		this.server = Servidor.inicializaServidor();
	}

	@After
	public void mataServidor() {

		server.stop();
	}

	/**
	 * Cria um cliente HTTP para acessar o servidor, URI base do servidor alvo das
	 * requisicoes, URI especifica do endpoint, ou path, que pega os dados do
	 * servidor e devolve a resposta em string.
	 */
	@Test
	public void testaBuscaProjeto() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("http://localhost:8080");
		String conteudo = target.path("/projetos/id").request().get(String.class);

		Projeto projeto = (Projeto) new XStream().fromXML(conteudo);

		Assert.assertEquals("Minha loja", projeto.getNome());
	}
}

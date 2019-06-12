package br.com.alura.loja;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.junit.Assert;
import org.junit.Test;

public class ProjetoClientTest {

	/**
	 * Cria um cliente HTTP para acessar o servidor, URI base do servidor alvo das
	 * requisicoes, URI especifica do endpoint, ou path, que pega os dados do
	 * servidor e devolve a resposta em string.
	 */
	@Test
	public void testaBuscaProjeto() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("http://localhost:8080");
		String conteudo = target.path("/projetos").request().get(String.class);

		Assert.assertTrue(conteudo.contains("<nome>Minha loja"));
	}
}

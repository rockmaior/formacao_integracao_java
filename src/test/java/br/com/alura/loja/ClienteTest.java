package br.com.alura.loja;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.thoughtworks.xstream.XStream;

import br.com.alura.loja.modelo.Carrinho;
import br.com.alura.loja.modelo.Produto;

public class ClienteTest {
	
	private HttpServer server;
	
	@Before
	public void startaServidor() {
		this.server = Servidor.inicializaServidor();
	}
	
	@After
	public void mataServidor() {
		
		server.stop();
	}

	@Test
	public void testaQueBuscarUmCarrinhoTrazOCarrinhoEsperado() {
		
		Client client = ClientBuilder.newClient();//Cria um cliente HTTP para acessar o servidor
		WebTarget target = client.target("http://localhost:8080"); //URI base do servidor alvo das requisicoes.
		//URI especifica do endpoint, ou path, que pega os dados do servidor e devolve a resposta em string.
		String conteudo = target.path("/carrinhos/1").request().get(String.class);
		
		Carrinho carrinho = (Carrinho) new XStream().fromXML(conteudo);//desserealizar o XML para objeto
		
		Assert.assertEquals("Rua Vergueiro 3185, 8 andar", carrinho.getRua());

	}
	
	@Test
	public void testaQueSuportaNovosCarrinhos() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("http://localhost:8080");
		
		Carrinho carrinho = new Carrinho();
		carrinho.adiciona(new Produto(314L, "Tablet", 999, 1));
		carrinho.setRua( "Rua Vergueiro");
		carrinho.setCidade("São Paulo");
		String xml = carrinho.toXML();
		
		/**
		 * Cria a representacao do recurso: o conteudo e o media type
		 */
		Entity<String> entity = Entity.entity(xml, MediaType.APPLICATION_XML);
		Response response = target.path("/carrinhos").request().post(entity);
		
		Assert.assertEquals("<status>sucesso</status>", response.readEntity(String.class));
	}

}

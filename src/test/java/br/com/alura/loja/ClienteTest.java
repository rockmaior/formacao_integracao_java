package br.com.alura.loja;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.sun.corba.se.spi.activation.Server;
import com.thoughtworks.xstream.XStream;

import br.com.alura.loja.modelo.Carrinho;

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
		String conteudo = target.path("/carrinhos").request().get(String.class);
		
		Carrinho carrinho = (Carrinho) new XStream().fromXML(conteudo);//desserealizar o XML para objeto
		
		Assert.assertEquals("Rua Vergueiro 3185, 8 andar", carrinho.getRua());

	}

}
package br.com.alura.loja.resource;

import java.net.URI;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.thoughtworks.xstream.XStream;

import br.com.alura.loja.dao.CarrinhoDAO;
import br.com.alura.loja.modelo.Carrinho;
import br.com.alura.loja.modelo.Produto;

@Path("carrinhos")
public class CarrinhoResource {

	/**
	 * Busca carrinho com id 1, retorna sua representacao em formato XML. Produz uma
	 * resposta de conteudo em XML requisitado via GET para buscar informacoes do
	 * servidor.
	 */

	@Path("{id}")
	@GET
	@Produces(MediaType.APPLICATION_XML)
	public String busca(@PathParam("id") long id) {
		Carrinho carrinho = new CarrinhoDAO().busca(id);
		return carrinho.toXML();
	}

	@POST
	@Consumes(MediaType.APPLICATION_XML)
	public Response adiciona(String conteudo) {
		Carrinho carrinho = (Carrinho) new XStream().fromXML(conteudo);
		new CarrinhoDAO().adiciona(carrinho);
		URI uri = URI.create("/carrinhos/" + carrinho.getId());
		return Response.created(uri).build();
	}

	/**
	 * Recebe dois parametros no PATH da URI, efetuar requisição DELETE, instancia
	 * carrinho, remove o produto e retorna resposta OK.
	 */

	@Path("{id}/produtos/{produtoId}")
	@DELETE
	public Response removeCarrinho(@PathParam("id") long id, @PathParam("produtoId") long produtoId) {
		Carrinho carrinho = new CarrinhoDAO().busca(id);
		carrinho.remove(produtoId);
		return Response.ok().build();
	}
	
	/**
	 * Altera um recurso passando os parametros id do carrinho e id do produto e em
	 * seguida busca o carrinho, recebe o conteudo XML do Produto no parametro do
	 * metodo para transoformar o conteudo do produto fromxml, pegar o carrinho e
	 * chamar o metodo troca para trocar produto
	 */
	
	@Path("{id}/produtos/{produtoId}")
	@PUT
	@Consumes(MediaType.APPLICATION_XML)
	public Response alteraProduto(String conteudo, @PathParam("id") long id, @PathParam("produtoId") long produtoId) {
		Carrinho carrinho = new CarrinhoDAO().busca(id);
		Produto produto = (Produto) new XStream().fromXML(conteudo);
		carrinho.troca(produto );
		return Response.ok().build();
	}
	
	/**
	 * 	alterar a URI do path acrecentando a quantidade e altera carrinho para metodo trocaQuantidade que vai so trocar a quantidade do produto.
	 * @param conteudo
	 * @param id
	 * @param produtoId
	 * @return
	 */
	
	@Path("{id}/produtos/{produtoId}/quantidade") //http://localhost:8080/carrinhos/1/produtos/3467/20
	@PUT
	@Consumes(MediaType.APPLICATION_XML)
	public Response alteraQuantidadeProduto(String conteudo, @PathParam("id") long id, @PathParam("produtoId") long produtoId ) {
		Carrinho carrinho = new CarrinhoDAO().busca(id);
		Produto produto = (Produto) new XStream().fromXML(conteudo);
		carrinho.trocaQuantidade(produto);
		return Response.ok().build();
	}

}


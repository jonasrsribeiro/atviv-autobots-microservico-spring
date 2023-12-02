package com.autobots.automanager.servicos.hateoasdor;

import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.autobots.automanager.controles.VendaControle;
import com.autobots.automanager.entidades.Venda;

@Component
public class AdicionadorLinkVenda extends AdicionadorLinkFactory<Venda> implements AdicionadorLink<Venda> {

	@Override
    protected AdicionadorLink<Venda> criarAdicionadorLink(Class<?> classe) {
        if (classe.equals(Venda.class)) {
            return new AdicionadorLinkVenda();
        }

		return null;

	}

	@Override
    public void adicionarLink(Venda objeto) {
        adicionarLinkVenda(objeto);
    }

	@Override
    public void adicionarLink(List<Venda> lista) {
        for (Venda venda : lista) {
            adicionarLinkVenda(venda);
        }
    }

	private void adicionarLinkVenda(Venda venda) {
        long id = venda.getId();

        Link linkProprio = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(VendaControle.class).obterVenda(id))
                .withSelfRel().withType("GET").withRel("self").withTitle("Obter detalhes do venda");

        Link linkListaVendas = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(VendaControle.class).obterVendas())
                .withRel("todosVendas").withType("GET").withTitle("Obter lista de vendas");

        Link linkNovoVenda = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(VendaControle.class).cadastrarVenda(new Venda()))
                .withRel("novoVenda").withType("POST").withTitle("Criar um novo venda");

        Link linkAtualizar = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(VendaControle.class).atualizarVenda(venda))
                .withRel("atualizar").withType("PUT").withTitle("Atualizar detalhes do venda");

        Link linkExcluir = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(VendaControle.class).excluirVenda(venda)).withRel("excluir")
                .withType("DELETE").withTitle("Excluir venda");

        venda.add(linkProprio, linkListaVendas, linkNovoVenda, linkAtualizar, linkExcluir);
    }

	public void adicionarLinkListaVendas(List<Venda> vendas) {
        Link linkListaVendas = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(VendaControle.class).obterVendas()).withRel("vendas")
                .withType("GET").withTitle("Obter lista de vendas");

        Link linkNovoVenda = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(VendaControle.class).cadastrarVenda(new Venda()))
                .withRel("novoVenda").withType("POST").withTitle("Criar um novo venda");

        vendas.forEach(venda -> venda.add(linkListaVendas, linkNovoVenda));
    }
}

package com.autobots.automanager.controles;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.autobots.automanager.entidades.Endereco;

import com.autobots.automanager.repositorios.EnderecoRepositorio;
import com.autobots.automanager.servicos.atualizador.EnderecoAtualizador;
import com.autobots.automanager.servicos.hateoasdor.AdicionadorLinkEndereco;
import com.autobots.automanager.servicos.selecionador.EnderecoSelecionador;

@RestController
@RequestMapping("/enderecos")
public class EnderecoControle {
    @Autowired
    private EnderecoRepositorio repositorio;
    @Autowired
    private EnderecoSelecionador selecionador;
    @Autowired
    private AdicionadorLinkEndereco adicionadorLink;

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_GERENTE') and hasAnyAuthority('GERENTE', 'VENDEDOR', 'USUARIO')")
    @GetMapping("/{id}")
    public ResponseEntity<Endereco> obterEndereco(@PathVariable long id) {
        List<Endereco> enderecos = repositorio.findAll();
        Endereco endereco = selecionador.selecionar(enderecos, id);
        if (endereco == null) {
            ResponseEntity<Endereco> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
        } else {
            adicionadorLink.adicionarLink(enderecos);
			ResponseEntity<Endereco> resposta = new ResponseEntity<Endereco>(endereco, HttpStatus.FOUND);
			return resposta;
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_GERENTE') and hasAnyAuthority('GERENTE', 'VENDEDOR', 'USUARIO')")
    @GetMapping("/listar")
    public ResponseEntity<List<Endereco>> obterEnderecos() {
        List<Endereco> enderecos = repositorio.findAll();
        if (enderecos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            adicionadorLink.adicionarLink(enderecos);
			ResponseEntity<List<Endereco>> resposta = new ResponseEntity<>(enderecos, HttpStatus.FOUND);
			return resposta;
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_GERENTE') and hasAnyAuthority('GERENTE', 'VENDEDOR', 'USUARIO')")
    @PostMapping("/cadastro")
    public ResponseEntity<?> cadastrarEndereco(@RequestBody Endereco endereco) {
        HttpStatus status = HttpStatus.CONFLICT;
        if (endereco.getId() == null) {
            repositorio.save(endereco);
            status = HttpStatus.CREATED;
        }
        return new ResponseEntity<>(status);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_GERENTE') and hasAnyAuthority('GERENTE', 'VENDEDOR', 'USUARIO')")
    @PutMapping("/atualizar")
    public ResponseEntity<?> atualizarEndereco(@RequestBody Endereco atualizacao) {
        HttpStatus status = HttpStatus.CONFLICT;
        Optional<Endereco> optionalEndereco = repositorio.findById(atualizacao.getId());
        Endereco endereco = new Endereco();
        if (optionalEndereco.isPresent()) {
            endereco = optionalEndereco.get();
        } else {
            endereco = null;
        }
        if (endereco != null) {
            EnderecoAtualizador atualizador = new EnderecoAtualizador();
            atualizador.atualizar(endereco, atualizacao);
            repositorio.save(endereco);
            status = HttpStatus.OK;
        } else {
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(status);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_GERENTE') and hasAnyAuthority('GERENTE', 'VENDEDOR', 'USUARIO')")
    @DeleteMapping("/excluir")
    public ResponseEntity<?> excluirEndereco(@RequestBody Endereco exclusao) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        Optional<Endereco> optionalEndereco = repositorio.findById(exclusao.getId());
        Endereco endereco = new Endereco();
        if (optionalEndereco.isPresent()) {
            endereco = optionalEndereco.get();
        } else {
            endereco = null;
        }
        if (endereco != null) {
            repositorio.delete(endereco);
            status = HttpStatus.OK;
        }
        return new ResponseEntity<>(status);
    }
}

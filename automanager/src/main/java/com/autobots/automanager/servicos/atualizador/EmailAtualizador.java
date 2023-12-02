package com.autobots.automanager.servicos.atualizador;

import java.util.Set;

import com.autobots.automanager.entidades.Email;

public class EmailAtualizador {
    private StringVerificadorNulo verificador = new StringVerificadorNulo();

    public void atualizar(Email email, Email atualizacao) {
        if (atualizacao != null) {
            if (!verificador.verificar(atualizacao.getEndereco())) {
                email.setEndereco(atualizacao.getEndereco());
            }
        }
    }

    public void atualizar(Set<Email> emails, Set<Email> atualizacoes) {
        for (Email atualizacao : atualizacoes) {
            for (Email email : emails) {
                if (atualizacao.getId() != null) {
                    if (atualizacao.getId() == email.getId()) {
                        atualizar(email, atualizacao);
                        break;
                    }
                }
            }
        }
    }
}

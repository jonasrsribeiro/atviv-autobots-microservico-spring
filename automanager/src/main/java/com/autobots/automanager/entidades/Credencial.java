package com.autobots.automanager.entidades;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import com.autobots.automanager.servicos.selecionador.SelecionadorComId;
import com.autobots.automanager.servicos.selecionador.TemplateSelecionador;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
public class Credencial extends TemplateSelecionador implements SelecionadorComId, Serializable{
	@Id()
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private Date criacao;
	@Column()
	private Date ultimoAcesso;
	@Column(nullable = false)
	private boolean inativo;
	@Column(nullable = false, unique = true)
	private String nomeUsuario;
	@Column(nullable = false)
	private String senha;
}
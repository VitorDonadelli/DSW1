package br.ufscar.dc.dsw.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.ufscar.dc.dsw.domain.Cliente;
import br.ufscar.dc.dsw.service.spec.IClienteService;
import br.ufscar.dc.dsw.service.spec.IUsuarioService;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

	@Autowired
	private IUsuarioService usuarioService;

	@Autowired
	private IClienteService clienteService;

	@GetMapping("/cadastrar")
	public String cadastrar(Cliente cliente) {
		return "cliente/cadastro";
	}

	@GetMapping("/listar")
	public String listar(ModelMap model) {
		model.addAttribute("clientes", clienteService.buscarTodos());
		return "cliente/lista";
	}

	@PostMapping("/salvar")
	public String salvar(@Valid Cliente cliente, BindingResult result, RedirectAttributes attr, BCryptPasswordEncoder encoder) {
		if (cliente.getPapel() == null) {
			cliente.setPapel("Cli");
		}

		if (result.hasErrors()) {
			return "cliente/cadastro";
		}
		
		cliente.setPassword(encoder.encode(cliente.getPassword()));
		clienteService.salvar(cliente);
		attr.addFlashAttribute("sucess", "Cliente inserido com sucesso");
		return "redirect:/clientes/listar";
	}

	@GetMapping("/editar/{id}")
	public String preEditar(@PathVariable("id") Long id, ModelMap model) {
		model.addAttribute("cliente", usuarioService.buscarPorId(id));
		return "cliente/cadastro";
	}

	@PostMapping("/editar")
	public String editar(@Valid Cliente cliente, BindingResult result, RedirectAttributes attr) {
		if (cliente.getPapel() == null) {
			cliente.setPapel("Cli");
		}

		if (result.hasErrors()) {
			return "cliente/cadastro";
		}

		usuarioService.salvar(cliente);
		attr.addFlashAttribute("sucess", "Cliente editado com sucesso.");
		return "redirect:/clientes/listar";
	}

	@GetMapping("/excluir/{id}")
	public String excluir(@PathVariable("id") Long id, RedirectAttributes attr) {
		if (clienteService.clienteTemConsulta(id)) {
			attr.addFlashAttribute("fail", "Cliente não excluído. Possui consultas agendadas.");
		}
		else {
			usuarioService.excluir(id);
			attr.addFlashAttribute("sucess", "Cliente excluído com sucesso.");
		}
		return "redirect:/clientes/listar";
	}
}

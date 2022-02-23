package com.felipecarneiro.cursomc;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.felipecarneiro.cursomc.domain.Categoria;
import com.felipecarneiro.cursomc.domain.Cidade;
import com.felipecarneiro.cursomc.domain.Cliente;
import com.felipecarneiro.cursomc.domain.Endereco;
import com.felipecarneiro.cursomc.domain.Estado;
import com.felipecarneiro.cursomc.domain.ItemPedido;
import com.felipecarneiro.cursomc.domain.Pagamento;
import com.felipecarneiro.cursomc.domain.PagamentoComBoleto;
import com.felipecarneiro.cursomc.domain.PagamentoComCartao;
import com.felipecarneiro.cursomc.domain.Pedido;
import com.felipecarneiro.cursomc.domain.Produto;
import com.felipecarneiro.cursomc.domain.enums.EstadoPagamento;
import com.felipecarneiro.cursomc.domain.enums.TipoCliente;
import com.felipecarneiro.cursomc.repositories.CategoriaRepository;
import com.felipecarneiro.cursomc.repositories.CidadeRepository;
import com.felipecarneiro.cursomc.repositories.ClienteRepository;
import com.felipecarneiro.cursomc.repositories.EnderecoRepository;
import com.felipecarneiro.cursomc.repositories.EstadoRepository;
import com.felipecarneiro.cursomc.repositories.ItemPedidoRepository;
import com.felipecarneiro.cursomc.repositories.PagamentoRepository;
import com.felipecarneiro.cursomc.repositories.PedidoRepository;
import com.felipecarneiro.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

	@Autowired
	CategoriaRepository categoriaRepository;
	@Autowired
	ProdutoRepository produtoRepository;
	@Autowired
	CidadeRepository cidadeRepository;
	@Autowired
	EstadoRepository estadoRepository;
	@Autowired
	EnderecoRepository enderecoRepository;
	@Autowired
	ClienteRepository clienteRepository;
	@Autowired
	PedidoRepository pedidoRepository;
	@Autowired
	PagamentoRepository pagamentoRepository;
	@Autowired
	ItemPedidoRepository itemPedidoRepository;
	
	
	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Categoria cat1 = new Categoria(null,"Informática");
		Categoria cat2 = new Categoria(null,"Escritório");
		
		Produto p1 = new Produto(null, "Computador",2000.00);
		Produto p2 = new Produto(null, "Impressora",800.00);
		Produto p3 = new Produto(null, "Mouse",80.00);
		
		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");
		
		Cidade c1 = new Cidade(null, "Uberlândia",est1);
		Cidade c2 = new Cidade(null, "São Paulo",est2);
		Cidade c3 = new Cidade(null, "Campinas",est2);
		
		Cliente cli1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "36378912377", TipoCliente.PESSOAFISICA);
		
		Endereco e1 = new Endereco(null, "Rua Flores", "300", "Apto 203", "Jardim", "38220834", cli1, c1);
		Endereco e2 = new Endereco(null, "Avenida Matos", "105", "sala 800", "Centro", "38777012", cli1, c2);
		
		SimpleDateFormat sdf = new SimpleDateFormat("ss/MM/yyyy HH:mm");
		Pedido ped1 = new Pedido(null,sdf.parse("30/09/2017 10:32"), cli1, e1);
		Pedido ped2 = new Pedido(null,sdf.parse("10/10/2017 19:35"), cli1, e2);
		
		Pagamento pagto1 = new PagamentoComCartao(null,EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagto1);
		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2017 00:00"), null);
		ped2.setPagamento(pagto2);
		
		
		ItemPedido ip1 = new ItemPedido(ped1,p1,0.0,2000.00,1);
		ItemPedido ip2 = new ItemPedido(ped1, p3, 0.0, 80.00, 2);
		ItemPedido ip3 = new ItemPedido(ped2, p2, 100.00, 800.00, 1);
		
		ped1.getItens().addAll(Arrays.asList(ip1,ip2));
		ped2.getItens().addAll(Arrays.asList(ip3));
		
		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip3));
		p3.getItens().addAll(Arrays.asList(ip2));

		
		
		cli1.getPedidos().addAll(Arrays.asList(ped1,ped2));
		cli1.getTelefones().addAll(Arrays.asList("27363323","93838393"));
		
		cat1.getProdutos().addAll(Arrays.asList(p1,p2,p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));

		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1,cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));

		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2,c3));
		
		cli1.getEnderecos().addAll(Arrays.asList(e1,e2));
		
		categoriaRepository.saveAll(Arrays.asList(cat1,cat2));
		produtoRepository.saveAll(Arrays.asList(p1,p2,p3));
		estadoRepository.saveAll(Arrays.asList(est1,est2));
		cidadeRepository.saveAll(Arrays.asList(c1,c2,c3));
		clienteRepository.saveAll(Arrays.asList(cli1));
		enderecoRepository.saveAll(Arrays.asList(e1,e2));
		pedidoRepository.saveAll(Arrays.asList(ped1,ped2));
		pagamentoRepository.saveAll(Arrays.asList(pagto1,pagto2));
		itemPedidoRepository.saveAll(Arrays.asList(ip1,ip2,ip3));
		

	
	}
}

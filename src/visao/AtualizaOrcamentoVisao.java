package visao;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import com.jgoodies.forms.builder.ButtonBarBuilder;
import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.toedter.calendar.JDateChooser;
import com.toedter.components.JSpinField;

import controle.LumanferControle;
import modelo.vo.EmpresaVO;
import modelo.vo.ItensPedidoVO;
import modelo.vo.OrcamentoVO;

public class AtualizaOrcamentoVisao extends JFrame{
	private LumanferControle controle;
	private GerenciarOrcamentosVisao gerenciarOrcamentosVisao;

	private DefaultTableModel tableModel;

	private JTable table;

	private JScrollPane barraRolagem;

	private JSpinField spinQtd;
	private JSpinField spinNumOrc;

	public JTextField textCodAlternativo;
	public JTextField textDescricao;
	public JTextField textProduto;
	private JTextField textCliente;
	private JTextField textCondPag;
	private JTextField textObs;
	public JTextField textValorProd;

	private JDateChooser dateEmissao;
	private JDateChooser dateValidade;

	private JComboBox comboEmpresa;

	private JButton buttonAddProd;
	private JButton buttonDelProd;
	private JButton buttonAdicionar;
	private JButton buttonFechar;
	private JButton buttonAddNovaEmpresa;
	private JButton buttonPesqProd;

	private ImageIcon iconAdd;
	private ImageIcon iconVoltar;

	private ArrayList<EmpresaVO> arrayEmpresas;
	private ArrayList<ItensPedidoVO> itensTabela;

	private int id;

	//construtor
	public AtualizaOrcamentoVisao(LumanferControle controle, GerenciarOrcamentosVisao gerenciarOrcamentosVisao , int id) {
		super("Atualizar Orçamento");

		this.controle = controle;
		this.gerenciarOrcamentosVisao = gerenciarOrcamentosVisao;
		this.id = id;

		try { 
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");	// Java Swing Nimbus

		} 
		catch (Exception e) 
		{ }

		//chama o metodo responsavel por criar a JTable 
		criaJTable();

		//chama o metodo rsponsavel por inicializar os componentes
		inicializaComponentes();

		AutoCompleteDecorator.decorate(comboEmpresa);

		this.getContentPane().add(this.montaPainel());

		buttonAddProd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionarProduto();
			}
		});

		buttonDelProd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deletarProduto();
			}
		});

		buttonAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				atualizar();
			}
		});

		buttonFechar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fechar();
			}
		});

		buttonAddNovaEmpresa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionarEmpresa();
			}
		});

		buttonPesqProd.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				pesquisaProduto();
			}
		});
	}

	//metodo responsavel por criar a JTable
	private void criaJTable() {
		//inicializa a JTable
		table = new JTable();

		//mas a JTable precisa de algo para manipular seus dados(inserir linha, excluir...)
		tableModel = new DefaultTableModel() {
			//por padrao o DefaultTableModel permite fazer alteracoes na JTable, por isso precisamos 
			//sobrecarregar seu metodo dizendo que nenhuma das linhas podera ser alterada
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		//definindo que o tableModel seta responsavel por manipular os dados da JTable
		table.setModel(tableModel);

		//adicionando todas as colunas da tabela
		tableModel.addColumn("Cód. Alternativo");
		tableModel.addColumn("Cód. Desenho");
		tableModel.addColumn("Descrição");
		tableModel.addColumn("QTD (Orc)");
		tableModel.addColumn("Valor Unid");
		tableModel.addColumn("Valor Total");

		table.getColumnModel().getColumn(0).setPreferredWidth(40);
		table.getColumnModel().getColumn(1).setPreferredWidth(40);
		table.getColumnModel().getColumn(3).setPreferredWidth(40);
		table.getColumnModel().getColumn(4).setPreferredWidth(30);	
		table.getColumnModel().getColumn(5).setPreferredWidth(30);	
		table.getColumnModel().getColumn(2).setPreferredWidth(250);

		//setando o numero de linhas da JTable inicialmente como 0
		tableModel.setNumRows(0);
		
		table.setRowHeight(20);
	}

	//metodo responsavel por inicializar os componentes
	private void inicializaComponentes() {
		//inicializando e adicionando a tabela a um ScrollPane, case precise de uma barra de rolagem
		barraRolagem = new JScrollPane(table);

		spinQtd = new JSpinField();
		spinQtd.setMinimum(1);
		spinQtd.setValue(1);

		spinNumOrc = new JSpinField();
		spinNumOrc.setMinimum(0);
		spinNumOrc.setValue(controle.getNovoNumOrcamento());
		spinNumOrc.setEnabled(false);

		textDescricao = new JTextField(15);
		textProduto = new JTextField(15);
		textCodAlternativo = new JTextField(15);
		textCliente = new JTextField(15);
		textCondPag = new JTextField(15);
		textObs = new JTextField(15);
		textValorProd = new JTextField(15);

		buttonAddProd = new JButton("+");
		buttonAdicionar = new JButton("");
		buttonDelProd = new JButton("-");
		buttonFechar = new JButton("");
		buttonAddNovaEmpresa = new JButton("+");
		buttonPesqProd = new JButton("...");

		iconAdd = new ImageIcon("./icones/salvar.png");
		iconVoltar = new ImageIcon("./icones/de-volta.png");

		buttonAdicionar.setIcon(iconAdd);
		buttonFechar.setIcon(iconVoltar);

		dateEmissao = new JDateChooser();
		dateValidade = new JDateChooser();

		dateEmissao.setDate(new Date());

		dateEmissao.setEnabled(false);

		//comboBox de empresas

		arrayEmpresas = controle.getEmpresas("", "");

		comboEmpresa = new JComboBox();

		for(EmpresaVO e : arrayEmpresas) {
			comboEmpresa.addItem(e.getDescricao());
		}

		comboEmpresa.setMaximumRowCount(10);

		itensTabela = new ArrayList<ItensPedidoVO>();

		textProduto.setEditable(false);
		textCodAlternativo.setEditable(false);

		pesquisa();
	}

	//metodo responsavel por montar o painel 
	private JComponent montaPainel() {
		FormLayout layout = new FormLayout(
				"10dlu:grow, right:120, 5dlu, 100dlu, 5dlu, p, 5dlu, right:100dlu, 5dlu, 100dlu, 5dlu, p, 5dlu, p, 10dlu:grow",
				"10dlu:grow, p, 5dlu, p, 5dlu, p, 5dlu, p, 5dlu, p, 5dlu, p, 5dlu, p, 5dlu, p, 5dlu, p, 5dlu, p, 5dlu, 50dlu, 15dlu, p, 10dlu:grow");

		DefaultFormBuilder builder = new DefaultFormBuilder(layout);

		CellConstraints cc = new CellConstraints();

		builder.addLabel("Orçamento:", cc.xy(2, 2));
		builder.add(spinNumOrc, cc.xy(4, 2));

		builder.addLabel("Val. proposta:", cc.xy(2, 4));
		builder.add(dateValidade, cc.xy(4, 4));

		builder.addLabel("Cond. Pagamento:", cc.xy(2, 6));
		builder.add(textCondPag, cc.xy(4, 6));

		builder.addLabel("Emissão:", cc.xy(8, 2));
		builder.add(dateEmissao, cc.xy(10, 2));

		builder.addLabel("Cliente:", cc.xy(8, 4));
		builder.add(textCliente, cc.xy(10, 4));

		builder.addLabel("Empresa:", cc.xy(8, 6));
		builder.add(comboEmpresa, cc.xy(10, 6));

		builder.addLabel("Observação:", cc.xy(2, 8));
		builder.add(textObs, cc.xyw(4, 8, 7));

		builder.add(buttonAddNovaEmpresa, cc.xy(12, 6));

		builder.addSeparator("Produtos", cc.xyw(2, 10, 11));

		builder.addLabel("Produto:", cc.xy(2, 12));
		builder.add(textProduto, cc.xy(4, 12));

		builder.add(buttonPesqProd, cc.xy(6, 12));

		builder.addLabel("Quantidade:", cc.xy(8, 14));
		builder.add(spinQtd, cc.xy(10, 14));

		builder.addLabel("Valor Unid:", cc.xy(2, 14));
		builder.add(textValorProd, cc.xy(4, 14));

		builder.addLabel("Descrição:", cc.xy(2, 16));
		builder.add(textDescricao, cc.xyw(4, 16, 7));

		builder.addLabel("Cód. Alternativo:", cc.xy(8, 12));
		builder.add(textCodAlternativo, cc.xy(10, 12));

		builder.add(barraRolagem, cc.xywh(2, 18, 11, 5));

		builder.add(buttonAddProd, cc.xy(14, 18));

		builder.add(buttonDelProd, cc.xy(14, 20));

		builder.add(montaBarraBotao(), cc.xyw(2, 24, 8));

		return builder.getPanel();
	}

	private void pesquisa() {
		OrcamentoVO o = controle.getOrcamento(id);
		itensTabela = o.getItens();

		spinNumOrc.setValue(id);
		textCliente.setText(o.getCliente());
		textCondPag.setText(o.getCondPagamento());
		textObs.setText(o.getObs());
		dateEmissao.setDate(converteDia(o.getEmissao()));
		dateValidade.setDate(converteDia(o.getValProposta()));

		comboEmpresa.setSelectedItem(o.getEmpresa());

		tableModel.setNumRows(0);
		float valor = 0f;

		for(ItensPedidoVO i : itensTabela) {

			valor = i.getQtd() * Float.parseFloat(i.getValorUnid());

			tableModel.addRow(new Object[] {
					i.getCodAlternativo(), i.getCodigoProduto(), i.getDescricaoProduto(), i.getQtd(), i.getValorUnid().replace(".", ","), (valor + "").replace(".", ",")
			});

		}
	}

	//metodo responsavel por fechar a tela
	private void fechar() {
		AtualizaOrcamentoVisao.this.dispose();

		if(gerenciarOrcamentosVisao != null) {
			gerenciarOrcamentosVisao.atualizaTabela();
			gerenciarOrcamentosVisao.setVisible(true);
		}
	}

	private void atualizar() {
		OrcamentoVO o;

		o = new OrcamentoVO();
		o.setId(spinNumOrc.getValue());
		o.setCliente(textCliente.getText());
		o.setCondPagamento(textCondPag.getText());
		o.setObs(textObs.getText());
		o.setEmissao(new SimpleDateFormat("yyyy-MM-dd").format(dateEmissao.getDate()));
		o.setIdEmpresa(arrayEmpresas.get(comboEmpresa.getSelectedIndex()).getId());
		o.setItens(itensTabela);
		o.setValProposta(new SimpleDateFormat("yyyy-MM-dd").format(dateValidade.getDate()));

		if(controle.atualizaOrcamento(o)) {
			JOptionPane.showMessageDialog(AtualizaOrcamentoVisao.this, String.format("Orçamento atualizado com sucesso"), "", JOptionPane.INFORMATION_MESSAGE);
			fechar();
		}else
			JOptionPane.showMessageDialog(AtualizaOrcamentoVisao.this, String.format("Erro ao atualizar orçamento"), "", JOptionPane.ERROR_MESSAGE);

	}

	private void deletarProduto() {
		int linhaSelecionada = table.getSelectedRow();

		if(table.getSelectedRowCount() == 1) {
			itensTabela.remove(linhaSelecionada);
			atualizaTabela();
		}
	}

	private void pesquisaProduto() {
		GerenciarProdutosVisao g = new GerenciarProdutosVisao(controle, null, null, null, null, null, null, null, AtualizaOrcamentoVisao.this);

		g.setSize(800, 500);
		g.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		g.setResizable(true);
		g.setLocationRelativeTo(null);
		g.getContentPane().setBackground(Color.WHITE);
		g.setVisible(true);

		this.setVisible(false);

	}

	private void adicionarProduto() {	
		if(!textProduto.getText().equals("")) {
			if(ehNumero(textValorProd.getText())) {
				ItensPedidoVO item = new ItensPedidoVO();

				String descricao = textDescricao.getText();

				if(descricao.equals(""))
					descricao = "";

				String codAlternativo = textCodAlternativo.getText();

				if(codAlternativo == null)
					codAlternativo = ""; 

				item.setCodAlternativo(codAlternativo);
				item.setCodigoProduto(textProduto.getText());
				item.setIdProduto(controle.getIdProduto(textProduto.getText()));
				item.setQtd(spinQtd.getValue());
				item.setDescricaoProduto(descricao);
				item.setValorUnid(textValorProd.getText().replace(",", "."));

				itensTabela.add(item);

				atualizaTabela();
			}else{
				JOptionPane.showMessageDialog(AtualizaOrcamentoVisao.this, String.format("Entre com um valor válido"), "", JOptionPane.ERROR_MESSAGE);
				textValorProd.requestFocus();
				textValorProd.selectAll();
			}
		}
	}

	private void adicionarEmpresa() {	
		String descricao = JOptionPane.showInputDialog("Entre com a nova empresa:");

		if(descricao != null) {
			if(descricao.trim().length() != 0) {
				descricao = descricao.toUpperCase();

				EmpresaVO empresa = new EmpresaVO();
				empresa.setDescricao(descricao);

				controle.adicionaEmpresa(empresa);

				arrayEmpresas = controle.getEmpresas("", "");

				comboEmpresa.removeAllItems();

				for(EmpresaVO e : arrayEmpresas)
					comboEmpresa.addItem(e.getDescricao());

				comboEmpresa.setSelectedItem(descricao);
			}
		}

	}

	private void atualizaTabela() {
		tableModel.setNumRows(0);

		float soma = 0f;
		float valor = 0f;

		for(ItensPedidoVO i : itensTabela) {

			valor = i.getQtd() * Float.parseFloat(i.getValorUnid());
			soma += valor;

			tableModel.addRow(new Object[] {
					i.getCodAlternativo(), i.getCodigoProduto(), i.getDescricaoProduto(), i.getQtd(), i.getValorUnid().replace(".", ","), (valor + "").replace(".", ",")
			});

		}

		tableModel.addRow(new Object[] {
				"-", "-", "-", "-", "-", (soma + "").replace(".", ",")
		});
	}

	public String transformaDateString(String dataBanco) {
		Date data = new Date();
		SimpleDateFormat formatoBanco = new SimpleDateFormat("yyyy-MM-dd");

		try {
			data = formatoBanco.parse(dataBanco);
		} catch (ParseException e) {

		}

		SimpleDateFormat formatoUsual = new SimpleDateFormat("dd/MM/yyyy");

		String dataString = formatoUsual.format(data); 

		return dataString;
	}

	//metodo responsavel por montar a barra de botoes que sera adicionada ao final da janela
	private Component montaBarraBotao() {
		return ButtonBarBuilder.create().addButton(buttonAdicionar, buttonFechar).build();	
	}

	private boolean ehNumero(String numero) {
		boolean ehNumero = true;

		numero = numero.trim();
		numero = numero.replace(",", "");
		numero = numero.replace("R$", "");
		numero = numero.replace(".", "");

		try {
			float n = Float.parseFloat(numero);
		}catch(NumberFormatException e){
			ehNumero = false;
		}

		return ehNumero;
	}

	//converte String em variavel do tipo date
	public Date converteDia(String dia) {
		Date horaDate = null;
		SimpleDateFormat data = new SimpleDateFormat("yyyy-MM-dd");

		try {
			horaDate = data.parse(dia);
		} catch (ParseException e) {
		}

		return horaDate;
	}

}

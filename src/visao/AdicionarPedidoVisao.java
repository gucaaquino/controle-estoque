package visao;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
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
import modelo.vo.PedidoVO;

public class AdicionarPedidoVisao extends JFrame{
	private LumanferControle controle;
	private LumanferMenu menu;

	private DefaultTableModel tableModel;

	private JTable table;

	private JScrollPane barraRolagem;

	private JSpinField spinQtd;

	public JTextField textOrcamento;
	private JTextField textIdPedido;
	public JTextField textCodAlternativo;
	public JTextField textDescricao;
	public JTextField textProduto;

	private JDateChooser dateEmissao;
	private JDateChooser dateEntregaPedido;

	private JRadioButton radioAtivo;
	private JRadioButton radioFinalizado;

	private ButtonGroup groupStatus;

	private JComboBox comboEmpresa;

	private JButton buttonAddProd;
	private JButton buttonDelProd;
	private JButton buttonAdicionarPedido;
	private JButton buttonLimpar;
	private JButton buttonFechar;
	private JButton buttonAddNovaEmpresa;
	private JButton buttonPesqProd;
	private JButton buttonPesqOrc;

	private ImageIcon iconAdd;
	private ImageIcon iconLimpar;
	private ImageIcon iconVoltar;

	private ArrayList<EmpresaVO> arrayEmpresas;
	private ArrayList<ItensPedidoVO> itensTabela;
	
	//construtor
	public AdicionarPedidoVisao(LumanferControle controle, LumanferMenu menu) {
		super("Adicionar Pedido");

		this.controle = controle;
		this.menu = menu;

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

		buttonAdicionarPedido.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionarPedido();
			}
		});

		buttonLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpar();
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
		
		buttonPesqOrc.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				pesquisaOrcamento();
			}
		});

		textIdPedido.addFocusListener(new FocusListener() {

			public void focusLost(FocusEvent e) {
				verificaCodigo();
			}

			public void focusGained(FocusEvent e) {

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
		tableModel.addColumn("Revisão");
		tableModel.addColumn("Local");
		tableModel.addColumn("Pedido");
		tableModel.addColumn("Estoque");

		table.getColumnModel().getColumn(0).setPreferredWidth(120);
		table.getColumnModel().getColumn(1).setPreferredWidth(120);
		table.getColumnModel().getColumn(2).setPreferredWidth(250);
		
		table.setDefaultRenderer(Object.class, new TableRenderer());

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
		
		textIdPedido = new JTextField(15);
		textDescricao = new JTextField(15);
		textProduto = new JTextField(15);
		textCodAlternativo = new JTextField(15);
		textOrcamento = new JTextField(15);
		
		textOrcamento.setEditable(false);

		buttonAddProd = new JButton("+");
		buttonAdicionarPedido = new JButton("");
		buttonDelProd = new JButton("-");
		buttonLimpar = new JButton("");
		buttonFechar = new JButton("");
		buttonAddNovaEmpresa = new JButton("+");
		buttonPesqProd = new JButton("...");
		buttonPesqOrc = new JButton("...");

		iconAdd = new ImageIcon("./icones/salvar.png");
		iconLimpar = new ImageIcon("./icones/clean.png");
		iconVoltar = new ImageIcon("./icones/de-volta.png");

		buttonAdicionarPedido.setIcon(iconAdd);
		buttonLimpar.setIcon(iconLimpar);
		buttonFechar.setIcon(iconVoltar);

		radioAtivo = new JRadioButton("Ativo");
		radioFinalizado = new JRadioButton("Em andamento");

		radioAtivo.setEnabled(false);
		radioFinalizado.setEnabled(false);

		radioAtivo.setSelected(true);

		groupStatus = new ButtonGroup();
		groupStatus.add(radioAtivo);
		groupStatus.add(radioFinalizado);

		dateEmissao = new JDateChooser();
		dateEntregaPedido = new JDateChooser();

		dateEmissao.setDate(new Date());
		dateEntregaPedido.setDate(new Date());

		dateEmissao.setEnabled(false);

		//comboBox de empresas

		arrayEmpresas = controle.getEmpresas("", "");

		comboEmpresa = new JComboBox();

		for(EmpresaVO e : arrayEmpresas) {
			comboEmpresa.addItem(e.getDescricao());
		}

		comboEmpresa.setMaximumRowCount(10);

		itensTabela = new ArrayList<ItensPedidoVO>();

		textIdPedido.requestFocus();

		textProduto.setEditable(false);
		textCodAlternativo.setEditable(false);
	}

	//metodo responsavel por montar o painel 
	private JComponent montaPainel() {
		FormLayout layout = new FormLayout(
				"10dlu:grow, right:100, 5dlu, 100dlu, 5dlu, p, 15dlu, right:100dlu, 5dlu, 100dlu, 5dlu, p, 5dlu, p, 10dlu:grow",
				"10dlu:grow, p, 5dlu, p, 5dlu, p, 5dlu, p, 5dlu, p, 5dlu, p, 5dlu, p, 5dlu, p, 5dlu, p, 5dlu, 50dlu, 15dlu, p, 10dlu:grow");

		DefaultFormBuilder builder = new DefaultFormBuilder(layout);

		CellConstraints cc = new CellConstraints();

		builder.addLabel("Status:", cc.xy(2, 2));
		builder.add(montaBarraRadio(), cc.xy(4, 2));

		builder.addLabel("Pedido:", cc.xy(2, 4));
		builder.add(textIdPedido, cc.xy(4, 4));

		builder.addLabel("Orçamento:", cc.xy(2, 6));
		builder.add(textOrcamento, cc.xy(4, 6));
		
		builder.add(buttonPesqOrc, cc.xy(6, 6));

		builder.addLabel("Emissão:", cc.xy(8, 2));
		builder.add(dateEmissao, cc.xy(10, 2));

		builder.addLabel("Entrega:", cc.xy(8, 4));
		builder.add(dateEntregaPedido, cc.xy(10, 4));

		builder.addLabel("Empresa:", cc.xy(8, 6));
		builder.add(comboEmpresa, cc.xy(10, 6));

		builder.add(buttonAddNovaEmpresa, cc.xy(12, 6));

		builder.addSeparator("Produtos", cc.xyw(2, 8, 11));

		builder.addLabel("Produto:", cc.xy(2, 10));
		builder.add(textProduto, cc.xyw(4, 10, 7));

		builder.add(buttonPesqProd, cc.xy(12, 10));

		builder.addLabel("Quantidade:", cc.xy(8, 12));
		builder.add(spinQtd, cc.xy(10, 12));
		
		builder.addLabel("Cód. Alternativo:", cc.xy(2, 12));
		builder.add(textCodAlternativo, cc.xy(4, 12));

		builder.addLabel("Descrição:", cc.xy(2, 14));
		builder.add(textDescricao, cc.xyw(4, 14, 7));

		builder.add(barraRolagem, cc.xywh(2, 16, 11, 5));

		builder.add(buttonAddProd, cc.xy(14, 16));

		builder.add(buttonDelProd, cc.xy(14, 18));

		builder.add(montaBarraBotao(), cc.xyw(2, 22, 8));

		return builder.getPanel();
	}

	//metodo responsavel por limpar todos os campos
	private void limpar() {
		textIdPedido.setText("");
		textOrcamento.setText("");
		textDescricao.setText("");
		textProduto.setText("");
		textCodAlternativo.setText("");

		comboEmpresa.setSelectedIndex(0);

		spinQtd.setValue(0);

		itensTabela.clear();

		atualizaTabela();

		textIdPedido.requestFocus();

		textIdPedido.setEnabled(true);
	}

	//metodo responsavel por fechar a tela
	private void fechar() {
		AdicionarPedidoVisao.this.dispose();

		if(menu != null)
			menu.atualizaTabela();
	}

	private void verificaCodigo() {
		String codigo = textIdPedido.getText();

		if(codigo.trim().length() != 0) {
			if(!controle.existePedido(codigo)) {
				textIdPedido.setEnabled(false);
			}else {
				textIdPedido.selectAll();
				textIdPedido.requestFocus();
			}
		}else {
			textIdPedido.selectAll();
			textIdPedido.requestFocus();
		}
	}

	private void adicionarPedido() {
		if(!textIdPedido.isEnabled()) {
			if(!textOrcamento.getText().equals("")) {
				PedidoVO pedidoVO = new PedidoVO();
	
				pedidoVO.setId(textIdPedido.getText());
				pedidoVO.setIdEmpresa(arrayEmpresas.get(comboEmpresa.getSelectedIndex()).getId());
				pedidoVO.setDataEmissao(new SimpleDateFormat("yyyy-MM-dd").format(dateEmissao.getDate()));
				pedidoVO.setDataEntrega(new SimpleDateFormat("yyyy-MM-dd").format(dateEntregaPedido.getDate()));
				pedidoVO.setNumeroOrc(Integer.parseInt(textOrcamento.getText()));
	
				if(controle.adicionaPedido(pedidoVO, itensTabela)) {
					JOptionPane.showMessageDialog(AdicionarPedidoVisao.this, String.format("Pedido " + pedidoVO.getId() + " cadastrado com sucesso"), "", JOptionPane.INFORMATION_MESSAGE);
					fechar();
				}else
					JOptionPane.showMessageDialog(AdicionarPedidoVisao.this, String.format("Erro ao cadastrar pedido"), "", JOptionPane.ERROR_MESSAGE);
			}else
				JOptionPane.showMessageDialog(AdicionarPedidoVisao.this, String.format("Selecione um orçamento"), "", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void deletarProduto() {
		int linhaSelecionada = table.getSelectedRow();

		if(table.getSelectedRowCount() == 1) {
			itensTabela.remove(linhaSelecionada);
			atualizaTabela();
		}
	}

	private void pesquisaProduto() {
		if(!textIdPedido.isEnabled()) {
			GerenciarProdutosVisao g = new GerenciarProdutosVisao(controle, null, AdicionarPedidoVisao.this, null, null, null, null, null, null);

			g.setSize(800, 500);
			g.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			g.setResizable(true);
			g.setLocationRelativeTo(null);
			g.getContentPane().setBackground(Color.WHITE);
			g.setVisible(true);

			this.setVisible(false);
		}
	}
	
	private void pesquisaOrcamento() {
		if(!textIdPedido.isEnabled()) {
			GerenciarOrcamentosVisao g = new GerenciarOrcamentosVisao(controle, null, AdicionarPedidoVisao.this);

			g.setSize(800, 500);
			g.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			g.setResizable(true);
			g.setLocationRelativeTo(null);
			g.getContentPane().setBackground(Color.WHITE);
			g.setVisible(true);

			this.setVisible(false);
		}
	}
	
	public void implementaOrcamento() {
		OrcamentoVO o = controle.getOrcamento(Integer.parseInt(textOrcamento.getText()));
		
		itensTabela = o.getItens();
		
		for(ItensPedidoVO i : itensTabela) {
			//as linhas da tabela devem ser do tipo Object
			tableModel.addRow(new Object[] {
					i.getCodAlternativo(), i.getCodigoProduto(), i.getDescricaoProduto(), controle.getRevisaoProduto(i.getCodigoProduto()), controle.getLocalProduto(i.getCodigoProduto()), i.getQtd(), controle.getQtdEstoque(i.getCodigoProduto())
			});
		}
	}

	private void adicionarProduto() {		
		if(!textIdPedido.isEnabled()) {
			if(!textProduto.getText().equals("")) {
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
				item.setIdPedido(textIdPedido.getText());
				item.setStatus(true);

				itensTabela.add(item);

				atualizaTabela();
			}
		}
	}

	private void adicionarEmpresa() {		
		if(!textIdPedido.isEnabled()) {
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
	}

	private void atualizaTabela() {
		tableModel.setNumRows(0);

		for(ItensPedidoVO i : itensTabela) {
			//as linhas da tabela devem ser do tipo Object
			tableModel.addRow(new Object[] {
					i.getCodAlternativo(), i.getCodigoProduto(), i.getDescricaoProduto(), controle.getRevisaoProduto(i.getCodigoProduto()), controle.getLocalProduto(i.getCodigoProduto()), i.getQtd(), controle.getQtdEstoque(i.getCodigoProduto())
			});
		}
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
	private Component montaBarraRadio() {
		return ButtonBarBuilder.create().addButton(radioAtivo, radioFinalizado).build();	
	}

	//metodo responsavel por montar a barra de botoes que sera adicionada ao final da janela
	private Component montaBarraBotao() {
		return ButtonBarBuilder.create().addButton(buttonAdicionarPedido, buttonLimpar, buttonFechar).build();	
	}
	
	public class TableRenderer extends DefaultTableCellRenderer {

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

			int qtdPedido = Integer.parseInt(table.getModel().getValueAt(row, 5).toString());
			int qtdEstoque = Integer.parseInt(table.getModel().getValueAt(row, 6).toString());
			
			if(row % 2 == 0) {
				if(qtdEstoque >= qtdPedido) {
					comp.setBackground(new Color(0, 255, 154));
				}else {
					comp.setBackground(Color.WHITE);
				}
			}else {
				if(qtdEstoque >= qtdPedido) {
					comp.setBackground(new Color(0, 247, 148));
				}else {
					comp.setBackground(new Color(242, 242, 242));
				}
			}
			
			if(column == 4) {
				comp.setForeground(Color.RED);
				comp.setFont(new Font("Arial", Font.BOLD, 12));
			}else {
				comp.setForeground(Color.BLACK);
				comp.setFont(new Font("Arial", Font.PLAIN, 12));
			}
			
			return comp;
		}
	}
}

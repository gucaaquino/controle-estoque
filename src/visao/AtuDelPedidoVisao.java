package visao;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

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
import modelo.vo.PedidoVO;

public class AtuDelPedidoVisao extends JFrame{
	private LumanferControle controle;

	private DefaultTableModel tableModel;

	private JTable table;

	private JScrollPane barraRolagem;

	private JSpinField spinQtd;

	private JRadioButton radioAtivo;
	private JRadioButton radioFinalizado;

	private ButtonGroup groupStatus;

	public JTextField textDescricao;
	private JTextField textPedidos;
	public JTextField textProdutos;
	public JTextField textCodAlternativo;
	private JTextField textOrcamento;

	private JDateChooser dateEmissao;
	private JDateChooser dateEntregaPedido;

	private JComboBox comboEmpresa;

	private JButton buttonAddProd;
	private JButton buttonDelProd;
	private JButton buttonSalvar;
	private JButton buttonDeletar;
	private JButton buttonFechar;
	private JButton buttonAddNovaEmpresa;
	private JButton buttonPesqProd;
	private JButton buttonPDF;
	private JButton buttonExcel;

	private ImageIcon iconAlt;
	private ImageIcon iconVoltar;
	private ImageIcon iconDel;
	private ImageIcon iconPdf;
	private ImageIcon iconExcel;

	private ArrayList<EmpresaVO> arrayEmpresas;
	public ArrayList<ItensPedidoVO> itensTabela;

	private PedidoVO pedidoVO;

	private String codigo;

	private LumanferMenu menu;

	private JFileChooser chooser;
	//construtor
	public AtuDelPedidoVisao(LumanferControle controle, String codigo, LumanferMenu menu) {
		super("Gerenciar Pedido");

		this.controle = controle;
		this.codigo = codigo;
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

		//monta todo o layout e em seguida o adiciona
		this.getContentPane().add(this.montaPainel());

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

		buttonDeletar.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				deletaPedido();
			}
		});

		buttonSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				atualizaPedido();
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

		buttonFechar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fechar();
			}
		});

		buttonPDF.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				gerarPDF();
			}
		});

		buttonExcel.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				gerarExcel();
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
				if(column != 4)
					return false;

				return true;
			}
		};

		//definindo que o tableModel seta responsavel por manipular os dados da JTable
		table.setModel(tableModel);

		//adicionando todas as colunas da tabela
		tableModel.addColumn("Cód. Alternativo");
		tableModel.addColumn("Cód. Desenho");
		tableModel.addColumn("Descrição");
		tableModel.addColumn("QTD (Pedido)");
		tableModel.addColumn("Ok");

		table.getColumnModel().getColumn(0).setPreferredWidth(100);
		table.getColumnModel().getColumn(3).setPreferredWidth(100);
		table.getColumnModel().getColumn(1).setPreferredWidth(130);	
		table.getColumnModel().getColumn(2).setPreferredWidth(350);
		table.getColumnModel().getColumn(4).setPreferredWidth(5);	

		TableColumn column = table.getColumnModel().getColumn(4);
		column.setCellEditor(table.getDefaultEditor(Boolean.class));
		column.setCellRenderer(table.getDefaultRenderer(Boolean.class));
		column.setHeaderRenderer(new CheckBoxHeader(table, 4));

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

		radioAtivo = new JRadioButton("Em andamento");
		radioFinalizado = new JRadioButton("Finalizado");

		groupStatus = new ButtonGroup();
		groupStatus.add(radioAtivo);
		groupStatus.add(radioFinalizado);

		textDescricao = new JTextField(15);
		textPedidos = new JTextField(15);
		textProdutos = new JTextField(15);
		textCodAlternativo = new JTextField(15);
		textOrcamento = new JTextField(15);

		buttonAddProd = new JButton("+");
		buttonSalvar = new JButton("");
		buttonDelProd = new JButton("-");
		buttonDeletar = new JButton("");
		buttonAddNovaEmpresa = new JButton("+");
		buttonFechar = new JButton("");
		buttonPesqProd = new JButton("...");
		buttonPDF = new JButton("");
		buttonExcel = new JButton("");

		iconAlt = new ImageIcon("./icones/salvar.png");
		iconVoltar = new ImageIcon("./icones/de-volta.png");
		iconDel = new ImageIcon("./icones/excluir.png");
		iconPdf = new ImageIcon("./icones/pdf.png");
		iconExcel = new ImageIcon("./icones/excel.png");

		buttonSalvar.setIcon(iconAlt);
		buttonFechar.setIcon(iconVoltar);
		buttonDeletar.setIcon(iconDel);
		buttonPDF.setIcon(iconPdf);
		buttonExcel.setIcon(iconExcel);

		dateEmissao = new JDateChooser();
		dateEntregaPedido = new JDateChooser();

		dateEmissao.setEnabled(false);

		dateEmissao.setDate(new Date());

		//comboBox de empresas
		arrayEmpresas = controle.getEmpresas("", "");

		comboEmpresa = new JComboBox();

		for(EmpresaVO e : arrayEmpresas)
			comboEmpresa.addItem(e.getDescricao());

		comboEmpresa.setMaximumRowCount(10);

		itensTabela = new ArrayList<ItensPedidoVO>();

		desativaElementos();

		textDescricao.setEditable(false);
		textPedidos.setEditable(false);
		textCodAlternativo.setEditable(false);
		textOrcamento.setEditable(false);

		if(codigo != null) {
			textPedidos.setText(codigo);
			pesquisarPedido();
		}

		chooser = new JFileChooser();

		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

		FileNameExtensionFilter filter = new FileNameExtensionFilter("Pdf file(.pdf)", "pdf");

		chooser.resetChoosableFileFilters();
		chooser.setFileFilter(filter);		
		chooser.setApproveButtonText("Gerar PDF");
	}

	//metodo responsavel por montar o painel 
	private JComponent montaPainel() {
		FormLayout layout = new FormLayout(
				"10dlu:grow, right:100, 5dlu, 100dlu, 5dlu, p, 5dlu, right:100dlu, 5dlu, 100dlu, 5dlu, p, 5dlu, p, 10dlu:grow",
				"10dlu:grow, p, 5dlu, p, 5dlu, p, 5dlu, p, 5dlu, p, 5dlu, p, 5dlu, p, 5dlu, p, 5dlu, p, 5dlu, 50dlu, 15dlu, p, 10dlu:grow");

		DefaultFormBuilder builder = new DefaultFormBuilder(layout);

		CellConstraints cc = new CellConstraints();

		builder.addLabel("Status:", cc.xy(2, 2));
		builder.add(montaBarraRadio(), cc.xyw(4, 2, 5));

		builder.addLabel("Pedido:", cc.xy(2, 4));
		builder.add(textPedidos, cc.xy(4, 4));

		builder.addLabel("Orçamento:", cc.xy(2, 6));
		builder.add(textOrcamento, cc.xy(4, 6));

		builder.addLabel("Emissão:", cc.xy(8, 2));
		builder.add(dateEmissao, cc.xy(10, 2));

		builder.addLabel("Entrega:", cc.xy(8, 4));
		builder.add(dateEntregaPedido, cc.xy(10, 4));

		builder.addLabel("Empresa:", cc.xy(8, 6));
		builder.add(comboEmpresa, cc.xy(10, 6));
		builder.add(buttonAddNovaEmpresa, cc.xy(12, 6));

		builder.addSeparator("Produtos", cc.xyw(2, 8, 11));

		builder.addLabel("Produto:", cc.xy(2, 10));
		builder.add(textProdutos, cc.xyw(4, 10, 7));
		builder.add(buttonPesqProd, cc.xy(12, 10));

		builder.addLabel("Quantidade:", cc.xy(8, 12));
		builder.add(spinQtd, cc.xy(10, 12));

		builder.addLabel("Descrição:", cc.xy(2, 14));
		builder.add(textDescricao, cc.xyw(4, 14, 7));

		builder.addLabel("Cód. Alternativo:", cc.xy(2, 12));
		builder.add(textCodAlternativo, cc.xy(4, 12));

		builder.add(barraRolagem, cc.xywh(2, 16, 11, 5));

		builder.add(buttonAddProd, cc.xy(14, 16));

		builder.add(buttonDelProd, cc.xy(14, 18));

		builder.add(montaBarraBotao(), cc.xyw(2, 22, 8));

		return builder.getPanel();
	}

	private void atualizaPedido() {
		if(!textPedidos.isEditable()) {
			PedidoVO p = new PedidoVO();
			p.setId(textPedidos.getText());
			p.setIdEmpresa(arrayEmpresas.get(comboEmpresa.getSelectedIndex()).getId());
			p.setDataEmissao(new SimpleDateFormat("yyyy-MM-dd").format(dateEmissao.getDate()));
			p.setDataEntrega(new SimpleDateFormat("yyyy-MM-dd").format(dateEntregaPedido.getDate()));
			p.setNumeroOrc(Integer.parseInt(textOrcamento.getText()));

			if(radioAtivo.isSelected())
				p.setStatus("true");
			else
				p.setStatus("false");

			int cont = 0;

			for(ItensPedidoVO i : itensTabela) {
				boolean check = !Boolean.valueOf(table.getValueAt(cont, 4).toString());
				i.setStatus(check);
				cont++;
			}

			if(controle.alteraPedido(p, itensTabela)) {
				if(menu != null)
					menu.atualizaTabela();
				
				JOptionPane.showMessageDialog(AtuDelPedidoVisao.this, String.format("Pedido alterado com sucesso"), "", JOptionPane.INFORMATION_MESSAGE);
				
			}else
				JOptionPane.showMessageDialog(AtuDelPedidoVisao.this, String.format("Erro ao alterar pedido"), "", JOptionPane.ERROR_MESSAGE);

			pesquisarPedido();
		}
	}

	private void gerarExcel() {
		AdicionarExcelVisao a;

		if(table.getRowCount() != 0) {			
			a = new AdicionarExcelVisao(pedidoVO, itensTabela, AtuDelPedidoVisao.this, controle);

			a.setSize(830, 500);
			a.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			a.setResizable(false);
			a.setLocationRelativeTo(null);
			a.getContentPane().setBackground(Color.WHITE);
			a.setVisible(true);

			this.setVisible(false);
		}
	}

	private void gerarPDF() {
		if(tableModel.getRowCount() != 0) {
			
			int ret = chooser.showOpenDialog(this);

			if(ret == JFileChooser.APPROVE_OPTION) {
				String caminho;
				File file = chooser.getSelectedFile();

				GerarPDFPedidoVisao g;

				if(!(file.getName() == "")) {
					if(file.getName().contains(".pdf")) 
						caminho = file.getPath();

					else 
						caminho =file.getPath() + ".pdf";

					g = new GerarPDFPedidoVisao(caminho, pedidoVO, itensTabela, controle);
					if(g.montaPDF())
						JOptionPane.showMessageDialog(AtuDelPedidoVisao.this, String.format("PDF gerado com sucesso"), "", JOptionPane.INFORMATION_MESSAGE);
					else
						JOptionPane.showMessageDialog(AtuDelPedidoVisao.this, String.format("Erro ao gerar PDF"), "", JOptionPane.ERROR_MESSAGE);
				}

			}
		}
	}

	private void adicionarEmpresa() {		
		if(!textPedidos.isEditable()) {
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

	private void desativaElementos() {
		textPedidos.setEditable(true);
	}

	private void ativaElementos() {
		textPedidos.setEditable(false);
	}

	public void pesquisarPedido() {
		pedidoVO = controle.getPedido(textPedidos.getText());

		if(pedidoVO != null) {
			textPedidos.setEditable(false);
			buttonSalvar.setEnabled(true);

			textOrcamento.setText("" + pedidoVO.getOrcamento());
			comboEmpresa.setSelectedItem(pedidoVO.getEmpresa());

			dateEmissao.setDate(converteDia(pedidoVO.getDataEmissao()));
			dateEntregaPedido.setDate(converteDia(pedidoVO.getDataEntrega()));

			if(pedidoVO.getStatus().equals("true"))
				radioAtivo.setSelected(true);
			else
				radioFinalizado.setSelected(true);

			tableModel.setNumRows(0);

			itensTabela = controle.getProdutosPedido(textPedidos.getText());

			atualizaTabela();

			ativaElementos();
		}
	}

	//metodo responsavel por fechar a tela
	private void fechar() {
		AtuDelPedidoVisao.this.dispose();
		if(menu != null)
			menu.atualizaTabela();
	}

	private void deletarProduto() {
		int linhaSelecionada = table.getSelectedRow();

		if(table.getSelectedRowCount() == 1) {
			itensTabela.remove(linhaSelecionada);
			atualizaTabela();
		}

	}

	public void adicionarProduto() {
		if(!textPedidos.isEditable()) {
			if(!textProdutos.getText().equals("")) {
				ItensPedidoVO item = new ItensPedidoVO();

				String descricao = textDescricao.getText();

				if(descricao == null)
					descricao = "";

				String codAlternativo = textCodAlternativo.getText();

				if(codAlternativo.equals(""))
					codAlternativo = ""; 

				item.setCodigoProduto(textProdutos.getText());
				item.setIdProduto(controle.getIdProduto(item.getCodigoProduto()));
				item.setIdPedido(textPedidos.getText());
				item.setQtd(spinQtd.getValue());
				item.setDescricaoProduto(descricao);
				item.setStatus(true);
				item.setCodAlternativo(codAlternativo);

				itensTabela.add(item);

				atualizaTabela();
			}
		}

	}

	private void pesquisaProduto() {
		if(!textPedidos.isEditable()) {
			GerenciarProdutosVisao g = new GerenciarProdutosVisao(controle, null, null, AtuDelPedidoVisao.this, null, null, null, null, null);

			g.setSize(800, 500);
			g.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			g.setResizable(true);
			g.setLocationRelativeTo(null);
			g.getContentPane().setBackground(Color.WHITE);
			g.setVisible(true);

			this.setVisible(false);
		}
	}

	private void deletaPedido() {
		if(!textPedidos.isEditable()) {
			int opcao = JOptionPane.showConfirmDialog(AtuDelPedidoVisao.this, "Tem certeza que deseja excluir esse pedido?", "CUIDADO", JOptionPane.OK_CANCEL_OPTION);

			if(opcao == JOptionPane.OK_OPTION) {
				String codigo = textPedidos.getText();

				if(controle.excluiPedido(codigo, itensTabela)) {
					JOptionPane.showMessageDialog(AtuDelPedidoVisao.this, String.format("Pedido deletado com sucesso"), "", JOptionPane.INFORMATION_MESSAGE);

					fechar();
				}else
					JOptionPane.showMessageDialog(AtuDelPedidoVisao.this, String.format("Erro ao excluir pedido"), "", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void atualizaTabela() {
		tableModel.setNumRows(0);

		for(ItensPedidoVO i : itensTabela) {
			//as linhas da tabela devem ser do tipo Object

			tableModel.addRow(new Object[] {
					i.getCodAlternativo(), i.getCodigoProduto(), i.getDescricaoProduto(), i.getQtd(), !i.isStatus()
			});
		}
	}

	private class CheckBoxHeader extends JCheckBox implements TableCellRenderer {
		CheckBoxHeader(JTable table, int checkboxColumn) {

			table.getTableHeader().addMouseListener(new MyMouseListener(this, checkboxColumn));

			this.addItemListener((event) -> {
				Object source = event.getSource();
				if (!(source instanceof AbstractButton)) return;

				boolean checked = event.getStateChange() == ItemEvent.SELECTED;

				for (int x = 0, y = table.getRowCount(); x < y; x++)
					table.setValueAt(checked, x, 4);
			});
		}

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			return this;
		}
	}

	public class MyMouseListener extends MouseAdapter {
		private final CheckBoxHeader checkbox;
		private final int checkboxColumn;

		MyMouseListener(CheckBoxHeader checkbox, int checkboxColumn) {
			this.checkbox = checkbox;
			this.checkboxColumn = checkboxColumn;
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			JTableHeader header = (JTableHeader) e.getSource();
			JTable tableView = header.getTable();
			TableColumnModel columnModel = tableView.getColumnModel();
			int viewColumn = columnModel.getColumnIndexAtX(e.getX());
			int column = tableView.convertColumnIndexToModel(viewColumn);

			if (viewColumn == checkboxColumn && e.getClickCount() == 1 && column != -1)
				checkbox.doClick();
		}
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

	//metodo responsavel por montar a barra de botoes que sera adicionada ao final da janela
	private Component montaBarraRadio() {
		return ButtonBarBuilder.create().addButton(radioAtivo, radioFinalizado).build();	
	}

	private Component montaBarraBotao() {
		return ButtonBarBuilder.create().addButton(buttonSalvar, buttonDeletar, buttonExcel, buttonPDF, buttonFechar).build();	
	}
}

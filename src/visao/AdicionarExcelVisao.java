package visao;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import com.jgoodies.forms.builder.ButtonBarBuilder;
import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.toedter.calendar.JDateChooser;
import com.toedter.components.JSpinField;

import controle.LumanferControle;
import modelo.vo.ItensPedidoVO;
import modelo.vo.OrcamentoVO;
import modelo.vo.PedidoVO;

public class AdicionarExcelVisao extends JFrame{
	private AtuDelPedidoVisao pedidoVisao;
	private LumanferControle controle;

	private DefaultTableModel tableModel;

	private JTable table;

	private JScrollPane barraRolagem;
	
	private JSpinField spinNumOrc;

	private JTextField textPedidos;
	private JTextField textEmpresa;
	private JTextField textCliente;
	private JTextField textPag;
	private JTextField textCondPag;
	private JTextField textObs;

	private JDateChooser dateEntregaPedido;

	private JButton buttonExcel;
	private JButton buttonFechar;

	private ImageIcon iconVoltar;
	private ImageIcon iconExcel;

	private ArrayList<ItensPedidoVO> itensTabela;

	private PedidoVO pedidoVO;

	private JFileChooser chooser;

	//construtor
	public AdicionarExcelVisao(PedidoVO pedidoVO, ArrayList<ItensPedidoVO> itensTabela, AtuDelPedidoVisao pedidoVisao, LumanferControle controle) {
		super("Gerar Excel");

		this.pedidoVO = pedidoVO;
		this.itensTabela = itensTabela;
		this.pedidoVisao = pedidoVisao;
		this.controle = controle;
		
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

		buttonExcel.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				gerarExcel();
			}
		});

		buttonFechar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fechar();
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
		tableModel.addColumn("Código");
		tableModel.addColumn("Descrição");
		tableModel.addColumn("QTD (Pedido)");

		table.getColumnModel().getColumn(2).setPreferredWidth(100);
		table.getColumnModel().getColumn(0).setPreferredWidth(130);	
		table.getColumnModel().getColumn(1).setPreferredWidth(350);

		//setando o numero de linhas da JTable inicialmente como 0
		tableModel.setNumRows(0);
	}

	//metodo responsavel por inicializar os componentes
	private void inicializaComponentes() {
		//inicializando e adicionando a tabela a um ScrollPane, case precise de uma barra de rolagem
		barraRolagem = new JScrollPane(table);

		spinNumOrc = new JSpinField();
		spinNumOrc.setMinimum(0);
		
		textPedidos = new JTextField(15);
		textEmpresa = new JTextField(15);
		textCliente = new JTextField(15);
		textCondPag = new JTextField(15);
		textObs = new JTextField(15);
		textPag = new JTextField(15);

		buttonExcel = new JButton("");
		buttonFechar = new JButton("");

		iconVoltar = new ImageIcon("./icones/de-volta.png");
		iconExcel = new ImageIcon("./icones/excel.png");

		buttonExcel.setIcon(iconExcel);
		buttonFechar.setIcon(iconVoltar);

		dateEntregaPedido = new JDateChooser();

		desativaElementos();

		preencheCampos();

		chooser = new JFileChooser();

		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

		FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel", "xls");

		chooser.resetChoosableFileFilters();
		chooser.setFileFilter(filter);		
		chooser.setApproveButtonText("Gerar Excel");
	}

	//metodo responsavel por montar o painel 
	private JComponent montaPainel() {
		FormLayout layout = new FormLayout(
				"10dlu:grow, right:100, 5dlu, 100dlu, 5dlu, p, 5dlu, right:100dlu, 5dlu, 100dlu, 5dlu, p, 5dlu, p, 10dlu:grow",
				"10dlu:grow, p, 5dlu, p, 5dlu, p, 5dlu, p, 5dlu, p, 5dlu, p, 5dlu, p, 5dlu, p, 5dlu, 100dlu, 15dlu, p, 10dlu:grow");

		DefaultFormBuilder builder = new DefaultFormBuilder(layout);

		CellConstraints cc = new CellConstraints();

		builder.addLabel("Pedido:", cc.xy(2, 2));
		builder.add(textPedidos, cc.xy(4, 2));

		builder.addLabel("Orçamento:", cc.xy(2, 4));
		builder.add(spinNumOrc, cc.xy(4, 4));

		builder.addLabel("Entrega:", cc.xy(8, 2));
		builder.add(dateEntregaPedido, cc.xy(10, 2));

		builder.addLabel("Empresa:", cc.xy(8, 4));
		builder.add(textEmpresa, cc.xy(10, 4));

		builder.addLabel("Valor proposta:", cc.xy(2, 6));
		builder.add(textPag, cc.xy(4, 6));

		builder.addLabel("Cliente:", cc.xy(8, 6));
		builder.add(textCliente, cc.xy(10, 6));

		builder.addLabel("Cond. Pagamento:", cc.xy(2, 8));
		builder.add(textCondPag, cc.xy(4, 8));

		builder.addLabel("Observação:", cc.xy(8, 8));
		builder.add(textObs, cc.xy(10, 8));

		builder.addSeparator("Produtos", cc.xyw(2, 10, 11));

		builder.add(barraRolagem, cc.xywh(2, 12, 11, 7));

		builder.add(montaBarraBotao(), cc.xyw(2, 20, 8));

		return builder.getPanel();
	}

	private void gerarExcel() {
		OrcamentoVO o;
		
		int ret = chooser.showOpenDialog(this);

		if(ret == JFileChooser.APPROVE_OPTION) {
			String caminho;
			File file = chooser.getSelectedFile();

			GerarExcelPedidoVisao g;

			if(!(file.getName() == "")) {
				if(file.getName().contains(".xls")) 
					caminho = file.getPath();

				else 
					caminho =file.getPath() + ".xls";
				
				o = new OrcamentoVO();
				o.setCliente(textEmpresa.getText());
				o.setCondPagamento(textCondPag.getText());
				o.setEmpresa(textCliente.getText());
				o.setItens(itensTabela);
				o.setId(spinNumOrc.getValue());
				o.setObs(textObs.getText());
				o.setPedido(textPedidos.getText());
				o.setEmissao(new SimpleDateFormat("dd/MM/yyyy").format(dateEntregaPedido.getDate()));
				o.setValProposta(textPag.getText());			

				g = new GerarExcelPedidoVisao(o, caminho);
				if(g.geraExcel()) {
					controle.alteraOrcamentoPedido(o.getPedido(), o.getId());
					JOptionPane.showMessageDialog(AdicionarExcelVisao.this, String.format("Excel gerado com sucesso"), "", JOptionPane.INFORMATION_MESSAGE);
					
				}else
					JOptionPane.showMessageDialog(AdicionarExcelVisao.this, String.format("Erro ao gerar Excel"), "", JOptionPane.ERROR_MESSAGE);
			}

		}

	}

	private void preencheCampos() {
		textPedidos.setEditable(false);
		buttonExcel.setEnabled(true);

		textPedidos.setText(pedidoVO.getId());
		
		int numOrc = pedidoVO.getOrcamento();
		
		spinNumOrc.setEnabled(false);
		
		float valorTot = 0f;
		
		for(ItensPedidoVO i : itensTabela) {
			valorTot += i.getQtd() * Float.parseFloat(i.getValorUnid());
		}
		
		String valor = valorTot + "";
		
		textPag.setText("R$ " + valor.replace(".", ","));
		
		spinNumOrc.setValue(numOrc);
		textEmpresa.setText(pedidoVO.getEmpresa());

		dateEntregaPedido.setDate(converteDia(pedidoVO.getDataEntrega()));

		tableModel.setNumRows(0);

		atualizaTabela();

		desativaElementos();

		OrcamentoVO o = controle.getOrcamento(numOrc);
		
		textCliente.setText(o.getCliente());
		textPag.setText("R$ " + o.getValProposta().replace(".", ","));
		textObs.setText(o.getObs());
		textCondPag.setText(o.getCondPagamento());
	}

	private void desativaElementos() {
		textPedidos.setEditable(false);
	}

	//metodo responsavel por fechar a tela
	private void fechar() {
		AdicionarExcelVisao.this.dispose();

		if(pedidoVisao != null) {
			pedidoVisao.pesquisarPedido();
			pedidoVisao.setVisible(true);
		}
	}

	private void atualizaTabela() {
		tableModel.setNumRows(0);

		for(ItensPedidoVO i : itensTabela) {
			//as linhas da tabela devem ser do tipo Object

			tableModel.addRow(new Object[] {
					i.getCodigoProduto(), i.getDescricaoProduto(), i.getQtd()
			});
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

	private Component montaBarraBotao() {
		return ButtonBarBuilder.create().addButton(buttonExcel, buttonFechar).build();	
	}
}

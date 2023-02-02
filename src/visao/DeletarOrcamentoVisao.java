package visao;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
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

public class DeletarOrcamentoVisao extends JFrame{
	private LumanferControle controle;
	private GerenciarOrcamentosVisao gerenciarOrcamentosVisao;

	private DefaultTableModel tableModel;

	private JTable table;

	private JScrollPane barraRolagem;

	private JSpinField spinNumOrc;

	private JTextField textCliente;
	private JTextField textCondPag;
	private JTextField textValorProposta;
	private JTextField textObs;
	private JTextField textEmpresa;

	private JDateChooser dateEmissao;

	private JButton buttonDeletar;
	private JButton buttonFechar;

	private ImageIcon iconDel;
	private ImageIcon iconVoltar;

	private ArrayList<ItensPedidoVO> itensTabela;

	private int id;

	//construtor
	public DeletarOrcamentoVisao(LumanferControle controle, GerenciarOrcamentosVisao gerenciarOrcamentosVisao , int id) {
		super("Deletar Orçamento");

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

		this.getContentPane().add(this.montaPainel());
		
		buttonDeletar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deletar();
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

		spinNumOrc = new JSpinField();
		spinNumOrc.setMinimum(0);
		spinNumOrc.setValue(controle.getNovoNumOrcamento());
		spinNumOrc.setEnabled(false);
		
		textCliente = new JTextField(15);
		textCondPag = new JTextField(15);
		textValorProposta = new JTextField(15);
		textObs = new JTextField(15);
		textEmpresa = new JTextField(15);
		
		textCliente.setEditable(false);
		textCondPag.setEditable(false);
		textValorProposta.setEditable(false);
		textObs.setEditable(false);
		textEmpresa.setEditable(false);

		textValorProposta.setText("R$ 0,0");

		buttonDeletar = new JButton("");
		buttonFechar = new JButton("");

		iconDel = new ImageIcon("./icones/excluir.png");
		iconVoltar = new ImageIcon("./icones/de-volta.png");

		buttonDeletar.setIcon(iconDel);
		buttonFechar.setIcon(iconVoltar);

		dateEmissao = new JDateChooser();

		dateEmissao.setDate(new Date());

		dateEmissao.setEnabled(false);

		itensTabela = new ArrayList<ItensPedidoVO>();
		
		pesquisa();
	}

	//metodo responsavel por montar o painel 
	private JComponent montaPainel() {
		FormLayout layout = new FormLayout(
				"10dlu:grow, right:120, 5dlu, 100dlu, 5dlu, p, 5dlu, right:100dlu, 5dlu, 100dlu, 5dlu, p, 5dlu, p, 10dlu:grow",
				"10dlu:grow, p, 5dlu, p, 5dlu, p, 5dlu, p, 5dlu, p, 5dlu, p, 5dlu, p, 5dlu, p, 5dlu, p, 5dlu, 100dlu, 15dlu, 10dlu:grow");

		DefaultFormBuilder builder = new DefaultFormBuilder(layout);

		CellConstraints cc = new CellConstraints();

		builder.addLabel("Orçamento:", cc.xy(2, 2));
		builder.add(spinNumOrc, cc.xy(4, 2));

		builder.addLabel("Val. proposta:", cc.xy(2, 4));
		builder.add(textValorProposta, cc.xy(4, 4));

		builder.addLabel("Cond. Pagamento:", cc.xy(2, 6));
		builder.add(textCondPag, cc.xy(4, 6));

		builder.addLabel("Emissão:", cc.xy(8, 2));
		builder.add(dateEmissao, cc.xy(10, 2));

		builder.addLabel("Cliente:", cc.xy(8, 4));
		builder.add(textCliente, cc.xy(10, 4));
		
		builder.addLabel("Empresa:", cc.xy(8, 6));
		builder.add(textEmpresa, cc.xy(10, 6));

		builder.addLabel("Observação:", cc.xy(2, 8));
		builder.add(textObs, cc.xyw(4, 8, 7));

		builder.addSeparator("Produtos", cc.xyw(2, 10, 11));

		builder.add(barraRolagem, cc.xywh(2, 16, 11, 5));

		builder.add(montaBarraBotao(), cc.xyw(2, 18, 12));
		
		builder.add(montaBarraBotao(), cc.xyw(2, 22, 8));

		return builder.getPanel();
	}

	private void pesquisa() {
		OrcamentoVO o = controle.getOrcamento(id);
		itensTabela = o.getItens();

		spinNumOrc.setValue(id);
		textCliente.setText(o.getCliente());
		textCondPag.setText(o.getCondPagamento());
		textObs.setText(o.getObs());
		textEmpresa.setText(o.getEmpresa());
		
		String valorProp = o.getValProposta();
		valorProp = valorProp.replace(".", ",");
		valorProp = "R$ " + valorProp;
		
		textValorProposta.setText(valorProp);

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
		DeletarOrcamentoVisao.this.dispose();

		if(gerenciarOrcamentosVisao != null) {
			gerenciarOrcamentosVisao.atualizaTabela();
			gerenciarOrcamentosVisao.setVisible(true);
		}
	}

	private void deletar() {
		int opcao = JOptionPane.showConfirmDialog(DeletarOrcamentoVisao.this, "Tem certeza que deseja excluir esse orçamento?", "CUIDADO", JOptionPane.OK_CANCEL_OPTION);

		if(opcao == JOptionPane.OK_OPTION) {
			int codigo = spinNumOrc.getValue();

			if(controle.excluiOrcamento(codigo)) {
				JOptionPane.showMessageDialog(DeletarOrcamentoVisao.this, String.format("Orçamento deletado com sucesso"), "", JOptionPane.INFORMATION_MESSAGE);

			}else
				JOptionPane.showMessageDialog(DeletarOrcamentoVisao.this, String.format("Erro ao excluir orçamento"), "", JOptionPane.ERROR_MESSAGE);
			
			fechar();
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
	private Component montaBarraBotao() {
		return ButtonBarBuilder.create().addButton(buttonDeletar, buttonFechar).build();	
	}
}

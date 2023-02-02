package visao;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import controle.LumanferControle;
import modelo.vo.EmpresaVO;
import modelo.vo.PedidoVO;

public class LumanferMenu extends JFrame{

	//relogio
	private int hh;
	private int mm;
	private int ss;
	private Calendar hora;   
	private DecimalFormat formato; 
	private JLabel relogio;	

	private String saudacoes;

	private JMenuItem localMenu;
	private JMenuItem empresaMenu;
	private JMenuItem produtoMenu;
	private JMenuItem orcamentoMenu;
	private JMenuItem producaoMenu;
	private JMenuItem sairMenu;

	private JMenuBar barra;

	private JLabel labelLogo;
	private JLabel labelLogin;
	private JLabel labelPesquisa;

	private ImageIcon imgLogo;
	private ImageIcon produtoIcon;
	private ImageIcon localIcon;
	private ImageIcon empresaIcon;
	private ImageIcon orcamentoIcon;
	private ImageIcon logoutIcon;
	private ImageIcon producaoIcon;
	private ImageIcon pedidoIcon;
	private ImageIcon atualizaIcon;

	private JRadioButton radioAtivo;
	private JRadioButton radioQq;

	private ButtonGroup groupPesquisa;

	private JTextField textPesquisa;

	private JComboBox comboEmpresas;

	private JButton buttonAddPedido;
	private JButton buttonAtualiza;
	private LumanferControle controle;

	private ArrayList<PedidoVO> itensTabela;
	private ArrayList<EmpresaVO> empresas;

	private DefaultTableModel tableModel;

	private JTable table;

	private JScrollPane barraRolagem;
	
	private JFileChooser chooser;
	
	boolean[] keys = new boolean[500];

	public LumanferMenu() {
		super("Lumanfer - Controle de Estoque");

		try { 
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");	// Java Swing Nimbus

		} 
		catch (Exception e) 
		{ }

		controle = new LumanferControle();

		criaJTable();
		inicializaComponentes();

		Timer time = new Timer(1000, ativar);  
		time.start();

		barra.add(produtoMenu);
		barra.add(producaoMenu);
		barra.add(localMenu);
		barra.add(empresaMenu);
		barra.add(orcamentoMenu);
		barra.add(sairMenu);
		
		setJMenuBar(barra);

		this.getContentPane().add(this.montaPainel());

		AutoCompleteDecorator.decorate(comboEmpresas);

		produtoMenu.addActionListener(
				new ActionListener(){ // classe interna anonima
					public void actionPerformed( ActionEvent event ) {
						gerenciarProdutos();
					}
				}
				);

		sairMenu.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				fechar();
			}
		});

		empresaMenu.addActionListener(
				new ActionListener(){ // classe interna anonima
					public void actionPerformed( ActionEvent event ) {
						gerenciarEmpresas();
					}
				}
				);

		localMenu.addActionListener(
				new ActionListener(){ // classe interna anonima
					public void actionPerformed( ActionEvent event ) {
						gerenciarLocais();
					}
				}
				);

		producaoMenu.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				gerenciarProducoes();
			}
		});

		orcamentoMenu.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				gerenciarOrcamentos();
			}
		});

		table.addMouseListener(new MouseListener() {

			public void mouseReleased(MouseEvent e) {

			}

			public void mousePressed(MouseEvent e) {

			}

			public void mouseExited(MouseEvent e) {

			}

			public void mouseEntered(MouseEvent e) {

			}

			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 2) 
					gerenciarPedido();
			}
		});

		textPesquisa.addKeyListener(new KeyListener() {

			public void keyTyped(KeyEvent arg0) {

			}

			public void keyReleased(KeyEvent arg0) {
				atualizaTabela();
			}

			public void keyPressed(KeyEvent arg0) {

			}
		});

		radioAtivo.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				atualizaTabela();
			}
		});

		buttonAtualiza.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				atualizaTabela();
			}
		});

		buttonAddPedido.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				adicionarPedido();
			}
		});

		radioQq.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				atualizaTabela();
			}
		});

		comboEmpresas.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				atualizaTabela();
			}
		});
		
//		textPesquisa.addKeyListener(new KeyAdapter() {
//		    public void keyPressed(KeyEvent e) {
//				if(e.getKeyCode() == KeyEvent.VK_ALT || e.getKeyCode() == KeyEvent.VK_V || e.getKeyCode() == KeyEvent.VK_B || e.getKeyCode() == KeyEvent.VK_P)
//					keys[e.getKeyCode()] = true;
//				
//		    	if (keys[KeyEvent.VK_ALT] && keys[KeyEvent.VK_V]){
//		            relatorioVendas();
//		            
//		            keys[KeyEvent.VK_ALT] = false;
//		            keys[KeyEvent.VK_V] = false;
//		        }else if(keys[KeyEvent.VK_ALT] && keys[KeyEvent.VK_P]) {
//		        	relatorioABC();
//		        	
//		        	keys[KeyEvent.VK_ALT] = false;
//		        	keys[KeyEvent.VK_P] = false;
//		        }else if(keys[KeyEvent.VK_ALT] && keys[KeyEvent.VK_B]) {
//		        	backup();
//		        	
//		        	keys[KeyEvent.VK_ALT] = false;
//		        	keys[KeyEvent.VK_B] = false;
//		        }
//		    }
//
//		    public void keyReleased(KeyEvent e) { 
//		    	if(e.getKeyCode() == KeyEvent.VK_ALT || e.getKeyCode() == KeyEvent.VK_V || e.getKeyCode() == KeyEvent.VK_B || e.getKeyCode() == KeyEvent.VK_P)
//					keys[e.getKeyCode()] = false;
//		    }
//
//		    public void keyTyped(KeyEvent e) { /* Not used */ }
//		});
	}
	
	private void backup() {		
		chooser = new JFileChooser();

		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.resetChoosableFileFilters();
		chooser.setApproveButtonText("Selecionar pasta");
		
		int ret = chooser.showOpenDialog(this);
		
		if(ret == JFileChooser.APPROVE_OPTION) {
			File file = chooser.getSelectedFile();
			String origem = file.getAbsolutePath();
			
			chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

			chooser.resetChoosableFileFilters();	
			chooser.setApproveButtonText("Gerar Backup");
			
			ret = chooser.showOpenDialog(this);
			
			if(ret == JFileChooser.APPROVE_OPTION) {
				file = chooser.getSelectedFile();
				String destino = file.getName();
				
				if(!(file.getName() == "")) {
					if(file.getName().contains(".zip")) 
						destino = file.getPath();

					else 
						destino =file.getPath() + ".zip";
				}
				
				if(Zipar.compactarParaZip(destino, new String[]{origem + "\\produtos.accdb", origem + "\\pedidos.accdb", origem + "\\usuarios.accdb", origem + "\\producao.accdb"}))
					JOptionPane.showMessageDialog(LumanferMenu.this, String.format("Backup gerado com sucesso"), "", JOptionPane.INFORMATION_MESSAGE);
				else
					JOptionPane.showMessageDialog(LumanferMenu.this, String.format("Erro ao gerar Backup"), "", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void gerenciarLocais() {
		GerenciarLocaisVisao g = new GerenciarLocaisVisao(controle, LumanferMenu.this);

		g.setSize(800, 500);
		g.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		g.setResizable(true);
		g.setLocationRelativeTo(null);
		g.getContentPane().setBackground(Color.WHITE);
		g.setVisible(true);
	}

	private void gerenciarOrcamentos() {
		GerenciarOrcamentosVisao g = new GerenciarOrcamentosVisao(controle, LumanferMenu.this, null);

		g.setSize(800, 500);
		g.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		g.setResizable(true);
		g.setLocationRelativeTo(null);
		g.getContentPane().setBackground(Color.WHITE);
		g.setVisible(true);
	}

	private void gerenciarEmpresas() {
		GerenciarEmpresasVisao g = new GerenciarEmpresasVisao(controle, LumanferMenu.this);

		g.setSize(800, 500);
		g.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		g.setResizable(true);
		g.setLocationRelativeTo(null);
		g.getContentPane().setBackground(Color.WHITE);
		g.setVisible(true);
	}

	private void gerenciarProducoes() {
		GerenciarProducaoVisao g = new GerenciarProducaoVisao(controle, LumanferMenu.this);

		g.setSize(800, 500);
		g.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		g.setResizable(true);
		g.setLocationRelativeTo(null);
		g.getContentPane().setBackground(Color.WHITE);
		g.setVisible(true);
	}

	private void adicionarPedido() {
		AdicionarPedidoVisao a = new AdicionarPedidoVisao(controle, LumanferMenu.this);

		a.setSize(1000, 550);
		a.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		a.setResizable(false);
		a.setLocationRelativeTo(null);
		a.getContentPane().setBackground(Color.WHITE);
		a.setVisible(true);
	}

	private void gerenciarPedido() {
		AtuDelPedidoVisao a = new AtuDelPedidoVisao(controle, table.getValueAt(table.getSelectedRow(), 0).toString(), LumanferMenu.this);

		a.setSize(830, 550);
		a.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		a.setResizable(true);
		a.setLocationRelativeTo(null);
		a.getContentPane().setBackground(Color.WHITE);
		a.setVisible(true);
	}

	private void relatorioVendas() {
		GerarRelatorioVendasVisao g = new GerarRelatorioVendasVisao(controle, LumanferMenu.this);
		g.setSize(1130, 650);
		g.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		g.setResizable(true);
		g.setLocationRelativeTo(null);
		g.getContentPane().setBackground(Color.WHITE);
		g.setVisible(true);
	}
	
	
	private void relatorioABC() {
		GerarRelatorioABCVisao g = new GerarRelatorioABCVisao(controle, LumanferMenu.this);
		g.setSize(1130, 650);
		g.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		g.setResizable(true);
		g.setLocationRelativeTo(null);
		g.getContentPane().setBackground(Color.WHITE);
		g.setVisible(true);
	}

	private void gerenciarProdutos() {
		GerenciarProdutosVisao g = new GerenciarProdutosVisao(controle, LumanferMenu.this, null, null, null, null, null, null, null);

		g.setSize(900, 500);
		g.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		g.setResizable(true);
		g.setLocationRelativeTo(null);
		g.getContentPane().setBackground(Color.WHITE);
		g.setVisible(true);
	}

	public void atualizaEmpresas() {
		empresas = controle.getEmpresas("", "");

		comboEmpresas.addItem("Qualquer empresa");

		for(EmpresaVO e : empresas) 
			comboEmpresas.addItem(e.getDescricao());
	}

	private void horas(){  
		hora = Calendar.getInstance();  
		hh = hora.get(Calendar.HOUR_OF_DAY);  
		mm = hora.get(Calendar.MINUTE);  
		ss = hora.get(Calendar.SECOND);  

		relogio.setText(formatar(hh)+":"+formatar(mm)+":"+formatar(ss));

	}  

	private String formatar(int num){  
		formato = new DecimalFormat("00");  
		return formato.format(num);  
	}  

	ActionListener ativar = (  
			new ActionListener(){  
				public void actionPerformed(ActionEvent e){  
					horas();  
				}  

			}  
			);

	public void inicializaComponentes() {
		controle = new LumanferControle();

		textPesquisa = new JTextField(15);

		produtoMenu = new JMenuItem("Produtos");
		localMenu = new JMenuItem("Locais");
		empresaMenu = new JMenuItem("Empresas");
		orcamentoMenu = new JMenuItem("Orçamentos");
		producaoMenu = new JMenuItem("Produção");
		sairMenu = new JMenuItem("Sair");

		buttonAddPedido = new JButton("Adicionar Pedido");
		buttonAtualiza = new JButton("Atualizar");

		labelPesquisa = new JLabel("Pesquisa:");

		//		finalizarItem.setMnemonic(KeyEvent.VK_F);

		barra = new JMenuBar();

		produtoIcon = new ImageIcon("./icones/novo-produto.png");
		localIcon = new ImageIcon("./icones/caixa.png");
		empresaIcon = new ImageIcon("./icones/companhia.png");
		orcamentoIcon = new ImageIcon("./icones/orcamento.png");
		producaoIcon = new ImageIcon("./icones/producao-em-massa.png");
		imgLogo = new ImageIcon("./imagens/logoInicio.png");
		logoutIcon = new ImageIcon("./icones/logout.png");
		pedidoIcon = new ImageIcon("./icones/pedido.png");
		atualizaIcon = new ImageIcon("./icones/reload.png");
		labelLogo = new JLabel(imgLogo);
		
		labelLogo.setBorder(BorderFactory.createLineBorder(Color.black));

		radioAtivo = new JRadioButton("Ativos");
		radioQq = new JRadioButton("Qualquer");

		groupPesquisa = new ButtonGroup();
		groupPesquisa.add(radioAtivo);
		groupPesquisa.add(radioQq);

		radioAtivo.setSelected(true);

		comboEmpresas = new JComboBox();

		atualizaEmpresas();

		produtoMenu.setIcon(produtoIcon);
		localMenu.setIcon(localIcon);
		empresaMenu.setIcon(empresaIcon);
		orcamentoMenu.setIcon(orcamentoIcon);
		producaoMenu.setIcon(producaoIcon);
		sairMenu.setIcon(logoutIcon);
		buttonAddPedido.setIcon(pedidoIcon);
		buttonAtualiza.setIcon(atualizaIcon);

		produtoMenu.setFont(new Font("Arial", Font.BOLD, 20));
		localMenu.setFont(new Font("Arial", Font.BOLD, 20));
		empresaMenu.setFont(new Font("Arial", Font.BOLD, 20));
		orcamentoMenu.setFont(new Font("Arial", Font.BOLD, 20));
		producaoMenu.setFont(new Font("Arial", Font.BOLD, 20));
		sairMenu.setFont(new Font("Arial", Font.BOLD, 20));
		labelPesquisa.setFont(new Font("Arial", Font.PLAIN, 20));
		radioAtivo.setFont(new Font("Arial", Font.PLAIN, 15));
		radioQq.setFont(new Font("Arial", Font.PLAIN, 15));
		comboEmpresas.setFont(new Font("Arial", Font.PLAIN, 15));
		buttonAddPedido.setFont(new Font("Arial", Font.PLAIN, 15));
		buttonAtualiza.setFont(new Font("Arial", Font.PLAIN, 15));

		//relogio
		hora = Calendar.getInstance();  
		hh = hora.get(Calendar.HOUR_OF_DAY);  
		mm = hora.get(Calendar.MINUTE);  
		ss = hora.get(Calendar.SECOND);  

		if(hh > 6 && hh < 12) {
			saudacoes = "Bom dia";
		} else if(hh >= 12 && hh <= 18){
			saudacoes = "Boa tarde"; 
		} else {
			saudacoes = "Boa noite"; 
		}

		relogio = new JLabel(formatar(hh)+":"+formatar(mm)+":"+formatar(ss)); 

		Font f = new Font("Arial", Font.PLAIN, 40);
		relogio.setFont(f);
		relogio.setHorizontalAlignment(SwingConstants.CENTER);

		labelLogin = new JLabel(saudacoes);

		labelLogin.setFont(new Font("Arial", Font.PLAIN, 15));
		labelLogin.setHorizontalAlignment(SwingConstants.CENTER);

		barraRolagem = new JScrollPane(table);

	}

	//metodo responsavel por montar o painel 
	private JComponent montaPainel() {
		FormLayout layout = new FormLayout(
				"5dlu:grow, 20dlu:grow, right:p:grow, 5dlu, p, 5dlu, p, 5dlu, p, 5dlu, p, 5dlu, p, 20dlu:grow, 5dlu:grow",
				"5dlu:grow, p, 5dlu, p, 5dlu, p, 5dlu, p, p, 5dlu, p, 5dlu:grow");

		layout.setColumnGroup(new int[] {3, 5, 9, 11, 13});

		DefaultFormBuilder builder = new DefaultFormBuilder(layout);

		CellConstraints cc = new CellConstraints();

		builder.add(barraRolagem, cc.xywh(2, 11, 13, 1));

		builder.add(labelLogo, cc.xyw(6, 2, 5));
		builder.add(labelLogin, cc.xyw(6, 4, 5));
		builder.add(relogio, cc.xyw(6, 6, 5));

		builder.add(labelPesquisa, cc.xy(3, 9));
		builder.add(textPesquisa, cc.xy(5, 9));

		builder.add(radioAtivo, cc.xy(7, 8));
		builder.add(radioQq, cc.xy(7, 9));

		builder.add(comboEmpresas, cc.xy(9, 9));

		builder.add(buttonAtualiza, cc.xy(11, 9));

		builder.add(buttonAddPedido, cc.xy(13, 9));

		return builder.getPanel();
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
		tableModel.addColumn("Código");
		tableModel.addColumn("Empresa");
		tableModel.addColumn("Data entrega");
		tableModel.addColumn("Status");
		tableModel.addColumn("Produtos (prontos/total)");
		tableModel.addColumn("Porcentagem conclusão");

		table.setDefaultRenderer(Object.class, new TableRenderer());
		TableColumn tableCol = table.getColumnModel().getColumn(5);
		tableCol.setCellRenderer(new renderProgress());

		//setando o numero de linhas da JTable inicialmente como 0
		tableModel.setNumRows(0);
		
		table.setRowHeight(25);
	}

	public class renderProgress extends JProgressBar implements TableCellRenderer{

		public renderProgress(){
			super(0,100);
			setValue(0);
			setString("0%");
			setStringPainted(true);

		}
		@Override
		public boolean isDisplayable(){
			return true;
		}

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		
			//value is a percentage e.g. 95%
			if(value == null)
				value = "";
			final String sValue = value.toString();
			int index = sValue.indexOf('%');
			if (index != -1) {
				int p = 0;
				try{
					p = Integer.parseInt(sValue.substring(0, index));
				}
				catch(NumberFormatException e){
				}
				setValue(p);
				setString(sValue);
			}
			return this;
		}

	}

	public class TableRenderer extends DefaultTableCellRenderer {

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

			String status = table.getModel().getValueAt(row, 3).toString();

			if(status.equals("ATRASADO")) {
				comp.setForeground(Color.WHITE);
				comp.setBackground(new Color(255, 10, 10));
			}
			else if(status.equals("EM ANDAMENTO")){
				comp.setBackground(new Color(91, 234, 153));
				comp.setForeground(Color.BLACK);
			}else if(status.equals("ENTREGA HOJE")){
				comp.setBackground(new Color(135, 206, 255));
				comp.setForeground(Color.BLACK);
			}else {
				comp.setBackground(Color.WHITE);
				comp.setForeground(Color.BLACK);
			}

			comp.setFont(new Font("Arial", Font.BOLD, 15));

			return comp;
		}
	}

	public void atualizaTabela() {		
		tableModel.setNumRows(0);

		String codigo = textPesquisa.getText();

		if(codigo == null)
			codigo = "";

		String empresaSelecionada = comboEmpresas.getSelectedItem().toString();

		if(empresaSelecionada == "Qualquer empresa")
			empresaSelecionada = "";

		String status = "true";
		String limite = "";

		if(radioQq.isSelected()) {
			status = "";
			limite = "LIMIT 50";
		}

		itensTabela = controle.getPedidos(codigo, empresaSelecionada, status, limite);

		for(PedidoVO p : itensTabela) {
			//as linhas da tabela devem ser do tipo Object
			tableModel.addRow(new Object[] {
					p.getId(), p.getEmpresa(), transformaDateString(p.getDataEntrega()), p.getStatus(), p.getProdutosProntos(), p.getPorcentConclusao()
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

	private void fechar() {
		System.exit(0);
	}
}

package visao;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
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
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;

import com.jgoodies.forms.builder.ButtonBarBuilder;
import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.toedter.components.JSpinField;

import controle.LumanferControle;
import modelo.vo.LocalizacaoVO;
import modelo.vo.ProdutoVO;

public class AtualizaProdutoVisao extends JFrame{
	private LumanferControle controle;
	
	private GerenciarProdutosVisao gerenciarProdutosVisao;

	private JComboBox comboLocais;

	private ArrayList<LocalizacaoVO> locaisArray;

	private JTextField textRevisao;
	private JTextField textDescricao;
	private JTextField textValor;
	private JTextField textCodigo;
	private JTextField textCodAlternativo;
	
	private JTextArea textObs;
	
	private JScrollPane sp;

	private JSpinField spinQtd;

	private JButton buttonAddLocal;
	private JButton buttonAlterar;
	private JButton buttonVoltar;
	
	private ImageIcon iconAlt;
	private ImageIcon iconVoltar;

	private ProdutoVO produtoVO;

	private String codigo;

	//construtor
	public AtualizaProdutoVisao(LumanferControle controle, String codigo, GerenciarProdutosVisao gerenciarProdutosVisao) {
		super("Atualizar produto");

		this.controle = controle;
		this.codigo = codigo;
		this.gerenciarProdutosVisao = gerenciarProdutosVisao;

		try { 

			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");	
		} 
		catch (Exception e) 
		{ }

		inicializaComponentes();

		this.getContentPane().add(this.montaPainel());

		buttonAddLocal.addActionListener(
				new ActionListener(){ // classe interna anonima
					public void actionPerformed( ActionEvent event ) {
						adicionarLocal();
					}
				}
				);

		buttonVoltar.addActionListener(
				new ActionListener(){ // classe interna anonima
					public void actionPerformed( ActionEvent event ) {
						fechar();
					}
				}
				);

		buttonAlterar.addActionListener(
				new ActionListener(){ // classe interna anonima
					public void actionPerformed( ActionEvent event ) {
						alterar();
					}
				}
				);
	}

	//metodo responsavel por inicializar os componentes
	private void inicializaComponentes() {
		textRevisao = new JTextField();
		textDescricao = new JTextField();
		textValor = new JTextField();
		textCodigo = new JTextField();
		textCodAlternativo = new JTextField();
		
		textObs = new JTextArea();
		
		sp = new JScrollPane(textObs);

		buttonAddLocal = new JButton("+");
		buttonAlterar = new JButton("");
		buttonVoltar = new JButton("");
		
		iconAlt = new ImageIcon("./icones/salvar.png");
		iconVoltar = new ImageIcon("./icones/de-volta.png");
		
		buttonAlterar.setIcon(iconAlt);
		buttonVoltar.setIcon(iconVoltar);

		spinQtd = new JSpinField();

		//comboBox
		locaisArray = controle.getLocalizacoes("", "");

		comboLocais = new JComboBox();

		for(LocalizacaoVO l : locaisArray) 
			comboLocais.addItem(l.getDescricao());		

		comboLocais.setMaximumRowCount(10);

		desativaElementos();

		if(codigo != null) {
			textCodigo.setText(codigo);
			pesquisar();
		}
	}

	//metodo responsavel por montar o painel 
	private JComponent montaPainel() {
		FormLayout layout = new FormLayout(
				"10dlu:grow, right:p, 5dlu, 90dlu, 5dlu, p, right:90dlu, 5dlu, 75dlu, 5dlu, p, 10dlu:grow",
				"10dlu:grow, p, 5dlu, p, 5dlu, p, 5dlu, p, 5dlu, p, 5dlu, p, 5dlu, p, 5dlu, 50dlu, 15dlu, p, 10dlu:grow");

		DefaultFormBuilder builder = new DefaultFormBuilder(layout);

		CellConstraints cc = new CellConstraints();

		builder.addLabel("Código:", cc.xy(2, 2));
		builder.add(textCodigo, cc.xy(4, 2));

		builder.addLabel("Cód: Alternativo:", cc.xy(7, 2));
		builder.add(textCodAlternativo, cc.xy(9, 2));

		builder.addLabel("Descrição:", cc.xy(2, 4));
		builder.add(textDescricao, cc.xyw(4, 4, 6));

		builder.addLabel("Revisão:", cc.xy(7, 8));
		builder.add(textRevisao, cc.xy(9, 8));

		builder.addLabel("Valor:", cc.xy(2, 8));
		builder.add(textValor, cc.xy(4, 8));

		builder.addLabel("Localização:", cc.xy(7, 6));
		builder.add(comboLocais, cc.xy(9, 6));
		builder.add(buttonAddLocal, cc.xy(11, 6));

		builder.addLabel("Quantidade:", cc.xy(2, 6));
		builder.add(spinQtd, cc.xy(4, 6));

		builder.addSeparator("Observações", cc.xyw(2, 10, 8));

		builder.add(sp, cc.xywh(2, 12, 8, 5));

		builder.add(montaBarraBotao(), cc.xyw(2, 18, 7));

		return builder.getPanel();
	}

	private void adicionarLocal() {
		if(!textCodigo.isEditable()) {
			String descricao = JOptionPane.showInputDialog("Entre com o novo local:");

			if(descricao != null) {
				if(descricao.trim().length() != 0) {
					descricao = descricao.toUpperCase();

					if(!controle.existeLocal(descricao)) {
						if(controle.addLocal(descricao)) {
							locaisArray = controle.getLocalizacoes("", "");

							comboLocais.removeAllItems();

							for(LocalizacaoVO l : locaisArray)
								comboLocais.addItem(l.getDescricao());

							comboLocais.setSelectedItem(descricao);
						}
					}
				}
			}
		}
	}

	//metodo responsavel por fechar a tela
	private void fechar() {
		AtualizaProdutoVisao.this.dispose();

		gerenciarProdutosVisao.setVisible(true);
		gerenciarProdutosVisao.atualizaTabela();
	}	

	private void desativaElementos() {
		textCodigo.setEditable(true);
	}

	private void ativaElementos() {
		textCodigo.setEditable(false);
	}

	private void pesquisar() {
		produtoVO = controle.getProduto(textCodigo.getText());

		if(produtoVO != null) {
			textDescricao.setText(produtoVO.getDescricao());
			textRevisao.setText(produtoVO.getRevisao());
			textValor.setText(produtoVO.getValor());
			comboLocais.setSelectedItem(produtoVO.getLocalizacao());
			spinQtd.setValue(produtoVO.getQtd());
			textCodAlternativo.setText(produtoVO.getCodAlternativo());
			textObs.setText(produtoVO.getObservacao());

			ativaElementos();
		}else {
			JOptionPane.showMessageDialog(AtualizaProdutoVisao.this, String.format("Erro ao pesquisar produto"), "", JOptionPane.ERROR_MESSAGE);
			fechar();
		}
	}

	private void alterar() {
		if(!textCodigo.isEditable()) {
			produtoVO = new ProdutoVO();

			produtoVO.setCodigo(textCodigo.getText());
			produtoVO.setId(controle.getIdProduto(textCodigo.getText()));
			produtoVO.setDescricao(textDescricao.getText());
			produtoVO.setQtd(spinQtd.getValue());
			produtoVO.setObservacao(textObs.getText());
			produtoVO.setCodAlternativo(textCodAlternativo.getText());

			int itemSelecionado = comboLocais.getSelectedIndex();

			if(itemSelecionado == -1) {
				itemSelecionado = 0;
			}

			produtoVO.setLocalizacao("" + locaisArray.get(itemSelecionado).getId());
			produtoVO.setRevisao(textRevisao.getText());

			DateFormat dateFormat;
			dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date data = new Date();

			produtoVO.setUltima_modificacao(dateFormat.format(data));

			if(ehNumero(textValor.getText())) {
				String valor = textValor.getText();

				valor = valor.replace(",", ".");
				valor = valor.trim();

				produtoVO.setValor(valor);

				if(controle.alteraProduto(produtoVO)) {
					JOptionPane.showMessageDialog(AtualizaProdutoVisao.this, String.format("Produto Alterado com Sucesso"), "", JOptionPane.INFORMATION_MESSAGE);

				}else {
					JOptionPane.showMessageDialog(AtualizaProdutoVisao.this, String.format("Erro ao alterar produto"), "", JOptionPane.ERROR_MESSAGE);
				}
				
				fechar();
				
			}else {
				textValor.selectAll();
				textValor.requestFocus();
			}
		}
	}

	private boolean ehNumero(String numero) {
		boolean ehNumero = true;

		numero = numero.trim();
		numero = numero.replace(",", "");
		numero = numero.replace(".", "");

		try {
			float n = Float.parseFloat(numero);
		}catch(NumberFormatException e){
			ehNumero = false;
		}

		return ehNumero;
	}

	//metodo responsavel por montar a barra de botoes que sera adicionada ao final da janela
	private Component montaBarraBotao() {
		return ButtonBarBuilder.create().addButton(buttonAlterar, buttonVoltar).build();	
	}
}

package visao;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
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

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import com.jgoodies.forms.builder.ButtonBarBuilder;
import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.toedter.components.JSpinField;

import controle.LumanferControle;
import modelo.vo.LocalizacaoVO;
import modelo.vo.ProdutoVO;

public class AdicionarProdutoVisao extends JFrame{
	private LumanferControle controle;
	
	private GerenciarProdutosVisao gerenciarProdutosVisao;

	private JTextField textCodigo;
	private JTextField textRevisao;
	private JTextField textDescricao;
	private JTextField textValor;
	private JTextField textCodAlternativo;

	private JComboBox comboLocalizacao;

	private JSpinField spinQuantidade;
	
	private JTextArea textObs;
	
	private JScrollPane sp;

	private JButton buttonLimpar;
	private JButton buttonAdicionar;
	private JButton buttonAddLocal;
	private JButton buttonVoltar;

	private ArrayList<LocalizacaoVO> localizacoesArray;

	private ImageIcon iconAdd;
	private ImageIcon iconLimpar;
	private ImageIcon iconVoltar;

	private ProdutoVO produtoVO;

	//construtor
	public AdicionarProdutoVisao(LumanferControle controle, GerenciarProdutosVisao gerenciarProdutosVisao) {
		super("Adicionar Produto");

		this.controle = controle;
		this.gerenciarProdutosVisao = gerenciarProdutosVisao;

		try { 
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");	// Java Swing Nimbus

		} 
		catch (Exception e) 
		{ }

		//chama o metodo rsponsavel por inicializar os componentes
		inicializaComponentes();

		//monta todo o layout e em seguida o adiciona
		this.getContentPane().add(this.montaPainel());

		AutoCompleteDecorator.decorate(comboLocalizacao);

		this.getContentPane().add(this.montaPainel());

		buttonLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpar();
			}
		});

		buttonAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionaProduto();
			}
		});

		buttonVoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fechar();
			}
		});

		buttonAddLocal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionarLocal();
			}
		});

		textCodigo.addFocusListener(new FocusListener() {

			public void focusLost(FocusEvent e) {
				verificaProduto();
			}

			public void focusGained(FocusEvent e) {

			}
		});
	}

	//metodo responsavel por inicializar os componentes
	private void inicializaComponentes() {

		textCodigo = new JTextField();
		textRevisao = new JTextField();
		textDescricao = new JTextField();
		textValor = new JTextField();
		textCodAlternativo = new JTextField();
		
		textObs = new JTextArea();
		
		sp = new JScrollPane(textObs);
		
		spinQuantidade = new JSpinField();
		spinQuantidade.setMinimum(0);

		buttonLimpar = new JButton("");
		buttonAdicionar = new JButton("");
		buttonAddLocal = new JButton("+");
		buttonVoltar = new JButton("");

		iconAdd = new ImageIcon("./icones/salvar.png");
		iconLimpar = new ImageIcon("./icones/clean.png");
		iconVoltar = new ImageIcon("./icones/de-volta.png");

		buttonAdicionar.setIcon(iconAdd);
		buttonLimpar.setIcon(iconLimpar);
		buttonVoltar.setIcon(iconVoltar);

		comboLocalizacao = new JComboBox();

		localizacoesArray = controle.getLocalizacoes("", "");

		for(LocalizacaoVO l : localizacoesArray)
			comboLocalizacao.addItem(l.getDescricao());

		comboLocalizacao.setMaximumRowCount(10);
		comboLocalizacao.setSelectedItem("Sem local");

		desativarElementos();
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

		builder.addLabel("Cód. Alternativo:", cc.xy(7, 2));
		builder.add(textCodAlternativo, cc.xy(9, 2));

		builder.addLabel("Descrição:", cc.xy(2, 4));
		builder.add(textDescricao, cc.xyw(4, 4, 6));

		builder.addLabel("Revisão:", cc.xy(7, 8));
		builder.add(textRevisao, cc.xy(9, 8));

		builder.addLabel("Valor:", cc.xy(2, 8));
		builder.add(textValor, cc.xy(4, 8));

		builder.addLabel("Localização:", cc.xy(7, 6));
		builder.add(comboLocalizacao, cc.xy(9, 6));
		builder.add(buttonAddLocal, cc.xy(11, 6));

		builder.addLabel("Quantidade:", cc.xy(2, 6));
		builder.add(spinQuantidade, cc.xy(4, 6));

		builder.addSeparator("Observações", cc.xyw(2, 10, 8));

		builder.add(sp, cc.xywh(2, 12, 8, 5));

		builder.add(montaBarraBotao(), cc.xyw(2, 18, 7));

		return builder.getPanel();
	}

	//metodo responsavel por fechar a tela
	private void fechar() {
		AdicionarProdutoVisao.this.dispose();
		
		gerenciarProdutosVisao.setVisible(true);
		gerenciarProdutosVisao.atualizaTabela();
	}

	private void adicionarLocal() {
		if(!textCodigo.isEnabled()) {
			String descricao = JOptionPane.showInputDialog("Entre com o novo local:");

			if(descricao != null) {
				if(descricao.trim().length() != 0) {
					descricao = descricao.toUpperCase();

					if(!controle.existeLocal(descricao)) {
						if(controle.addLocal(descricao)) {
							localizacoesArray = controle.getLocalizacoes("", "");

							comboLocalizacao.removeAllItems();

							for(LocalizacaoVO l : localizacoesArray)
								comboLocalizacao.addItem(l.getDescricao());

							comboLocalizacao.setSelectedItem(descricao);
						}
					}
				}
			}
		}
	}

	private void adicionaProduto() {
		if(!textCodigo.isEnabled()) {
			produtoVO = new ProdutoVO();
			produtoVO.setCodigo(textCodigo.getText());
			produtoVO.setDescricao(textDescricao.getText());
			produtoVO.setObservacao(textObs.getText());
			produtoVO.setCodAlternativo(textCodAlternativo.getText());

			int itemSelecionado = comboLocalizacao.getSelectedIndex();

			if(itemSelecionado == -1) {
				itemSelecionado = 0;
			}

			produtoVO.setLocalizacao("" + localizacoesArray.get(itemSelecionado).getId());

			produtoVO.setQtd(spinQuantidade.getValue());
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

				if(controle.adicionaProduto(produtoVO)) {
					JOptionPane.showMessageDialog(AdicionarProdutoVisao.this, String.format("Produto cadastrado com sucesso"), "", JOptionPane.INFORMATION_MESSAGE);
				}else
					JOptionPane.showMessageDialog(AdicionarProdutoVisao.this, String.format("Erro ao cadastrar produto"), "", JOptionPane.ERROR_MESSAGE);

				fechar();
			}else {
				JOptionPane.showMessageDialog(AdicionarProdutoVisao.this, String.format("Entre com um valor válido"), "", JOptionPane.ERROR_MESSAGE);
				textValor.requestFocus();
				textValor.selectAll();
			}

		}
	}

	private void verificaProduto() {
		if(textCodigo.getText().trim().length() != 0) {
			if(!controle.existeProduto(textCodigo.getText())) {
				ativarElementos();

			}else {
				textCodigo.requestFocus();
				textCodigo.selectAll();
			}
		}else {
			textCodigo.requestFocus();
			textCodigo.selectAll();
		}
	}

	private void ativarElementos() {
		textCodigo.setEnabled(false);
	}

	private void desativarElementos() {
		textCodigo.setEnabled(true);
	}

	//metodo responsavel por limpar todos os campos
	private void limpar() {
		textCodigo.setText("");
		textDescricao.setText("");
		textRevisao.setText("");
		textValor.setText("");
		textObs.setText("");
		spinQuantidade.setValue(0);
		textCodAlternativo.setText("");

		desativarElementos();

		textCodigo.requestFocus();
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
		return ButtonBarBuilder.create().addButton(buttonAdicionar, buttonLimpar, buttonVoltar).build();	
	}
}

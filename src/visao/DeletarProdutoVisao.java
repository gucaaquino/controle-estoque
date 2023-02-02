package visao;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;

import com.jgoodies.forms.builder.ButtonBarBuilder;
import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.toedter.components.JSpinField;

import controle.LumanferControle;
import modelo.vo.ProdutoVO;

public class DeletarProdutoVisao extends JFrame{
	private LumanferControle controle;
	
	private GerenciarProdutosVisao gerenciarProdutosVisao;

	private JTextField textRevisao;
	private JTextField textValor;
	private JTextField textDescricao;
	private JTextField textLocal;
	private JTextField textCodigo;
	private JTextField textCodAlternativo;
	
	private JTextArea textObs;

	private JSpinField spinQtd;

	private JButton buttonDeletar;
	private JButton buttonVoltar;

	private ImageIcon iconDel;
	private ImageIcon iconVoltar;

	private ProdutoVO produtoVO;

	private String codigo;

	//construtor
	public DeletarProdutoVisao(LumanferControle controle, String codigo, GerenciarProdutosVisao gerenciarProdutosVisao) {
		super("Deletar produto");

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

		buttonVoltar.addActionListener(
				new ActionListener(){ // classe interna anonima
					public void actionPerformed( ActionEvent event ) {
						fechar();
					}
				}
				);

		buttonDeletar.addActionListener(
				new ActionListener(){ // classe interna anonima
					public void actionPerformed( ActionEvent event ) {
						deletar();
					}
				}
				);
	}

	//metodo responsavel por inicializar os componentes
	private void inicializaComponentes() {
		textRevisao = new JTextField();
		textValor = new JTextField();
		textDescricao = new JTextField();
		textLocal = new JTextField();
		textCodigo = new JTextField();
		textCodAlternativo = new JTextField();
		
		textObs = new JTextArea();

		buttonDeletar = new JButton("");
		buttonVoltar = new JButton("");
		
		iconDel = new ImageIcon("./icones/excluir.png");
		iconVoltar = new ImageIcon("./icones/de-volta.png");

		buttonDeletar.setIcon(iconDel);
		buttonVoltar.setIcon(iconVoltar);

		spinQtd = new JSpinField();
		spinQtd.setMinimum(0);

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

		builder.addLabel("Cód. Alternativo:", cc.xy(7, 2));
		builder.add(textCodAlternativo, cc.xy(9, 2));

		builder.addLabel("Descrição:", cc.xy(2, 4));
		builder.add(textDescricao, cc.xyw(4, 4, 6));

		builder.addLabel("Revisão:", cc.xy(7, 8));
		builder.add(textRevisao, cc.xy(9, 8));

		builder.addLabel("Valor:", cc.xy(2, 8));
		builder.add(textValor, cc.xy(4, 8));

		builder.addLabel("Localização:", cc.xy(7, 6));
		builder.add(textLocal, cc.xy(9, 6));

		builder.addLabel("Quantidade:", cc.xy(2, 6));
		builder.add(spinQtd, cc.xy(4, 6));

		builder.addSeparator("Observações", cc.xyw(2, 10, 8));

		builder.add(textObs, cc.xywh(2, 12, 8, 5));

		builder.add(montaBarraBotao(), cc.xyw(2, 18, 7));

		return builder.getPanel();
	}

	//metodo responsavel por fechar a tela
	private void fechar() {
		DeletarProdutoVisao.this.dispose();

		gerenciarProdutosVisao.setVisible(true);
		gerenciarProdutosVisao.atualizaTabela();
	}

	private void deletar() {
		if(!textCodigo.isEditable()) {
			int opcao = JOptionPane.showConfirmDialog(DeletarProdutoVisao.this, "Tem certeza que deseja excluir esse produto?", "CUIDADO", JOptionPane.OK_CANCEL_OPTION);

			if(opcao == JOptionPane.OK_OPTION) {
				String codigo = textCodigo.getText();

				if(controle.excluiProduto(codigo)) {
					JOptionPane.showMessageDialog(DeletarProdutoVisao.this, String.format("Produto deletado com sucesso"), "", JOptionPane.INFORMATION_MESSAGE);

				}else
					JOptionPane.showMessageDialog(DeletarProdutoVisao.this, String.format("Erro ao excluir produto"), "", JOptionPane.ERROR_MESSAGE);

				fechar();

			}
		}
	}

	private void pesquisar() {
		if(textCodigo.isEditable()) {
			produtoVO = controle.getProduto(textCodigo.getText());

			if(produtoVO != null) {
				textDescricao.setText(produtoVO.getDescricao());
				textRevisao.setText(produtoVO.getRevisao());
				textValor.setText("" + produtoVO.getValor());
				textCodAlternativo.setText(produtoVO.getCodAlternativo());
				textLocal.setText(produtoVO.getLocalizacao());
				spinQtd.setValue(produtoVO.getQtd());
				textObs.setText(produtoVO.getObservacao());

				textCodigo.setEditable(false);
				textDescricao.setEditable(false);
				textRevisao.setEditable(false);
				textValor.setEditable(false);
				textLocal.setEditable(false);
				textObs.setEditable(false);
				textCodAlternativo.setEditable(false);
			}else {
				JOptionPane.showMessageDialog(DeletarProdutoVisao.this, String.format("Erro ao pesquisar produto"), "", JOptionPane.ERROR_MESSAGE);
				fechar();
			}
		}
	}

	//metodo responsavel por montar a barra de botoes que sera adicionada ao final da janela
	private Component montaBarraBotao() {
		return ButtonBarBuilder.create().addButton(buttonDeletar, buttonVoltar).build();	
	}
}

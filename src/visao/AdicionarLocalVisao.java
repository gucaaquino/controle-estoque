package visao;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.jgoodies.forms.builder.ButtonBarBuilder;
import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import controle.LumanferControle;

public class AdicionarLocalVisao extends JFrame{
	private GerenciarLocaisVisao gerenciarLocaisVisao;
	private LumanferControle controle;
	
	private JTextField textNome;

	private JButton buttonAdicionar;
	private JButton buttonVoltar;
	
	private ImageIcon iconAdd;
	private ImageIcon iconVoltar;

	public AdicionarLocalVisao(LumanferControle controle, GerenciarLocaisVisao gerenciarLocaisVisao) {
		super("Adicionar Local");

		this.controle = controle;
		this.gerenciarLocaisVisao = gerenciarLocaisVisao;

		inicializaComponentes();

		this.getContentPane().add(this.montaPainel());

		buttonVoltar.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				fechar();
			}
		});

		buttonAdicionar.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				adicionaLocal();
			}
		});
	}

	//metodo responsavel por montar o painel 
	private JComponent montaPainel() {
		FormLayout layout = new FormLayout(
				"10dlu:grow, right:p, 5dlu, p, 10dlu:grow",
				"10dlu:grow, p, 15dlu, p, 10dlu:grow");

		DefaultFormBuilder builder = new DefaultFormBuilder(layout);

		CellConstraints cc = new CellConstraints();;

		builder.addLabel("Nome:", cc.xy(2, 2));
		builder.add(textNome, cc.xy(4, 2));

		builder.add(montaBarraBotao(), cc.xyw(2, 4, 4));

		return builder.getPanel();
	}

	private void fechar() {
		AdicionarLocalVisao.this.dispose();

		gerenciarLocaisVisao.setVisible(true);
		gerenciarLocaisVisao.atualizaTabela();
	}

	private void adicionaLocal() {
		String local = textNome.getText().toUpperCase();
		
		if(!local.equals("")) {				
				if(controle.addLocal(local)) {
					JOptionPane.showMessageDialog(AdicionarLocalVisao.this, String.format("Local cadastrado com sucesso"), "", JOptionPane.INFORMATION_MESSAGE);
				}else
					JOptionPane.showMessageDialog(AdicionarLocalVisao.this, String.format("Erro ao cadastrar local"), "", JOptionPane.ERROR_MESSAGE);
				
				fechar();
		}
	}
	
	private void inicializaComponentes() {
		textNome = new JTextField(15);

		buttonAdicionar = new JButton("");
		buttonVoltar = new JButton("");
		
		iconAdd = new ImageIcon("./icones/salvar.png");
		iconVoltar = new ImageIcon("./icones/de-volta.png");

		buttonAdicionar.setIcon(iconAdd);
		buttonVoltar.setIcon(iconVoltar);
	}

	//metodo responsavel por montar a barra de botoes que sera adicionada ao final da janela
	private Component montaBarraBotao() {
		return ButtonBarBuilder.create().addButton(buttonAdicionar, buttonVoltar).build();	
	}
}

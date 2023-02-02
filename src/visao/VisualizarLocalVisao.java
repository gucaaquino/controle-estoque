package visao;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JTextField;

import com.jgoodies.forms.builder.ButtonBarBuilder;
import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import controle.LumanferControle;

public class VisualizarLocalVisao extends JFrame{
	private GerenciarLocaisVisao gerenciarLocaisVisao;
	private LumanferControle controle;

	private JTextField textNome;

	private JButton buttonVoltar;

	private int id;
	private String desc;
	
	private ImageIcon iconVoltar;

	public VisualizarLocalVisao(LumanferControle controle, GerenciarLocaisVisao gerenciarLocaisVisao, int id, String desc) {
		super("Visualizar Local");

		this.controle = controle;
		this.gerenciarLocaisVisao = gerenciarLocaisVisao;
		this.id = id;
		this.desc = desc;

		inicializaComponentes();

		this.getContentPane().add(this.montaPainel());

		buttonVoltar.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				fechar();
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

		builder.add(montaBarraBotao(), cc.xyw(2, 4, 3));

		return builder.getPanel();
	}

	private void fechar() {
		VisualizarLocalVisao.this.dispose();

		gerenciarLocaisVisao.setVisible(true);
		gerenciarLocaisVisao.atualizaTabela();
	}

	private void inicializaComponentes() {
		textNome = new JTextField(15);

		textNome.setEditable(false);

		textNome.setText(desc);

		buttonVoltar = new JButton("");
		
		iconVoltar = new ImageIcon("./icones/de-volta.png");

		buttonVoltar.setIcon(iconVoltar);
	}

	//metodo responsavel por montar a barra de botoes que sera adicionada ao final da janela
	private Component montaBarraBotao() {
		return ButtonBarBuilder.create().addButton(buttonVoltar).build();	
	}
}

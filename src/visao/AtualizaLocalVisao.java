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
import modelo.vo.LocalizacaoVO;

public class AtualizaLocalVisao extends JFrame{
	private GerenciarLocaisVisao gerenciarLocaisVisao;
	private LumanferControle controle;

	private JTextField textNome;

	private JButton buttonAtualizar;
	private JButton buttonVoltar;
	
	private int id;
	private String desc;
	
	private ImageIcon iconAlt;
	private ImageIcon iconVoltar;

	public AtualizaLocalVisao(LumanferControle controle, GerenciarLocaisVisao gerenciarLocaisVisao, int id, String desc) {
		super("Atualizar Local");

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

		buttonAtualizar.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				atualizaLocal();
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
		AtualizaLocalVisao.this.dispose();

		gerenciarLocaisVisao.setVisible(true);
		gerenciarLocaisVisao.atualizaTabela();
	}

	private void atualizaLocal() {
		String local = textNome.getText().toUpperCase();

		if(!local.equals("")) {				
			LocalizacaoVO l = new LocalizacaoVO();
			l.setDescricao(local);
			l.setId(id);

			if(controle.atualizaLocal(l)) {
				JOptionPane.showMessageDialog(AtualizaLocalVisao.this, String.format("Local alterado com sucesso"), "", JOptionPane.INFORMATION_MESSAGE);
			}else
				JOptionPane.showMessageDialog(AtualizaLocalVisao.this, String.format("Erro ao alterar local"), "", JOptionPane.ERROR_MESSAGE);
			
			fechar();
		}
	}

	private void inicializaComponentes() {
		textNome = new JTextField(15);

		textNome.setText(desc);
		
		buttonAtualizar = new JButton("");
		buttonVoltar = new JButton("");
		
		iconAlt = new ImageIcon("./icones/salvar.png");
		iconVoltar = new ImageIcon("./icones/de-volta.png");

		buttonAtualizar.setIcon(iconAlt);
		buttonVoltar.setIcon(iconVoltar);
	}

	//metodo responsavel por montar a barra de botoes que sera adicionada ao final da janela
	private Component montaBarraBotao() {
		return ButtonBarBuilder.create().addButton(buttonAtualizar, buttonVoltar).build();	
	}
}

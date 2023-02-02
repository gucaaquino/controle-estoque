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
import modelo.vo.EmpresaVO;

public class VisualizarEmpresaVisao extends JFrame{
	private GerenciarEmpresasVisao gerenciarEmpresasVisao;
	private LumanferControle controle;

	private JTextField textCodigo;
	private JTextField textNome;
	private JTextField textCnpj;
	private JTextField textTel;

	private JButton buttonVoltar;

	private ImageIcon iconVoltar;

	private String codigo;

	public VisualizarEmpresaVisao(LumanferControle controle, GerenciarEmpresasVisao gerenciarEmpresasVisao, String codigo) {
		super("Visualizar Empresa");

		this.controle = controle;
		this.gerenciarEmpresasVisao = gerenciarEmpresasVisao;
		this.codigo = codigo;

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
				"10dlu:grow, p, 5dlu, p, 5dlu, p, 5dlu, p, 15dlu, p, 10dlu:grow");

		DefaultFormBuilder builder = new DefaultFormBuilder(layout);

		CellConstraints cc = new CellConstraints();

		builder.addLabel("Código:", cc.xy(2, 2));
		builder.add(textCodigo, cc.xy(4, 2));

		builder.addLabel("Nome:", cc.xy(2, 4));
		builder.add(textNome, cc.xy(4, 4));

		builder.addLabel("CNPJ:", cc.xy(2, 6));
		builder.add(textCnpj, cc.xy(4, 6));

		builder.addLabel("Telefone:", cc.xy(2, 8));
		builder.add(textTel, cc.xy(4, 8));

		builder.add(montaBarraBotao(), cc.xyw(2, 10, 3));

		return builder.getPanel();
	}

	private void fechar() {
		VisualizarEmpresaVisao.this.dispose();

		gerenciarEmpresasVisao.setVisible(true);
		gerenciarEmpresasVisao.atualizaTabela();
	}

	private void pesquisaEmpresa() {
		EmpresaVO empresa = controle.getEmpresa(codigo);

		textCodigo.setText(empresa.getId() + "");
		textCnpj.setText(empresa.getCnpj());
		textNome.setText(empresa.getDescricao());
		textTel.setText(empresa.getTelefone());
	}

	private void inicializaComponentes() {
		textCodigo = new JTextField(15);
		textNome = new JTextField(15);
		textCnpj = new JTextField(15);
		textTel = new JTextField(15);

		buttonVoltar = new JButton("");
		
		iconVoltar = new ImageIcon("./icones/de-volta.png");

		buttonVoltar.setIcon(iconVoltar);

		textCodigo.setEditable(false);
		textNome.setEditable(false);
		textCnpj.setEditable(false);
		textTel.setEditable(false);

		pesquisaEmpresa();
	}

	//metodo responsavel por montar a barra de botoes que sera adicionada ao final da janela
	private Component montaBarraBotao() {
		return ButtonBarBuilder.create().addButton(buttonVoltar).build();	
	}
}

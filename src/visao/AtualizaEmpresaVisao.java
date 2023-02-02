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
import modelo.vo.EmpresaVO;

public class AtualizaEmpresaVisao extends JFrame{
	private GerenciarEmpresasVisao gerenciarEmpresasVisao;
	private LumanferControle controle;

	private JTextField textCodigo;
	private JTextField textNome;
	private JTextField textCnpj;
	private JTextField textTel;

	private JButton buttonAlterar;
	private JButton buttonVoltar;
	
	private ImageIcon iconAlt;
	private ImageIcon iconVoltar;

	private String codigo;

	public AtualizaEmpresaVisao(LumanferControle controle, GerenciarEmpresasVisao gerenciarEmpresasVisao, String codigo) {
		super("Atualizar Empresa");

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

		buttonAlterar.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				atualizaEmpresa();
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

		builder.add(montaBarraBotao(), cc.xyw(2, 10, 4));

		return builder.getPanel();
	}

	private void fechar() {
		AtualizaEmpresaVisao.this.dispose();

		gerenciarEmpresasVisao.setVisible(true);
		gerenciarEmpresasVisao.atualizaTabela();
	}

	private void atualizaEmpresa() {
		String empresa = textNome.getText().toUpperCase();

		if(!empresa.equals("")) {
			EmpresaVO e = new EmpresaVO();
			e.setCnpj(textCnpj.getText());
			e.setDescricao(empresa);
			e.setTelefone(textTel.getText());
			e.setId(Integer.parseInt(textCodigo.getText()));

			if(controle.atualizaEmpresa(e)) {
				JOptionPane.showMessageDialog(AtualizaEmpresaVisao.this, String.format("Empresa alterada com sucesso"), "", JOptionPane.INFORMATION_MESSAGE);
			}else
				JOptionPane.showMessageDialog(AtualizaEmpresaVisao.this, String.format("Erro ao alterar empresa"), "", JOptionPane.ERROR_MESSAGE);

		}
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

		buttonAlterar = new JButton("");
		buttonVoltar = new JButton("");
		
		iconAlt = new ImageIcon("./icones/salvar.png");
		iconVoltar = new ImageIcon("./icones/de-volta.png");

		buttonAlterar.setIcon(iconAlt);
		buttonVoltar.setIcon(iconVoltar);

		textCodigo.setEnabled(false);

		pesquisaEmpresa();
	}

	//metodo responsavel por montar a barra de botoes que sera adicionada ao final da janela
	private Component montaBarraBotao() {
		return ButtonBarBuilder.create().addButton(buttonAlterar, buttonVoltar).build();	
	}
}

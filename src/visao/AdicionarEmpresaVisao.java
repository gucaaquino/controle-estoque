package visao;

import java.awt.Color;
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

public class AdicionarEmpresaVisao extends JFrame{
	private GerenciarEmpresasVisao gerenciarEmpresasVisao;
	private LumanferControle controle;
	
	private JTextField textNome;
	private JTextField textCnpj;
	private JTextField textTel;

	private JButton buttonAdicionar;
	private JButton buttonVoltar;
	
	private ImageIcon iconAdd;
	private ImageIcon iconVoltar;

	public AdicionarEmpresaVisao(LumanferControle controle, GerenciarEmpresasVisao gerenciarEmpresasVisao) {
		super("Adicionar Empresa");

		this.controle = controle;
		this.gerenciarEmpresasVisao = gerenciarEmpresasVisao;

		inicializaComponentes();

		this.getContentPane().add(this.montaPainel());

		buttonVoltar.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				fechar();
			}
		});

		buttonAdicionar.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				adicionaEmpresa();
			}
		});
	}

	//metodo responsavel por montar o painel 
	private JComponent montaPainel() {
		FormLayout layout = new FormLayout(
				"10dlu:grow, right:p, 5dlu, p, 10dlu:grow",
				"10dlu:grow, p, 5dlu, p, 5dlu, p, 15dlu, p, 10dlu:grow");

		DefaultFormBuilder builder = new DefaultFormBuilder(layout);

		CellConstraints cc = new CellConstraints();;

		builder.addLabel("Nome:", cc.xy(2, 2));
		builder.add(textNome, cc.xy(4, 2));

		builder.addLabel("CNPJ:", cc.xy(2, 4));
		builder.add(textCnpj, cc.xy(4, 4));

		builder.addLabel("Telefone:", cc.xy(2, 6));
		builder.add(textTel, cc.xy(4, 6));

		builder.add(montaBarraBotao(), cc.xyw(2, 8, 4));

		return builder.getPanel();
	}

	private void fechar() {
		AdicionarEmpresaVisao.this.dispose();

		gerenciarEmpresasVisao.setVisible(true);
		gerenciarEmpresasVisao.atualizaTabela();
	}

	private void adicionaEmpresa() {
		String empresa = textNome.getText().toUpperCase();
		
		if(!empresa.equals("")) {
				EmpresaVO e = new EmpresaVO();
				e.setCnpj(textCnpj.getText());
				e.setDescricao(empresa);
				e.setTelefone(textTel.getText());
				
				if(controle.adicionaEmpresa(e)) {
					JOptionPane.showMessageDialog(AdicionarEmpresaVisao.this, String.format("Empresa cadastrada com sucesso"), "", JOptionPane.INFORMATION_MESSAGE);
					limpar();
				}else
					JOptionPane.showMessageDialog(AdicionarEmpresaVisao.this, String.format("Erro ao cadastrar empresa"), "", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void limpar() {
		textTel.setText("");
		textCnpj.setText("");
		textNome.setText("");
	}

	private void inicializaComponentes() {
		textNome = new JTextField(15);
		textCnpj = new JTextField(15);
		textTel = new JTextField(15);

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
